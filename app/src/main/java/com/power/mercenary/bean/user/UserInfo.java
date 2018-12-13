package com.power.mercenary.bean.user;

import android.util.Log;

/**
 * admin  2018/7/5 wan
 */
public class UserInfo {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * nick_name : 花花
     * name : 张三
     * age : 23
     * sex : 花花
     * mail : 123@163.com
     * mobile : 13167399351
     * user_type : 0
     * is_check : 0
     * head_img : 0.jpg
     * id_card : 11111234234
     * identity_front : 2.jpg
     * business_license : 2.jpg
     * create_time : 1530760364
     * industry : 互联网
     * speciality : 跑步
     * experience : 网站开发
     * workyear : 10年
     * certificate : 2.jpg
     */

    private String id;



    private String nick_name;
    private String name;
    private String age;
    private String sex;
    private String mail;
    private String mobile;
    private int user_type;
    private int is_check;
    private String head_img;
    private String id_card;
    private String identity_front;
    private String business_license;
    private String create_time;
    private String industry;
    private String speciality;
    private String experience;
    private String workyear;
    private String certificate;
    public String money;

    public String getIsagree() {
        return isagree;
    }

    public void setIsagree(String isagree) {
        this.isagree = isagree;
    }

    public String isagree;

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getUser_type() {
        return user_type;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }

    public int getIs_check() {
        return is_check;
    }

    public void setIs_check(int is_check) {
        this.is_check = is_check;
    }

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public String getIdentity_front() {
        return identity_front;
    }

    public void setIdentity_front(String identity_front) {
        this.identity_front = identity_front;
    }

    public String getBusiness_license() {
        return business_license;
    }

    public void setBusiness_license(String business_license) {
        this.business_license = business_license;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getWorkyear() {
        return workyear;
    }

    public void setWorkyear(String workyear) {
        this.workyear = workyear;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }
}
