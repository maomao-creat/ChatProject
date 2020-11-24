package com.zykj.samplechat.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zykj.samplechat.R;
import com.zykj.samplechat.model.Friend;
import com.zykj.samplechat.model.Team;
import com.zykj.samplechat.presenter.TeamCreatePresenter;
import com.zykj.samplechat.ui.activity.base.RecycleViewActivity;
import com.zykj.samplechat.ui.adapter.TeamCreateAdapter;
import com.zykj.samplechat.ui.view.TeamCreateView;
import com.zykj.samplechat.ui.widget.CharacterParser;
import com.zykj.samplechat.ui.widget.PinyinFriendComparator;
import com.zykj.samplechat.utils.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;

/**
 * Created by ninos on 2016/7/14.
 */
public class FriendSearchActivity extends RecycleViewActivity<TeamCreatePresenter, TeamCreateAdapter, Friend>
        implements TeamCreateView,TextView.OnEditorActionListener {

    @Bind(R.id.f_phone)EditText f_search;
    private ArrayList<Friend> list;

    @Override
    protected TeamCreateAdapter provideAdapter() {
        return new TeamCreateAdapter(this, presenter, true);
    }

    @Override
    protected RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_user_search;
    }

    @Override
    public void initListeners() {
        f_search.setOnEditorActionListener(this);
    }

    @Override
    public TeamCreatePresenter createPresenter() {
        return new TeamCreatePresenter();
    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        String friends = getIntent().getStringExtra("friends");
        list = new Gson().fromJson(friends, new TypeToken<List<Friend>>(){}.getType());
        //successFound(list);
    }

    @Override
    public void onItemClick(View view, int pos, Friend item) {
//        startActivity(FriendsInfoActivity.class,new Bun().putInt("id", item.Id).ok());
    }

    @Override
    public void successFound(ArrayList<Friend> list) {
        PinyinFriendComparator mPinyinComparator = new PinyinFriendComparator();
        CharacterParser mCharacterParser = CharacterParser.getInstance();

        for (Friend friend : list) {
            if (StringUtil.isEmpty(friend.RemarkName)) {
                friend.topc = mCharacterParser.getSelling(friend.NicName).substring(0, 1).toUpperCase().matches("[A-Z]")?mCharacterParser.getSelling(friend.NicName).substring(0, 1).toUpperCase():"#";
            } else {
                friend.topc = mCharacterParser.getSelling(friend.RemarkName).substring(0, 1).toUpperCase().matches("[A-Z]")?mCharacterParser.getSelling(friend.RemarkName).substring(0, 1).toUpperCase():"#";
            }
        }
        Collections.sort(list, mPinyinComparator);
        bd(list);
    }

    @Override
    public void errorFound() {}

    @Override
    public void refresh(boolean refreshing) {}

    @Override
    public void success(Team team) {}

    @Override
    protected CharSequence provideTitle() {
        return "发起群聊";
    }

    private ArrayList<Friend> searchFriends = new ArrayList<>();
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            String ear = f_search.getText().toString();
            if(StringUtil.isEmpty(ear)){
                toast("搜索不能为空！");
            }else{
                hideSoftMethod(f_search);
                searchFriends.clear();page = 1;
                for (Friend friend : list) {
                    if(friend.NicName.contains(ear) || friend.RemarkName.contains(ear)){
                        searchFriends.add(friend);
                    }
                }
                successFound(searchFriends);
            }
            return true;
        }
        return false;
    }
}