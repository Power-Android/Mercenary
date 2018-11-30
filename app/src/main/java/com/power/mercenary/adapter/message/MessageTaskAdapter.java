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
import com.power.mercenary.bean.MsgTaskBean;
import com.power.mercenary.utils.PhpTimeUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.umeng.socialize.net.dplus.CommonNetImpl.TAG;

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
            //将十位的时间戳通过调用方法转换为正常时间格式
            String phptime = PhpTimeUtils.phptime(datas.get(position).getPush_time());
            viewHolder.time.setText(phptime);
            if (datas.get(position).getRead_state() != null) {
                switch (datas.get(position).getRead_state()) {
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
