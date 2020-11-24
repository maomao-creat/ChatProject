package com.zykj.samplechat.wxapi;

/**
 * Created by Administrator on 2017/8/30.
 */

public class dingdanBean {//保存 修改支付状态的 单利
    private String usercode;
    private String code;
    private static dingdanBean  dingdan  = new dingdanBean();
    private dingdanBean(){}
    public static dingdanBean getdingdanBean(){
        return dingdan;
    }


    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
