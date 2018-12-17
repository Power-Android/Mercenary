package com.power.mercenary.adapter.message;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.power.mercenary.R;
import com.power.mercenary.bean.MsgSystemBean;
import com.power.mercenary.utils.PhpTimeUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * admin  2018/7/23 wan
 */
public class MessageSystemAdapter  extends RecyclerView.Adapter {

    private Context context;

    private OnItemClickListener onItemClickListener;

    private List<MsgSystemBean> datas;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public MessageSystemAdapter(Context context, List<MsgSystemBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.system_msg_item_view, parent, false);
        return new SystemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof SystemViewHolder) {
            SystemViewHolder viewHolder = (SystemViewHolder) holder;
            viewHolder.title.setText(datas.get(position).getContent());

            String phptime = PhpTimeUtils.phptime(datas.get(position).getTime());
            viewHolder.time.setText(phptime);

            viewHolder.content.setText(datas.get(position).getContent());

            viewHolder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClickListener(datas.get(position));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class SystemViewHolder extends RecyclerView.ViewHolder {

        private TextView title;

        private TextView time;

        private TextView content;

        private LinearLayout layout;

        public SystemViewHolder(View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.item_system_layout);
            title = itemView.findViewById(R.id.item_system_title);
            time = itemView.findViewById(R.id.item_system_time);
            content = itemView.findViewById(R.id.item_system_content);
        }
    }

    public interface OnItemClickListener {
        void onItemClickListener(MsgSystemBean msgSystemBean);
    }


}
