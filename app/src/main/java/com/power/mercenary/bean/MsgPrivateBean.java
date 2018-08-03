package com.power.mercenary.bean;

/**
 * admin  2018/8/2 wan
 */
public class MsgPrivateBean {

    /**
     * id : 2
     * lxr : 2
     * fromuserid : 2
     * fromuserhead_img : http://www.yongbing.com/Uploads/img/3/1530602439990.jpg
     * fromuser_name : 用户2
     * touserid : 1
     * touserhead_img : http://www.yongbing.com/Uploads/img/3/1530602439990.jpg
     * touser_name : 用户1
     * content : hello word 1
     * msgtime : 1533114479
     * read_status : 1
     */

    private String id;
    private String lxr;
    private String fromuserid;
    private String fromuserhead_img;
    private String fromuser_name;
    private String touserid;
    private String touserhead_img;
    private String touser_name;
    private String content;
    private long msgtime;
    private String read_status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLxr() {
        return lxr;
    }

    public void setLxr(String lxr) {
        this.lxr = lxr;
    }

    public String getFromuserid() {
        return fromuserid;
    }

    public void setFromuserid(String fromuserid) {
        this.fromuserid = fromuserid;
    }

    public String getFromuserhead_img() {
        return fromuserhead_img;
    }

    public void setFromuserhead_img(String fromuserhead_img) {
        this.fromuserhead_img = fromuserhead_img;
    }

    public String getFromuser_name() {
        return fromuser_name;
    }

    public void setFromuser_name(String fromuser_name) {
        this.fromuser_name = fromuser_name;
    }

    public String getTouserid() {
        return touserid;
    }

    public void setTouserid(String touserid) {
        this.touserid = touserid;
    }

    public String getTouserhead_img() {
        return touserhead_img;
    }

    public void setTouserhead_img(String touserhead_img) {
        this.touserhead_img = touserhead_img;
    }

    public String getTouser_name() {
        return touser_name;
    }

    public void setTouser_name(String touser_name) {
        this.touser_name = touser_name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getMsgtime() {
        return msgtime;
    }

    public void setMsgtime(long msgtime) {
        this.msgtime = msgtime;
    }

    public String getRead_status() {
        return read_status;
    }

    public void setRead_status(String read_status) {
        this.read_status = read_status;
    }
}
