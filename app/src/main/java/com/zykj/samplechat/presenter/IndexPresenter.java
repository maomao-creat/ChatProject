package com.zykj.samplechat.presenter;

import com.zykj.samplechat.model.About;
import com.zykj.samplechat.model.Res;
import com.zykj.samplechat.model.Team;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.network.Net;
import com.zykj.samplechat.presenter.base.BasePresenterImp;
import com.zykj.samplechat.ui.view.IndexView;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 ******************************************************
 *                                                    *
 *                                                    *
 *                       _oo0oo_                      *
 *                      o8888888o                     *
 *                      88" . "88                     *
 *                      (| -_- |)                     *
 *                      0\  =  /0                     *
 *                    ___/`---'\___                   *
 *                  .' \\|     |# '.                  *
 *                 / \\|||  :  |||# \                 *
 *                / _||||| -:- |||||- \               *
 *               |   | \\\  -  #/ |   |               *
 *               | \_|  ''\---/''  |_/ |              *
 *               \  .-\__  '-'  ___/-. /              *
 *             ___'. .'  /--.--\  `. .'___            *
 *          ."" '<  `.___\_<|>_/___.' >' "".          *
 *         | | :  `- \`.;`\ _ /`;.`/ - ` : | |        *
 *         \  \ `_.   \_ __\ /__ _/   .-` /  /        *
 *     =====`-.____`.___ \_____/___.-`___.-'=====     *
 *                       `=---='                      *
 *                                                    *
 *                                                    *
 *     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~    *
 *                                                    *
 *               佛祖保佑         永无BUG              *
 *                                                    *
 *                                                    *
 ******************************************************
 *
 * Created by ninos on 2016/6/2.
 *
 */
public class IndexPresenter extends BasePresenterImp<IndexView> {

    public void SelectTeam(String data){
        Subscription subscription = Net.getService()
                .SelectTeam(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Res<Team>>() {
                    @Override
                    public void onCompleted() {}
                    @Override
                    public void onError(Throwable e) {
                        view.dismissDialog();
                    }
                    @Override
                    public void onNext(Res<Team> res) {
                        view.dismissDialog();
                        if (res.code== Const.OK){
                            view.response(String.valueOf(res.data.Type));
                        }
                    }
                });
        addSubscription(subscription);
    }

//    public void GetAllGonggao(String data){
//        Subscription subscription = Net.getService()
//                .SelectGongGaoList(data)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<Res<ArrayList<Gonggao>>>() {
//                    @Override
//                    public void onCompleted() {}
//                    @Override
//                    public void onError(Throwable e) {}
//                    @Override
//                    public void onNext(Res<ArrayList<Gonggao>> res) {
//                        if (res.code== Const.OK){
//                            if(res.data.size() > 0){
//                                view.response("#"+res.data.get(0).Message+"    ");
//                            }
//                        }
//                    }
//                });
//        addSubscription(subscription);
//    }

    public void SelectAboutUs(String data){
        Subscription subscription = Net.getService()
                .SelectAboutUs(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Res<About>>() {
                    @Override
                    public void onCompleted() {}
                    @Override
                    public void onError(Throwable e) {
                        view.response(null);
                    }
                    @Override
                    public void onNext(Res<About> res) {
                        if (res.code== Const.OK){
                            view.response(res.data.TelePhone);
                        }else {
                            view.response(null);
                        }
                    }
                });
        addSubscription(subscription);
    }
}
