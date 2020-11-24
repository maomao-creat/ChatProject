package com.zykj.samplechat.ui.activity;

import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zykj.samplechat.R;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.presenter.WalletCongZhiPresenter;
import com.zykj.samplechat.presenter.base.HotWeiXinBean;
import com.zykj.samplechat.presenter.base.HotZhiFuBaoBean;
import com.zykj.samplechat.ui.activity.base.AuthResult;
import com.zykj.samplechat.ui.activity.base.PayResult;
import com.zykj.samplechat.ui.activity.base.ToolBarActivity;
import com.zykj.samplechat.ui.view.SendCongZhiView;
import com.zykj.samplechat.utils.AESOperator;
import com.zykj.samplechat.utils.StringUtil;
import com.zykj.samplechat.utils.UserUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

//activity_cong_zhi
public class CongZhiActivity extends ToolBarActivity<WalletCongZhiPresenter> implements SendCongZhiView {
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_action)
    TextView tvAction;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @Bind(R.id.tv_jine)
    EditText tvJine;
    @Bind(R.id.rb_50)
    RadioButton rb50;
    @Bind(R.id.rb_100)
    RadioButton rb100;
    @Bind(R.id.rb_200)
    RadioButton rb200;
    @Bind(R.id.rb_500)
    RadioButton rb500;
    @Bind(R.id.rb_1000)
    RadioButton rb1000;
    @Bind(R.id.rb_5000)
    RadioButton rb5000;
    @Bind(R.id.cb_weixin)
    CheckBox cbWeixin;
    @Bind(R.id.ll_weixin)
    LinearLayout llWeixin;
    @Bind(R.id.cb_zhifubao)
    CheckBox cbZhifubao;
    @Bind(R.id.ll_zhifubao)
    LinearLayout llZhifubao;
    @Bind(R.id.queding)
    Button queding;
    private int biaoshi = 1;//1微信 2支付宝
    private IWXAPI iwxapi; //微信支付api
    HotWeiXinBean qian;
    @Override
    public void success(final HotWeiXinBean qian) {
        this.qian=qian;
        weixinchongzhi();
    }
    public static final String RSA2_PRIVATE = "";
    public static final String RSA_PRIVATE = "";

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                     //   Toast.makeText(CongZhiActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        toast("支付成功");
                        finish();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                       // Toast.makeText(CongZhiActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                        toast("支付失败");
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(CongZhiActivity.this,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(CongZhiActivity.this,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        };
    };

    @Override
    public void zsuccess(HotZhiFuBaoBean qian) {//支付宝充值回调
        final String orderInfo = qian.getBody();   // 订单信息

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(CongZhiActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo,true);

                Message msg = new Message();
                msg.what = 1;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_cong_zhi;
    }

    @Override
    public void initListeners() {//初始化
    }
    void zhifubao(){//获取支付宝支付参数
        presenter.getzfb("1",tvJine.getText().toString().trim(),"朋友APP充值");
    }
    void chushihuaweixin(){//获取微信支付参数
        iwxapi = WXAPIFactory.createWXAPI(getContext(), null); //初始化微信api
        iwxapi.registerApp("wx58a8da7a415641ce"); //注册appid  appid可以在开发平台获取
        Map map = new HashMap();
        map.put("key", Const.KEY);
        map.put("uid", Const.UID);
        map.put("function", "getWXPayParameters");
        map.put("userId", new UserUtil(getContext()).getUserId());
        map.put("productId","1");
        map.put("amount",""+tvJine.getText().toString().trim());
        map.put("goodsDescription","朋友APP充值");
        map.put("code","1");
        String json = StringUtil.toJson(map);
        try {
            String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
            presenter.getce(data);
        } catch (Exception ex) {
            dismissDialog();
        }
    }
    @Override
    public WalletCongZhiPresenter createPresenter() {
        return new WalletCongZhiPresenter();
    }

    @Override
    protected CharSequence provideTitle() {
        return "充值";
    }


    @OnClick({R.id.rb_50, R.id.rb_100, R.id.rb_200, R.id.rb_500, R.id.rb_1000, R.id.rb_5000, R.id.cb_weixin, R.id.ll_weixin, R.id.cb_zhifubao, R.id.ll_zhifubao, R.id.queding})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_50:
                chushihua();
                rb50.setChecked(true);
                tvJine.setText("50");
                break;
            case R.id.rb_100:
                chushihua();
                rb100.setChecked(true);
                tvJine.setText("100");
                break;
            case R.id.rb_200:
                chushihua();
                rb200.setChecked(true);
                tvJine.setText("200");
                break;
            case R.id.rb_500:
                chushihua();
                rb500.setChecked(true);
                tvJine.setText("500");
                break;
            case R.id.rb_1000:
                chushihua();
                rb1000.setChecked(true);
                tvJine.setText("1000");
                break;
            case R.id.rb_5000:
                chushihua();
                rb5000.setChecked(true);
                tvJine.setText("5000");
                break;
            case R.id.cb_weixin:
                cbWeixin.setChecked(true);
                cbZhifubao.setChecked(false);
                biaoshi=1;
                break;
            case R.id.ll_weixin:
                cbWeixin.setChecked(true);
                cbZhifubao.setChecked(false);
                biaoshi=1;
                break;
            case R.id.cb_zhifubao:
                cbZhifubao.setChecked(true);
                cbWeixin.setChecked(false);
                biaoshi=2;
                break;
            case R.id.ll_zhifubao:
                cbZhifubao.setChecked(true);
                cbWeixin.setChecked(false);
                biaoshi=2;
                break;
            case R.id.queding:
                if(cbZhifubao.isChecked()){
                    zhifubao();
                }else{
                    Toast.makeText(CongZhiActivity.this,"微信支付维护中，请联系客服或选择支付宝支付",Toast.LENGTH_LONG).show();
                   // chushihuaweixin();
                }

                break;
        }
    }
    void weixinchongzhi(){//吊起微信充值
        PayReq request = new PayReq(); //调起微信APP的对象
        //下面是设置必要的参数，也就是前面说的参数,这几个参数从何而来请看上面说明
        request.appId = "wx58a8da7a415641ce";
        request.partnerId = qian.getPartnerid();
        request.prepayId = qian.getPrepayid();
        request.packageValue = "Sign=WXPay";
        request.nonceStr = qian.getNoncestr();
        request.timeStamp = qian.getTimestamp()+"";
        request.sign = qian.getSign();
        iwxapi.sendReq(request);//发送调起微信的请求
//        OpenWebview.Req req = new OpenWebview.Req();
//        req.url = "www.baidu.com";
//        iwxapi.sendReq(req);

    }
    void chushihua(){
        rb50.setChecked(false);
        rb100.setChecked(false);
        rb200.setChecked(false);
        rb500.setChecked(false);
        rb1000.setChecked(false);
        rb5000.setChecked(false);
    }
}
