package com.zykj.samplechat.model;

import java.io.Serializable;


/**
 * Created by 11655 on 2016/10/18.
 */

public class agerenBean implements Serializable {


        /**
         * Id : 485ef053-3044-4390-8bc2-a95f01130dbb
         * NickName : cesttwyuqq
         * UserCode : 023i37
         * Sex : 1
         * RYToken : HsRPBSLmxmlgELH8O/t4jJFR+24vqKv9mGkRhWEh0bs97GraxtzeUMjj10yQL+yJcXWkgl1TsH0ZCwKMjsh3wQ==
         * TJUserCode : g2ri30
         * HeadUrl : UpLoad/Photo/80804a27-0fb2-4bb5-822b-35e7712bd3fa.jpg
         * Mobile : 15763444322
         * amount : 9999999
         * RealName : 语文i
         * AliCode : 15763444322
         * BankUser : null
         * BankName : null
         * BankCode : null
         * BankAddress : null
         * CashConfig : {"Min":100,"Multiple":10,"ServiceFeeBL":1}
         */

        private String Id;
        private String NickName;
        private String UserCode;
        private int Sex;
        private String RYToken;
        private String TJUserCode;
        private String HeadUrl;
        private String Mobile;
        private double amount;
        private String RealName;
        private String AliCode;
        private Object BankUser;
        private Object BankName;
        private Object BankCode;
        private Object BankAddress;
        private CashConfigBean CashConfig;

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

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getRealName() {
            return RealName;
        }

        public void setRealName(String RealName) {
            this.RealName = RealName;
        }

        public String getAliCode() {
            return AliCode;
        }

        public void setAliCode(String AliCode) {
            this.AliCode = AliCode;
        }

        public Object getBankUser() {
            return BankUser;
        }

        public void setBankUser(Object BankUser) {
            this.BankUser = BankUser;
        }

        public Object getBankName() {
            return BankName;
        }

        public void setBankName(Object BankName) {
            this.BankName = BankName;
        }

        public Object getBankCode() {
            return BankCode;
        }

        public void setBankCode(Object BankCode) {
            this.BankCode = BankCode;
        }

        public Object getBankAddress() {
            return BankAddress;
        }

        public void setBankAddress(Object BankAddress) {
            this.BankAddress = BankAddress;
        }

        public CashConfigBean getCashConfig() {
            return CashConfig;
        }

        public void setCashConfig(CashConfigBean CashConfig) {
            this.CashConfig = CashConfig;
        }

        public static class CashConfigBean implements Serializable{
            /**
             * Min : 100//最小提现金额
             * Multiple : 10 //倍数
             * ServiceFeeBL : 1 //手续费
             */

            private Double Min;
            private Double Multiple;
            private Double ServiceFeeBL;

            public Double getMin() {
                return Min;
            }

            public void setMin(Double Min) {
                this.Min = Min;
            }

            public Double getMultiple() {
                return Multiple;
            }

            public void setMultiple(Double Multiple) {
                this.Multiple = Multiple;
            }

            public Double getServiceFeeBL() {
                return ServiceFeeBL;
            }

            public void setServiceFeeBL(Double ServiceFeeBL) {
                this.ServiceFeeBL = ServiceFeeBL;
            }
        }

}

