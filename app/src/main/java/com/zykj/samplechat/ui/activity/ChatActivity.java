package com.zykj.samplechat.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo;
import com.zykj.samplechat.R;
import com.zykj.samplechat.ui.fragment.ChatFragment;

public class ChatActivity extends Activity {

    private ChatFragment mChatFragment;
    private ChatInfo mChatInfo;
    public static final String CHAT_INFO = "chatInfo";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            startSplashActivity();
        } else {
            mChatInfo = (ChatInfo) bundle.getSerializable(CHAT_INFO);
            if (mChatInfo == null) {
                startSplashActivity();
                return;
            }
            mChatFragment = new ChatFragment();
            mChatFragment.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.empty_view, mChatFragment).commitAllowingStateLoss();
        }
    }

    private void startSplashActivity() {
//        Intent intent = new Intent(ChatActivity.this, SplashActivity.class);
//        startActivity(intent);
//        finish();
    }
}
