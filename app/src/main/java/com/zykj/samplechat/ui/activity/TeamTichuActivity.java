package com.zykj.samplechat.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zykj.samplechat.R;
import com.zykj.samplechat.model.Friend;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.presenter.TeamTichuPresenter;
import com.zykj.samplechat.ui.activity.base.RecycleViewActivity;
import com.zykj.samplechat.ui.adapter.BindUserAdapters;
import com.zykj.samplechat.ui.adapter.TeamTichuAdapter;
import com.zykj.samplechat.ui.view.TeamTichuView;
import com.zykj.samplechat.ui.widget.CharacterParser;
import com.zykj.samplechat.ui.widget.IndexView;
import com.zykj.samplechat.ui.widget.PinyinFriendComparator;
import com.zykj.samplechat.utils.AESOperator;
import com.zykj.samplechat.utils.StringUtil;
import com.zykj.samplechat.utils.UserUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by ninos on 2016/7/14.
 */
public class TeamTichuActivity extends RecycleViewActivity<TeamTichuPresenter, TeamTichuAdapter, Friend> implements TeamTichuView {

    @Bind(R.id.tv_recyclerindexview_tip)TextView tv_recyclerindexview_tip;
    @Bind(R.id.indexview)IndexView indexview;
    @Bind(R.id.recycler_showuser)RecyclerView recycler_showuser;

    LinearLayout f_add_friends;
    BindUserAdapters bindUserAdapter;
    private String id;
    private View header;
    private ArrayList<Friend> friends = new ArrayList<>();
    private ArrayList<Friend> fs = new ArrayList<>();

    @Override
    protected void action() {
        super.action();
        if(friends.size() > 0){
            String idlist = "";

            for (Friend friend : friends){
                idlist+=friend.MemnerId2+",";
            }
            try{idlist = idlist.substring(0,idlist.lastIndexOf(","));}catch (Exception ex){idlist="";}
            presenter.TiChu(idlist,id);
        }else{
            toast("请至少选择一个人");
        }
    }

    @Override
    public boolean canAction() {
        return true;
    }

    @Override
    protected TeamTichuAdapter provideAdapter() {
        return new TeamTichuAdapter(this, presenter);
    }

    public void getFriends(){
        Map map = new HashMap();
        map.put("key", Const.KEY);
        map.put("uid",Const.UID);
        map.put("function","GetFriendForKeyValue");
        map.put("userid",new UserUtil(getContext()).getUserId());
        String json = StringUtil.toJson(map);
        try {
            String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
            presenter.GetFriendForKeyValue(data);
        }catch (Exception ex){
        }
    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        fs = (ArrayList<Friend>) getIntent().getBundleExtra("data").getSerializable("fs");
        id = getIntent().getBundleExtra("data").getString("id");
        indexview.setTipTv(tv_recyclerindexview_tip);
        tvAction.setText("移除");
        successFound(fs);
        recycler_showuser.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        bindUserAdapter = new BindUserAdapters(this);

        recycler_showuser.setAdapter(bindUserAdapter);
    }

    @Override
    protected RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_team_create;
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
    }

    @Override
    public TeamTichuPresenter createPresenter() {
        return new TeamTichuPresenter();
    }

    @Override
    public void onItemClick(View view, int pos, Friend item) {

        if(item.MemnerId == new UserUtil(this).getUserId()){
            return;
        }

        item.isSelected = item.isSelected?false:true;
        if (item.isSelected){
            friends.add(item);
        }else{
            friends.remove(item);
        }
        adapter.notifyDataSetChanged();

        bindUserAdapter.data.clear();
        bindUserAdapter.data.addAll(friends);

        bindUserAdapter.notifyDataSetChanged();
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
    public void errorFound() {

    }

    @Override
    public void refresh(boolean refreshing) {

    }

    @Override
    public void success() {
        toast("移除成功");
        dismissDialog();
        finishActivity();
    }

    @Override
    protected CharSequence provideTitle() {
        return "移除";
    }
}
