package com.power.mercenary.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.okgo.model.Response;
import com.power.mercenary.MyApplication;
import com.power.mercenary.R;
import com.power.mercenary.activity.TaskListActivity;
import com.power.mercenary.bean.SuccessBean;
import com.power.mercenary.bean.mytask.PublishTaskBean;
import com.power.mercenary.http.DialogCallback;
import com.power.mercenary.http.HttpManager;
import com.power.mercenary.http.ResponseBean;
import com.power.mercenary.utils.MercenaryUtils;
import com.power.mercenary.utils.MyUtils;
import com.power.mercenary.view.BaseDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/30.
 */

public class ReleaseSHZAdapter extends RecyclerView.Adapter {


    private static final String TAG = "ReleaseSHZAdapter";

    private Context context;

    private List<PublishTaskBean> data;

    private TaskBtnListener taskBtnListener;
    private int type;

    public void setTaskBtnListener(TaskBtnListener taskBtnListener) {
        this.taskBtnListener = taskBtnListener;
    }

    public ReleaseSHZAdapter(Context context, List<PublishTaskBean> data) {
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

            if (data.get(position).getIs_yanqi().equals("1")) {//是否延期
                viewHolder.tv_jujue.setVisibility(View.GONE);
                viewHolder.tv_shenhe.setVisibility(View.GONE);
                viewHolder.layout_all_price.setVisibility(View.GONE);
                viewHolder.layout_yanqi.setVisibility(View.VISIBLE);
                viewHolder.tv_yanqi.setText("已延期处理");
//                viewHolder.tv_yanqi.setEnabled(false);
                viewHolder.layout_yanqi.setVisibility(View.VISIBLE);
                viewHolder.tvCause.setText(data.get(position).getYanqi_reason());
                viewHolder.tvDays.setText(data.get(position).getYanqi_days());
                viewHolder.tvYanTime.setText(MyUtils.getDateToStringTime(data.get(position).getYanqi_start()));
            } else {
                viewHolder.layout_all_price.setVisibility(View.VISIBLE);
                viewHolder.tv_jujue.setVisibility(View.VISIBLE);
                viewHolder.tv_shenhe.setVisibility(View.VISIBLE);
            }

            String zfpt_ticheng = data.get(position).getZfpt_ticheng();
            String ticheng = data.get(position).getTicheng();
            String fabu_money = data.get(position).getFabu_money();
            String fafang_money = data.get(position).getFafang_money();

            //String 转换Double
            double newZfpt_ticheng = Double.parseDouble(zfpt_ticheng);
            double newTicheng = Double.parseDouble(ticheng);
            double newFabu_money = Double.parseDouble(fabu_money);
            double newFafang_money = Double.parseDouble(fafang_money);

            int number = 100;

            String id = data.get(position).getId();

            Log.e(TAG, "onBindViewHolder: "+id );

            viewHolder.tv_fbpt_price.setText((newZfpt_ticheng / number) + "元");
            viewHolder.tv_pt_price.setText((newTicheng / number + "元"));
            viewHolder.tv_fb_price.setText((newFabu_money / number + "元"));
            viewHolder.tv_js_price.setText((newFafang_money / number + "元"));
            viewHolder.title.setText(data.get(position).getTask_name());

            viewHolder.price.setText("￥" + data.get(position).getPay_amount());

            viewHolder.content.setText(data.get(position).getTask_description());

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
                    taskBtnListener.TaskOnClickViewListener(data.get(position).getId(), position, data.get(position).getTask_type(), data.get(position).getTask_status());
                }
            });

            viewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            TaskListActivity.TagAdapter tagAdapter = new TaskListActivity.TagAdapter(R.layout.item_tag_layout, MercenaryUtils.stringToList(data.get(position).getTask_tag()));
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
                        .addParams("id", data.get(position).getId())
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
                        .addParams("id", data.get(position).getId())
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

        RelativeLayout rl_wdtg = mDialog.getView(R.id.rl_wdtg);
        final TextView edt_cause = mDialog.getView(R.id.edt_cause);
        final RecyclerView timeRecycler = mDialog.getView(R.id.time_recycler);
        timeRecycler.setNestedScrollingEnabled(false);
        timeRecycler.setLayoutManager(new LinearLayoutManager(context));
        NewbqAdapter newbqAdapter = new NewbqAdapter(R.layout.yanqi_recycler_item, mlist);
        timeRecycler.setAdapter(newbqAdapter);
        newbqAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                tv_show_num.setText(mlist.get(position));
                timeRecycler.setVisibility(View.GONE);
            }
        });
        timeRecycler.setVisibility(View.GONE);
        rl_wdtg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeRecycler.setVisibility(View.VISIBLE);
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
                        .addParams("id", data.get(position).getId())
                        .addParams("yanqi_days", tv_show_num.getText().toString())
                        .addParams("yanqi_reason", edt_cause.getText().toString())
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
            tvDays = itemView.findViewById(R.id.tv_days);
            tvYanTime = itemView.findViewById(R.id.tv_yanqi_time);
            layout_yanqi = itemView.findViewById(R.id.layout_yanqi);
            layout_all_price = itemView.findViewById(R.id.layout_all_price);
        }
    }

    public interface TaskBtnListener {
        void TaskOnClickListener();

        void TaskOnClickViewListener(String id, int position, String taskType, String taskState);
    }

}
