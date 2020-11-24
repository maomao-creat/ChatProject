package com.zykj.samplechat.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;

import com.zykj.samplechat.R;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.presenter.TeamNamePresenter;
import com.zykj.samplechat.ui.activity.base.ToolBarActivity;
import com.zykj.samplechat.ui.view.TeamNameView;
import com.zykj.samplechat.utils.AESOperator;
import com.zykj.samplechat.utils.StringUtil;
import com.zykj.samplechat.utils.UserUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.CommandMessage;

/**
 * Created by csh
 * Created date 2016/12/8.
 * Description 群公告/投诉
 */
public class TeamNoticeActivity extends ToolBarActivity<TeamNamePresenter> implements TeamNameView {

    @Bind(R.id.et_notice)
    WebView etNotice;
    private String action;
    private int id;

    @Override
    protected CharSequence provideTitle() {
        return action;
    }

    @Override
    public TeamNamePresenter createPresenter() {
        return new TeamNamePresenter();
    }


    @Override
    protected void action() {
        super.action();
//        if (et_notice.getText().toString().trim().equals("")) {
//            snb(action + "不能为空", et_notice);
//            return;
//        }
//        Map map = new HashMap();
//        map.put("key", Const.KEY);
//        map.put("uid", Const.UID);
//        map.put("function", action.equals("群公告") ? "EditGonggao" : "ReportUser");
//        if (action.equals("群公告")) {
//            map.put("teamid", id);
//        } else {
//            map.put("userId", new UserUtil(getContext()).getUserId());
//            map.put("targetId", id);
//        }
//        map.put("content", et_notice.getText().toString());
//        String json = StringUtil.toJson(map);
//        try {
//            String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
//            if (action.equals("群公告")) {
//                presenter.EditTeam(data);
//            } else {
//                presenter.ReportUser(data);
//            }
//        } catch (Exception ex) {
//        }
    }

    @Override
    public boolean canAction() {
        boolean flag = getIntent().getBundleExtra("data").getBoolean("flag");
        action = getIntent().getBundleExtra("data").getString("action");
//        et_notice.setEnabled(flag);
//        et_notice.setHint(flag ? action.equals("群公告") ? "请输入群公告" : "请输入群投诉原因" : "");
        return flag;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        tvAction.setText("保存");
        id = getIntent().getBundleExtra("data").getInt("id");
        String notice = getIntent().getBundleExtra("data").getString("notice");
//        et_notice.setText(notice);
        WebSettings webSettings = etNotice.getSettings();
        webSettings.setJavaScriptEnabled(true);//允许使用js
        etNotice.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY); //取消滚动条白边效果
        //不支持屏幕缩放
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(false);
       //不显示webview缩放按钮
        webSettings.setDisplayZoomControls(false);
        etNotice.loadDataWithBaseURL(Const.BASE, notice, "text/html", "utf-8", null);
        // 这部分应该写在onCreate里了

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_team_notice;
    }

    @Override
    public void initListeners() {
    }

    @Override
    public void successUpload() {
        if (action.equals("群公告")) {
            Intent msgIntent = new Intent("ANNOUNCE_RECEIVED_ACTION");
            sendBroadcast(msgIntent);

            CommandMessage message = CommandMessage.obtain("annouce", "{}");
            RongIM.getInstance().sendMessage(Message.obtain(id + "", Conversation.ConversationType.GROUP, message),
                    null, null, new IRongCallback.ISendMessageCallback() {
                        @Override
                        public void onAttached(Message message) {
                        }

                        @Override
                        public void onSuccess(Message message) {
                        }

                        @Override
                        public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                        }
                    });
        }
        toast("提交成功");
//        hideSoftMethod(et_notice);
        finish();
    }

    @Override
    public void errorUpload() {
        toast("修改失败，请检测您的网络或名字是否合法");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
