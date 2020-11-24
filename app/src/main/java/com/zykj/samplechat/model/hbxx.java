package com.zykj.samplechat.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ninos on 2016/7/27.
 */
public class hbxx implements Serializable {

        /**
         * NickName : cesttwyuqq
         * HeadUrl : UpLoad/Photo/80804a27-0fb2-4bb5-822b-35e7712bd3fa.jpg
         * Time :
         * TotalMoney : 111
         * TotalCount : 7
         * LastCount : 5
         * Lei : 1
         * UserList : [{"NickName":"免死号","HeadUrl":"/upload/user/2018092709335972988.jpg","OpenTime":"09月27日 17:38:22","Amount":20.33,"IsLuck":1},{"NickName":"cesttwyuqq","HeadUrl":"UpLoad/Photo/80804a27-0fb2-4bb5-822b-35e7712bd3fa.jpg","OpenTime":"09月27日 17:38:37","Amount":12.42,"IsLuck":0}]
         */

        private String NickName;
        private String HeadUrl;
        private String Time;
        private Double TotalMoney;
        private int TotalCount;
        private int LastCount;
        private int Lei;
        private List<UserListBean> UserList;

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

        public String getTime() {
            return Time;
        }

        public void setTime(String Time) {
            this.Time = Time;
        }

        public Double getTotalMoney() {
            return TotalMoney;
        }

        public void setTotalMoney(Double TotalMoney) {
            this.TotalMoney = TotalMoney;
        }

        public int getTotalCount() {
            return TotalCount;
        }

        public void setTotalCount(int TotalCount) {
            this.TotalCount = TotalCount;
        }

        public int getLastCount() {
            return LastCount;
        }

        public void setLastCount(int LastCount) {
            this.LastCount = LastCount;
        }

        public int getLei() {
            return Lei;
        }

        public void setLei(int Lei) {
            this.Lei = Lei;
        }

        public List<UserListBean> getUserList() {
            return UserList;
        }

        public void setUserList(List<UserListBean> UserList) {
            this.UserList = UserList;
        }

        public static class UserListBean {
            /**
             * NickName : 免死号
             * HeadUrl : /upload/user/2018092709335972988.jpg
             * OpenTime : 09月27日 17:38:22
             * Amount : 20.33
             * IsLuck : 1
             */

            private String NickName;
            private String HeadUrl;
            private String OpenTime;
            private double Amount;
            private int IsLuck;

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

            public String getOpenTime() {
                return OpenTime;
            }

            public void setOpenTime(String OpenTime) {
                this.OpenTime = OpenTime;
            }

            public double getAmount() {
                return Amount;
            }

            public void setAmount(double Amount) {
                this.Amount = Amount;
            }

            public int getIsLuck() {
                return IsLuck;
            }

            public void setIsLuck(int IsLuck) {
                this.IsLuck = IsLuck;
            }
        }

}

