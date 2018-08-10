package com.power.mercenary.presenter;

import android.app.Activity;

import com.lzy.okgo.model.Response;
import com.power.mercenary.MyApplication;
import com.power.mercenary.bean.AchieveBean;
import com.power.mercenary.http.DialogCallback;
import com.power.mercenary.http.HttpManager;
import com.power.mercenary.http.ResponseBean;

/**
 * admin  2018/7/5 wan
 */
public class AchievePresenter {
    private Activity activity;

    private AchieveCallBack callBack;

    public AchievePresenter(Activity activity, AchieveCallBack callBack) {
        this.activity = activity;
        this.callBack = callBack;
    }

    /**
     * 获取用户信息
     */
    public void getAchieveInfo() {
        new HttpManager<ResponseBean<AchieveBean>>("Home/MyAchieve/my_achieve", this)
                .addParams("token", MyApplication.getUserToken())
                .postRequest(new DialogCallback<ResponseBean<AchieveBean>>(activity) {
                    @Override
                    public void onSuccess(Response<ResponseBean<AchieveBean>> response) {
                        callBack.getAchieveInfo(response.body().data);
                    }
                });
    }

    public interface AchieveCallBack {
        void getAchieveInfo(AchieveBean achieveBean);
    }
}
