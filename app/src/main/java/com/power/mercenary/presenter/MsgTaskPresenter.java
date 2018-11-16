package com.power.mercenary.presenter;

import android.app.Activity;

import com.lzy.okgo.model.Response;
import com.power.mercenary.MyApplication;
import com.power.mercenary.bean.MsgTaskBean;
import com.power.mercenary.http.DialogCallback;
import com.power.mercenary.http.HttpManager;
import com.power.mercenary.http.JsonCallback;
import com.power.mercenary.http.ResponseBean;

import java.util.List;

/**
 * admin  2018/8/8 wan
 */
public class MsgTaskPresenter {
    private Activity activity;

    private TaskCallBack callBack;

    public MsgTaskPresenter(Activity activity, TaskCallBack callBack) {
        this.activity = activity;
        this.callBack = callBack;
    }

    public void getTaskList(int page){
        new HttpManager<ResponseBean<List<MsgTaskBean>>>("Home/YbTest/task_message_list", this)
                .addParams("token", MyApplication.getUserToken())
                .addParams("page", page)
                .addParams("rows", 20)
                .postRequest(new DialogCallback<ResponseBean<List<MsgTaskBean>>>(activity) {
                    @Override
                    public void onSuccess(Response<ResponseBean<List<MsgTaskBean>>> response) {
                        callBack.getTaskList(response.body().data);
                    }

                    @Override
                    public void onError(Response<ResponseBean<List<MsgTaskBean>>> response) {
                        super.onError(response);
                        callBack.getTaskListFail();
                    }
                });
    }

    public void setMessageState(String message_ids){
        new HttpManager<ResponseBean<Void>>("Home/YbTest/read_set", this)
                .addParams("token", MyApplication.getUserToken())
                .addParams("message_ids", message_ids)
                .addParams("read_status", "read")
                .addParams("message_type", "task")
                .postRequest(new JsonCallback<ResponseBean<Void>>() {
                    @Override
                    public void onSuccess(Response<ResponseBean<Void>> response) {

                    }
                });

    }

    public interface TaskCallBack {

        void getTaskList(List<MsgTaskBean> data);

        void getTaskListFail();
    }
}
