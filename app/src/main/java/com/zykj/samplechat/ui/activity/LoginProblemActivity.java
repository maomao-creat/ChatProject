package com.zykj.samplechat.ui.activity;

import android.os.Bundle;

import com.rey.material.widget.Button;
import com.zykj.samplechat.R;
import com.zykj.samplechat.presenter.LoginProblemPresenter;
import com.zykj.samplechat.ui.activity.base.ToolBarActivity;
import com.zykj.samplechat.ui.view.LoginProblemView;
import com.zykj.samplechat.utils.ActivityUtil;

import butterknife.Bind;
import butterknife.OnClick;

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
public class LoginProblemActivity extends ToolBarActivity<LoginProblemPresenter> implements LoginProblemView {

    @Bind(R.id.lp_btn)Button lp_btn;

    @OnClick(R.id.lp_btn)
    public void lp_btn(){
        startActivity(LoginSmsActivity.class);
        ActivityUtil.addActivity(this);
    }
    @Override
    protected CharSequence provideTitle() {
        return "登录遇到问题";
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_login_problem;
    }

    @Override
    public void initListeners() {

    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
    }

    @Override
    public LoginProblemPresenter createPresenter() {
        return new LoginProblemPresenter();
    }
}
