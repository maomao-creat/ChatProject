package com.zykj.samplechat.presenter;

import android.net.Uri;
import android.util.Log;

import com.rey.material.app.Dialog;
import com.zykj.samplechat.model.Friend;
import com.zykj.samplechat.model.FriendMap;
import com.zykj.samplechat.model.Gonggao;
import com.zykj.samplechat.model.Res;
import com.zykj.samplechat.model.Team;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.network.Net;
import com.zykj.samplechat.presenter.base.RefreshAndLoadMorePresenter;
import com.zykj.samplechat.ui.view.TeamView;
import com.zykj.samplechat.utils.AESOperator;
import com.zykj.samplechat.utils.StringUtil;
import com.zykj.samplechat.utils.UserUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * date 2016/6/2
 * Created by csh.
 */
public class HzwPresenter extends RefreshAndLoadMorePresenter<TeamView> {

    @Override
    public void getData(final int page, final int count) {
        Map map = new HashMap();
        map.put("key", Const.KEY);
        map.put("uid", Const.UID);
        map.put("function", "GetTeamList");
        map.put("startRowIndex", (page - 1) * count);
        map.put("maximumRows", count);
        String json = StringUtil.toJson(map);
        try {
            String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
            Log.e("TAG",data);
            Subscription subscription = Net.getService()
                    .GetTeamList(data)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Res<ArrayList<Team>>>() {

                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            view.refresh(false);
                        }

                        @Override
                        public void onNext(Res<ArrayList<Team>> res) {
                            if (res.code == Const.OK) {
                                view.success(res.data);
                                setDataStatus(page, count, res);
                                view.refresh(false);
                            } else {
                                view.refresh(false);
                            }
                        }
                    });

            addSubscription(subscription);
        } catch (Exception e) {
        }
    }

    public void GetAllGonggao(String data){
        Subscription subscription = Net.getService()
                .SelectGongGaoList(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Res<ArrayList<Gonggao>>>() {
                    @Override
                    public void onCompleted() {}
                    @Override
                    public void onError(Throwable e) {}
                    @Override
                    public void onNext(Res<ArrayList<Gonggao>> res) {
                      //  LogUtils.i("xxxxx", "" +res.data);  //输出测试
                      //  LogUtils.i("xxxx2x", "" +res.data.size());  //输出测试
                        if (res.code== Const.OK){
                            if(res.data.size() > 0){
                                view.success("#"+res.data.get(0).Message+"    ");
                            }
                        }
                    }
                });
        addSubscription(subscription);
    }

    public void IsInTeam(final String teamid, final String teamName){
        view.showDialog();
        Map<String, Object> map = new HashMap<>();
        map.put("key", Const.KEY);
        map.put("uid", Const.UID);
        map.put("function", "IsInTeam");
        map.put("userid", new UserUtil(view.getContext()).getUserId());
        map.put("teamid", teamid);
        String json = StringUtil.toJson(map);
        try {
            String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
            Subscription subscription = Net.getService()
                    .IsInTeam(data)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Res<FriendMap>>() {
                        @Override
                        public void onCompleted() {}
                        @Override
                        public void onError(Throwable e) {}
                        @Override
                        public void onNext(Res<FriendMap> res) {
                            view.dismissDialog();
                            if (res.code== Const.OK){
                                if(res.data.State == 0)
                                    view.toast("您未在本群建立用户，无法发送消息，请联系本群管理员建立用户！");
                                else{
                                    RongIM.getInstance().startGroupChat(view.getContext(), teamid, teamName);//打开群聊天
                                }
                            }
                        }
                    });
            addSubscription(subscription);
        } catch (Exception e) {}
    }

    public void SelectTeam(String data){
        Subscription subscription = Net.getService()
                .SelectTeam(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Res<Team>>() {
                    @Override
                    public void onCompleted() {}
                    @Override
                    public void onError(Throwable e) {
                        view.dismissDialog();
                    }
                    @Override
                    public void onNext(Res<Team> res) {
//                        view.dismissDialog();
                        if (res.code== Const.OK){
                            RongIM.getInstance().refreshGroupInfoCache(
                                    new io.rong.imlib.model.Group(res.data.Id + "",
                                            res.data.Name,
                                            Uri.parse(Const.getbase(res.data.ImagePath ))));
                        }
                    }
                });
        addSubscription(subscription);
    }
    public void SelectMyFriend(String data){
        Subscription subscription = Net.getService()
                .SelectMyFriend(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Res<Friend>>() {
                    @Override
                    public void onCompleted() {}
                    @Override
                    public void onError(Throwable e) {}
                    @Override
                    public void onNext(Res<Friend> res) {
                        if (res.code== Const.OK){
                            RongIM.getInstance().refreshUserInfoCache(new UserInfo(res.data.Id + "", res.data.RemarkName.trim().equals("")?res.data.NicName:res.data.NicName, Uri.parse(Const.getbase( res.data.PhotoPath ))));
                        }else {
                        }
                    }
                });
        addSubscription(subscription);
    }

    public void getVersion() {
//        Subscription subscription = Net.getService()
//                .getVersion()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<Res<About>>() {
//                    @Override
//                    public void onCompleted() {
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        view.errorFound();
//                    }
//
//                    @Override
//                    public void onNext(final Res<About> res) {
//                        if (res.code != Const.OK || !"2".equals(res.data.AppVerson)) {
//                            if (dialog_phone == null) {
//                                dialog_phone = new Dialog(view.getContext()).title("有新版本，请更新后使用").positiveAction("确定").positiveActionClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        Intent intent = new Intent();
//                                        intent.setAction("android.intent.action.VIEW");
//                                        Uri content_url = Uri.parse("https://www.pgyer.com/fODe");
//                                        intent.setData(content_url);
//                                        view.getContext().startActivity(intent);
//                                        System.exit(0);
//                                    }
//                                }).backgroundColor(Color.parseColor("#ffffff")).titleColor(Color.parseColor("#292A2F"));
//                            }
//                            dialog_phone.setCanceledOnTouchOutside(false);
//                            dialog_phone.setCancelable(false);
//                            dialog_phone.show();
//                        }
//                    }
//                });
//        addSubscription(subscription);
    }

    private Dialog dialog_phone;
}