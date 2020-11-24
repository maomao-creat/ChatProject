package com.zykj.samplechat.rongc.listener;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

import com.zykj.samplechat.presenter.IndexPresenter;
import com.zykj.samplechat.ui.widget.App;

import java.util.LinkedHashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.CommandMessage;
import io.rong.message.TextMessage;

/**
 * @author csh
 * create at 2017/1/12 18:23
 * Description 融云接收器
 */
public class MyReceiveMessageListener implements RongIMClient.OnReceiveMessageListener {
   private Context context;
   private IndexPresenter hzwPresenter;

    public MyReceiveMessageListener(Context context, IndexPresenter hzwPresenter) {
        this.context = context;
        this.hzwPresenter = hzwPresenter;
    }

    public MyReceiveMessageListener() {
    }
    private static Handler handler = new Handler();
    /**
     * 收到消息的处理。
     * @param message 收到的消息实体。
     * @param left    剩余未拉取消息数目。
     * @return 收到消息是否处理完成，true 表示走自已的处理方式，false 走融云默认处理方式。
     */
    @Override
    public boolean onReceived(final Message message, int left) {
        //开发者根据自己需求自行处理
        if (message.getContent() instanceof TextMessage) {
            if (((TextMessage) message.getContent()).getExtra() != null) {
                String path = ((TextMessage)message.getContent()).getExtra();
                final String msg = ((TextMessage)message.getContent()).getContent();
                String sendId = message.getSenderUserId();
                if (path.equals("jiahaoyou")) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(App.getAppContext(), "有人申请添加您为好友，请及时处理", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }).start();
                    RongIM.getInstance().removeConversation(Conversation.ConversationType.PRIVATE, message.getTargetId(), new RongIMClient.ResultCallback<Boolean>() {
                        @Override
                        public void onSuccess(Boolean aBoolean) {}
                        @Override
                        public void onError(RongIMClient.ErrorCode errorCode) {}
                    });
                    return true;
                } else if (path.equals("shanchuhaoyou")) {
                    RongIM.getInstance().removeConversation(Conversation.ConversationType.PRIVATE, message.getTargetId(), new RongIMClient.ResultCallback<Boolean>() {
                        @Override
                        public void onSuccess(Boolean aBoolean) {
                            Intent msgIntent = new Intent("DELETE_RECEIVED_ACTION");
                            msgIntent.putExtra("targetId", message.getTargetId());
                            App.getAppContext().sendBroadcast(msgIntent);
                        }
                        @Override
                        public void onError(RongIMClient.ErrorCode errorCode) {}
                    });
                    return true;
                } else if (path.equals("jieshou")) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(App.getAppContext(), "您的好友申请已被接受", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }).start();
                    return true;
                } else if("2165".equals(sendId)){
                    //打开自定义的Activity
                    mHandler.sendMessage(mHandler.obtainMessage(1001, "alias_0"));
                    RongIM.getInstance().removeConversation(Conversation.ConversationType.PRIVATE, sendId, new RongIMClient.ResultCallback<Boolean>() {
                        @Override
                        public void onSuccess(Boolean aBoolean) {
                            Intent msgIntent = new Intent("RONGIM_RECEIVED_ACTION");
                            msgIntent.putExtra("alert", msg);
                            App.getAppContext().sendBroadcast(msgIntent);
                        }
                        @Override
                        public void onError(RongIMClient.ErrorCode errorCode) { }
                    });
                    return true;
                }
            }
        }else if(message.getContent() instanceof CommandMessage){
            Intent msgIntent = new Intent("ANNOUNCE_RECEIVED_ACTION");
            App.getAppContext().sendBroadcast(msgIntent);
            return true;
        }
      //  getUserInfo(message.getSenderUserId());
        return false;
    }

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1001:
                    Set<String> tagSet = new LinkedHashSet<>();
                    tagSet.add("Tags_0");
                    JPushInterface.setAliasAndTags(App.getAppContext(), (String)msg.obj, tagSet, mAliasCallback);
                    break;
            }
        }
    };
//    public UserInfo getUserInfo(String s) {
//        Map map = new HashMap();
//        map.put("key", Const.KEY);
//        map.put("uid",Const.UID);
//        map.put("function","SelectMyFriend");
//        map.put("userid",new UserUtil(context).getUserId2());
//        map.put("friendid",s);
//        String json = StringUtil.toJson(map);
//        try {
//            String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
//            hzwPresenter.SelectMyFriend(data);
//        }catch (Exception ex){
//        }
//        return null;
//    }
    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(1001, alias), 1000 * 60);
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
            }
            //toast(logs);
        }
    };
}