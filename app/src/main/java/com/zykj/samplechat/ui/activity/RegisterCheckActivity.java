package com.zykj.samplechat.ui.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.rey.material.widget.Button;
import com.zykj.samplechat.R;
import com.zykj.samplechat.presenter.RegisterCheckPresenter;
import com.zykj.samplechat.ui.activity.base.ToolBarActivity;
import com.zykj.samplechat.ui.view.RegisterCheckView;
import com.zykj.samplechat.utils.ActivityUtil;
import com.zykj.samplechat.utils.Bun;
import com.zykj.samplechat.utils.StringUtil;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

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
public class RegisterCheckActivity extends ToolBarActivity<RegisterCheckPresenter> implements RegisterCheckView {

    //常量
//    private static final String APPKEY = "23997cf59730e";
//    private static final String APPSECRET = "aa90a964442dd81511f4d6129e781010";
    private static final int TIME_INIT = 60;        //初始化时间用
//    private int random = 0;

    private String phoneNumber;
    private String code;
    private int time;
    private Handler handler = new Handler();
    private boolean canClick = false;

    @Bind(R.id.lc_time)
    TextView lc_time;
    @Bind(R.id.rc_phone)
    EditText rc_phone;
    @Bind(R.id.rc_code)
    EditText rc_code;
    @Bind(R.id.rc_btn)
    Button rc_btn;
    private boolean isFlag = false;

    @OnTextChanged(R.id.rc_code)
    public void rc_code(CharSequence s, int start, int before, int count) {
        if (rc_code.getText().toString().length() == 0) {
            rc_btn.setBackgroundResource(R.drawable.shape_2);
            rc_btn.setText("下一步");
            canClick = false;
        } else {
            rc_btn.setBackgroundResource(R.drawable.shape_3);
            rc_btn.setText("下一步");
            canClick = true;
        }
    }

    @OnClick(R.id.lc_time)
    public void lc_time() {
        if (lc_time.getText().toString().equals("重新获取验证码")) {
            if (isFlag) {
                SMSSDK.getVerificationCode("86", phoneNumber);
                new MyCount(60000, 1000).start();//一分钟倒计时
            }
        }
    }

    @OnClick(R.id.rc_btn)
    public void rc_btn() {
        if (!canClick)
            return;
        code = rc_code.getText().toString();
        if (!StringUtil.isEmpty(code)) {
            hideSoftMethod(rc_code);
            SMSSDK.submitVerificationCode("86", phoneNumber, rc_code.getText().toString().trim());
        } else
            snb("验证码不能为空", lc_time);
    }

    @Override
    protected CharSequence provideTitle() {
        return "验证码";
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_register_check;
    }

    @Override
    public void initListeners() {

    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        isFlag = true;
        phoneNumber = getIntent().getBundleExtra("data").getString("phoneNumber");
        rc_phone.setText(phoneNumber);

        SMSSDK.getVerificationCode("86", phoneNumber);
        new MyCount(60000, 1000).start();//一分钟倒计时


        EventHandler eh = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        // 提交验证码成功
                        startActivity(RegisterCreateActivity.class, new Bun().putString("phoneNumber", phoneNumber).ok());
                        ActivityUtil.addActivity(RegisterCheckActivity.this);
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
//                        snb("验证码已发送");
                        Log.e("GET_VERIFICATION_CODE", "SUCCESS");
                        //获取验证码成功
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表
                        SMSSDK.getSupportedCountries();
                    }
                }  else {
                    try {
                        snb(event == SMSSDK.EVENT_GET_VERIFICATION_CODE ? "验证过于频繁，请稍后再试" : "验证码错误", lc_time);
//                        startActivity(RegisterCreateActivity.class, new Bun().putString("phoneNumber", phoneNumber).ok());
//                        ActivityUtil.addActivity(RegisterCheckActivity.this);
                    } catch (Exception ex) {
                    }
                }
            }
        };
        SMSSDK.registerEventHandler(eh); //注册短信回调

    }


    /* 定义一个倒计时的内部类 */
    class MyCount extends CountDownTimer {
        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            isFlag = true;
            if (lc_time != null)
                lc_time.setText("重新获取验证码");
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if (lc_time != null)
                lc_time.setText("接收短信大约需要" + millisUntilFinished / 1000 + "s");
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }

    @Override
    public RegisterCheckPresenter createPresenter() {
        return new RegisterCheckPresenter();
    }


    @OnClick(R.id.img_back)
    public void onViewClicked() {
        finish();
    }
}