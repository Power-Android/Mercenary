package com.power.mercenary.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
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

public class ReleaseRWZAdapter extends RecyclerView.Adapter {
    private Context context;

    private List<PublishTaskBean.DataBean> data1;

    private TaskBtnListener listener;
    private EditText edt_pub_people;
    private EditText edt_shou_people;
    private TextView tv_all_price;
    private CheckBox cb_all_parice;
    private CheckBox cb_zdy_parice;
    private TextView tv_pingtai_peice;

    public void setListener(TaskBtnListener listener) {
        this.listener = listener;
    }

    public ReleaseRWZAdapter(Context context, List<PublishTaskBean.DataBean> data) {
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
            viewHolder.title.setText(this.data1.get(position).getTask_name());
            viewHolder.price.setText("￥" + this.data1.get(position).getPay_amount());
            viewHolder.num.setText("浏览数：" + this.data1.get(position).getView_num() + " 分享数：" + this.data1.get(position).getShare_num());
            if (!this.data1.get(position).getRefuse_cause().equals("")){
                viewHolder.layout_jujue.setVisibility(View.VISIBLE);
                viewHolder.tv_jujue.setText(this.data1.get(position).getRefuse_cause());

            }else{
                //viewHolder.tuikuan.setVisibility(View.VISIBLE);

            }
            viewHolder.chexiao.setVisibility(View.GONE);
            viewHolder.yaoqing.setVisibility(View.GONE);
            viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.TaskOnClickViewListener(ReleaseRWZAdapter.this.data1.get(position).getId(), position, ReleaseRWZAdapter.this.data1.get(position).getTask_type(), ReleaseRWZAdapter.this.data1.get(position).getTask_status());
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

        TextView chexiao;

        TextView tuikuan;

        TextView yaoqing;

        LinearLayout mView;
        LinearLayout layout_jujue;
        TextView tv_jujue;
        TextView tv_weitongguo;

        public WJDViewHolder(View itemView) {
            super(itemView);
            mView = itemView.findViewById(R.id.item_wjd_view_layout);
            title = itemView.findViewById(R.id.item_wjd_view_title);
            price = itemView.findViewById(R.id.item_wjd_view_price);
            recyclerView = itemView.findViewById(R.id.item_wjd_view_recyclerView);
            num = itemView.findViewById(R.id.item_wjd_view_num);
            chexiao = itemView.findViewById(R.id.item_wjd_view_chexiao);
            tuikuan = itemView.findViewById(R.id.item_wjd_view_tuikuan);
            yaoqing = itemView.findViewById(R.id.item_wjd_view_yaoqing);
            layout_jujue = itemView.findViewById(R.id.layout_jujue);
            tv_jujue = itemView.findViewById(R.id.tv_jujue);
            tv_weitongguo = itemView.findViewById(R.id.tv_weitongguo);
        }
    }

    public interface TaskBtnListener {
        void TaskOnClickViewListener(String id, int position, String taskType, String taskState);
    }
}