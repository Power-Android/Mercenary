package com.power.mercenary.bean.task;

/**
 * admin  2018/7/12 wan
 */
public class ApplyListBean {

    /**
     * id : 1
     * apply_user_id : 1
     * apply_user_name : 张三
     * apply_user_headimg : a.jpg
     * apply_amount : 100.00
     */

    private String id;
    private String apply_user_id;
    private String apply_user_name;
    private String apply_user_headimg;
    private String apply_amount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApply_user_id() {
        return apply_user_id;
    }

    public void setApply_user_id(String apply_user_id) {
        this.apply_user_id = apply_user_id;
    }

    public String getApply_user_name() {
        return apply_user_name;
    }

    public void setApply_user_name(String apply_user_name) {
        this.apply_user_name = apply_user_name;
    }

    public String getApply_user_headimg() {
        return apply_user_headimg;
    }

    public void setApply_user_headimg(String apply_user_headimg) {
        this.apply_user_headimg = apply_user_headimg;
    }

    public String getApply_amount() {
        return apply_amount;
    }

    public void setApply_amount(String apply_amount) {
        this.apply_amount = apply_amount;
    }
}
