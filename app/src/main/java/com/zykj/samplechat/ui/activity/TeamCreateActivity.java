package com.zykj.samplechat.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.zhy.http.okhttp.callback.StringCallback;
import com.zykj.samplechat.R;
import com.zykj.samplechat.model.Friend;
import com.zykj.samplechat.model.MyHYLB;
import com.zykj.samplechat.model.Team;
import com.zykj.samplechat.network.HttpUtils;
import com.zykj.samplechat.network.SubscriberRes;
import com.zykj.samplechat.presenter.TeamCreatePresenter;
import com.zykj.samplechat.ui.activity.base.RecycleViewActivity;
import com.zykj.samplechat.ui.adapter.BindUserAdapter;
import com.zykj.samplechat.ui.adapter.TeamCreateAdapter;
import com.zykj.samplechat.ui.view.TeamCreateView;
import com.zykj.samplechat.ui.widget.CharacterParser;
import com.zykj.samplechat.ui.widget.IndexView;
import com.zykj.samplechat.ui.widget.PinyinFriendComparator;
import com.zykj.samplechat.utils.ActivityUtil;
import com.zykj.samplechat.utils.IsZH;
import com.zykj.samplechat.utils.NR;
import com.zykj.samplechat.utils.NRUtils;
import com.zykj.samplechat.utils.StringUtil;
import com.zykj.samplechat.utils.ToolsUtils;
import com.zykj.samplechat.utils.UserUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import okhttp3.Request;

/**
 * Created by ninos on 2016/7/14.
 */
public class TeamCreateActivity extends RecycleViewActivity<TeamCreatePresenter, TeamCreateAdapter, Friend>
        implements TeamCreateView, TextView.OnEditorActionListener {

    @Bind(R.id.tv_recyclerindexview_tip)
    TextView tv_recyclerindexview_tip;
    @Bind(R.id.indexview)
    IndexView indexview;
    @Bind(R.id.recycler_showuser)
    RecyclerView recycler_showuser;

    EditText f_search;
    BindUserAdapter bindUserAdapter;
    private View header;
    private ArrayList<Friend> friends = new ArrayList<>();

    @Override
    protected void action() {
        super.action();
        if (friends.size() == 1) {
            RongIM.getInstance().startPrivateChat(getContext(), friends.get(0).Id + "", friends.get(0).NicName);
            ActivityUtil.addActivity(this);
            ActivityUtil.finishActivitys();
            toast("创建群最少选择2个好友");
        } else if (friends.size() > 1) {
            String idlist = "";
            for (Friend friend : friends) {
                idlist += friend.MemnerId2 + ",";
            }
            idlist += new UserUtil(this).getUserId2();
//            try{idlist = idlist.substring(0,idlist.lastIndexOf("A"));}catch (Exception ex){idlist="";}
            // startActivity(new Intent(this, TeamManagerActivity.class).putExtra("ids",idlist));//这里有上传群头像的一步 暂时没有
            if (StringUtil.isEmpty(id)) {//创建群
                goqunl(idlist);
            } else {//邀请好友
//                yqhy(idlist);
            }

            // finish();
        } else {

            toast("请至少选择一个好友");
        }
    }

    void goqunl(String idlist) {//创建群
        // LogUtils.i("xxxxx", "" +idlist);  //输出测试
        showDialog();
        HttpUtils.UserCreateTeam (new SubscriberRes<String>() {
            @Override
            public void onSuccess(String userBean) {
                dismissDialog();
                ToolsUtils.toast(getContext(),"创建成功");
                finish();
            }
            @Override
            public void completeDialog() {
                dismissDialog();
            }
        },idlist);
    }

//    void yqhy(String idlist) {//群邀请好友
//        // LogUtils.i("xxxxx", "" +idlist);  //输出测试
//        showDialog();
//        HttpUtils.YaoQing(new SubscriberRes<String>() {
//            @Override
//            public void onSuccess(String userBean) {
//                dismissDialog();
//                ToolsUtils.toast(getContext(),"邀请成功");
//                finish();
//            }
//            @Override
//            public void completeDialog() {
//                dismissDialog();
//            }
//        },idlist,id);
//    }

    @Override
    public boolean canAction() {
        return true;
    }

    @Override
    protected TeamCreateAdapter provideAdapter() {
        header = getLayoutInflater().inflate(R.layout.activity_team_create_header, null);
        return new TeamCreateAdapter(this, presenter, header);
    }

    String id = "";

    public void getFriends() {
//        Map map = new HashMap();
//        map.put("key", Const.KEY);
//        map.put("uid",Const.UID);
//        map.put("function","GetFriendForKeyValue");
//        map.put("userid",new UserUtil(getContext()).getUserId());
//        String json = StringUtil.toJson(map);
//        try {
//            String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
        //  presenter.GetFriendForKeyValue(data);
//        }catch (Exception ex){
//        }
        id = getIntent().getBundleExtra("data").getString("id");
        Map map = new HashMap();
        map.put("function", "" + "FriendList");
        map.put("userid", "" + new UserUtil(getContext()).getUserId2());
        showDialog();
        NR.posts("WebService/UserService.asmx/Entry", map, new StringCallback() {

            @Override
            public void onError(Request request, Exception e) {
                dismissDialog();
                //  LogUtils.i("xxxxx222  :", "" +e);  //输出测试
                IsZH.getToast(TeamCreateActivity.this, "服务器错误");  //吐司
            }

            @Override
            public void onResponse(String s) {
                dismissDialog();
                ArrayList<Friend> list = new ArrayList<>();
                // LogUtils.i("xxxxx", "" +s);  //输出测试
                if (NRUtils.getYse(TeamCreateActivity.this, s)) {
                    //  IsZH.getToast(TeamCreateActivity.this, "成功");  //吐司
                    List<MyHYLB> mye = NRUtils.getDataList(s, MyHYLB.class);
                    //  LogUtils.i("xxxxx2", "" +mye.size());  //输出测试
                    int i = 0;
                    for (MyHYLB friendMap1 : mye) {
                        i = i + 1;
                        Friend friend = new Friend();
                        friend.NicName = friendMap1.getNickName();
                        friend.PhotoPath = friendMap1.getHeadUrl();
                        friend.MemnerId2 = friendMap1.getFriendId();
                        friend.MemnerId = i;
                        //     friend.Id = friendMap1.getFriendId();
                        list.add(friend);
                    }
                    //finish();
                    successFound(list);
                    refresh(false);
                }
            }

        });
    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        indexview.setTipTv(tv_recyclerindexview_tip);
        getFriends();
        tvAction.setText("下一步");
        f_search = (EditText) header.findViewById(R.id.f_search);
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
        f_search.setOnEditorActionListener(this);

//        f_team.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(TeamActivity.class);
//            }
//        });
    }

    @Override
    public TeamCreatePresenter createPresenter() {
        return new TeamCreatePresenter();
    }

    @Override
    public void onItemClick(View view, int pos, Friend item) {
        item.isSelected = item.isSelected ? false : true;

        if (item.isSelected) {
            friends.add(item);
            //    item.isSelected=true;
        } else {
            friends.remove(item);
            //  item.isSelected=false;
        }
        adapter.notifyDataSetChanged();

        bindUserAdapter.data.clear();
//        bindUserAdapter.data.addAll(friends);

        bindUserAdapter.notifyDataSetChanged();
        //  LogUtils.i("xxxxx", "" + item.isSelected +"--"+ item.NicName +"--"+ friends.size() );  //输出测试
    }

    @OnClick(R.id.tv_all)
    protected void selectAll(View v) {
        TextView textView = (TextView) v;
        String all = textView.getText().toString();
        if ("全选".equals(all)) {
            textView.setText("取消");
            friends.clear();
            for (Friend item : adapter.data) {
                item.isSelected = true;
                friends.add(item);
            }
            adapter.notifyDataSetChanged();

            bindUserAdapter.data.clear();
//            bindUserAdapter.data.addAll(friends);

            bindUserAdapter.notifyDataSetChanged();
        } else {
            textView.setText("全选");
            friends.clear();
            for (Friend item : adapter.data) {
                item.isSelected = false;
            }
            adapter.notifyDataSetChanged();

            bindUserAdapter.data.clear();
            bindUserAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void successFound(ArrayList<Friend> list) {
        PinyinFriendComparator mPinyinComparator = new PinyinFriendComparator();
        CharacterParser mCharacterParser = CharacterParser.getInstance();

        for (Friend friend : list) {
            if (StringUtil.isEmpty(friend.RemarkName)) {
                friend.topc = mCharacterParser.getSelling(friend.NicName).substring(0, 1).toUpperCase().matches("[A-Z]") ? mCharacterParser.getSelling(friend.NicName).substring(0, 1).toUpperCase() : "#";
            } else {
                friend.topc = mCharacterParser.getSelling(friend.RemarkName).substring(0, 1).toUpperCase().matches("[A-Z]") ? mCharacterParser.getSelling(friend.RemarkName).substring(0, 1).toUpperCase() : "#";
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
    public void success(Team team) {
        dismissDialog();
        RongIM.getInstance().startGroupChat(this, team.TeamId + "", "未命名");
        finishActivity();
    }

    @Override
    protected CharSequence provideTitle() {
        return "发起群聊";
    }

    private ArrayList<Friend> searchFriends = new ArrayList<>();

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            String ear = f_search.getText().toString();
            if (StringUtil.isEmpty(ear)) {
                toast("搜索不能为空！");
            } else {
                hideSoftMethod(f_search);
                searchFriends.clear();
                page = 1;
                for (Friend friend : presenter.getList()) {
                    if (friend.NicName.contains(ear)) {
                        searchFriends.add(friend);
                    }
                }
                successFound(searchFriends);
            }
            return true;
        }
        return false;
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
