package com.zykj.samplechat.ui.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tencent.imsdk.TIMConversationType;
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo;
import com.zykj.samplechat.R;
import com.zykj.samplechat.model.Friend;
import com.zykj.samplechat.model.QuanJu;
import com.zykj.samplechat.presenter.FriendsPresenter;
import com.zykj.samplechat.ui.activity.ChatActivity;
import com.zykj.samplechat.ui.activity.FriendsInfoActivity;
import com.zykj.samplechat.ui.activity.base.SwipeRecycleViewFragment;
import com.zykj.samplechat.ui.adapter.FriendsAdapter;
import com.zykj.samplechat.ui.view.FriendsView;
import com.zykj.samplechat.ui.widget.CharacterParser;
import com.zykj.samplechat.ui.widget.IndexView;
import com.zykj.samplechat.ui.widget.PinyinFriendComparator;
import com.zykj.samplechat.utils.Bun;
import com.zykj.samplechat.utils.StringUtil;
import com.zykj.samplechat.utils.UserUtil;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.Bind;
import butterknife.OnClick;

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
public class FriendsFragment extends SwipeRecycleViewFragment<FriendsPresenter, FriendsAdapter, Friend> implements FriendsView {

    TextView number;
    @Bind(R.id.tv_recyclerindexview_tip)
    TextView tv_recyclerindexview_tip;
    @Bind(R.id.indexview)
    IndexView indexview;
    LinearLayout f_add_friends;
    LinearLayout f_group_create;
    LinearLayout f_group;
    @Bind(R.id.tzxxnr)
    TextView tzxxnr;
    private View header;
    private String friends = "";
    public MessageReceiver mMessageReceiver;
    public static final String CHAT_INFO = "chatInfo";
    @Override
    protected void initThings(View view) {
        super.initThings(view);
        indexview.setTipTv(tv_recyclerindexview_tip);
        f_add_friends = (LinearLayout) header.findViewById(R.id.f_add_friends);//我的好友
        f_group_create = (LinearLayout) header.findViewById(R.id.f_group_create);//群聊
        f_group = (LinearLayout) header.findViewById(R.id.f_group);//小莫客服
        number = (TextView) header.findViewById(R.id.number);
        registerMessageReceiver();
        tzxxnr.setText(QuanJu.getQuanJu().getTzxx());
    }

    @Override
    public void onResume() {
        super.onResume();
        page = 1;
        presenter.getData(page, count);//发送post
    }



    @Override
    public void initListeners() {
        indexview.setOnChangedListener(new IndexView.OnChangedListener() {
            @Override
            public void onChanged(String text) {
                int position = adapter.getPositionForSection(text.charAt(0));
                position++;
                if (position != -1) {
                    // position的item滑动到RecyclerView的可见区域，如果已经可见则不会滑动
                    layoutManager.scrollToPosition(position);
                }
            }
        });

        f_add_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//我的好友
//                startActivity(MymoyouActivity.class);
            }
        });

        f_group_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//群聊
//                startActivity(MyqunActivity.class);
            }
        });

        f_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//小莫客服
               // startActivity(TeamActivity.class);
//                QuanJu.getQuanJu().gokefu(getContext());
            }
        });
    }

    @OnClick(R.id.img_search)
    public void clickSearch() {
        //startActivity(new Intent(getContext(), FriendSearchActivity.class).putExtra("friends", friends));
    }

    @Override
    public int provideLayoutId() {
        return R.layout.fragment_friends;
    }

    @Override
    public FriendsPresenter createPresenter() {
        return new FriendsPresenter();
    }

    @Override
    protected FriendsAdapter provideAdapter() {
        header = getActivity().getLayoutInflater().inflate(R.layout.fragment_friends_header, null);
        return new FriendsAdapter(getContext(), presenter, header);
    }

    @Override
    protected RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @Override
    public void onItemClick(View view, int pos, Friend item) {//用户信息 格式转换  勿乱动
        ChatInfo chatInfo = new ChatInfo();
        chatInfo.setType(TIMConversationType.C2C);
        chatInfo.setId(item.UserCode+"");
        String chatName = item.NicName;
        if (!TextUtils.isEmpty(item.NicName)) {
            chatName = item.NicName;
        } else if (!TextUtils.isEmpty(item.RemarkName)) {
            chatName = item.RemarkName;
        }
        chatInfo.setChatName(chatName);
        Intent intent = new Intent(getActivity(), ChatActivity.class);
        intent.putExtra(CHAT_INFO, chatInfo);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 17 && resultCode == Activity.RESULT_OK && data != null) {
            String result = data.getStringExtra("result");
            if (result.startsWith("http")) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(result));
                startActivity(intent);
            } else if (result.startsWith("朋友:")) {
                int id = Integer.parseInt(result.substring(result.indexOf(":") + 1));
                startActivity(FriendsInfoActivity.class, new Bun().putInt("id", id).ok());
                finishActivity();
            } else {
                snb(result, indexview);
            }
        }
    }

    @Override
    public void successFound(ArrayList<Friend> list, ArrayList<Friend> top, int credit) {

        adapter.data.clear();

        PinyinFriendComparator mPinyinComparator = new PinyinFriendComparator();
        CharacterParser mCharacterParser = CharacterParser.getInstance();
        if(UserUtil.getUser().isManager()){
            for (Friend friend : list) {
                if (StringUtil.isEmpty(friend.RemarkName)) {
                    friend.topc = mCharacterParser.getSelling(friend.NicName).substring(0, 1).toUpperCase().matches("[A-Z]") ? mCharacterParser.getSelling(friend.NicName).substring(0, 1).toUpperCase() : "#";
                } else {
                    friend.topc = mCharacterParser.getSelling(friend.RemarkName).substring(0, 1).toUpperCase().matches("[A-Z]") ? mCharacterParser.getSelling(friend.RemarkName).substring(0, 1).toUpperCase() : "#";
                }
            }
        }else {
            for (Friend friend : list) {
                friend.topc = "管理员";
            }
        }
        Collections.sort(list, mPinyinComparator);

        bd(list);
    }

    @Override
    public void credit(int state) {

    }

    @Override
    public void errorFound() {
    }

    @Override
    public void success(int count) {
        if (count == 0) {
            number.setVisibility(View.GONE);
        } else {
            number.setVisibility(View.VISIBLE);
            number.setText(String.valueOf(count));
        }
    }


    //注册广播
    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction("DELETE_RECEIVED_ACTION");
        getContext().registerReceiver(mMessageReceiver, filter);
    }




    //广播接收者
    public class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("DELETE_RECEIVED_ACTION".equals(intent.getAction())) {
                page = 1;
                presenter.getData(page, count);
            }
        }
    }
}