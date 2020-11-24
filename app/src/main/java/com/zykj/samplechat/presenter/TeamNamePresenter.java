package com.zykj.samplechat.presenter;

import com.zykj.samplechat.model.Res;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.network.Net;
import com.zykj.samplechat.presenter.base.BasePresenterImp;
import com.zykj.samplechat.ui.view.TeamNameView;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ninos on 2016/7/14.
 */
public class TeamNamePresenter extends BasePresenterImp<TeamNameView> {
    public void EditTeam(String data){
        Subscription subscription = Net.getService()
                .EditTeam(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Res>() {
                    @Override
                    public void onCompleted() {}
                    @Override
                    public void onError(Throwable e) {
                        view.errorUpload();
                    }
                    @Override
                    public void onNext(Res res) {
                        if (res.code== Const.OK){
                            view.successUpload();
                        }else {
                            view.errorUpload();
                        }
                    }
                });
        addSubscription(subscription);
    }

    public void ReportUser(String data){
        Subscription subscription = Net.getService()
                .ReportUser(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Res>() {
                    @Override
                    public void onCompleted() {}
                    @Override
                    public void onError(Throwable e) {
                        view.errorUpload();
                    }
                    @Override
                    public void onNext(Res res) {
                        if (res.code== Const.OK){
                            view.successUpload();
                        }else {
                            view.errorUpload();
                        }
                    }
                });
        addSubscription(subscription);
    }
}