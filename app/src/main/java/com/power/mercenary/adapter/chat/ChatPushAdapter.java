package com.power.mercenary.adapter.chat;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.power.mercenary.R;
import com.power.mercenary.bean.MsgDetailsBean;
import com.power.mercenary.utils.Urls;
import com.power.mercenary.view.CircleImageView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * admin  2018/7/26 wan
 */
public class ChatPushAdapter extends BaseAdapter {
    private Context context;
    private List<MsgDetailsBean> list;

    private ChatMsgAdapter.OnBackgroundClickListener onBackgroundClickListener;

    public ChatPushAdapter(Context context, List<MsgDetailsBean> list) {
        this.context = context;
        this.list = list;
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
            if (list.size() > 0) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_chat_view, null);

            }
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

            long nowTime = System.currentTimeMillis();
            SimpleDateFormat sdf = null;
            if (nowTime - list.get(position).getCreate_time() < 1000 * 60 * 60 * 24) {
                sdf = new SimpleDateFormat("HH:mm");// 1
            } else {
                sdf = new SimpleDateFormat("MM月dd日 HH:mm");
            }
            if (position == 0) {
                vh.time.setVisibility(View.VISIBLE);
                vh.time.setText(sdf.format(new Date(list.get(position).getCreate_time())));
            } else {
                if (list.get(position).getCreate_time() - list.get(position - 1).getCreate_time() > 3 * 60 * 1000) {
                    vh.time.setVisibility(View.VISIBLE);
                    vh.time.setText(sdf.format(new Date(list.get(position).getCreate_time())));
                } else {
                    vh.time.setVisibility(View.GONE);
                }
            }


            vh.left.setVisibility(View.VISIBLE);
            vh.right.setVisibility(View.GONE);

            Glide.with(context)
                    .load(Urls.BASEIMGURL + list.get(position).getLiuyan_user_headimg())
                    .into(vh.leftPhoto);
            vh.leftContent.setText(list.get(position).getLiuyan_content());



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
