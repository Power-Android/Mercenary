package com.power.mercenary.http;

import java.io.Serializable;

public class BaseResponseBean implements Serializable {

    private static final long serialVersionUID = -1477609349345966116L;

    public int code;
    public String msg;

    public ResponseBean toResponseBean() {
        ResponseBean responseBean = new ResponseBean();
        responseBean.code = code;
        responseBean.msg = msg;
        return responseBean;
    }
}
