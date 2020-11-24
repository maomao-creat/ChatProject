package com.zykj.samplechat.presenter;

import com.google.gson.Gson;
import com.rey.material.app.Dialog;
import com.zykj.samplechat.model.Res;
import com.zykj.samplechat.model.User;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.network.Net;
import com.zykj.samplechat.presenter.base.BasePresenterImp;
import com.zykj.samplechat.ui.view.LoginExistView;

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
public class LoginExistPresenter extends BasePresenterImp<LoginExistView> {

    public void Login(String data){
        Subscription subscription = Net.getService()
                .Login(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Res>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.errorLogin("服务器繁忙，请稍后重试");
                    }

                    @Override
                    public void onNext(Res res) {
                        if (res.code == Const.OK){
                            String json = new Gson().toJson(res.data);
                            view.successLogin(new Gson().fromJson(json, User.class));
                        }else {
                            view.errorLogin(res.error);
                        }
                    }
                });

        addSubscription(subscription);
    }

    public void getVersion() {
//        Subscription subscription = Net.getService()
//                .getVersion()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<Res<About>>() {
//                    @Override
//                    public void onCompleted() {
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                    }
//
//                    @Override
//                    public void onNext(final Res<About> res) {
//                        if (res.code != Const.OK || !"2".equals(res.data.AppVerson)) {
//                            if (dialog_phone == null) {
//                                dialog_phone = new Dialog(view.getContext()).title("有新版本，请更新后使用").positiveAction("确定").positiveActionClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        Intent intent = new Intent();
//                                        intent.setAction("android.intent.action.VIEW");
//                                        Uri content_url = Uri.parse("https://www.pgyer.com/fODe");
//                                        intent.setData(content_url);
//                                        view.getContext().startActivity(intent);
//                                        System.exit(0);
//                                    }
//                                }).backgroundColor(Color.parseColor("#ffffff")).titleColor(Color.parseColor("#292A2F"));
//                            }
//                            dialog_phone.setCanceledOnTouchOutside(false);
//                            dialog_phone.setCancelable(false);
//                            dialog_phone.show();
//                        }
//                    }
//                });
//        addSubscription(subscription);
    }

    private Dialog dialog_phone;
}
