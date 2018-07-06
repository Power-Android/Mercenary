package com.power.mercenary.presenter;

import android.app.Activity;

import com.lzy.okgo.model.Response;
import com.power.mercenary.MyApplication;
import com.power.mercenary.bean.user.TokenInfo;
import com.power.mercenary.http.DialogCallback;
import com.power.mercenary.http.HttpManager;
import com.power.mercenary.http.ResponseBean;
import com.power.mercenary.utils.MyUtils;
import com.power.mercenary.utils.Urls;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * admin  2018/7/5 wan
 */
public class AccountPresenter {
    private Activity activity;

    private AccountCallBack callBack;

    public AccountPresenter(Activity activity, AccountCallBack callBack) {
        this.activity = activity;
        this.callBack = callBack;
    }

    /**
     * 修改密码
     *
     * @param mobile
     * @param pwd
     * @param code
     */
    public void changePassword(String mobile, String pwd, String code) {
        String signature = "code=" + code + "mobile=" + mobile + "pwd=" + pwd + Urls.SECRET;

        new HttpManager<ResponseBean<TokenInfo>>("Home/User/resetpwd", this)
                .addParams("mobile", mobile)
                .addParams("pwd", pwd)
                .addParams("code", code)
                .addParams("signature", MyUtils.getMD5(signature))
                .postRequest(new DialogCallback<ResponseBean<TokenInfo>>(activity) {
                    @Override
                    public void onSuccess(Response<ResponseBean<TokenInfo>> response) {
                        if (response.body().data != null) {
                            callBack.changePassword(response.body().data);
                        }
                    }
                });
    }

    /**
     * 修改手机号
     *
     * @param mobile
     * @param code
     */
    public void changePhone(String mobile, String code) {
        new HttpManager<ResponseBean<TokenInfo>>("Home/UserCenter/setmobile", this)
                .addParams("token", MyApplication.getUserToken())
                .addParams("code", code)
                .addParams("mobile", mobile)
                .postRequest(new DialogCallback<ResponseBean<TokenInfo>>(activity) {
                    @Override
                    public void onSuccess(Response<ResponseBean<TokenInfo>> response) {
                        if (response.body().data != null) {
                            callBack.changePhone(response.body().data);
                        }
                    }
                });
    }

    public interface AccountCallBack {
        void changePassword(TokenInfo tokenInfo);

        void changePhone(TokenInfo tokenInfo);
    }
}
