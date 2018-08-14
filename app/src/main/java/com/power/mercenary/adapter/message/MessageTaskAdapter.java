package com.power.mercenary.adapter.message;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.power.mercenary.R;
import com.power.mercenary.bean.MsgTaskBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * admin  2018/7/23 wan
 */
public class MessageTaskAdapter extends RecyclerView.Adapter {

    private Context context;

    private OnItemClickListener onItemClickListener;

    private List<MsgTaskBean> datas;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public MessageTaskAdapter(Context context, List<MsgTaskBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.msg_item_view, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof TaskViewHolder) {
            TaskViewHolder viewHolder = (TaskViewHolder) holder;

            viewHolder.title.setText(datas.get(position).getTask_name());

            long nowTime = System.currentTimeMillis();
            SimpleDateFormat sdf = null;
            if (nowTime - datas.get(position).getPush_time() < 1000 * 60 * 60 * 24) {
                sdf = new SimpleDateFormat("HH:mm");// 1
            } else {
                sdf = new SimpleDateFormat("MM月dd日 HH:mm");
            }
            viewHolder.time.setText(sdf.format(new Date(datas.get(position).getPush_time())));

            if (datas.get(position).getRead_status() != null) {
                switch (datas.get(position).getRead_status()) {
                    case "1":
                        viewHolder.state.setVisibility(View.GONE);
                        break;
                    case "0":
                        viewHolder.state.setVisibility(View.VISIBLE);
                        break;
                }
            }

            viewHolder.content.setText(datas.get(position).getContent());

            viewHolder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClickListener(datas.get(position), position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {

        private TextView title;

        private TextView time;

        private TextView content;

        private LinearLayout layout;

        private View state;

        public TaskViewHolder(View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.item_messageTask_layout);
            title = itemView.findViewById(R.id.item_messageTask_title);
            time = itemView.findViewById(R.id.item_messageTask_time);
            content = itemView.findViewById(R.id.item_messageTask_content);
            state = itemView.findViewById(R.id.item_messagePrivate_hint);
        }
    }

    public interface OnItemClickListener {
        void onItemClickListener(MsgTaskBean msgTaskBean, int position);
    }
}
