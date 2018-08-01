package com.power.mercenary.adapter.chat;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.power.mercenary.R;
import com.power.mercenary.bean.user.UserInfo;
import com.power.mercenary.data.CacheConstants;
import com.power.mercenary.utils.CacheUtils;
import com.power.mercenary.utils.Urls;
import com.power.mercenary.view.CircleImageView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.message.TextMessage;

/**
 * Created by   admin on 2018/5/17.
 */

public class ChatMsgAdapter extends BaseAdapter {
    private Context context;
    private List<Message> list;

    private String avatar;

    private OnBackgroundClickListener onBackgroundClickListener;

    public ChatMsgAdapter(Context context, List<Message> list) {
        this.context = context;
        this.list = list;
    }

    public void setUserHeader(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MsgViewHolder vh = null;
        if (convertView == null) {
            vh = new MsgViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_chat_view, null);
            vh.layout = (LinearLayout) convertView.findViewById(R.id.item_chat_lt);
            vh.time = (TextView) convertView.findViewById(R.id.item_chat_time);
            vh.left = (LinearLayout) convertView.findViewById(R.id.item_chat_qipao1);
            vh.leftPhoto = (ImageView) convertView.findViewById(R.id.item_chat_text_left);
            vh.leftContent = (TextView) convertView.findViewById(R.id.item_chat_content_left);
            vh.right = (LinearLayout) convertView.findViewById(R.id.item_chat_qipao2);
            vh.rightPhoto = (CircleImageView) convertView.findViewById(R.id.item_chat_text_right);
            vh.rightContent = (TextView) convertView.findViewById(R.id.item_chat_content_right);
            convertView.setTag(vh);
        } else {
            vh = (MsgViewHolder) convertView.getTag();
        }

        vh.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onBackgroundClickListener.onClickListener();
            }
        });

        if (list != null) {
            MessageContent messageContent = list.get(position).getContent();

            long nowTime = System.currentTimeMillis();
            SimpleDateFormat sdf = null;
            if (nowTime - list.get(position).getSentTime() < 1000 * 60 * 60 * 24) {
                sdf = new SimpleDateFormat("HH:mm");// 1
            } else {
                sdf = new SimpleDateFormat("MM月dd日 HH:mm");
            }
            if (position == 0) {
                vh.time.setVisibility(View.VISIBLE);
                vh.time.setText(sdf.format(new Date(list.get(position).getSentTime())));
            } else {
                if (list.get(position).getSentTime() - list.get(position - 1).getSentTime() > 3 * 60 * 1000) {
                    vh.time.setVisibility(View.VISIBLE);
                    vh.time.setText(sdf.format(new Date(list.get(position).getSentTime())));
                } else {
                    vh.time.setVisibility(View.GONE);
                }
            }

            UserInfo userInfo = CacheUtils.get(CacheConstants.USERINFO);

            if (!TextUtils.equals(userInfo.getId(), list.get(position).getSenderUserId())) {
                io.rong.imlib.model.UserInfo userInfo1 = messageContent.getUserInfo();
                vh.left.setVisibility(View.VISIBLE);
                vh.right.setVisibility(View.GONE);

                Glide.with(context)
                        .load(Urls.BASEIMGURL + avatar)
                        .into(vh.leftPhoto);

                if (messageContent instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) messageContent;
                    vh.leftContent.setText(textMessage.getContent());
                }

            } else {
                vh.left.setVisibility(View.GONE);
                vh.right.setVisibility(View.VISIBLE);
                Glide.with(context)
                        .load(Urls.BASEIMGURL + userInfo.getHead_img())
                        .into(vh.rightPhoto);

                if (messageContent instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) messageContent;
                    vh.rightContent.setText(textMessage.getContent());
                }
            }

        }

        return convertView;
    }

    public int getAdapterListSize() {

        return list.size();
    }

    class MsgViewHolder {
        LinearLayout layout;

        TextView time;

        LinearLayout left;
        ImageView leftPhoto;
        TextView leftContent;

        LinearLayout right;
        CircleImageView rightPhoto;
        TextView rightContent;
    }

    public interface OnBackgroundClickListener {
        void onClickListener();
    }
}
