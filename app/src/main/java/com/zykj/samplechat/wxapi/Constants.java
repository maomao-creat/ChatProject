package com.zykj.samplechat.wxapi;
//com.apa.apaotui_shipper.wxapi  为微信支付包  复制到项目下(包名不要修改 必须放在项目根目录下)
//导包                compile files('libs/wechat-sdk-android-with-mta.jar') compile files('libs/wechat-sdk-android-without-mta.jar')
//微信专用包           compile 'com.tencent.mm.opensdk:wechat-sdk-android-with-mta:+'
// 添加配置信息 <activity android:name=".wxapi.WXPayEntryActivity" android:exported="true" android:launchMode="singleTop" />
//修改该类3个配置信息即可
//PayActivity  有详细支付流程 最下面 复制即可 无须修改
public class Constants {
    //appid 微信分配的公众账号ID
    private   String APP_ID = "wx58a8da7a415641ce";

    //商户号 微信分配的公众账号ID
    private   String MCH_ID = "1498748852";//1415675302
    //b9a67f483821a2bfaea9b511e6404db2  AppSecret:
//  API密钥，在商户平台设置
    private    String API_KEY= "paeKwvxT6fN2ilon18hSEa1EencpuXVJ";//ganzhoualpha112114115WXZHOUALPHA
    // ADF8AC8F05C2CBA8C043617E95138FFB
    private void Constants(){}
    private static Constants Constants=new Constants();
    public static Constants getConstants(){
        return Constants;
    }

    public  String getApiKey() {
        return API_KEY;
    }

    public  void setApiKey(String apiKey) {
        API_KEY = apiKey;
    }

    public  String getAppId() {
        return APP_ID;
    }

    public  void setAppId(String appId) {
        APP_ID = appId;
    }

    public  String getMchId() {
        return MCH_ID;
    }

    public  void setMchId(String mchId) {
        MCH_ID = mchId;
    }
    //应用签名
    //56f038b7176f53299e9583a3cae04ffa   打包之后的
    //21cab93d833e79faae4f60e70d502213   打包之前的
}