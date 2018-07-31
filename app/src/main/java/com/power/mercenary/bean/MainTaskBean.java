package com.power.mercenary.bean;

import java.util.List;

/**
 * admin  2018/7/31 wan
 */
public class MainTaskBean {

    private List<TuijianBean> tuijian;
    private List<TuijianBean> same_city;

    public List<TuijianBean> getTuijian() {
        return tuijian;
    }

    public void setTuijian(List<TuijianBean> tuijian) {
        this.tuijian = tuijian;
    }

    public List<TuijianBean> getSame_city() {
        return same_city;
    }

    public void setSame_city(List<TuijianBean> same_city) {
        this.same_city = same_city;
    }

    public static class TuijianBean {
        /**
         * id : 2
         * task_name : 买衣服
         * task_type : 2
         * task_type_child : 3
         * pay_amount : 0.99
         * task_description : 买一件短袖
         * task_tag : 简单，好看
         */

        private String id;
        private String task_name;
        private String task_type;
        private String task_type_child;
        private String pay_amount;
        private String task_description;
        private String task_tag;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTask_name() {
            return task_name;
        }

        public void setTask_name(String task_name) {
            this.task_name = task_name;
        }

        public String getTask_type() {
            return task_type;
        }

        public void setTask_type(String task_type) {
            this.task_type = task_type;
        }

        public String getTask_type_child() {
            return task_type_child;
        }

        public void setTask_type_child(String task_type_child) {
            this.task_type_child = task_type_child;
        }

        public String getPay_amount() {
            return pay_amount;
        }

        public void setPay_amount(String pay_amount) {
            this.pay_amount = pay_amount;
        }

        public String getTask_description() {
            return task_description;
        }

        public void setTask_description(String task_description) {
            this.task_description = task_description;
        }

        public String getTask_tag() {
            return task_tag;
        }

        public void setTask_tag(String task_tag) {
            this.task_tag = task_tag;
        }
    }
}
