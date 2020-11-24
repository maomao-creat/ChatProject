package com.zykj.samplechat.model;

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
public class User {

        /**
         * Id : 485ef053-3044-4390-8bc2-a95f01130dbb
         * NickName : ces
         * UserCode : 023i37
         * Sex : 1
         * RYToken : rjgU+HqdtXxfVxTt3SFCrWbo1cl4U/FcrFndSbh8ei3tjBk/u0EW+2MxguKoGY/OvZS6jBGAr4jGmFtcuImxwUzhcbOQSx4dchb9HqXZnNPady8EMUx3yWdLqOmJVArZ0caVYcflmZY=
         * TJUserCode : g2ri30
         * HeadUrl : null
         * Mobile : 15763444322
         * IsManager : false
         * IsHavePayPassword : 0
         * Amount : 0
         */

        private String Id;
        private String NickName;
        private String UserCode;
        private int Sex;
        public String RYToken;
        private String TJUserCode;
        private String HeadUrl;
        private String Mobile;
        private String mm;//密码

    public String getOpenId() {
        return OpenId;
    }

    public void setOpenId(String OpenId) {
        this.OpenId = OpenId;
    }

    private String OpenId;
        private boolean IsManager;
        private int IsHavePayPassword;

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    private double amount;
    private String RealName;
    private String AliCode;
    private Object BankUser;
    private Object BankName;
    private Object BankCode;

    public boolean isManager() {
        return IsManager;
    }

    public void setManager(boolean manager) {
        IsManager = manager;
    }

    public String getAliCode() {
        return AliCode;
    }

    public void setAliCode(String aliCode) {
        AliCode = aliCode;
    }

    public Object getBankUser() {
        return BankUser;
    }

    public void setBankUser(Object bankUser) {
        BankUser = bankUser;
    }

    public Object getBankName() {
        return BankName;
    }

    public void setBankName(Object bankName) {
        BankName = bankName;
    }

    public Object getBankCode() {
        return BankCode;
    }

    public void setBankCode(Object bankCode) {
        BankCode = bankCode;
    }

    public Object getBankAddress() {
        return BankAddress;
    }

    public void setBankAddress(Object bankAddress) {
        BankAddress = bankAddress;
    }

    public agerenBean.CashConfigBean getCashConfig() {
        return CashConfig;
    }

    public void setCashConfig(agerenBean.CashConfigBean cashConfig) {
        CashConfig = cashConfig;
    }

    private Object BankAddress;
    private agerenBean.CashConfigBean CashConfig;


    public String getRealName() {
        return RealName;
    }

    public void setRealName(String realName) {
        RealName = realName;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    private String Token;

    public String getMm() {
        return mm;
    }

    public void setMm(String mm) {
        this.mm = mm;
    }

    public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public String getNickName() {
            return NickName;
        }

        public void setNickName(String NickName) {
            this.NickName = NickName;
        }

        public String getUserCode() {
            return UserCode;
        }

        public void setUserCode(String UserCode) {
            this.UserCode = UserCode;
        }

        public int getSex() {
            return Sex;
        }

        public void setSex(int Sex) {
            this.Sex = Sex;
        }

        public String getRYToken() {
            return RYToken;
        }

        public void setRYToken(String RYToken) {
            this.RYToken = RYToken;
        }

        public String getTJUserCode() {
            return TJUserCode;
        }

        public void setTJUserCode(String TJUserCode) {
            this.TJUserCode = TJUserCode;
        }

        public String getHeadUrl() {
            return HeadUrl;
        }

        public void setHeadUrl(String HeadUrl) {
            this.HeadUrl = HeadUrl;
        }

        public String getMobile() {
            return Mobile;
        }

        public void setMobile(String Mobile) {
            this.Mobile = Mobile;
        }

        public int getIsHavePayPassword() {
            return IsHavePayPassword;
        }

        public void setIsHavePayPassword(int IsHavePayPassword) {
            this.IsHavePayPassword = IsHavePayPassword;
        }

//    public int Id;
//    public String UserName;             // 用户名
//    public String NicName;              // 用户昵称
//    public String RealName;             // 真实姓名
//    public String Phone;                // 电话
//    public String RongCloudName;        // 融云token
//    public String Token;                // 融云token
//    public int Rank;                    // 级别
//    public String UserRankName;         // 级别名称
//    public int Credit;                  // 当前积分
//    public int CreditTotal;             // 总积分（升级的唯一标准）
//    public String PhotoPath;            // 头像地址
//    public int TeamId;                  // 战队ID
//    public String TeamName;             // 战队名称
//    public String HomeAddress;          // 家庭详细住址
//    public String HomeAreaprovinceName; // 所在省
//    public String HomeAreaCityName;     // 所在市
//    public String HomeAreaCountyName;   // 所在县区
//    public int HomeAreaId;              // 所在县区ID
//    public int Sexuality;               // 性别 0:未知 1：男 2：女
//    public int Height;                  // 身高 单位/cm
//    public int Weight;                  // 体重 单位/Kg
//    public int Experience;              // 习武经历 单位/年
//    public String Birthday;             // 生日
//    public boolean IsClose;             // 是否禁用
//    public boolean IsPay;               // 是否付费
//    public String OverTime;             // 付费到期时间
//    public boolean IsShieldComment;     // 是否屏蔽评论通知
//    public String Description;          // 个人简介
//    public String Address;              // 地址
//    public String RecommendCode;              // 邀请人ID
}
