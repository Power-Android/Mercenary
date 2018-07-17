package com.power.mercenary.bean.task;

/**
 * admin  2018/7/11 wan
 */
public class TaskDetailsBean {

    /**
     * id : 1
     * task_name : 送东西
     * pay_amount : 1.00
     * publisher_id : 1
     * publish_time : 0
     * task_status : 0 用户发布后台还没有审核通过
     *               1 审核通过在报名中 未决定
     *               2 已选定，任务中
     *               3 用户完成等待发布者审核 ，审核中
     *               4 用户下架任务
     *               5 后台审核未通过
     *               6 发布者审核通过，待评价 。
     *               7 已评价
     * task_tag : 简单,便宜
     * task_description : 任务详情
     * task_purpose : 任务目的
     * task_request : 任务要求
     * itemname : 物品名称
     * numbers : 物品数量
     * transport : 运输要求
     * validity_time : 30 有效期 单位天
     * begin_address : 开始地址
     * end_address : 目的地址
     * delivery_time : 1531185323 送达时间
     * task_img : a.jpg,b.jpg
     * other_request : 其他要求
     * name : 发布任务人姓名
     * head_img : 发布人头像
     * collect : 1
     * * xuanding : {"id":"19","name":"67766767","head_img":"/Uploads/img/19/1531205169669.jpg"}
     */

    private String id;
    private String task_name;
    private String pay_amount;
    private String publisher_id;
    private String task_person_id;
    private String publish_time;
    private String task_status;
    private String task_tag;
    private String task_description;
    private String task_purpose;
    private String task_request;
    private String itemname;
    private String numbers;
    private String transport;
    private String validity_time;
    private String begin_address;
    private String end_address;
    private String delivery_time;
    private String task_img;
    private String other_request;
    private String name;
    private String head_img;

    public String getApply() {
        return apply;
    }

    public void setApply(String apply) {
        this.apply = apply;
    }

    private String apply;
    private int collect;
    private XuandingBean xuanding;

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

    public String getPay_amount() {
        return pay_amount;
    }

    public void setPay_amount(String pay_amount) {
        this.pay_amount = pay_amount;
    }

    public String getPublisher_id() {
        return publisher_id;
    }

    public void setPublisher_id(String publisher_id) {
        this.publisher_id = publisher_id;
    }

    public String getTask_person_id() {
        return task_person_id;
    }

    public void setTask_person_id(String task_person_id) {
        this.task_person_id = task_person_id;
    }

    public String getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(String publish_time) {
        this.publish_time = publish_time;
    }

    public String getTask_status() {
        return task_status;
    }

    public void setTask_status(String task_status) {
        this.task_status = task_status;
    }

    public String getTask_tag() {
        return task_tag;
    }

    public void setTask_tag(String task_tag) {
        this.task_tag = task_tag;
    }

    public String getTask_description() {
        return task_description;
    }

    public void setTask_description(String task_description) {
        this.task_description = task_description;
    }

    public String getTask_purpose() {
        return task_purpose;
    }

    public void setTask_purpose(String task_purpose) {
        this.task_purpose = task_purpose;
    }

    public String getTask_request() {
        return task_request;
    }

    public void setTask_request(String task_request) {
        this.task_request = task_request;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public String getValidity_time() {
        return validity_time;
    }

    public void setValidity_time(String validity_time) {
        this.validity_time = validity_time;
    }

    public String getBegin_address() {
        return begin_address;
    }

    public void setBegin_address(String begin_address) {
        this.begin_address = begin_address;
    }

    public String getEnd_address() {
        return end_address;
    }

    public void setEnd_address(String end_address) {
        this.end_address = end_address;
    }

    public String getDelivery_time() {
        return delivery_time;
    }

    public void setDelivery_time(String delivery_time) {
        this.delivery_time = delivery_time;
    }

    public String getTask_img() {
        return task_img;
    }

    public void setTask_img(String task_img) {
        this.task_img = task_img;
    }

    public String getOther_request() {
        return other_request;
    }

    public void setOther_request(String other_request) {
        this.other_request = other_request;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

    public int getCollect() {
        return collect;
    }

    public void setCollect(int collect) {
        this.collect = collect;
    }

    public XuandingBean getXuanding() {
        return xuanding;
    }

    public void setXuanding(XuandingBean xuanding) {
        this.xuanding = xuanding;
    }

    public static class XuandingBean {
        /**
         * id : 19
         * name : 67766767
         * head_img : /Uploads/img/19/1531205169669.jpg
         */

        private String id;
        private String name;
        private String head_img;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }
    }
}
