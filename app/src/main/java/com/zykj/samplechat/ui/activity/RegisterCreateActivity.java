package com.zykj.samplechat.ui.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.zykj.samplechat.R;
import com.zykj.samplechat.model.User;
import com.zykj.samplechat.presenter.RegisterCreatePresenter;
import com.zykj.samplechat.ui.activity.base.ToolBarActivity;
import com.zykj.samplechat.ui.view.RegisterCreateView;
import com.zykj.samplechat.utils.ActivityUtil;
import com.zykj.samplechat.utils.StringUtil;

import java.util.regex.Pattern;

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
 * 佛祖保佑         永无BUG               *
 * *
 * *
 * *****************************************************
 * <p>
 * Created by ninos on 2016/6/2.
 */
public class RegisterCreateActivity extends ToolBarActivity<RegisterCreatePresenter> implements RegisterCreateView {
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
        } else if (StringUtil.isEmpty(rcYam.getText().toString()) || StringUtil.isEmpty(rcYam.getText().toString())) {
            snb("邀请码不能为空", rc_phone);
        } else if (!StringUtil.isPassword(rc_pwd.getText().toString()) || !StringUtil.isPassword(rc_repwd.getText().toString())) {
            snb("密码必须由6-16位数字和字母组成", rc_phone);
        } else if (!rc_pwd.getText().toString().equals(rc_repwd.getText().toString())) {
            snb("两次输入的密码不一致", rc_phone);
        } else if (StringUtil.isEmpty(name.trim())) {
            snb("昵称不能为空", rc_name);
        } else if (Pattern.compile("^[0-9]+$").matcher(name).matches()) {
            snb("昵称不能为纯数字", rc_name);
        } else {
            presenter.Register(rc_phone,phoneNumber,rc_pwd.getText().toString().trim(),rcYam.getText().toString().trim(),name.trim());
//            Map map = new HashMap();
//            map.put("function", "" + "Register");
//            map.put("nickname", "" + name.trim());
//            map.put("password", "" + rc_pwd.getText().toString().trim());
//            map.put("mobile", "" + phoneNumber);
//            map.put("smscode", "");
//            map.put("filename", "");
//            map.put("filestream", "");
//            map.put("sex", "0");
//            map.put("tjusercode", "" + rcYam.getText().toString().trim());
//            showDialog();
//            NR.posts("WebService/UserService.asmx/Entry", map, new StringCallback() {
//
//                @Override
//                public void onError(Request request, Exception e) {
//                    dismissDialog();
//                    //  LogUtils.i("xxxxx222  :", "" +e);  //输出测试
//                    IsZH.getToast(RegisterCreateActivity.this, "服务器错误");  //吐司
//                }
//
//                @Override
//                public void onResponse(String s) {
//                    dismissDialog();
//                    //  LogUtils.i("xxxxx", "" +s);  //输出测试
//                    if (NRUtils.getYse(RegisterCreateActivity.this, s)) {
//                        IsZH.getToast(RegisterCreateActivity.this, "注册成功!");  //吐司
//                        // MyYuEBean mye = NRUtils.getData(s,MyYuEBean.class);
//                        finish();
//                    }
//                }
//
//            });
//            Map map = new HashMap();
//            map.put("key", Const.KEY);
//            map.put("uid", Const.UID);
//            map.put("function", "Register");
//            map.put("phone", phoneNumber);
//            map.put("password", rc_pwd.getText().toString());
//            map.put("name", phoneNumber);
//            map.put("recommendCode", ""+rcYam.getText().toString().trim());
//            String json = StringUtil.toJson(map);
//            showDialog();
//            try {
//                presenter.Register(AESOperator.getInstance().encrypt(json.length() + "&" + json),
//                        rc_name.getText().toString().trim(), phoneNumber, rc_pwd.getText().toString());
//            } catch (Exception ex) {
//            }
        }
    }

    @Override
    public boolean canAction() {
        return true;
    }

    @Override
    protected CharSequence provideTitle() {
        return "完善信息";
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_register_create;
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
        Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
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