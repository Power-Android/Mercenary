package com.power.mercenary.presenter;

import android.app.Activity;

import com.lzy.okgo.model.Response;
import com.power.mercenary.MyApplication;
import com.power.mercenary.bean.task.TaskDetailsBean;
import com.power.mercenary.bean.task.TaskClassifyListBean;
import com.power.mercenary.bean.task.TaskListBean;
import com.power.mercenary.http.DialogCallback;
import com.power.mercenary.http.HttpManager;
import com.power.mercenary.http.JsonCallback;
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
     *  param pageSize        每页显示数据 默认为8
     *                        param order           排序依据 参数 publish_time：按发布时间排序 pay_amount：按佣金排序 默认为 publish_time
     *                        param order_type      排序方式 参数DESC ，ASC，默认为 DESC
     * @param task_type       任务类型id
     * @param task_type_child 任务子类型id
     */
    public void getTaskList(int pageNum, String task_type, String task_type_child) {
        new HttpManager<ResponseBean<List<TaskListBean>>>("Home/CommonTask/get_tasklist", this)
                .addParams("pageNum", pageNum)
                .addParams("pageSize", 10)
                .addParams("order", "publish_time")
                .addParams("order_type", "DESC")
                .addParams("task_type", task_type)
                .addParams("task_type_child", task_type_child)
                .postRequest(new DialogCallback<ResponseBean<List<TaskListBean>>>(activity) {
                    @Override
                    public void onSuccess(Response<ResponseBean<List<TaskListBean>>> response) {
                        callBack.getTaskList(response.body().data);
                    }

                    @Override
                    public void onError(Response<ResponseBean<List<TaskListBean>>> response) {
                        super.onError(response);
                        callBack.getListFail();
                    }
                });
    }

    /**
     * 获取任务详情
     *
     * @param id 查看详情的任务id
     */
    public void getTaskDetails(String id) {
        new HttpManager<ResponseBean<TaskDetailsBean>>("Home/CommonTask/get_taskdetail", this)
                .addParams("id", id)
                .postRequest(new DialogCallback<ResponseBean<TaskDetailsBean>>(activity) {
                    @Override
                    public void onSuccess(Response<ResponseBean<TaskDetailsBean>> response) {
                        callBack.getTaskDetails(response.body().data);
                    }
                });
    }

    public interface TaskListCallBack {
        void getTaskList(List<TaskListBean> datas);

        void getTaskDetails(TaskDetailsBean datas);

        void getListFail();
    }
}
