package com.power.mercenary.adapter.task;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.power.mercenary.R;
import com.power.mercenary.bean.task.MsgListBean;
import com.power.mercenary.view.CircleImageView;

import java.util.List;

/**
 * admin  2018/7/13 wan
 */
public class DetailsMsgAdapter extends RecyclerView.Adapter {
    private Context context;

    private List<MsgListBean> data;

    public DetailsMsgAdapter(Context context, List<MsgListBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.message_board_iten_view, null);
        return new MsgViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MsgViewHolder) {
            MsgViewHolder viewHolder = (MsgViewHolder) holder;

            Glide.with(context)
                    .load(data.get(position).getLeavemsg_user_headimg())
                    .into(viewHolder.icon);

            viewHolder.name.setText(data.get(position).getLeavemsg_user_name());

            viewHolder.time.setText(data.get(position).getCreate_time() + "");

            viewHolder.content.setText(data.get(position).getLeavemsg_content());

        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MsgViewHolder extends RecyclerView.ViewHolder {
        CircleImageView icon;
        TextView name;
        TextView time;
        TextView content;

        public MsgViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.item_msgDetails_view_icon);
            name = itemView.findViewById(R.id.item_msgDetails_view_name);
            time = itemView.findViewById(R.id.item_msgDetails_view_time);
            content = itemView.findViewById(R.id.item_msgDetails_view_content);
        }
    }
}
