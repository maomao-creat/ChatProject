package com.zykj.samplechat.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ninos on 2016/7/27.
 */
public class ZhunZhangBean implements Serializable {


        /**
         * SumData : null
         * Data : [{"Id":"7f4c4ba7-6fb1-4dd0-b5bd-a96200c25a92","UserCode":"023i37","Amount":9999999,"Description":"第三方","IsCompanyIncome":false,"Date":"/Date(1537501657230)/","CreateDate":"2018/9/21"},{"Id":"a38ed0b1-47eb-4763-b52d-f7c318ffa29f","UserCode":"ces","Amount":101.25,"Description":"一级分销收益，来自g2ri30","IsCompanyIncome":false,"Date":"/Date(1537404547763)/","CreateDate":"2018/9/20"},{"Id":"ddeda7c3-7b07-404c-bd43-0d2cb11a3d78","UserCode":"ces","Amount":200,"Description":"一级分销收益，来自g2ri30","IsCompanyIncome":false,"Date":"/Date(1537404539067)/","CreateDate":"2018/9/20"},{"Id":"67c431f5-67c3-4873-8fda-cd92ffaf2c90","UserCode":"ces","Amount":100,"Description":"一级分销收益，来自g2ri30","IsCompanyIncome":false,"Date":"/Date(1537404532467)/","CreateDate":"2018/9/20"},{"Id":"8a4d65c9-c5f3-49f5-a0dc-eed6207e0d92","UserCode":"ces","Amount":101.25,"Description":"一级分销收益，来自g2ri30","IsCompanyIncome":false,"Date":"/Date(1537326671000)/","CreateDate":"2018/9/19"},{"Id":"84cfdd61-3f0a-4afb-a6e7-a53a73979360","UserCode":"ces","Amount":101.25,"Description":"一级分销收益，来自g2ri30","IsCompanyIncome":false,"Date":"/Date(1537326671000)/","CreateDate":"2018/9/19"}]
         * ItemCount : 6
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

        public static class DataBean implements Serializable {
            /**
             * Id : 7f4c4ba7-6fb1-4dd0-b5bd-a96200c25a92
             * UserCode : 023i37
             * Amount : 9999999
             * Description : 第三方
             * IsCompanyIncome : false
             * Date : /Date(1537501657230)/
             * CreateDate : 2018/9/21
             */

            private String Id;
            private String UserCode;
            private Double Amount;
            private String Description;
            private boolean IsCompanyIncome;
            private String Date;
            private String CreateDate;

            public String getPackId() {
                return PackId;
            }

            public void setPackId(String packId) {
                PackId = packId;
            }

            private String PackId;

            public String getTime() {
                return Time;
            }

            public void setTime(String time) {
                Time = time;
            }

            private String Time;
            public String getFiName() {
                return FiName;
            }

            public void setFiName(String fiName) {
                FiName = fiName;
            }

            private String FiName;

            public String getId() {
                return Id;
            }

            public void setId(String Id) {
                this.Id = Id;
            }

            public String getUserCode() {
                return UserCode;
            }

            public void setUserCode(String UserCode) {
                this.UserCode = UserCode;
            }

            public Double getAmount() {
                return Amount;
            }

            public void setAmount(Double Amount) {
                this.Amount = Amount;
            }

            public String getDescription() {
                return Description;
            }

            public void setDescription(String Description) {
                this.Description = Description;
            }

            public boolean isIsCompanyIncome() {
                return IsCompanyIncome;
            }

            public void setIsCompanyIncome(boolean IsCompanyIncome) {
                this.IsCompanyIncome = IsCompanyIncome;
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
