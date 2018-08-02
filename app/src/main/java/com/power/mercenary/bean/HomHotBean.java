package com.power.mercenary.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2018/8/2.
 */

public class HomHotBean {

    /**
     * id : 1
     * task_type : 1
     * task_type_child : 3
     * post_user_id : 3
     * post_user_name : 大水杯
     * post_user_headimg : a.jpg
     * post_content : 帖子内容
     * post_img : a.jpg,b.jpg
     * create_time : 1531185323
     * count : 3
     */

    private String id;
    private String task_type;
    private String task_type_child;
    private String post_user_id;
    private String post_user_name;
    private String post_user_headimg;
    private String post_content;
    private String post_img;
    private String create_time;
    private String count;
    public boolean isShowAll = false;
    public List<String> urlList = new ArrayList<>();


    public List<String> getUrlList() {
        return urlList;
    }

    public void setUrlList(List<String> urlList) {
        this.urlList = urlList;
    }
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

    public String getTask_type_child() {
        return task_type_child;
    }

    public void setTask_type_child(String task_type_child) {
        this.task_type_child = task_type_child;
    }

    public String getPost_user_id() {
        return post_user_id;
    }

    public void setPost_user_id(String post_user_id) {
        this.post_user_id = post_user_id;
    }

    public String getPost_user_name() {
        return post_user_name;
    }

    public void setPost_user_name(String post_user_name) {
        this.post_user_name = post_user_name;
    }

    public String getPost_user_headimg() {
        return post_user_headimg;
    }

    public void setPost_user_headimg(String post_user_headimg) {
        this.post_user_headimg = post_user_headimg;
    }

    public String getPost_content() {
        return post_content;
    }

    public void setPost_content(String post_content) {
        this.post_content = post_content;
    }

    public String getPost_img() {
        return post_img;
    }

    public void setPost_img(String post_img) {
        this.post_img = post_img;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
