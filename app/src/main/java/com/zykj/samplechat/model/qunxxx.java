package com.zykj.samplechat.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ninos on 2016/7/27.
 */
public class qunxxx implements Serializable {

        /**
         * query : [{"userid":"635e13bd-7f22-4da6-a9e4-a95f011171a7","UserCode":"g2ri30","NickName":"测试1号","HeadUrl":null,"Status":0,"IsTeamManager":true},{"userid":"979086bc-de2a-47f2-8322-a95f0111c531","UserCode":"ta18n3","NickName":"免死号","HeadUrl":"/upload/user/2018092709335972988.jpg","Status":0,"IsTeamManager":true},{"userid":"7c0d8c7b-a938-4c05-a992-a9630114f038","UserCode":"8vm79l","NickName":"8vm79l","HeadUrl":"UpLoad/Photo/13997846-38d8-4cca-b0e9-0bb369eed945.jpg","Status":0,"IsTeamManager":false},{"userid":"485ef053-3044-4390-8bc2-a95f01130dbb","UserCode":"023i37","NickName":"cesttwyuqq","HeadUrl":"UpLoad/Photo/80804a27-0fb2-4bb5-822b-35e7712bd3fa.jpg","Status":0,"IsTeamManager":false},{"userid":"22482ba4-7df4-49bb-8be1-a96900abd9bf","UserCode":"ty8q5h","NickName":"ty8q5h","HeadUrl":null,"Status":0,"IsTeamManager":false},{"userid":"6f95c5d7-bb01-47c2-9f8c-a96900f0a831","UserCode":"fh4ghf","NickName":"啦啦啦111","HeadUrl":null,"Status":0,"IsTeamManager":false}]
         * TeamInfoData : {"Name":"红包2(30-500)7包1.5倍","ImagePath":"/upload/user/2018092711064188196.png","teamAnnouncement":null}
         */

        private TeamInfoDataBean TeamInfoData;
        private List<QueryBean> query;

        public TeamInfoDataBean getTeamInfoData() {
            return TeamInfoData;
        }

        public void setTeamInfoData(TeamInfoDataBean TeamInfoData) {
            this.TeamInfoData = TeamInfoData;
        }

        public List<QueryBean> getQuery() {
            return query;
        }

        public void setQuery(List<QueryBean> query) {
            this.query = query;
        }

        public static class TeamInfoDataBean {
            /**
             * Name : 红包2(30-500)7包1.5倍
             * ImagePath : /upload/user/2018092711064188196.png
             * teamAnnouncement : null
             */

            private String Name;
            private String ImagePath;
            private String teamAnnouncement;

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }

            public String getImagePath() {
                return ImagePath;
            }

            public void setImagePath(String ImagePath) {
                this.ImagePath = ImagePath;
            }

            public String getTeamAnnouncement() {
                return teamAnnouncement;
            }

            public void setTeamAnnouncement(String teamAnnouncement) {
                this.teamAnnouncement = teamAnnouncement;
            }
        }

        public static class QueryBean {
            /**
             * userid : 635e13bd-7f22-4da6-a9e4-a95f011171a7
             * UserCode : g2ri30
             * NickName : 测试1号
             * HeadUrl : null
             * Status : 0
             * IsTeamManager : true
             */

            private String userid;
            private String UserCode;
            private String NickName;
            private String HeadUrl;
            private int Status;
            private boolean IsTeamManager;

            public String getUserid() {
                return userid;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }

            public String getUserCode() {
                return UserCode;
            }

            public void setUserCode(String UserCode) {
                this.UserCode = UserCode;
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

            public int getStatus() {
                return Status;
            }

            public void setStatus(int Status) {
                this.Status = Status;
            }

            public boolean isIsTeamManager() {
                return IsTeamManager;
            }

            public void setIsTeamManager(boolean IsTeamManager) {
                this.IsTeamManager = IsTeamManager;
            }
        }

}

