package com.power.mercenary.bean.mytask;

/**
 * admin  2018/7/18 wan
 */
public class AcceptTaskBean {
    /**
     * id : 1
     * task_name : 任务名称
     * task_person_id : 接收任务人id
     * pay_amount : 2.00
     * task_description : 任务详情
     * task_tag : 简单,便宜
     * task_status : 1
     * apply_status : 0
     */

    private String id;
    private String task_name;
    private String task_person_id;
    private String pay_amount;
    private String task_description;
    private String task_tag;
    private String task_status;
    private String apply_status;
    private String task_type;
    public String getTask_status() {
        return task_status;
    }

    public void setTask_status(String task_status) {
        this.task_status = task_status;
    }
    public String getTask_type() {
        return task_type;
    }

    public void setTask_type(String task_type) {
        this.task_type = task_type;
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

    public String getTask_person_id() {
        return task_person_id;
    }

    public void setTask_person_id(String task_person_id) {
        this.task_person_id = task_person_id;
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



    public String getApply_status() {
        return apply_status;
    }

    public void setApply_status(String apply_status) {
        this.apply_status = apply_status;
    }
}
