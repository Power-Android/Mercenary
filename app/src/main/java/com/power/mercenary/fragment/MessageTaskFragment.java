package com.power.mercenary.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.power.mercenary.R;
import com.power.mercenary.activity.chat.ChatActivity;
import com.power.mercenary.activity.chat.ChatPushActivity;
import com.power.mercenary.adapter.message.MessageTaskAdapter;
import com.power.mercenary.base.BaseFragment;
import com.power.mercenary.bean.MsgTaskBean;
import com.power.mercenary.presenter.MsgTaskPresenter;
import com.power.mercenary.view.pullrecyclerview.WanRecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.rong.imlib.RongIMClient;

/**
 * admin  2018/7/23 wan
 */
public class MessageTaskFragment extends BaseFragment implements WanRecyclerView.PullRecyclerViewCallBack, MessageTaskAdapter.OnItemClickListener, MsgTaskPresenter.TaskCallBack {

    private WanRecyclerView mRecyclerView;

    private MessageTaskAdapter adapter;

    private List<MsgTaskBean> datas;

    private MsgTaskPresenter presenter;
    private int page = 1;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_message_list, null);
        mRecyclerView = view.findViewById(R.id.frag_messageList_recyclerView);
        mRecyclerView.setLinearLayout();
        mRecyclerView.setPullRecyclerViewListener(this);
        datas = new ArrayList<>();
        adapter = new MessageTaskAdapter(getContext(), datas);
        adapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(adapter);
        presenter = new MsgTaskPresenter(getActivity(), this);
        presenter.getTaskList(page);
        return view;
    }
    @Override
    protected void initLazyData() {

    }
    @Override
    public void onRefresh() {
        page = 1;
        datas.clear();
        presenter.getTaskList(page);
    }

    @Override
    public void onLoadMore() {
        page++;
        presenter.getTaskList(page);
    }

    @Override
    public void onItemClickListener(MsgTaskBean msgTaskBean, int position) {
        presenter.setMessageState(msgTaskBean.getId());
       // ChatActivity.invoke(getActivity(),msgTaskBean.getUser_id(),msgTaskBean.getUser_head_img(),msgTaskBean.getUser_nick_name());
        datas.get(position).setRead_state("2");
        adapter.notifyDataSetChanged();

    }

    @Override
    public void getTaskList(List<MsgTaskBean> data) {
        if (data != null) {
            datas.addAll(data);
            mRecyclerView.setHasMore(data.size(), 20);
        } else {
            mRecyclerView.setHasMore(0, 20);
        }
        adapter.notifyDataSetChanged();
        mRecyclerView.setStateView(datas.size());
    }

    @Override
    public void getTaskListFail() {
        mRecyclerView.setHasMore(0, 20);
    }
}