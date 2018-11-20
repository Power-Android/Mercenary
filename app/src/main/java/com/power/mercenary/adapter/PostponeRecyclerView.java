package com.power.mercenary.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.power.mercenary.R;

import java.util.List;

public class PostponeRecyclerView extends RecyclerView.Adapter<PostponeRecyclerView.ViewHolder>{

    private Context context;

    private List<Integer> list;

    public PostponeRecyclerView(Context context, List<Integer> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public PostponeRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View inflate = LayoutInflater.from(context).inflate(R.layout.postpone_item_layout, parent, false);

        ViewHolder viewHolder = new ViewHolder(inflate);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PostponeRecyclerView.ViewHolder holder, final int position) {


        holder.postponeTime.setText(list.get(position)+"天");


        holder.postponeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (onItemClickListener !=null){

                    onItemClickListener.OnClick(position);

                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size() == 0 ? 0 :list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView postponeTime;

        public ViewHolder(View itemView) {
            super(itemView);

            postponeTime = itemView.findViewById(R.id.postpone_time);


        }
    }


    OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{

        void OnClick(int position);

    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
