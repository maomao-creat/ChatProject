package com.zykj.samplechat.model;

import java.io.Serializable;

/**
 * Created by ninos on 2016/7/27.
 */
public class lbt implements Serializable {

        /**
         * Id : 233cb9cc-6781-421e-a3d6-a97300ad73c3
         * Name : 快餐小吃
         * ImgPath : /upload/user/2018100810313182812.png
         * CreateDate : /Date(1538965891207)/
         */

        private String Id;
        private String Name;
        private String ImgPath;
        private String CreateDate;

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getImgPath() {
            return ImgPath;
        }

        public void setImgPath(String ImgPath) {
            this.ImgPath = ImgPath;
        }

        public String getCreateDate() {
            return CreateDate;
        }

        public void setCreateDate(String CreateDate) {
            this.CreateDate = CreateDate;
        }

}

