package com.zykj.samplechat.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zykj.samplechat.R;

//e8ca07a771d36861dfb419d73af458d3  公司电脑未打包
//2dbf71b4cc320986fc1ebf7fb9e75d04  公司电脑打包
//6e49a5c53819428fe78c02caf3895c17  我的电脑 未打包
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;
    private static final String APP_ID = "wx58a8da7a415641ce";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxpay_entry2);
        api = WXAPIFactory.createWXAPI(this, APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    /**
     * 得到支付结果回调
     */
    @Override
    public void onResp(BaseResp resp) {
        if( resp.errCode==0){
            Toast.makeText(WXPayEntryActivity.this,"充值成功",Toast.LENGTH_LONG).show();
           finish();
        }else{
            Toast.makeText(WXPayEntryActivity.this,"充值失败",Toast.LENGTH_LONG).show();
          finish();
        }
    }
    public  void uopdatacode(String usercode,String code) {

    }
}