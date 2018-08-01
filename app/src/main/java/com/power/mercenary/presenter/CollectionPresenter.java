package com.power.mercenary.presenter;

import android.app.Activity;

import com.lzy.okgo.model.Response;
import com.power.mercenary.CollectionPeopleBean;
import com.power.mercenary.MyApplication;
import com.power.mercenary.bean.CollectionBean;
import com.power.mercenary.http.DialogCallback;
import com.power.mercenary.http.HttpManager;
import com.power.mercenary.http.ResponseBean;

import java.util.List;

/**
 * admin  2018/7/5 wan
 */
public class CollectionPresenter {
    private Activity activity;

    private Collection callBack;

    public CollectionPresenter(Activity activity, Collection callBack) {
        this.activity = activity;
        this.callBack = callBack;
    }

    /**
     * 获取用户收藏任务
     */
    public void getCollectionTask() {
        new HttpManager<ResponseBean<List<CollectionBean>>>("Home/UserCenter/get_collect", this)
                .addParams("token", MyApplication.getUserToken())
                .postRequest(new DialogCallback<ResponseBean<List<CollectionBean>>>(activity) {
                    @Override
                    public void onSuccess(Response<ResponseBean<List<CollectionBean>>> response) {
                        callBack.getCollectionTask(response.body().data);
                    }
                });
    }

    /**
     * 获取用户收藏的人
     */
    public void getCollectionPeople() {
        new HttpManager<ResponseBean<List<CollectionPeopleBean>>>("Home/UserCenter/get_care", this)
                .addParams("token", MyApplication.getUserToken())
                .postRequest(new DialogCallback<ResponseBean<List<CollectionPeopleBean>>>(activity) {
                    @Override
                    public void onSuccess(Response<ResponseBean<List<CollectionPeopleBean>>> response) {
                        callBack.getCollectionPeople(response.body().data);
                    }
                });
    }
    public interface Collection {

        void getCollectionTask(List<CollectionBean> response);

        void getCollectionPeople(List<CollectionPeopleBean> response);
    }
}
