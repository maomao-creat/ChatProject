package com.zykj.samplechat.presenter.base;


/**
 * Created by 11655 on 2016/10/14.
 */

public class HotWeiXinBean {
    private String timestamp;
    private String noncestr;
    private String prepayid;
  //  @SerializedName("package")
   // private String package;
    private String sign;
    private String partnerid;

    public HotWeiXinBean() {
    }

    public HotWeiXinBean(String timestamp, String noncestr, String prepayid, String sign, String partnerid) {
        this.timestamp = timestamp;
        this.noncestr = noncestr;
        this.prepayid = prepayid;
        this.sign = sign;
        this.partnerid = partnerid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }
}
