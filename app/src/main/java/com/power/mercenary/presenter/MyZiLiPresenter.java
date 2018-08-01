package com.power.mercenary.presenter;

import android.app.Activity;

import com.lzy.okgo.model.Response;
import com.power.mercenary.MyApplication;
import com.power.mercenary.bean.MyZiLiBean;
import com.power.mercenary.http.DialogCallback;
import com.power.mercenary.http.HttpManager;
import com.power.mercenary.http.ResponseBean;

/**
 * admin  2018/7/5 wan
 */
public class MyZiLiPresenter {
    private Activity activity;

    private Collection callBack;

    public MyZiLiPresenter(Activity activity, Collection callBack) {
        this.activity = activity;
        this.callBack = callBack;
    }

    /**
     * 获取用户资历
     */
    public void getmZiLi() {
        new HttpManager<ResponseBean<MyZiLiBean>>("Home/UserCenter/get_zili", this)
                .addParams("token", MyApplication.getUserToken())
                .postRequest(new DialogCallback<ResponseBean<MyZiLiBean>>(activity) {
                    @Override
                    public void onSuccess(Response<ResponseBean<MyZiLiBean>> response) {
                        callBack.getmZiLi(response.body().data);
                    }
                });
    }
    /**
     * 设置用户资历
     *
     * token	是	string	用户token
     industry	否	string	行业
     workyear	否	string	工作年限
     speciality	否	string	特长
     introduce	否	string	个人介绍
     certificate	否	string	证书图片地址
     */
    public void setmZiLi(String industry,String workyear,String speciality,String introduce,String certificate) {
        new HttpManager<ResponseBean>("Home/UserCenter/set_zili", this)
                .addParams("token", MyApplication.getUserToken())
                .addParams("industry",industry)
                .addParams("workyear", workyear)
                .addParams("speciality", speciality)
                .addParams("introduce", introduce)
                .addParams("certificate", certificate)
                .postRequest(new DialogCallback<ResponseBean>(activity) {
                    @Override
                    public void onSuccess(Response<ResponseBean> response) {
                        callBack.setmZiLi(response.body());
                    }
                });
    }

    /**
     * 设置认证信息
     *
     token	是	string	用户token
     id_card	否	string	身份证号
     identity_front	否	string	身份证地址
     business_license	否	string	营业执照地址
     */
    public void setIdCard(String id_card,String identity_front,String business_license) {
        new HttpManager<ResponseBean>("Home/UserCenter/set_renzheng", this)
                .addParams("token", MyApplication.getUserToken())
                .addParams("id_card",id_card)
                .addParams("identity_front", identity_front)
                .addParams("business_license", business_license)
                .postRequest(new DialogCallback<ResponseBean>(activity) {
                    @Override
                    public void onSuccess(Response<ResponseBean> response) {
                        callBack.setIdCard(response.body());
                    }
                });
    }

    public interface Collection {

        void getmZiLi(MyZiLiBean response);

        void setmZiLi(ResponseBean response);

        void setIdCard(ResponseBean response);
    }
}
