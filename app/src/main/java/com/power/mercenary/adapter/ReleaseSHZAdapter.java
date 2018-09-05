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

public class ReleaseSHZAdapter extends RecyclerView.Adapter {
    private Context context;

    private List<PublishTaskBean> data;

    private TaskBtnListener taskBtnListener;

    public void setTaskBtnListener(TaskBtnListener taskBtnListener){
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

            viewHolder.title.setText(data.get(position).getTask_name());

            viewHolder.price.setText("￥" +data.get(position).getPay_amount());

            viewHolder.content.setText(data.get(position).getTask_description());

            viewHolder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    taskBtnListener.TaskOnClickListener(data.get(position).getId(), position);
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

        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class WJDViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        TextView price;

        RecyclerView recyclerView;

        TextView content;

        TextView btn;

        LinearLayout mView;

        public WJDViewHolder(View itemView) {
            super(itemView);
            mView = itemView.findViewById(R.id.item_shz_view_layout);
            title = itemView.findViewById(R.id.item_wjd_view_title);
            price = itemView.findViewById(R.id.item_wjd_view_price);
            recyclerView = itemView.findViewById(R.id.item_wjd_view_recyclerView);
            content = itemView.findViewById(R.id.item_wjd_view_content);
            btn = itemView.findViewById(R.id.item_wjd_view_btn);
        }
    }

    public interface TaskBtnListener {
        void TaskOnClickListener(String id, int position);
        void TaskOnClickViewListener(String id, int position, String taskType, String taskState);
    }

}
