package com.power.mercenary.presenter;

import android.app.Activity;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.power.mercenary.MyApplication;
import com.power.mercenary.bean.PicNumsBean;
import com.power.mercenary.http.JsonCallback;
import com.power.mercenary.http.ResponseBean;
import com.power.mercenary.utils.Urls;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2018/7/17.
 */

public class PicNumsPresenter {
    private Activity activity;

    private PubTaskCallBack callBack;

    public PicNumsPresenter(Activity activity, PubTaskCallBack callBack) {
        this.activity = activity;
        this.callBack = callBack;
    }

    /**
     * 多张图片上传
     *
     * @param file  	用户表单提交图片
     */
    public void publishTask(List<File> file) {
        HttpParams params = new HttpParams();
        params.put("token", MyApplication.getUserToken());

        for (int i = 0; i < file.size(); i++) {
            params.put("image" + i, file.get(i));
        }

        OkGo.<ResponseBean<PicNumsBean>>post(Urls.BASEURL + "Home/Task/imgupload")
                .tag(this)
                .isMultipart(true) //使用 multipart/form-data 表单上传
                .params(params)
                .execute(new JsonCallback<ResponseBean<PicNumsBean>>() {
                    @Override
                    public void onSuccess(Response<ResponseBean<PicNumsBean>> response) {
                        callBack.PicNums(response);
                    }
                });
    }
    public interface PubTaskCallBack {
        void PicNums(Response<ResponseBean<PicNumsBean>> response);
    }
}
