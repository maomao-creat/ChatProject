package com.zykj.samplechat.presenter;

import com.zykj.samplechat.model.Friend;
import com.zykj.samplechat.model.ImageTemp;
import com.zykj.samplechat.model.Res;
import com.zykj.samplechat.model.Team;
import com.zykj.samplechat.network.BaseEntityRes;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.network.HttpUtils;
import com.zykj.samplechat.network.Net;
import com.zykj.samplechat.network.SubscriberRes;
import com.zykj.samplechat.presenter.base.BasePresenterImp;
import com.zykj.samplechat.ui.view.TeamInfoView;
import java.util.ArrayList;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ninos on 2016/7/14.
 */
public class TeamInfoPresenter extends BasePresenterImp<TeamInfoView> {
    public void UpLoadImage(String data){
        Subscription subscription = Net.getService()
                .UpLoadImage(data)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Res<ImageTemp>>() {
                    @Override
                    public void onNext(Res<ImageTemp> res) {
                        if (res.code == Const.OK) {
                            view.dismissDialog();
                            view.other(0, res.data.BigImagePath);
                        }
                    }
                    @Override
                    public void onCompleted() {}
                    @Override
                    public void onError(Throwable e) {}
                });
        addSubscription(subscription);
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

    public void SelectTeamMemberForAnZhuo(String teamid){
        HttpUtils.SelectTeamMember(new SubscriberRes<ArrayList<Friend>>() {
            @Override
            public void onNext(BaseEntityRes<ArrayList<Friend>> res) {
                super.onNext(res);
                view.successFriends(res.data);
                view.other(1, String.valueOf(res.data4));
                view.other(2, String.valueOf(res.BuilderId));
            }

            @Override
            public void onSuccess(ArrayList<Friend> mye) {

            }

            @Override
            public void completeDialog() {
            }
        },teamid);
    }

    public void TiChu(String dlist,String teamid){
        HttpUtils.TiChu(new SubscriberRes<String>() {
            @Override
            public void onSuccess(String userBean) {
                view.success();
            }
            @Override
            public void completeDialog() {

            }
        },dlist,teamid);
    }

    public void SendYue(String data){
        Subscription subscription = Net.getService()
                .SendYue(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Res>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Res res) {
                        if (res.code== Const.OK){
                            view.successSendYue();
                        }else {
                        }
                    }
                });

        addSubscription(subscription);
    }

    public void DismissTeam(String data){
        Subscription subscription = Net.getService()
                .DismissTeam(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Res>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Res res) {
                        if (res.code== Const.OK){
                            view.successJiesan();
                        }else {
                        }
                    }
                });
        addSubscription(subscription);
    }
}
