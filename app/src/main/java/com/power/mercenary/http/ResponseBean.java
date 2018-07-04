package com.power.mercenary.http;

import java.io.Serializable;

public class ResponseBean<T> implements Serializable {

    public int code;
    public String msg;
    public T data;
}