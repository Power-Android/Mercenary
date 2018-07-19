package com.power.mercenary.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.power.mercenary.R;
import com.power.mercenary.activity.TaskListActivity;
import com.power.mercenary.bean.mytask.PublishTaskBean;
import com.power.mercenary.utils.MercenaryUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/30.
 */

public class ReleaseRWZAdapter extends RecyclerView.Adapter {
    private Context context;

    private List<PublishTaskBean> data;

    public ReleaseRWZAdapter(Context context, List<PublishTaskBean> data) {
        this.context = context;
        this.data = data;
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

            viewHolder.title.setText(data.get(position).getTask_name());

            viewHolder.price.setText(data.get(position).getPay_amount());

            viewHolder.num.setText("浏览数：" + data.get(position).getView_num() + " 分享数：" + data.get(position).getShare_num());

            viewHolder.xiugai.setVisibility(View.GONE);
            viewHolder.chexiao.setVisibility(View.GONE);
            viewHolder.yaoqing.setVisibility(View.GONE);

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

        TextView num;

        TextView xiugai;

        TextView chexiao;

        TextView yaoqing;

        public WJDViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_wjd_view_title);
            price = itemView.findViewById(R.id.item_wjd_view_price);
            recyclerView = itemView.findViewById(R.id.item_wjd_view_recyclerView);
            num = itemView.findViewById(R.id.item_wjd_view_num);
            xiugai = itemView.findViewById(R.id.item_wjd_view_xiugai);
            chexiao = itemView.findViewById(R.id.item_wjd_view_chexiao);
            yaoqing = itemView.findViewById(R.id.item_wjd_view_yaoqing);
        }
    }
}