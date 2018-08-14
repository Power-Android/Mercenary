package com.power.mercenary.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.power.mercenary.R;
import com.power.mercenary.activity.chat.ChatPushActivity;
import com.power.mercenary.adapter.message.MessageSystemAdapter;
import com.power.mercenary.base.BaseFragment;
import com.power.mercenary.bean.MsgSystemBean;
import com.power.mercenary.presenter.MsgSystemPresenter;
import com.power.mercenary.view.pullrecyclerview.WanRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * admin  2018/7/23 wan
 */
public class MessageSystemFragment extends BaseFragment implements WanRecyclerView.PullRecyclerViewCallBack, MessageSystemAdapter.OnItemClickListener, MsgSystemPresenter.SystemCallBack {

    private WanRecyclerView mRecyclerView;

    private MessageSystemAdapter adapter;

    private MsgSystemPresenter presenter;

    private int page = 1;

    private List<MsgSystemBean> datas;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_message_list, null);

        mRecyclerView = view.findViewById(R.id.frag_messageList_recyclerView);
        mRecyclerView.setLinearLayout();
        mRecyclerView.setPullRecyclerViewListener(this);

        datas = new ArrayList<>();

        adapter = new MessageSystemAdapter(getContext(), datas);
        adapter.setOnItemClickListener(this);

        mRecyclerView.setAdapter(adapter);

        presenter = new MsgSystemPresenter(getActivity(), this);
        presenter.getSystemList(page);

        return view;
    }

    @Override
    protected void initLazyData() {

    }

    @Override
    public void onRefresh() {
        page = 1;
        datas.clear();
        presenter.getSystemList(page);
    }

    @Override
    public void onLoadMore() {
        page++;
        presenter.getSystemList(page);
    }

    @Override
    public void onItemClickListener(MsgSystemBean msgSystemBean) {
        ChatPushActivity.invoke(getActivity(), "系统消息", msgSystemBean.getId(), "notice", "");
    }

    @Override
    public void getSystemList(List<MsgSystemBean> data) {
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
    public void getSystemListFail() {
        mRecyclerView.setHasMore(0, 20);
    }
}