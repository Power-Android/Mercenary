package com.power.mercenary.presenter;

import android.app.Activity;

/**
 * admin  2018/7/5 wan
 */
public class UpdataPresenter {
    private Activity activity;

    private UpdataCallBack callBack;

    public UpdataPresenter(Activity activity, UpdataCallBack callBack) {
        this.activity = activity;
        this.callBack = callBack;
    }

    public interface UpdataCallBack{

    }
}
