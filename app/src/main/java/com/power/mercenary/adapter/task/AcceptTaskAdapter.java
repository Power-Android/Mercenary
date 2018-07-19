package com.power.mercenary.adapter.task;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.power.mercenary.R;
import com.power.mercenary.activity.TaskListActivity;
import com.power.mercenary.bean.mytask.AcceptTaskBean;
import com.power.mercenary.utils.MercenaryUtils;

import org.w3c.dom.Text;

import java.util.List;

/**
 * admin  2018/7/18 wan
 */
public class AcceptTaskAdapter extends RecyclerView.Adapter {

    private Context context;

    private List<AcceptTaskBean> data;

    public AcceptTaskAdapter(Context context, List<AcceptTaskBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view_accept_task, null);
        return new AcceptViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AcceptViewHolder) {
            AcceptViewHolder viewHolder = (AcceptViewHolder) holder;

            viewHolder.price.setText("￥" + data.get(position).getPay_amount());

            viewHolder.title.setText(data.get(position).getTask_name());

            viewHolder.content.setText(data.get(position).getTask_description());

            if (data.get(position).getTask_status().equals("7") || data.get(position).getTask_status().equals("6")) {
                viewHolder.imageView.setVisibility(View.VISIBLE);
            } else {
                viewHolder.imageView.setVisibility(View.GONE);
            }

            viewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            TaskListActivity.TagAdapter tagAdapter = new TaskListActivity.TagAdapter(R.layout.item_tag_layout, MercenaryUtils.stringToList(data.get(position).getTask_tag()));
            viewHolder.recyclerView.setAdapter(tagAdapter);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class AcceptViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        TextView price;

        TextView content;

        RecyclerView recyclerView;

        ImageView imageView;

        public AcceptViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_view_accept_title);
            price = itemView.findViewById(R.id.item_view_accept_price);
            content = itemView.findViewById(R.id.item_view_accept_content);
            recyclerView = itemView.findViewById(R.id.item_view_accept_recyclerView);
            imageView = itemView.findViewById(R.id.item_view_accept_success);

        }
    }
}
