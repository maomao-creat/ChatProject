package com.zykj.samplechat.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.gson.Gson;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMFriendshipManager;
import com.tencent.imsdk.TIMUserProfile;
import com.tencent.imsdk.TIMValueCallBack;
import com.tencent.qcloud.tim.uikit.base.BaseFragment;
import com.tencent.qcloud.tim.uikit.base.ITitleBarLayout;
import com.tencent.qcloud.tim.uikit.base.IUIKitCallBack;
import com.tencent.qcloud.tim.uikit.component.TitleBarLayout;
import com.tencent.qcloud.tim.uikit.modules.chat.ChatLayout;
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.input.InputLayout;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.inputmore.InputMoreActionUnit;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.message.MessageLayout;
import com.tencent.qcloud.tim.uikit.modules.message.MessageInfo;
import com.tencent.qcloud.tim.uikit.modules.message.MessageInfoUtil;
import com.tencent.qcloud.tim.uikit.utils.ToastUtil;
import com.zykj.samplechat.R;
import com.zykj.samplechat.helper.CustomMessageData;
import com.zykj.samplechat.helper.CustomMessageDraw;
import com.zykj.samplechat.model.QuanJu;
import com.zykj.samplechat.model.TeamBean;
import com.zykj.samplechat.network.HttpUtils;
import com.zykj.samplechat.network.SubscriberRes;
import com.zykj.samplechat.ui.activity.FriendsInfoActivity;
import com.zykj.samplechat.ui.activity.SendMoneyActivity;
import com.zykj.samplechat.ui.activity.TeamInfoActivity;
import com.zykj.samplechat.ui.activity.ZWalletActivity;
import com.zykj.samplechat.ui.activity.ZhuanZhangActivity;
import com.zykj.samplechat.ui.widget.App;
import com.zykj.samplechat.utils.UserUtil;

import java.util.ArrayList;
import java.util.List;


public class ChatFragment extends BaseFragment {

    private View mBaseView;
    private ChatLayout mChatLayout;
    private TitleBarLayout mTitleBar;
    private ChatInfo mChatInfo;
    private String TAG = "chatFragment";
    public static final String CHAT_INFO = "chatInfo";
    private MessageLayout messageLayout;
    private QuanJu quanJu;
    private boolean IsSetBg = false;
    private IUIKitCallBack mCallback;

    public void setCallback(IUIKitCallBack callback) {
        mCallback = callback;
    }

    public static final int REQUEST_CODE_FILE = 1011;
    public static final int REQUEST_CODE_PHOTO = 1012;
    public EditText mEditText;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        mChatInfo = (ChatInfo) bundle.getSerializable(CHAT_INFO);
        if (mChatInfo == null) {
            return null;
        }
        mBaseView = inflater.inflate(R.layout.chat_fragment, container, false);
        initView();
        return mBaseView;
    }


    private void initView() {

        //从布局文件中获取聊天面板组件
        mChatLayout = mBaseView.findViewById(R.id.chat_layout);
        //单聊组件的默认UI和交互初始化
        mChatLayout.initDefault();
        // TODO 通过api设置ChatLayout各种属性的样例
        final ChatFragment chatFragment = new ChatFragment();
        /*
         * 需要聊天的基本信息
         */
        mChatLayout.setChatInfo(mChatInfo);
        //获取单聊面板的标题栏
        mTitleBar = mChatLayout.getTitleBar();
        messageLayout = mChatLayout.getMessageLayout();
        // 从 ChatLayout 里获取 InputLayout
        InputLayout inputLayout = mChatLayout.getInputLayout();
        mEditText=inputLayout.findViewById(R.id.chat_message_input);
//        inputLayout.disableSendPhotoAction(true);
        // 隐藏拍照并发送
        inputLayout.disableCaptureAction(true);
        // 隐藏发送文件
        inputLayout.disableSendFileAction(true);
        // 隐藏摄像并发送
        inputLayout.disableVideoRecordAction(true);
        // 设置自定义的消息渲染时的回调
        messageLayout.setOnCustomMessageDrawListener(new CustomMessageDraw(getActivity(), messageLayout));

// 设置自己聊天气泡的背景
        messageLayout.setRightBubble(getActivity().getResources().getDrawable(R.drawable.message_send_border_my));
// 设置朋友聊天气泡的背景
        messageLayout.setLeftBubble(getActivity().getResources().getDrawable(R.drawable.message_send_border_my));


        if (mChatInfo.getType().equals(TIMConversationType.C2C)) {
            // 个人转账8
            // 定义一个动作单元501
            InputMoreActionUnit unit = new InputMoreActionUnit();
            unit.setIconResId(R.drawable.chat_tran); // 设置单元的图标
            unit.setTitleId(R.string.zhuanzhang); // 设置单元的文字标题
            unit.setOnClickListener(new View.OnClickListener() { // 定义点击事件
                @Override
                public void onClick(View v) {
//                    Intent intent =new Intent(getActivity(), ZhuanZhangActivity.class);
                    startActivityForResult(new Intent(getActivity(), ZhuanZhangActivity.class).
                            putExtra("id", mChatInfo.getId()), 200);
                }
            });
            // 把定义好的单元增加到更多面板
            inputLayout.addAction(unit);

            //待获取用户资料的用户列表
            List<String> users = new ArrayList<String>();
            users.add(mChatInfo.getId());
            users.add(new UserUtil(getActivity()).getUser().getUserCode());
            //获取用户资料
            TIMFriendshipManager.getInstance().getUsersProfile(users, true, new TIMValueCallBack<List<TIMUserProfile>>() {
                @Override
                public void onError(int code, String desc) {
                    //错误码 code 和错误描述 desc，可用于定位请求失败原因
                    //错误码 code 列表请参见错误码表
                    Log.e("getUsersProfile", "getUsersProfile failed: " + code + " desc");
                }

                @Override
                public void onSuccess(List<TIMUserProfile> result) {
                    Log.e("getUsersProfile", "getUsersProfile succ");
                    for (TIMUserProfile res : result) {
                        Log.e("getUsersProfile", "identifier: " + res.getIdentifier() + " nickName: " + res.getNickName());
                        mTitleBar.setTitle(result.get(0).getNickName(), ITitleBarLayout.POSITION.MIDDLE);

                    }
                }
            });
        } else {
            //  群组充值
            // 定义一个动作单元
            InputMoreActionUnit unit = new InputMoreActionUnit();
            unit.setIconResId(R.drawable.recharge); // 设置单元的图标
            unit.setTitleId(R.string.chongzhi); // 设置单元的文字标题
            unit.setOnClickListener(new View.OnClickListener() { // 定义点击事件
                @Override
                public void onClick(View v) {
                    QuanJu.getQuanJu().gokefu(getActivity());
                }
            });
            // 把定义好的单元增加到更多面板
            inputLayout.addAction(unit);
            //  群组体现
            // 定义一个动作单元
            InputMoreActionUnit unit1 = new InputMoreActionUnit();
            unit1.setIconResId(R.drawable.chat_balance); // 设置单元的图标
            unit1.setTitleId(R.string.tixian); // 设置单元的文字标题
            unit1.setOnClickListener(new View.OnClickListener() { // 定义点击事件
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), ZWalletActivity.class));
                }
            });
            // 把定义好的单元增加到更多面板
            inputLayout.addAction(unit1);
            //  群组红包
            // 定义一个动作单元
            InputMoreActionUnit unit2 = new InputMoreActionUnit();
            unit2.setIconResId(R.drawable.chat_red); // 设置单元的图标
            unit2.setTitleId(R.string.hongbao); // 设置单元的文字标题
            unit2.setOnClickListener(new View.OnClickListener() { // 定义点击事件
                @Override
                public void onClick(View v) {
                    startActivityForResult(new Intent(getActivity(), SendMoneyActivity.class).putExtra("fid", mChatInfo.getId()).
                            putExtra("type", "group"), 21);

                }
            });
// 把定义好的单元增加到更多面板
            inputLayout.addAction(unit2);
            HttpUtils.GetTeam(new SubscriberRes<TeamBean>() {
                @Override
                public void onSuccess(TeamBean teamBean) {
                    App.targetId = teamBean.Id;
                }

                @Override
                public void completeDialog() {
                }
            }, mChatInfo.getId(), "0");
        }

        messageLayout.setOnItemClickListener(new MessageLayout.OnItemClickListener() {
            @Override
            public void onMessageLongClick(View view, int position, MessageInfo messageInfo) {
                //因为adapter中第一条为加载条目，位置需减1
                mChatLayout.getMessageLayout().showItemPopMenu(position - 1, messageInfo, view);
            }

            @Override
            public void onUserIconClick(View view, int position, final MessageInfo messageInfo) {
                if (null == messageInfo || null == messageInfo.getTIMMessage()) {
                    return;
                }
//                ToastUtil.toastLongMessage("1");
                if (mChatInfo.getType().equals(TIMConversationType.Group)){
                    if (messageInfo.isSelf()){
                        startActivity(new Intent(getActivity(), FriendsInfoActivity.class).putExtra("id",new UserUtil(getActivity()).getUser().getUserCode()).
                                putExtra("type","0"));
                    }else {
                        startActivity(new Intent(getActivity(), FriendsInfoActivity.class).putExtra("id",messageInfo.getFromUser()).
                                putExtra("type","0"));
                    }


                }
            }

            @Override
            public void onUserIconLongClick(View view, int position, MessageInfo messageInfo) {
                if (null == messageInfo || null == messageInfo.getTIMMessage()) {
                    return;
                }
                if (mChatInfo.getType().equals(TIMConversationType.Group)){
                    HttpUtils.GetTeam(new SubscriberRes<TeamBean>() {
                        @Override
                        public void onSuccess(TeamBean teamBean) {
                            mEditText.setText("@ "+teamBean.Name+" ");
                            mEditText.setSelection(mEditText.getText().toString().length());
                        }

                        @Override
                        public void completeDialog() {
                        }
                    }, messageInfo.getFromUser(), "1");
                }
                }



        });


        // 设置头像圆角，不设置则默认不做圆角处理
        messageLayout.setAvatarRadius(50);
        messageLayout.setRightNameVisibility(1);
        messageLayout.setLeftNameVisibility(1);
        //单聊面板标记栏返回按钮点击事件，这里需要开发者
        // 自行控制
        mTitleBar.setOnLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        mTitleBar.setOnRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mChatInfo.getType() == TIMConversationType.C2C) {
                    startActivity(new Intent(getActivity(), FriendsInfoActivity.class).putExtra("id", mChatInfo.getId()));
                } else {
                    startActivity(new Intent(getActivity(), TeamInfoActivity.class).putExtra("id", mChatInfo.getId()));
//                    startActivity(TeamInfoActivity.class, new Bun().putString("id",  mChatInfo.getId()).ok());//群组详情 暂时屏蔽
                }
            }
        });



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 21 && resultCode == 22) {
            final String hongId = data.getStringExtra("hongbaoId");
            CustomMessageData customMessageData = new CustomMessageData();
            customMessageData.msgData.desc = data.getStringExtra("desc");
            customMessageData.msgData.hongBaoId = data.getStringExtra("hongbaoId");
            customMessageData.msgData.leiNum = Integer.parseInt(data.getStringExtra("num"));
            customMessageData.msgData.money = Integer.parseInt(data.getStringExtra("amount"));
            customMessageData.msgData.photo = data.getStringExtra("photo");
            customMessageData.msgData.name = data.getStringExtra("name");
            customMessageData.msgData.suijiStr = data.getStringExtra("suijiStr");
            customMessageData.msgData.UserNum = data.getStringExtra("Usernum");
            customMessageData.customMsgName="红包";
            customMessageData.msgType = "hongbao";
            Gson gson = new Gson();
            String json = gson.toJson(customMessageData);
            final MessageInfo info = MessageInfoUtil.buildCustomMessage(json);
            mChatLayout.getChatManager().sendMessage(info, false, new IUIKitCallBack() {
                @Override
                public void onSuccess(Object data) {
                    Log.e("SendCustom", "Success");
                }

                @Override
                public void onError(String module, int errCode, String errMsg) {
                    Log.e("SendCustom", "Failed:errcode:" + errCode + "errMsg:" + errMsg);
                }
            });
        }
        if (resultCode == 200) {
            final String content = data.getStringExtra("content");
            CustomMessageData customMessageData = new CustomMessageData();
            customMessageData.msgData.content = content;
            customMessageData.msgType = "zhuanzhang";
            customMessageData.customMsgName="转账";
            Gson gson = new Gson();
            String json = gson.toJson(customMessageData);
            MessageInfo info = MessageInfoUtil.buildCustomMessage(json);
//            info.setExtra("转账");
            mChatLayout.getChatManager().sendMessage(info, false, new IUIKitCallBack() {
                @Override
                public void onSuccess(Object data) {
                    IsSetBg = true;
                    Log.e("ZhuangMessageData", "Success");
                }

                @Override
                public void onError(String module, int errCode, String errMsg) {
                    Log.e("ZhuangMessageData", "Failed:errcode:" + errCode + "errMsg:" + errMsg);
                }
            });
        }

        if (requestCode == REQUEST_CODE_PHOTO) {
            if (resultCode != -1) {
                return;
            }
            Uri uri = data.getData();//得到uri，后面就是将uri转化成file的过程。
            if (mCallback != null) {
                mCallback.onSuccess(uri);
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mChatLayout.exitChat();
    }


}
