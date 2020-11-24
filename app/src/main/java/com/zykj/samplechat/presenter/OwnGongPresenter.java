package com.zykj.samplechat.presenter;

import com.zykj.samplechat.model.Gonggao;
import com.zykj.samplechat.model.Res;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.network.Net;
import com.zykj.samplechat.presenter.base.RefreshAndLoadMorePresenter;
import com.zykj.samplechat.ui.view.OwnGongView;

import java.util.ArrayList;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ninos on 2016/7/27.
 */
public class OwnGongPresenter extends RefreshAndLoadMorePresenter<OwnGongView> {
    @Override
    public void getData(final int page, final int count) {
        String data = view.getJsonData();
        Subscription subscription = Net.getService()
                .SelectGongGaoList(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Res<ArrayList<Gonggao>>>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Res<ArrayList<Gonggao>> res) {
                        if (res.code== Const.OK){
                            view.success(res.data);
                            setDataStatus(page, count, res);
                        }else {
                        }
                    }
                });

        addSubscription(subscription);
    }
}
