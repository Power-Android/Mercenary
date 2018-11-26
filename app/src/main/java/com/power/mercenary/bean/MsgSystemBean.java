package com.power.mercenary.bean;

/**
 * admin  2018/8/8 wan
 */
public class MsgSystemBean {
    /**
     * id : 311
     * system_push_id : 0
     * task_id : 345
     * post_id : 0
     * user_id : 17
     * content : 您提交完成的任务确认完成！
     * time : 1542685388
     * read_status : 1
     */

    private String id;
    private String system_push_id;
    private String task_id;
    private String post_id;
    private String user_id;
    private String content;
    private long time;
    private String read_status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSystem_push_id() {
        return system_push_id;
    }

    public void setSystem_push_id(String system_push_id) {
        this.system_push_id = system_push_id;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getRead_status() {
        return read_status;
    }

    public void setRead_status(String read_status) {
        this.read_status = read_status;
    }



}
