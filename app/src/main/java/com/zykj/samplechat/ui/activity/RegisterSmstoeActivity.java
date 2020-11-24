package com.zykj.samplechat.ui.activity;

import android.os.Bundle;
import android.widget.EditText;

import com.rey.material.widget.Button;
import com.zykj.samplechat.R;
import com.zykj.samplechat.presenter.RegisterSmsPresenter;
import com.zykj.samplechat.ui.activity.base.ToolBarActivity;
import com.zykj.samplechat.ui.view.RegisterSmsView;
import com.zykj.samplechat.utils.ActivityUtil;
import com.zykj.samplechat.utils.Bun;
import com.zykj.samplechat.utils.StringUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

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
public class RegisterSmstoeActivity extends ToolBarActivity<RegisterSmsPresenter> implements RegisterSmsView {

    private String phoneNumber;
    private boolean canClick = false;

    @Bind(R.id.ls_btn)
    Button ls_btn;
    @Bind(R.id.rs_phone)
    EditText rs_phone;

    @OnTextChanged(R.id.rs_phone)
    public void rs_phone(CharSequence s, int start, int before, int count) {
        if (rs_phone.getText().toString().length() == 0) {
            ls_btn.setBackgroundResource(R.drawable.shape_2);
            ls_btn.setText("忘记密码");
            canClick = false;
        } else {
            ls_btn.setBackgroundResource(R.drawable.shape_3);
            ls_btn.setText("忘记密码");
            canClick = true;
        }
    }

    @OnClick(R.id.ls_btn)
    public void ls_btn() {
        if (!canClick)
            return;
        phoneNumber = rs_phone.getText().toString();
        if (StringUtil.isMobile(phoneNumber)) {
            startActivity(RegisterChecktowActivity.class, new Bun().putString("phoneNumber", phoneNumber).ok());
            ActivityUtil.addActivity(this);
        } else
            snb("请输入正确的手机号", ls_btn);
    }

    @Override
    protected CharSequence provideTitle() {
        return "忘记密码";
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_register_sms;
    }

    @Override
    public void initListeners() {

    }

    @Override
    public RegisterSmsPresenter createPresenter() {
        return new RegisterSmsPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.img_back)
    public void onViewClicked() {
        finish();
    }
}
