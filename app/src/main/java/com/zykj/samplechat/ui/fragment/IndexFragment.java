package com.zykj.samplechat.ui.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.rey.material.app.Dialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zykj.samplechat.R;
import com.zykj.samplechat.model.CheckNewBean;
import com.zykj.samplechat.model.QuanJu;
import com.zykj.samplechat.model.Team;
import com.zykj.samplechat.model.qunxxx;
import com.zykj.samplechat.model.sshyBean;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.network.HttpUtils;
import com.zykj.samplechat.network.SubscriberRes;
import com.zykj.samplechat.presenter.IndexPresenter;
import com.zykj.samplechat.rongc.listener.MyReceiveMessageListener;
import com.zykj.samplechat.ui.activity.BasicMapActivity;
import com.zykj.samplechat.ui.activity.FriendsAddActivity;
import com.zykj.samplechat.ui.activity.FriendsInfoActivity;
import com.zykj.samplechat.ui.activity.LocationActivity;
import com.zykj.samplechat.ui.activity.LoginActivity;
import com.zykj.samplechat.ui.activity.MainActivity;
import com.zykj.samplechat.ui.activity.PlayVideoActiviy;
import com.zykj.samplechat.ui.activity.TeamCreateActivity;
import com.zykj.samplechat.ui.activity.ZZhuanQianActivity;
import com.zykj.samplechat.ui.activity.base.BaseFragment;
import com.zykj.samplechat.ui.view.IndexView;
import com.zykj.samplechat.ui.widget.App;
import com.zykj.samplechat.utils.Bun;
import com.zykj.samplechat.utils.StringUtil;
import com.zykj.samplechat.utils.ToolsUtils;
import com.zykj.samplechat.utils.UserUtil;
import com.zykj.samplechat.zxing.CaptureActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imkit.model.UIConversation;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Group;
import io.rong.imlib.model.UserInfo;
import io.rong.message.ImageMessage;
import io.rong.message.LocationMessage;
import io.rong.message.RichContentMessage;
import okhttp3.Request;

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
public class IndexFragment extends BaseFragment<IndexPresenter> implements
        RongIM.GroupInfoProvider,
        RongIM.UserInfoProvider,
        RongIM.IGroupMembersProvider,
        RongIM.ConversationBehaviorListener,
        RongIM.LocationProvider,
        RongIMClient.ConnectionStatusListener   ,  View.OnClickListener, IndexView {
    @Bind(R.id.tzxxnr)
    TextView tzxxnr;

    public static LocationCallback _LocationCallback;
    //private MessageReceiver mMessageReceiver;
    private PopupWindow popupWindow;
    private Dialog dialog_phone;
    private String targetId, targetTitle;
    public double m_newVerName;
    public String url;
    public ProgressDialog progressDialog;

    public Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    progressDialog.setProgress(msg.getData().getInt("msg"));
//                    builder.setProgress(100, msg.getData().getInt("msg"), false);
//                    builder.setContentText("下载进度:" + msg.getData().getInt("msg") + "%");
////                                progressDialog.setProgress((int) (progress * 100));
//                    notification = builder.build();
//                    notificationManager.notify(1, notification);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };
//    InputProvider.ExtendProvider[] provider = {
//            new ImageInputProvider(RongContext.getInstance()),//图片
//            new CameraInputProvider(RongContext.getInstance()),//相机
//            new LocationInputProvider(RongContext.getInstance()),//地理位置
//            new VideoInputProvider(RongContext.getInstance()),
//    };

    @OnClick(R.id.img_action)
    public void clickAction(View v) {
        popupWindow.showAsDropDown(v);
    }

    @Override
    public void initListeners() {
    }

    @Override
    protected void initThings(View view) {
        connect(UserUtil.getUser().getRYToken());
        //registerMessageReceiver();
        initPopupWindow();
        tzxxnr.setText(QuanJu.getQuanJu().getTzxx());
        RongIM.setConversationListBehaviorListener(new RongIM.ConversationListBehaviorListener() {
            @Override
            public boolean onConversationPortraitClick(Context context, Conversation.ConversationType conversationType, String s) {
                return false;
            }

            @Override
            public boolean onConversationPortraitLongClick(Context context, Conversation.ConversationType conversationType, String s) {
                return false;
            }

            @Override
            public boolean onConversationLongClick(Context context, View view, UIConversation uiConversation) {
                return false;
            }

            @Override
            public boolean onConversationClick(Context context, View view, UIConversation uiConversation) {
                targetId = uiConversation.getConversationTargetId();
                targetTitle = uiConversation.getUIConversationTitle();
                if (uiConversation.getConversationType() == Conversation.ConversationType.GROUP) {
                    //  getGroupInfo(targetId);
                    RongIM.getInstance().startGroupChat(getContext(), targetId, targetTitle);
                    return true;
                } else {
                    return false;
                }
            }
        });
        //this.view_notice = (TextView)getActivity().getLayoutInflater().inflate(R.layout.dialog_notice, null);
    }

    public void getCheckNew(){
        HttpUtils.checkNew(new SubscriberRes<CheckNewBean>() {
            @Override
            public void onSuccess(CheckNewBean checkNewBean) {
                m_newVerName = Double.parseDouble(checkNewBean.anzhuoversion.substring(0,3));
                url = checkNewBean.anzhuourl;
                String verName = ToolsUtils.getVerName(getContext()).substring(0,3); // 用到前面第一节写的方法
                if (Double.parseDouble(verName) < m_newVerName) {
//                    doNewVersionUpdate(checkNewBean.anzhuoversion); // 更新新版本
                }
            }

            @Override
            public void completeDialog() {
            }
        });
    }

    private void doNewVersionUpdate(String name) {
        String verName = ToolsUtils.getVerName(getContext());
        String str = "当前版本：" + verName + " ,发现新版本：" + name +
                " ,是否更新？";
        android.app.Dialog dialog = new android.app.AlertDialog.Builder(getContext()).setTitle("软件更新").setMessage(str)
                // 设置内容
                .setPositiveButton("更新",// 设置确定按钮
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
//                                initNotification();
                                initProgress();
                                try {
                                    showDownloadAPK(getContext(),url);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                .setNegativeButton("暂不更新",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // 点击"取消"按钮之后退出程序
                            }
                        }).create();// 创建
        dialog.show();
    }

    public void showDownloadAPK(final Context context, final String url) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpUtils.get().url(url).build()
                        .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), "newpengyou.apk") {
                            @Override
                            public void inProgress(float progress) {
                                Message msg=new Message();
                                msg.what=0;
                                Bundle bundle=new Bundle();
                                bundle.putInt("msg",(int) (progress * 100));
                                msg.setData(bundle);
                                handler.sendMessage(msg);
                            }

                            @Override
                            public void onError(Request request, Exception e) {

                            }

                            @Override
                            public void onResponse(File response) {
                                showSelectAPK(context);
                            }
                        });
            }
        });
        thread.start();
    }

    /***
     * 调起安装APP窗口  安装APP
     * @param context
     */
    private void showSelectAPK(Context context) {
        File fileLocation = new File(Environment.getExternalStorageDirectory(), "makefriends.apk");//APK名称
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(fileLocation), "application/vnd.android.package-archive");
        context.startActivity(intent);
        progressDialog.dismiss();
//        notificationManager.cancel(1);//取消通知
    }

    public void initProgress(){
        progressDialog =  new ProgressDialog(getContext());//实例化ProgressDialog
        progressDialog.setMax(100);//设置最大值
        progressDialog.setTitle("文件下载");//设置标题
        progressDialog.setIcon(R.mipmap.logo);//设置标题小图标
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);//设置样式为横向显示进度的样式
        progressDialog.setMessage("正在下载，请稍后！");
        progressDialog.incrementProgressBy(0);//设置初始值为0，其实可以不用设置，默认就是0
        progressDialog.setIndeterminate(false);//是否精确显示对话框，flase为是，反之为否
        //是否可以通过返回按钮退出对话框
        progressDialog.setCancelable(false);
        progressDialog.show();//显示对话框
    }
    //初始化popupwindow
    private void initPopupWindow() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.activity_fase_menu, null);
        LinearLayout ll_Profile = (LinearLayout) v.findViewById(R.id.ll_Profile);//扫一扫
        LinearLayout ll_Phones = (LinearLayout) v.findViewById(R.id.ll_Phones);//收款
        LinearLayout ll_Coin = (LinearLayout) v.findViewById(R.id.ll_Coin);//添加好友
        LinearLayout ll_fqql = (LinearLayout) v.findViewById(R.id.ll_fqql);//发起群聊
        ll_Profile.setOnClickListener(this);
        ll_Phones.setOnClickListener(this);
        ll_Coin.setOnClickListener(this);
        ll_fqql.setOnClickListener(this);

        popupWindow = new PopupWindow(v, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x0000));
    }

    @Override
    public void onClick(View v) {
        popupWindow.dismiss();
        if (v.getId() == R.id.ll_Profile) {//扫一扫
            startActivityForResult(CaptureActivity.class, 201);
        } else if (v.getId() == R.id.ll_Phones) {//收款 无
            startActivity(ZZhuanQianActivity.class);
        } else if (v.getId() == R.id.ll_Coin) {//添加好友
            startActivity(FriendsAddActivity.class);

        } else if (v.getId() == R.id.ll_fqql) {//发起群聊
            startActivity(TeamCreateActivity.class,new Bun().putString("id","").ok());
        }
//
//    }else{
//        if(dialog_notice == null)
//            dialog_notice = new Dialog(getContext()).backgroundColor(Color.parseColor("#ffffff")).contentView(view_notice);
//        view_notice.setText(notice);
//        dialog_notice.show();
//        startActivity(QRCodeActivity.class);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 201) {//二维码回调
            Toast.makeText(getContext(), "识别" + data.getStringExtra("jg"), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int provideLayoutId() {
        return R.layout.fragment_index;
    }

    @Override
    public IndexPresenter createPresenter() {
        return new IndexPresenter();
    }

    private void enterFragment() {
        ConversationListFragment fragment = new ConversationListFragment();
        Uri uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//设置群组会话聚合显示
                .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")//设置讨论组会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")//设置系统会话非聚合显示
                .build();
        fragment.setUri(uri);

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        //rong_content 为你要加载的 id
        transaction.add(R.id.conversationlist, fragment);
        transaction.commitAllowingStateLoss();
    }

//    public void showMessage(){
//        toast("有人申请加您为好友");
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == 23) {
//            if (grantResults.length > 0){
//                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {//授权成功
//                    VideoIPluginModule plug = (VideoIPluginModule)RongExtensionManager.getInstance()
//                            .getExtensionModules().get(0)
//                            .getPluginModules(Conversation.ConversationType.GROUP).get(2);
//                    plug.requestPermissions();
//                }else if (grantResults[0] == PackageManager.PERMISSION_DENIED){//点击拒绝授权
//                    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {//点击拒绝，再次弹出
//                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 23);
//                    }else{ // 选择不再询问，并点击拒绝，弹出提示框
//                        dialog = new Dialog(getContext()).title("无法开启摄像头，请检查朋友是否有访问摄像头的权限，或重启设备后重试")
//                                .positiveAction("确定").positiveActionClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        dialog.dismiss();
//                                    }
//                                }).backgroundColor(Color.parseColor("#FFFFFF")).titleColor(Color.parseColor("#292A2F")).cancelable(false);
//                        dialog.show();
//                    }
//                }
//            }
//        }
//    }


//    @Override
//    public void onDestroy() {
//        if(mMessageReceiver != null)
//            getContext().unregisterReceiver(mMessageReceiver);
//        super.onDestroy();
//    }

    //注册广播
//    public void registerMessageReceiver(){
//        mMessageReceiver = new MessageReceiver();
//        IntentFilter filter = new IntentFilter();
//        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
//        filter.addAction("MESSAGE_RECEIVED_ACTION");
//        filter.addAction("RONGIM_RECEIVED_ACTION");
//        filter.addAction("DELETE_RECEIVED_ACTION");
//        getContext().registerReceiver(mMessageReceiver, filter);
//    }

    //广播接收者
//    public class MessageReceiver extends BroadcastReceiver {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if ("MESSAGE_RECEIVED_ACTION".equals(intent.getAction())) {
//                GetSystemNews();
//            }else if("RONGIM_RECEIVED_ACTION".equals(intent.getAction())){
//                RongIM.getInstance().logout();
//                Intent i = new Intent(context, LoginExistActivity.class);
//                String alert = intent.getStringExtra("alert");
//                i.putExtra("data", new Bun().putInt("state",5).putString("alert", alert).ok());
//                startActivity(i);
//                finishActivity();
//            }else if("DELETE_RECEIVED_ACTION".equals(intent.getAction())){
//                String targetId = intent.getStringExtra("targetId");
//                RongIM.getInstance().removeConversation(Conversation.ConversationType.PRIVATE, targetId, new RongIMClient.ResultCallback<Boolean>() {
//                    @Override
//                    public void onSuccess(Boolean aBoolean) {}
//                    @Override
//                    public void onError(RongIMClient.ErrorCode errorCode) {}
//                });
//            }
//        }
//    }

    @Override
    public void success(ArrayList<Team> list) {
    }

    @Override
    public void response(final String res) {
        if (res != null) {
            if (res.startsWith("#")) {
                //notice = res;
                //scrollText.setStateList(notice);
                //scrollText.setText(notice);
                //scrollText.init(handler);
                //ll_notice.setVisibility(View.VISIBLE);
                //toast("收到推送");
            } else if ("0".equals(res)) {
                RongIM.getInstance().startGroupChat(getContext(), targetId, targetTitle);
            } else if ("1".equals(res)) {
                toast("请从游戏大厅进去该群");
                ((MainActivity) getActivity()).rebackHome();
            } else {
                if (dialog_phone == null) {
                    dialog_phone = new Dialog(getContext()).title("是否拨打客服电话").positiveAction("立即拨打").positiveActionClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + res)));
                        }
                    }).negativeAction("取消").negativeActionClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog_phone.dismiss();
                        }
                    }).backgroundColor(Color.parseColor("#ffffff")).titleColor(Color.parseColor("#292A2F"));
                }
                dialog_phone.show();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        getCheckNew();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public UserInfo getUserInfo(final String s) {
        HttpUtils.SearchFriendById(new SubscriberRes<sshyBean>() {
            @Override
            public void onSuccess(sshyBean friend) {
                RongIM.getInstance().refreshUserInfoCache(new UserInfo(s, friend.getNickName(), Uri.parse(Const.getbase( friend.getHeadUrl()))));
            }

            @Override
            public void completeDialog() {
            }
        }, s);
        return null;
    }

    /**
     * 建立与融云服务器的连接
     *
     * @param token 用户标示
     */
    private void connect(String token) {
        if (getActivity().getApplicationInfo().packageName.equals(App.getCurProcessName(getActivity().getApplicationContext()))) {
            /**
             * IMKit SDK调用第二步,建立与服务器的连接
             */
            RongIM.connect(token, new RongIMClient.ConnectCallback() {
                /**
                 * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
                 */
                @Override
                public void onTokenIncorrect() {
                    Log.d("ConnectRongIM", "-----------------------------------onTokenIncorrect");
                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token
                 */
                @Override
                public void onSuccess(String userid) {
                    Log.d("ConnectRongIM", "-----------------------------------onSuccess" + userid);
                    RongIM.setUserInfoProvider(IndexFragment.this, true);//设置用户信息提供者。
                    RongIM.setGroupInfoProvider(IndexFragment.this, true);//设置群组信息提供者。
                    RongIM.setConversationBehaviorListener(IndexFragment.this);//设置会话界面操作的监听器。
                    RongIM.setLocationProvider(IndexFragment.this);//设置地理位置提供者,不用位置的同学可以注掉此行代码
//                    RongIM.setGroupUserInfoProvider(IndexFragment.this,true);
// RongIM.getInstance().resetInputExtensionProvider(Conversation.ConversationType.PRIVATE, provider);
                    RongIM.getInstance().enableNewComingMessageIcon(true);
                    RongIM.setOnReceiveMessageListener(new MyReceiveMessageListener(getContext(), presenter));
                    enterFragment();
                    if (RongIM.getInstance() != null) {
                        //设置连接状态变化的监听器.
                        RongIM.setConnectionStatusListener(IndexFragment.this);
                    }

                    RongIM.getInstance().refreshUserInfoCache(new UserInfo(UserUtil.getUser().getId(),
                            StringUtil.isEmpty(UserUtil.getUser().getNickName())?UserUtil.getUser().getRealName():UserUtil.getUser().getNickName()
                            ,Uri.parse(Const.getbase(UserUtil.getUser().getHeadUrl()))));
//                    RongIM.getInstance().setMessageAttachedUserInfo(true);
//                    //清空聊天记录
//                    Conversation.ConversationType[] types = new Conversation.ConversationType[]{
//                            Conversation.ConversationType.PRIVATE,
//                            Conversation.ConversationType.GROUP,
//                            Conversation.ConversationType.DISCUSSION
//                    };
                    //  RongIM.getInstance().clearConversations(null, types);
                    //设置为已读
//                    RongIM.getInstance().clearMessagesUnreadStatus(Conversation.ConversationType.GROUP, "572");
//                    RongIM.getInstance().clearMessagesUnreadStatus(Conversation.ConversationType.GROUP, "576");
//                    RongIM.getInstance().clearMessagesUnreadStatus(Conversation.ConversationType.GROUP, "571");
//                    RongIM.getInstance().clearMessagesUnreadStatus(Conversation.ConversationType.GROUP, "570");
                    //加载服务器远端历史消息。
//                    RongIM.getInstance().getRemoteHistoryMessages(Conversation.ConversationType.GROUP, "576", 0, 0, new RongIMClient.ResultCallback<List<Message>>() {
//                        @Override
//                        public void onSuccess(List<Message> messages) {
//
//                        }
//
//                        @Override
//                        public void onError(RongIMClient.ErrorCode errorCode) {
//
//                        }
//                    });

                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    Log.d("ConnectRongIM", "-----------------------------------onError" + errorCode);
                }
            });
        }
    }

    @Override
    public boolean onUserPortraitClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo) {
        String id = userInfo.getUserId();
//        startActivity(FriendsInfoActivity.class, new Bun().putString("id", id).ok());
        context.startActivity(new Intent(context,FriendsInfoActivity.class).putExtra("id", id));
        return false;
    }

    @Override
    public boolean onUserPortraitLongClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo) {
        return false;
    }

    @Override
    public boolean onMessageClick(Context context, View view, io.rong.imlib.model.Message message) {
        if (message.getContent() instanceof LocationMessage) {
            Intent intent = new Intent(context, BasicMapActivity.class);
            intent.putExtra("data", new Bun()
                    .putString("lat", ((LocationMessage) message.getContent()).getLat() + "")
                    .putString("lng", ((LocationMessage) message.getContent()).getLng() + "")
                    .putString("address", ((LocationMessage) message.getContent()).getPoi())
                    .putString("name", "").putString("title", "位置信息").ok());
            context.startActivity(intent);
        } else if (message.getContent() instanceof RichContentMessage) {
            RichContentMessage mRichContentMessage = (RichContentMessage) message.getContent();
            Log.d("Begavior", "extra:" + mRichContentMessage.getExtra());
        } else if (message.getContent() instanceof ImageMessage) {
            if (((ImageMessage) message.getContent()).getExtra() != null) {
                String path = ((ImageMessage) message.getContent()).getExtra().toString().substring(0, 7);
                String id = ((ImageMessage) message.getContent()).getExtra().toString().substring(7);
                if (path.equals("654321;")) {
                    startActivity(FriendsInfoActivity.class, new Bun().putInt("id", Integer.parseInt(id)).ok());
                    return true;
                } else if (path.equals("123456;")) {
                    startActivity(PlayVideoActiviy.class, new Bun().putString("path", id).ok());
                    return true;
                }
            }
//            ImageMessage imageMessage = (ImageMessage) message.getContent();
//            Image image = new Image();
//            image.ImagePath = imageMessage.getLocalUri() == null ? imageMessage.getRemoteUri().toString(): imageMessage.getLocalUri().toString();
//            ArrayList<Image> list = new ArrayList<>();
//            list.add(image);
//
//            Intent intent = new Intent(context, PicsActivity.class);
//            intent.putExtra("data", new Bun().putInt("pos", 1).
//                    putString("pics", new Gson().toJson(list)).ok());
//            startActivity(intent);
        }
        return false;
    }

    @Override
    public boolean onMessageLinkClick(Context context, String s) {
        return false;
    }

    @Override
    public boolean onMessageLongClick(Context context, View view, io.rong.imlib.model.Message message) {
        return false;
    }

    @Override
    public Group getGroupInfo(final String groupId) {//群组信息  已经自动更新
//        Map<String, Object> map = new HashMap<>();
//        map.put("key", Const.KEY);
//        map.put("uid", Const.UID);
//        map.put("function", "SelectTeam");
//        map.put("teamid", s);
//        String json = StringUtil.toJson(map);
//        try {
//            String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
//            presenter.SelectTeam(data);
//        } catch (Exception ex) {
//        }
        HttpUtils.SelectTeamMember1(new SubscriberRes<qunxxx>() {
            @Override
            public void onSuccess(final qunxxx res) {
                RongIM.getInstance().refreshGroupInfoCache(new io.rong.imlib.model.Group(groupId,
                        res.getTeamInfoData().getName(),
                        Uri.parse(Const.getbase(res.getTeamInfoData().getImagePath()))));
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        for (int i=0;i<res.getQuery().size();i++){
//                            RongIM.getInstance().refreshUserInfoCache(new UserInfo(res.getQuery().get(i).getUserid() + "", res.getQuery().get(i).getNickName(), Uri.parse(Const.getbase( res.getQuery().get(i).getHeadUrl()))));
////                            RongIM.getInstance().refreshGroupUserInfoCache(new GroupUserInfo(groupId,res.getQuery().get(i).getUserid()+""
////                                    ,res.getQuery().get(i).getNickName()));
//                        }
//                    }
//                }).start();
            }
            @Override
            public void completeDialog() {
            }
        },groupId);
        return null;
    }

    @Override
    public void onStartLocation(Context context, LocationCallback locationCallback) {
        this._LocationCallback = locationCallback;
        Intent intent = new Intent(getContext(), LocationActivity.class);
        intent.putExtra("data", new Bun().putString("title", "选择位置").ok());
        getActivity().startActivity(intent);
    }

    @Override
    public void onChanged(ConnectionStatus connectionStatus) {
        switch (connectionStatus) {
            case CONNECTED://连接成功。
                break;
            case DISCONNECTED://断开连接。
                break;
            case CONNECTING://连接中。
                break;
            case NETWORK_UNAVAILABLE://网络不可用。
                break;
            case KICKED_OFFLINE_BY_OTHER_CLIENT://用户账户在其他设备登录，本机会被踢掉线
                startActivity(LoginActivity.class, new Bun().putInt("state", 4).ok());
                new UserUtil(getContext()).removeUserInfo();
                finishActivity();
                break;
        }
    }

//    @Override
//    public GroupUserInfo getGroupUserInfo(final String groupId, String userId) {
//        HttpUtils.SelectTeamMemberForAnZhuo(new SubscriberRes<qunxxx>() {
//            @Override
//            public void onSuccess(final qunxxx res) {
//                for (int i=0;i<res.getQuery().size();i++){
//                    RongIM.getInstance().refreshUserInfoCache(new UserInfo(res.getQuery().get(i).getUserid() + "", res.getQuery().get(i).getNickName(), Uri.parse(Const.getbase( res.getQuery().get(i).getHeadUrl()))));
//                }
//            }
//            @Override
//            public void completeDialog() {
//            }
//        },groupId);
//        return null;
//    }

    @Override
    public void getGroupMembers(String groupId, final RongIM.IGroupMemberCallback iGroupMemberCallback) {
        HttpUtils.SelectTeamMemberForAnZhuo(new SubscriberRes<qunxxx>() {
            @Override
            public void onSuccess(final qunxxx res) {
                List<UserInfo> list = new ArrayList<>();
                for(int i=0;i<res.getQuery().size();i++){
//                    RongIM.getInstance().refreshUserInfoCache(new UserInfo(res.getQuery().get(i).getUserid() + "", res.getQuery().get(i).getNickName(), Uri.parse(Const.getbase( res.getQuery().get(i).getHeadUrl()))));
                    UserInfo userInfo = new UserInfo(res.getQuery().get(i).getUserid()+"",
                            res.getQuery().get(i).getNickName(),
                            Uri.parse(StringUtil.getImagePath(res.getQuery().get(i).getHeadUrl())));
                    list.add(userInfo);
                }
                iGroupMemberCallback.onGetGroupMembersResult(list);
            }
            @Override
            public void completeDialog() {
            }
        },groupId);
    }
}