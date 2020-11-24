package com.zykj.samplechat.presenter;

import com.zykj.samplechat.model.Res;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.network.Net;
import com.zykj.samplechat.presenter.base.BasePresenterImp;
import com.zykj.samplechat.ui.view.FriendsRemarkView;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ninos on 2016/7/15.
 */
public class FriendsRemarkPresenter extends BasePresenterImp<FriendsRemarkView> {
    public void ChangeFriendRName(String data){
        Subscription subscription = Net.getService()
                .ChangeFriendRName(data)
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
                            view.success();
                        }else {
                        }
                    }
                });

        addSubscription(subscription);
    }
}
