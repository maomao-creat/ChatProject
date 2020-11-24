package com.zykj.samplechat.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zykj.samplechat.R;
import com.zykj.samplechat.model.Team;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.presenter.TeamPresenter;
import com.zykj.samplechat.ui.activity.base.RecycleViewActivity;
import com.zykj.samplechat.ui.adapter.TeamAdapter;
import com.zykj.samplechat.ui.view.TeamView;
import com.zykj.samplechat.utils.AESOperator;
import com.zykj.samplechat.utils.StringUtil;
import com.zykj.samplechat.utils.UserUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.rong.imkit.RongIM;

/**
 * Created by ninos on 2016/7/14.
 */
public class TeamActivity extends RecycleViewActivity<TeamPresenter,TeamAdapter,Team> implements TeamView {

    @Override
    protected TeamAdapter provideAdapter() {
        return new TeamAdapter(getContext(), presenter);
    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Map map = new HashMap();
        map.put("key", Const.KEY);
        map.put("uid",Const.UID);
        map.put("function","SelectAllTeamByUserId");
        map.put("userid",new UserUtil(this).getUserId());
        String json = StringUtil.toJson(map);
        try {
            String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
            presenter.SelectAllTeamByUserId(data);
        }catch (Exception e){
        }
    }

    @Override
    protected RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @Override
    protected CharSequence provideTitle() {
        return "选择群聊";
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_team;
    }

    @Override
    public void initListeners() {

    }

    @Override
    public TeamPresenter createPresenter() {
        return new TeamPresenter();
    }

    @Override
    public void onItemClick(View view, int pos, Team item) {
        RongIM.getInstance().startGroupChat(view.getContext(), item.Id + "", item.Name);
        finish();
    }

    @Override
    public void success(ArrayList<Team> list) {
        bd(list);
    }

    @Override
    public void errorFound() {}

    @Override
    public void refresh(boolean refreshing) {}

    @Override
    public void success(String str) {}
}
