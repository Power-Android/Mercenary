package com.power.mercenary.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.power.mercenary.R;
import com.power.mercenary.activity.chat.ChatActivity;
import com.power.mercenary.adapter.message.MessagePrivateAdapter;
import com.power.mercenary.base.BaseFragment;
import com.power.mercenary.view.pullrecyclerview.WanRecyclerView;

/**
 * admin  2018/7/23 wan
 */
public class MessagePrivateFragment extends BaseFragment implements WanRecyclerView.PullRecyclerViewCallBack, MessagePrivateAdapter.OnItemClickListener {

    private MessagePrivateAdapter adapter;

    private WanRecyclerView mRecyclerView;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_message_list, null);

        mRecyclerView = view.findViewById(R.id.frag_messageList_recyclerView);
        mRecyclerView.setLinearLayout();
        mRecyclerView.setPullRecyclerViewListener(this);

        adapter = new MessagePrivateAdapter(getContext());
        adapter.setOnItemClickListener(this);

        mRecyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    protected void initLazyData() {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onItemClickListener() {
        String[] strings = {"18", "20", "21", "22", "23"};
        String[] imgUrl = {"http://imgsrc.baidu.com/imgad/pic/item/0df3d7ca7bcb0a466e41231d6163f6246b60afb7.jpg", "http://p1.qzone.la/upload/20150218/x5alew4n.jpg", "http://att.bbs.duowan.com/forum/201411/09/152152z6vxvha6kmkwokx6.jpg", "http://imgsrc.baidu.com/forum/pic/item/0b46f21fbe096b63c7397ec70c338744ebf8ac00.jpg", "https://img5.duitang.com/uploads/item/201506/14/20150614205903_85zCv.jpeg"};
        int i = (int) (Math.random() * strings.length);
        ChatActivity.invoke(getActivity(), strings[i], imgUrl[i], strings[i]);
    }
}
