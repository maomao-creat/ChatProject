package com.zykj.samplechat.presenter;

import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.zykj.samplechat.model.Res;
import com.zykj.samplechat.model.SentRecords;
import com.zykj.samplechat.network.Net;
import com.zykj.samplechat.presenter.base.BasePresenterImp;
import com.zykj.samplechat.ui.view.SendJiLuView;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 徐学坤 on 2018/2/9.
 */
public class WalleJiLuPresenter extends BasePresenterImp<SendJiLuView>  {
    //获取充值记录
    public void getchongzhi(String data,final int i, final TwinklingRefreshLayout trs) {
        Subscription subscription = Net.getService()
                .getchongzhi(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Res<ArrayList<SentRecords>>>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.toast("没有更多记录了");
                        List<HotSellerListBean> rs = new ArrayList<HotSellerListBean>();
                        view.success(rs,i,trs);
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Res<ArrayList<SentRecords>> res) {
                        try {
                         //   Log.i("xxxxxxx",res.toString());

//                            List<SentRecords> sentRecords=new ArrayList<SentRecords>();
//                            sentRecords = res.data;
                            List<HotSellerListBean> rs = new ArrayList<HotSellerListBean>();
                            rs.add(new HotSellerListBean(res.data));
                          view.success(rs,i,trs);

                        } catch (Exception ex) {
                            view.toast(res.data.toString());
                        }
                    }
                });

        addSubscription(subscription);
    }
}
