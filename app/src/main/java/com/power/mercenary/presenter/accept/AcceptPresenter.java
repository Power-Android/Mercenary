package com.power.mercenary.presenter.accept;

import android.app.Activity;

import com.lzy.okgo.model.Response;
import com.power.mercenary.MyApplication;
import com.power.mercenary.bean.mytask.AcceptTaskBean;
import com.power.mercenary.http.DialogCallback;
import com.power.mercenary.http.HttpManager;
import com.power.mercenary.http.JsonCallback;
import com.power.mercenary.http.ResponseBean;

import java.util.List;

/**
 * admin  2018/7/18 wan
 */
public class AcceptPresenter {
    private Activity activity;

    private AcceptCallBack callBack;

    public AcceptPresenter(Activity activity, AcceptCallBack callBack) {
        this.activity = activity;
        this.callBack = callBack;
    }

    /**
     * 接受列表
     *
     * @param pageNum
     * @param task_status
     */
    public void getAcceptTaskList(int pageNum, int task_status) {
        new HttpManager<ResponseBean<List<AcceptTaskBean>>>("Home/MyTask/receive_list", this)
                .addParams("token", MyApplication.getUserToken())
                .addParams("pageNum", pageNum)
                .addParams("pageSize", 10)
                .addParams("task_status", task_status)
                .postRequest(new JsonCallback<ResponseBean<List<AcceptTaskBean>>>() {
                    @Override
                    public void onSuccess(Response<ResponseBean<List<AcceptTaskBean>>> response) {
                        callBack.getAcceptTaskList(response.body().data);
                    }

                    @Override
                    public void onError(Response<ResponseBean<List<AcceptTaskBean>>> response) {
                        super.onError(response);
                        callBack.getAcceptTaskListFail();
                    }
                });
    }



    public interface AcceptCallBack {
        void getAcceptTaskList(List<AcceptTaskBean> datas);

        void getAcceptTaskListFail();
    }
}
