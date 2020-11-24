package com.zykj.samplechat.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ninos on 2016/7/27.
 */
public class mYshouyi implements Serializable {

        /**
         * SumData : null
         * Data : [{"Amount":101.25,"Description":"一级分销收益，来自g2ri30","CreateDate":"/Date(1537404547763)/"},{"Amount":200,"Description":"一级分销收益，来自g2ri30","CreateDate":"/Date(1537404539067)/"},{"Amount":100,"Description":"一级分销收益，来自g2ri30","CreateDate":"/Date(1537404532467)/"},{"Amount":101.25,"Description":"一级分销收益，来自g2ri30","CreateDate":"/Date(1537326671000)/"},{"Amount":101.25,"Description":"一级分销收益，来自g2ri30","CreateDate":"/Date(1537326671000)/"}]
         * ItemCount : 5
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

        public static class DataBean implements Serializable{
            /**
             * Amount : 101.25
             * Description : 一级分销收益，来自g2ri30
             * CreateDate : /Date(1537404547763)/
             */

            private double Amount;
            private String Description;
            private String CreateDate;

            public String getPackId() {
                return PackId;
            }

            public void setPackId(String packId) {
                PackId = packId;
            }

            private String PackId;
            public double getAmount() {
                return Amount;
            }

            public void setAmount(double Amount) {
                this.Amount = Amount;
            }

            public String getDescription() {
                return Description;
            }

            public void setDescription(String Description) {
                this.Description = Description;
            }

            public String getCreateDate() {
                return CreateDate;
            }

            public void setCreateDate(String CreateDate) {
                this.CreateDate = CreateDate;
            }
        }

}
