package com.power.mercenary.presenter;

import android.app.Activity;

import com.lzy.okgo.model.Response;
import com.power.mercenary.MyApplication;
import com.power.mercenary.bean.MsgPrivateBean;
import com.power.mercenary.http.DialogCallback;
import com.power.mercenary.http.HttpManager;
import com.power.mercenary.http.JsonCallback;
import com.power.mercenary.http.ResponseBean;

import java.util.List;

/**
 * admin  2018/8/2 wan
 */
public class MessagePresenter {
    private Activity activity;

    private MessageCallBack callBack;

    public MessagePresenter(Activity activity, MessageCallBack callBack) {
        this.activity = activity;
        this.callBack = callBack;
    }

    /**
     * 获取私聊列表
     */
    public void getMessagePrivateList(int page){
        new HttpManager<ResponseBean<List<MsgPrivateBean>>>("Home/YbTest/message_get", this)
                .addParams("token", MyApplication.getUserToken())
                .addParams("rows", "20")
                .addParams("page", page)
                .postRequest(new DialogCallback<ResponseBean<List<MsgPrivateBean>>>(activity) {
                    @Override
                    public void onSuccess(Response<ResponseBean<List<MsgPrivateBean>>> response) {
                        callBack.getMessagePrivateList(response.body().data);
                    }

                    @Override
                    public void onError(Response<ResponseBean<List<MsgPrivateBean>>> response) {
                        super.onError(response);
                        callBack.getMessagePrivateListFail();
                    }
                });
    }

    public void setMessageState(String message_ids){
        new HttpManager<ResponseBean<Void>>("Home/YbTest/read_set", this)
                .addParams("token", MyApplication.getUserToken())
                .addParams("message_ids", message_ids)
                .addParams("read_status", "read")
                .addParams("message_type", "message")
                .postRequest(new JsonCallback<ResponseBean<Void>>() {
                    @Override
                    public void onSuccess(Response<ResponseBean<Void>> response) {

                    }
                });

    }

    public interface MessageCallBack {

        void getMessagePrivateList(List<MsgPrivateBean> data);

        void getMessagePrivateListFail();
    }
}
