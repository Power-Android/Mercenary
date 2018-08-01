package com.power.mercenary.bean;

/**
 * admin  2018/7/26 wan
 */
public class ObtainUserInfo {

    /**
     * id : 1
     * name : 张三
     * head_img : /Uploads/news/20180712/5b46ff1110961.jpg
     * create_time : 1530782193
     * fans : 58
     * total : 100.00
     */

    private int id;
    private String name;
    private String head_img;
    private String create_time;
    private String fans;
    private String total;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getFans() {
        return fans;
    }

    public void setFans(String fans) {
        this.fans = fans;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
