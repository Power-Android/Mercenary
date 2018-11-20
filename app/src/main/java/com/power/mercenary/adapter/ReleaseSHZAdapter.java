package com.power.mercenary.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.lzy.okgo.model.Response;
import com.power.mercenary.MyApplication;
import com.power.mercenary.R;
import com.power.mercenary.activity.TaskListActivity;
import com.power.mercenary.bean.SuccessBean;
import com.power.mercenary.bean.mytask.PublishTaskBean;
import com.power.mercenary.http.DialogCallback;
import com.power.mercenary.http.HttpManager;
import com.power.mercenary.http.OkhtttpUtils;
import com.power.mercenary.http.ResponseBean;
import com.power.mercenary.utils.MercenaryUtils;
import com.power.mercenary.utils.MyUtils;
import com.power.mercenary.view.BaseDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/30.
 */

public class ReleaseSHZAdapter extends RecyclerView.Adapter {

    private static final String TAG = "ReleaseSHZAdapter";

    private Context context;

    private List<PublishTaskBean.DataBean> data;

    private TaskBtnListener taskBtnListener;
    private int type;
    private PublishTaskBean.DataBean dataBean;


    private PopupWindow window;

    private List<Integer> list;


    public void setTaskBtnListener(TaskBtnListener taskBtnListener) {
        this.taskBtnListener = taskBtnListener;
    }

    public ReleaseSHZAdapter(Context context, List<PublishTaskBean.DataBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.shz_item_view, null);
        return new WJDViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof WJDViewHolder) {
            WJDViewHolder viewHolder = (WJDViewHolder) holder;

            dataBean = data.get(position);

            viewHolder.ll_refund.setVisibility(View.GONE);
            viewHolder.ll_receiverRefund.setVisibility(View.GONE);

            if (this.dataBean.getIs_yanqi().equals("1")) {//是否延期
                viewHolder.tv_jujue.setVisibility(View.GONE);
                viewHolder.tv_shenhe.setVisibility(View.GONE);
                viewHolder.layout_all_price.setVisibility(View.GONE);

                viewHolder.layout_yanqi.setVisibility(View.VISIBLE);

                //改变Button的文字
                viewHolder.tv_yanqi.setText("已延期处理");

                //让延期天数 和延期至显示

                viewHolder.ll_yanqi_time.setVisibility(View.VISIBLE);
                viewHolder.ll_yanqi_to.setVisibility(View.VISIBLE);

//                viewHolder.tv_yanqi.setEnabled(false);
                //设置隐藏
                viewHolder.ll_refund.setVisibility(View.GONE);
                viewHolder.ll_receiverRefund.setVisibility(View.GONE);
                viewHolder.layout_yanqi.setVisibility(View.VISIBLE);
                viewHolder.tvCause.setText(this.dataBean.getYanqi_reason());
                viewHolder.tvDays.setText(this.dataBean.getYanqi_days());
                viewHolder.tvYanTime.setText(MyUtils.getDateToStringTime(this.dataBean.getYanqi_end()));
            } else {
                viewHolder.layout_all_price.setVisibility(View.VISIBLE);
                viewHolder.tv_jujue.setVisibility(View.VISIBLE);
                viewHolder.tv_shenhe.setVisibility(View.VISIBLE);

            }

            //判断接受者是否选择了退款

            String settle_status = dataBean.getSettle_status();

            if (settle_status.equals("2")) {

                viewHolder.ll_refund.setVisibility(View.VISIBLE);
                viewHolder.ll_receiverRefund.setVisibility(View.VISIBLE);

            }

            String zfpt_ticheng = this.dataBean.getZfpt_ticheng();
            String ticheng = this.dataBean.getTicheng();
            String fabu_money = this.dataBean.getFabu_money();
            String fafang_money = this.dataBean.getFafang_money();

            //String 转换Double
            double newZfpt_ticheng = Double.parseDouble(zfpt_ticheng);
            double newTicheng = Double.parseDouble(ticheng);
            double newFabu_money = Double.parseDouble(fabu_money);
            double newFafang_money = Double.parseDouble(fafang_money);

            int number = 100;

            String id = this.dataBean.getId();

            Log.e(TAG, "onBindViewHolder: " + id);

            String task_type = this.dataBean.getTask_type();

            viewHolder.tv_fbpt_price.setText((newZfpt_ticheng / number) + "元");
            viewHolder.tv_pt_price.setText((newTicheng / number + "元"));
            viewHolder.tv_fb_price.setText((newFabu_money / number + "元"));
            viewHolder.tv_js_price.setText((newFafang_money / number + "元"));
            viewHolder.title.setText(this.dataBean.getTask_name());

            viewHolder.price.setText("￥" + this.dataBean.getPay_amount());

            viewHolder.content.setText(this.dataBean.getTask_description());

            viewHolder.tv_shenhe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showShenHeDialog(position);
                }
            });
            viewHolder.tv_yanqi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showYanQiDialog(position);
                }
            });
            viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    taskBtnListener.TaskOnClickViewListener(ReleaseSHZAdapter.this.dataBean.getId(), position, ReleaseSHZAdapter.this.dataBean.getTask_type(), ReleaseSHZAdapter.this.dataBean.getTask_status());
                }
            });

            viewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            TaskListActivity.TagAdapter tagAdapter = new TaskListActivity.TagAdapter(R.layout.item_tag_layout, MercenaryUtils.stringToList(this.dataBean.getTask_tag()));
            viewHolder.recyclerView.setAdapter(tagAdapter);
            viewHolder.tv_jujue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    type = 2;
                    showIssueDialog(position);
                }
            });
        }
    }

    private BaseDialog mDialog;
    private BaseDialog.Builder mBuilder;
    private List<String> mlist = new ArrayList<>();
    private boolean isShow;

    private void showIssueDialog(final int position) {
        mBuilder = new BaseDialog.Builder(context);
        mDialog = mBuilder.setViewId(R.layout.dialog_jujue)
                //设置dialogpadding
                .setPaddingdp(0, 0, 0, 0)
                //设置显示位置
                .setGravity(Gravity.CENTER)
                //设置动画
                .setAnimation(R.style.Bottom_Top_aniamtion)
                //设置dialog的宽高
                .setWidthHeightpx(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                //设置触摸dialog外围是否关闭
                .isOnTouchCanceled(true)
                //设置监听事件
                .builder();
        TextView tv_sure = mDialog.getView(R.id.tv_sure);
        TextView tv_cancle = mDialog.getView(R.id.tv_cancle);
        final TextView edt_cause = mDialog.getView(R.id.edt_cause);
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(edt_cause.getText().toString())) {
                    Toast.makeText(context, "请输入未通过原因", Toast.LENGTH_SHORT).show();
                    return;
                }
                new HttpManager<ResponseBean<SuccessBean>>("Home/MyTask/shenhe", this)
                        .addParams("token", MyApplication.getUserToken())
                        .addParams("id", dataBean.getId())
                        .addParams("type", 2)
                        .addParams("refuse_cause", edt_cause.getText().toString())
                        .postRequest(new DialogCallback<ResponseBean<SuccessBean>>((Activity) context) {
                            @Override
                            public void onSuccess(Response<ResponseBean<SuccessBean>> response) {
                                Toast.makeText(context, response.body().msg, Toast.LENGTH_SHORT).show();
                                taskBtnListener.TaskOnClickListener();
                                mDialog.dismiss();
                            }

                            @Override
                            public void onError(Response<ResponseBean<SuccessBean>> response) {
                                super.onError(response);
                                Log.d("ReleaseRWZAdapter", response.getException().getMessage() + "--------");
                            }
                        });
            }
        });
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });
        mDialog.show();
    }

    private void showShenHeDialog(final int position) {
        mBuilder = new BaseDialog.Builder(context);
        mDialog = mBuilder.setViewId(R.layout.dialog_shenhe)
                //设置dialogpadding
                .setPaddingdp(0, 0, 0, 0)
                //设置显示位置
                .setGravity(Gravity.CENTER)
                //设置动画
                .setAnimation(R.style.Bottom_Top_aniamtion)
                //设置dialog的宽高
                .setWidthHeightpx(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                //设置触摸dialog外围是否关闭
                .isOnTouchCanceled(true)
                //设置监听事件
                .builder();
        TextView tv_sure = mDialog.getView(R.id.tv_sure);
        TextView tv_cancle = mDialog.getView(R.id.tv_cancle);


        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new HttpManager<ResponseBean<SuccessBean>>("Home/MyTask/shenhe", this)
                        .addParams("token", MyApplication.getUserToken())
                        .addParams("id", dataBean.getId())
                        .addParams("type", 1)
                        .addParams("refuse_cause", "")
                        .postRequest(new DialogCallback<ResponseBean<SuccessBean>>((Activity) context) {
                            @Override
                            public void onSuccess(Response<ResponseBean<SuccessBean>> response) {
                                Toast.makeText(context, response.body().msg, Toast.LENGTH_SHORT).show();
                                taskBtnListener.TaskOnClickListener();
                                mDialog.dismiss();
                            }

                            @Override
                            public void onError(Response<ResponseBean<SuccessBean>> response) {
                                super.onError(response);
                                Log.d("ReleaseRWZAdapter", response.getException().getMessage() + "--------");
                            }
                        });
            }
        });
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });
        mDialog.show();
    }

    private void showYanQiDialog(final int position) {
        for (int i = 1; i <= 7; i++) {
            mlist.add(i + "天");
        }
        mBuilder = new BaseDialog.Builder(context);
        mDialog = mBuilder.setViewId(R.layout.dialog_yanqi)
                //设置dialogpadding
                .setPaddingdp(0, 0, 0, 0)
                //设置显示位置
                .setGravity(Gravity.CENTER)
                //设置动画
                .setAnimation(R.style.Bottom_Top_aniamtion)
                //设置dialog的宽高
                .setWidthHeightpx(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                //设置触摸dialog外围是否关闭
                .isOnTouchCanceled(true)
                //设置监听事件
                .builder();
        TextView tv_sure = mDialog.getView(R.id.tv_sure);
        TextView tv_cancle = mDialog.getView(R.id.tv_cancle);
        final TextView tv_show_num = mDialog.getView(R.id.tv_show_num);

        final Spinner yanqiSpinner = mDialog.getView(R.id.yanqi_spinner);

        RelativeLayout rl_wdtg = mDialog.getView(R.id.rl_wdtg);
        final TextView edt_cause = mDialog.getView(R.id.edt_cause);
        //final RecyclerView timeRecycler = mDialog.getView(R.id.time_recycler);
        //timeRecycler.setNestedScrollingEnabled(false);
        //timeRecycler.setLayoutManager(new LinearLayoutManager(context));
        NewbqAdapter newbqAdapter = new NewbqAdapter(R.layout.yanqi_recycler_item, mlist);

        yanqiSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //获得选中的天数
                String selectedItem = (String) yanqiSpinner.getSelectedItem();
                //判断字符串不等于'请选择延期天数'
                if (!selectedItem.equals("请选择延期天数")) {

                    tv_show_num.setText(selectedItem);

                } else {
                    //否侧Toast '请选择天数'
                    //Toast.makeText(context, "请选择天数", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //timeRecycler.setAdapter(newbqAdapter);
        newbqAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                tv_show_num.setText(mlist.get(position));
                //timeRecycler.setVisibility(View.GONE);
            }
        });
        //timeRecycler.setVisibility(View.GONE);
        rl_wdtg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //timeRecycler.setVisibility(View.VISIBLE);
            }
        });

        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(edt_cause.getText().toString())) {
                    Toast.makeText(context, "请输入延期原因", Toast.LENGTH_SHORT).show();
                    return;
                } else if (tv_show_num.getText().toString().equals("请选择延期天数")) {
                    Toast.makeText(context, "请选择延期天数", Toast.LENGTH_SHORT).show();
                    return;
                }
                new HttpManager<ResponseBean<SuccessBean>>("Home/TaskFaBu/yanqi", this)
                        .addParams("token", MyApplication.getUserToken())
                        .addParams("id", dataBean.getId())
                        .addParams("yanqi_days", tv_show_num.getText().toString())
                        .addParams("yanqi_reason", edt_cause.getText().toString())
                        .postRequest(new DialogCallback<ResponseBean<SuccessBean>>((Activity) context) {
                            @Override
                            public void onSuccess(Response<ResponseBean<SuccessBean>> response) {
                                Toast.makeText(context, response.body().msg, Toast.LENGTH_SHORT).show();
                                // Log.e(TAG, "onSuccess: "+response.body().data );

                                taskBtnListener.TaskOnClickListener();
                                mDialog.dismiss();
                            }

                            @Override
                            public void onError(Response<ResponseBean<SuccessBean>> response) {
                                super.onError(response);
                                Log.d("ReleaseRWZAdapter", response.getException().getMessage() + "--------");
                            }
                        });
            }
        });
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });
        mDialog.show();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private class NewbqAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public NewbqAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, final String item) {
            helper.setText(R.id.tv_day_nums, item);

        }
    }

    class WJDViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        TextView price;

        RecyclerView recyclerView;

        TextView content;

        LinearLayout mView;
        TextView tv_jujue;
        TextView tv_shenhe;
        TextView tv_yanqi;
        TextView tv_fbpt_price;
        TextView tv_pt_price;
        TextView tv_fb_price;
        TextView tv_js_price;
        TextView tvCause;
        TextView tvDays;
        TextView tvYanTime;
        LinearLayout layout_yanqi;
        LinearLayout layout_all_price;
        private final LinearLayout ll_refund;
        private final LinearLayout ll_receiverRefund;
        private final LinearLayout ll_yanqi_time;
        private final LinearLayout ll_yanqi_to;

        public WJDViewHolder(View itemView) {
            super(itemView);
            mView = itemView.findViewById(R.id.item_shz_view_layout);
            title = itemView.findViewById(R.id.item_wjd_view_title);
            price = itemView.findViewById(R.id.item_wjd_view_price);
            recyclerView = itemView.findViewById(R.id.item_wjd_view_recyclerView);
            content = itemView.findViewById(R.id.item_wjd_view_content);
            tv_jujue = itemView.findViewById(R.id.tv_jujue);
            tv_shenhe = itemView.findViewById(R.id.tv_shenhe);
            tv_yanqi = itemView.findViewById(R.id.tv_yanqi);
            tv_fbpt_price = itemView.findViewById(R.id.tv_fbpt_price);
            tv_pt_price = itemView.findViewById(R.id.tv_pt_price);
            tv_fb_price = itemView.findViewById(R.id.tv_fb_price);
            tv_js_price = itemView.findViewById(R.id.tv_js_price);
            tvCause = itemView.findViewById(R.id.tv_cause);
            ll_yanqi_time = itemView.findViewById(R.id.ll_yanqi_time);
            ll_yanqi_to = itemView.findViewById(R.id.ll_yanqi_to);
            tvDays = itemView.findViewById(R.id.tv_days);
            ll_refund = itemView.findViewById(R.id.refund);
            ll_receiverRefund = itemView.findViewById(R.id.receiver_refund);
            tvYanTime = itemView.findViewById(R.id.tv_yanqi_time);
            layout_yanqi = itemView.findViewById(R.id.layout_yanqi);
            layout_all_price = itemView.findViewById(R.id.layout_all_price);
        }
    }

    public interface TaskBtnListener {
        void TaskOnClickListener();

        void TaskOnClickViewListener(String id, int position, String taskType, String taskState);
    }

    public void getData() {
        list = new ArrayList<>();

        for (int i = 0; i < 50; i++) {

            list.add(i);

        }

    }

}
