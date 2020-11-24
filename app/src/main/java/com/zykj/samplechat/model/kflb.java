package com.zykj.samplechat.model;

import java.io.Serializable;

/**
 * Created by ninos on 2016/7/27.
 */
public class kflb implements Serializable {


        /**
         * UserId : 635e13bd-7f22-4da6-a9e4-a95f011171a7
         * xxxx : 635e13bd-7f22-4da6-a9e4-a95f011171a7
         * NickName : 测试1号
         * HeadUrl : null
         * CreateDate : 09 18 2018  4:35PM
         */

        private String UserId;
        private String xxxx;
        private String NickName;
        private String HeadUrl;
        private String CreateDate;
        public String UserCode;

        public String getUserId() {
            return UserId;
        }

        public void setUserId(String UserId) {
            this.UserId = UserId;
        }

        public String getXxxx() {
            return xxxx;
        }

        public void setXxxx(String xxxx) {
            this.xxxx = xxxx;
        }

        public String getNickName() {
            return NickName;
        }

        public void setNickName(String NickName) {
            this.NickName = NickName;
        }

        public String getHeadUrl() {
            return HeadUrl;
        }

        public void setHeadUrl(String HeadUrl) {
            this.HeadUrl = HeadUrl;
        }

        public String getCreateDate() {
            return CreateDate;
        }

        public void setCreateDate(String CreateDate) {
            this.CreateDate = CreateDate;
        }

}

