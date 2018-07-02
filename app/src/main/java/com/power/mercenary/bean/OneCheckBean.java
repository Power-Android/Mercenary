package com.power.mercenary.bean;

/**
 * Created by Administrator on 2018/4/3.
 */

public class OneCheckBean {
    private boolean isChecked;
    private String name;


    public OneCheckBean(boolean isChecked, String name) {
        this.isChecked = isChecked;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
