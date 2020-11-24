package com.zykj.samplechat.presenter;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.rey.material.app.Dialog;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMFriendshipManager;
import com.tencent.imsdk.TIMUserProfile;
import com.tencent.qcloud.tim.uikit.TUIKit;
import com.tencent.qcloud.tim.uikit.base.IUIKitCallBack;
import com.zykj.samplechat.model.Res;
import com.zykj.samplechat.model.User;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.network.HttpUtils;
import com.zykj.samplechat.network.Net;
import com.zykj.samplechat.network.SubscriberRes;
import com.zykj.samplechat.presenter.base.BasePresenterImp;
import com.zykj.samplechat.ui.activity.MainActivity;
import com.zykj.samplechat.ui.view.LoginView;
import com.zykj.samplechat.utils.StringUtil;
import com.zykj.samplechat.utils.ToolsUtils;
import com.zykj.samplechat.utils.UserUtil;

import java.util.HashMap;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * *****************************************************
 * *
 * *
 * _oo0oo_                      *
 * o8888888o                     *
 * 88" . "88                     *
 * (| -_- |)                     *
 * 0\  =  /0                     *
 * ___/`---'\___                   *
 * .' \\|     |# '.                  *
 * / \\|||  :  |||# \                 *
 * / _||||| -:- |||||- \               *
 * |   | \\\  -  #/ |   |               *
 * | \_|  ''\---/''  |_/ |              *
 * \  .-\__  '-'  ___/-. /              *
 * ___'. .'  /--.--\  `. .'___            *
 * ."" '<  `.___\_<|>_/___.' >' "".          *
 * | | :  `- \`.;`\ _ /`;.`/ - ` : | |        *
 * \  \ `_.   \_ __\ /__ _/   .-` /  /        *
 * =====`-.____`.___ \_____/___.-`___.-'=====     *
 * `=---='                      *
 * *
 * *
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~    *
 * *
 * 佛祖保佑         永无BUG              *
 * *
 * *
 * *****************************************************
 * <p>
 * Created by ninos on 2016/6/2.
 */
public class LoginPresenter extends BasePresenterImp<LoginView> {

    public void login(View rootView, final String userphone, final String password) {
        if (StringUtil.isEmpty(userphone)) {
            ToolsUtils.toast(view.getContext(), "手机号不能为空");
        }

//        else if (!ToolsUtils.isMobile(userphone)) {
//            ToolsUtils.toast(view.getContext(), "手机号格式无效");
//        }

        else if (StringUtil.isEmpty(password)) {
            ToolsUtils.toast(view.getContext(), "密码不能为空");
        } else {
            final HashMap map = new HashMap<>();
            view.showDialog();
            HttpUtils.login(new SubscriberRes<User>(rootView) {
                @Override
                public void onSuccess(final User userBean) {
                    view.dismissDialog();
                    Log.e("TAG", "用户ID=" + userBean.getId());
//                    String userSig = GenerateTestUserSig.genTestUserSig(userBean.getId().toLowerCase());
                    TUIKit.login(userBean.getUserCode().toLowerCase(), userBean.getRYToken(), new IUIKitCallBack() {
                        @Override
                        public void onSuccess(Object data) {
                            UserUtil.setPassword(password);
                            UserUtil.putUser(userBean);
                            HashMap<String, Object> hashMap = new HashMap<>();
// 头像，mIconUrl 就是您上传头像后的 URL，可以参考 Demo 中的随机头像作为示例
                            if (!TextUtils.isEmpty(userBean.getNickName())) {
                                hashMap.put(TIMUserProfile.TIM_PROFILE_TYPE_KEY_NICK, userBean.getNickName());
                            }
                            TIMFriendshipManager.getInstance().modifySelfProfile(hashMap, new TIMCallBack() {
                                @Override
                                public void onError(int i, String s) {
                                    Log.e("WeLogin", "modifySelfProfile err code = " + i + ", desc = " + s);
                                }
                                @Override
                                public void onSuccess() {
                                    Log.i("WeLogin", "modifySelfProfile success");
                                }
                            });
                            view.getContext().startActivity(new Intent(view.getContext(), MainActivity.class));
                            view.finishActivity();
                        }

                        @Override
                        public void onError(String module, int errCode, String errMsg) {
                            if (errCode == 6208) {
                                TUIKit.login(userBean.getUserCode().toLowerCase(), userBean.getRYToken(), new IUIKitCallBack() {
                                    @Override
                                    public void onSuccess(Object data) {
                                        UserUtil.setPassword(password);
                                        UserUtil.putUser(userBean);
                                        HashMap<String, Object> hashMap = new HashMap<>();
// 头像，mIconUrl 就是您上传头像后的 URL，可以参考 Demo 中的随机头像作为示例
                                        if (!TextUtils.isEmpty(userBean.getNickName())) {
                                            hashMap.put(TIMUserProfile.TIM_PROFILE_TYPE_KEY_NICK, userBean.getNickName());
                                        }
                                        TIMFriendshipManager.getInstance().modifySelfProfile(hashMap, new TIMCallBack() {
                                            @Override
                                            public void onError(int i, String s) {
                                                Log.e("WeLogin", "modifySelfProfile err code = " + i + ", desc = " + s);
                                            }
                                            @Override
                                            public void onSuccess() {
                                                Log.i("WeLogin", "modifySelfProfile success");
                                            }
                                        });
                                        view.getContext().startActivity(new Intent(view.getContext(), MainActivity.class));
                                        view.finishActivity();
                                    }

                                    @Override
                                    public void onError(String module, int errCode, String errMsg) {
                                        Toast.makeText(view.getContext(), "腾讯云链接失败", Toast.LENGTH_SHORT);
                                    }
                                });
                            } else {
                                Toast.makeText(view.getContext(), "腾讯云链接失败", Toast.LENGTH_SHORT);
                            }

                        }
                    });


                }

                @Override
                public void completeDialog() {
                    view.dismissDialog();
                }
            }, userphone, password);
        }
    }

    public void Login(String data) {
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
                        if (res.code == Const.OK) {
                            String json = new Gson().toJson(res.data);
                            view.successLogin(new Gson().fromJson(json, User.class));
                        } else {
                            view.errorLogin(res.error);
                        }
                    }
                });

        addSubscription(subscription);
    }

    public void Logins(String data) {
        Subscription subscription = Net.getService()
                .Logins(data)
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
                        if (res.code == Const.OK) {
                            String json = new Gson().toJson(res.data);
                            view.successLogin(new Gson().fromJson(json, User.class));
                        } else {
                            view.errorLogin(res.error);
                        }
                    }
                });

        addSubscription(subscription);
    }

    public void yaoqing(String data) {
        Subscription subscription = Net.getService()
                .yaoqing(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Res>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.successyaoqing("");
                    }

                    @Override
                    public void onNext(Res res) {
                        if (res.code == Const.OK) {
                            String json = new Gson().toJson(res.data);
                            view.successyaoqing(json);
                        } else {
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
//
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