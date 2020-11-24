package com.zykj.samplechat.presenter;

import com.zykj.samplechat.model.Friend;
import com.zykj.samplechat.model.MultiRes;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.network.Net;
import com.zykj.samplechat.presenter.base.RefreshAndLoadMorePresenter;
import com.zykj.samplechat.ui.view.TeamFriendsView;

import java.util.ArrayList;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ninos on 2016/7/14.
 */
public class TeamFriendsPresenter extends RefreshAndLoadMorePresenter<TeamFriendsView> {

    @Override
    public void getData(final int page, final int count) {

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
                            view.success(res.data);
                        }else {
                        }
                    }
                });

        addSubscription(subscription);
    }


}
