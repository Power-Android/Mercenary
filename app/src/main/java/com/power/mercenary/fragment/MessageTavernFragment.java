package com.power.mercenary.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.power.mercenary.R;
import com.power.mercenary.activity.chat.ChatPushActivity;
import com.power.mercenary.adapter.message.MessageTavernAdapter;
import com.power.mercenary.base.BaseFragment;
import com.power.mercenary.bean.MsgTavernBean;
import com.power.mercenary.presenter.MsgTavernPresenter;
import com.power.mercenary.view.pullrecyclerview.WanRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * admin  2018/7/23 wan
 */
public class MessageTavernFragment extends BaseFragment implements WanRecyclerView.PullRecyclerViewCallBack, MessageTavernAdapter.OnItemClickListener, MsgTavernPresenter.TavernCallBack {

    private WanRecyclerView mRecyclerView;

    private MessageTavernAdapter adapter;

    private List<MsgTavernBean> datas;

    private MsgTavernPresenter presenter;
    private int page = 1;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_message_list, null);
        mRecyclerView = view.findViewById(R.id.frag_messageList_recyclerView);
        mRecyclerView.setLinearLayout();
        mRecyclerView.setPullRecyclerViewListener(this);
        datas = new ArrayList<>();
        adapter = new MessageTavernAdapter(getContext(), datas);
        adapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(adapter);
        presenter = new MsgTavernPresenter(getActivity(), this);
        presenter.getTavernList(page);
        return view;
    }
    @Override
    protected void initLazyData() {
    }
    @Override
    public void onRefresh() {
        page = 1;
        datas.clear();
        presenter.getTavernList(page);
    }

    @Override
    public void onLoadMore() {
        page++;
        presenter.getTavernList(page);
    }

    @Override
    public void onItemClickListener(MsgTavernBean msgTavernBean, int position) {
        datas.get(position).setRead_status("1");
        adapter.notifyDataSetChanged();
        ChatPushActivity.invoke(getActivity(), "酒馆", msgTavernBean.getId(), "post", msgTavernBean.messageType);
        presenter.setMessageState(msgTavernBean.getId(), msgTavernBean.messageType);
    }

    @Override
    public void getTavernList(List<MsgTavernBean> data) {

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
    public void getTavernListFail() {
        mRecyclerView.setHasMore(0, 20);
    }
}