package com.power.mercenary.presenter;

import android.app.Activity;

import com.lzy.okgo.model.Response;
import com.power.mercenary.MyApplication;
import com.power.mercenary.bean.user.UserInfo;
import com.power.mercenary.http.DialogCallback;
import com.power.mercenary.http.HttpManager;
import com.power.mercenary.http.ResponseBean;

/**
 * admin  2018/7/5 wan
 */
public class UserPresenter {
    private Activity activity;

    private UserCallBack callBack;

    public UserPresenter(Activity activity, UserCallBack callBack) {
        this.activity = activity;
        this.callBack = callBack;
    }

    /**
     * 获取用户信息
     */
    public void getUserInfo(){
        new HttpManager<ResponseBean<UserInfo>>("Home/UserCenter/getinfo", this)
                .addParams("token", MyApplication.getUserToken())
                .postRequest(new DialogCallback<ResponseBean<UserInfo>>(activity) {
                    @Override
                    public void onSuccess(Response<ResponseBean<UserInfo>> response) {
                        if (response.body().data != null) {
                            callBack.getUserInfo(response.body().data);
                        }
                    }
                });
    }

    public interface UserCallBack {
        void getUserInfo(UserInfo userInfo);
    }
}
