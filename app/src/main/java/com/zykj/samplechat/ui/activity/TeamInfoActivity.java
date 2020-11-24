package com.zykj.samplechat.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rey.material.app.Dialog;
import com.rey.material.widget.Button;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMManager;
import com.yancy.imageselector.ImageConfig;
import com.yancy.imageselector.ImageSelector;
import com.yancy.imageselector.ImageSelectorActivity;
import com.zykj.samplechat.R;
import com.zykj.samplechat.model.Friend;
import com.zykj.samplechat.model.QuanJu;
import com.zykj.samplechat.model.Team;
import com.zykj.samplechat.model.TeamBean;
import com.zykj.samplechat.model.qunxxx;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.network.HttpUtils;
import com.zykj.samplechat.network.SubscriberRes;
import com.zykj.samplechat.presenter.TeamInfoPresenter;
import com.zykj.samplechat.ui.activity.base.ToolBarActivity;
import com.zykj.samplechat.ui.adapter.BindTeamUserAdapter;
import com.zykj.samplechat.ui.view.TeamInfoView;
import com.zykj.samplechat.ui.widget.SwitchButton;
import com.zykj.samplechat.utils.AESOperator;
import com.zykj.samplechat.utils.Bun;
import com.zykj.samplechat.utils.Encoder;
import com.zykj.samplechat.utils.GlideLoader;
import com.zykj.samplechat.utils.StringUtil;
import com.zykj.samplechat.utils.UserUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imkit.widget.AsyncImageView;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

/**
 * Created by csh
 * Created date 2016/12/19.
 * Description 创建群组
 */
public class TeamInfoActivity extends ToolBarActivity<TeamInfoPresenter>
        implements TeamInfoView,CompoundButton.OnCheckedChangeListener {

    public String id2;
    public String Tid;
    private Team team;
    private BindTeamUserAdapter bindTeamUserAdapter;
    private ArrayList<Friend> friends;
    private String permissions = "0";
    private String BuilderId = "0";
    Dialog dialog;
    public boolean isManger;

    @Bind(R.id.teamname)TextView teamname;
    @Bind(R.id.oi_team)LinearLayout oi_team;
    @Bind(R.id.f_team)LinearLayout f_team;
    @Bind(R.id.recycler_showuser)RecyclerView recycler_showuser;
    @Bind(R.id.f_name)TextView f_name;
    @Bind(R.id.sub)Button sub;
    @Bind(R.id.add)Button add;
    @Bind(R.id.ls_btn)Button ls_btn;
    @Bind(R.id.sw_group_notfaction)SwitchButton switchButton;
    @Bind(R.id.iv_group)AsyncImageView iv_group;

    @OnClick(R.id.ls_btn)
    public void ls_btn(){
        if(BuilderId.equals(new UserUtil(TeamInfoActivity.this).getUserId2())){
            dialog = new Dialog(getContext()).title("确定解散该群？").positiveAction("确定").positiveActionClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Map<String,Object> map = new HashMap<>();
                    map.put("key", Const.KEY);
                    map.put("uid", Const.UID);
                    map.put("function", "DismissTeam");
                    map.put("userid", new UserUtil(TeamInfoActivity.this).getUserId());
                    map.put("teamid", id2);
                    String json = StringUtil.toJson(map);
                    try {
                        String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
                        presenter.DismissTeam(data);
                    } catch (Exception e) {
                    }
                    dialog.dismiss();
                }
            }).backgroundColor(Color.parseColor("#FFFFFF")).titleColor(Color.parseColor("#292A2F")).cancelable(true);
            dialog.show();
        }else {
            dialog = new Dialog(getContext()).title("确定退出该群？").positiveAction("确定").positiveActionClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String mid = "";
                    String cid=UserUtil.getUser().getId();
                    for (Friend friend : friends){
//                        if(friend.MemnerId.equals(cid)){
//                            mid = friend.Id;
//                        }
                    }
                    presenter.TiChu(mid+"",id2);

                }
            }).backgroundColor(Color.parseColor("#FFFFFF")).titleColor(Color.parseColor("#292A2F")).cancelable(true);
            dialog.show();
        }
    }

    @OnClick(R.id.sub)
    public void sub(){
        startActivity(TeamTichuActivity.class,new Bun().putSerializable("fs",friends).putString("id",id2).ok());
    }

    @OnClick(R.id.f_team)
    public void f_team(){//全部群成员
//        startActivity(TeamFriendsActivity.class,new Bun().putSerializable("fs",friends).ok());
    }

    @OnClick(R.id.add)
    public void add(){//邀请
        startActivity(TeamYaoqingActivity.class,new Bun().putSerializable("fs",friends).putString("id",id2).ok());
//        startActivity(TeamCreateActivity.class,new Bun().putString("id",id2).ok());
    }

    @OnClick(R.id.oi_team)
    public void oi_team(){
//        if(permissions.equals("1"))
//            startActivity(TeamNameActivity.class,new Bun().putInt("id",id2).putString("teamName",team.Name.equals("")?"未命名":team.Name).putString("url",team.ImagePath.equals("")?"":team.ImagePath).ok());
        //else
            //toast("您不是群主，不能修改群名称");
    }

    @OnClick(R.id.ll_avatar)
    public void avatar(){
        if(permissions.equals("1")){
            ImageConfig imageConfig
                    = new ImageConfig.Builder(new GlideLoader())
                    .steepToolBarColor(ContextCompat.getColor(this,R.color.colorPrimary))
                    .titleBgColor(ContextCompat.getColor(this,R.color.colorPrimary))
                    .titleSubmitTextColor(ContextCompat.getColor(this,R.color.white))
                    .titleTextColor(ContextCompat.getColor(this,R.color.white))
                    // (截图默认配置：关闭    比例 1：1    输出分辨率  500*500)
                    .crop()
                    // 开启单选   （默认为多选）
                    .singleSelect()
                    // 开启拍照功能 （默认关闭）
                    .showCamera()
                    // 拍照后存放的图片路径（默认 /temp/picture） （会自动创建）
                    .filePath("/ImageSelector/Pictures")
                    .build();
            ImageSelector.open(this, imageConfig);   // 开启图片选择器
        }
    }

    @OnClick({R.id.ll_notice,R.id.tv_message,R.id.tv_complain,R.id.tv_balance,R.id.tv_trend,R.id.tv_clear,R.id.img_back})
    public void message(View v){
        if(v.getId() == R.id.ll_notice){//群公告
            startActivity(TeamNoticeActivity.class,new Bun()
                    .putString("id",id2)
                    .putString("action", "群公告")
                    .putBoolean("flag", permissions.equals("1"))
                    .putString("notice", mye.getTeamInfoData().getTeamAnnouncement()).ok());
        }else if(v.getId() == R.id.tv_message){//查找聊天记录
//            Conversation conversation = new Conversation();
//            conversation.setTargetId(id+"");
//            conversation.setConversationType(Conversation.ConversationType.GROUP);
//            conversation.setPortraitUrl("PortraitUtl");//群组头像
//            startActivity(new Intent(this,SearchChattingActivity.class).putExtra("conversation", conversation));
        }else if(v.getId() == R.id.tv_complain){//投诉
//            startActivity(TeamNoticeActivity.class,new Bun()
//                    .putInt("id",id)
//                    .putString("action", "投诉")
//                    .putBoolean("flag", true).ok());
        }else if(v.getId() == R.id.tv_balance){//查余额
//            TextMessage message = TextMessage.obtain("查余额");
//            RongIM.getInstance().insertMessage(Conversation.ConversationType.GROUP,id+"",
//                    new UserUtil(getContext()).getUserId()+"",message,new RongIMClient.ResultCallback<Message>(){
//                @Override
//                public void onSuccess(Message message) {
//                    toast("已发送");
//                }
//                @Override
//                public void onError(RongIMClient.ErrorCode errorCode) {
//                    toast("查询失败");
//                }
//            });
//            TextMessage textMessage = TextMessage.obtain("查余额");
//            Message message = Message.obtain(id+"",Conversation.ConversationType.GROUP,textMessage);
//            RongIM.getInstance().sendMessage(message, null, null, new IRongCallback.ISendMessageCallback(){
//                @Override
//                public void onAttached(Message message) {}
//                @Override
//                public void onSuccess(Message message) {
//                    toast("已发送");
//                }
//                @Override
//                public void onError(Message message, RongIMClient.ErrorCode errorCode) {
//                    toast("查询失败");
//                }
//            });
        }else if(v.getId() == R.id.tv_trend){//查走势
//            TextMessage message = TextMessage.obtain("查走势");
//            RongIM.getInstance().insertMessage(Conversation.ConversationType.GROUP,id+"",
//                    new UserUtil(getContext()).getUserId()+"",message,new RongIMClient.ResultCallback<Message>(){
//                @Override
//                public void onSuccess(Message message) {
//                    toast("已发送");
//                }
//                @Override
//                public void onError(RongIMClient.ErrorCode errorCode) {
//                    toast("查询失败");
//                }
//            });
        }else if(v.getId() == R.id.tv_clear){//清空聊天记录
            RongIM.getInstance().clearMessages(Conversation.ConversationType.GROUP,id2+"",new RongIMClient.ResultCallback<Boolean>(){
                @Override
                public void onSuccess(Boolean flag) {
                    toast("清除成功！");
                }
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    toast("清除失败！");
                }
            });
        }else if(v.getId() == R.id.img_back){//返回
          finish();
        }
    }

    @Override
    protected CharSequence provideTitle() {
        return "群组信息";
    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        Tid =getIntent().getStringExtra("id");

       //  recycler_showuser.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recycler_showuser.setLayoutManager(new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL));
        bindTeamUserAdapter = new BindTeamUserAdapter(this);

        recycler_showuser.setAdapter(bindTeamUserAdapter);
        switchButton.setOnCheckedChangeListener(this);

        HttpUtils.GetTeam(new SubscriberRes<TeamBean>() {
            @Override
            public void onSuccess(TeamBean teamBean) {
                   id2=teamBean.Id;
                HttpUtils.GTeamUserInfo(new SubscriberRes<qunxxx.QueryBean>() {
                    @Override
                    public void onSuccess(qunxxx.QueryBean res) {
                        isManger = res.isIsTeamManager();
                        if(res.isIsTeamManager()){
                            sub.setVisibility(View.VISIBLE);
                            add.setVisibility(View.VISIBLE);
                        }else {
                            sub.setVisibility(View.GONE);
                            add.setVisibility(View.GONE);
                        }
                    }
                    @Override
                    public void completeDialog() {
                    }
                },id2);



                HttpUtils.SelectTeamMember1(new SubscriberRes<qunxxx>() {
                    @Override
                    public void onSuccess(qunxxx res) {
                        mye = res;
                        teamname.setText(mye.getTeamInfoData().getName().equals("")?"未命名":mye.getTeamInfoData().getName());
                        Glide.with(getContext()).load(Const.getbase(mye.getTeamInfoData().getImagePath())).apply(new RequestOptions().placeholder(R.drawable.userimg)).into(iv_group);
                        ArrayList<Friend> list = new ArrayList<Friend>();
                        for(qunxxx.QueryBean qb : mye.getQuery()){
                            Friend f = new Friend();
                            f.RemarkName = qb.getNickName();
                            f.PhotoPath = qb.getHeadUrl();
                            f.MemnerId2 = qb.getUserid();
                            f.UserCode = qb.getUserCode();
//                    RongIM.getInstance().refreshUserInfoCache(new UserInfo(f.MemnerId2 + "", f.PhotoPath, Uri.parse(Const.getbase(f.PhotoPath))));
                            list.add(f);
                            if(!QuanJu.getQuanJu().getlei(id2)){//只有非中雷红包才走这里  暂时无用
                                if(qb.getUserid().equals(new UserUtil(getContext()).getUserId2())){
                                    //  LogUtils.i("xxxxx", "" +qb.getNickName()+"--"+qb.isIsTeamManager());  //输出测试
                                    if(!qb.isIsTeamManager()){
                                        other(1, String.valueOf(1));
                                        other(2, String.valueOf(qb.getUserid()));
                                    }
                                }
                                ls_btn.setVisibility(View.VISIBLE);
                            }else{
                                ls_btn.setVisibility(View.GONE);
                            }
                        }
                        friends = list;
                        bindTeamUserAdapter.data.clear();
                        bindTeamUserAdapter.data.addAll(list);
                        bindTeamUserAdapter.notifyDataSetChanged();
                    }
                    @Override
                    public void completeDialog() {
                    }
                },id2);

                HttpUtils.SelectTeamMemberForAnZhuo(new SubscriberRes<qunxxx>() {
                    @Override
                    public void onSuccess(final qunxxx res) {
                        f_name.setText("全部群成员("+res.getQuery().size()+")");
                    }
                    @Override
                    public void completeDialog() {
                    }
                },id2);
            }
            @Override
            public void completeDialog() {
                dismissDialog();
            }
        },Tid,"0");


    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_team_info;
    }

    @Override
    public void initListeners() {
//        RongIM.getInstance().getConversationNotificationStatus(Conversation.ConversationType.GROUP,id2+"",
//                new RongIMClient.ResultCallback<Conversation.ConversationNotificationStatus>() {
//            @Override
//            public void onSuccess(Conversation.ConversationNotificationStatus conversationNotificationStatus) {
//                if (conversationNotificationStatus == Conversation.ConversationNotificationStatus.DO_NOT_DISTURB) {
//                    switchButton.setChecked(true);
//                } else {
//                    switchButton.setChecked(false);
//                }
//            }
//            @Override
//            public void onError(RongIMClient.ErrorCode errorCode) {}
//        });
    }

    @Override
    public TeamInfoPresenter createPresenter() {
        return new TeamInfoPresenter();
    }
    qunxxx mye;//群信息
    @Override
    protected void onResume() {//初始化群组信息
        super.onResume();
//        HttpUtils.SelectTeamMember1(new SubscriberRes<qunxxx>() {
//            @Override
//            public void onSuccess(qunxxx res) {
//                mye = res;
//                teamname.setText(mye.getTeamInfoData().getName().equals("")?"未命名":mye.getTeamInfoData().getName());
//                Glide.with(getContext()).load(Const.getbase(mye.getTeamInfoData().getImagePath())).apply(new RequestOptions().placeholder(R.drawable.userimg)).into(iv_group);
//                ArrayList<Friend> list = new ArrayList<Friend>();
//                for(qunxxx.QueryBean qb : mye.getQuery()){
//                    Friend f = new Friend();
//                    f.RemarkName = qb.getNickName();
//                    f.PhotoPath = qb.getHeadUrl();
//                    f.MemnerId2 = qb.getUserid();
////                    RongIM.getInstance().refreshUserInfoCache(new UserInfo(f.MemnerId2 + "", f.PhotoPath, Uri.parse(Const.getbase(f.PhotoPath))));
//                    list.add(f);
//                    if(!QuanJu.getQuanJu().getlei(id2)){//只有非中雷红包才走这里  暂时无用
//                        if(qb.getUserid().equals(new UserUtil(getContext()).getUserId2())){
//                            //  LogUtils.i("xxxxx", "" +qb.getNickName()+"--"+qb.isIsTeamManager());  //输出测试
//                            if(!qb.isIsTeamManager()){
//                                other(1, String.valueOf(1));
//                                other(2, String.valueOf(qb.getUserid()));
//                            }
//                        }
//                        ls_btn.setVisibility(View.VISIBLE);
//                    }else{
//                        ls_btn.setVisibility(View.GONE);
//                    }
//                }
//                friends = list;
//                bindTeamUserAdapter.data.clear();
//                bindTeamUserAdapter.data.addAll(list);
//                bindTeamUserAdapter.notifyDataSetChanged();
//            }
//            @Override
//            public void completeDialog() {
//            }
//        },id2);
//
//        HttpUtils.SelectTeamMemberForAnZhuo(new SubscriberRes<qunxxx>() {
//            @Override
//            public void onSuccess(final qunxxx res) {
//                f_name.setText("全部群成员("+res.getQuery().size()+")");
//            }
//            @Override
//            public void completeDialog() {
//            }
//        },id2);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImageSelector.IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            // Get Image Path List
            List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
            showDialog();
            String fileStream = Encoder.getEnocodeStr(pathList.get(0));
            Map map = new HashMap();
            map.put("key", Const.KEY);
            map.put("uid",Const.UID);
            map.put("function","UpLoadImage");
            map.put("fileName",pathList.get(0));
            map.put("filestream",fileStream);
            String json = StringUtil.toJson(map);
            try {
                String args = AESOperator.getInstance().encrypt(json.length() + "&" + json);
                presenter.UpLoadImage(args);
            }catch (Exception e){}
        }
    }

    @Override
    public void successTeam(Team team) {
        this.team = team;
        teamname.setText(team.Name.equals("")?"未命名":team.Name);
        Glide.with(getContext()).load(Const.getbase(team.ImagePath)).apply(new RequestOptions().placeholder(R.drawable.userimg)).into(iv_group);
//        presenter.SelectTeamMember(id2);
    }

    @Override
    public void successFriends(ArrayList<Friend> list) {
        friends = list;
        String id = UserUtil.getUser().getId();
        boolean isIn = false;
        for (Friend friend : list){
//            RongIM.getInstance().refreshUserInfoCache(new UserInfo(friend.MemnerId2 + "", friend.PhotoPath, Uri.parse(Const.getbase(friend.PhotoPath))));
            if(id.equals(friend.MemnerId)){
                isIn = true;
                break;
            }
        }
        if(!isIn){
            toast("你已不在该群组");
//            RongIM.getInstance().removeConversation(Conversation.ConversationType.GROUP, id+"", null);
            TIMManager.getInstance().deleteConversation(TIMConversationType.Group, Tid);
            finish();
        }
        bindTeamUserAdapter.data.clear();
        bindTeamUserAdapter.data.addAll(list);
        bindTeamUserAdapter.notifyDataSetChanged();
        f_name.setText("全部群成员("+bindTeamUserAdapter.data.size()+")");
    }

    @Override
    public void success() {
//        RongIM.getInstance().removeConversation(Conversation.ConversationType.GROUP, id+"", null);
//        toast("退出成功");
//        ActivityUtil.addActivity(this);
//        ActivityUtil.finishActivitys();
    }

    @Override
    public void successSendYue() {

    }

    @Override
    public void successJiesan() {
//        RongIM.getInstance().removeConversation(Conversation.ConversationType.GROUP, id+"", null);
//        toast("解散成功");
//        ActivityUtil.addActivity(this);
//        ActivityUtil.finishActivitys();
    }

    @Override
    public void error() {
//        RongIM.getInstance().removeConversation(Conversation.ConversationType.GROUP, id+"", null);
//        toast("该群组已被解散");
//        ActivityUtil.addActivity(this);
//        ActivityUtil.finishActivitys();
    }

    @Override
    public void other(int type, String data) {
        if(type == 0){//修改头像回调走的这里
            Glide.with(getContext())
                    .load(Const.getbase(data))
                    .apply(new RequestOptions().placeholder(R.drawable.groupcreate))
                    .into(iv_group);
        }else if(type == 1){//管理员  permissions=1 可以修改头像等功能
            permissions = data;
            sub.setVisibility(data.equals("1")?View.VISIBLE:View.GONE);
            add.setVisibility(data.equals("1")?View.VISIBLE:View.GONE);
        }else if(type == 2){//BuilderId 创建者
            BuilderId = data;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//        Conversation.ConversationNotificationStatus cns;
//        if (isChecked) {
//            cns = Conversation.ConversationNotificationStatus.DO_NOT_DISTURB;
//        } else {
//            cns = Conversation.ConversationNotificationStatus.NOTIFY;
//        }
//        RongIM.getInstance().setConversationNotificationStatus(Conversation.ConversationType.GROUP,id2,cns,
//                new RongIMClient.ResultCallback<Conversation.ConversationNotificationStatus>() {
//            @Override
//            public void onSuccess(Conversation.ConversationNotificationStatus conversationNotificationStatus) {}
//            @Override
//            public void onError(RongIMClient.ErrorCode errorCode) {
//                toast("设置失败");
//            }
//        });
    }
}