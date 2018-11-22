package com.power.mercenary.adapter;

import android.content.Context;
import android.print.PrinterId;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.power.mercenary.R;
import com.power.mercenary.activity.TaskListActivity;
import com.power.mercenary.bean.mytask.PublishTaskBean;
import com.power.mercenary.utils.MercenaryUtils;

import java.util.List;

/**
 * admin  2018/7/19 wan
 */
public class ReleaseQBAdapter extends RecyclerView.Adapter {
    private Context context;

    private List<PublishTaskBean.DataBean> data;

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public ReleaseQBAdapter(Context context, List<PublishTaskBean.DataBean> datas) {
        this.context = context;
        this.data = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_release_task_view, null);
        return new QBViewHolder(view);


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof QBViewHolder) {
            QBViewHolder viewHolder = (QBViewHolder) holder;

            viewHolder.title.setText(data.get(position).getTask_name());

            viewHolder.price.setText("￥" + data.get(position).getPay_amount());

            String is_yanqi = data.get(position).getIs_yanqi();

            if (is_yanqi.equals("1")){

                viewHolder.content.setText("延期原因:" + data.get(position).getYanqi_reason());

                viewHolder.shzSh.setText("延期处理");

                viewHolder.shzSh.setEnabled(false);

            }else{

                viewHolder.content.setVisibility(View.GONE);

                viewHolder.shzSh.setEnabled(true);

            }

            viewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            TaskListActivity.TagAdapter tagAdapter = new TaskListActivity.TagAdapter(R.layout.item_tag_layout, MercenaryUtils.stringToList(data.get(position).getTask_tag()));
            viewHolder.recyclerView.setAdapter(tagAdapter);

            switch (data.get(position).getTask_status()) {
                case "1"://未决定
                    viewHolder.wjdLayout.setVisibility(View.VISIBLE);
                    viewHolder.wjdNum.setText("浏览数：" + data.get(position).getView_num() + " 分享数：" + data.get(position).getShare_num());

                    viewHolder.shzLayout.setVisibility(View.GONE);
                    viewHolder.content.setVisibility(View.GONE);
                    viewHolder.dpjLayout.setVisibility(View.GONE);
                    viewHolder.yxjLayout.setVisibility(View.GONE);
                    viewHolder.rwzLayout.setVisibility(View.GONE);
                    viewHolder.shzSh.setText("未决定");
                    break;

                case "3"://审核中
                    viewHolder.shzLayout.setVisibility(View.VISIBLE);
                    viewHolder.content.setVisibility(View.VISIBLE);

                    viewHolder.wjdLayout.setVisibility(View.GONE);
                    viewHolder.dpjLayout.setVisibility(View.GONE);
                    viewHolder.yxjLayout.setVisibility(View.GONE);
                    viewHolder.rwzLayout.setVisibility(View.GONE);

                    if (data.get(position).getIs_yanqi().equals("1")){

                        viewHolder.shzSh.setText("延期处理");

                    }else{

                        viewHolder.shzSh.setText("审核中");

                    }

                    break;

                case "6"://待评价
                    viewHolder.dpjLayout.setVisibility(View.VISIBLE);
                    viewHolder.dpjNum.setText("浏览数：" + data.get(position).getView_num() + " 分享数：" + data.get(position).getShare_num());

                    viewHolder.wjdLayout.setVisibility(View.GONE);
                    viewHolder.shzLayout.setVisibility(View.GONE);
                    viewHolder.content.setVisibility(View.GONE);
                    viewHolder.yxjLayout.setVisibility(View.GONE);
                    viewHolder.rwzLayout.setVisibility(View.GONE);

                    viewHolder.shzSh.setText("待评价");
                    break;

                case "4"://已下架
                    viewHolder.yxjLayout.setVisibility(View.VISIBLE);
                    viewHolder.yxjNum.setText("浏览数：" + data.get(position).getView_num() + " 分享数：" + data.get(position).getShare_num());

                    viewHolder.wjdLayout.setVisibility(View.GONE);
                    viewHolder.shzLayout.setVisibility(View.GONE);
                    viewHolder.content.setVisibility(View.GONE);
                    viewHolder.dpjLayout.setVisibility(View.GONE);
                    viewHolder.rwzLayout.setVisibility(View.GONE);

                    viewHolder.shzSh.setText("已下架");
                    break;

                default://任务中
                    viewHolder.rwzLayout.setVisibility(View.VISIBLE);
                    viewHolder.rwzNum.setText("浏览数：" + data.get(position).getView_num() + " 分享数：" + data.get(position).getShare_num());

                    viewHolder.wjdLayout.setVisibility(View.GONE);
                    viewHolder.shzLayout.setVisibility(View.GONE);
                    viewHolder.content.setVisibility(View.GONE);
                    viewHolder.dpjLayout.setVisibility(View.GONE);
                    viewHolder.yxjLayout.setVisibility(View.GONE);

                    viewHolder.shzSh.setText("任务中");
                    break;
            }

            viewHolder.wjdCx.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onClickCXListener(data.get(position).getId(), position);
                }
            });

            viewHolder.wjdYq.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onClickYQListener(data.get(position).getId(), position);
                }
            });

            viewHolder.shzSh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onClickSHListener(data.get(position).getId(), position);
                }
            });

            viewHolder.yxjSj.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onClickSJListener(data.get(position).getId(), position);
                }
            });

            viewHolder.dpjPj.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onClickPJListener(data.get(position).getId(), position);
                }
            });

            viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.TaskOnClickViewListener(data.get(position).getId(), position, data.get(position).getTask_type(), data.get(position).getTask_status());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class QBViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        TextView price;

        TextView content;

        RecyclerView recyclerView;

        RelativeLayout wjdLayout;
        TextView wjdNum;
        TextView wjdCx;
        TextView wjdYq;

        RelativeLayout shzLayout;
        TextView shzSh;

        RelativeLayout yxjLayout;
        TextView yxjNum;
        TextView yxjSj;

        RelativeLayout dpjLayout;
        TextView dpjNum;
        TextView dpjPj;

        RelativeLayout rwzLayout;
        TextView rwzNum;

        LinearLayout mView;

        public QBViewHolder(View itemView) {
            super(itemView);
            mView = itemView.findViewById(R.id.item_qb_view_layout);
            title = itemView.findViewById(R.id.item_wjd_view_title);
            price = itemView.findViewById(R.id.item_wjd_view_price);
            recyclerView = itemView.findViewById(R.id.item_wjd_view_recyclerView);
            wjdLayout = itemView.findViewById(R.id.item_release_task_wjd_layout);
            wjdNum = itemView.findViewById(R.id.item_release_task_wjd_num);
            wjdCx = itemView.findViewById(R.id.item_release_task_wjd_cx);
            wjdYq = itemView.findViewById(R.id.item_release_task_wjd_yq);
            shzLayout = itemView.findViewById(R.id.item_release_task_shz_layout);
            shzSh = itemView.findViewById(R.id.item_release_task_shz_sh);
            yxjLayout = itemView.findViewById(R.id.item_release_task_yxj_layout);
            yxjNum = itemView.findViewById(R.id.item_release_task_yxj_num);
            yxjSj = itemView.findViewById(R.id.item_release_task_yxj_sj);
            dpjLayout = itemView.findViewById(R.id.item_release_task_dpj_layout);
            dpjNum = itemView.findViewById(R.id.item_release_task_dpj_num);
            dpjPj = itemView.findViewById(R.id.item_release_task_dpj_pj);
            rwzLayout = itemView.findViewById(R.id.item_release_task_rwz_layout);
            rwzNum = itemView.findViewById(R.id.item_release_task_rwz_num);
            content = itemView.findViewById(R.id.item_wjd_view_content);
        }
    }

    public interface OnClickListener {
        void onClickXGListener(String id, int position);

        void onClickCXListener(String id, int position);

        void onClickYQListener(String id, int position);

        void onClickPJListener(String id, int position);

        void onClickSJListener(String id, int position);

        void onClickSHListener(String id, int position);

        void TaskOnClickViewListener(String id, int position, String taskType, String taskState);
    }
}
