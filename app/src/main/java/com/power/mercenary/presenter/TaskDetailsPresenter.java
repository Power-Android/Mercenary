package com.power.mercenary.presenter;

import android.app.Activity;
import android.util.Log;

import com.lzy.okgo.model.Response;
import com.power.mercenary.MyApplication;
import com.power.mercenary.bean.PayBean;
import com.power.mercenary.bean.task.ApplyListBean;
import com.power.mercenary.bean.task.MsgBean;
import com.power.mercenary.bean.task.MsgListBean;
import com.power.mercenary.bean.task.TaskDetailsBean;
import com.power.mercenary.http.DialogCallback;
import com.power.mercenary.http.HttpManager;
import com.power.mercenary.http.JsonCallback;
import com.power.mercenary.http.ResponseBean;

import java.util.List;

/**
 * admin  2018/7/12 wan
 */
public class TaskDetailsPresenter {
    private Activity activity;

    private TaskDetailsCallBack callBack;

    public TaskDetailsPresenter(Activity activity, TaskDetailsCallBack callBack) {
        this.activity = activity;
        this.callBack = callBack;
    }

    /**
     * 获取任务详情
     *
     * @param id 查看详情的任务id
     */
    public void getTaskDetails(String id) {
        new HttpManager<ResponseBean<TaskDetailsBean>>("Home/CommonTask/get_taskdetail", this)
                .addParams("token", MyApplication.getUserToken())
                .addParams("id", id)
                .postRequest(new DialogCallback<ResponseBean<TaskDetailsBean>>(activity) {
                    @Override
                    public void onSuccess(Response<ResponseBean<TaskDetailsBean>> response) {
                        callBack.getTaskDetails(response.body().data);
                    }
                });
    }

    /**
     * 发表留言
     *
     * @param id
     * @param leavemsg_content
     * @param leavemsg_img
     */
    public void publishMsg(String id, String leavemsg_content, String leavemsg_img) {
        new HttpManager<ResponseBean<MsgBean>>("Home/Board/add_board", this)
                .addParams("token", MyApplication.getUserToken())
                .addParams("id", id)
                .addParams("leavemsg_content", leavemsg_content)
                .addParams("leavemsg_img", leavemsg_img)
                .postRequest(new DialogCallback<ResponseBean<MsgBean>>(activity) {
                    @Override
                    public void onSuccess(Response<ResponseBean<MsgBean>> response) {
                        callBack.publishMsg(response.body().data);
                    }
                });
    }

    /**
     * 申请列表
     *
     * @param id
     * @param apply_status 0 已申请 1待选定
     */
    public void getApplyList(String id, int apply_status) {
        new HttpManager<ResponseBean<List<ApplyListBean>>>("Home/CommonTask/get_applylist", this)
                .addParams("id", id)
                .addParams("apply_status", apply_status)
                .postRequest(new JsonCallback<ResponseBean<List<ApplyListBean>>>() {
                    @Override
                    public void onSuccess(Response<ResponseBean<List<ApplyListBean>>> response) {
                        callBack.getApplyList(response.body().data);
                    }
                });
    }

    /**
     * 留言列表
     *
     * @param id
     */
    public void getMsgList(String id, int pageNum) {
        new HttpManager<ResponseBean<List<MsgListBean>>>("Home/CommonTask/get_boardlist", this)
                .addParams("pageNum", pageNum)
                .addParams("pageSize", 10)
                .addParams("id", id)
                .postRequest(new DialogCallback<ResponseBean<List<MsgListBean>>>(activity) {
                    @Override
                    public void onSuccess(Response<ResponseBean<List<MsgListBean>>> response) {
                        callBack.getMsgList(response.body().data);
                    }

                    @Override
                    public void onError(Response<ResponseBean<List<MsgListBean>>> response) {
                        super.onError(response);
                        callBack.getMsgListFail();
                    }
                });
    }

    /**
     * 用户报名
     *
     * @param id
     * @param apply_amount
     * @param leavemsg_content
     */
    public void applyRequest(String id, String apply_amount, String leavemsg_content) {
        new HttpManager<ResponseBean<Void>>("Home/Apply/add_apply", this)
                .addParams("token", MyApplication.getUserToken())
                .addParams("id", id)
                .addParams("apply_amount", apply_amount)
                .addParams("leavemsg_content", leavemsg_content)
                .postRequest(new DialogCallback<ResponseBean<Void>>(activity) {
                    @Override
                    public void onSuccess(Response<ResponseBean<Void>> response) {
                        callBack.applyRequest();
                    }
                });
    }

    /**
     * 报名人操作
     *  @param id
     * @param apply_status 状态1待选 2选定 3放弃
     * @param avatar
     */
    public void changePeople(String id, int apply_status, final String task_id, final String avatar, final String name) {
        new HttpManager<ResponseBean<Void>>("Home/Apply/change_apply", this)
                .addParams("token", MyApplication.getUserToken())
                .addParams("id", id)
                .addParams("task_id", task_id)
                .addParams("apply_status", apply_status)
                .postRequest(new DialogCallback<ResponseBean<Void>>(activity) {
                    @Override
                    public void onSuccess(Response<ResponseBean<Void>> response) {
                        callBack.changePeople(response, avatar, name);
                    }
                });
    }

    /**
     *  1收藏 2取消
     * @param task_id
     * @param type
     */
    public void changeCollection(String task_id, int type){
        new HttpManager<ResponseBean<Void>>("Home/Handle/add_collect", this)
                .addParams("token", MyApplication.getUserToken())
                .addParams("task_id", task_id)
                .addParams("type", type)
                .postRequest(new JsonCallback<ResponseBean<Void>>() {
                    @Override
                    public void onSuccess(Response<ResponseBean<Void>> response) {
                        callBack.changeCollection();
                    }
                });
    }

    public void toPay(String task_id){
        new HttpManager<ResponseBean<PayBean>>("Home/Pay/topay", this)
                .addParams("token", MyApplication.getUserToken())
                .addParams("task_id", task_id)
                .postRequest(new DialogCallback<ResponseBean<PayBean>>(activity) {
                    @Override
                    public void onSuccess(Response<ResponseBean<PayBean>> response) {
                        callBack.toPayRequest(response.body().data);
                    }
                });
    }

    public interface TaskDetailsCallBack {
        void getTaskDetails(TaskDetailsBean datas);

        void publishMsg(MsgBean datas);

        void getApplyList(List<ApplyListBean> datas);

        void getMsgList(List<MsgListBean> datas);

        void applyRequest();

        void changePeople(Response<ResponseBean<Void>> response, String avatar, String name);

        void changeCollection();

        void getMsgListFail();

        void toPayRequest(PayBean data);
    }
}
