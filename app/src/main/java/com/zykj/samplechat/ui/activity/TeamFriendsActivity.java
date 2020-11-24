package com.zykj.samplechat.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zykj.samplechat.R;
import com.zykj.samplechat.model.Friend;
import com.zykj.samplechat.model.qunxxx;
import com.zykj.samplechat.presenter.TeamFriendsPresenter;
import com.zykj.samplechat.ui.activity.base.RecycleViewActivity;
import com.zykj.samplechat.ui.adapter.TeamFriendsAdapter;
import com.zykj.samplechat.ui.view.TeamFriendsView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ninos on 2016/7/14.
 */
public class TeamFriendsActivity extends RecycleViewActivity<TeamFriendsPresenter, TeamFriendsAdapter, Friend> implements TeamFriendsView {

    private int teamid;
    private String teamid2;
    public ArrayList<Friend> fs;

    @Override
    protected TeamFriendsAdapter provideAdapter() {
        return new TeamFriendsAdapter(getContext(), presenter);
    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        fs = (ArrayList<Friend>) getIntent().getBundleExtra("data").getSerializable("fs");
        bd(fs);
//        onResume1();
    }

    qunxxx mye;//群信息

//    protected void onResume1() {//初始化群组信息
//        super.onResume();
//        HttpUtils.SelectTeamMember(new SubscriberRes<ArrayList<Friend>>() {
//            @Override
//            public void onSuccess(ArrayList<Friend> mye) {
//                bd(mye);
//            }
//
//            @Override
//            public void completeDialog() {
//            }
//        },teamid2);
//    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @Override
    protected CharSequence provideTitle() {
        return "聊天成员";
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_team;
    }

    @Override
    public void initListeners() {

    }

    @Override
    public TeamFriendsPresenter createPresenter() {
        return new TeamFriendsPresenter();
    }

    @Override
    public void onItemClick(View view, int pos, Friend item) {
        startActivity(new Intent(getContext(),FriendsInfoActivity.class).putExtra("id", item.MemnerId2));
    }

    @Override
    public void success(ArrayList<Friend> list) {
//        bd(list);
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
}
