package com.power.mercenary.presenter;

import android.app.Activity;

import com.lzy.okgo.model.Response;
import com.power.mercenary.MyApplication;
import com.power.mercenary.activity.chat.ChatActivity;
import com.power.mercenary.bean.ObtainUserInfo;
import com.power.mercenary.http.DialogCallback;
import com.power.mercenary.http.HttpManager;
import com.power.mercenary.http.JsonCallback;
import com.power.mercenary.http.ResponseBean;
import com.power.mercenary.utils.TUtils;

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

    public void addMessage(String toUserId, String content){
        new HttpManager<ResponseBean<Void>>("Home/YbTest/message_add", this)
                .addParams("token", MyApplication.getUserToken())
                .addParams("toUserId", toUserId)
                .addParams("objectName", "TxtMsg")
                .addParams("content", content)
                .postRequest(new JsonCallback<ResponseBean<Void>>() {
                    @Override
                    public void onSuccess(Response<ResponseBean<Void>> response) {
                        TUtils.showCustom(activity, response.body().msg);
                    }
                });
    }

    public interface ChatCallBack{
        void getUserInfo(ObtainUserInfo userInfo);
    }
}
