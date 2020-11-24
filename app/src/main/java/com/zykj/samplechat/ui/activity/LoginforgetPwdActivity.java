package com.zykj.samplechat.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.zykj.samplechat.R;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.presenter.LoginResetPwdPresenter;
import com.zykj.samplechat.ui.activity.base.ToolBarActivity;
import com.zykj.samplechat.ui.view.LoginResetPwdView;
import com.zykj.samplechat.utils.AESOperator;
import com.zykj.samplechat.utils.StringUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;

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
public class LoginforgetPwdActivity extends ToolBarActivity<LoginResetPwdPresenter> implements LoginResetPwdView {

    @Bind(R.id.rp_phone)EditText rp_phone;
    @Bind(R.id.rp_pwd)EditText rp_pwd;
    @Bind(R.id.rp_newpwd)EditText rp_newpwd;

    @Override
    protected void action() {
        super.action();
        if(StringUtil.isEmpty(rp_pwd.getText().toString())||StringUtil.isEmpty(rp_newpwd.getText().toString())){
            snb("密码不能为空",rp_pwd);
            return;
        }if(!StringUtil.isPassword(rp_pwd.getText().toString())||!StringUtil.isPassword(rp_newpwd.getText().toString())) {
            snb("密码必须由6-16位数字和字母组成",rp_pwd);
            return;
        }if(!rp_pwd.getText().toString().equals(rp_newpwd.getText().toString())){
            snb("两次输入的密码不一致",rp_pwd);
            return;
        }else{
            Map map = new HashMap();
            map.put("key", Const.KEY);
            map.put("uid",Const.UID);
            map.put("function","ForgetPassword");
            map.put("phone",rp_phone.getText().toString());
            map.put("password",rp_newpwd.getText().toString());
            String json = StringUtil.toJson(map);
            showDialog();
            try {
                String data=AESOperator.getInstance().encrypt(json.length() + "&" + json);
                presenter.ChangePassWordByPhone(data);
            }catch (Exception ex){
            }
        }
    }

    @Override
    public boolean canAction() {
        return true;
    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        tvAction.setText("修改");
        rp_phone.setText(getIntent().getStringExtra("tel"));
        rp_phone.setEnabled(false);
    }

    @Override
    protected CharSequence provideTitle() {
        return "重置密码";
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_reset_pwd;
    }

    @Override
    public void initListeners() {}

    @Override
    public LoginResetPwdPresenter createPresenter() {
        return new LoginResetPwdPresenter();
    }

    @Override
    public void successLogin() {
        dismissDialog();
        Toast.makeText(this,"密码重置成功",Toast.LENGTH_SHORT).show();
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    public void errorLogin() {
        dismissDialog();
        snb("密码重置失败，请重试",rp_phone);

    }
}
