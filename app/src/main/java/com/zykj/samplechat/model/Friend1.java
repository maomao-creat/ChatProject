package com.zykj.samplechat.model;


import java.io.Serializable;
import java.util.List;

/**
 ******************************************************
 *                                                    *
 *                                                    *
 *                       _oo0oo_                      *
 *                      o8888888o                     *
 *                      88" . "88                     *
 *                      (| -_- |)                     *
 *                      0\  =  /0                     *
 *                    ___/`---'\___                   *
 *                  .' \\|     |# '.                  *
 *                 / \\|||  :  |||# \                 *
 *                / _||||| -:- |||||- \               *
 *               |   | \\\  -  #/ |   |               *
 *               | \_|  ''\---/''  |_/ |              *
 *               \  .-\__  '-'  ___/-. /              *
 *             ___'. .'  /--.--\  `. .'___            *
 *          ."" '<  `.___\_<|>_/___.' >' "".          *
 *         | | :  `- \`.;`\ _ /`;.`/ - ` : | |        *
 *         \  \ `_.   \_ __\ /__ _/   .-` /  /        *
 *     =====`-.____`.___ \_____/___.-`___.-'=====     *
 *                       `=---='                      *
 *                                                    *
 *                                                    *
 *     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~    *
 *                                                    *
 *               佛祖保佑         永无BUG              *
 *                                                    *
 *                                                    *
 ******************************************************
 *
 * Created by ninos on 2016/6/2.
 *
 */
public class Friend1 implements Serializable {


    /**
     * error : 操作成功
     * data : [{"circleImgList":[{"circle_img_id":19,"circle_img_url":"/upload/circleImg/20180112/1515738255992233233.jpg","offset":0,"page":1,"rows":10}],"circle_content":"你好好","circle_id":10,"comment_num":3,"offset":0,"page":1,"praise_num":4,"rows":10,"user_id":8,"visit_num":35},{"circleImgList":[{"circle_img_id":27,"circle_img_url":"/upload/circleImg/20180116/1516064468575182276.jpg","offset":0,"page":1,"rows":10}],"circle_content":"Qqqqq","circle_id":17,"comment_num":2,"offset":0,"page":1,"praise_num":2,"rows":10,"user_id":4,"visit_num":6},{"circleImgList":[{"circle_img_id":20,"circle_img_url":"/upload/circleImg/20180112/1515738291108513977.jpg","offset":0,"page":1,"rows":10},{"circle_img_id":21,"circle_img_url":"/upload/circleImg/20180112/1515738291173664503.jpg","offset":0,"page":1,"rows":10}],"circle_content":"冠军咯恶龙","circle_id":11,"comment_num":3,"offset":0,"page":1,"praise_num":1,"rows":10,"user_id":4,"visit_num":5},{"circleImgList":[{"circle_img_id":72,"circle_img_url":"/upload/null/20180410/1523345290054306388.png","offset":0,"page":1,"rows":10},{"circle_img_id":73,"circle_img_url":"/upload/null/20180410/1523345290082140063.png","offset":0,"page":1,"rows":10},{"circle_img_id":74,"circle_img_url":"/upload/null/20180410/1523345290171664013.png","offset":0,"page":1,"rows":10},{"circle_img_id":75,"circle_img_url":"/upload/null/20180410/1523345290197067750.png","offset":0,"page":1,"rows":10},{"circle_img_id":76,"circle_img_url":"/upload/null/20180410/1523345290377143115.png","offset":0,"page":1,"rows":10}],"circle_content":"sss","circle_id":46,"comment_num":1,"head_img":"/upload/headimg/20180507/1525690148123331917.jpg","offset":0,"page":1,"praise_num":1,"rows":10,"user_id":12,"user_nikename":"Arapaho","visit_num":3},{"circleImgList":[{"circle_img_id":36,"circle_img_url":"/upload/circleImg/20180403/1522739165690879636.jpg","offset":0,"page":1,"rows":10}],"circle_content":"??????","circle_id":20,"comment_num":0,"offset":0,"page":1,"praise_num":0,"rows":10,"user_id":23,"visit_num":2},{"circleImgList":[{"circle_img_id":42,"circle_img_url":"/upload/circleImg/20180409/1523262546773624579.jpg","offset":0,"page":1,"rows":10}],"circle_content":"哈哈哈","circle_id":24,"comment_num":0,"head_img":"/upload/circleImg/20180511/1526008811518088072.mp4","offset":0,"page":1,"praise_num":0,"rows":10,"user_id":22,"user_nikename":"怕","visit_num":2},{"circleImgList":[{"circle_img_id":24,"circle_img_url":"/upload/circleImg/20180112/1515741326068946083.jpg","offset":0,"page":1,"rows":10}],"circle_content":"vs ???","circle_id":14,"comment_num":0,"offset":0,"page":1,"praise_num":0,"rows":10,"user_id":9,"visit_num":0},{"circleImgList":[{"circle_img_id":77,"circle_img_url":"/upload/null/20180410/1523345324295355334.png","offset":0,"page":1,"rows":10},{"circle_img_id":78,"circle_img_url":"/upload/null/20180410/1523345324387371062.png","offset":0,"page":1,"rows":10},{"circle_img_id":79,"circle_img_url":"/upload/null/20180410/1523345324405189953.png","offset":0,"page":1,"rows":10},{"circle_img_id":80,"circle_img_url":"/upload/null/20180410/1523345324499521863.png","offset":0,"page":1,"rows":10}],"circle_content":"www","circle_id":47,"comment_num":0,"head_img":"/upload/headimg/20180507/1525690148123331917.jpg","offset":0,"page":1,"praise_num":0,"rows":10,"user_id":12,"user_nikename":"Arapaho","visit_num":0}]
     * code : 200
     */

    private String error;
    private String code;
    private List<DataBean> data;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * circleImgList : [{"circle_img_id":19,"circle_img_url":"/upload/circleImg/20180112/1515738255992233233.jpg","offset":0,"page":1,"rows":10}]
         * circle_content : 你好好
         * circle_id : 10
         * comment_num : 3
         * offset : 0
         * page : 1
         * praise_num : 4
         * rows : 10
         * user_id : 8
         * visit_num : 35
         * head_img : /upload/headimg/20180507/1525690148123331917.jpg
         * user_nikename : Arapaho
         */

        private String circle_content;
        private int circle_id;
        private int comment_num;
        private int offset;
        private int page;
        private int praise_num;
        private int rows;
        private int user_id;
        private int visit_num;
        private String head_img;
        private String user_nikename;
        private List<CircleImgListBean> circleImgList;

        public String getCircle_content() {
            return circle_content;
        }

        public void setCircle_content(String circle_content) {
            this.circle_content = circle_content;
        }

        public int getCircle_id() {
            return circle_id;
        }

        public void setCircle_id(int circle_id) {
            this.circle_id = circle_id;
        }

        public int getComment_num() {
            return comment_num;
        }

        public void setComment_num(int comment_num) {
            this.comment_num = comment_num;
        }

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getPraise_num() {
            return praise_num;
        }

        public void setPraise_num(int praise_num) {
            this.praise_num = praise_num;
        }

        public int getRows() {
            return rows;
        }

        public void setRows(int rows) {
            this.rows = rows;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getVisit_num() {
            return visit_num;
        }

        public void setVisit_num(int visit_num) {
            this.visit_num = visit_num;
        }

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }

        public String getUser_nikename() {
            return user_nikename;
        }

        public void setUser_nikename(String user_nikename) {
            this.user_nikename = user_nikename;
        }

        public List<CircleImgListBean> getCircleImgList() {
            return circleImgList;
        }

        public void setCircleImgList(List<CircleImgListBean> circleImgList) {
            this.circleImgList = circleImgList;
        }

        public static class CircleImgListBean {
            /**
             * circle_img_id : 19
             * circle_img_url : /upload/circleImg/20180112/1515738255992233233.jpg
             * offset : 0
             * page : 1
             * rows : 10
             */

            private int circle_img_id;
            private String circle_img_url;
            private int offset;
            private int page;
            private int rows;

            public int getCircle_img_id() {
                return circle_img_id;
            }

            public void setCircle_img_id(int circle_img_id) {
                this.circle_img_id = circle_img_id;
            }

            public String getCircle_img_url() {
                return circle_img_url;
            }

            public void setCircle_img_url(String circle_img_url) {
                this.circle_img_url = circle_img_url;
            }

            public int getOffset() {
                return offset;
            }

            public void setOffset(int offset) {
                this.offset = offset;
            }

            public int getPage() {
                return page;
            }

            public void setPage(int page) {
                this.page = page;
            }

            public int getRows() {
                return rows;
            }

            public void setRows(int rows) {
                this.rows = rows;
            }
        }
    }
}
