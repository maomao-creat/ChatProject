package com.zykj.samplechat.presenter;

import com.zykj.samplechat.model.Res;
import com.zykj.samplechat.model.Team;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.network.Net;
import com.zykj.samplechat.presenter.base.RefreshAndLoadMorePresenter;
import com.zykj.samplechat.ui.view.TeamView;

import java.util.ArrayList;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ninos on 2016/7/14.
 */
public class TeamPresenter extends RefreshAndLoadMorePresenter<TeamView> {

    @Override
    public void getData(final int page, final int count) {

    }

    public void SelectAllTeamByUserId(String data){
        Subscription subscription = Net.getService()
                .SelectAllTeamByUserId(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Res<ArrayList<Team>>>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Res<ArrayList<Team>> res) {
                        if (res.code== Const.OK){
                            view.success(res.data);
                        }else {
                        }
                    }
                });

        addSubscription(subscription);
    }

}
