package com.zykj.samplechat.presenter;

import com.zykj.samplechat.model.Res;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.network.Net;
import com.zykj.samplechat.presenter.base.BasePresenterImp;
import com.zykj.samplechat.ui.view.OwnWanJiaView;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ninos on 2016/7/6.
 */
public class WanJiadPresenter extends BasePresenterImp<OwnWanJiaView>{

    public void WanJia(String data){

        Subscription subscription = Net.getService()
                .WanJia(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Res<WanJiasBean>>() {

                    @Override
                    public void onCompleted() {
                        view.errorUpload();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.errorUpload();
                    }

                    @Override
                    public void onNext(Res<WanJiasBean> res) {
                        if (res.code== Const.OK){
                            WanJiasBean wjb= res.data;
                            view.successUpload(wjb);
                        }
                    }
                });

        addSubscription(subscription);
    }
}
