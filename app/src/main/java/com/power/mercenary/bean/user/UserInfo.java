package com.power.mercenary.bean.user;

/**
 * admin  2018/7/5 wan
 */
public class UserInfo {

    /**
     * nick_name : 花花
     * name : 张三
     * age : 23
     * sex : 花花
     * mail : 123@163.com
     * mobile : 13167399351
     */

    private String nick_name;
    private String name;
    private String age;
    private String sex;
    private String mail;
    private String mobile;

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
}
