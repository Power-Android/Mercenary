package com.power.mercenary.event;

/**
 * admin  2018/7/5 wan
 */
public class EventUtils {
    private int type;
    private Object data;

    public EventUtils(int type) {
        this.type = type;
    }

    public EventUtils(int type, Object data) {
        this.type = type;
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
