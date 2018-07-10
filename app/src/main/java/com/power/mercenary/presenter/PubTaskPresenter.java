package com.power.mercenary.presenter;

import android.app.Activity;

import com.lzy.okgo.model.Response;
import com.power.mercenary.http.DialogCallback;
import com.power.mercenary.http.HttpManager;
import com.power.mercenary.http.ResponseBean;

import java.util.List;

/**
 * admin  2018/7/9 wan
 */
public class PubTaskPresenter {
    private Activity activity;

    private PubTaskCallBack callBack;

    public PubTaskPresenter(Activity activity, PubTaskCallBack callBack) {
        this.activity = activity;
        this.callBack = callBack;
    }

    /**
     * 发布任务
     *
     * @param task_type        任务分类对应id
     * @param task_type_child  子任务分类对应id
     * @param task_name        任务名称 如果是个人定制类任务表示是定制物品名称
     * @param task_tag         任务标签 数组键用0,1,2…表示
     * @param task_img         任务相关图片 数组键用0,1,2…表示
     * @param pay_amount       佣金金额 金额以分为单位
     * @param validity_time    任务有效期 以天为单位
     * @param task_description 任务详情
     * @param task_purpose     任务目的
     * @param task_request     任务要求
     * @param itemname         物品名称
     * @param numbers          物品数量
     * @param transport        运输要求
     * @param delivery_time    送达时间 时间戳格式 （1530961214）10位
     * @param begin_address    开始地址
     * @param end_address      目的地址
     * @param other_request    其它要求
     */
    public void publishTask(int task_type, String task_type_child, String task_name, List<String> task_tag, List<String> task_img, int pay_amount,
                            int validity_time, String task_description, String task_purpose, String task_request, String itemname,
                            int numbers, String transport, int delivery_time, String begin_address, String end_address, String other_request) {

        new HttpManager<ResponseBean>("Home/Task/addtask", this)
                .addParams("task_type", task_type)
                .addParams("task_type_child", task_type_child)
                .addParams("task_name", task_name)
                .addParams("task_tag", task_tag)
                .addParams("task_img", task_img)
                .addParams("pay_amount", pay_amount)
                .addParams("validity_time", validity_time)
                .addParams("task_description", task_description)
                .addParams("task_purpose", task_purpose)
                .addParams("task_request", task_request)
                .addParams("itemname", itemname)
                .addParams("numbers", numbers)
                .addParams("transport", transport)
                .addParams("delivery_time", delivery_time)
                .addParams("begin_address", begin_address)
                .addParams("end_address", end_address)
                .addParams("other_request", other_request)
                .postRequest(new DialogCallback<ResponseBean>(activity) {
                    @Override
                    public void onSuccess(Response<ResponseBean> response) {
                        callBack.publishTask();
                    }
                });
    }

    public interface PubTaskCallBack {
        void publishTask();
    }
}
