package com.zykj.samplechat.ui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMGroupManager;
import com.tencent.imsdk.TIMValueCallBack;
import com.tencent.imsdk.ext.group.TIMGroupDetailInfoResult;
import com.tencent.qcloud.tim.uikit.component.action.PopActionClickListener;
import com.tencent.qcloud.tim.uikit.component.action.PopDialogAdapter;
import com.tencent.qcloud.tim.uikit.component.action.PopMenuAction;
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo;
import com.tencent.qcloud.tim.uikit.modules.conversation.ConversationLayout;
import com.tencent.qcloud.tim.uikit.modules.conversation.ConversationListLayout;
import com.tencent.qcloud.tim.uikit.modules.conversation.base.ConversationInfo;
import com.tencent.qcloud.tim.uikit.utils.PopWindowUtil;
import com.vector.update_app.UpdateAppBean;
import com.vector.update_app.UpdateAppManager;
import com.vector.update_app.UpdateCallback;
import com.vector.update_app.listener.ExceptionHandler;
import com.zykj.samplechat.R;
import com.zykj.samplechat.model.QuanJu;
import com.zykj.samplechat.model.VersonBean;
import com.zykj.samplechat.model.mqunlb;
import com.zykj.samplechat.network.HttpUtils;
import com.zykj.samplechat.network.SubscriberRes;
import com.zykj.samplechat.presenter.base.BasePresenterImp;
import com.zykj.samplechat.ui.activity.ChatActivity;
import com.zykj.samplechat.ui.activity.base.ToolBarFragment;
import com.zykj.samplechat.utils.APKVersionCodeUtils;
import com.zykj.samplechat.utils.OkGoUpdateHttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2019/8/15.
 */

public class ConversationFragment extends ToolBarFragment {

    public static final String CHAT_INFO = "chatInfo";
    @Bind(R.id.conversation_layout)
    ConversationLayout mConversationLayout;
    private ListView mConversationPopList;
    private PopDialogAdapter mConversationPopAdapter;
    private PopupWindow mConversationPopWindow;
    private List<PopMenuAction> mConversationPopActions = new ArrayList<>();
    private List<TIMConversation> mList = new ArrayList<>();

    //49r3tn   tzzno6     7fwg5v
    @Override
    protected void initThings(View view) {
        super.initThings(view);
        // 会话列表面板的默认UI和交互初始化
        mConversationLayout.initDefault();
        mConversationLayout.getTitleBar().setVisibility(View.GONE);
//                ConversationLayoutHelper.customizeConversation(mConversationLayout);
        ConversationListLayout listLayout = mConversationLayout.getConversationList();
        listLayout.enableItemRoundIcon(true);// 设置item头像是否显示圆角，默认是方形
        listLayout.disableItemUnreadDot(true);// 设置item是否不显示未读红点，默认显示


        mConversationLayout.getConversationList().setOnItemClickListener(new ConversationListLayout.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, ConversationInfo conversationInfo) {
                //此处为demo的实现逻辑，更根据会话类型跳转到相关界面，开发者可根据自己的应用场景灵活实现
                ChatInfo chatInfo = new ChatInfo();
                chatInfo.setType(conversationInfo.isGroup() ? TIMConversationType.Group : TIMConversationType.C2C);
                chatInfo.setId(conversationInfo.getId());
                chatInfo.setChatName(conversationInfo.getTitle());
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra(CHAT_INFO, chatInfo);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        mConversationLayout.getConversationList().setOnItemLongClickListener(new ConversationListLayout.OnItemLongClickListener() {
            @Override
            public void OnItemLongClick(View view, int position, ConversationInfo conversationInfo) {
                startPopShow(view, position, conversationInfo);
            }
        });

        initPopMenuAction();


        GetSystemNews();


        UpdateVersion();
    }

    public void UpdateVersion() {
        HttpUtils.AppVersion(new SubscriberRes<VersonBean>() {
            @Override
            public void onSuccess(final VersonBean versonBean) {
                if (APKVersionCodeUtils.getVersionCode(getContext()) < versonBean.anzhuoversion) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("更新提示")
                            .setMessage("是否升级到新版本")
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //代码实现跳转
                                    Intent intent = new Intent();
                                    intent.setAction("android.intent.action.VIEW");
                                    Uri content_url = Uri.parse(versonBean.anzhuourl);//此处填链接
                                    intent.setData(content_url);
                                    startActivity(intent);
                                }
                            }).create()
                            .show();
                }



            }

            @Override
            public void completeDialog() {
                super.completeDialog();
            }
        });
    }


    private void GetSystemNews() {
        HttpUtils.GetSaoLeiGroupList(new SubscriberRes<ArrayList<mqunlb>>() {
            @Override
            public void onSuccess(ArrayList<mqunlb> mye) {
                QuanJu.getQuanJu().setMye(mye);

                List<String> list = new ArrayList<>();
                for (int i = 0; i < mye.size(); i++) {
                    list.add(mye.get(i).TeamCode);
                }
                TIMGroupManager.getInstance().getGroupInfo(list, new TIMValueCallBack<List<TIMGroupDetailInfoResult>>() {
                    @Override
                    public void onError(int i, String s) {
                        Log.e("getGroupInfo", i + "");
                    }

                    @Override
                    public void onSuccess(List<TIMGroupDetailInfoResult> timGroupDetailInfoResults) {
                        Log.e("getGroupInfo", timGroupDetailInfoResults + "");
                    }
                });

            }

            @Override
            public void completeDialog() {
            }
        });
    }


    private void initPopMenuAction() {

        // 设置长按conversation显示PopAction
        List<PopMenuAction> conversationPopActions = new ArrayList<PopMenuAction>();
        PopMenuAction action = new PopMenuAction();
        action.setActionName("置顶聊天");
        action.setActionClickListener(new PopActionClickListener() {
            @Override
            public void onActionClick(int position, Object data) {
                mConversationLayout.setConversationTop(position, (ConversationInfo) data);
            }
        });
        conversationPopActions.add(action);
        action = new PopMenuAction();
        action.setActionClickListener(new PopActionClickListener() {
            @Override
            public void onActionClick(int position, Object data) {
                mConversationLayout.deleteConversation(position, (ConversationInfo) data);
            }
        });
        action.setActionName("删除聊天");
        conversationPopActions.add(action);
        mConversationPopActions.clear();
        mConversationPopActions.addAll(conversationPopActions);
    }

    /**
     * 长按会话item弹框
     *
     * @param index            会话序列号
     * @param conversationInfo 会话数据对象
     * @param locationX        长按时X坐标
     * @param locationY        长按时Y坐标
     */
    private void showItemPopMenu(final int index, final ConversationInfo conversationInfo, float locationX, float locationY) {
        if (mConversationPopActions == null || mConversationPopActions.size() == 0)
            return;
        View itemPop = LayoutInflater.from(getActivity()).inflate(R.layout.pop_menu_layout, null);
        mConversationPopList = itemPop.findViewById(R.id.pop_menu_list);
        mConversationPopList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PopMenuAction action = mConversationPopActions.get(position);
                if (action.getActionClickListener() != null) {
                    action.getActionClickListener().onActionClick(index, conversationInfo);
                }
                mConversationPopWindow.dismiss();
            }
        });

        for (int i = 0; i < mConversationPopActions.size(); i++) {
            PopMenuAction action = mConversationPopActions.get(i);
            if (conversationInfo.isTop()) {
                if (action.getActionName().equals("置顶聊天")) {
                    action.setActionName("取消置顶");
                }
            } else {
                if (action.getActionName().equals("取消置顶")) {
                    action.setActionName("置顶聊天");
                }

            }
        }
        mConversationPopAdapter = new PopDialogAdapter();
        mConversationPopList.setAdapter(mConversationPopAdapter);
        mConversationPopAdapter.setDataSource(mConversationPopActions);
        mConversationPopWindow = PopWindowUtil.popupWindow(itemPop, toolBar, (int) locationX, (int) locationY);
        toolBar.postDelayed(new Runnable() {
            @Override
            public void run() {
                mConversationPopWindow.dismiss();
            }
        }, 10000); // 10s后无操作自动消失
    }

    public void startPopShow(View view, int position, ConversationInfo info) {
        showItemPopMenu(position, info, /*view.getWidth() / 2 + */view.getX(), view.getY() + view.getHeight() / 2);
    }


    @Override
    protected String provideTitle() {
        return "聊天";
    }

    @Override
    public void initListeners() {

    }

    @Override
    public int provideLayoutId() {
        return R.layout.conversation_fragment;
    }

    @Override
    public BasePresenterImp createPresenter() {
        return null;
    }


}
