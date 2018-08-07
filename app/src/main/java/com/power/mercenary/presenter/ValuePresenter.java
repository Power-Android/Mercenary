package com.power.mercenary.presenter;

import android.app.Activity;

import com.lzy.okgo.model.Response;
import com.power.mercenary.MyApplication;
import com.power.mercenary.bean.MingxiInfo;
import com.power.mercenary.bean.ValueInfo;
import com.power.mercenary.http.DialogCallback;
import com.power.mercenary.http.HttpManager;
import com.power.mercenary.http.ResponseBean;

/**
 * admin  2018/7/5 wan
 */
public class ValuePresenter {
    private Activity activity;

    private ValueCallBack callBack;

    public ValuePresenter(Activity activity, ValueCallBack callBack) {
        this.activity = activity;
        this.callBack = callBack;
    }

    /**
     * 获取我的价值
     */
    public void getValueInfo() {
        new HttpManager<ResponseBean<ValueInfo>>("Home/MyWallet/get_wallet", this)
                .addParams("token", MyApplication.getUserToken())
                .postRequest(new DialogCallback<ResponseBean<ValueInfo>>(activity) {
                    @Override
                    public void onSuccess(Response<ResponseBean<ValueInfo>> response) {
                        callBack.getValueInfo(response.body().data);
                    }
                });
    }

    /**
     * 零钱明细
     */
    public void getMingxiInfo(String order_type,String year,String month) {
        new HttpManager<ResponseBean<MingxiInfo>>("Home/MyWallet/get_mingxi", this)
                .addParams("token", MyApplication.getUserToken())
                .addParams("order_type",order_type)
                .addParams("year", year)
                .addParams("month",month)

                .postRequest(new DialogCallback<ResponseBean<MingxiInfo>>(activity) {
                    @Override
                    public void onSuccess(Response<ResponseBean<MingxiInfo>> response) {
//                        callBack.getValueInfo(response.body().data);
                    }
                });
    }


    public interface ValueCallBack {

        void getValueInfo(ValueInfo ValueInfo);

        void getMingxiInfo(MingxiInfo MingxiInfo);
    }
}
