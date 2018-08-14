package com.power.mercenary.presenter;

import android.app.Activity;

import com.lzy.okgo.model.Response;
import com.power.mercenary.MyApplication;
import com.power.mercenary.bean.MsgTavernBean;
import com.power.mercenary.http.DialogCallback;
import com.power.mercenary.http.HttpManager;
import com.power.mercenary.http.JsonCallback;
import com.power.mercenary.http.ResponseBean;

import java.util.List;

/**
 * admin  2018/8/8 wan
 */
public class MsgTavernPresenter {
    private Activity activity;

    private TavernCallBack callBack;

    public MsgTavernPresenter(Activity activity, TavernCallBack callBack) {
        this.activity = activity;
        this.callBack = callBack;
    }

    public void getTavernList(int page){
        new HttpManager<ResponseBean<List<MsgTavernBean>>>("Home/YbTest/post_get", this)
                .addParams("token", MyApplication.getUserToken())
                .addParams("page", page)
                .addParams("rows", 20)
                .postRequest(new DialogCallback<ResponseBean<List<MsgTavernBean>>>(activity) {
                    @Override
                    public void onSuccess(Response<ResponseBean<List<MsgTavernBean>>> response) {
                        callBack.getTavernList(response.body().data);
                    }

                    @Override
                    public void onError(Response<ResponseBean<List<MsgTavernBean>>> response) {
                        super.onError(response);
                        callBack.getTavernListFail();
                    }
                });
    }

    public void setMessageState(String message_ids, String post_type){//post_type 1：评论 2：回复
        new HttpManager<ResponseBean<Void>>("Home/YbTest/read_set", this)
                .addParams("token", MyApplication.getUserToken())
                .addParams("message_ids", message_ids)
                .addParams("read_status", "read")
                .addParams("message_type", "post")
                .addParams("post_type", post_type)
                .postRequest(new JsonCallback<ResponseBean<Void>>() {
                    @Override
                    public void onSuccess(Response<ResponseBean<Void>> response) {

                    }
                });

    }

    public interface TavernCallBack{

        void getTavernList(List<MsgTavernBean> data);

        void getTavernListFail();
    }
}
