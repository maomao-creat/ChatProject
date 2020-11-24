package com.zykj.samplechat.presenter;

import com.google.gson.Gson;
import com.zykj.samplechat.model.Hongbao;
import com.zykj.samplechat.model.Res;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.network.Net;
import com.zykj.samplechat.presenter.base.BasePresenterImp;
import com.zykj.samplechat.ui.view.SendMoneyView;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ninos on 2017/8/22.
 */

public class SendMoneyPresenter extends BasePresenterImp<SendMoneyView> {
    public void SendSingleRedPacket(String data) {
        Subscription subscription = Net.getService()
                .SendSingleRedPacket(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Res<Hongbao>>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Res<Hongbao> res) {
                        if (res.code == Const.OK) {
                            view.success(res.data.Id);
                        } else {
                        }
                    }
                });

        addSubscription(subscription);
    }
    public void Sendxx(String data) {
        Subscription subscription = Net.getService()
                .Sendxx(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Res<String>>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Res<String> res) {
                        if (res.code == Const.OK) {

                        } else {
                        }
                    }
                });

        addSubscription(subscription);
    }

    public void SendManyOrdinaryPacket(String data) {
        Subscription subscription = Net.getService()
                .SendManyOrdinaryPacket(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Res<Hongbao>>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Res<Hongbao> res) {
                        if (res.code == Const.OK) {
                            view.success(res.data.Id);
                        } else {
                        }
                    }
                });

        addSubscription(subscription);
    }

    public void SendManyRandomPacket(String totalamount,String teamid,String lei) {
//        HttpUtils.SendManyRandomPacket(new SubscriberRes<Hongbao>() {
//            @Override
//            public void onSuccess(Hongbao userBean) {
//                int hid = (int)Double.parseDouble(userBean.Id);
//                view.success(hid + "");
//            }
//
//            @Override
//            public void onError(Throwable throwable) {
//                super.onError(throwable);
//                ToolsUtils.toast(view.getContext(),"零钱不足，请先充值！");
//            }
//
//            @Override
//            public void completeDialog() {
//
//            }
//        },totalamount,teamid,lei);
    }
    //中雷
    public void SendManyRandomPacket2(String data) {
        Subscription subscription = Net.getService()
                .SendManyRandomPacket2(data)
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
                            Hongbao hongbao = new Gson().fromJson(json, Hongbao.class);
                            if (res.code == Const.OK) {
                                int hid = (int)Double.parseDouble(hongbao.Id);
                                view.success(hid + "");
                            } else {
                            }
                        } catch (Exception ex) {
                            view.toast(res.data.toString());
                        }
                    }
                });

        addSubscription(subscription);
    }

//    public void PayPassWodExt(String args){
//        Subscription subscription = Net.getService()
//                .PayPassWodExt(args)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<Res>() {
//
//                    @Override
//                    public void onCompleted() {
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        view.error("密码错误");
//                    }
//
//                    @Override
//                    public void onNext(Res res) {
//                        if (res.code== Const.OK){
//                            view.successCheck();
//                        }else {
//                            view.error("提现密码错误");
//                        }
//                    }
//                });
//
//        addSubscription(subscription);
//    }
}
