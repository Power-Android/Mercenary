package com.power.mercenary.bean.task;

/**
 * admin  2018/7/12 wan
 */
public class MsgBean {

    /**
     * task_id : 1
     * leavemsg_user_id : 1
     * leavemsg_user_name : 张三
     * leavemsg_user_headimg : a.jpg
     * leavemsg_content : 留言内容
     * leavemsg_img : 留言图片
     * create_time : 1738287371
     */

    private String task_id;
    private String leavemsg_user_id;
    private String leavemsg_user_name;
    private String leavemsg_user_headimg;
    private String leavemsg_content;
    private String leavemsg_img;
    private String create_time;

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getLeavemsg_user_id() {
        return leavemsg_user_id;
    }

    public void setLeavemsg_user_id(String leavemsg_user_id) {
        this.leavemsg_user_id = leavemsg_user_id;
    }

    public String getLeavemsg_user_name() {
        return leavemsg_user_name;
    }

    public void setLeavemsg_user_name(String leavemsg_user_name) {
        this.leavemsg_user_name = leavemsg_user_name;
    }

    public String getLeavemsg_user_headimg() {
        return leavemsg_user_headimg;
    }

    public void setLeavemsg_user_headimg(String leavemsg_user_headimg) {
        this.leavemsg_user_headimg = leavemsg_user_headimg;
    }

    public String getLeavemsg_content() {
        return leavemsg_content;
    }

    public void setLeavemsg_content(String leavemsg_content) {
        this.leavemsg_content = leavemsg_content;
    }

    public String getLeavemsg_img() {
        return leavemsg_img;
    }

    public void setLeavemsg_img(String leavemsg_img) {
        this.leavemsg_img = leavemsg_img;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
