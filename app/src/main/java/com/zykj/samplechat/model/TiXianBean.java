package com.zykj.samplechat.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ninos on 2016/7/27.
 */
public class TiXianBean implements Serializable {


        /**
         * SumData : null
         * Data : [{"Id":"00c889a6-2983-488a-b2ec-a9620124c506","NickName":"cesttwyuqq","Amount":120,"Total":118.8,"UserCode":"023i37","ServiceFee":1.2,"Date":"/Date(1537523156493)/","CreateDate":"2018/9/21"},{"Id":"03f99df7-6668-4b0d-8824-a962011f5ec1","NickName":"cesttwyuqq","Amount":100,"Total":99,"UserCode":"023i37","ServiceFee":1,"Date":"/Date(1537521976960)/","CreateDate":"2018/9/21"}]
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
             * Id : 00c889a6-2983-488a-b2ec-a9620124c506
             * NickName : cesttwyuqq
             * Amount : 120
             * Total : 118.8
             * UserCode : 023i37
             * ServiceFee : 1.2
             * Date : /Date(1537523156493)/
             * CreateDate : 2018/9/21
             */

            private String Id;
            private String NickName;
            private double Amount;
            private int Status ;
            private double Total;
            private String UserCode;
            private double ServiceFee;
            private String Date;
            private String CreateDate;

            public int getStatus() {
                return Status;
            }

            public void setStatus(int status) {
                Status = status;
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

            public double getAmount() {
                return Amount;
            }

            public void setAmount(double Amount) {
                this.Amount = Amount;
            }

            public double getTotal() {
                return Total;
            }

            public void setTotal(double Total) {
                this.Total = Total;
            }

            public String getUserCode() {
                return UserCode;
            }

            public void setUserCode(String UserCode) {
                this.UserCode = UserCode;
            }

            public double getServiceFee() {
                return ServiceFee;
            }

            public void setServiceFee(double ServiceFee) {
                this.ServiceFee = ServiceFee;
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
