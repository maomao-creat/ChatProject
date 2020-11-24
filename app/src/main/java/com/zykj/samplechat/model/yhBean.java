package com.zykj.samplechat.model;

import java.io.Serializable;


/**
 * Created by 11655 on 2016/10/18.
 */

public class yhBean implements Serializable {

        /**
         * HeadUrl : UpLoad/Photo/13997846-38d8-4cca-b0e9-0bb369eed945.jpg
         * UserCode : 8vm79l
         * Id : 7c0d8c7b-a938-4c05-a992-a9630114f038
         * NickName : 8vm79l
         * IsFriend : true
         * Sex : 1
         */

        private String HeadUrl;
        private String UserCode;
        private String Id;
        private String NickName;
        private boolean IsFriend;
        private int Sex;

        public String getHeadUrl() {
            return HeadUrl;
        }

        public void setHeadUrl(String HeadUrl) {
            this.HeadUrl = HeadUrl;
        }

        public String getUserCode() {
            return UserCode;
        }

        public void setUserCode(String UserCode) {
            this.UserCode = UserCode;
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

        public boolean isIsFriend() {
            return IsFriend;
        }

        public void setIsFriend(boolean IsFriend) {
            this.IsFriend = IsFriend;
        }

        public int getSex() {
            return Sex;
        }

        public void setSex(int Sex) {
            this.Sex = Sex;
        }
    }


