package com.power.mercenary.presenter;

import android.app.Activity;

import com.lzy.okgo.model.Response;
import com.power.mercenary.MyApplication;
import com.power.mercenary.bean.task.TaskListBean;
import com.power.mercenary.http.DialogCallback;
import com.power.mercenary.http.HttpManager;
import com.power.mercenary.http.ResponseBean;

import java.util.List;

/**
 * admin  2018/7/10 wan
 */
public class TaskListPresenter {
    private Activity activity;

    private TaskListCallBack callBack;

    public TaskListPresenter(Activity activity, TaskListCallBack callBack) {
        this.activity = activity;
        this.callBack = callBack;
    }

    /**
     * 获取任务列表
     *
     * @param pageNum         当前页码 默认为1
     * @param pageSize        每页显示数据 默认为8
     *  param order           排序依据 参数 publish_time：按发布时间排序 pay_amount：按佣金排序 默认为 publish_time
     *  param order_type      排序方式 参数DESC ，ASC，默认为 DESC
     * @param task_type       任务类型id
     * @param task_type_child 任务子类型id
     */
    public void getTaskList(int pageNum, int pageSize, int task_type, int task_type_child) {
        new HttpManager<ResponseBean<List<TaskListBean>>>("Home/Task/get_tasklist", this)
                .addParams("token", MyApplication.getUserToken())
                .addParams("pageNum", pageNum)
                .addParams("pageSize", pageSize)
                .addParams("order", "publish_time")
                .addParams("order_type", "DESC")
                .addParams("task_type", task_type)
                .addParams("task_type_child", task_type_child)
                .postRequest(new DialogCallback<ResponseBean<List<TaskListBean>>>(activity) {
                    @Override
                    public void onSuccess(Response<ResponseBean<List<TaskListBean>>> response) {
                        if (response.body().data != null) {
                            callBack.getTaskList(response.body().data);
                        }
                    }
                });
    }

    public interface TaskListCallBack {
        void getTaskList(List<TaskListBean> datas);
    }
}
