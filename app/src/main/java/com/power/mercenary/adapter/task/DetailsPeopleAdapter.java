package com.power.mercenary.adapter.task;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.power.mercenary.MyApplication;
import com.power.mercenary.R;
import com.power.mercenary.activity.chat.ChatActivity;
import com.power.mercenary.bean.task.ApplyListBean;
import com.power.mercenary.utils.Urls;
import com.power.mercenary.view.CircleImageView;

import java.util.List;

/**
 * admin  2018/7/13 wan
 */
public class DetailsPeopleAdapter extends RecyclerView.Adapter {
    private Context context;

    private List<ApplyListBean> data;

    private int viewState = 0;

    private DetailsPepCallBack detailsPepCallBack;

    public void setDetailsPepCallBack(DetailsPepCallBack detailsPepCallBack){
        this.detailsPepCallBack = detailsPepCallBack;
    }

    public DetailsPeopleAdapter(Context context, List<ApplyListBean> data, int viewState) {
        this.context = context;
        this.data = data;
        this.viewState = viewState;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ybmr_item_view, null);
        return new PepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof PepViewHolder) {
            PepViewHolder viewHolder = (PepViewHolder) holder;

            switch (viewState) {
                case 1:
                    viewHolder.dx.setVisibility(View.GONE);
                    viewHolder.del.setVisibility(View.GONE);
                    break;
                case 0:
                    viewHolder.dx.setVisibility(View.VISIBLE);
                    viewHolder.del.setVisibility(View.VISIBLE);
                    break;
            }

            Glide.with(context)
                    .load(Urls.BASEIMGURL + data.get(position).getApply_user_headimg())
                    .into(viewHolder.icon);

            viewHolder.name.setText(data.get(position).getApply_user_name());

            viewHolder.icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    detailsPepCallBack.queryInformation(data.get(position).getApply_user_id());
                    if (!TextUtils.equals(data.get(position).getApply_user_id(), MyApplication.getUserId())) {
                        ChatActivity.invoke(context, data.get(position).getApply_user_id(), data.get(position).getApply_user_headimg(), data.get(position).getApply_user_name());
                    }
                }
            });

            viewHolder.del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    detailsPepCallBack.selectedPeople(data.get(position).getId(), 3, data.get(position).getApply_user_headimg(), data.get(position).getApply_user_name(), data.get(position).getApply_user_id());
                }
            });

            viewHolder.dx.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    detailsPepCallBack.selectedPeople(data.get(position).getId(), 1, data.get(position).getApply_user_headimg(), data.get(position).getApply_user_name(), data.get(position).getApply_user_id());
                }
            });

            viewHolder.xd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    detailsPepCallBack.selectedPeople(data.get(position).getId(), 2, data.get(position).getApply_user_headimg(), data.get(position).getApply_user_name(), data.get(position).getApply_user_id());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class PepViewHolder extends RecyclerView.ViewHolder {

        TextView xd;
        TextView dx;
        TextView del;
        TextView name;
        CircleImageView icon;

        public PepViewHolder(View itemView) {
            super(itemView);
            xd = itemView.findViewById(R.id.item_ybmr_view_Btn_ok);
            dx = itemView.findViewById(R.id.item_ybmr_view_Btn_dx);
            del = itemView.findViewById(R.id.item_ybmr_view_Btn_del);
            name = itemView.findViewById(R.id.item_ybmr_view_name);
            icon = itemView.findViewById(R.id.item_ybmr_view_icon);
        }
    }

    public interface DetailsPepCallBack{
        void selectedPeople(String id, int state, String avatar, String name, String userId);
        void queryInformation(String userId);
    }
}
