package com.power.mercenary.presenter;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.lzy.okgo.model.Response;
import com.power.mercenary.MyApplication;
import com.power.mercenary.bean.MsgDetailsBean;
import com.power.mercenary.http.DialogCallback;
import com.power.mercenary.http.HttpManager;
import com.power.mercenary.http.ResponseBean;

import java.util.List;

/**
 * admin  2018/8/10 wan
 */
public class MessageDetailsPresenter {
    private Activity activity;

    private DetailsCallBack callBack;

    public MessageDetailsPresenter(Activity activity, DetailsCallBack callBack) {
        this.activity = activity;
        this.callBack = callBack;
    }

    public void getMessageDetails(String type, String post_type, String message_id){
        new HttpManager<ResponseBean<List<MsgDetailsBean>>>("Home/YbTest/message_info_get", this)
                .addParams("token", MyApplication.getUserToken())
                .addParams("type", type)
                .addParams("post_type", "")
                .addParams("message_id", message_id)
                .addParams("page", "")
                .addParams("rows", "")
                .postRequest(new DialogCallback<ResponseBean<List<MsgDetailsBean>>>(activity) {
                    @Override
                    public void onSuccess(Response<ResponseBean<List<MsgDetailsBean>>> response) {
                        //成功方法的回调
                        Log.i("ppppppppppp", "getMessageDetails: "+response.body().data.size());

                        callBack.getMessageDetails(response.body().data);

                    }


                });
    }

    public interface DetailsCallBack {

        void getMessageDetails(List<MsgDetailsBean> data);
    }
}
