package com.zykj.samplechat.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rey.material.app.Dialog;
import com.zykj.samplechat.R;
import com.zykj.samplechat.model.Friend;
import com.zykj.samplechat.model.Res;
import com.zykj.samplechat.model.Team;
import com.zykj.samplechat.model.qunxxx;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.network.HttpUtils;
import com.zykj.samplechat.network.Net;
import com.zykj.samplechat.network.SubscriberRes;
import com.zykj.samplechat.presenter.ConversationPresenter;
import com.zykj.samplechat.ui.activity.base.ToolBarActivity;
import com.zykj.samplechat.ui.view.TeamInfoView;
import com.zykj.samplechat.ui.widget.App;
import com.zykj.samplechat.ui.widget.DataWebView;
import com.zykj.samplechat.ui.widget.ScrollText;
import com.zykj.samplechat.utils.AESOperator;
import com.zykj.samplechat.utils.ActivityUtil;
import com.zykj.samplechat.utils.Bun;
import com.zykj.samplechat.utils.StringUtil;
import com.zykj.samplechat.utils.UserUtil;
import com.zykj.samplechat.utils.WindowUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;
import io.rong.message.InformationNotificationMessage;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * *****************************************************
 * *
 * *
 * _oo0oo_                      *
 * o8888888o                     *
 * 88" . "88                     *
 * (| -_- |)                     *
 * 0\  =  /0                     *
 * ___/`---'\___                   *
 * .' \\|     |# '.                  *
 * / \\|||  :  |||# \                 *
 * / _||||| -:- |||||- \               *
 * |   | \\\  -  #/ |   |               *
 * | \_|  ''\---/''  |_/ |              *
 * \  .-\__  '-'  ___/-. /              *
 * ___'. .'  /--.--\  `. .'___            *
 * ."" '<  `.___\_<|>_/___.' >' "".          *
 * | | :  `- \`.;`\ _ /`;.`/ - ` : | |        *
 * \  \ `_.   \_ __\ /__ _/   .-` /  /        *
 * =====`-.____`.___ \_____/___.-`___.-'=====     *
 * `=---='                      *
 * *
 * *
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~    *
 * *
 * 佛祖保佑         永无BUG               *
 * *
 * *
 * *****************************************************
 * <p>
 * Created by ninos on 2016/6/2.
 */
public class ConversationActivity extends ToolBarActivity<ConversationPresenter>
        implements View.OnClickListener, TeamInfoView {

    @Bind(R.id.ll_message)
    LinearLayout ll_message;
    @Bind(R.id.news_statustxt)
    ScrollText scrollText;
    @Bind(R.id.tv_menu)
    ImageView tv_menu;
    @Bind(R.id.wv_data)
    DataWebView wv_data;
    @Bind(R.id.wv_list)
    DataWebView wv_list;
    @Bind(R.id.wv_yue)
    WebView wv_yue;
    private WebViewClient client = new WebViewClient() {
        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    };
    private Dialog dialog_notice;
    private TextView view_notice;
    private String notice = "";
    private boolean flag = false;
    private boolean webFlag = false;
    private String t = "0";

    @OnClick(R.id.tv_menu)
    public void tv_menu() {//右上角点击事件
        ActivityUtil.addActivity(this);
        if (type.equals("group")) {
            startActivity(TeamInfoActivity.class, new Bun().putString("id", mTargetId).ok());//群组详情 暂时屏蔽
        } else if (type.equals("private")) {
//            startActivity(FriendsInfoActivity.class, new Bun().putString("id", mTargetId).putString("bs","1").ok());
            startActivity(new Intent(this,FriendsInfoActivity.class).putExtra("id", mTargetId));
        }
    }

    /**
     * 目标 Id
     */
    private String mTargetId;

    /**
     * 刚刚创建完讨论组后获得讨论组的id 为targetIds，需要根据 为targetIds 获取 targetId
     */
    private String mTargetIds;

    private String title;
    private String type;
    private String permission = "0";
    private String isWuYou = "1";
    private MessageReceiver mMessageReceiver;
    private List<UserInfo> userInfos = new ArrayList<>();

    /**
     * 会话类型
     */
    private Conversation.ConversationType mConversationType;

    @Override
    protected CharSequence provideTitle() {
        return "聊天窗口";
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_conversation;
    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        getIntentDate(getIntent());
        ll_message.setVisibility(View.GONE);
        wv_data.getSettings().setJavaScriptEnabled(true);
        wv_data.getSettings().setAppCacheEnabled(true);
        //设置 缓存模式
        wv_data.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        // 开启 DOM storage API 功能
        wv_data.getSettings().setDomStorageEnabled(true);
        //wv_data.loadUrl("http://39.106.74.58:8012/ListOpenResult.asp?teamid=" + mTargetId + "&isDiplay=0");
        wv_data.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // view.loadUrl(url);
                return true;
            }
        });

        wv_data.setOnWebClickListener(new DataWebView.OnWebClickListener() {
            @Override
            public void onWebClick(DataWebView v) {
                wv_data.setVisibility(View.GONE);
                wv_list.setVisibility(View.VISIBLE);
            }
        });
        wv_list.getSettings().setJavaScriptEnabled(true);
        wv_list.getSettings().setAppCacheEnabled(true);
        //设置 缓存模式
        wv_list.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        // 开启 DOM storage API 功能
        wv_list.getSettings().setDomStorageEnabled(true);
        // wv_list.loadUrl("http://39.106.74.58:8012/ListOpenResult.asp?teamid=" + mTargetId + "&isDiplay=1");
        wv_list.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // view.loadUrl(url);
                return true;
            }
        });
        wv_list.setOnWebClickListener(new DataWebView.OnWebClickListener() {
            @Override
            public void onWebClick(DataWebView v) {
                wv_data.setVisibility(View.VISIBLE);
                wv_list.setVisibility(View.GONE);
            }
        });
        if (type.equals("group")) {
            try {
                if (!mTargetIds.equals("555"))
                    wv_data.setVisibility(View.VISIBLE);
                else
                    wv_data.setVisibility(View.GONE);
            } catch (Exception ex) {
                if (!mTargetId.equals("555"))
                    wv_data.setVisibility(View.VISIBLE);
                else
                    wv_data.setVisibility(View.GONE);
            }
        } else if (type.equals("private")) {
            wv_data.setVisibility(View.GONE);
        }
//        if (type.equals("group")) {
////            Map<String, Object> map = new HashMap<>();
////            map.put("key", Const.KEY);
////            map.put("uid", Const.UID);
////            map.put("function", "SelectTeamMember");
////            map.put("userid", new UserUtil(getContext()).getUserId());
////            map.put("teamid", mTargetId);
////            String json = StringUtil.toJson(map);
////            try {
////                String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
////                presenter.SelectTeamMember(data);
////            } catch (Exception ex) {
////            }
//
//            HttpUtils.SelectTeamMemberForAnZhuo(new SubscriberRes<qunxxx>() {
//                @Override
//                public void onSuccess(final qunxxx res) {
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            for (int i=0;i<res.getQuery().size();i++){
//                                RongIM.getInstance().refreshUserInfoCache(new UserInfo(res.getQuery().get(i).getUserid() + "", res.getQuery().get(i).getNickName(), Uri.parse(Const.getbase( res.getQuery().get(i).getHeadUrl()))));
////                            RongIM.getInstance().refreshGroupUserInfoCache(new GroupUserInfo(groupId,res.getQuery().get(i).getUserid()+""
////                                    ,res.getQuery().get(i).getNickName()));
//                            }
//                        }
//                    }).start();
//                }
//                @Override
//                public void completeDialog() {
//                }
//            },mTargetId);
//
//            RongIM.getInstance().setGroupMembersProvider(new RongIM.IGroupMembersProvider() {
//                @Override
//                public void getGroupMembers(String groupId, RongIM.IGroupMemberCallback callback) {
//                    callback.onGetGroupMembersResult(userInfos);
//                }
//            });
//        }


    }

    private void setStateText() {
        ll_message.setOnClickListener(this);
        scrollText.setOnClickListener(this);
        String s = "正在加载...";
        scrollText.setStateList(s);
        scrollText.setText(s);
        scrollText.init(handler);
        scrollText.startScroll();
        scrollText.start();
        SelectTeam();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            if (msg.what == ScrollText.TEXT_TIMER && scrollText != null) {
                scrollText.scrollText();
            }
        }
    };

    @Override
    protected void onDestroy() {
        scrollText.stop();
        if (mMessageReceiver != null)
            unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    /**
     * 展示如何从 Intent 中得到 融云会话页面传递的 Uri
     */
    private void getIntentDate(Intent intent) {
        mTargetId = intent.getData().getQueryParameter("targetId");
        mTargetIds = intent.getData().getQueryParameter("targetIds");
        title = getIntent().getData().getQueryParameter("title");
        // LogUtils.i("xxxxx", "" +mTargetId);  //输出测试
        // LogUtils.i("xxxxx1", "" +mTargetIds);  //输出测试
        // LogUtils.i("xxxxx2", "" +title);  //输出测试

        try {
            if (mTargetIds != null || !mTargetIds.equals(""))
                App.targetId = mTargetIds;
            else
                App.targetId = mTargetId;
        } catch (Exception ex) {
            App.targetId = mTargetId;
        }
//        intent.getData().getLastPathSegment();//获得当前会话类型
        mConversationType = Conversation.ConversationType.valueOf(intent.getData().getLastPathSegment().toUpperCase(Locale.getDefault()));
        type = mConversationType.getName();
        enterFragment(mConversationType, mTargetId);
        if (mConversationType.getName().equals("group")) {
            tv_menu.setImageResource(R.drawable.icon_groupinfo);
            tvTitle.setText(title);
            HttpUtils.SelectTeamMemberForAnZhuo(new SubscriberRes<qunxxx>() {
                @Override
                public void onSuccess(final qunxxx res) {
                    final List<UserInfo> list = new ArrayList<>();
                    for(int i=0;i<res.getQuery().size();i++){
//                    RongIM.getInstance().refreshUserInfoCache(new UserInfo(res.getQuery().get(i).getUserid() + "", res.getQuery().get(i).getNickName(), Uri.parse(Const.getbase( res.getQuery().get(i).getHeadUrl()))));
                        UserInfo userInfo = new UserInfo(res.getQuery().get(i).getUserid()+"",
                                res.getQuery().get(i).getNickName(),
                                Uri.parse(StringUtil.getImagePath(res.getQuery().get(i).getHeadUrl())));
                        list.add(userInfo);
                    }
                    RongIM.getInstance().setGroupMembersProvider(new RongIM.IGroupMembersProvider() {
                        @Override
                        public void getGroupMembers(String s, RongIM.IGroupMemberCallback iGroupMemberCallback) {
                            iGroupMemberCallback.onGetGroupMembersResult(list);
                        }
                    });
                }
                @Override
                public void completeDialog() {
                }
            },mTargetId);

//            HttpUtils.SelectTeamMemberForAnZhuo(new SubscriberRes<qunxxx>() {
//                @Override
//                public void onSuccess(final qunxxx res) {
//                    for(int i=0;i<res.getQuery().size();i++){
//                        UserInfo userInfo = new UserInfo(res.getQuery().get(i).getUserid()+"",
//                                res.getQuery().get(i).getNickName(),
//                                Uri.parse(StringUtil.getImagePath(res.getQuery().get(i).getHeadUrl())));
//                        RongIM.getInstance().refreshUserInfoCache(userInfo);
//                    }
//
//                }
//                @Override
//                public void completeDialog() {
//                }
//            },mTargetId);
            String uid = String.valueOf(new UserUtil(getContext()).getUserId());
            String name = String.valueOf(new UserUtil(getContext()).getUser().getNickName());
            InformationNotificationMessage message = InformationNotificationMessage.obtain("欢迎" + name + "进入群聊");
 //            RongIM.getInstance().insertMessage(Conversation.ConversationType.GROUP, mTargetId, uid, message, null);
        } else if (mConversationType.getName().equals("private")) {
            tv_menu.setImageResource(R.drawable.icon_chat_user);
            tvTitle.setText(title);
        }
    }

    /**
     * 加载会话页面 ConversationFragment
     *
     * @param mConversationType 会话类型
     * @param mTargetId         目标 Id
     */
    private void enterFragment(Conversation.ConversationType mConversationType, String mTargetId) {

        //ConversationFragment fragment = new ConversationFragment();
        ConversationFragment fragment = (ConversationFragment) getSupportFragmentManager().findFragmentById(R.id.conversation);

        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("conversation").appendPath(mConversationType.getName().toLowerCase())
                .appendQueryParameter("targetId", mTargetId).build();

        fragment.setUri(uri);

        //FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //xxx 为你要加载的 id
        //transaction.add(R.id.conversation, fragment);
        //transaction.commitAllowingStateLoss();
    }

    @Override
    public void initListeners() {
        Map<String, Object> map = new HashMap<>();
        map.put("key", Const.KEY);
        map.put("uid", Const.UID);
        map.put("function", "permission");
        map.put("userid", new UserUtil(getContext()).getUserId());
        String json = StringUtil.toJson(map);
        try {
            String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
            presenter.permission(data);
        } catch (Exception ex) {
        }
        if (type.equals("group")) {
            View noticeView = getLayoutInflater().inflate(R.layout.dialog_notice, null);
            this.view_notice = (TextView) noticeView.findViewById(R.id.tv_notice);
            view_notice.getLayoutParams().width = WindowUtil.getScreenWidth(this) / 5 * 4;
            dialog_notice = new Dialog(getContext()).backgroundColor(Color.parseColor("#ffffff")).contentView(noticeView);

            Window window = dialog_notice.getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.height = WindowUtil.getScreenWidth(this); // 设置宽度
            window.setAttributes(lp);
            registerMessageReceiver();
            setStateText();
        } else {
            // isWuyou();
        }
    }

    private void isWuyou() {
        Map<String, Object> map = new HashMap<>();
        map.put("key", Const.KEY);
        map.put("uid", Const.UID);
        map.put("function", "IsWuyou");
        map.put("userid", new UserUtil(getContext()).getUserId());
        map.put("friendid", mTargetId);
        String json = StringUtil.toJson(map);
        try {
            String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
            Subscription subscription = Net.getService()
                    .IsWuyou(data)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Res<String>>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                        }

                        @Override
                        public void onNext(Res<String> res) {
                            if (res.code == Const.OK) {
                                isWuYou = res.data;
                            } else {
                                isWuYou = "0";
                            }
                        }
                    });
            presenter.addSubscription(subscription);
        } catch (Exception ex) {
        }
    }

    @Override
    public ConversationPresenter createPresenter() {
        return new ConversationPresenter();
    }

    //注册广播
    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction("ANNOUNCE_RECEIVED_ACTION");
        filter.addAction("DELETE_RECEIVED_ACTION");
        getContext().registerReceiver(mMessageReceiver, filter);
    }

    @Override
    public void onClick(View v) {
        view_notice.setText(notice);
        dialog_notice.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.img_back)
    public void onViewClicked() {
        finish();
    }

    //广播接收者
    public class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("ANNOUNCE_RECEIVED_ACTION".equals(intent.getAction())) {
                SelectTeam();
            } else if ("DELETE_RECEIVED_ACTION".equals(intent.getAction())) {
                isWuYou = "0";
            }
        }
    }

    private void SelectTeam() {
        Map map = new HashMap();
        map.put("key", Const.KEY);
        map.put("uid", Const.UID);
        map.put("function", "SelectTeam");
        map.put("teamid", mTargetId);
        String json = StringUtil.toJson(map);
        try {
            String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
            presenter.SelectTeam(data);
        } catch (Exception ex) {
        }
    }

    @Override
    public void successTeam(Team team) {
        tvTitle.setText(title + "(" + team.Count + ")");
        App.countpeople = team.Count;
        if (!StringUtil.isEmpty(team.TeamAnnouncement)) {
            notice = "#" + team.TeamAnnouncement + "    ";
            scrollText.setStateList(notice);
            scrollText.setText(notice);
            scrollText.init(handler);
            //ll_message.setVisibility(View.VISIBLE);
        }
        if (team.Type == 1) {
//            wv_yue.setVisibility(View.VISIBLE);

//            int uId = new UserUtil(getContext()).getUserId();
//            wv_yue.loadUrl(Const.BASE+String.format(Locale.getDefault(),Const.LISTYUE,uId,mTargetId));
//            wv_yue.setWebViewClient(client);
        }
    }

    @Override
    public void successFriends(ArrayList<Friend> list) {
//        for (Friend friend : list) {
//            UserInfo userInfo = new UserInfo(String.valueOf(friend.MemnerId),
//                    StringUtil.isEmpty(friend.RemarkName)? friend.NicName : friend.RemarkName,
//                    Uri.parse(Const.getbase(friend.PhotoPath)));
//            userInfos.add(userInfo);
////            if (friend.MemnerId == new UserUtil(getContext()).getUserId()) {//附加注册 本来是可以用的
////                flag = true;
////            }
//        }
//        RongIM.getInstance().setGroupMembersProvider(new RongIM.IGroupMembersProvider() {
//            @Override
//            public void getGroupMembers(String s, RongIM.IGroupMemberCallback iGroupMemberCallback) {
//                iGroupMemberCallback.onGetGroupMembersResult(userInfos);
//            }
//        });
        tv_menu.setVisibility(flag ? View.VISIBLE : View.GONE);
    }

    @Override
    public void other(int type, String data) {
        permission = data;
    }

    @Override
    public void success() {
    }

    @Override
    public void successSendYue() {

    }

    @Override
    public void successJiesan() {
    }

    @Override
    public void error() {
    }

    @Override
    protected void onResume() {
        super.onResume();
//        RongIM.getInstance().setSendMessageListener(new RongIM.OnSendMessageListener() {
//            @Override
//            public Message onSend(Message message) {
//                MessageContent messageContent = message.getContent();
//                if (messageContent instanceof VoiceMessage) {//语音消息消息
//                    if (type.equals("group")){
//                       // presenter.SaveMessage(mTargetId, ((TextMessage) messageContent).getContent());
//                         //toast("无法聊天");
//                        return null;
//                    }
//                }
//                 if (messageContent instanceof TextMessage) {//文本消息
//                    if ("0".equals(permission) && ((TextMessage) messageContent).getContent().length() > 100) {
//                        toast("字数超出限制");
//                        return null;
//                    } else if ("0".equals(isWuYou)) {
//                        toast("你们还不是好友，请先添加好友");
//                        return null;
//                    }
////                    } else if (type.equals("group") && !flag) {
////                        toast("您未在本群建立用户，无法发送消息，请联系管理员");
////                        return null;
////                    }
//                    if (type.equals("group")){
//                      //  presenter.SaveMessage(mTargetId, ((TextMessage) messageContent).getContent());
//                       // toast("无法聊天");
//                        return null;
//                    }
//                }
//                return message;
//            }
//
//            @Override
//            public boolean onSent(Message message, RongIM.SentMessageErrorCode sentMessageErrorCode) {
//                return false;
//            }
//        });
    }
}