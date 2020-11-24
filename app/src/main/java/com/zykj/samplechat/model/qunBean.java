package com.zykj.samplechat.model;

import java.io.Serializable;


/**
 * Created by 11655 on 2016/10/18.
 */

public class qunBean implements Serializable {


        /**
         * Id : b29b6d8e-01d1-4cd8-a9db-a967011fd7a8
         * ImagePath : UpLoad/Photo/80804a27-0fb2-4bb5-822b-35e7712bd3fa.jpg
         * Name : cesttwyuqq的群聊
         * Type : 1
         */

        private String Id;
        private String ImagePath;
        private String Name;
        private int Type;

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public String getImagePath() {
            return ImagePath;
        }

        public void setImagePath(String ImagePath) {
            this.ImagePath = ImagePath;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public int getType() {
            return Type;
        }

        public void setType(int Type) {
            this.Type = Type;
        }
    }


