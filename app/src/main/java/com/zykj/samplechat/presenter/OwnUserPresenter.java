package com.zykj.samplechat.presenter;

import com.zykj.samplechat.model.Res;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.network.Net;
import com.zykj.samplechat.presenter.base.BasePresenterImp;
import com.zykj.samplechat.ui.view.OwnUserView;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ninos on 2016/7/6.
 */
public class OwnUserPresenter extends BasePresenterImp<OwnUserView>{

    public void ChangeInfor(String data){

        Subscription subscription = Net.getService()
                .ChangeInfor(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Res>() {

                    @Override
                    public void onCompleted() {
                    }

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
