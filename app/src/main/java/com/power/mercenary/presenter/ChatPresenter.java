package com.power.mercenary.presenter;

import android.app.Activity;

import com.lzy.okgo.model.Response;
import com.power.mercenary.MyApplication;
import com.power.mercenary.bean.ObtainUserInfo;
import com.power.mercenary.http.DialogCallback;
import com.power.mercenary.http.HttpManager;
import com.power.mercenary.http.ResponseBean;

/**
 * admin  2018/7/26 wan
 */
public class ChatPresenter {
    private Activity activity;

    private ChatCallBack chatCallBack;

    public ChatPresenter(Activity activity, ChatCallBack chatCallBack) {
        this.activity = activity;
        this.chatCallBack = chatCallBack;
    }

    /**
     * 查询用户
     * @param userId
     */
    public void getUserInfo(String userId){
        new HttpManager<ResponseBean<ObtainUserInfo>>("Home/UserCenter/get_userinfo", this)
                .addParams("token", MyApplication.getUserToken())
                .addParams("uid", userId)
                .postRequest(new DialogCallback<ResponseBean<ObtainUserInfo>>(activity) {
                    @Override
                    public void onSuccess(Response<ResponseBean<ObtainUserInfo>> response) {
                        chatCallBack.getUserInfo(response.body().data);
                    }
                });
    }

    public interface ChatCallBack{
        void getUserInfo(ObtainUserInfo userInfo);
    }
}
