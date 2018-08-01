package com.power.mercenary.presenter;

import android.app.Activity;

import com.lzy.okgo.model.Response;
import com.power.mercenary.bean.CityBean;
import com.power.mercenary.bean.LocationBean;
import com.power.mercenary.http.DialogCallback;
import com.power.mercenary.http.HttpManager;
import com.power.mercenary.http.JsonCallback;
import com.power.mercenary.http.ResponseBean;

import java.util.List;

/**
 * admin  2018/7/31 wan
 */
public class LocationPresenter {
    private Activity activity;

    private LocationCallBack callBack;

    public LocationPresenter(Activity activity, LocationCallBack callBack) {
        this.activity = activity;
        this.callBack = callBack;
    }

    /**
     * 获取城市
     */
    public void getCityList(String city){
        new HttpManager<ResponseBean<List<LocationBean>>>("Home/Index/get_city", this)
                .addParams("city", city)
                .postRequest(new JsonCallback<ResponseBean<List<LocationBean>>>() {
                    @Override
                    public void onSuccess(Response<ResponseBean<List<LocationBean>>> response) {
                        callBack.getCityList(response.body().data);
                    }

                    @Override
                    public void onError(Response<ResponseBean<List<LocationBean>>> response) {
                        super.onError(response);
                        callBack.getCityListFail();
                    }
                });
    }

    public interface LocationCallBack {
        void getCityList(List<LocationBean> datas);

        void getCityListFail();
    }
}
