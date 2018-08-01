package com.power.mercenary.adapter.chat;

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
import com.power.mercenary.bean.user.UserInfo;
import com.power.mercenary.data.CacheConstants;
import com.power.mercenary.utils.CacheUtils;
import com.power.mercenary.utils.Urls;
import com.power.mercenary.view.CircleImageView;

import java.util.List;

import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.message.TextMessage;

/**
 * admin  2018/7/25 wan
 */
public class ChatSearchAdapter extends RecyclerView.Adapter {

    private Context context;

    private List<Message> datas;

    private String imgUrls;

    private OnItemClickListener onItemClickListener;

    public ChatSearchAdapter(Context context, List<Message> datas, String imgUrls, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.datas = datas;
        this.imgUrls = imgUrls;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view_chat_search, null);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof SearchViewHolder) {
            SearchViewHolder viewHolder = (SearchViewHolder) holder;
            MessageContent messageContent = datas.get(position).getContent();

            if (messageContent instanceof TextMessage) {
                if (!TextUtils.equals(MyApplication.getUserId(), datas.get(position).getSenderUserId())) {
                    Glide.with(context)
                            .load(Urls.BASEIMGURL + imgUrls)
                            .into(viewHolder.icon);
                } else {
                    UserInfo userInfo = CacheUtils.get(CacheConstants.USERINFO);
                    if (userInfo != null) {
                        if (!TextUtils.isEmpty(userInfo.getHead_img())) {
                            Glide.with(context)
                                    .load(Urls.BASEIMGURL + userInfo.getHead_img())
                                    .into(viewHolder.icon);
                        }
                    }
                }
                TextMessage textMessage = (TextMessage) messageContent;
                viewHolder.content.setText(textMessage.getContent());
            }

            viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClickListener(position);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class SearchViewHolder extends RecyclerView.ViewHolder {
        CircleImageView icon;
        TextView content;

        View mView;

        public SearchViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

            icon = itemView.findViewById(R.id.item_chatSearch_icon);
            content = itemView.findViewById(R.id.item_chatSearch_content);
        }
    }

    public interface OnItemClickListener {
        void onItemClickListener(int position);
    }
}
