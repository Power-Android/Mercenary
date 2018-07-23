package com.power.mercenary.adapter.task;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.power.mercenary.R;
import com.power.mercenary.bean.mytask.StatisticsListBean;

import java.util.List;

/**
 * admin  2018/7/19 wan
 */
public class TaskStatisticsAdapter extends RecyclerView.Adapter {
    private Context context;

    private List<StatisticsListBean> data;

    public TaskStatisticsAdapter(Context context, List<StatisticsListBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.renwu_item_view, null);
        return new StatisticsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof StatisticsViewHolder) {
            StatisticsViewHolder viewHolder = (StatisticsViewHolder) holder;

            viewHolder.title.setText(data.get(position).getTask_name());

            viewHolder.price.setText(data.get(position).getPay_amount());
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class StatisticsViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        TextView price;

        public StatisticsViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_statistics_title);
            price = itemView.findViewById(R.id.item_statistics_price);
        }
    }
}
