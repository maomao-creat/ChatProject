package com.zykj.samplechat.presenter;

import com.zykj.samplechat.model.Res;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.network.Net;
import com.zykj.samplechat.presenter.base.BasePresenterImp;
import com.zykj.samplechat.ui.view.OwnYJView;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ninos on 2016/7/27.
 */
public class OwnYJPresenter extends BasePresenterImp<OwnYJView> {
    public void putInfo(String data){
        Subscription subscription = Net.getService()
                .UpdateSuggestion(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Res>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.error();
                    }

                    @Override
                    public void onNext(Res res) {
                        if (res.code== Const.OK){
                            view.success();
                        }else {
                            view.error();
                        }
                    }
                });

        addSubscription(subscription);
    }
}
