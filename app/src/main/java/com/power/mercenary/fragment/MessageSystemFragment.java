package com.power.mercenary.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.power.mercenary.R;
import com.power.mercenary.activity.chat.ChatActivity;
import com.power.mercenary.activity.chat.ChatPushActivity;
import com.power.mercenary.adapter.message.MessageTaskAdapter;
import com.power.mercenary.base.BaseFragment;
import com.power.mercenary.view.pullrecyclerview.WanRecyclerView;

/**
 * admin  2018/7/23 wan
 */
public class MessageSystemFragment extends BaseFragment implements WanRecyclerView.PullRecyclerViewCallBack, MessageTaskAdapter.OnItemClickListener {

    private WanRecyclerView mRecyclerView;

    private MessageTaskAdapter adapter;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_message_list, null);

        mRecyclerView = view.findViewById(R.id.frag_messageList_recyclerView);
        mRecyclerView.setLinearLayout();
        mRecyclerView.setPullRecyclerViewListener(this);

        adapter = new MessageTaskAdapter(getContext());
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
        ChatPushActivity.invoke(getActivity(), "系统消息");
    }
}