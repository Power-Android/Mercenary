package com.power.mercenary.adapter.message;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.power.mercenary.R;
import com.power.mercenary.view.CircleImageView;

/**
 * admin  2018/7/23 wan
 */
public class MessagePrivateAdapter extends RecyclerView.Adapter {

    private Context context;

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public MessagePrivateAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.siliao_msg_item_view, null);
        return new PrivateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PrivateViewHolder) {
            PrivateViewHolder viewHolder = (PrivateViewHolder) holder;

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

    class PrivateViewHolder extends RecyclerView.ViewHolder {

        CircleImageView icon;

        TextView title;

        TextView time;

        TextView content;

        LinearLayout layout;

        public PrivateViewHolder(View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.item_messagePrivate_layout);
            icon = itemView.findViewById(R.id.item_messagePrivate_icon);
            title = itemView.findViewById(R.id.item_messagePrivate_title);
            time = itemView.findViewById(R.id.item_messagePrivate_time);
            content = itemView.findViewById(R.id.item_messagePrivate_content);
        }
    }

    public interface OnItemClickListener {
        void onItemClickListener();
    }
}
