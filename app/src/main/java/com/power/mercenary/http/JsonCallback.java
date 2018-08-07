package com.power.mercenary.http;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.request.base.Request;
import com.power.mercenary.MyApplication;
import com.power.mercenary.activity.RegisterActivity;
import com.power.mercenary.activity.SignInActivity;
import com.power.mercenary.data.EventConstants;
import com.power.mercenary.event.EventUtils;
import com.power.mercenary.utils.TUtils;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import okhttp3.Response;

public abstract class JsonCallback<T> extends AbsCallback<T> {

    private Type type;
    private Class<T> clazz;

    public JsonCallback() {
    }

    public JsonCallback(Type type) {
        this.type = type;
    }

    public JsonCallback(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public void onStart(Request<T, ? extends Request> request) {
        super.onStart(request);
        // 主要用于在所有请求之前添加公共的请求头或请求参数
        // 例如登录授权的 token
        // 使用的设备信息
        // 可以随意添加,也可以什么都不传
        // 还可以在这里对所有的参数进行加密，均在这里实现
//        request.headers("header1", "HeaderValue1")
//                .params("params1", "ParamsValue1")
//                .params("token", "3215sdf13ad1f65asd4f3ads1f");
    }

    /**
     * 该方法是子线程处理，不能做ui相关的工作
     * 主要作用是解析网络返回的 response 对象,生产onSuccess回调中需要的数据对象
     * 这里的解析工作不同的业务逻辑基本都不一样,所以需要自己实现,以下给出的时模板代码,实际使用根据需要修改
     */
    @Override
    public T convertResponse(Response response) throws Throwable {

        if (type == null) {
            if (clazz == null) {
                Type genType = getClass().getGenericSuperclass();
                type = ((ParameterizedType) genType).getActualTypeArguments()[0];
            } else {
                JsonConvert<T> convert = new JsonConvert<>(clazz);
                return convert.convertResponse(response);
            }
        }

        JsonConvert<T> convert = new JsonConvert<>(type);
        return convert.convertResponse(response);
    }

    @Override
    public void onError(com.lzy.okgo.model.Response<T> response) {
        super.onError(response);
        int code = response.code();
        if (code == 404) {
            Log.d("======>>", "404 当前链接不存在");
        }

        if (response.getException() instanceof SocketTimeoutException) {
            Log.d("======>>", "请求超时");
        } else if (response.getException() instanceof SocketException) {
            Log.d("======>>", "服务器异常");
        } else if (response.getException() instanceof HttpException) {

            switch (((HttpException) response.getException()).getErrorBean().code) {
                case 10010:
                    Intent intent = new Intent(MyApplication.getGloableContext(), SignInActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    MyApplication.getGloableContext().startActivity(intent);
                    MyApplication.loginOut();
                    EventBus.getDefault().post(new EventUtils(EventConstants.JUPMP_TO_MAIN));
                    break;
                case 1:
                case 2:
                case 3:
                case 4:
                    if (!TextUtils.equals(((HttpException) response.getException()).getErrorBean().msg, "error")) {
                        TUtils.showCustom(MyApplication.getGloableContext(), ((HttpException) response.getException()).getErrorBean().msg);
                    }
                    break;
            }

        }

    }

}
