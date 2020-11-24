package com.zykj.samplechat.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.rey.material.widget.Button;
import com.zykj.samplechat.R;
import com.zykj.samplechat.presenter.FriendsMenuPresenter;
import com.zykj.samplechat.ui.activity.base.ToolBarActivity;
import com.zykj.samplechat.ui.view.FriendsMenuView;
import com.zykj.samplechat.utils.ActivityUtil;
import com.zykj.samplechat.utils.Bun;

import butterknife.Bind;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.TextMessage;

/**
 * Created by ninos on 2016/7/13.
 */
public class FriendsMenuActivity extends ToolBarActivity<FriendsMenuPresenter> implements FriendsMenuView {

    private int id;
    private String photo;
    private String nicname;
    @Bind(R.id.ls_btn)Button ls_btn;
    @Bind(R.id.ou_vip)RelativeLayout ou_vip;
    @Bind(R.id.tuijian)RelativeLayout tuijian;

    @OnClick(R.id.tuijian)
    public void tuijian(){
        startActivity(new Intent(this, FriendsTuijianActivity.class).putExtra("data", new Bun().putInt("id",id).putString("photo",photo).putString("nicname",nicname).ok()));
    }

    @OnClick(R.id.ou_vip)
    public void ou_vip(){
        startActivity(new Intent(this, FriendsRemarkActivity.class).putExtra("data", new Bun().putInt("id",id).ok()));
    }

    @OnClick(R.id.ls_btn)
    public void ls_btn(){
        presenter.DeleteFriend(id);
    }

    @Override
    protected void onStop() {
        super.onStop();
        setResult(1112);
    }

    @Override
    protected CharSequence provideTitle() {
        return "更多";
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_user_menu;
    }

    @Override
    public void initListeners() {

    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        id = getIntent().getBundleExtra("data").getInt("id");
        photo = getIntent().getBundleExtra("data").getString("photo");
        nicname = getIntent().getBundleExtra("data").getString("nicname");
        int Credit = getIntent().getBundleExtra("data").getInt("Credit");
        ls_btn.setVisibility(Credit==1?View.GONE:View.VISIBLE);
    }

    @Override
    public FriendsMenuPresenter createPresenter() {
        return new FriendsMenuPresenter();
    }

    @Override
    public void successDelete() {
        TextMessage textMessage = TextMessage.obtain("删除好友");
        textMessage.setExtra("shanchuhaoyou");
        Message myMessage = Message.obtain(id+"", Conversation.ConversationType.PRIVATE, textMessage);
        RongIM.getInstance().sendMessage(myMessage, null, null, new IRongCallback.ISendMessageCallback() {
            @Override
            public void onAttached(Message message) {}
            @Override
            public void onSuccess(Message message) {
                RongIM.getInstance().removeConversation(Conversation.ConversationType.PRIVATE, id+"", new RongIMClient.ResultCallback<Boolean>() {
                    @Override
                    public void onSuccess(Boolean aBoolean) {}
                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {}
                });
            }
            @Override
            public void onError(Message message, RongIMClient.ErrorCode errorCode) {}
        });
        ActivityUtil.addActivity(this);
        ActivityUtil.finishActivitys();
    }

    @Override
    public void error(String error) {
        toast(error);
    }
}
