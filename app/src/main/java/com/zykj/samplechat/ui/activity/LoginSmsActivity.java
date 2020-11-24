package com.zykj.samplechat.ui.activity;

import android.widget.EditText;

import com.rey.material.widget.Button;
import com.zykj.samplechat.R;
import com.zykj.samplechat.presenter.LoginSmsPresenter;
import com.zykj.samplechat.ui.activity.base.ToolBarActivity;
import com.zykj.samplechat.ui.view.LoginSmsView;
import com.zykj.samplechat.utils.ActivityUtil;
import com.zykj.samplechat.utils.Bun;
import com.zykj.samplechat.utils.StringUtil;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;

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
public class LoginSmsActivity extends ToolBarActivity<LoginSmsPresenter> implements LoginSmsView{

    private String phoneNumber;
    private boolean canClick=false;

    @Bind(R.id.ls_btn)Button ls_btn;
    @Bind(R.id.ls_phone)EditText ls_phone;

    @OnTextChanged(R.id.ls_phone)
    public void ls_phone(CharSequence s, int start, int before, int count){
        if(ls_phone.getText().toString().length()==0) {
            ls_btn.setBackgroundResource(R.drawable.shape_2);
            ls_btn.setText("下一步");
            canClick=false;
        } else {
            ls_btn.setBackgroundResource(R.drawable.shape_3);
            ls_btn.setText("下一步");
            canClick=true;
        }
    }

    @OnClick(R.id.ls_btn)
    public void ls_btn(){
        if(!canClick)
            return;
        phoneNumber = ls_phone.getText().toString();
        if(StringUtil.isMobile(phoneNumber)) {
            startActivity(LoginCheckActivity.class, new Bun().putString("phoneNumber", phoneNumber).ok());
            ActivityUtil.addActivity(this);
        }
        else
            snb("请输入正确的手机号",ls_btn);
    }

    @Override
    protected CharSequence provideTitle() {
        return "通过短信验证码登录";
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_login_sms;
    }

    @Override
    public void initListeners() {

    }

    @Override
    public LoginSmsPresenter createPresenter() {
        return new LoginSmsPresenter();
    }
}
