package com.zykj.samplechat.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.zykj.samplechat.R;
import com.zykj.samplechat.model.Friend;
import com.zykj.samplechat.model.MyHYLB;
import com.zykj.samplechat.presenter.TeamYaoqingPresenter;
import com.zykj.samplechat.ui.activity.base.RecycleViewActivity;
import com.zykj.samplechat.ui.adapter.BindUserAdapter;
import com.zykj.samplechat.ui.adapter.TeamYaoqingAdapter;
import com.zykj.samplechat.ui.view.TeamYaoqingView;
import com.zykj.samplechat.ui.widget.CharacterParser;
import com.zykj.samplechat.ui.widget.IndexView;
import com.zykj.samplechat.ui.widget.PinyinFriendComparators;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by ninos on 2016/7/14.
 */
public class TeamYaoqingActivity extends RecycleViewActivity<TeamYaoqingPresenter, TeamYaoqingAdapter, MyHYLB>
        implements TeamYaoqingView,TextView.OnEditorActionListener {

    @Bind(R.id.tv_recyclerindexview_tip)TextView tv_recyclerindexview_tip;
    @Bind(R.id.indexview)IndexView indexview;
    @Bind(R.id.recycler_showuser)RecyclerView recycler_showuser;

    BindUserAdapter bindUserAdapter;
    private String id;
//    private View header;
    private ArrayList<MyHYLB> friends = new ArrayList<>();
    private ArrayList<Friend> fs = new ArrayList<>();

    @Override
    protected void action() {
        super.action();
        if(friends.size() > 0){
            String idlist = "";

            for (MyHYLB friend : friends){
                idlist+=friend.getUserId()+",";
            }
            try{idlist = idlist.substring(0,idlist.lastIndexOf(","));}catch (Exception ex){idlist="";}
            presenter.YaoQing(idlist,id);
        }else{
            toast("请至少选择一个人");
        }
    }

    @Override
    public boolean canAction() {
        return true;
    }

    @Override
    protected TeamYaoqingAdapter provideAdapter() {
//        header = getLayoutInflater().inflate(R.layout.activity_team_create_header, null);
        return new TeamYaoqingAdapter(this, presenter);
    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        fs = (ArrayList<Friend>) getIntent().getBundleExtra("data").getSerializable("fs");
        id = getIntent().getBundleExtra("data").getString("id");
        indexview.setTipTv(tv_recyclerindexview_tip);
        presenter.GetFriendForKeyValue();
        tvAction.setText("邀请");
//        f_search = (EditText)header.findViewById(R.id.f_search) ;
        recycler_showuser.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        bindUserAdapter = new BindUserAdapter(this);
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
//        f_search.setOnEditorActionListener(this);
    }

    @Override
    public TeamYaoqingPresenter createPresenter() {
        return new TeamYaoqingPresenter();
    }

    @Override
    public void onItemClick(View view, int pos, MyHYLB item) {
        if(item.isAdd){
            return;
        }
        item.setSelected(item.getSelected()?false:true);
        if (item.getSelected()){
            friends.add(item);
        }else{
            friends.remove(item);
        }
        adapter.notifyDataSetChanged();

        bindUserAdapter.data.clear();
        bindUserAdapter.data.addAll(friends);

        bindUserAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.tv_all)
    protected void selectAll(View v){
        TextView textView = (TextView)v;
        String all = textView.getText().toString();
        if("全选".equals(all)){
            textView.setText("取消");
            friends.clear();
            for (MyHYLB item:adapter.data){
                item.setSelected(true);
                friends.add(item);
            }
            adapter.notifyDataSetChanged();

            bindUserAdapter.data.clear();
            bindUserAdapter.data.addAll(friends);

            bindUserAdapter.notifyDataSetChanged();
        }else{
            textView.setText("全选");
            friends.clear();
            for (MyHYLB item:adapter.data){
                item.setSelected(false);
            }
            adapter.notifyDataSetChanged();

            bindUserAdapter.data.clear();
            bindUserAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void successFound(ArrayList<MyHYLB> list) {
        PinyinFriendComparators mPinyinComparator = new PinyinFriendComparators();
        CharacterParser mCharacterParser = CharacterParser.getInstance();

        for (MyHYLB friend : list) {
//            if (StringUtil.isEmpty(friend.getNickName())) {
                friend.setTopc(mCharacterParser.getSelling(friend.getNickName()).substring(0, 1).toUpperCase().matches("[A-Z]")?mCharacterParser.getSelling(friend.getNickName()).substring(0, 1).toUpperCase():"#");
//            } else {
//                friend.topc = mCharacterParser.getSelling(friend.RemarkName).substring(0, 1).toUpperCase().matches("[A-Z]")?mCharacterParser.getSelling(friend.RemarkName).substring(0, 1).toUpperCase():"#";
//            }
        }

        Collections.sort(list, mPinyinComparator);

        for(Friend f : fs){
            for (MyHYLB ff : list){
                if(f.MemnerId2.equals(ff.getUserId())){
                    ff.isAdd = true;
                }
            }
        }
        bd(list);
    }

    @Override
    public void errorFound() {
        dismissDialog();
        toast("您没有邀请权限");
    }

    @Override
    public void success() {
        toast("邀请成功");
        dismissDialog();
        finishActivity();
    }

    @Override
    public void refresh(boolean refreshing) {
//        dismissDialog();
    }

    @Override
    protected CharSequence provideTitle() {
        return "邀请";
    }

    private ArrayList<MyHYLB> searchFriends = new ArrayList<>();

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
////            String ear = f_search.getText().toString();
//            if(StringUtil.isEmpty(ear)){
//                toast("搜索不能为空！");
//            }else{
//                hideSoftMethod(f_search);
//                searchFriends.clear();page = 1;
//                for (MyHYLB friend : presenter.getList()) {
//                    if(friend.getNickName().contains(ear)){
//                        searchFriends.add(friend);
//                    }
//                }
//                successFound(searchFriends);
//            }
//            return true;
//        }
        return false;
    }
}
