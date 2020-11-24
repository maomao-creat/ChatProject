package com.zykj.samplechat.presenter.base;


/**
 * Created by 11655 on 2016/10/14.
 */

public class HotZhiFuBaoBean {

        /**
         * OutTradeNo : null
         * SellerId : null
         * TotalAmount : null
         * TradeNo : null
         * Code : null
         * Msg : null
         * SubCode : null
         * SubMsg : null
         * Body : app_id=2018032602448116&biz_content=%7b%22body%22%3a%221%22%2c%22out_trade_no%22%3a%22636603354900898438%22%2c%22product_code%22%3a%22QUICK_MSECURITY_PAY%22%2c%22subject%22%3a%22%e6%9c%8b%e5%8f%8b%e7%a4%be%e4%ba%a4%e5%85%85%e5%80%bc%22%2c%22timeout_express%22%3a%2230m%22%2c%22total_amount%22%3a%221%22%7d&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3a%2f%2f122.114.222.153%3a8015%2fAdmin%2fHander%2falinotify.ashx&sign_type=RSA2&timestamp=2018-04-26+10%3a31%3a30&version=1.0&sign=a%2bPYAStXvhUC6esLJbTlEeN3zoeSn%2b7qpfgeiP5TIgWr8oSCujXOoTTvNc4m4LWUW7NPx7t%2bOE5dnKRNF9HL98O%2b%2bLTGm78ZCNugzs9lmwJDl8dkD9cmQt8jnbwfgr4V8A9gAwFc4Xv%2bnbK4cvTUZu9AcCsHEVe6GJ775tA9nfdBqIoG0kg3PTUXppe1Kj0TQ4MqP9LTuVaqXz166Vse878FgL7U2mLP8HdvJ5YJXy2sbXcb6%2f90r%2fRSMJNFHzrClBYuCRYX4xtQ1mf8AkOHfTiouKh5uANkB0D6LDGmQ2MwSMysVm2glpBO%2b61jdoVTjRW70%2bqmFdCu%2f9Rwn4F4wg%3d%3d
         * IsError : false
         */

        private Object OutTradeNo;
        private Object SellerId;
        private Object TotalAmount;
        private Object TradeNo;
        private Object Code;
        private Object Msg;
        private Object SubCode;
        private Object SubMsg;
        private String Body;
        private boolean IsError;

        public Object getOutTradeNo() {
            return OutTradeNo;
        }

        public void setOutTradeNo(Object OutTradeNo) {
            this.OutTradeNo = OutTradeNo;
        }

        public Object getSellerId() {
            return SellerId;
        }

        public void setSellerId(Object SellerId) {
            this.SellerId = SellerId;
        }

        public Object getTotalAmount() {
            return TotalAmount;
        }

        public void setTotalAmount(Object TotalAmount) {
            this.TotalAmount = TotalAmount;
        }

        public Object getTradeNo() {
            return TradeNo;
        }

        public void setTradeNo(Object TradeNo) {
            this.TradeNo = TradeNo;
        }

        public Object getCode() {
            return Code;
        }

        public void setCode(Object Code) {
            this.Code = Code;
        }

        public Object getMsg() {
            return Msg;
        }

        public void setMsg(Object Msg) {
            this.Msg = Msg;
        }

        public Object getSubCode() {
            return SubCode;
        }

        public void setSubCode(Object SubCode) {
            this.SubCode = SubCode;
        }

        public Object getSubMsg() {
            return SubMsg;
        }

        public void setSubMsg(Object SubMsg) {
            this.SubMsg = SubMsg;
        }

        public String getBody() {
            return Body;
        }

        public void setBody(String Body) {
            this.Body = Body;
        }

        public boolean isIsError() {
            return IsError;
        }

        public void setIsError(boolean IsError) {
            this.IsError = IsError;
        }
    }

