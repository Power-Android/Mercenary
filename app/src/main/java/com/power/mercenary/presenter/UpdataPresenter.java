package com.power.mercenary.presenter;

import android.app.Activity;
import android.util.Log;

import com.lzy.okgo.model.Response;
import com.power.mercenary.MyApplication;
import com.power.mercenary.bean.user.UserImgInfo;
import com.power.mercenary.http.DialogCallback;
import com.power.mercenary.http.HttpManager;
import com.power.mercenary.http.ResponseBean;

import java.io.File;

/**
 * admin  2018/7/5 wan
 */
public class UpdataPresenter {
    private Activity activity;

    private UpdataCallBack callBack;

    public UpdataPresenter(Activity activity, UpdataCallBack callBack) {
        this.activity = activity;
        this.callBack = callBack;
    }

    /**
     * 上传图片
     *
     * @param image
     */
    public void updataUserImg(File image) {

        Log.e("sss", MyApplication.getUserToken() + "");
        new HttpManager<ResponseBean<UserImgInfo>>("Home/UserCenter/imgupload", this)
                .addParams("token", MyApplication.getUserToken())
                .addParams("image", image)
                .postFileRequest(new DialogCallback<ResponseBean<UserImgInfo>>(activity) {
                    @Override
                    public void onSuccess(Response<ResponseBean<UserImgInfo>> response) {
                        callBack.updataUserImg(response.body().data);
                    }
                });
    }

    /**
     * 更新用户信息
     *
     * @param nick_name
     * @param name
     * @param age
     * @param sex       男：0 女：1
     * @param mail
     */
    public void updataUserInfo(String nick_name, String name, String age, int sex, String mail) {
        new HttpManager<ResponseBean<Void>>("Home/UserCenter/setinfo", this)
                .addParams("token", MyApplication.getUserToken())
                .addParams("nick_name", nick_name)
                .addParams("name", name)
                .addParams("age", age)
                .addParams("sex", sex)
                .addParams("mail", mail)
                .postRequest(new DialogCallback<ResponseBean<Void>>(activity) {
                    @Override
                    public void onSuccess(Response<ResponseBean<Void>> response) {
                        callBack.updataSuccess();
                    }
                });
    }

    public interface UpdataCallBack {
        void updataUserImg(UserImgInfo imgInfo);

        void updataSuccess();
    }
}
