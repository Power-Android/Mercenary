package com.power.mercenary.adapter.task;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.power.mercenary.R;
import com.power.mercenary.activity.TaskListActivity;
import com.power.mercenary.bean.task.TaskListBean;
import com.power.mercenary.utils.MercenaryUtils;

import java.util.List;

/**
 * admin  2018/7/11 wan
 */
public class TaskListAdapter extends RecyclerView.Adapter {
    private Context context;

    private List<TaskListBean> datas;

    private onItemClickListener listener;

    public void setOnItemClickListener(onItemClickListener listener){
        this.listener = listener;
    }

    public TaskListAdapter(Context context, List<TaskListBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tuijian_renwu, null);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof TaskViewHolder) {
            TaskViewHolder viewHolder = (TaskViewHolder) holder;
            viewHolder.titleTv.setText(datas.get(position).getTask_name());
            viewHolder.contentTv.setText(datas.get(position).getTask_description());
            viewHolder.priceTv.setText("￥" + datas.get(position).getPay_amount());

            viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onTaskClickListener(datas.get(position).getId());
                }
            });

            viewHolder.tagRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            TaskListActivity.TagAdapter tagAdapter = new TaskListActivity.TagAdapter(R.layout.item_tag_layout, MercenaryUtils.stringToList(datas.get(position).getTask_tag()));
            viewHolder.tagRecyclerView.setAdapter(tagAdapter);
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {

        TextView titleTv;
        TextView contentTv;
        TextView priceTv;
        RecyclerView tagRecyclerView;

        View mView;

        public TaskViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

            titleTv = itemView.findViewById(R.id.item_title_tv);
            contentTv = itemView.findViewById(R.id.item_content_tv);
            priceTv = itemView.findViewById(R.id.item_money_tv);
            tagRecyclerView = itemView.findViewById(R.id.tag_recycler);
        }
    }

    public interface onItemClickListener{
        void onTaskClickListener(String itemId);
    }
}
