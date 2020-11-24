package com.zykj.samplechat.presenter;

import com.zykj.samplechat.model.ChaiHongbao;
import com.zykj.samplechat.model.Res;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.network.Net;
import com.zykj.samplechat.presenter.base.BasePresenterImp;
import com.zykj.samplechat.ui.view.GetHongbaoView;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ninos on 2017/8/23.
 */

public class GetHongbaoPresenter extends BasePresenterImp<GetHongbaoView> {
    public void OpenSingleRedPacket(String data){
        Subscription subscription = Net.getService()
                .OpenSingleRedPacket(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Res<ChaiHongbao>>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.dismissDialog();
                    }

                    @Override
                    public void onNext(Res<ChaiHongbao> res) {
                        if (res.code== Const.OK){
                            view.success(res.data);
                        }else {
                            view.dismissDialog();
                        }
                    }
                });

        addSubscription(subscription);
    }
}
