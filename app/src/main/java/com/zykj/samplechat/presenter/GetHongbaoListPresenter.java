package com.zykj.samplechat.presenter;

import com.zykj.samplechat.model.ChaiHongbao;
import com.zykj.samplechat.model.HongbaoList;
import com.zykj.samplechat.model.Res;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.network.HttpUtils;
import com.zykj.samplechat.network.Net;
import com.zykj.samplechat.network.SubscriberRes;
import com.zykj.samplechat.presenter.base.RefreshAndLoadMorePresenter;
import com.zykj.samplechat.ui.view.GetHongbaoListView;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ninos on 2017/8/23.
 */

public class GetHongbaoListPresenter extends RefreshAndLoadMorePresenter<GetHongbaoListView> {
    @Override
    public void getData(int page, int count) {

    }

    public void OpenSingleRedPacket(String data) {
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
                        view.errorChai("服务器错误");
                    }

                    @Override
                    public void onNext(Res<ChaiHongbao> res) {
                        if (res.code == Const.OK) {
                            view.success(res.data);
                        } else {
                            view.dismissDialog();
                            view.errorChai(res.error);
                        }
                    }
                });

        addSubscription(subscription);
    }
    public void OpenSingleRedPacket1(String teamid,String packageid,String randomnum) {//抢红包post
        HttpUtils.GRedPackage(new SubscriberRes<ChaiHongbao>() {
            @Override
            public void onSuccess(ChaiHongbao res) {
                view.success(res);
            }
            @Override
            public void completeDialog() {
            }
        },teamid,packageid,randomnum);
    }

    public void ViewPacketList(String data) {//抢成功进了这里  失败也是这里
        Subscription subscription = Net.getService()
                .ViewPacketList(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Res<HongbaoList>>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
//                        view.dismissDialog();
                    }

                    @Override
                    public void onNext(Res<HongbaoList> res) {
                        if (res.code == Const.OK) {
                            view.success(res.data);
                        } else {
                           //view.dismissDialog();
                        }
                    }
                });

        addSubscription(subscription);
    }
}
