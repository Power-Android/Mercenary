package com.power.mercenary.bean;

/**
 * Created by lenovo on 2018/8/8.
 * 添加所有类型的数据（收入，支出，全部）
 */

public class MingxiBean {
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
