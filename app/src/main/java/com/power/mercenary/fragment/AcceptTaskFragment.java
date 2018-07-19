package com.power.mercenary.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.power.mercenary.R;
import com.power.mercenary.adapter.ReleaseSHZAdapter;
import com.power.mercenary.adapter.task.AcceptTaskAdapter;
import com.power.mercenary.base.BaseFragment;
import com.power.mercenary.bean.mytask.AcceptTaskBean;
import com.power.mercenary.presenter.accept.AcceptPresenter;
import com.power.mercenary.view.pullrecyclerview.WanRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * admin  2018/7/18 wan
 */
public class AcceptTaskFragment extends BaseFragment implements WanRecyclerView.PullRecyclerViewCallBack, AcceptPresenter.AcceptCallBack {

    private WanRecyclerView wanRecyclerView;

    private int page = 1;

    private AcceptTaskAdapter adapter;

    private List<AcceptTaskBean> mList;

    private int state;
    private AcceptPresenter acceptPresenter;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_release, null);

        state = getArguments().getInt("state");

        acceptPresenter = new AcceptPresenter(getActivity(), this);

        mList = new ArrayList<>();

        wanRecyclerView = view.findViewById(R.id.frag_release_recyclerView);
        wanRecyclerView.setLinearLayout();
        wanRecyclerView.setPullRecyclerViewListener(this);
        adapter = new AcceptTaskAdapter(getContext(), mList);

        wanRecyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    protected void initLazyData() {
        acceptPresenter.getAcceptTaskList(page, state);
    }

    @Override
    public void onRefresh() {
        page = 1;
        mList.clear();
        acceptPresenter.getAcceptTaskList(page, state);
    }

    @Override
    public void onLoadMore() {
        page++;
        acceptPresenter.getAcceptTaskList(page, state);
    }

    @Override
    public void getAcceptTaskList(List<AcceptTaskBean> datas) {
        if (datas != null) {
            mList.addAll(datas);
            wanRecyclerView.setHasMore(datas.size(), 10);
        } else {
            wanRecyclerView.setHasMore(0, 10);
        }
        adapter.notifyDataSetChanged();
        wanRecyclerView.setStateView(mList.size());
    }

    @Override
    public void getAcceptTaskListFail() {
        wanRecyclerView.setHasMore(0, 10);
    }
}
