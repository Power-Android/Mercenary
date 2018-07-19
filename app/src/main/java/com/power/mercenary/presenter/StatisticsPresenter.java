package com.power.mercenary.presenter;

import android.app.Activity;
import android.drm.DrmStore;
import android.os.HandlerThread;

import com.lzy.okgo.model.Response;
import com.power.mercenary.MyApplication;
import com.power.mercenary.bean.mytask.StatisticsListBean;
import com.power.mercenary.bean.mytask.StatisticsNumBean;
import com.power.mercenary.http.DialogCallback;
import com.power.mercenary.http.HttpManager;
import com.power.mercenary.http.JsonCallback;
import com.power.mercenary.http.ResponseBean;

import java.util.List;

/**
 * admin  2018/7/18 wan
 */
public class StatisticsPresenter {
    private Activity activity;

    private StatisticsCallBack callBack;

    public StatisticsPresenter(Activity activity, StatisticsCallBack callBack) {
        this.activity = activity;
        this.callBack = callBack;
    }

    /**
     * 发布通缉
     */
    public void getTaskStatisticsPublishNum(){
        new HttpManager<ResponseBean<StatisticsNumBean>>("Home/MyTask/publish_sum_total", this)
                .addParams("token", MyApplication.getUserToken())
                .postRequest(new DialogCallback<ResponseBean<StatisticsNumBean>>(activity) {
                    @Override
                    public void onSuccess(Response<ResponseBean<StatisticsNumBean>> response) {
                        callBack.getTaskStatisticsPublishNum(response.body().data);
                    }
                });
    }

    /**
     * 接受统计
     */
    public void getTaskStatisticsAcceptNum(){
        new HttpManager<ResponseBean<StatisticsNumBean>>("Home/MyTask/receive_sum_total", this)
                .addParams("token", MyApplication.getUserToken())
                .postRequest(new DialogCallback<ResponseBean<StatisticsNumBean>>(activity) {
                    @Override
                    public void onSuccess(Response<ResponseBean<StatisticsNumBean>> response) {
                        callBack.getTaskStatisticsAcceptNum(response.body().data);
                    }   
                });
    }

    /**
     * 发布列表
     * @param pageNum
     * @param star
     */
    public void getTaskStatisticsPublishList(int pageNum, int star){
        new HttpManager<ResponseBean<List<StatisticsListBean>>>("Home/MyTask/publish_sum_list", this)
                .addParams("token", MyApplication.getUserToken())
                .addParams("pageNum", pageNum)
                .addParams("pageSize", 20)
                .addParams("star", star)
                .postRequest(new JsonCallback<ResponseBean<List<StatisticsListBean>>>() {
                    @Override
                    public void onSuccess(Response<ResponseBean<List<StatisticsListBean>>> response) {
                        callBack.getTaskStatisticsPublishList(response.body().data);
                    }
                });

    }

    /**
     * 接受列表
     * @param pageNum
     * @param star
     */
    public void getTaskStatisticsAcceptList(int pageNum, int star){
        new HttpManager<ResponseBean<List<StatisticsListBean>>>("Home/MyTask/receive_sum_list", this)
                .addParams("token", MyApplication.getUserToken())
                .addParams("pageNum", pageNum)
                .addParams("pageSize", 20)
                .addParams("star", star)
                .postRequest(new JsonCallback<ResponseBean<List<StatisticsListBean>>>() {
                    @Override
                    public void onSuccess(Response<ResponseBean<List<StatisticsListBean>>> response) {
                        callBack.getTaskStatisticsAcceptList(response.body().data);
                    }
                });

    }

    public interface StatisticsCallBack{

        void getTaskStatisticsPublishNum(StatisticsNumBean data);

        void getTaskStatisticsAcceptNum(StatisticsNumBean data);

        void getTaskStatisticsPublishList(List<StatisticsListBean> data);

        void getTaskStatisticsAcceptList(List<StatisticsListBean> data);
    }
}
