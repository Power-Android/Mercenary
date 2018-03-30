package com.power.mercenary.bean;

import java.io.Serializable;

/**
 * Created by power on 2018/3/26.
 */

public class Testbean implements Serializable {
    private String title;
    private int img;
    private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
