package com.zykj.samplechat.presenter;

import android.content.Intent;
import android.widget.Toast;

import com.tencent.qcloud.tim.uikit.TUIKit;
import com.tencent.qcloud.tim.uikit.base.IUIKitCallBack;
import com.zykj.samplechat.model.User;
import com.zykj.samplechat.network.HttpUtils;
import com.zykj.samplechat.network.SubscriberRes;
import com.zykj.samplechat.presenter.base.BasePresenterImp;
import com.zykj.samplechat.ui.activity.LoginActivity;
import com.zykj.samplechat.ui.activity.MainActivity;
import com.zykj.samplechat.ui.view.SplashView;
import com.zykj.samplechat.utils.NetworkUtil;
import com.zykj.samplechat.utils.StringUtil;
import com.zykj.samplechat.utils.UserUtil;

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
public class SplashPresenter extends BasePresenterImp<SplashView> {

    public void navigate() {

        /**
         * 是否进入引导图*/
        if (new UserUtil(view.getContext()).isLogin()) {
            if (NetworkUtil.getNetWorkType(view.getContext()) == -1) {
                TUIKit.login(new UserUtil(view.getContext()).getUser().getUserCode(), new UserUtil(view.getContext()).getUser().getRYToken(), new IUIKitCallBack() {
                    @Override
                    public void onSuccess(Object data) {
                        view.getContext().startActivity(new Intent(view.getContext(), MainActivity.class));
                        view.finishActivity();
                    }

                    @Override
                    public void onError(String module, int errCode, String errMsg) {
                        Toast.makeText(view.getContext(), "腾讯云链接失败", Toast.LENGTH_SHORT);
                        view.getContext().startActivity(new Intent(view.getContext(), LoginActivity.class));

                    }
                });

            } else {
                if (StringUtil.isEmpty(UserUtil.getUser().getOpenId())) {
                    LoginWithoutPassword();
                } else {
                    sss2();
                }
            }
        } else {
            view.startActivity(LoginActivity.class);
        }
    }

    public void LoginWithoutPassword() {
        if (!StringUtil.isEmpty(new UserUtil(view.getContext()).getUser().getUserCode())) {
            TUIKit.login(new UserUtil(view.getContext()).getUser().getUserCode().toLowerCase(), new UserUtil(view.getContext()).getUser().getRYToken(), new IUIKitCallBack() {
                @Override
                public void onSuccess(Object data) {
                    view.getContext().startActivity(new Intent(view.getContext(), MainActivity.class));
                    view.finishActivity();
                }

                @Override
                public void onError(String module, int errCode, String errMsg) {
                    Toast.makeText(view.getContext(), "腾讯云链接失败", Toast.LENGTH_SHORT);
                    view.getContext().startActivity(new Intent(view.getContext(), LoginActivity.class));

                }
            });
        } else {
            view.getContext().startActivity(new Intent(view.getContext(), LoginActivity.class));
        }
    }

    private void sss2() {
        if (!StringUtil.isEmpty(new UserUtil(view.getContext()).getUser().getTJUserCode())) {
            HttpUtils.LoginByOtherWithInfor(new SubscriberRes<User>() {
                @Override
                public void onSuccess(final User userBean) {
                    TUIKit.login(userBean.getUserCode().toLowerCase(), userBean.getRYToken(), new IUIKitCallBack() {
                        @Override
                        public void onSuccess(Object data) {
                            UserUtil.putUser(userBean);
                            view.getContext().startActivity(new Intent(view.getContext(), MainActivity.class));
                            view.finishActivity();
//                        view.getContext().startActivity(new Intent(view.getContext(), MainActivity.class));
//                        view.finishActivity();
                        }

                        @Override
                        public void onError(String module, int errCode, String errMsg) {
                            Toast.makeText(view.getContext(), "腾讯云链接失败", Toast.LENGTH_SHORT);
                            view.getContext().startActivity(new Intent(view.getContext(), LoginActivity.class));

                        }
                    });

                }

                @Override
                public void completeDialog() {
                    view.getContext().startActivity(new Intent(view.getContext(), LoginActivity.class));
                }
            }, UserUtil.getUser().getOpenId() + "", UserUtil.getUser().getNickName() + "", UserUtil.getUser().getHeadUrl() + "");
        } else {
            view.getContext().startActivity(new Intent(view.getContext(), LoginActivity.class));
        }
    }
}
