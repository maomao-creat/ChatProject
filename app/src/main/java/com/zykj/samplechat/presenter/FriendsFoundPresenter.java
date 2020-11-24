package com.zykj.samplechat.presenter;

import com.zykj.samplechat.model.Res;
import com.zykj.samplechat.model.sshyBean;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.network.Net;
import com.zykj.samplechat.presenter.base.BasePresenterImp;
import com.zykj.samplechat.ui.view.FriendsFoundView;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ninos on 2016/7/7.
 */
public class FriendsFoundPresenter extends BasePresenterImp<FriendsFoundView> {
    public void SearchFriendByPhone(String data){
        Subscription subscription = Net.getService()
                .SearchFriendByPhone(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Res<sshyBean>>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.errorFound();
                    }

                    @Override
                    public void onNext(Res<sshyBean> res) {
                        if (res.code== Const.OK){
                            view.successFound(res.data);
                        }else {
                            view.errorFound();
                        }
                    }
                });

        addSubscription(subscription);
    }
}
