package com.zykj.samplechat.model;

import java.io.Serializable;

/**
 * Created by ninos on 2016/7/27.
 */
public class MyshouyiBean implements Serializable {

        /**
         * YestodayAmount : 0
         * TodayAmount : 0
         * AllAmount : 0
         */

        private Double YestodayAmount;
        private Double TodayAmount;
        private Double AllAmount;

        public Double getYestodayAmount() {
            return YestodayAmount;
        }

        public void setYestodayAmount(Double YestodayAmount) {
            this.YestodayAmount = YestodayAmount;
        }

        public Double getTodayAmount() {
            return TodayAmount;
        }

        public void setTodayAmount(Double TodayAmount) {
            this.TodayAmount = TodayAmount;
        }

        public Double getAllAmount() {
            return AllAmount;
        }

        public void setAllAmount(Double AllAmount) {
            this.AllAmount = AllAmount;
        }

}
