package com.zykj.samplechat.model;

import java.io.Serializable;
import java.util.List;


/**
 * Created by 11655 on 2016/10/18.
 */

public class myxuqBean implements Serializable {

        /**
         * list : [{"needId":"11","status":"2","name":"名字","sex":"0","price_type":"1","price":"6767","num":"64","deal_type":"1","create_time":"2018-08-01 15:39:32","style_name":"Html5定制开发","orders":[{"memberIds":"5","image_head":"public/uploads/member/18-07-31/5b60764237a121.86113568.jpg"}],"orders_count":1},{"needId":"10","status":"4","name":"名字","sex":"0","price_type":"1","price":"24","num":"24","deal_type":"1","create_time":"2018-08-01 15:22:27","style_name":"Html5定制开发","orders":[],"orders_count":0}]
         * count : 2
         */

        private String count;
        private String memberId;
        private List<ListBean> list;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements Serializable {
            /**
             * needId : 11
             * status : 2
             * name : 名字
             * sex : 0
             * price_type : 1
             * price : 6767
             * num : 64
             * deal_type : 1
             * create_time : 2018-08-01 15:39:32
             * style_name : Html5定制开发
             * orders : [{"memberIds":"5","image_head":"public/uploads/member/18-07-31/5b60764237a121.86113568.jpg"}]
             * orders_count : 1
             */

            private String needId;
            private int status;
            private String name;
            private int sex;
            private String price_type;
            private String price;
            private String num;
            private int deal_type;
            private String create_time;
            private String style_name;
            private String fangshi;
            private int orders_count;
            private List<OrdersBean> orders;
            private String start_date;
            private String stop_date;

            public String getStart_date() {
                return start_date;
            }

            public void setStart_date(String start_date) {
                this.start_date = start_date;
            }

            public String getStop_date() {
                return stop_date;
            }

            public void setStop_date(String stop_date) {
                this.stop_date = stop_date;
            }

            public String getFangshi() {
                return fangshi;
            }

            public void setFangshi(String fangshi) {
                this.fangshi = fangshi;
            }

            public String getNeedId() {
                return needId;
            }

            public void setNeedId(String needId) {
                this.needId = needId;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }

            public String getPrice_type() {
                return price_type;
            }

            public void setPrice_type(String price_type) {
                this.price_type = price_type;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public int getDeal_type() {
                return deal_type;
            }

            public void setDeal_type(int deal_type) {
                this.deal_type = deal_type;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getStyle_name() {
                return style_name;
            }

            public void setStyle_name(String style_name) {
                this.style_name = style_name;
            }

            public int getOrders_count() {
                return orders_count;
            }

            public void setOrders_count(int orders_count) {
                this.orders_count = orders_count;
            }

            public List<OrdersBean> getOrders() {
                return orders;
            }

            public void setOrders(List<OrdersBean> orders) {
                this.orders = orders;
            }

            public static class OrdersBean implements Serializable {
                /**
                 * memberIds : 5
                 * image_head : public/uploads/member/18-07-31/5b60764237a121.86113568.jpg
                 */

                private String memberIds;
                private String image_head;

                public String getMemberIds() {
                    return memberIds;
                }

                public void setMemberIds(String memberIds) {
                    this.memberIds = memberIds;
                }

                public String getImage_head() {
                    return image_head;
                }

                public void setImage_head(String image_head) {
                    this.image_head = image_head;
                }
            }
        }
    }

