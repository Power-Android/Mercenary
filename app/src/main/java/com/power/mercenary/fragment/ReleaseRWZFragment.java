package com.power.mercenary.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.power.mercenary.R;
import com.power.mercenary.activity.details_appraise_publish.GRPublishAppraiseActivity;
import com.power.mercenary.activity.details_appraise_publish.GZPublishAppraiseActivity;
import com.power.mercenary.activity.details_appraise_publish.PTPublishAppraiseActivity;
import com.power.mercenary.activity.details_appraise_publish.SHPublishAppraiseActivity;
import com.power.mercenary.activity.details_intask_publish.GRPublishInTaskActivity;
import com.power.mercenary.activity.details_intask_publish.GZPublishInTaskActivity;
import com.power.mercenary.activity.details_intask_publish.PTPublishInTaskActivity;
import com.power.mercenary.activity.details_intask_publish.SHPublishInTaskActivity;
import com.power.mercenary.adapter.ReleaseRWZAdapter;
import com.power.mercenary.base.BaseFragment;
import com.power.mercenary.bean.mytask.PublishTaskBean;
import com.power.mercenary.presenter.publish.PublishPresenter;
import com.power.mercenary.view.pullrecyclerview.WanRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/30.
 */

public class ReleaseRWZFragment extends BaseFragment implements PublishPresenter.PublishCallBack, WanRecyclerView.PullRecyclerViewCallBack, ReleaseRWZAdapter.TaskBtnListener {


    List<PublishTaskBean> mList=new ArrayList<>();

    private PublishPresenter publishPresenter;

    private WanRecyclerView wanRecyclerView;

    private ReleaseRWZAdapter adapter;

    private int page = 1;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = View.inflate(getActivity(), R.layout.fragment_release,null);

        wanRecyclerView = view.findViewById(R.id.frag_release_recyclerView);
        wanRecyclerView.setLinearLayout();
        wanRecyclerView.setPullRecyclerViewListener(this);

        adapter = new ReleaseRWZAdapter(getContext(), mList);
        wanRecyclerView.setAdapter(adapter);
        adapter.setListener(this);

        publishPresenter = new PublishPresenter(getActivity(), this);

        return view;
    }

    @Override
    protected void initLazyData() {
        publishPresenter.getPublishTaskList(page, 2);
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
    public void putTaskRequestSuccess(int position) {

    }

    @Override
    public void outTaskRequestSuccess(int position) {

    }

    @Override
    public void auditTaskRequestSuccess(int type, int position) {

    }

    @Override
    public void appraiseRequestSuccess() {

    }

    @Override
    public void onRefresh() {
        page = 1;
        mList.clear();
        publishPresenter.getPublishTaskList(page, 2);
    }

    @Override
    public void onLoadMore() {
        page ++;
        publishPresenter.getPublishTaskList(page, 2);
    }

    @Override
    public void TaskOnClickViewListener(String id, int position, String taskType, String taskState) {
        switch (taskType) {
            case "1":
                Intent ptIntent = new Intent(getActivity(), PTPublishInTaskActivity.class);
                ptIntent.putExtra("taskId", id);
                startActivity(ptIntent);
                break;

            case "2":
            case "5":
            case "6":
                Intent shIntent = new Intent(getActivity(), SHPublishInTaskActivity.class);
                shIntent.putExtra("taskId", id);
                startActivity(shIntent);
                break;

            case "3":
                Intent grIntent = new Intent(getActivity(), GRPublishInTaskActivity.class);
                grIntent.putExtra("taskId", id);
                startActivity(grIntent);
                break;

            case "4":
                Intent gzIntent = new Intent(getActivity(), GZPublishInTaskActivity.class);
                gzIntent.putExtra("taskId", id);
                startActivity(gzIntent);
                break;
        }
    }
}
