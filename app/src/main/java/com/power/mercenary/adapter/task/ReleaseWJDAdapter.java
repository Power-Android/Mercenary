package com.power.mercenary.adapter.task;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.power.mercenary.R;
import com.power.mercenary.activity.TaskListActivity;
import com.power.mercenary.bean.mytask.PublishTaskBean;
import com.power.mercenary.utils.MercenaryUtils;

import org.w3c.dom.Text;

import java.util.List;

/**
 * admin  2018/7/18 wan
 */
public class ReleaseWJDAdapter extends RecyclerView.Adapter {
    private Context context;

    private List<PublishTaskBean> data;

    private TaskHandleListener listener;

    public void setListener(TaskHandleListener listener){
        this.listener = listener;
    }

    public ReleaseWJDAdapter(Context context, List<PublishTaskBean> data) {
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

            viewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            TaskListActivity.TagAdapter tagAdapter = new TaskListActivity.TagAdapter(R.layout.item_tag_layout, MercenaryUtils.stringToList(data.get(position).getTask_tag()));
            viewHolder.recyclerView.setAdapter(tagAdapter);

            viewHolder.chexiao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.chexiao(data.get(position).getId(), position);
                }
            });

            viewHolder.xiugai.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.xiugai(data.get(position).getId());
                }
            });

            viewHolder.yaoqing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.yaoqing(data.get(position).getId());
                }
            });

            viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.TaskOnClickViewListener(data.get(position).getId(), position, data.get(position).getTask_type(), data.get(position).getTask_status());
                }
            });
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

        LinearLayout mView;

        public WJDViewHolder(View itemView) {
            super(itemView);
            mView = itemView.findViewById(R.id.item_wjd_view_layout);
            title = itemView.findViewById(R.id.item_wjd_view_title);
            price = itemView.findViewById(R.id.item_wjd_view_price);
            recyclerView = itemView.findViewById(R.id.item_wjd_view_recyclerView);
            num = itemView.findViewById(R.id.item_wjd_view_num);
            xiugai = itemView.findViewById(R.id.item_wjd_view_xiugai);
            chexiao = itemView.findViewById(R.id.item_wjd_view_chexiao);
            yaoqing = itemView.findViewById(R.id.item_wjd_view_yaoqing);
        }
    }

    public interface TaskHandleListener{
        void xiugai(String id);
        void chexiao(String id, int itemPosition);
        void yaoqing(String id);
        void TaskOnClickViewListener(String id, int position, String taskType, String taskState);
    }
}
