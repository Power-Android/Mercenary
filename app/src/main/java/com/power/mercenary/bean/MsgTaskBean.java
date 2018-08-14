package com.power.mercenary.bean;

/**
 * admin  2018/8/8 wan
 */
public class MsgTaskBean {

    /**
     * id : 1
     * user_id : 19
     * task_id : 6
     * push_type : 2
     * content : 指定id为3的某某人接受了任务
     * push_time : 1533114479566
     * read_status : 1
     * task_name : 送东西
     */

    private String id;
    private String user_id;
    private String task_id;
    private String push_type;
    private String content;
    private long push_time;
    private String read_status;
    private String task_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getPush_type() {
        return push_type;
    }

    public void setPush_type(String push_type) {
        this.push_type = push_type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getPush_time() {
        return push_time;
    }

    public void setPush_time(long push_time) {
        this.push_time = push_time;
    }

    public String getRead_status() {
        return read_status;
    }

    public void setRead_status(String read_status) {
        this.read_status = read_status;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }
}
