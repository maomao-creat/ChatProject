package com.zykj.samplechat.model;

import java.io.Serializable;


/**
 * Created by 11655 on 2016/10/18.
 */

public class sshyBean implements Serializable {


        /**
         * HeadUrl : null
         * Id : a97c0610-2bc0-4523-92cd-a963011b7f76
         * NickName : 皮广达
         * HeadUrl 头像
         Id 搜索出的会员userid
         NickName 昵称
         UserCode 会员编号
         IsFriend 是否已经好友 tru是 false不是、
         Sex 性别（1男0女）
         */

        private String HeadUrl;
        private String Id;
        private String NickName;
        private String UserCode ;
        private Boolean IsFriend  ;
        private int Sex   ;

    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String userCode) {
        UserCode = userCode;
    }

    public Boolean getFriend() {
        return IsFriend;
    }

    public void setFriend(Boolean friend) {
        IsFriend = friend;
    }

    public int getSex() {
        return Sex;
    }

    public void setSex(int sex) {
        Sex = sex;
    }

    public String getHeadUrl() {
            return HeadUrl;
        }

        public void setHeadUrl(String HeadUrl) {
            this.HeadUrl = HeadUrl;
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

}

