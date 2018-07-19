package com.power.mercenary.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.power.mercenary.R;
import com.power.mercenary.adapter.task.ReleaseWJDAdapter;
import com.power.mercenary.base.BaseFragment;
import com.power.mercenary.bean.mytask.PublishTaskBean;
import com.power.mercenary.presenter.publish.PublishPresenter;
import com.power.mercenary.view.pullrecyclerview.WanRecyclerView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2018/3/30.
 */

public class ReleaseWJDFragment extends BaseFragment implements PublishPresenter.PublishCallBack, WanRecyclerView.PullRecyclerViewCallBack, ReleaseWJDAdapter.TaskHandleListener {


    List<PublishTaskBean> mList=new ArrayList<>();

    private PublishPresenter publishPresenter;

    private WanRecyclerView wanRecyclerView;

    private ReleaseWJDAdapter adapter;

    private int page = 1;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = View.inflate(getActivity(), R.layout.fragment_release,null);

        wanRecyclerView = view.findViewById(R.id.frag_release_recyclerView);
        wanRecyclerView.setLinearLayout();
        wanRecyclerView.setPullRecyclerViewListener(this);

        adapter = new ReleaseWJDAdapter(getContext(), mList);
        adapter.setListener(this);
        wanRecyclerView.setAdapter(adapter);

        publishPresenter = new PublishPresenter(getActivity(), this);

        return view;
    }

    @Override
    protected void initLazyData() {
        publishPresenter.getPublishTaskList(page, 1);
    }

    @Override
    public void getPublishTaskList(List<PublishTaskBean> datas) {
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
    public void getPublishTaskListFail() {
        wanRecyclerView.setHasMore(0, 10);
    }

    @Override
    public void putTaskRequestSuccess() {

    }

    @Override
    public void outTaskRequestSuccess() {

    }

    @Override
    public void auditTaskRequestSuccess() {

    }

    @Override
    public void appraiseRequestSuccess() {

    }

    @Override
    public void onRefresh() {
        page = 1;
        mList.clear();
        publishPresenter.getPublishTaskList(page, 1);
    }

    @Override
    public void onLoadMore() {
        page ++;
        publishPresenter.getPublishTaskList(page, 1);
    }

    @Override
    public void xiugai(String id) {

    }

    @Override
    public void chexiao(String id, int itemPosition) {
        mList.remove(itemPosition);
        adapter.notifyDataSetChanged();
        publishPresenter.outTaskRequest(id);
    }

    @Override
    public void yaoqing(String id) {

    }
}
