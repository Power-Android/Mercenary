package com.power.mercenary.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.power.mercenary.R;
import com.power.mercenary.activity.TaskListActivity;
import com.power.mercenary.bean.mytask.PublishTaskBean;
import com.power.mercenary.utils.MercenaryUtils;

import java.util.List;

/**
 * Created by Administrator on 2018/3/30.
 */

public class ReleaseYXJAdapter extends RecyclerView.Adapter {
    private Context context;

    private List<PublishTaskBean.DataBean> data1;

    private String btnStr;

    private TaskBtnListener taskBtnListener;
    public void setTaskBtnListener(TaskBtnListener taskBtnListener){
        this.taskBtnListener = taskBtnListener;
    }

    public ReleaseYXJAdapter(Context context, List<PublishTaskBean.DataBean> data, String btnStr) {
        this.context = context;
        this.data1 = data;
        this.btnStr = btnStr;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.yxj_item_view, null);
        return new WJDViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof WJDViewHolder) {
            WJDViewHolder viewHolder = (WJDViewHolder) holder;


            viewHolder.title.setText(this.data1.get(position).getTask_name());

            viewHolder.price.setText("￥" + this.data1.get(position).getPay_amount());

            viewHolder.btn.setText(btnStr);

            viewHolder.num.setText("浏览数：" + this.data1.get(position).getView_num() + " 分享数：" + this.data1.get(position).getShare_num());

            viewHolder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if (btnStr.equals("评价")) {
//                        taskBtnListener.TaskOnClickListener(data.get(position).getId(), position);
//                    } else {
                        taskBtnListener.TaskOnClick2Listener(ReleaseYXJAdapter.this.data1.get(position).getId(), position);
//                    }

                }
            });

            viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    taskBtnListener.TaskOnClickListener(ReleaseYXJAdapter.this.data1.get(position).getId(), position, ReleaseYXJAdapter.this.data1.get(position).getTask_type(), ReleaseYXJAdapter.this.data1.get(position).getTask_status());
                }
            });

            viewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            TaskListActivity.TagAdapter tagAdapter = new TaskListActivity.TagAdapter(R.layout.item_tag_layout, MercenaryUtils.stringToList(this.data1.get(position).getTask_tag()));
            viewHolder.recyclerView.setAdapter(tagAdapter);

        }
    }

    @Override
    public int getItemCount() {
        return data1.size();
    }

    class WJDViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        TextView price;

        RecyclerView recyclerView;

        TextView num;

        TextView btn;

        LinearLayout mView;

        public WJDViewHolder(View itemView) {
            super(itemView);
            mView = itemView.findViewById(R.id.item_wjd_view_layout);
            title = itemView.findViewById(R.id.item_wjd_view_title);
            price = itemView.findViewById(R.id.item_wjd_view_price);
            recyclerView = itemView.findViewById(R.id.item_wjd_view_recyclerView);
            num = itemView.findViewById(R.id.item_wjd_view_num);
            btn = itemView.findViewById(R.id.item_wjd_view_btn);
        }
    }

    public interface TaskBtnListener {
        void TaskOnClickListener(String id, int position, String taskType, String taskState);
        void TaskOnClick2Listener(String id, int position);
    }
}