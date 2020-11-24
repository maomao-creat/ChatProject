package com.zykj.samplechat.presenter;

import com.google.gson.Gson;
import com.zykj.samplechat.model.Res;
import com.zykj.samplechat.network.HttpUtils;
import com.zykj.samplechat.network.Net;
import com.zykj.samplechat.network.SubscriberRes;
import com.zykj.samplechat.presenter.base.BasePresenterImp;
import com.zykj.samplechat.presenter.base.HotWeiXinBean;
import com.zykj.samplechat.presenter.base.HotZhiFuBaoBean;
import com.zykj.samplechat.ui.view.SendCongZhiView;
import com.zykj.samplechat.utils.ToolsUtils;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 徐学坤 on 2018/2/9.
 */
public class WalletCongZhiPresenter extends BasePresenterImp<SendCongZhiView>  {
    public void getce(String data) {
        Subscription subscription = Net.getService()
                .getce(data)
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
                            HotWeiXinBean yue =  new Gson().fromJson(json, HotWeiXinBean.class);
                            view.success(yue);
                        } catch (Exception ex) {
                            view.toast(res.data.toString());
                        }
                    }
                });

        addSubscription(subscription);
    }
    public void getzfb(String body,String subject,String amount) {
        HttpUtils.getAliPayOrderInfo(new SubscriberRes<HotZhiFuBaoBean>() {
            @Override
            public void onSuccess(HotZhiFuBaoBean userBean) {
                view.zsuccess(userBean);
            }

            @Override
            public void onError(Throwable throwable) {
                super.onError(throwable);
                ToolsUtils.toast(view.getContext(),"零钱不足，请先充值！");
            }

            @Override
            public void completeDialog() {

            }
        },body,subject,amount);
    }
}
