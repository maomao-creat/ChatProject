package com.zykj.samplechat.model;

import java.io.Serializable;

/**
 * Created by ninos on 2017/8/22.
 */

public class TongZhiBean implements Serializable {


        /**
         * Title : 红包上线喽~
         * Content : 红包上线喽~快来玩哦~红包上线喽~快来玩哦~红包上线喽~快来玩哦~
         */

        private String Title;
        private String Content;

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public String getContent() {
            return Content;
        }

        public void setContent(String Content) {
            this.Content = Content;
        }

}
