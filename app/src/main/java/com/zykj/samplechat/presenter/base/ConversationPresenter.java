package com.zykj.samplechat.presenter.base;

import com.zykj.samplechat.model.Friend;
import com.zykj.samplechat.model.MultiRes;
import com.zykj.samplechat.model.Res;
import com.zykj.samplechat.model.Team;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.network.Net;
import com.zykj.samplechat.ui.view.TeamInfoView;
import com.zykj.samplechat.utils.AESOperator;
import com.zykj.samplechat.utils.StringUtil;
import com.zykj.samplechat.utils.UserUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by csh
 * Created date 2016/12/16.
 * Description 群公告
 */

public class ConversationPresenter extends BasePresenterImp<TeamInfoView> {
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
                        view.error();
                    }
                    @Override
                    public void onNext(Res<Team> res) {
                        if (res.code== Const.OK){
                            view.successTeam(res.data);
                        }else {
                        }
                    }
                });
        addSubscription(subscription);
    }

    public void permission(String data){
        Subscription subscription = Net.getService()
                .permission(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Res<String>>() {
                    @Override
                    public void onCompleted() {}
                    @Override
                    public void onError(Throwable e) {}
                    @Override
                    public void onNext(Res<String> res) {
                        if (res.code== Const.OK){
                            view.other(1, res.data);
                        }else {
                            view.error();
                        }
                    }
                });
        addSubscription(subscription);
    }

    public void SaveMessage(String teamId, String msg){
        Map<String,Object> map = new HashMap<>();
        map.put("key", Const.KEY);
        map.put("uid",Const.UID);
        map.put("function","SaveMessage");
        map.put("userid",new UserUtil(view.getContext()).getUserId());
        map.put("teamid",teamId);
        map.put("content",msg);
        String json = StringUtil.toJson(map);
        try {
            String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
            Subscription subscription = Net.getService()
                    .SaveMessage(data)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Res>() {
                        @Override
                        public void onCompleted() {}
                        @Override
                        public void onError(Throwable e) {

                        }
                        @Override
                        public void onNext(Res res) {

                        }
                    });
            addSubscription(subscription);
        }catch (Exception ex){
        }
    }

    public void SelectTeamMember(String data){
        Subscription subscription = Net.getService()
                .SelectTeamMember(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MultiRes<ArrayList<Friend>>>() {
                    @Override
                    public void onCompleted() {}
                    @Override
                    public void onError(Throwable e) {}
                    @Override
                    public void onNext(MultiRes<ArrayList<Friend>> res) {
                        if (res.code== Const.OK){
                            view.successFriends(res.data);
                        }else {
                            view.error();
                        }
                    }
                });
        addSubscription(subscription);
    }
}