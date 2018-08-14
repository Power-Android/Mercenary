package com.power.mercenary.bean;

/**
 * admin  2018/8/8 wan
 */
public class MsgSystemBean {

    /**
     * id : 4
     * user_id : 19
     * notice_push_name : 123
     * notice_push_content :
     * notice_id : 3
     * read_status : 1
     * push_time : 1533114479569
     */

    private String id;
    private String user_id;
    private String notice_push_name;
    private String notice_push_content;
    private String notice_id;
    private String read_status;
    private long push_time;

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

    public String getNotice_push_name() {
        return notice_push_name;
    }

    public void setNotice_push_name(String notice_push_name) {
        this.notice_push_name = notice_push_name;
    }

    public String getNotice_push_content() {
        return notice_push_content;
    }

    public void setNotice_push_content(String notice_push_content) {
        this.notice_push_content = notice_push_content;
    }

    public String getNotice_id() {
        return notice_id;
    }

    public void setNotice_id(String notice_id) {
        this.notice_id = notice_id;
    }

    public String getRead_status() {
        return read_status;
    }

    public void setRead_status(String read_status) {
        this.read_status = read_status;
    }

    public long getPush_time() {
        return push_time;
    }

    public void setPush_time(long push_time) {
        this.push_time = push_time;
    }
}
