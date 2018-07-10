package com.power.mercenary.presenter;

import android.app.Activity;

import com.lzy.okgo.model.Response;
import com.power.mercenary.MyApplication;
import com.power.mercenary.bean.task.TaskChildClssifyBean;
import com.power.mercenary.bean.task.TaskClassifyBean;
import com.power.mercenary.http.DialogCallback;
import com.power.mercenary.http.HttpManager;
import com.power.mercenary.http.ResponseBean;

import java.util.List;

/**
 * admin  2018/7/9 wan
 */
public class TaskClassifyPresenter {
    private Activity activity;

    private TaskClassifyCallBack callBack;

    public TaskClassifyPresenter(Activity activity, TaskClassifyCallBack callBack) {
        this.activity = activity;
        this.callBack = callBack;
    }

    /**
     * 获取任务分类
     */
    public void getTaskClassify() {
        new HttpManager<ResponseBean<List<TaskClassifyBean>>>("Home/Task/get_tasktype", this)
                .addParams("token", MyApplication.getUserToken())
                .postRequest(new DialogCallback<ResponseBean<List<TaskClassifyBean>>>(activity) {
                    @Override
                    public void onSuccess(Response<ResponseBean<List<TaskClassifyBean>>> response) {
                        if (response.body().data != null) {
                            callBack.getTaskClassify(response.body().data);
                        }
                    }
                });
    }

    /**
     * 获取任务分类的子分类
     *
     * @param pid 分类的ID
     */
    public void getTaskChildClassify(String pid) {
        new HttpManager<ResponseBean<List<TaskChildClssifyBean>>>("Home/Task/getchild_tasktype", this)
                .addParams("token", MyApplication.getUserToken())
                .addParams("pid", pid)
                .postRequest(new DialogCallback<ResponseBean<List<TaskChildClssifyBean>>>(activity) {
                    @Override
                    public void onSuccess(Response<ResponseBean<List<TaskChildClssifyBean>>> response) {
                        if (response.body().data != null) {
                            callBack.getTaskChildClassify(response.body().data);
                        }
                    }
                });
    }

    public interface TaskClassifyCallBack {
        void getTaskClassify(List<TaskClassifyBean> datas);

        void getTaskChildClassify(List<TaskChildClssifyBean> datas);
    }
}
