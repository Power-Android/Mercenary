package com.power.mercenary.http;

import com.google.gson.Gson;

public class HttpException extends IllegalStateException {

    private BaseResponseBean errorBean;

    public HttpException(String s) {
        super(s);
        errorBean = new Gson().fromJson(s, BaseResponseBean.class);
    }

    public BaseResponseBean getErrorBean() {
        return errorBean;
    }
}
