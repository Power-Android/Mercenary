package com.power.mercenary.presenter.publish;

import android.app.Activity;
import android.os.Handler;

import com.google.gson.Gson;
import com.lzy.okgo.model.Response;
import com.power.mercenary.MyApplication;
import com.power.mercenary.activity.RegisterActivity;
import com.power.mercenary.bean.mytask.PublishTaskBean;
import com.power.mercenary.http.DialogCallback;
import com.power.mercenary.http.HttpManager;
import com.power.mercenary.http.JsonCallback;
import com.power.mercenary.http.OkhtttpUtils;
import com.power.mercenary.http.ResponseBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * admin  2018/7/18 wan
 */
public class PublishPresenter {
    private Activity activity;

    private PublishCallBack callBack;

    public PublishPresenter(Activity activity, PublishCallBack callBack) {
        this.activity = activity;
        this.callBack = callBack;
    }

    /**
     * 我发布的任务列表
     *
     * @param pageNum
     * @param task_status
     */
    public void getPublishTaskList(int pageNum, int task_status) {
/*        new HttpManager<ResponseBean<List<PublishTaskBean>>>("Home/MyTask/publish_list", this)
                .addParams("token", MyApplication.getUserToken())
                .addParams("pageNum", pageNum)
                .addParams("pageSize", 10)
                .addParams("task_status", task_status)
                .postRequest(new DialogCallback<ResponseBean<List<PublishTaskBean>>>(activity) {
                    @Override
                    public void onSuccess(Response<ResponseBean<List<PublishTaskBean>>> response) {
                        callBack.getPublishTaskList(response.body().data);
                    }

                    @Override
                    public void onError(Response<ResponseBean<List<PublishTaskBean>>> response) {
                        super.onError(response);
                        callBack.getPublishTaskListFail();
                    }
                });*/


        Map<String, String> map = new HashMap<>();
        map.put("pageNum", pageNum + "");
        map.put("task_status", task_status + "");
        map.put("pageSize", 10 + "");
        map.put("token", MyApplication.getUserToken());

        OkhtttpUtils.getInstance().doPost("http://yb.dashuibei.com/index.php/Home/MyTask/publish_list", map, new OkhtttpUtils.OkCallback() {
            @Override
            public void onFailure(Exception e) {

                if (e.getMessage() != "") {


                    //callBack.getPublishTaskListFail();

                }


            }

            @Override
            public void onResponse(String json) {

                Gson gson = new Gson();

                PublishTaskBean publishTaskBean = gson.fromJson(json, PublishTaskBean.class);

                if (publishTaskBean.getCode() == 0) {

                    List<PublishTaskBean.DataBean> data = publishTaskBean.getData();

                    callBack.getPublishTaskList(data);


                }

            }
        });

    }

    /**
     * 我发布的任务列表
     *
     * @param pageNum
     */
    public void getPublishTaskList(int pageNum) {
/*        new HttpManager<ResponseBean<List<PublishTaskBean>>>("Home/MyTask/publish_list", this)
                .addParams("token", MyApplication.getUserToken())
                .addParams("pageNum", pageNum)
                .addParams("pageSize", 10)
                .postRequest(new DialogCallback<ResponseBean<List<PublishTaskBean>>>(activity) {
                    @Override
                    public void onSuccess(Response<ResponseBean<List<PublishTaskBean>>> response) {
                        callBack.getPublishTaskList(response.body().data);
                    }

                    @Override
                    public void onError(Response<ResponseBean<List<PublishTaskBean>>> response) {
                        super.onError(response);
                        callBack.getPublishTaskListFail();
                    }
                });*/

        Map<String, String> map = new HashMap<>();
        map.put("pageNum", pageNum + "");
        map.put("token", MyApplication.getUserToken());

        OkhtttpUtils.getInstance().doPost("http://yb.dashuibei.com/index.php/Home/MyTask/publish_list", map, new OkhtttpUtils.OkCallback() {
            @Override
            public void onFailure(Exception e) {

                //callBack.getPublishTaskListFail();

                if (e.getMessage() != "") {


                    //callBack.getPublishTaskListFail();

                }


            }

            @Override
            public void onResponse(String json) {

                Gson gson = new Gson();

                PublishTaskBean publishTaskBean = gson.fromJson(json, PublishTaskBean.class);

                if (publishTaskBean.getCode() == 0) {

                    List<PublishTaskBean.DataBean> data = publishTaskBean.getData();

                    callBack.getPublishTaskList(data);


                }

            }
        });

    }

    /**
     * 上架任务
     *
     * @param id
     */
    public void putTaskRequest(String id, final int position) {
        new HttpManager<ResponseBean<Void>>("Home/MyTask/shangjia", this)
                .addParams("token", MyApplication.getUserToken())
                .addParams("id", id)
                .postRequest(new DialogCallback<ResponseBean<Void>>(activity) {
                    @Override
                    public void onSuccess(Response<ResponseBean<Void>> response) {
                        callBack.putTaskRequestSuccess(position);
                    }
                });
    }

    /**
     * 下架任务
     *
     * @param id
     */
    public void outTaskRequest(String id, final int position) {
        new HttpManager<ResponseBean<Void>>("Home/MyTask/xiajia", this)
                .addParams("token", MyApplication.getUserToken())
                .addParams("id", id)
                .postRequest(new DialogCallback<ResponseBean<Void>>(activity) {
                    @Override
                    public void onSuccess(Response<ResponseBean<Void>> response) {
                        callBack.outTaskRequestSuccess(position);
                    }
                });
    }

    /**
     * 任务审核
     *
     * @param id
     * @param type 审核通过 1 审核不通过 2
     */
    public void auditTaskRequest(String id, final int type, final int position) {
        new HttpManager<ResponseBean<Void>>("Home/MyTask/shenhe", this)
                .addParams("token", MyApplication.getUserToken())
                .addParams("id", id)
                .addParams("type", type)
                .postRequest(new DialogCallback<ResponseBean<Void>>(activity) {
                    @Override
                    public void onSuccess(Response<ResponseBean<Void>> response) {
                        callBack.auditTaskRequestSuccess(type, position);
                    }
                });
    }

    /**
     * pingjia
     *
     * @param id
     * @param star
     */
    public void appraiseRequest(String id, int star) {
        new HttpManager<ResponseBean<Void>>("Home/MyTask/pingjia", this)
                .addParams("token", MyApplication.getUserToken())
                .addParams("id", id)
                .addParams("star", star)
                .postRequest(new DialogCallback<ResponseBean<Void>>(activity) {
                    @Override
                    public void onSuccess(Response<ResponseBean<Void>> response) {
                        callBack.appraiseRequestSuccess();
                    }
                });
    }

    public interface PublishCallBack {
        void getPublishTaskList(List<PublishTaskBean.DataBean> datas);

        void getPublishTaskListFail();

        void putTaskRequestSuccess(int position);

        void outTaskRequestSuccess(int position);

        void auditTaskRequestSuccess(int type, int position);

        void appraiseRequestSuccess();
    }
}
