package com.zykj.samplechat.presenter;

import android.view.View;

import com.google.gson.Gson;
import com.zykj.samplechat.model.Login;
import com.zykj.samplechat.model.Res;
import com.zykj.samplechat.model.User;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.network.HttpUtils;
import com.zykj.samplechat.network.Net;
import com.zykj.samplechat.network.SubscriberRes;
import com.zykj.samplechat.presenter.base.BasePresenterImp;
import com.zykj.samplechat.ui.view.RegisterCreateView;
import com.zykj.samplechat.utils.AESOperator;
import com.zykj.samplechat.utils.StringUtil;
import com.zykj.samplechat.utils.ToolsUtils;
import com.zykj.samplechat.utils.UserUtil;

import java.util.HashMap;
import java.util.Map;

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
public class RegisterCreatePresenter extends BasePresenterImp<RegisterCreateView> {
    public void Register(View rootView, String userphone,String password,String tjusercode,String nickname) {
        if (StringUtil.isEmpty(userphone)) {
            ToolsUtils.toast(view.getContext(),"手机号不能为空");
        } else if (!ToolsUtils.isMobile(userphone)) {
            ToolsUtils.toast(view.getContext(),"手机号格式无效");
        } else if (StringUtil.isEmpty(password)) {
            ToolsUtils.toast(view.getContext(),"密码不能为空");
        } else if (StringUtil.isEmpty(tjusercode)) {
            ToolsUtils.toast(view.getContext(),"推荐人不能为空");
        } else if (StringUtil.isEmpty(nickname)) {
            ToolsUtils.toast(view.getContext(),"昵称不能为空");
        } else {
            final HashMap map = new HashMap<>();
            view.showDialog();
            HttpUtils.Register(new SubscriberRes<String>(rootView) {
                @Override
                public void onSuccess(String userBean) {
                    view.dismissDialog();
                    ToolsUtils.toast(view.getContext(),"注册成功");
//                    UserUtil.putUser(userBean);
//                    view.getContext().startActivity(new Intent(view.getContext(),MainActivity.class));
                    view.finishActivity();
                }

                @Override
                public void completeDialog() {
                    view.dismissDialog();
                }
            }, userphone,password,tjusercode,nickname);
        }
    }

    public void ChangeInfor(String data, final String phoneNumber, final String password){
        Subscription subscription = Net.getService()
                .ChangeInfor(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Res>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.error("注册失败，如果您注册过贝聊，请前往登录界面登录");
                    }

                    @Override
                    public void onNext(Res res) {
                        if (res.code==Const.OK){
                            Map<String, Object> map = new HashMap<>();
                            map.put("key", Const.KEY);
                            map.put("uid",Const.UID);
                            map.put("function","Login");
                            map.put("username",phoneNumber);
                            map.put("password",password);
                            String json = StringUtil.toJson(map);
                            try {
                                Login(AESOperator.getInstance().encrypt(json.length() + "&" + json));
                            }catch (Exception ex){}
                        }else{
                            view.error(res.error);
                        }
                    }
                });

        addSubscription(subscription);
    }

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
                        view.error("注册失败，如果您注册过朋友，请前往登录界面登录");
                    }

                    @Override
                    public void onNext(Res res) {
                        if (res.code==Const.OK){
                            String jsonStr = new Gson().toJson(res.data);
                            User user = new Gson().fromJson(jsonStr, User.class);
                            UserUtil uu = new UserUtil(view.getContext());
                            uu.putUser(user);
                            uu.putLogin(new Login(user.getMobile(),user.getHeadUrl()));

                            Map<String, Object> map = new HashMap<>();
                            map.put("key", Const.KEY);
                            map.put("uid",Const.UID);
                            map.put("function","PushRegister");
                            map.put("id",user.getId());
                            String json = StringUtil.toJson(map);
                            try {
                                PushRegister(AESOperator.getInstance().encrypt(json.length() + "&" + json));
                            }catch (Exception ex){}
                        }else{
                            view.error(res.error);
                        }
                    }
                });

        addSubscription(subscription);
    }

    public void PushRegister(String data){
        Subscription subscription = Net.getService()
                .PushRegister(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Res>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Res res) {
                        view.successRegister(null);
                    }
                });
        addSubscription(subscription);
    }
}