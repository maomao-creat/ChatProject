package com.zykj.samplechat.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ninos on 2016/7/27.
 */
public class sheqhyy implements Serializable {

        /**
         * SumData : null
         * Data : [{"UserId":"485ef053-3044-4390-8bc2-a95f01130dbb","FriendId":"6f95c5d7-bb01-47c2-9f8c-a96900f0a831","FHeadUrl":null,"FNickName":"啦啦啦111","Status":0,"StatusName":"申请中","Date":"/Date(1538116587967)/","CreateDate":"2018/9/28 14:36:27"},{"UserId":"485ef053-3044-4390-8bc2-a95f01130dbb","FriendId":"22482ba4-7df4-49bb-8be1-a96900abd9bf","FHeadUrl":null,"FNickName":"ty8q5h","Status":0,"StatusName":"申请中","Date":"/Date(1538116525623)/","CreateDate":"2018/9/28 14:35:25"}]
         * ItemCount : 2
         * PageSize : 10
         * PageNum : 1
         * PageCount : 1
         */

        private Object SumData;
        private int ItemCount;
        private int PageSize;
        private int PageNum;
        private int PageCount;
        private List<DataBean> Data;

        public Object getSumData() {
            return SumData;
        }

        public void setSumData(Object SumData) {
            this.SumData = SumData;
        }

        public int getItemCount() {
            return ItemCount;
        }

        public void setItemCount(int ItemCount) {
            this.ItemCount = ItemCount;
        }

        public int getPageSize() {
            return PageSize;
        }

        public void setPageSize(int PageSize) {
            this.PageSize = PageSize;
        }

        public int getPageNum() {
            return PageNum;
        }

        public void setPageNum(int PageNum) {
            this.PageNum = PageNum;
        }

        public int getPageCount() {
            return PageCount;
        }

        public void setPageCount(int PageCount) {
            this.PageCount = PageCount;
        }

        public List<DataBean> getData() {
            return Data;
        }

        public void setData(List<DataBean> Data) {
            this.Data = Data;
        }

        public static class DataBean {
            /**
             * UserId : 485ef053-3044-4390-8bc2-a95f01130dbb
             * FriendId : 6f95c5d7-bb01-47c2-9f8c-a96900f0a831
             * FHeadUrl : null
             * FNickName : 啦啦啦111
             * Status : 0
             * StatusName : 申请中
             * Date : /Date(1538116587967)/
             * CreateDate : 2018/9/28 14:36:27
             */

            private String UserId;
            private String FriendId;
            private String FHeadUrl;
            private String FNickName;
            private int Status;
            private String StatusName;
            private String Date;
            private String CreateDate;

            public String getUserId() {
                return UserId;
            }

            public void setUserId(String UserId) {
                this.UserId = UserId;
            }

            public String getFriendId() {
                return FriendId;
            }

            public void setFriendId(String FriendId) {
                this.FriendId = FriendId;
            }

            public String getFHeadUrl() {
                return FHeadUrl;
            }

            public void setFHeadUrl(String FHeadUrl) {
                this.FHeadUrl = FHeadUrl;
            }

            public String getFNickName() {
                return FNickName;
            }

            public void setFNickName(String FNickName) {
                this.FNickName = FNickName;
            }

            public int getStatus() {
                return Status;
            }

            public void setStatus(int Status) {
                this.Status = Status;
            }

            public String getStatusName() {
                return StatusName;
            }

            public void setStatusName(String StatusName) {
                this.StatusName = StatusName;
            }

            public String getDate() {
                return Date;
            }

            public void setDate(String Date) {
                this.Date = Date;
            }

            public String getCreateDate() {
                return CreateDate;
            }

            public void setCreateDate(String CreateDate) {
                this.CreateDate = CreateDate;
            }
        }

}

