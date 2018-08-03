package com.power.mercenary.adapter.message;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.power.mercenary.MyApplication;
import com.power.mercenary.R;
import com.power.mercenary.bean.MsgPrivateBean;
import com.power.mercenary.utils.Urls;
import com.power.mercenary.view.CircleImageView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * admin  2018/7/23 wan
 */
public class MessagePrivateAdapter extends RecyclerView.Adapter {

    private Context context;

    private OnItemClickListener onItemClickListener;

    private List<MsgPrivateBean> datas;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public MessagePrivateAdapter(Context context, List<MsgPrivateBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.siliao_msg_item_view, null);
        return new PrivateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof PrivateViewHolder) {
            PrivateViewHolder viewHolder = (PrivateViewHolder) holder;

            viewHolder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClickListener(datas.get(position), position);
                }
            });

            if (TextUtils.equals(datas.get(position).getFromuserid(), MyApplication.getUserId())) {
                Glide.with(context)
                        .load(Urls.BASEIMGURL + datas.get(position).getTouser_name())
                        .into(viewHolder.icon);

                viewHolder.title.setText(datas.get(position).getTouser_name());
            } else {
                Glide.with(context)
                        .load(Urls.BASEIMGURL + datas.get(position).getFromuserhead_img())
                        .into(viewHolder.icon);

                viewHolder.title.setText(datas.get(position).getFromuser_name());

                switch (datas.get(position).getRead_status()) {
                    case "1":
                        viewHolder.mHint.setVisibility(View.GONE);
                        break;
                    case "0":
                        viewHolder.mHint.setVisibility(View.VISIBLE);
                        break;
                }
            }

            long nowTime = System.currentTimeMillis();
            SimpleDateFormat sdf = null;
            if (nowTime - datas.get(position).getMsgtime() < 1000 * 60 * 60 * 24) {
                sdf = new SimpleDateFormat("HH:mm");// 1
            } else {
                sdf = new SimpleDateFormat("MM月dd日 HH:mm");
            }
            viewHolder.time.setText(sdf.format(new Date(datas.get(position).getMsgtime())));

            viewHolder.content.setText(datas.get(position).getContent());
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class PrivateViewHolder extends RecyclerView.ViewHolder {

        CircleImageView icon;

        TextView title;

        TextView time;

        TextView content;

        LinearLayout layout;

        View mHint;

        public PrivateViewHolder(View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.item_messagePrivate_layout);
            icon = itemView.findViewById(R.id.item_messagePrivate_icon);
            title = itemView.findViewById(R.id.item_messagePrivate_title);
            time = itemView.findViewById(R.id.item_messagePrivate_time);
            content = itemView.findViewById(R.id.item_messagePrivate_content);
            mHint = itemView.findViewById(R.id.item_messagePrivate_hint);
        }
    }

    public interface OnItemClickListener {
        void onItemClickListener(MsgPrivateBean msgPrivateBean, int position);
    }
}
