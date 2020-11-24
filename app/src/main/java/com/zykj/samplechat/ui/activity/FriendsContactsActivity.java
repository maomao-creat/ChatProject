package com.zykj.samplechat.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zykj.samplechat.R;
import com.zykj.samplechat.model.Contacts;
import com.zykj.samplechat.model.Friend;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.presenter.FriendsContactsPresenter;
import com.zykj.samplechat.ui.activity.base.RecycleViewActivity;
import com.zykj.samplechat.ui.adapter.FriendsContactsAdapter;
import com.zykj.samplechat.ui.view.FriendsContactsView;
import com.zykj.samplechat.ui.widget.CharacterParser;
import com.zykj.samplechat.ui.widget.IndexView;
import com.zykj.samplechat.ui.widget.PinyinFriendComparator;
import com.zykj.samplechat.utils.AESOperator;
import com.zykj.samplechat.utils.PhoneUtil;
import com.zykj.samplechat.utils.StringUtil;
import com.zykj.samplechat.utils.UserUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by ninos on 2016/7/8.
 */
public class FriendsContactsActivity extends RecycleViewActivity<FriendsContactsPresenter,FriendsContactsAdapter,Friend> implements FriendsContactsView {

    private ArrayList<Contacts> contactses;

    private View header;
    private ArrayList<Friend> friends = new ArrayList<>();

    LinearLayout add_for_phone;
    @Bind(R.id.tv_riv_tip)TextView tv_riv_tip;
    @Bind(R.id.index)IndexView index;

    @Override
    protected FriendsContactsAdapter provideAdapter() {
        header = getLayoutInflater().inflate(R.layout.activity_friends_contacts_header, null);
        return new FriendsContactsAdapter(this,getContext(), presenter, header);
    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        index.setTipTv(tv_riv_tip);
        contactses = new PhoneUtil(this).testReadAllContacts();
        add_for_phone = (LinearLayout)header.findViewById(R.id.add_for_phone);
        String phone = "";
        for(Contacts contacts : contactses){
            for(String p : contacts.phones){
                if(!p.trim().equals(""))
                    phone+="'" + p + "'" +"A";
            }
        }
        add_for_phone = (LinearLayout)header.findViewById(R.id.add_for_phone);
        Map map = new HashMap();
        map.put("key", Const.KEY);
        map.put("uid",Const.UID);
        map.put("function","ContactsMatch");
        map.put("userid",new UserUtil(this).getUserId());
        map.put("contacts",phone);
        String json = StringUtil.toJson(map);
        try {
            String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
            presenter.ContactsMatch(data);
        }catch (Exception ex){}
    }

    @Override
    protected RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @Override
    protected CharSequence provideTitle() {
        return "通讯录朋友";
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_friends_contacts;
    }

    @Override
    public void initListeners() {
        index.setOnChangedListener(new IndexView.OnChangedListener() {
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
        add_for_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(FriendsFoundActivity.class);
            }
        });
    }

    @Override
    public FriendsContactsPresenter createPresenter() {
        return new FriendsContactsPresenter();
    }

    @Override
    public void onItemClick(View view, int pos, Friend item) {

    }

    private void bindData(ArrayList<Friend> list) {
        PinyinFriendComparator mPinyinComparator = new PinyinFriendComparator();
        CharacterParser mCharacterParser = CharacterParser.getInstance();

        for (Friend friend : list) {
            if (StringUtil.isEmpty(friend.RealName)) {
                friend.topc = mCharacterParser.getSelling(friend.Phone).substring(0, 1).toUpperCase().matches("[A-Z]")?mCharacterParser.getSelling(friend.Phone).substring(0, 1).toUpperCase():"#";
            } else {
                friend.topc = mCharacterParser.getSelling(friend.RealName).substring(0, 1).toUpperCase().matches("[A-Z]")?mCharacterParser.getSelling(friend.RealName).substring(0, 1).toUpperCase():"#";
            }
        }

        Collections.sort(list, mPinyinComparator);
    }

    @Override
    public void success(ArrayList<Friend> list) {
//        for(Friend friend : list){
//            for(Contacts contacts : contactses){
//                for(String p : contacts.phones){
//                    if(p.equals(friend.Phone))
//                        friend.RealName = contacts.name;
//                }
//            }
//        }

        ArrayList<Friend> temp = new ArrayList<>();


        for (Contacts contacts : contactses) {
            for (String p : contacts.phones) {
                Friend f = new Friend();
                f.RealName = contacts.name;
                f.Phone = p;
                f.isShare = true;
                temp.add(f);
            }
        }

        for (Friend friend : temp) {
            for (Friend friend1 : list) {

                if (friend.Phone.equals(friend1.Phone)) {
                    friend.isShare = false;
                }

            }
        }

        bindData(temp);
        bd(temp);
    }

    @Override
    public void error() {
        toast("您的好友请求已发送，请耐心等候对方处理！");
    }
}
