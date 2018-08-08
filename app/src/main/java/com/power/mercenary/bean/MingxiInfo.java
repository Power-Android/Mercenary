package com.power.mercenary.bean;

import java.util.List;

/**
 * Created by lenovo on 2018/8/6.
 */

public class MingxiInfo {


        /**
         * all : [{"publish_time":"1533282387","task_name":"买衣服","task_description":"买一件短袖","money":"162.00"},{"publish_time":"1533279467","task_name":"个人定制","task_description":"这是个测试任务","money":"-0.88"},{"publish_time":"1533279467","task_name":"送东西","task_description":"送东西送东西","money":"162.00"}]
         * shouru : [{"publish_time":"1533279467","task_name":"送东西","task_description":"送东西送东西","money":"162.00"},{"publish_time":"1533282387","task_name":"买衣服","task_description":"买一件短袖","money":"162.00"}]
         * zhichu : [{"publish_time":"1533279467","task_name":"个人定制","task_description":"这是个测试任务","money":"-0.88"}]
         * zhichu_total : 0.88
         * shouru_total : 324.00
         * date : {"year":"2018","month":"8"}
         */

        private String zhichu_total;
        private String shouru_total;
        private DateBean date;
        private List<AllBean> all;
        private List<ShouruBean> shouru;
        private List<ZhichuBean> zhichu;

        public String getZhichu_total() {
            return zhichu_total;
        }

        public void setZhichu_total(String zhichu_total) {
            this.zhichu_total = zhichu_total;
        }

        public String getShouru_total() {
            return shouru_total;
        }

        public void setShouru_total(String shouru_total) {
            this.shouru_total = shouru_total;
        }

        public DateBean getDate() {
            return date;
        }

        public void setDate(DateBean date) {
            this.date = date;
        }

        public List<AllBean> getAll() {
            return all;
        }

        public void setAll(List<AllBean> all) {
            this.all = all;
        }

        public List<ShouruBean> getShouru() {
            return shouru;
        }

        public void setShouru(List<ShouruBean> shouru) {
            this.shouru = shouru;
        }

        public List<ZhichuBean> getZhichu() {
            return zhichu;
        }

        public void setZhichu(List<ZhichuBean> zhichu) {
            this.zhichu = zhichu;
        }

        public static class DateBean {
            /**
             * year : 2018
             * month : 8
             */

            private String year;
            private String month;

            public String getYear() {
                return year;
            }

            public void setYear(String year) {
                this.year = year;
            }

            public String getMonth() {
                return month;
            }

            public void setMonth(String month) {
                this.month = month;
            }
        }

        public static class AllBean {
            /**
             * publish_time : 1533282387
             * task_name : 买衣服
             * task_description : 买一件短袖
             * money : 162.00
             */

            private String publish_time;
            private String task_name;
            private String task_description;
            private String money;

            public String getPublish_time() {
                return publish_time;
            }

            public void setPublish_time(String publish_time) {
                this.publish_time = publish_time;
            }

            public String getTask_name() {
                return task_name;
            }

            public void setTask_name(String task_name) {
                this.task_name = task_name;
            }

            public String getTask_description() {
                return task_description;
            }

            public void setTask_description(String task_description) {
                this.task_description = task_description;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }
        }

        public static class ShouruBean {
            /**
             * publish_time : 1533279467
             * task_name : 送东西
             * task_description : 送东西送东西
             * money : 162.00
             */

            private String publish_time;
            private String task_name;
            private String task_description;
            private String money;

            public String getPublish_time() {
                return publish_time;
            }

            public void setPublish_time(String publish_time) {
                this.publish_time = publish_time;
            }

            public String getTask_name() {
                return task_name;
            }

            public void setTask_name(String task_name) {
                this.task_name = task_name;
            }

            public String getTask_description() {
                return task_description;
            }

            public void setTask_description(String task_description) {
                this.task_description = task_description;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }
        }

        public static class ZhichuBean {
            /**
             * publish_time : 1533279467
             * task_name : 个人定制
             * task_description : 这是个测试任务
             * money : -0.88
             */

            private String publish_time;
            private String task_name;
            private String task_description;
            private String money;

            public String getPublish_time() {
                return publish_time;
            }

            public void setPublish_time(String publish_time) {
                this.publish_time = publish_time;
            }

            public String getTask_name() {
                return task_name;
            }

            public void setTask_name(String task_name) {
                this.task_name = task_name;
            }

            public String getTask_description() {
                return task_description;
            }

            public void setTask_description(String task_description) {
                this.task_description = task_description;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }
        }
}
