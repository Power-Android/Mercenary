package com.power.mercenary.bean;

/**
 * Created by Administrator on 2018/7/24.
 */

public class CollectionBean {

    /**
     * id : 1
     * task_name : 送东西
     * pay_amount : 1.00
     * task_description : 送东西送东西
     * task_tag : 简单
     */

    private String id;
    private String task_name;
    private String pay_amount;
    private String task_description;
    private String task_tag;
    private String task_type;
    private String task_status;

    public String getTask_type() {
        return task_type;
    }

    public void setTask_type(String task_type) {
        this.task_type = task_type;
    }

    public String getTask_status() {
        return task_status;
    }

    public void setTask_status(String task_status) {
        this.task_status = task_status;
    }

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
