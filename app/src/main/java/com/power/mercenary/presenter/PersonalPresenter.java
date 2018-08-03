package com.power.mercenary.presenter;

import android.app.Activity;

import com.lzy.okgo.model.Response;
import com.power.mercenary.MyApplication;
import com.power.mercenary.bean.MyRecordBean;
import com.power.mercenary.bean.MyTaskBean;
import com.power.mercenary.bean.PersonalBean;
import com.power.mercenary.http.DialogCallback;
import com.power.mercenary.http.HttpManager;
import com.power.mercenary.http.JsonCallback;
import com.power.mercenary.http.ResponseBean;

/**
 * admin  2018/8/2 wan
 */
public class PersonalPresenter {
    private Activity activity;

    private PersonalCallBack callBack;

    public PersonalPresenter(Activity activity, PersonalCallBack callBack) {
        this.activity = activity;
        this.callBack = callBack;
    }

    /**
     * 个人资料——基本信息
     * @param uid
     */
    public void getPersonalData(String uid){
        new HttpManager<ResponseBean<PersonalBean>>("Home/UserCenter/get_userinfo", this)
                .addParams("token", MyApplication.getUserToken())
                .addParams("uid", uid)
                .postRequest(new DialogCallback<ResponseBean<PersonalBean>>(activity) {
                    @Override
                    public void onSuccess(Response<ResponseBean<PersonalBean>> response) {
                        callBack.getPersonalData(response.body().data);
                    }
                });
    }

    /**
     * 个人资料——我的履历
     * @param uid
     */
    public void getMyRecord(String uid){
        new HttpManager<ResponseBean<MyRecordBean>>("Home/UserCenter/get_uservitae", this)
                .addParams("token", MyApplication.getUserToken())
                .addParams("uid", uid)
                .postRequest(new JsonCallback<ResponseBean<MyRecordBean>>() {
                    @Override
                    public void onSuccess(Response<ResponseBean<MyRecordBean>> response) {
                        callBack.getMyRecord(response.body().data);
                    }
                });
    }

    /**
     * 个人资料——我的任务
     * @param uid
     */
    public void getMyTask(String uid){
        new HttpManager<ResponseBean<MyTaskBean>>("Home/UserCenter/get_tasksum", this)
                .addParams("token", MyApplication.getUserToken())
                .addParams("uid", uid)
                .postRequest(new JsonCallback<ResponseBean<MyTaskBean>>() {
                    @Override
                    public void onSuccess(Response<ResponseBean<MyTaskBean>> response) {
                        callBack.getMyTask(response.body().data);
                    }
                });
    }

    /**
     * 关注
     * @param care_user_id
     * @param type
     */
    public void requestAttention(String care_user_id, int type){
        new HttpManager<ResponseBean<Void>>("Home/Handle/add_care", this)
                .addParams("token", MyApplication.getUserToken())
                .addParams("care_user_id", care_user_id)
                .addParams("type", type)
                .postRequest(new DialogCallback<ResponseBean<Void>>(activity) {
                    @Override
                    public void onSuccess(Response<ResponseBean<Void>> response) {
                        callBack.requestAttention();
                    }
                });
    }

    public interface PersonalCallBack {

        void getPersonalData(PersonalBean data);

        void getMyRecord(MyRecordBean data);

        void getMyTask(MyTaskBean data);

        void requestAttention();
    }
}
