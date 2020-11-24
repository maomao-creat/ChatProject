package com.zykj.samplechat.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rey.material.app.Dialog;
import com.rey.material.widget.Button;
import com.zykj.samplechat.R;
import com.zykj.samplechat.model.TeamBean;
import com.zykj.samplechat.model.sshyBean;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.network.HttpUtils;
import com.zykj.samplechat.network.SubscriberRes;
import com.zykj.samplechat.presenter.FriendsInfoPresenter;
import com.zykj.samplechat.ui.activity.base.ToolBarActivity;
import com.zykj.samplechat.ui.view.FriendsInfoView;
import com.zykj.samplechat.utils.AESOperator;
import com.zykj.samplechat.utils.ActivityUtil;
import com.zykj.samplechat.utils.Bun;
import com.zykj.samplechat.utils.NR;
import com.zykj.samplechat.utils.StringUtil;
import com.zykj.samplechat.utils.StringUtils;
import com.zykj.samplechat.utils.ToolsUtils;
import com.zykj.samplechat.utils.UserUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

/**
 * Created by ninos on 2016/7/8.
 */
public class FriendsInfoActivity extends ToolBarActivity<FriendsInfoPresenter> implements FriendsInfoView {

    @Bind(R.id.ls_btnsc)
    Button lsBtnsc;
    @Bind(R.id.ls_zhuanzhang)
    Button lsZhuanzhang;
    private sshyBean friend;
    private int id;
    private Dialog dialog;

    @Bind(R.id.tv_menu)
    ImageView tv_menu;
    @Bind(R.id.gi_userimg)
    ImageView gi_userimg;
    @Bind(R.id.ui_userimg_i)
    ImageView ui_userimg_i;
    @Bind(R.id.ui_userimg_ii)
    ImageView ui_userimg_ii;
    @Bind(R.id.ui_userimg_iii)
    ImageView ui_userimg_iii;
    @Bind(R.id.ui_gender)
    ImageView ui_gender;
    @Bind(R.id.gi_username)
    TextView gi_username;
    @Bind(R.id.gi_userid)
    TextView gi_userid;
    @Bind(R.id.gi_usernicname)
    TextView gi_usernicname;
    @Bind(R.id.ui_aboutme)
    TextView ui_aboutme;
    @Bind(R.id.gi_adds)
    TextView gi_adds;
    @Bind(R.id.tv_black)
    TextView tv_black;
    @Bind(R.id.ui_rn)
    LinearLayout ui_rn;
    @Bind(R.id.gi_photo)
    LinearLayout gi_photo;
    @Bind(R.id.remark_name)
    LinearLayout remark_name;
    @Bind(R.id.am_ll)
    LinearLayout am_ll;
    @Bind(R.id.ls_btn)
    Button ls_btn;
    private String Type;
    private String UserCode;
    @OnClick(R.id.remark_name)
    public void remark_name() {
        startActivityForResult(new Intent(this, FriendsRemarkActivity.class).putExtra("data", new Bun().putString("id", friend.getId()).ok()), 1111);
    }

    private void showDialogMore() {
        if (dialog == null)
            dialog = new Dialog(this).backgroundColor(Color.parseColor("#ffffff")).contentView(R.layout.dialog_nouser).positiveAction("确定").positiveActionClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FriendsInfoActivity.this.finish();
                    dialog.dismiss();
                }
            });
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1111 && resultCode == 1112) {
            Map map = new HashMap();
            map.put("key", Const.KEY);
            map.put("uid", Const.UID);
            map.put("function", "GetUserInfor");
            map.put("userid", new UserUtil(this).getUserId());
            map.put("friendid", id);
            String json = StringUtil.toJson(map);
            showDialog();
            try {
                String d = AESOperator.getInstance().encrypt(json.length() + "&" + json);
                presenter.GetUserInfor(d);
            } catch (Exception e) {
                dismissDialog();
            }
        }

        if (resultCode  ==  200  ){
            toast("转账成功!");

        }
    }

    @OnClick(R.id.gi_photo)
    public void gi_photo() {
//        startActivity(new Intent(this, GroupFriendsActivity.class).putExtra("data", new Bun().putInt("id",friend.Id).ok()));
//        ActivityUtil.addActivity(this);
    }

    @OnClick(R.id.tv_menu)
    public void tv_menu() {
        startActivityForResult(new Intent(this, FriendsMenuActivity.class)
                .putExtra("data", new Bun()
                        .putString("id", friend.getId())
                        .putString("photo", friend.getHeadUrl())
                        .putString("Credit", friend.getId())
                        .putString("nicname", friend.getNickName()).ok()), 1111);
        ActivityUtil.addActivity(this);
    }
    @OnClick(R.id.ls_zhuanzhang)
    public void ls_zhuanzhang() {
        startActivityForResult(new Intent(getContext(), ZhuanZhangActivity.class).
                putExtra("id", UserCode), 200);
    }

    @OnClick(R.id.tv_clear)
    public void tv_clear() {
        RongIM.getInstance().clearMessages(Conversation.ConversationType.PRIVATE, friend.getId() + "", new RongIMClient.ResultCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean flag) {
                toast("清除成功！");
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                toast("清除失败！");
            }
        });
    }

    @OnClick(R.id.tv_black)
    public void tv_black(View v) {
        if ("0".equals(v.getTag())) {
            RongIM.getInstance().addToBlacklist(String.valueOf(friend.getId()), new RongIMClient.OperationCallback() {
                @Override
                public void onSuccess() {
                    toast("加入成功！");
                    tv_black.setText("移除黑名单");
                    tv_black.setTag("1");
                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    toast("加入失败！");
                }
            });
        } else {
            RongIM.getInstance().removeFromBlacklist(String.valueOf(friend.getId()), new RongIMClient.OperationCallback() {
                @Override
                public void onSuccess() {
                    toast("移除成功！");
                    tv_black.setText("加入黑名单");
                    tv_black.setTag("0");
                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    toast("移除失败！");
                }
            });
        }
    }

    @OnClick(R.id.ls_btn)
    public void ls_btn() {
        if (ls_btn.getText().toString().equals("添加好友")) {
            ApplyFriend(ls_btn, friend.getId());
//            Map map = new HashMap();
//            map.put("function", "" + "ApplyFriend");
//            map.put("userid", "" + new UserUtil(getContext()).getUserId2());
//            map.put("friendid", "" + friend.getId());
//            showDialog();
//            NR.posts("WebService/UserService.asmx/Entry", map, new StringCallback() {
//
//                @Override
//                public void onError(Request request, Exception e) {
//                    dismissDialog();
//                    //  LogUtils.i("xxxxx222  :", "" +e);  //输出测试
//                    IsZH.getToast(FriendsInfoActivity.this, "服务器错误");  //吐司
//                }
//
//                @Override
//                public void onResponse(String s) {
//                    dismissDialog();
//                    //  LogUtils.i("xxxxx", "" +s);  //输出测试
//                    if (NRUtils.getYse(FriendsInfoActivity.this, s)) {
//                        IsZH.getToast(FriendsInfoActivity.this, "好友请求已发送!");  //吐司
//                        // MyYuEBean mye = NRUtils.getData(s,MyYuEBean.class);
//                        finish();
//                    }
//                }
//
//            });
//            Map map = new HashMap();
//            map.put("key", Const.KEY);
//            map.put("uid",Const.UID);
//            map.put("function","SaveFriend");
//            map.put("userid",new UserUtil(this).getUserId());
//            map.put("friendid",friend.getId());
//            String json = StringUtil.toJson(map);
//            showDialog();
//            try {
//                String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
////                presenter.SaveFriend(data,friend.getId());
//                presenter.SaveFriend(data,1);
//            }catch (Exception e){
//                dismissDialog();
//            }
        } else {
            // RongIM.getInstance().startPrivateChat(getContext(), friend.getId() + "", friend.getNickName().trim().equals("")?friend.getNickName():friend.getNickName());
            RongIM.getInstance().startPrivateChat(getContext(), friend.getId(), "" + friend.getNickName());
            // RongIM.getInstance().startPrivateChat(getContext(),"666666", ""+friend.getNickName());
            RongIM.getInstance().refreshUserInfoCache(new UserInfo(friend.getId(), "" + friend.getNickName(), Uri.parse("" + NR.url + friend.getHeadUrl())));
            ActivityUtil.addActivity(this);
            ActivityUtil.finishActivitys();
        }
    }

    public void ApplyFriend(View rootView, String friendid) {
        HttpUtils.ApplyFriend(new SubscriberRes<String>(rootView) {
            @Override
            public void onSuccess(String userBean) {
                ToolsUtils.toast(getContext(), "已提交申请");
            }

            @Override
            public void completeDialog() {
            }
        }, friendid);
    }

    @Override
    protected CharSequence provideTitle() {
        return "详细资料";
    }

    String bs = "0";

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        String id = getIntent().getStringExtra("id");
        UserCode = getIntent().getStringExtra("id");

        Type=getIntent().getStringExtra("type");
        if ("0".equals(Type)){
            if (UserCode.equals(new UserUtil(getContext()).getUser().getUserCode())){
                lsZhuanzhang.setVisibility(View.GONE);
            }else  {
                lsZhuanzhang.setVisibility(View.VISIBLE);
            }
        }else  {
            lsZhuanzhang.setVisibility(View.GONE);
        }
        if (!StringUtil.isEmpty(id)) {
            HttpUtils.GetTeam(new SubscriberRes<TeamBean>() {
                @Override
                public void onSuccess(TeamBean teamBean) {
//                    fid=teamBean.Id;
                    HttpUtils.SearchFriendById(new SubscriberRes<sshyBean>() {
                        @Override
                        public void onSuccess(sshyBean userBean) {
                            friend = userBean;
                            UserInfoShow(true);
                        }

                        @Override
                        public void completeDialog() {
                        }
                    }, teamBean.Id);
                }

                @Override
                public void completeDialog() {
                    dismissDialog();
                }
            }, id, "1");


        } else {
            friend = (sshyBean) getIntent().getBundleExtra("data").getSerializable("friend");
            if (StringUtils.isEmpty(getIntent().getBundleExtra("data").getString("bs"))) {
                bs = "0";
            } else {
                bs = getIntent().getBundleExtra("data").getString("bs");
            }

            UserInfoShow(true);
        }
//        try {
//
//            if(friend==null){
//                id = getIntent().getBundleExtra("data").getInt("id");
//                Map map = new HashMap();
//                map.put("key", Const.KEY);
//                map.put("uid",Const.UID);
//                map.put("function","GetUserInfor");
//                map.put("userid",new UserUtil(this).getUserId());
//                map.put("friendid",id);
//                String json = StringUtil.toJson(map);
//                showDialog();
//                try {
//                    String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
//                    presenter.GetUserInfor(data);
//                }catch (Exception e){
//                    dismissDialog();
//                }
//            }else{
////                id = friend.getId();
//                id = 1;
//                UserInfoShow(false);
//            }
//
//        }catch (Exception ex){
//
//        }

    }

    public void UserInfoShow(boolean isId) {
//        if(!StringUtil.isEmail(friend.getHeadUrl())) {
//            Glide.with(this)
//                    .load(Const.getbase(friend.getHeadUrl()))
//                    .centerCrop()
//                    .crossFade()
//                    .placeholder(R.drawable.userimg)
//                    .into(gi_userimg);
//        }
//        PicassoUtil.loadPicassoARGB_8888(getContext(), Const.getbase(friend.getHeadUrl()), gi_userimg, false);//加载网络图片

        Glide.with(getContext())
                .load(Const.getbase(friend.getHeadUrl()))
                .apply(new RequestOptions().placeholder(R.drawable.userimg))
                .into(gi_userimg);
        gi_username.setText(friend.getNickName() + "");
        gi_userid.setText("社交号:" + friend.getUserCode());//小莫账号
//        RongIM.getInstance().refreshUserInfoCache(new UserInfo(getIntent().getStringExtra("id"), friend.getNickName(), Uri.parse(Const.getbase(friend.getHeadUrl()))));
        //ui_gender.setImageResource(R.drawable.quanbnu);//加载本地图片
        if (StringUtils.isEmpty(bs)) {
            bs = "0";
        }

        if (bs.equals("1")) {//只有搜索才能走这里
            if (isId)
                if (new UserUtil(this).getUserId2().equals(friend.getId())) {
                    ls_btn.setVisibility(View.GONE);
                    ui_rn.setVisibility(View.GONE);
                    tv_menu.setVisibility(View.GONE);
                } else if (friend.getFriend()) {
                    ls_btn.setText("发送消息");
                    ls_btn.setVisibility(View.VISIBLE);
                    lsBtnsc.setVisibility(View.VISIBLE);
                    ui_rn.setVisibility(View.GONE);
                    tv_menu.setVisibility(View.GONE);
                } else {
                    ls_btn.setText("添加好友");
                    ls_btn.setVisibility(View.VISIBLE);
                    ui_rn.setVisibility(View.GONE);
                    tv_menu.setVisibility(View.GONE);
                    lsBtnsc.setVisibility(View.GONE);
                }
        } else {
            ls_btn.setVisibility(View.GONE);
            ui_rn.setVisibility(View.GONE);
            tv_menu.setVisibility(View.GONE);
        }

        //    else
//        if (new UserUtil(this).getUserId2().equals(friend.getId())) {
//            ls_btn.setVisibility(View.GONE);
//            ui_rn.setVisibility(View.GONE);
//        } else if (friend.IsWuyou == 1) {
//            ls_btn.setText("发送消息");
//            ls_btn.setVisibility(View.VISIBLE);
//            ui_rn.setVisibility(View.VISIBLE);
//        } else {
//            ls_btn.setText("添加好友");
//            ls_btn.setVisibility(View.VISIBLE);
//            ui_rn.setVisibility(View.GONE);
//            tv_menu.setVisibility(View.GONE);
//        }
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_user_info;
    }

    @Override
    public void initListeners() {
    }

    @Override
    public FriendsInfoPresenter createPresenter() {
        return new FriendsInfoPresenter();
    }

    @Override
    public void successFound(sshyBean friend) {
        dismissDialog();
        this.friend = friend;
        UserInfoShow(true);
//        if(friend.Credit != 1){
//            tv_black.setVisibility(View.VISIBLE);
//            RongIM.getInstance().getBlacklistStatus(String.valueOf(friend.Id),new RongIMClient.ResultCallback<RongIMClient.BlacklistStatus>() {
//                @Override
//                public void onSuccess(RongIMClient.BlacklistStatus blacklistStatus) {
//                    if (blacklistStatus == RongIMClient.BlacklistStatus.IN_BLACK_LIST) {
//                        tv_black.setText("移除黑名单");
//                        tv_black.setTag("1");
//                    }else{
//                        tv_black.setText("加入黑名单");
//                        tv_black.setTag("0");
//                    }
//                }
//                @Override
//                public void onError(RongIMClient.ErrorCode errorCode) {}
//            });
//        }
    }

    @Override
    public void errorFound() {
        showDialogMore();
        dismissDialog();
    }



    @OnClick(R.id.ls_btnsc)
    public void onViewClicked() {//删除好友
        HttpUtils.DeleteFriend(new SubscriberRes<String>() {
            @Override
            public void onSuccess(String userBean) {
                ToolsUtils.toast(getContext(), "删除好友成功");
                finish();
            }

            @Override
            public void completeDialog() {
            }
        }, Integer.parseInt(friend.getId()));
    }


}