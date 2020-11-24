package com.zykj.samplechat.model;

import java.io.Serializable;

/**
 * Created by ninos on 2016/7/27.
 */
public class MyHYLB implements Serializable {


        /**
         * UserId : 485ef053-3044-4390-8bc2-a95f01130dbb
         * xxxx : a97c0610-2bc0-4523-92cd-a963011b7f76
         * FriendId : a97c0610-2bc0-4523-92cd-a963011b7f76
         * NickName : 皮广达
         * HeadUrl : null
         * CreateDate : 09 25 2018  4:35PM
         * UserId 会员id
         FriendId 好友id
         NickName 好友昵称
         HeadUrl 好友头像
         UserCode//好友展示的 Id
         */

        private String UserId;
        private String topc;
        private String xxxx;
        private String FriendId;
        private String NickName;
        private String HeadUrl;
        private String CreateDate;
        private String UserCode;
        private boolean isSelected;
        public boolean isAdd;
    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String userCode) {
        UserCode = userCode;
    }

    public boolean getSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getTopc() {
        return topc;
    }

    public void setTopc(String topc) {
        this.topc = topc;
    }

    public String getUserId() {
            return FriendId;
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

        public String getFriendId() {
            return FriendId;
        }

        public void setFriendId(String FriendId) {
            this.FriendId = FriendId;
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
