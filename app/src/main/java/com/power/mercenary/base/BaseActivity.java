package com.power.mercenary.base;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.model.Response;
import com.power.mercenary.MainActivity;
import com.power.mercenary.MyApplication;
import com.power.mercenary.R;
import com.power.mercenary.activity.GRTaskDetailsActivity;
import com.power.mercenary.activity.GZTaskDetailsActivity;
import com.power.mercenary.activity.PTTaskDetailsActivity;
import com.power.mercenary.activity.SHTaskDetailsActivity;
import com.power.mercenary.activity.details_appraise_publish.GRPublishAppraiseActivity;
import com.power.mercenary.activity.details_appraise_publish.GZPublishAppraiseActivity;
import com.power.mercenary.activity.details_appraise_publish.PTPublishAppraiseActivity;
import com.power.mercenary.activity.details_appraise_publish.SHPublishAppraiseActivity;
import com.power.mercenary.activity.details_audit_publish.GRPublishAuditActivity;
import com.power.mercenary.activity.details_audit_publish.GZPublishAuditActivity;
import com.power.mercenary.activity.details_audit_publish.PTPublishAuditActivity;
import com.power.mercenary.activity.details_audit_publish.SHPublishAuditActivity;
import com.power.mercenary.activity.details_intask_publish.GRPublishInTaskActivity;
import com.power.mercenary.activity.details_intask_publish.GZPublishInTaskActivity;
import com.power.mercenary.activity.details_intask_publish.PTPublishInTaskActivity;
import com.power.mercenary.activity.details_intask_publish.SHPublishInTaskActivity;
import com.power.mercenary.activity.details_out_publish.GRPublishOutActivity;
import com.power.mercenary.activity.details_out_publish.GZPublishOutActivity;
import com.power.mercenary.activity.details_out_publish.PTPublishOutActivity;
import com.power.mercenary.activity.details_out_publish.SHPublishOutActivity;
import com.power.mercenary.bean.task.TaskDetailsBean;
import com.power.mercenary.http.DialogCallback;
import com.power.mercenary.http.HttpManager;
import com.power.mercenary.http.ResponseBean;
import com.power.mercenary.presenter.TaskDetailsPresenter;
import com.power.mercenary.utils.NetWorkUtils;
import com.power.mercenary.utils.ShearUtils;
import com.power.mercenary.view.BaseDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by lxk on 2017/6/10.
 */

public abstract class BaseActivity extends AppCompatActivity  {

    private static List<Activity> activityList = new ArrayList<>();
    protected Context mContext;
    private static ClipboardManager mClipboardManager;
    private String id;
    private BaseDialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSystemBarTint();
        mContext = getApplicationContext();//主要是方便子类调用
        if (activityList != null) {
            activityList.add(this);
        }
        if (!NetWorkUtils.isNetworkConnected(this)) {
            Toast.makeText(this, "当前无网络连接，请检查设置", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
    }

    /**
     * 子类可以重写改变状态栏颜色
     */
    protected int setStatusBarColor() {
        return getColorPrimary();
    }

    /**
     * 子类可以重写决定是否使用透明状态栏
     */
    protected boolean translucentStatusBar() {
        return false;
    }

    /**
     * 设置状态栏颜色
     */
    protected void initSystemBarTint() {
        Window window = getWindow();
        if (translucentStatusBar()) {
            // 设置状态栏全透明
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
            return;
        }
        // 沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0以上使用原生方法
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(setStatusBarColor());
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4-5.0使用三方工具类，有些4.4的手机有问题，这里为演示方便，不使用沉浸式
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        String shearContent = ShearUtils.isShearContent(getApplicationContext());
        if (null != shearContent) {
            String rgex = "【(.*?)】";//正则截取的规则
            List<String> lists = (getSubUtil(shearContent, rgex));
            String userID = "";
            for (String string : lists) {
                userID += string;
            }
            if (userID.length() > 4) {
                id = userID.substring(userID.length() - 3, userID.length());
            }
            new HttpManager<ResponseBean<TaskDetailsBean>>("Home/CommonTask/get_taskdetail", this)
                    .addParams("token", MyApplication.getUserToken())
                    .addParams("id", id)
                    .postRequest(new DialogCallback<ResponseBean<TaskDetailsBean>>(this) {
                        @Override
                        public void onSuccess(Response<ResponseBean<TaskDetailsBean>> response) {
                            if (response.body().data != null)
                                CreatDialog(response.body().data.getItemname(), response.body().data.getTask_name(), response.body().data.getPay_amount(),response.body().data.getTask_type(),response.body().data.getTask_status());
                        }
                    });

        }
    }


    public List<String> getSubUtil(String soap, String rgex) {
        List<String> list = new ArrayList<String>();
        Pattern pattern = Pattern.compile(rgex);// 匹配的模式
        Matcher m = pattern.matcher(soap);
        while (m.find()) {
            int i = 1;
            list.add(m.group(i));
            i++;
        }
        return list;
    }

    public String getSubUtilSimple(String soap, String rgex) {
        Pattern pattern = Pattern.compile(rgex);// 匹配的模式
        Matcher m = pattern.matcher(soap);
        while (m.find()) {
            return m.group(1);
        }
        return "";
    }

    private void CreatDialog(String itemname, String task_name, String pay_amount, final String task_type, final String task_state) {
        BaseDialog.Builder builder = new BaseDialog.Builder(this);
        mDialog = builder.setViewId(R.layout.dialog_share)
                //设置dialogpadding
                .setPaddingdp(0, 0, 0, 0)
                //设置显示位置
                .setGravity(Gravity.CENTER)
                //设置动画
                //设置dialog的宽高
                .setWidthHeightpx(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                //设置触摸dialog外围是否关闭
                .isOnTouchCanceled(true)
                //设置监听事件
                .builder();
        Button lookdetails_But = mDialog.getView(R.id.lookdetails);
        ImageView ig_error = mDialog.getView(R.id.error);
        TextView TaskItemName = mDialog.getView(R.id.getItemname);
        TextView payAmount = mDialog.getView(R.id.Pay_amount);
        TextView TaskName = mDialog.getView(R.id.Task_name);
        TaskItemName.setText("任务名称:" + itemname);
        payAmount.setText("￥" + pay_amount);
        TaskName.setText("任务目的:" + task_name);
        ig_error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShearUtils.fuShear(getApplicationContext(), "");
                mDialog.dismiss();

            }
        });
        lookdetails_But.setOnClickListener(new View.OnClickListener() {
/*
           通过任务的task_state判断当前分享任务处于什么状态，分别判断进行跳转
           1  未决定
          2  任务中
          3  审核中
          4  已下架
           6  待评价
*/
                  @Override
            public void onClick(View view) {
                if (TextUtils.equals(task_state, "2")) {
                    switch (task_type) {
                        case "1":
                            Intent ptIntent = new Intent(getApplicationContext(), PTPublishInTaskActivity.class);
                            ptIntent.putExtra("taskId", id);
                            startActivity(ptIntent);
                            break;

                        case "2":
                        case "5":
                        case "6":
                            Intent shIntent = new Intent(getApplicationContext(), SHPublishInTaskActivity.class);
                            shIntent.putExtra("taskId", id);
                            startActivity(shIntent);
                            break;

                        case "3":
                            Intent grIntent = new Intent(getApplicationContext(), GRPublishInTaskActivity.class);
                            grIntent.putExtra("taskId", id);
                            startActivity(grIntent);
                            break;

                        case "4":
                            Intent gzIntent = new Intent(getApplicationContext(), GZPublishInTaskActivity.class);
                            gzIntent.putExtra("taskId", id);
                            startActivity(gzIntent);
                            break;
                    }
                } else if (TextUtils.equals(task_state, "3")) {
                    switch (task_type) {
                        case "1":
                            Intent ptIntent = new Intent(getApplicationContext(), PTPublishAuditActivity.class);
                            ptIntent.putExtra("taskId", id);
                            startActivity(ptIntent);
                            break;

                        case "2":
                        case "5":
                        case "6":
                            Intent shIntent = new Intent(getApplicationContext(), SHPublishAuditActivity.class);
                            shIntent.putExtra("taskId", id);
                            startActivity(shIntent);
                            break;

                        case "3":
                            Intent grIntent = new Intent(getApplicationContext(), GRPublishAuditActivity.class);
                            grIntent.putExtra("taskId", id);
                            startActivity(grIntent);
                            break;

                        case "4":
                            Intent gzIntent = new Intent(getApplicationContext(), GZPublishAuditActivity.class);
                            gzIntent.putExtra("taskId", id);
                            startActivity(gzIntent);
                            break;
                    }
                } else if (TextUtils.equals(task_state, "6")) {
                    switch (task_type) {
                        case "1":
                            Intent ptIntent = new Intent(getApplicationContext(), PTPublishAppraiseActivity.class);
                            ptIntent.putExtra("taskId", id);
                            startActivity(ptIntent);
                            break;

                        case "2":
                        case "5":
                        case "6":
                            Intent shIntent = new Intent(getApplicationContext(), SHPublishAppraiseActivity.class);
                            shIntent.putExtra("taskId", id);
                            startActivity(shIntent);
                            break;

                        case "3":
                            Intent grIntent = new Intent(getApplicationContext(), GRPublishAppraiseActivity.class);
                            grIntent.putExtra("taskId", id);
                            startActivity(grIntent);
                            break;

                        case "4":
                            Intent gzIntent = new Intent(getApplicationContext(), GZPublishAppraiseActivity.class);
                            gzIntent.putExtra("taskId", id);
                            startActivity(gzIntent);
                            break;
                    }
                } else if (TextUtils.equals(task_state, "4")) {
                    switch (task_type) {
                        case "1":
                            Intent ptIntent = new Intent(getApplicationContext(), PTPublishOutActivity.class);
                            ptIntent.putExtra("taskId", id);
                            startActivity(ptIntent);
                            break;

                        case "2":
                        case "5":
                        case "6":
                            Intent shIntent = new Intent(getApplicationContext(), SHPublishOutActivity.class);
                            shIntent.putExtra("taskId", id);
                            startActivity(shIntent);
                            break;

                        case "3":
                            Intent grIntent = new Intent(getApplicationContext(), GRPublishOutActivity.class);
                            grIntent.putExtra("taskId", id);
                            startActivity(grIntent);
                            break;

                        case "4":
                            Intent gzIntent = new Intent(getApplicationContext(), GZPublishOutActivity.class);
                            gzIntent.putExtra("taskId", id);
                            startActivity(gzIntent);
                            break;
                    }
                } else {
                    switch (task_type) {
                        case "1":
                            Intent ptIntent = new Intent(getApplicationContext(), PTTaskDetailsActivity.class);
                            ptIntent.putExtra("taskId", id);
                            startActivity(ptIntent);
                            break;

                        case "2":
                        case "5":
                        case "6":
                            Intent shIntent = new Intent(getApplicationContext(), SHTaskDetailsActivity.class);
                            shIntent.putExtra("taskId", id);
                            startActivity(shIntent);
                            break;

                        case "3":
                            Intent grIntent = new Intent(getApplicationContext(), GRTaskDetailsActivity.class);
                            grIntent.putExtra("taskId", id);
                            startActivity(grIntent);
                            break;

                        case "4":
                            Intent gzIntent = new Intent(getApplicationContext(), GZTaskDetailsActivity.class);
                            gzIntent.putExtra("taskId", id);
                            startActivity(gzIntent);
                            break;
                    }
                }
                ShearUtils.fuShear(getApplicationContext(), "");

                mDialog.dismiss();
            }
        });
        mDialog.show();
    }

    /**
     * 获取主题色
     */
    public int getColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (activityList != null) {
            activityList.remove(this);
        }
    }

    public static void removeAllActivitys() {
        if (activityList != null && activityList.size() > 0) {
            for (int i = 0; i < activityList.size(); i++) {
                if (activityList.get(i) != null) {
                    activityList.get(i).finish();
                }
            }
            activityList.clear();
        }
    }

    public static void realBack() {
        if (activityList != null && activityList.size() > 0) {
            for (int i = 0; i < activityList.size(); i++) {
                if (activityList.get(i) != null) {
                    activityList.get(i).finish();
                }
            }
            activityList.clear();
            activityList = null;
        }
    }


    public void setShowPop(PopupWindow popupWindow, View view) {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            setWindowTranslucence(10);
            popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        }
    }

    //设置Window窗口的透明度
    public void setWindowTranslucence(double d) {

        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.alpha = (float) d;
        window.setAttributes(attributes);

    }


}
