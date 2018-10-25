package com.power.mercenary.presenter;

import android.app.Activity;
import android.util.Log;

import com.lzy.okgo.model.Response;
import com.power.mercenary.bean.SuccessBean;
import com.power.mercenary.bean.user.TokenInfo;
import com.power.mercenary.http.DialogCallback;
import com.power.mercenary.http.HttpManager;
import com.power.mercenary.http.ResponseBean;
import com.power.mercenary.utils.TUtils;

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
    public void getUserInfo(String signature,String code,String mobile,String userType,String pwd){
        new HttpManager<ResponseBean<TokenInfo>>("Home/User/register", this)
                .addParams("signature",signature)
                .addParams("code",code)
                .addParams("user_type",userType)
                .addParams("mobile",mobile)
                .addParams("pwd",pwd)
                .postRequest(new DialogCallback<ResponseBean<TokenInfo>>(activity) {
                    @Override
                    public void onSuccess(Response<ResponseBean<TokenInfo>> response) {
                        if (response.body().data != null) {
                            callBack.getTokenInfo(response.body().data);
                        }
                    }
                });
    }

    /**
     * 密码登录
     */
    public void getPassLoginInfo(String signature,String mobile,String pwd){
        new HttpManager<ResponseBean<TokenInfo>>("Home/User/loginbypwd", this)
                .addParams("signature",signature)
                .addParams("mobile",mobile)
                .addParams("pwd",pwd)
                .postRequest(new DialogCallback<ResponseBean<TokenInfo>>(activity) {
                    @Override
                    public void onSuccess(Response<ResponseBean<TokenInfo>> response) {
                        if (response.body().code == 1) {
                            TUtils.showCustom(activity, response.body().msg);
                            return;
                        }

                        if (response.body().data != null) {
                            callBack.getPassLoginInfo(response.body().data);
                        }
                    }
                });
    }
    /**
     * 验证码登录
     */
    public void getCodeLoginInfo(String signature,String mobile,String code){
        new HttpManager<ResponseBean<TokenInfo>>("Home/User/loginbycode", this)
                .addParams("signature",signature)
                .addParams("mobile",mobile)
                .addParams("code",code)
                .postRequest(new DialogCallback<ResponseBean<TokenInfo>>(activity) {
                    @Override
                    public void onSuccess(Response<ResponseBean<TokenInfo>> response) {
                        if (response.body().data != null) {
                            callBack.getCodeLoginInfo(response.body().data);
                        }
                    }

                    @Override
                    public void onError(Response<ResponseBean<TokenInfo>> response) {
                        super.onError(response);
                        Log.d("LoginPresenter", response.getException().getMessage()+"-----");
                    }
                });

    }
    /**
     * 获取验证码
     */
    public void getCodeInfo(String signature,String mobile){
        new HttpManager<ResponseBean<SuccessBean>>("Home/User/sendsms", this)
                .addParams("signature",signature)
                .addParams("mobile",mobile)
                .postRequest(new DialogCallback<ResponseBean<SuccessBean>>(activity) {
                    @Override
                    public void onSuccess(Response<ResponseBean<SuccessBean>> response) {
                        if (response.body().data != null) {
                            callBack.getCodeInfo();
                        }
                    }
                });
    }
    /**
     * 重置密码
     */
    public void ForgetPassrInfo(String signature,String code,String mobile,String pwd){
        new HttpManager<ResponseBean<TokenInfo>>("Home/User/resetpwd", this)
                .addParams("signature",signature)
                .addParams("code",code)
                .addParams("mobile",mobile)
                .addParams("pwd",pwd)
                .postRequest(new DialogCallback<ResponseBean<TokenInfo>>(activity) {
                    @Override
                    public void onSuccess(Response<ResponseBean<TokenInfo>> response) {
                        if (response.body().data != null) {
                            callBack.getForgetPassInfo(response.body().data);
                        }
                    }
                });
    }
    public interface TokenCallBack {
        void getTokenInfo(TokenInfo userInfo);
        void getCodeLoginInfo(TokenInfo userInfo);
        void getPassLoginInfo(TokenInfo userInfo);
        void getForgetPassInfo(TokenInfo userInfo);
        void getCodeInfo();
    }
}
