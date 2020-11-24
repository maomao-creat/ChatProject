package com.zykj.samplechat.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rey.material.app.Dialog;
import com.yancy.imageselector.ImageConfig;
import com.yancy.imageselector.ImageSelector;
import com.yancy.imageselector.ImageSelectorActivity;
import com.zykj.samplechat.R;
import com.zykj.samplechat.model.Friend;
import com.zykj.samplechat.model.Group;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.presenter.GroupFriendsPresenter;
import com.zykj.samplechat.ui.activity.base.SwipeRecycleViewActivity;
import com.zykj.samplechat.ui.adapter.GroupFriendsAdapter;
import com.zykj.samplechat.ui.view.GroupFriendsView;
import com.zykj.samplechat.utils.AESOperator;
import com.zykj.samplechat.utils.GlideLoader;
import com.zykj.samplechat.utils.StringUtil;
import com.zykj.samplechat.utils.UserUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ninos on 2016/7/13.
 */
public class GroupFriendsActivity extends SwipeRecycleViewActivity<GroupFriendsPresenter,GroupFriendsAdapter,Group> implements GroupFriendsView {

    private View header;
    private ArrayList<Group> list = new ArrayList<Group>();
    private Friend friend;
    private int id;

    ImageView group_top_userimg;
    TextView group_top_username;
    ImageView group_top_img;

    private Dialog dialog_img;
    private View view_img;
    private LinearLayout di_img;

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        group_top_userimg = (ImageView) header.findViewById(R.id.group_top_userimg);
        group_top_username = (TextView) header.findViewById(R.id.group_top_username);
        group_top_img = (ImageView) header.findViewById(R.id.group_top_img);
        this.view_img = getLayoutInflater().inflate(R.layout.dialog_img, null);
        di_img = (LinearLayout) this.view_img.findViewById(R.id.di_img);
        id = getIntent().getBundleExtra("data").getInt("id");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Map map = new HashMap();
        map.put("key", Const.KEY);
        map.put("uid",Const.UID);
        map.put("function","GetUserInfor");
        map.put("userid",new UserUtil(this).getUserId());
        map.put("friendid",id);
        String json = StringUtil.toJson(map);
        try {
            String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
            presenter.GetUserInfor(data);
        }catch (Exception e){
        }
    }

    @Override
    protected GroupFriendsAdapter provideAdapter() {
        header = getLayoutInflater().inflate(R.layout.fragment_friend_group_header, null);
        return new GroupFriendsAdapter(getContext(), presenter, header);
    }

    @Override
    protected RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_friend_group;
    }

    @Override
    public void initListeners() {
        group_top_userimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(friend!=null)
//                    startActivity(FriendsInfoActivity.class,new Bun().putInt("id",friend.Id).ok());
            }
        });

        if(id == new UserUtil(this).getUserId()) {
            group_top_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialogImage();
                }
            });
        }

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

                ImageSelector.open(GroupFriendsActivity.this, imageConfig);   // 开启图片选择器
            }
        });
    }

    private void showDialogImage(){
        if(dialog_img == null)
            dialog_img = new Dialog(getContext()).backgroundColor(Color.parseColor("#ffffff")).contentView(view_img);
        dialog_img.show();
    }

    @Override
    public GroupFriendsPresenter createPresenter() {
        return new GroupFriendsPresenter();
    }

    @Override
    public void onItemClick(View view, int pos, Group item) {

    }

    @Override
    protected CharSequence provideTitle() {
        return "好友动态";
    }

    @Override
    public void success(ArrayList<Group> data,String path) {
        list = data;
        Glide.with(this)
                .load(Const.getbase( path))
                .apply(new RequestOptions().placeholder(R.drawable.group_top))
                .into(group_top_img);
        bd(data);
    }

    @Override
    public void successUserInfo(Friend friend) {
        this.friend = friend;
        group_top_username.setText(friend.RemarkName.trim().equals("")?friend.NicName:friend.RemarkName);
        Glide.with(this)
                .load(Const.getbase(friend.PhotoPath))
                .apply(new RequestOptions().placeholder(R.drawable.userimg))
                .into(group_top_userimg);
        tvTitle.setText(friend.RemarkName.trim().equals("")?friend.NicName:friend.RemarkName);
        presenter.getData(page,count);

    }

    @Override
    public int getFriendId() {
        return id;
    }

    @Override
    public void topImage(String path) {
        Glide.with(this)
                .load(Const.getbase(path))
                .apply(new RequestOptions().placeholder(R.drawable.group_top))
                .into(group_top_img);

        dismissDialog();
        dialog_img.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ImageSelector.IMAGE_REQUEST_CODE && resultCode == -1 && data != null) {

            // Get Image Path List
            List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
            Bitmap bitmap = BitmapFactory.decodeFile(pathList.get(0));
            presenter.UpLoadImage(pathList.get(0), new UserUtil(getContext()).getUserId() + "");
            showDialog();
        }

    }
}
