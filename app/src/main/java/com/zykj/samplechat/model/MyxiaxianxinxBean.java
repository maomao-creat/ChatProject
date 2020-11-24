package com.zykj.samplechat.model;

import java.io.Serializable;
import java.util.List;


/**
 * Created by 11655 on 2016/10/18.
 */

public class MyxiaxianxinxBean implements Serializable {


        /**
         * SumData : null
         * Data : [{"NickName":"oy4hah","HeadUrl":null,"Date":"/Date(1537427482163)/","CreateDate":"2018/9/20 15:11:22","TJNickName":"cesttwyuqq","TJUserId":"485ef053-3044-4390-8bc2-a95f01130dbb"}]
         * ItemCount : 1
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
             * NickName : oy4hah
             * HeadUrl : null
             * Date : /Date(1537427482163)/
             * CreateDate : 2018/9/20 15:11:22
             * TJNickName : cesttwyuqq
             * TJUserId : 485ef053-3044-4390-8bc2-a95f01130dbb
             */

            private String NickName;
            private String HeadUrl;
            private String Date;
            private String CreateDate;
            private String TJNickName;
            private String TJUserId;

            public String getForMeAmount() {
                return ForMeAmount;
            }

            public void setForMeAmount(String forMeAmount) {
                ForMeAmount = forMeAmount;
            }

            private String ForMeAmount;
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

            public String getTJNickName() {
                return TJNickName;
            }

            public void setTJNickName(String TJNickName) {
                this.TJNickName = TJNickName;
            }

            public String getTJUserId() {
                return TJUserId;
            }

            public void setTJUserId(String TJUserId) {
                this.TJUserId = TJUserId;
            }
        }

}

