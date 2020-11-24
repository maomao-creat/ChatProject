package com.zykj.samplechat.presenter;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zykj.samplechat.model.Res;
import com.zykj.samplechat.model.User;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.network.Net;
import com.zykj.samplechat.presenter.base.BasePresenterImp;
import com.zykj.samplechat.ui.view.LoginCheckView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Request;
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
public class LoginCheckPresenter extends BasePresenterImp<LoginCheckView> {
    public void LoginWithoutPassword(String data){
        Subscription subscription = Net.getService()
                .LoginWithoutPassword(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Res<User>>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.errorLogin();
                    }

                    @Override
                    public void onNext(Res<User> res) {
                        if (res.code== Const.OK){
                            view.successLogin(res.data);
                        }else {
                            view.errorLogin();
                        }
                    }
                });

        addSubscription(subscription);
    }

    public void checkNum(String phoneNumber,String content){
        String url = "http://118.145.18.170:8080/sms.aspx?action=send&userid=2375&account=twslkj&password=a123123&mobile="+phoneNumber+"&content="+content+"&sendTime=&extno=";
        OkHttpUtils
                .post()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        e.toString();
                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.optJSONArray("city_info");
//                            cityInfos = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<CityInfo>>() {}.getType());



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                });
    }
}
