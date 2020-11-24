package com.zykj.samplechat.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.rey.material.app.Dialog;
import com.zykj.samplechat.R;
import com.zykj.samplechat.model.Comment;
import com.zykj.samplechat.model.Group;
import com.zykj.samplechat.model.Image;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.presenter.GroupPresenter;
import com.zykj.samplechat.ui.activity.GroupFriendsActivity;
import com.zykj.samplechat.ui.activity.PicsActivity;
import com.zykj.samplechat.ui.activity.PlayVideoActiviy;
import com.zykj.samplechat.ui.adapter.base.BaseAdapter;
import com.zykj.samplechat.utils.AESOperator;
import com.zykj.samplechat.utils.Bun;
import com.zykj.samplechat.utils.StringUtil;
import com.zykj.samplechat.utils.TimeUtil;
import com.zykj.samplechat.utils.UserUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ninos on 2016/6/24.
 */
public class GroupAdapter extends BaseAdapter<GroupAdapter.VHolder, Group, GroupPresenter> {

    private int count = 3;
    private SpannableString spannableString;
    public Dialog dialogDelete;

    public GroupAdapter(Context context, GroupPresenter groupPresenter, View header) {
        super(context, groupPresenter, header);
        isAddFooter = false;
    }

    @Override
    public int provideItemLayoutId() {
        return R.layout.fragment_group_item;
    }

    @Override
    public VHolder createVH(ViewGroup parent, int viewType, View view) {
        return new VHolder(view);
    }

    @Override
    public void bindData(final VHolder holder, final int position) {
        final Group group = data.get(position);

        final PopupWindow pop;
        LayoutInflater telinflater = LayoutInflater.from(context);
        View v = telinflater.inflate(R.layout.pop_group, null);

        LinearLayout pg_like = (LinearLayout) v.findViewById(R.id.pg_like);
        LinearLayout pg_comment = (LinearLayout) v.findViewById(R.id.pg_comment);
        TextView like = (TextView) v.findViewById(R.id.like);
        like.setText(group.IsLike?"取消赞":"赞");
        v.setFocusableInTouchMode(true);
        // PopupWindow实例化
        pop = new PopupWindow(v, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);

        /**
         * 改变背景可拉的弹出窗口。后台可以设置为null。 这句话必须有，否则按返回键popwindow不能消失 或者加入这句话
         * ColorDrawable dw = new
         * ColorDrawable(-00000);pop.setBackgroundDrawable(dw);
         */
        pop.setBackgroundDrawable(new BitmapDrawable());

        if (group.UserId == new UserUtil(context).getUserId())
            holder.gi_delete.setVisibility(View.VISIBLE);
        else
            holder.gi_delete.setVisibility(View.GONE);
        holder.gi_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogDelete(group.Id);
            }
        });
        holder.gi_username.setText(group.NicName);
        holder.gi_usercontent.setText(group.Content);
        holder.gi_userdate.setText(TimeUtil.timeAgo(group.PublishTime));
        Glide.with(context)
                .load(Const.getbase(group.PhotoPath))
                .apply(new RequestOptions().placeholder(R.drawable.userimg))
                .into(holder.gi_userimg);


        holder.gi_userimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, GroupFriendsActivity.class).putExtra("data", new Bun().putInt("id",group.UserId).ok()));
            }
        });

        holder.gi_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, GroupFriendsActivity.class).putExtra("data", new Bun().putInt("id",group.UserId).ok()));
            }
        });


        if(group.VideoPath.toString().trim().equals("")) {
            holder.gi_img.setVisibility(View.GONE);
            holder.gi_pics.setVisibility(View.VISIBLE);
            holder.gi_pics.removeAllViews();//先清除之前的views
            holder.gi_pics.setVisibility(View.GONE);
            if (group.PicList.size() > 0) {
                holder.gi_pics.setVisibility(View.VISIBLE);
                int pageCount = group.PicList.size() / count + 1;
                for (int i = 0; i < pageCount; i++) {
                    addImg(holder, group, i);
                }
            }
        }else{
            holder.gi_pics.setVisibility(View.GONE);
            holder.gi_img.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(Const.getbase(group.ImagePath))
                    .apply(new RequestOptions().placeholder(R.drawable.userimg))
                    .into(holder.gi_img);
        }

        holder.gi_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, PlayVideoActiviy.class).putExtra("data",new Bun().putString("path",Const.getbase(group.VideoPath)).ok()));
            }
        });

        holder.lay_comment.removeAllViews();

        if (group.ComList.size() > 0||group.LikeNum > 0) {

            holder.gi_comment.setVisibility(View.VISIBLE);

            if (group.LikeNum > 0) {
                holder.like_comment.setVisibility(View.VISIBLE);
                holder.lay_like.setVisibility(View.VISIBLE);
                holder.gi_likepeople.setText(context.getString(R.string.like_people, group.LikeNum));
            } else {
                holder.like_comment.setVisibility(View.GONE);
                holder.lay_like.setVisibility(View.GONE);
            }

            if(group.ComList.size() > 0) {

                holder.lay_comment.setVisibility(View.VISIBLE);

                for (int i = 0; i < group.ComList.size(); i++) {
                    final Comment comment = group.ComList.get(i);
                    final View view = _inflater.inflate(R.layout.comment_item, null);
                    holder.lay_comment.addView(view);

                    TextView tvConent = (TextView) view.findViewById(R.id.tv_conent);

                    String content = "";

                    if (comment.ParentId == 0) {
                        content = comment.NicName + "：" + comment.Content;
                        spannableString = new SpannableString(content);
                        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#225599")), 0, comment.NicName.length(), 0);
                    } else {
                        content = comment.NicName + "回复" + comment.CommentedNicName + "：" + comment.Content;
                        spannableString = new SpannableString(content);
                        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#225599")), 0, comment.NicName.length(), 0);
                        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#225599")), comment.NicName.length() + 2, comment.NicName.length() + 2 + comment.CommentedNicName.length(), 0);
                    }

                    tvConent.setText(spannableString);

                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (comment.CommenterId == new UserUtil(context).getUserId()) {
                                _presenter.DeleteComment(comment.Id);
                            } else {
                                _presenter.showReplyInput(comment.Id, comment.NicName, comment.Id, position, view);
                            }
                        }
                    });
                }
            }else{
                holder.lay_comment.setVisibility(View.GONE);
            }
        } else {
            holder.gi_comment.setVisibility(View.GONE);
        }

        holder.gi_usercomimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pop.isShowing()) {
                    // 隐藏窗口，如果设置了点击窗口外消失，则不需要此方式隐藏
                    pop.dismiss();
                } else {
                    // 弹出窗口显示内容视图,默认以锚定视图的左下角为起点，这里为点击按钮
                    pop.showAsDropDown(holder.gi_usercomimg, -380, -70);
                }
            }

        });

        pg_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map map = new HashMap();
                map.put("key", Const.KEY);
                map.put("uid",Const.UID);
                map.put("function",group.IsLike?"MessageRepeatAndFavoriteCancel":"MessageRepeatAndFavorite");
                map.put("id",group.Id);
                map.put("userid",new UserUtil(context).getUserId());
                map.put("flg",2);
                String json = StringUtil.toJson(map);
                try {
                    String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
                    if(group.IsLike)
                        _presenter.MessageRepeatAndFavoriteCancel(data, position);
                    else
                        _presenter.MessageRepeatAndFavorite(data,position);
                    pop.dismiss();
                }catch (Exception ex){
                }
            }
        });

        pg_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _presenter.showCommentInput(group.Id, position, v);
                pop.dismiss();
            }
        });
    }

    private void showDialogDelete(final int id) {
        dialogDelete = null;
        if (dialogDelete == null)
            dialogDelete = new Dialog(context).backgroundColor(Color.parseColor("#ffffff")).contentView(R.layout.dialog_delete_msg).negativeAction("取消").negativeActionClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogDelete.dismiss();
                }
            }).positiveAction("确定").positiveActionClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Map map = new HashMap();
                    map.put("key", Const.KEY);
                    map.put("uid",Const.UID);
                    map.put("function","DelDongtai");
                    map.put("id",id);
                    String json = StringUtil.toJson(map);
                    try {
                        String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
                            _presenter.DelDongtai(data);

                    }catch (Exception ex){
                    }
                    dialogDelete.dismiss();
                }
            });
        dialogDelete.show();
    }

    /**
     * 添加图片
     *
     * @param holder
     * @param group
     * @param page
     */
    private void addImg(VHolder holder, final Group group, final int page) {
        LinearLayout linearLayout = (LinearLayout) _inflater.inflate(R.layout.layout_pic, null);
        holder.gi_pics.addView(linearLayout);

        /**
         * 要添加的图片
         * */
        List<Image> subList = group.PicList.subList(page * count, page * count + count >= group.PicList.size() ? group.PicList.size() : page * count + count);
        for (int i = 0; i < subList.size(); i++) {
            Image pic = subList.get(i);

            LinearLayout imgWraper = (LinearLayout) _inflater.inflate(R.layout.singer_pic, null);
            linearLayout.addView(imgWraper);

            Glide.with(context)
                    .load(Const.getbase(pic.ImagePath))
                    .apply(new RequestOptions().placeholder(R.drawable.userimg))
                    .into((ImageView) imgWraper.findViewById(R.id.img));

            final int finalI = i;
            imgWraper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /**
                     * 到图片集页
                     * */
                    for (Image pic1 : group.PicList) {
                        pic1.PictureUrl = pic1.ImagePath;
                    }
                    Intent intent = new Intent(context, PicsActivity.class);
                    intent.putExtra("data", new Bun().putInt("pos", finalI + (page * count)).
                            putString("pics", new Gson().toJson(group.PicList)).ok());
                    context.startActivity(intent);
                }
            });
        }

    }

    class VHolder extends RecyclerView.ViewHolder {

        @Nullable
        @Bind(R.id.gi_comment)
        LinearLayout gi_comment;
        @Nullable
        @Bind(R.id.lay_like)
        LinearLayout lay_like;
        @Nullable
        @Bind(R.id.like_comment)
        View like_comment;
        @Nullable
        @Bind(R.id.lay_comment)
        LinearLayout lay_comment;
        @Nullable
        @Bind(R.id.gi_username)
        TextView gi_username;
        @Nullable
        @Bind(R.id.gi_usercontent)
        TextView gi_usercontent;
        @Nullable
        @Bind(R.id.gi_likepeople)
        TextView gi_likepeople;
        @Nullable
        @Bind(R.id.gi_userdate)
        TextView gi_userdate;
        @Nullable
        @Bind(R.id.gi_userimg)
        ImageView gi_userimg;
        @Nullable
        @Bind(R.id.gi_usercomimg)
        ImageView gi_usercomimg;
        @Nullable
        @Bind(R.id.gi_img)
        ImageView gi_img;
        @Nullable
        @Bind(R.id.gi_delete)
        TextView gi_delete;
        @Nullable
        @Bind(R.id.gi_pics)
        LinearLayout gi_pics;

        VHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
