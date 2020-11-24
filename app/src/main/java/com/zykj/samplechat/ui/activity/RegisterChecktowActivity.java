package com.zykj.samplechat.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.EditText;
import android.widget.TextView;

import com.mob.MobSDK;
import com.rey.material.widget.Button;
import com.zykj.samplechat.R;
import com.zykj.samplechat.presenter.RegisterCheckPresenter;
import com.zykj.samplechat.ui.activity.base.ToolBarActivity;
import com.zykj.samplechat.ui.view.RegisterCheckView;
import com.zykj.samplechat.utils.ActivityUtil;
import com.zykj.samplechat.utils.Bun;
import com.zykj.samplechat.utils.StringUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
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
public class RegisterChecktowActivity extends ToolBarActivity<RegisterCheckPresenter> implements RegisterCheckView {

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
        if (lc_time.getText().toString().equals("重新获取验证码"))
            sendMessageCode();
    }

    @OnClick(R.id.rc_btn)
    public void rc_btn() {
        if (!canClick)
            return;
        code = rc_code.getText().toString();
        if (!StringUtil.isEmpty(code)) {
//            if (code.equals(random + "")) {
//                startActivity(RegisterCreateActivity.class, new Bun().putString("phoneNumber", phoneNumber).ok());
//                ActivityUtil.addActivity(RegisterCheckActivity.this);
//            }else{
//                snb("验证失败，验证码不一致",lc_time);
//            }
            SMSSDK.submitVerificationCode("86", phoneNumber, code);
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
        phoneNumber = getIntent().getBundleExtra("data").getString("phoneNumber");

        rc_phone.setText(phoneNumber);

        // 短信验证
        // 初始化短信验证
//        MobSDK.init(this, "2a498d803ecdc", "0d4cc780c8a82d0d92faa82af5d2df5b");
        //MobSDK.init(this, "23997cf59730e", "aa90a964442dd81511f4d6129e781010");
        EventHandler eh = new EventHandler() {

            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handlersms.sendMessage(msg);
            }
        };
        SMSSDK.registerEventHandler(eh); // 注册短信回调

        sendMessageCode();
    }

    /**
     * 验证码时间倒计时
     */
    Runnable runnabletime = new Runnable() {
        @Override
        public void run() {
            time--;
            if (time >= 0) {
                lc_time.setText("接收短信大约需要" + time + "秒");
                handler.postDelayed(this, 1000);
            } else {
                lc_time.setText("重新获取验证码");
            }
        }
    };

    /**
     * 手机发送验证码
     */
    public void sendMessageCode() {
        lc_time.setText("接收短信大约需要" + TIME_INIT + "秒");
        time = TIME_INIT;
        handler.postDelayed(runnabletime, 1000);
        SMSSDK.getVerificationCode("86", phoneNumber);
    }

//    /**
//     *手机发送验证码
//     */
//    public void sendMessageCode() {
//        random=(int)(Math.random()*999999+100000);
//        lc_time.setText("接收短信大约需要"+TIME_INIT+"秒");
//        time=TIME_INIT;
//        handler.postDelayed(runnabletime, 1000);
//        presenter.checkNum(phoneNumber,"【朋友】尊敬的用户您好，感谢您注册朋友账号，本次注册的验证码为："+random+"，请妥善保管");
//    }

    /**
     * 创建可调用ui的handler
     */
    Handler handlersms = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;

            if (result == SMSSDK.RESULT_COMPLETE) {
                // 回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    // 提交验证码成功
                    startActivity(RegisterCreatetwoActivity.class, new Bun().putString("phoneNumber", phoneNumber).ok());
                    ActivityUtil.addActivity(RegisterChecktowActivity.this);
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    // 获取验证码成功
                    snb("验证码已经发送", lc_time);
                    //} else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                    // 返回支持发送验证码的国家列表
                }
            } else {
                try {
                    snb(event == SMSSDK.EVENT_GET_VERIFICATION_CODE ? "验证过于频繁，请稍后再试" : "验证码错误", lc_time);
//                    startActivity(RegisterCreateActivity.class, new Bun().putString("phoneNumber", phoneNumber).ok());
//                    ActivityUtil.addActivity(RegisterCheckActivity.this);
                } catch (Exception ex) {
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
        handler.removeCallbacks(runnabletime);
    }

    @Override
    public RegisterCheckPresenter createPresenter() {
        return new RegisterCheckPresenter();
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