package com.zykj.samplechat.presenter;

import com.zykj.samplechat.model.Res;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.network.Net;
import com.zykj.samplechat.presenter.base.BasePresenterImp;
import com.zykj.samplechat.ui.view.EntityView;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 徐学坤 on 2018/2/1.
 */
public class ZhuanQianPresenter extends BasePresenterImp<EntityView<Object>> {
    public void getRecommendCode(String d) {
        Subscription subscription = Net.getService()
                .getRecommendCode(d)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Res<Object>>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.dismissDialog();
                        view.error();
                    }

                    @Override
                    public void onNext(Res<Object> res) {
                        view.dismissDialog();
                        if (res.code == Const.OK) {
                            view.model(res.data);
                        } else {
                            view.error();
                        }
                    }
                });

        addSubscription(subscription);
    }
}
