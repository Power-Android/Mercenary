package com.power.mercenary.presenter;

import android.app.Activity;
import android.util.Log;

import com.lzy.okgo.model.Response;
import com.power.mercenary.MyApplication;
import com.power.mercenary.bean.MsgSystemBean;
import com.power.mercenary.http.DialogCallback;
import com.power.mercenary.http.HttpManager;
import com.power.mercenary.http.JsonCallback;
import com.power.mercenary.http.ResponseBean;

import java.util.List;

/**
 * admin  2018/8/8 wan
 */
public class MsgSystemPresenter {
    private Activity activity;

    private SystemCallBack callBack;

    public MsgSystemPresenter(Activity activity, SystemCallBack callBack) {
        this.activity = activity;
        this.callBack = callBack;
    }

    public void getSystemList(int page){
        new HttpManager<ResponseBean<List<MsgSystemBean>>>("Home/YbTest/notice_list", this)
                .addParams("token", MyApplication.getUserToken())
                .addParams("page", page)
                .addParams("rows", 20)
                .postRequest(new DialogCallback<ResponseBean<List<MsgSystemBean>>>(activity) {
                    @Override
                    public void onSuccess(Response<ResponseBean<List<MsgSystemBean>>> response) {
                        callBack.getSystemList(response.body().data);
                    }

                    @Override
                    public void onError(Response<ResponseBean<List<MsgSystemBean>>> response) {
                        super.onError(response);
                        callBack.getSystemListFail();
                    }
                });
    }

    public void setMessageState(String message_ids){
        new HttpManager<ResponseBean<Void>>("Home/YbTest/read_set", this)
                .addParams("token", MyApplication.getUserToken())
                .addParams("message_ids", message_ids)
                .addParams("read_status", "read")
                .addParams("message_type", "notice")
                .postRequest(new JsonCallback<ResponseBean<Void>>() {
                    @Override
                    public void onSuccess(Response<ResponseBean<Void>> response) {

                    }
                });

    }

    public interface SystemCallBack {

        void getSystemList(List<MsgSystemBean> data);

        void getSystemListFail();
    }
}
