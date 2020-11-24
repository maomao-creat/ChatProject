package com.zykj.samplechat.model;

import java.io.Serializable;

/**
 * Created by ninos on 2016/7/27.
 */
public class tj implements Serializable {


        /**
         * Id : 1
         * Name : 发红包
         */

        private int Id;
        private String Name;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

}

