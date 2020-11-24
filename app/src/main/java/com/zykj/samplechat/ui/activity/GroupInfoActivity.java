package com.zykj.samplechat.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.rey.material.app.Dialog;
import com.rey.material.widget.Button;
import com.zykj.samplechat.R;
import com.zykj.samplechat.model.Comment;
import com.zykj.samplechat.model.Group;
import com.zykj.samplechat.model.Image;
import com.zykj.samplechat.model.User;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.presenter.GroupInfoPresenter;
import com.zykj.samplechat.ui.activity.base.ToolBarActivity;
import com.zykj.samplechat.ui.view.GroupInfoView;
import com.zykj.samplechat.utils.AESOperator;
import com.zykj.samplechat.utils.Bun;
import com.zykj.samplechat.utils.StringUtil;
import com.zykj.samplechat.utils.TimeUtil;
import com.zykj.samplechat.utils.UserUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by ninos on 2016/7/14.
 */
public class GroupInfoActivity extends ToolBarActivity<GroupInfoPresenter> implements GroupInfoView{

    private User user;
    public Group group;
    private int count = 3;
    private SpannableString spannableString;
    public LayoutInflater inflater;
    public Dialog dialogDelete;

    @Bind(R.id.input_rl)
    RelativeLayout input_rl;
    @Bind(R.id.comment)
    EditText comment;
    @Bind(R.id.send)
    Button send;
    @Bind(R.id.gi_comment)
    LinearLayout gi_comment;
    @Bind(R.id.lay_like)
    LinearLayout lay_like;
    @Bind(R.id.like_comment)
    View like_comment;
    @Bind(R.id.lay_comment)
    LinearLayout lay_comment;
    @Bind(R.id.gi_username)
    TextView gi_username;
    @Bind(R.id.gi_usercontent)
    TextView gi_usercontent;
    @Bind(R.id.gi_likepeople)
    TextView gi_likepeople;
    @Bind(R.id.gi_userdate)
    TextView gi_userdate;
    @Bind(R.id.gi_delete)
    TextView gi_delete;
    @Bind(R.id.gi_userimg)
    ImageView gi_userimg;
    @Bind(R.id.gi_usercomimg)
    ImageView gi_usercomimg;
    @Bind(R.id.gi_pics)
    LinearLayout gi_pics;
    @Bind(R.id.gi_img)
    ImageView gi_img;

    @Override
    protected CharSequence provideTitle() {
        return "详情";
    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);

        inflater = LayoutInflater.from(this);

        group = (Group) getIntent().getBundleExtra("data").getSerializable("group");
        update();
    }

    public void update(){
        final PopupWindow pop;
        LayoutInflater telinflater = LayoutInflater.from(this);
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

        if (group.UserId == new UserUtil(getContext()).getUserId())
            gi_delete.setVisibility(View.VISIBLE);
        else
            gi_delete.setVisibility(View.GONE);

        gi_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogDelete(group.Id);
            }
        });
        gi_username.setText(group.NicName);
        gi_usercontent.setText(group.Content);
        gi_userdate.setText(TimeUtil.timeAgo(group.PublishTime));
        Glide.with(this)
                .load(Const.getbase( group.PhotoPath))
                .apply(new RequestOptions().placeholder(R.drawable.userimg))
                .into(gi_userimg);


        gi_userimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        gi_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        gi_pics.removeAllViews();//先清除之前的views
//        gi_pics.setVisibility(View.GONE);
//        if (group.PicList.size() > 0) {
//            gi_pics.setVisibility(View.VISIBLE);
//            int pageCount = group.PicList.size() / count + 1;
//            for (int i = 0; i < pageCount; i++) {
//                addImg(group, i);
//            }
//        }

        if(group.VideoPath.toString().trim().equals("")) {
            gi_img.setVisibility(View.GONE);
            gi_pics.setVisibility(View.VISIBLE);
            gi_pics.removeAllViews();//先清除之前的views
            gi_pics.setVisibility(View.GONE);
            if (group.PicList.size() > 0) {
                gi_pics.setVisibility(View.VISIBLE);
                int pageCount = group.PicList.size() / count + 1;
                for (int i = 0; i < pageCount; i++) {
                    addImg(group, i);
                }
            }
        }else{
            gi_pics.setVisibility(View.GONE);
            gi_img.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load(Const.getbase( group.ImagePath))
                    .apply(new RequestOptions().placeholder(R.drawable.userimg))
                    .into(gi_img);
        }

        gi_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), PlayVideoActiviy.class).putExtra("data",new Bun().putString("path",Const.getbase(group.VideoPath)).ok()));
            }
        });

        lay_comment.removeAllViews();

        if (group.ComList.size() > 0||group.LikeNum > 0) {

            gi_comment.setVisibility(View.VISIBLE);

//            if(group.ComList.size() > 0&&group.LikeNum > 0)
//                like_comment.setVisibility(View.VISIBLE);
//
//            if(group.LikeNum > 0){
//                lay_like.setVisibility(View.VISIBLE);
//                gi_likepeople.setText(context.getString(R.string.like_people,group.LikeNum));
//            }else{
//            }

            if(group.ComList.size() > 0) {

                lay_comment.setVisibility(View.VISIBLE);

                for (int i = 0; i < group.ComList.size(); i++) {
                    final Comment comment = group.ComList.get(i);
                    final View view = inflater.inflate(R.layout.comment_item, null);
                    lay_comment.addView(view);

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
                            if (comment.CommenterId == new UserUtil(getContext()).getUserId()) {
                                presenter.DeleteComment(comment.Id);
                            } else {
                                showReplyInput(comment.Id, comment.NicName, comment.Id, view);
                            }
                        }
                    });
                }
            }else{
            }
        } else {
            gi_comment.setVisibility(View.GONE);
        }

        gi_usercomimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (pop.isShowing()) {
                    // 隐藏窗口，如果设置了点击窗口外消失，则不需要此方式隐藏
                    pop.dismiss();
                } else {
                    // 弹出窗口显示内容视图,默认以锚定视图的左下角为起点，这里为点击按钮
                    pop.showAsDropDown(gi_usercomimg, -380, -70);
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
                map.put("userid",new UserUtil(getContext()).getUserId());
                map.put("flg",2);
                String json = StringUtil.toJson(map);
                try {
                    String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
                    if(group.IsLike)
                        presenter.MessageRepeatAndFavoriteCancel(data);
                    else
                        presenter.MessageRepeatAndFavorite(data);
                    pop.dismiss();
                }catch (Exception ex){
                }
            }
        });

        pg_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCommentInput(group.Id, v);
                pop.dismiss();
            }
        });
    }

    /**
     * 添加图片
     * @param group
     * @param page
     */
    private void addImg(final Group group, final int page) {
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.layout_pic, null);
        gi_pics.addView(linearLayout);

        /**
         * 要添加的图片
         * */
        List<Image> subList = group.PicList.subList(page * count, page * count + count >= group.PicList.size() ? group.PicList.size() : page * count + count);
        for (int i = 0; i < subList.size(); i++) {
            Image pic = subList.get(i);

            LinearLayout imgWraper = (LinearLayout) inflater.inflate(R.layout.singer_pic, null);
            linearLayout.addView(imgWraper);

            Glide.with(this)
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
                    Intent intent = new Intent(GroupInfoActivity.this, PicsActivity.class);
                    intent.putExtra("data", new Bun().putInt("pos", finalI + (page * count)).
                            putString("pics", new Gson().toJson(group.PicList)).ok());
                    startActivity(intent);
                }
            });
        }

    }

    private void showDialogDelete(final int id) {
        if (dialogDelete == null)
            dialogDelete = new Dialog(this).backgroundColor(Color.parseColor("#ffffff")).contentView(R.layout.dialog_delete_msg).negativeAction("取消").negativeActionClickListener(new View.OnClickListener() {
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
                        presenter.DelDongtai(data);

                    }catch (Exception ex){
                    }
                    dialogDelete.dismiss();
                    dialogDelete.dismiss();
                }
            });
        dialogDelete.show();
    }

    public void showReplyInput(final int id, final String nicName, final int commenterId, View v) {
        input_rl.setVisibility(View.VISIBLE);
        comment.requestFocus();
        comment.setHint("回复 " + nicName + ":");
        showSoftInput(comment);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (comment.getText().toString().trim().equals("")) {
                    toast("回复内容不能为空");
                    return;
                }
                Map map = new HashMap();
                map.put("key", Const.KEY);
                map.put("uid", Const.UID);
                map.put("function", "CommentComment");
                map.put("id", id);
                map.put("userid", new UserUtil(getContext()).getUserId());
                map.put("comment", comment.getText().toString().trim());
                String json = StringUtil.toJson(map);
                try {
                    String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
                    presenter.CommentComment(data, nicName, commenterId, comment.getText().toString().trim());
                    input_rl.setVisibility(View.GONE);
                } catch (Exception ex) {
                }

            }
        });
    }

    public void showCommentInput(final int id, View v) {
        input_rl.setVisibility(View.VISIBLE);
        comment.requestFocus();
        comment.setText("");
        showSoftInput(comment);

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_group_info;
    }

    @Override
    public void initListeners() {
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (comment.getText().toString().trim().equals("")) {
                    toast("回复内容不能为空");
                    return;
                }
                Map map = new HashMap();
                map.put("key", Const.KEY);
                map.put("uid", Const.UID);
                map.put("function", "MessageComment");
                map.put("id", group.Id);
                map.put("userid", new UserUtil(getContext()).getUserId());
                map.put("comment", comment.getText().toString().trim());
                String json = StringUtil.toJson(map);
                try {
                    String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
                    presenter.MessageComment(data, comment.getText().toString().trim());
                    input_rl.setVisibility(View.GONE);
                } catch (Exception ex) {
                }

            }
        });
    }

    @Override
    public GroupInfoPresenter createPresenter() {
        return new GroupInfoPresenter();
    }

    @Override
    public void success(ArrayList<Group> data) {

    }

    @Override
    public void successLike() {
        group.IsLike = true;
        update();
    }

    @Override
    public void successLikeCancel() {
        group.IsLike = false;
        update();
    }

    @Override
    public void getDongtai(Group group) {
        this.group = group;
        update();
    }

    public void go(int id) {
        Map map = new HashMap();
        map.put("key", Const.KEY);
        map.put("uid", Const.UID);
        map.put("function", "GetDongtaiById");
        map.put("userid", new UserUtil(this).getUserId());
        map.put("messid", id);
        String json = StringUtil.toJson(map);
        try {
            String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
            presenter.GetDongtaiById(data);
        } catch (Exception ex) {
        }
    }

    @Override
    public void commentSuccess(Comment comment) {
        toast("回复成功");
        this.comment.setText("");
        user = new UserUtil(getContext()).getUser();
        comment.CommentedNicName = group.NicName;
        comment.CommenterId = new UserUtil(getContext()).getUserId();
        comment.CommentedId = group.UserId;
        comment.NicName = user.getNickName();
        group.ComList.add(comment);
        go(group.Id);
        hideSoftMethod(this.comment);
    }

    @Override
    public void replySuccess( Comment comment) {
        toast("回复成功");
        this.comment.setText("");
        user = new UserUtil(getContext()).getUser();
        comment.NicName = user.getNickName();
        comment.CommenterId = new UserUtil(getContext()).getUserId();
        group.ComList.add(comment);
        go(group.Id);
        hideSoftMethod(this.comment);
    }

    @Override
    public void successDelComment() {
        toast("评论删除成功");
        go(group.Id);
        update();
    }

    @Override
    public void successDelete() {
        toast("动态删除成功");
        finishActivity();
    }
}
