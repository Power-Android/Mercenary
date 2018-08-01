package com.power.mercenary.bean.mytask;

/**
 * admin  2018/7/18 wan
 */
public class StatisticsListBean {

    /**
     * id : 9
     * task_type : 1
     * task_status : 7
     * task_name : 任务4
     * pay_amount : 1800.00
     */

    private String id;
    private String task_type;
    private String task_status;
    private String task_name;
    private String pay_amount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
}
