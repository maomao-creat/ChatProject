package com.zykj.samplechat.ui.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rey.material.app.Dialog;
import com.rey.material.widget.Button;
import com.yancy.imageselector.ImageConfig;
import com.yancy.imageselector.ImageSelector;
import com.yancy.imageselector.ImageSelectorActivity;
import com.zykj.samplechat.R;
import com.zykj.samplechat.model.Comment;
import com.zykj.samplechat.model.Group;
import com.zykj.samplechat.model.User;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.presenter.GroupPresenter;
import com.zykj.samplechat.ui.activity.GroupFriendsActivity;
import com.zykj.samplechat.ui.activity.NewRecordVideoForPublishActivity;
import com.zykj.samplechat.ui.activity.PublishGroupActivity;
import com.zykj.samplechat.ui.activity.base.SwipeRecycleViewFragment;
import com.zykj.samplechat.ui.adapter.GroupAdapter;
import com.zykj.samplechat.ui.view.GroupView;
import com.zykj.samplechat.utils.AESOperator;
import com.zykj.samplechat.utils.Bun;
import com.zykj.samplechat.utils.GlideLoader;
import com.zykj.samplechat.utils.StringUtil;
import com.zykj.samplechat.utils.UserUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 ******************************************************
 *                                                    *
 *                                                    *
 *                       _oo0oo_                      *
 *                      o8888888o                     *
 *                      88" . "88                     *
 *                      (| -_- |)                     *
 *                      0\  =  /0                     *
 *                    ___/`---'\___                   *
 *                  .' \\|     |# '.                  *
 *                 / \\|||  :  |||# \                 *
 *                / _||||| -:- |||||- \               *
 *               |   | \\\  -  #/ |   |               *
 *               | \_|  ''\---/''  |_/ |              *
 *               \  .-\__  '-'  ___/-. /              *
 *             ___'. .'  /--.--\  `. .'___            *
 *          ."" '<  `.___\_<|>_/___.' >' "".          *
 *         | | :  `- \`.;`\ _ /`;.`/ - ` : | |        *
 *         \  \ `_.   \_ __\ /__ _/   .-` /  /        *
 *     =====`-.____`.___ \_____/___.-`___.-'=====     *
 *                       `=---='                      *
 *                                                    *
 *                                                    *
 *     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~    *
 *                                                    *
 *               佛祖保佑         永无BUG               *
 *                                                    *
 *                                                    *
 ******************************************************
 *
 * Created by ninos on 2016/6/2.
 *
 */
public class GroupFragment extends SwipeRecycleViewFragment<GroupPresenter,GroupAdapter,Group> implements GroupView {

    private User user;
    private Dialog dialog;
    private View view;
    private LinearLayout dg_dongtai;
    private LinearLayout dg_shipin;
    private int commentId;

    private Dialog dialog_img;
    private View view_img;
    private LinearLayout di_img;

    ImageView group_top_userimg;
    TextView group_top_username;
    ImageView group_top_img;

    @Bind(R.id.input_rl)
    RelativeLayout input_rl;
    @Bind(R.id.img_action)
    ImageView img_action;
    @Bind(R.id.comment)
    EditText comment;
    @Bind(R.id.send)
    Button send;

    @OnClick(R.id.img_action)
    public void img_action() {
        showDialogMore();
    }

    private View header;
    private ArrayList<Group> list = new ArrayList<Group>();

    @Override
    public void initListeners() {
        dg_dongtai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivityForResult(new Intent(getContext(),PublishGroupActivity.class), 1001);
                dialog.dismiss();
            }
        });

        dg_shipin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivityForResult(new Intent(getContext(),NewRecordVideoForPublishActivity.class), 1001);
                dialog.dismiss();
            }
        });

        di_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageConfig imageConfig
                        = new ImageConfig.Builder(new GlideLoader())
                        .steepToolBarColor(getResources().getColor(R.color.colorPrimary))
                        .titleBgColor(getResources().getColor(R.color.colorPrimary))
                        .titleSubmitTextColor(getResources().getColor(R.color.white))
                        .titleTextColor(getResources().getColor(R.color.white))
                        // (截图默认配置：关闭    比例 1：1    输出分辨率  500*500)
                        .crop(1,1,1000,1000)
                        // 开启单选   （默认为多选）
                        .singleSelect()
                        // 开启拍照功能 （默认关闭）
                        .showCamera()
                        // 拍照后存放的图片路径（默认 /temp/picture） （会自动创建）
                        .filePath("/ImageSelector/Pictures")
                        .build();

                ImageSelector.open(GroupFragment.this, imageConfig);   // 开启图片选择器
            }
        });

        group_top_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogImage();
            }
        });

        group_top_userimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), GroupFriendsActivity.class).putExtra("data", new Bun().putInt("id",new UserUtil(getContext()).getUserId()).ok()));
//                    startActivity(FriendsInfoActivity.class,new Bun().putInt("id",new UserUtil(getContext()).getUserId()).ok());
            }
        });
    }

    private void showDialogImage(){
        if(dialog_img == null)
            dialog_img = new Dialog(getContext()).backgroundColor(Color.parseColor("#ffffff")).contentView(view_img);
        dialog_img.show();
    }

    @Override
    public void onResume() {
        super.onResume();
//        user = new UserUtil(getContext()).getUser();
//
//        group_top_username.setText(user.NicName);
//        Glide.with(this)
//                .load(Const.BASE + user.PhotoPath)
//                .centerCrop()
//                .crossFade()
//                .placeholder(R.drawable.userimg)
//                .into(group_top_userimg);
//
//        presenter.getData(page, count);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden) {
            user = new UserUtil(getContext()).getUser();

            group_top_username.setText(user.getNickName());
            Glide.with(this)
                    .load(Const.getbase(user.getHeadUrl()))
                    .apply(new RequestOptions().placeholder(R.drawable.userimg))
                    .into(group_top_userimg);

            presenter.getData(page, count);
        }
    }

    @Override
    protected void initThings(View view) {
        super.initThings(view);

        group_top_userimg = (ImageView) header.findViewById(R.id.group_top_userimg);
        group_top_username = (TextView) header.findViewById(R.id.group_top_username);
        group_top_img = (ImageView) header.findViewById(R.id.group_top_img);

        this.view = getActivity().getLayoutInflater().inflate(R.layout.dialog_dongtai, null);
        dg_dongtai = (LinearLayout) this.view.findViewById(R.id.dg_dongtai);
        dg_shipin = (LinearLayout) this.view.findViewById(R.id.dg_shipin);
        this.view_img = getActivity().getLayoutInflater().inflate(R.layout.dialog_img, null);
        di_img = (LinearLayout) this.view_img.findViewById(R.id.di_img);

        user = new UserUtil(getContext()).getUser();

        group_top_username.setText(user.getNickName());
        Glide.with(this)
                .load(Const.getbase(user.getHeadUrl()))
                .apply(new RequestOptions().placeholder(R.drawable.userimg))
                .into(group_top_userimg);

        presenter.getData(page, count);

    }

    private void showDialogMore() {
        if (dialog == null)
            dialog = new Dialog(getContext()).backgroundColor(Color.parseColor("#ffffff")).contentView(view);
        dialog.show();
    }

    @Override
    public int provideLayoutId() {
        return R.layout.fragment_group;
    }

    @Override
    public GroupPresenter createPresenter() {
        return new GroupPresenter();
    }

    @Override
    protected GroupAdapter provideAdapter() {
        header = getActivity().getLayoutInflater().inflate(R.layout.fragment_group_header, null);
        return new GroupAdapter(getContext(), presenter, header);
    }

    @Override
    protected RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @Override
    public void onscroll(RecyclerView recyclerView, int dx, int dy) {
        super.onscroll(recyclerView, dx, dy);
        input_rl.setVisibility(View.GONE);
        hideSoftMethod(comment);
    }

    @Override
    public void onItemClick(View view, int pos, Group item) {
    }

    @Override
    public void topImage(String path) {
        Glide.with(this)
                .load(Const.getbase( path))
                .apply(new RequestOptions().placeholder(R.drawable.group_top))
                .into(group_top_img);

        dismissDialog();
        dialog_img.dismiss();
    }

    @Override
    public void success(ArrayList<Group> data,String path) {
        Glide.with(this)
                .load(Const.getbase(Const.getbase( path)))
                .apply(new RequestOptions().placeholder(R.drawable.group_top))
                .into(group_top_img);
        bd(data);
    }

    @Override
    public void successLike(int pos) {
        adapter.data.get(pos).IsLike = true;
        toast("点赞成功，请刷新该页面");
        requestDataRefresh();
    }

    @Override
    public void successLikeCancel(int pos) {
        adapter.data.get(pos).IsLike = false;
        toast("取消赞成功，请刷新该页面");
        requestDataRefresh();
    }

    @Override
    public void showCommentInput(final int id, final int position, View v) {
        input_rl.setVisibility(View.VISIBLE);
        comment.requestFocus();
        comment.setText("");
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
                map.put("function", "MessageComment");
                map.put("id", id);
                map.put("userid", new UserUtil(getContext()).getUserId());
                map.put("comment", comment.getText().toString().trim());
                String json = StringUtil.toJson(map);
                try {
                    String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
                    presenter.MessageComment(data, comment.getText().toString().trim(), position);
                    input_rl.setVisibility(View.GONE);
                } catch (Exception ex) {
                }

            }
        });
    }

    @Override
    public void commentSuccess(int position, Comment comment) {
        toast("回复成功");
        this.comment.setText("");
        this.comment.setHint("");
        user = new UserUtil(getContext()).getUser();
        Group group = adapter.data.get(position);
        comment.CommentedNicName = group.NicName;
        comment.CommenterId = new UserUtil(getContext()).getUserId();
        comment.CommentedId = group.UserId;
        comment.NicName = user.getNickName();
        group.ComList.add(comment);
        adapter.notifyDataSetChanged();
        input_rl.setVisibility(View.GONE);
        hideSoftMethod(this.comment);
    }

    @Override
    public void showReplyInput(final int id, final String nicName, final int commenterId, final int position, View v) {
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
                    presenter.CommentComment(data, nicName, commenterId, comment.getText().toString().trim(), position);
                    input_rl.setVisibility(View.GONE);
                } catch (Exception ex) {
                }

            }
        });
    }

    @Override
    public void replySuccess(int position, Comment comment) {
        toast("回复成功");
        this.comment.setHint("");
        this.comment.setText("");
        user = new UserUtil(getContext()).getUser();
        Group group = adapter.data.get(position);
        comment.NicName = user.getNickName();
        comment.CommenterId = new UserUtil(getContext()).getUserId();
        group.ComList.add(comment);
        adapter.notifyDataSetChanged();
        input_rl.setVisibility(View.GONE);
        hideSoftMethod(this.comment);
    }

    @Override
    public void deleteConditionSuccess(int position) {

    }

    @Override
    public void successDelComment() {
        toast("评论删除成功");
        requestDataRefresh();
    }

    @Override
    public void successDelete() {
        toast("动态删除成功");
        requestDataRefresh();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ImageSelector.IMAGE_REQUEST_CODE && resultCode == -1 && data != null) {

            // Get Image Path List
            List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
            Bitmap bitmap = BitmapFactory.decodeFile(pathList.get(0));
            presenter.UpLoadImage(pathList.get(0), new UserUtil(getContext()).getUserId() + "");
            showDialog();
        }

        if (requestCode == 1001 && resultCode == 1002){
            toast("发布成功");
            layoutManager.scrollToPosition(0);
            page = 1;
            presenter.getData(page,count);
        }
    }
}
