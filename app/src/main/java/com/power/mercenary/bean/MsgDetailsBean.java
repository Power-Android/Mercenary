package com.power.mercenary.bean;

/**
 * admin  2018/8/10 wan
 */
public class MsgDetailsBean {
    /**
     * id : 2
     * post_user_id : 1
     * liuyan_user_headimg : /Uploads/img/19/1532313951202.jpg
     * liuyan_user_name : 这是一个姓名
     * liuyan_content : hello word 1
     * create_time : 1533114479166
     * read_status : 0
     */

    private String id;
    private String post_user_id;
    private String liuyan_user_headimg;
    private String liuyan_user_name;
    private String liuyan_content;
    private long create_time;
    private String read_status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPost_user_id() {
        return post_user_id;
    }

    public void setPost_user_id(String post_user_id) {
        this.post_user_id = post_user_id;
    }

    public String getLiuyan_user_headimg() {
        return liuyan_user_headimg;
    }

    public void setLiuyan_user_headimg(String liuyan_user_headimg) {
        this.liuyan_user_headimg = liuyan_user_headimg;
    }

    public String getLiuyan_user_name() {
        return liuyan_user_name;
    }

    public void setLiuyan_user_name(String liuyan_user_name) {
        this.liuyan_user_name = liuyan_user_name;
    }

    public String getLiuyan_content() {
        return liuyan_content;
    }

    public void setLiuyan_content(String liuyan_content) {
        this.liuyan_content = liuyan_content;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public String getRead_status() {
        return read_status;
    }

    public void setRead_status(String read_status) {
        this.read_status = read_status;
    }
}
