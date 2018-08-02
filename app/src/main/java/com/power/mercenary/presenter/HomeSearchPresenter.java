package com.power.mercenary.presenter;

import android.app.Activity;

import com.lzy.okgo.model.Response;
import com.power.mercenary.bean.HomHotBean;
import com.power.mercenary.bean.HotSearchBean;
import com.power.mercenary.bean.MainTaskBean;
import com.power.mercenary.http.DialogCallback;
import com.power.mercenary.http.HttpManager;
import com.power.mercenary.http.ResponseBean;

import java.util.List;

/**
 * admin  2018/7/5 wan
 */
public class HomeSearchPresenter {
    private Activity activity;

    private HomeCallBack callBack;

    public HomeSearchPresenter(Activity activity, HomeCallBack callBack) {
        this.activity = activity;
        this.callBack = callBack;
    }

    /**
     * 获取热门推荐
     */
    public void getHotInfo(int pageNum) {
        new HttpManager<ResponseBean<List<HomHotBean>>>("Home/Index/hot_post", this)
                .addParams("pageNum",pageNum)
                .addParams("pageSize","10")
                .postRequest(new DialogCallback<ResponseBean<List<HomHotBean>>>(activity) {
                    @Override
                    public void onSuccess(Response<ResponseBean<List<HomHotBean>>> response) {
                        callBack.getHotInfo(response.body().data);
                    }
                });
    }

    /**
     * 获取热门搜索
     */
    public void getHotSearchInfo(int pageNum) {
        new HttpManager<ResponseBean<List<HotSearchBean>>>("Home/Index/get_hotsearch", this)
                .postRequest(new DialogCallback<ResponseBean<List<HotSearchBean>>>(activity) {
                    @Override
                    public void onSuccess(Response<ResponseBean<List<HotSearchBean>>> response) {
                        callBack.getHotSearchInfo(response.body().data);
                    }
                });
    }

    /**
     * 获取搜索结果列表
     */
    public void getTaskInfo(String search) {
        new HttpManager<ResponseBean<List<MainTaskBean.TuijianBean>>>("Home/Index/get_search", this)
                .addParams("search",search)
                .postRequest(new DialogCallback<ResponseBean<List<MainTaskBean.TuijianBean>>>(activity) {
                    @Override
                    public void onSuccess(Response<ResponseBean<List<MainTaskBean.TuijianBean>>> response) {
                        callBack.getTaskInfo(response.body().data);
                    }
                });
    }
    public interface HomeCallBack {

        void getHotInfo(List<HomHotBean> hotInfo);

        void getHotSearchInfo(List<HotSearchBean> hotInfo);

        void getTaskInfo(List<MainTaskBean.TuijianBean> hotInfo);
    }
}
