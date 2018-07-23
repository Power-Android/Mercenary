package com.power.mercenary.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.power.mercenary.R;
import com.power.mercenary.adapter.task.TaskStatisticsAdapter;
import com.power.mercenary.base.BaseFragment;
import com.power.mercenary.bean.mytask.StatisticsListBean;
import com.power.mercenary.bean.mytask.StatisticsNumBean;
import com.power.mercenary.presenter.StatisticsPresenter;
import com.power.mercenary.view.pullrecyclerview.WanRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * admin  2018/7/19 wan
 */
public class StatisticsFragment extends BaseFragment implements WanRecyclerView.PullRecyclerViewCallBack, StatisticsPresenter.StatisticsCallBack {

    private WanRecyclerView mRecyclerView;

    private StatisticsPresenter presenter;

    private int page = 1;

    private int state;
    private int frag;

    private List<StatisticsListBean> datas;

    private TaskStatisticsAdapter adapter;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_statistics, null);
        mRecyclerView = view.findViewById(R.id.act_task_star_recyclerView);
        mRecyclerView.setLinearLayout();
        mRecyclerView.setPullRecyclerViewListener(this);

        state = getArguments().getInt("state");
        frag = getArguments().getInt("frag");

        datas = new ArrayList<>();

        adapter = new TaskStatisticsAdapter(mContext, datas);
        mRecyclerView.setAdapter(adapter);

        presenter = new StatisticsPresenter(getActivity(), this);

        return view;
    }

    @Override
    protected void initLazyData() {
        switch (state) {
            case 1:
                presenter.getTaskStatisticsAcceptList(page, frag);
                break;
            case 2:
                presenter.getTaskStatisticsPublishList(page, frag);
                break;
        }
    }

    @Override
    public void onRefresh() {
        page = 1;
        datas.clear();
        switch (state) {
            case 1:
                presenter.getTaskStatisticsAcceptList(page, frag);
                break;
            case 2:
                presenter.getTaskStatisticsPublishList(page, frag);
                break;
        }
    }

    @Override
    public void onLoadMore() {
        page ++;
        switch (state) {
            case 1:
                presenter.getTaskStatisticsAcceptList(page, frag);
                break;
            case 2:
                presenter.getTaskStatisticsPublishList(page, frag);
                break;
        }
    }

    @Override
    public void getTaskStatisticsPublishNum(StatisticsNumBean data) {

    }

    @Override
    public void getTaskStatisticsAcceptNum(StatisticsNumBean data) {

    }

    @Override
    public void getTaskStatisticsPublishList(List<StatisticsListBean> data) {
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
    public void getTaskStatisticsAcceptList(List<StatisticsListBean> data) {
        if (data != null) {
            datas.addAll(data);
            mRecyclerView.setHasMore(data.size(), 20);
        } else {
            mRecyclerView.setHasMore(0, 20);
        }
        adapter.notifyDataSetChanged();
        mRecyclerView.setStateView(datas.size());
    }
}
