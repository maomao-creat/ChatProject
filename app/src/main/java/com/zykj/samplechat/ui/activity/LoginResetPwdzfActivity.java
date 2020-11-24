package com.zykj.samplechat.ui.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.zykj.samplechat.R;
import com.zykj.samplechat.presenter.LoginResetPwdPresenter;
import com.zykj.samplechat.ui.activity.base.ToolBarActivity;
import com.zykj.samplechat.ui.view.LoginResetPwdView;
import com.zykj.samplechat.utils.StringUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
public class LoginResetPwdzfActivity extends ToolBarActivity<LoginResetPwdPresenter> implements LoginResetPwdView {

    @Bind(R.id.rp_phone)
    EditText rp_phone;
    @Bind(R.id.rp_oldpwd)
    EditText rp_oldpwd;
    @Bind(R.id.rp_pwd)
    EditText rp_pwd;
    @Bind(R.id.rp_newpwd)
    EditText rp_newpwd;

    @Override
    protected void action() {
        super.action();
        if (StringUtil.isEmpty(rp_oldpwd.getText().toString())) {
            snb("登陆密码不能为空", rp_pwd);
            return;
        }
        if (StringUtil.isEmpty(rp_pwd.getText().toString()) || StringUtil.isEmpty(rp_newpwd.getText().toString())) {
            snb("密码不能为空", rp_pwd);
            return;
        }
        if (!rp_pwd.getText().toString().equals(rp_newpwd.getText().toString())) {
            snb("两次输入的密码不一致", rp_pwd);
            return;
        } else {
            presenter.EditPayPassword(rp_phone,rp_oldpwd.getText().toString().trim(),rp_pwd.getText().toString().trim());
//            Map map = new HashMap();
//            map.put("function",""+"EditPayPassword");
//            map.put("userid",""+new UserUtil(getContext()).getUserId2());
//            map.put("oldpaypass",""+rp_oldpwd.getText().toString().trim());
//            map.put("newpaypass",""+rp_pwd.getText().toString().trim());
//            showDialog();
//            NR.posts("WebService/UserService.asmx/Entry",map,new StringCallback(){
//
//                @Override
//                public void onError(Request request, Exception e) {
//                    dismissDialog();
//                    //  LogUtils.i("xxxxx222  :", "" +e);  //输出测试
//                    IsZH.getToast(LoginResetPwdzfActivity.this, "服务器错误");  //吐司
//                }
//
//                @Override
//                public void onResponse(String s) {
//                    dismissDialog();
//                    //  LogUtils.i("xxxxx", "" +s);  //输出测试
//                    if(NRUtils.getYse(LoginResetPwdzfActivity.this,s)) {
//                        IsZH.getToast(LoginResetPwdzfActivity.this, "修改成功!");  //吐司
//                        // MyYuEBean mye = NRUtils.getData(s,MyYuEBean.class);
//                        finish();
//                    }
//                }
//
//            });
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

    }

    @Override
    protected CharSequence provideTitle() {
        return "修改支付密码";
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_reset_pwdzf;
    }

    @Override
    public void initListeners() {

    }

    @Override
    public LoginResetPwdPresenter createPresenter() {
        return new LoginResetPwdPresenter();
    }

    @Override
    public void successLogin() {
        dismissDialog();
        Toast.makeText(this, "支付密码修改成功", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void errorLogin() {
        dismissDialog();
        snb("密码修改失败，请重试", rp_phone);

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
