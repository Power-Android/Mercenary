package com.power.mercenary.presenter;

import android.app.Activity;

import com.lzy.okgo.model.Response;
import com.power.mercenary.bean.BannerBean;
import com.power.mercenary.bean.MainTaskBean;
import com.power.mercenary.http.DialogCallback;
import com.power.mercenary.http.HttpManager;
import com.power.mercenary.http.ResponseBean;

import java.util.List;

/**
 * admin  2018/7/31 wan
 */
public class MainPresenter {
    private Activity activity;

    private MainCallBack callBack;

    public MainPresenter(Activity activity, MainCallBack callBack) {
        this.activity = activity;
        this.callBack = callBack;
    }

    /**
     * 获取任务列表
     */
    public void getTaskList(String city){
        new HttpManager<ResponseBean<MainTaskBean>>("Home/Index/index_task", this)
                .addParams("city", city)
                .postRequest(new DialogCallback<ResponseBean<MainTaskBean>>(activity) {
                    @Override
                    public void onSuccess(Response<ResponseBean<MainTaskBean>> response) {
                        callBack.getTaskList(response.body().data);
                    }
                });
    }

    public void getBannerList(int type){
        new HttpManager<ResponseBean<List<BannerBean>>>("Home/Index/get_banner", this)
                .addParams("type", type)
                .postRequest(new DialogCallback<ResponseBean<List<BannerBean>>>(activity) {
                    @Override
                    public void onSuccess(Response<ResponseBean<List<BannerBean>>> response) {
                        callBack.getBannerList(response.body().data);
                    }
                });
    }

    public interface MainCallBack{
        void getTaskList(MainTaskBean taskBean);
        void getBannerList(List<BannerBean> datas);
    }
}
