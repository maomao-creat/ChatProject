package com.zykj.samplechat.model;

import java.io.Serializable;

/**
 * Created by ninos on 2016/7/27.
 */
public class jyjl implements Serializable {


        /**
         * Id : 0e3033e0-fc39-43bb-9488-a97000ed311a
         * Amount : 146.03
         * CreateDate : 10 5 2018 2:23PM
         * Description : 超时退回146.03
         * IsCompanyIncome : true
         */

        private String Id;
        private double Amount;
        private String CreateDate;
        private String Description;
        private String Time;
        private String FiName;
        private boolean IsCompanyIncome;

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getFiName() {
        return FiName;
    }

    public void setFiName(String fiName) {
        FiName = fiName;
    }

    public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public double getAmount() {
            return Amount;
        }

        public void setAmount(double Amount) {
            this.Amount = Amount;
        }

        public String getCreateDate() {
            return CreateDate;
        }

        public void setCreateDate(String CreateDate) {
            this.CreateDate = CreateDate;
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

}

