package com.power.mercenary.bean.mytask;

import android.app.Activity;

import com.power.mercenary.http.HttpManager;
import com.power.mercenary.http.ResponseBean;

/**
 * admin  2018/7/18 wan
 */
public class StatsTaskPresenter {
    private Activity activity;

    private StatsTaskCallBack callBack;

    public StatsTaskPresenter(Activity activity, StatsTaskCallBack callBack) {
        this.activity = activity;
        this.callBack = callBack;
    }

    public void publishTask(){
        
    }

    public void acceptTask(){

    }

    public interface StatsTaskCallBack {

    }
}
