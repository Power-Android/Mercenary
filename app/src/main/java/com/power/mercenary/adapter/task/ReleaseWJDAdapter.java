package com.power.mercenary.adapter.task;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.model.Response;
import com.power.mercenary.MainActivity;
import com.power.mercenary.MyApplication;
import com.power.mercenary.R;
import com.power.mercenary.activity.TaskListActivity;
import com.power.mercenary.bean.SuccessBean;
import com.power.mercenary.bean.mytask.PublishTaskBean;
import com.power.mercenary.http.DialogCallback;
import com.power.mercenary.http.HttpManager;
import com.power.mercenary.http.ResponseBean;
import com.power.mercenary.utils.MercenaryUtils;
import com.power.mercenary.view.BaseDialog;

import java.util.List;

/**
 * admin  2018/7/18 wan
 */
public class ReleaseWJDAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    private Context context;

    private List<PublishTaskBean.DataBean> data1;

    private TaskHandleListener listener;
    private AlertDialog alertDialog;

    public void setListener(TaskHandleListener listener){
        this.listener = listener;
    }

    public ReleaseWJDAdapter(Context context, List<PublishTaskBean.DataBean> data) {
        this.context = context;
        this.data1 = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.wjd_item_view, null);
        return new WJDViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof WJDViewHolder) {
            WJDViewHolder viewHolder = (WJDViewHolder) holder;




            viewHolder.title.setText(
                    data1.get(position).getTask_name());

            viewHolder.price.setText("￥" + data1.get(position).getPay_amount());

            viewHolder.num.setText("浏览数：" + data1.get(position).getView_num() + " 分享数：" + data1.get(position).getShare_num());

            viewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            TaskListActivity.TagAdapter tagAdapter = new TaskListActivity.TagAdapter(R.layout.item_tag_layout, MercenaryUtils.stringToList(this.data1.get(position).getTask_tag()));
            viewHolder.recyclerView.setAdapter(tagAdapter);
            viewHolder.xiugaiPrice.setVisibility(View.VISIBLE);
            viewHolder.xiugaiPrice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showIssueDialog(position);
                }
            });
            viewHolder.chexiao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertExitDialog(position);
                }
            });

            viewHolder.yaoqing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.yaoqing(ReleaseWJDAdapter.this.data1.get(position).getId());
                }
            });

            viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.TaskOnClickViewListener(ReleaseWJDAdapter.this.data1.get(position).getId(), position, ReleaseWJDAdapter.this.data1.get(position).getTask_type(), ReleaseWJDAdapter.this.data1.get(position).getTask_status());
                }
            });
        }
    }

    private void alertExitDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = View
                .inflate(context, R.layout.dialog_shenhe, null);
        builder.setView(view);
        builder.setCancelable(true);
        TextView hint_tv = view.findViewById(R.id.hint_tv);
        TextView tv_sure = view.findViewById(R.id.tv_sure);
        TextView tv_cancle = view.findViewById(R.id.tv_cancle);
        hint_tv.setText("您确定要撤销此任务吗？");
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.chexiao(ReleaseWJDAdapter.this.data1.get(position).getId(), position);
                alertDialog.dismiss();
            }
        });
        tv_cancle.setOnClickListener(this);
        alertDialog = builder.create();
        alertDialog.show();
    }

    private BaseDialog mDialog;
    private BaseDialog.Builder mBuilder;
    private void showIssueDialog(final int position) {
        mBuilder = new BaseDialog.Builder(context);
        mDialog = mBuilder.setViewId(R.layout.dialog_xiugai_price)
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
        final TextView edt_price = mDialog.getView(R.id.edt_price);
        edt_price.setText(data1.get(position).getPay_amount()+"");
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double v = Double.parseDouble(edt_price.getText().toString()) * 100;
                new HttpManager<ResponseBean<SuccessBean>>("Home/TaskFaBu/editprice", this)
                        .addParams("token", MyApplication.getUserToken())
                        .addParams("id",data1.get(position).getId())
                        .addParams("pay_amount", v+"")
                        .postRequest(new DialogCallback<ResponseBean<SuccessBean>>((Activity) context) {
                            @Override
                            public void onSuccess(Response<ResponseBean<SuccessBean>> response) {
                                Toast.makeText(context, response.body().msg, Toast.LENGTH_SHORT).show();
                                listener.xiugai();
                                mDialog.dismiss();
                            }

                            @Override
                            public void onError(Response<ResponseBean<SuccessBean>> response) {
                                super.onError(response);
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
        return data1.size();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tv_cancle:
                alertDialog.dismiss();

                break;
        }
    }

    class WJDViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        TextView price;

        RecyclerView recyclerView;

        TextView num;

        TextView chexiao;

        TextView yaoqing;

        LinearLayout mView;

        TextView xiugaiPrice;

        public WJDViewHolder(View itemView) {
            super(itemView);
            mView = itemView.findViewById(R.id.item_wjd_view_layout);
            title = itemView.findViewById(R.id.item_wjd_view_title);
            price = itemView.findViewById(R.id.item_wjd_view_price);
            recyclerView = itemView.findViewById(R.id.item_wjd_view_recyclerView);
            num = itemView.findViewById(R.id.item_wjd_view_num);
            chexiao = itemView.findViewById(R.id.item_wjd_view_chexiao);
            yaoqing = itemView.findViewById(R.id.item_wjd_view_yaoqing);
            xiugaiPrice = itemView.findViewById(R.id.item_wjd_view_xiugai);
        }
    }

    public interface TaskHandleListener{
        void xiugai();
        void chexiao(String id, int itemPosition);
        void yaoqing(String id);
        void TaskOnClickViewListener(String id, int position, String taskType, String taskState);
    }
}
