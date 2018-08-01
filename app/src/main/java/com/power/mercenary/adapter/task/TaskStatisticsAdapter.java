package com.power.mercenary.adapter.task;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.power.mercenary.R;
import com.power.mercenary.activity.details_success_accept.GRAcceptSuccessActivity;
import com.power.mercenary.activity.details_success_accept.GZAcceptSuccessActivity;
import com.power.mercenary.activity.details_success_accept.PTAcceptSuccessActivity;
import com.power.mercenary.activity.details_success_accept.SHAcceptSuccessActivity;
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof StatisticsViewHolder) {
            StatisticsViewHolder viewHolder = (StatisticsViewHolder) holder;

            viewHolder.title.setText(data.get(position).getTask_name());

            viewHolder.price.setText(data.get(position).getPay_amount());

            viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (data.get(position).getTask_type()) {
                        case "1":
                            Intent ptIntent = new Intent(context, PTAcceptSuccessActivity.class);
                            ptIntent.putExtra("taskId", data.get(position).getId());
                            context.startActivity(ptIntent);
                            break;

                        case "2":
                        case "5":
                        case "6":
                            Intent shIntent = new Intent(context, SHAcceptSuccessActivity.class);
                            shIntent.putExtra("taskId", data.get(position).getId());
                            context.startActivity(shIntent);
                            break;

                        case "3":
                            Intent grIntent = new Intent(context, GRAcceptSuccessActivity.class);
                            grIntent.putExtra("taskId", data.get(position).getId());
                            context.startActivity(grIntent);
                            break;

                        case "4":
                            Intent gzIntent = new Intent(context, GZAcceptSuccessActivity.class);
                            gzIntent.putExtra("taskId", data.get(position).getId());
                            context.startActivity(gzIntent);
                            break;
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class StatisticsViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        TextView price;

        View mView;

        public StatisticsViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            title = itemView.findViewById(R.id.item_statistics_title);
            price = itemView.findViewById(R.id.item_statistics_price);
        }
    }
}
