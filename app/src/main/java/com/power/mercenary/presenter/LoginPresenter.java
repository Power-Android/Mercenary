package com.power.mercenary.presenter;

import android.app.Activity;

import com.lzy.okgo.model.Response;
import com.power.mercenary.MyApplication;
import com.power.mercenary.bean.user.TokenInfo;
import com.power.mercenary.bean.user.UserInfo;
import com.power.mercenary.http.DialogCallback;
import com.power.mercenary.http.HttpManager;
import com.power.mercenary.http.ResponseBean;
import com.power.mercenary.utils.MyUtils;

/**
 * Created by Administrator on 2018/7/5.
 */

public class LoginPresenter {
    private Activity activity;

    private LoginPresenter.TokenCallBack callBack;

    public LoginPresenter(Activity activity, LoginPresenter.TokenCallBack callBack) {
        this.activity = activity;
        this.callBack = callBack;
    }

    /**
     * 用户注册
     */
    public void getUserInfo(String signature,String code,String mobile,String userType){
        new HttpManager<ResponseBean<TokenInfo>>("Home/User/register", this)
                .addParams("signature",signature)
                .addParams("code",code)
                .addParams("user_type",userType)
                .addParams("mobile",mobile)
                .postRequest(new DialogCallback<ResponseBean<TokenInfo>>(activity) {
                    @Override
                    public void onSuccess(Response<ResponseBean<TokenInfo>> response) {
                        if (response.body().data != null) {
                            callBack.getTokenInfo(response.body().data);
                        }
                    }
                });
    }

    public interface TokenCallBack {
        void getTokenInfo(TokenInfo userInfo);
    }
}
