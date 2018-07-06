package com.power.mercenary.presenter;

import android.app.Activity;

import com.lzy.okgo.model.Response;
import com.power.mercenary.MyApplication;
import com.power.mercenary.http.DialogCallback;
import com.power.mercenary.http.HttpManager;
import com.power.mercenary.http.ResponseBean;

/**
 * admin  2018/7/5 wan
 */
public class FeedBackPresenter {
    private Activity activity;

    private FeedBackCallBack callBack;

    public FeedBackPresenter(Activity activity, FeedBackCallBack callBack) {
        this.activity = activity;
        this.callBack = callBack;
    }

    /**
     * 问题反馈提交
     * @param type
     * @param content
     * @param name
     * @param contact
     */
    public void requestFeedBack(String type, String content, String name, String contact){
        new HttpManager<ResponseBean>("Home/UserCenter/feedback", this)
                .addParams("token", MyApplication.getUserToken())
                .addParams("type", type)
                .addParams("content", content)
                .addParams("name", name)
                .addParams("contact", contact)
                .postRequest(new DialogCallback<ResponseBean>(activity) {
                    @Override
                    public void onSuccess(Response<ResponseBean> response) {
                        callBack.requestFeedBack();
                    }
                });
    }

    public interface FeedBackCallBack{
        void requestFeedBack();
    }
}
