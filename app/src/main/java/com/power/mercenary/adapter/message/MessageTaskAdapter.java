package com.power.mercenary.adapter.message;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.power.mercenary.R;

/**
 * admin  2018/7/23 wan
 */
public class MessageTaskAdapter extends RecyclerView.Adapter {

    private Context context;

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public MessageTaskAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.msg_item_view, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TaskViewHolder) {
            TaskViewHolder viewHolder = (TaskViewHolder) holder;

            viewHolder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClickListener();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {

        private TextView title;

        private TextView time;

        private TextView content;

        private LinearLayout layout;

        public TaskViewHolder(View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.item_messageTask_layout);
            title = itemView.findViewById(R.id.item_messageTask_title);
            time = itemView.findViewById(R.id.item_messageTask_time);
            content = itemView.findViewById(R.id.item_messageTask_content);
        }
    }

    public interface OnItemClickListener {
        void onItemClickListener();
    }
}
