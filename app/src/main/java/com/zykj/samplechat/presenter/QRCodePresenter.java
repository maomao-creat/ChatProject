package com.zykj.samplechat.presenter;

import com.zykj.samplechat.model.Picture;
import com.zykj.samplechat.model.Res;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.network.Net;
import com.zykj.samplechat.presenter.base.BasePresenterImp;
import com.zykj.samplechat.ui.view.QRCodeView;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by csh
 * Created date 2016/12/23.
 * Description 二维码
 */

public class QRCodePresenter extends BasePresenterImp<QRCodeView>{
    public void GetPicture(String data){
        Subscription subscription = Net.getService()
                .GetPicture(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Res<Picture>>() {
                    @Override
                    public void onCompleted() {}
                    @Override
                    public void onError(Throwable e) {
                        view.error();
                    }
                    @Override
                    public void onNext(Res<Picture> res) {
                        if (res.code== Const.OK){
                            view.success(res.data);
                        }
                    }
                });
        addSubscription(subscription);
    }
}