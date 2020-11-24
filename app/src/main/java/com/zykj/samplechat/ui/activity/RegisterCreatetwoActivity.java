package com.zykj.samplechat.ui.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.zhy.http.okhttp.callback.StringCallback;
import com.zykj.samplechat.R;
import com.zykj.samplechat.model.User;
import com.zykj.samplechat.network.HttpUtils;
import com.zykj.samplechat.network.SubscriberRes;
import com.zykj.samplechat.presenter.RegisterCreatePresenter;
import com.zykj.samplechat.ui.activity.base.ToolBarActivity;
import com.zykj.samplechat.ui.view.RegisterCreateView;
import com.zykj.samplechat.utils.ActivityUtil;
import com.zykj.samplechat.utils.IsZH;
import com.zykj.samplechat.utils.NR;
import com.zykj.samplechat.utils.NRUtils;
import com.zykj.samplechat.utils.StringUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

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
 * 佛祖保佑         永无BUG               *
 * *
 * *
 * *****************************************************
 * <p>
 * Created by ninos on 2016/6/2.
 */
public class RegisterCreatetwoActivity extends ToolBarActivity<RegisterCreatePresenter> implements RegisterCreateView {
    @Bind(R.id.rc_yam)
    EditText rcYam;
    private String phoneNumber;

    @Bind(R.id.rc_phone)
    EditText rc_phone;
    @Bind(R.id.rc_pwd)
    EditText rc_pwd;
    @Bind(R.id.rc_repwd)
    EditText rc_repwd;
    @Bind(R.id.rc_name)
    EditText rc_name;

    @Override
    protected void action() {
        super.action();
        String name = rc_name.getText().toString();
        if (StringUtil.isEmpty(rc_pwd.getText().toString()) || StringUtil.isEmpty(rc_repwd.getText().toString())) {
            snb("密码不能为空", rc_phone);
        } else if (!StringUtil.isPassword(rc_pwd.getText().toString()) || !StringUtil.isPassword(rc_repwd.getText().toString())) {
            snb("密码必须由6-16位数字和字母组成", rc_phone);
        } else if (!rc_pwd.getText().toString().equals(rc_repwd.getText().toString())) {
            snb("两次输入的密码不一致", rc_phone);
        } else {
            Map map = new HashMap();
            map.put("mobile",rc_phone.getText().toString().trim());
            map.put("password ",rc_pwd.getText().toString().trim());
            HttpUtils.Forget(new SubscriberRes<Object>() {

                @Override
                public void onSuccess(Object o) {
                    toast("修改成功");
                    startActivity(LoginActivity.class);
                    finishActivity();

                }

                @Override
                public void completeDialog() {
                    super.completeDialog();
                }
            },rc_phone.getText().toString().trim(),rc_pwd.getText().toString().trim());


        }

    }

    @Override
    public boolean canAction() {
        return true;
    }

    @Override
    protected CharSequence provideTitle() {
        return "找回密码";
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_register_tow_create;
    }

    @Override
    public void initListeners() {

    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        phoneNumber = getIntent().getBundleExtra("data").getString("phoneNumber");
        tvAction.setText("完成");
        rc_phone.setText(phoneNumber);
    }

    @Override
    public RegisterCreatePresenter createPresenter() {
        return new RegisterCreatePresenter();
    }

    @Override
    public void successRegister(User user) {
        ActivityUtil.addActivity(this);
        dismissDialog();
        Toast.makeText(this, "找回成功", Toast.LENGTH_SHORT).show();
        startActivity(MainActivity.class);
        ActivityUtil.finishActivitys();
    }

    @Override
    public void error(String error) {
        dismissDialog();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
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