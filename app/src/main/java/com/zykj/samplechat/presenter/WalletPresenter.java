package com.zykj.samplechat.presenter;

import com.google.gson.Gson;
import com.zykj.samplechat.model.Res;
import com.zykj.samplechat.network.Net;
import com.zykj.samplechat.presenter.base.BasePresenterImp;
import com.zykj.samplechat.ui.view.SendYuEView;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 徐学坤 on 2018/2/9.
 */
public class WalletPresenter extends BasePresenterImp<SendYuEView>  {
    public void getyue(String data) {
        Subscription subscription = Net.getService()
                .getyue(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Res>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.toast("零钱不足，请先充值！");
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Res res) {
                        try {
                            String json = new Gson().toJson(res.data);
                            String yue =  new Gson().fromJson(json, String.class);
                            view.success(yue);
                            //view.toast("零钱不足，请先充值！"+json);
//                            Hongbao hongbao = new Gson().fromJson(json, Hongbao.class);
//                            if (res.code == Const.OK) {
//                                //int hid = (int)Double.parseDouble(hongbao.Id);
//                                view.toast("零钱不足，请先充值！");
//                            } else {
//                            }
                        } catch (Exception ex) {
                            view.toast(res.data.toString());
                        }
                    }
                });

        addSubscription(subscription);
    }
}
