package com.zykj.samplechat.presenter;


/**
 * 精品店铺的bean对象
 * Created by 11655 on 2016/10/14.
 */

public class WanJiaListBean {

        /**
         * Id : 2289
         * UserName : 10000
         * NicName : 桑尼号(机器人)
         * RealName :
         * RegTime : 1900-01-01
         * IsClose : false
         * TeamName :
         * TeamId : 0
         * IsTeacher : 0
         * IsRecommend : false
         * Credit : 1
         * IsIndexShow : true
         * IsRecommendTeacher : false
         * RecommendCode :
         * PhotoPath : UpLoad/Product/8c55eea7-ade2-4732-8952-a8c994e0fd7b.jpg
         */

        private int Id;
        private String UserName;
        private String NicName;
        private String RealName;
        private String RegTime;
        private boolean IsClose;
        private String TeamName;
        private int TeamId;
        private int IsTeacher;
        private boolean IsRecommend;
        private int Credit;
        private boolean IsIndexShow;
        private boolean IsRecommendTeacher;
        private String RecommendCode;
        private String PhotoPath;

    public String getPackId() {
        return PackId;
    }

    public void setPackId(String packId) {
        PackId = packId;
    }

    private String PackId;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }

        public String getNicName() {
            return NicName;
        }

        public void setNicName(String NicName) {
            this.NicName = NicName;
        }

        public String getRealName() {
            return RealName;
        }

        public void setRealName(String RealName) {
            this.RealName = RealName;
        }

        public String getRegTime() {
            return RegTime;
        }

        public void setRegTime(String RegTime) {
            this.RegTime = RegTime;
        }

        public boolean isIsClose() {
            return IsClose;
        }

        public void setIsClose(boolean IsClose) {
            this.IsClose = IsClose;
        }

        public String getTeamName() {
            return TeamName;
        }

        public void setTeamName(String TeamName) {
            this.TeamName = TeamName;
        }

        public int getTeamId() {
            return TeamId;
        }

        public void setTeamId(int TeamId) {
            this.TeamId = TeamId;
        }

        public int getIsTeacher() {
            return IsTeacher;
        }

        public void setIsTeacher(int IsTeacher) {
            this.IsTeacher = IsTeacher;
        }

        public boolean isIsRecommend() {
            return IsRecommend;
        }

        public void setIsRecommend(boolean IsRecommend) {
            this.IsRecommend = IsRecommend;
        }

        public int getCredit() {
            return Credit;
        }

        public void setCredit(int Credit) {
            this.Credit = Credit;
        }

        public boolean isIsIndexShow() {
            return IsIndexShow;
        }

        public void setIsIndexShow(boolean IsIndexShow) {
            this.IsIndexShow = IsIndexShow;
        }

        public boolean isIsRecommendTeacher() {
            return IsRecommendTeacher;
        }

        public void setIsRecommendTeacher(boolean IsRecommendTeacher) {
            this.IsRecommendTeacher = IsRecommendTeacher;
        }

        public String getRecommendCode() {
            return RecommendCode;
        }

        public void setRecommendCode(String RecommendCode) {
            this.RecommendCode = RecommendCode;
        }

        public String getPhotoPath() {
            return PhotoPath;
        }

        public void setPhotoPath(String PhotoPath) {
            this.PhotoPath = PhotoPath;
        }
    }

