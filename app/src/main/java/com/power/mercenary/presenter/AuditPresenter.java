package com.power.mercenary.presenter;

import android.app.Activity;

import com.lzy.okgo.model.Response;
import com.power.mercenary.MyApplication;
import com.power.mercenary.bean.user.AuditInfo;
import com.power.mercenary.http.DialogCallback;
import com.power.mercenary.http.HttpManager;
import com.power.mercenary.http.ResponseBean;

/**
 * admin  2018/7/5 wan
 */
public class AuditPresenter {
    private Activity activity;

    private AuditCallBack callBack;

    public AuditPresenter(Activity activity, AuditCallBack callBack) {
        this.activity = activity;
        this.callBack = callBack;
    }

    /**
     * 获取审核状态
     * <p>
     * 0：未审核 1：审核中 2：审核通过 3：审核未通过
     */
    public void getAuditState() {
        new HttpManager<ResponseBean<AuditInfo>>("Home/UserCenter/get_check", this)
                .addParams("token", MyApplication.getUserToken())
                .postRequest(new DialogCallback<ResponseBean<AuditInfo>>(activity) {
                    @Override
                    public void onSuccess(Response<ResponseBean<AuditInfo>> response) {
                        if (response.body().data != null) {
                            callBack.getAuditState(response.body().data);
                        }
                    }
                });
    }

    public interface AuditCallBack {
        void getAuditState(AuditInfo auditInfo);
    }
}
