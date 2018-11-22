package com.power.mercenary.presenter;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.lzy.okgo.model.Response;
import com.power.mercenary.MyApplication;
import com.power.mercenary.bean.TieZiDetailsBean;
import com.power.mercenary.bean.TieZiListBean;
import com.power.mercenary.http.DialogCallback;
import com.power.mercenary.http.HttpManager;
import com.power.mercenary.http.JsonCallback;
import com.power.mercenary.http.ResponseBean;

import java.util.List;

/**
 * admin  2018/7/10 wan
 */
public class TieZiListPresenter {
    private Activity activity;

    private TaskListCallBack callBack;

    public TieZiListPresenter(Activity activity, TaskListCallBack callBack) {
        this.activity = activity;
        this.callBack = callBack;
    }

    /**
     * 获取帖子列表
     *
     *pageNum	否	int	当前页码 默认为1
     * pageSize	否	int	每页显示数据 默认为8
     *task_type	否	int	任务类型id
     *task_type_child	否	int	任务子类型id
     *
     */
    public void getTaskList(int pageNum, String task_type, String task_type_child) {
        new HttpManager<ResponseBean<List<TieZiListBean>>>("Home/CommonDrunkery/post_list", this)
                .addParams("pageNum", pageNum)
                .addParams("pageSize", 10)
                .addParams("task_type", task_type)
                .addParams("task_type_child", task_type_child)
                .postRequest(new JsonCallback<ResponseBean<List<TieZiListBean>>>() {
                    @Override
                    public void onSuccess(Response<ResponseBean<List<TieZiListBean>>> response) {
                        callBack.getTaskList(response.body().data);
                    }
                    @Override
                    public void onError(Response<ResponseBean<List<TieZiListBean>>> response) {
                        super.onError(response);
                        callBack.getListFail();
                    }
                });
    }

    /**
     * 获取帖子详情
     *
     * @param id 帖子id
     */
    public void getTaskDetails(String id) {
        new HttpManager<ResponseBean<TieZiDetailsBean>>("Home/CommonDrunkery/post_detail", this)
                .addParams("id", id)
                .postRequest(new DialogCallback<ResponseBean<TieZiDetailsBean>>(activity) {
                    @Override
                    public void onSuccess(Response<ResponseBean<TieZiDetailsBean>> response) {
                        callBack.getTaskDetails(response.body().data);
                    }
                });
    }

    /**
     * 酒馆详情-发表留言
     *
     * @param id 帖子id
     * @param liuyan_content 留言内容
     */
    public void getPubPinglun(String id,String liuyan_content) {
        new HttpManager<ResponseBean>("Home/Drunkery/liuyan_add", this)
                .addParams("token", MyApplication.getUserToken())
                .addParams("id", id)
                .addParams("liuyan_content", liuyan_content)
                .postRequest(new DialogCallback<ResponseBean>(activity) {
                    @Override
                    public void onSuccess(Response<ResponseBean> response) {
                        callBack.getPubPinglun((ResponseBean) response.body());
                    }
                });
    }

    /**
     * 酒馆详情-发表留言
     *
     * @param id 帖子id
     * @param huifu_content 回复内容
     */
    public void getHuifu(String id,String huifu_content) {
        new HttpManager<ResponseBean>("Home/Drunkery/huifu_add", this)
                .addParams("token", MyApplication.getUserToken())
                .addParams("id", id)
                .addParams("huifu_content", huifu_content)
                .postRequest(new DialogCallback<ResponseBean>(activity) {
                    @Override
                    public void onSuccess(Response<ResponseBean> response) {
                        callBack.getHuifu(response.body());
                    }
                });
    }
    /**
     * 酒馆详情-发帖
     *
     *参数名	必选	类型	说明
     *
     *token	是	string	用户token
     *task_type	是	int	类型id
     *task_type_child	否	int	子类型id
     *post_content	是	string	帖子内容
     *post_img	否	string	帖子图片
     */
    public void getPost(String task_type,String task_type_child,String post_content,String post_img) {
        new HttpManager<ResponseBean>("Home/Drunkery/post_add", this)
                .addParams("token", MyApplication.getUserToken())
                .addParams("task_type", task_type)
                .addParams("task_type_child", task_type_child)
                .addParams("post_content", post_content)
                .addParams("post_img", post_img)
                .postRequest(new DialogCallback<ResponseBean>(activity) {
                    @Override
                    public void onSuccess(Response<ResponseBean> response) {
                        callBack.getPost(response.body());
                    }
                });
    }

    public interface TaskListCallBack {
        void getTaskList(List<TieZiListBean> datas);

        void getTaskDetails(TieZiDetailsBean datas);

        void getListFail();

        void getPubPinglun(ResponseBean datas);

        void getHuifu(ResponseBean datas);

        void getPost(ResponseBean datas);

    }
}
