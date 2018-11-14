package com.power.mercenary.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.power.mercenary.R;
import com.power.mercenary.activity.details_out_publish.GRPublishOutActivity;
import com.power.mercenary.activity.details_out_publish.GZPublishOutActivity;
import com.power.mercenary.activity.details_out_publish.PTPublishOutActivity;
import com.power.mercenary.activity.details_out_publish.SHPublishOutActivity;
import com.power.mercenary.adapter.ReleaseYXJAdapter;
import com.power.mercenary.base.BaseFragment;
import com.power.mercenary.bean.mytask.PublishTaskBean;
import com.power.mercenary.presenter.publish.PublishPresenter;
import com.power.mercenary.view.pullrecyclerview.WanRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/30.
 */

public class ReleaseYXJFragment extends BaseFragment implements WanRecyclerView.PullRecyclerViewCallBack, PublishPresenter.PublishCallBack, ReleaseYXJAdapter.TaskBtnListener {

    List<PublishTaskBean.DataBean> mList=new ArrayList<>();

    private PublishPresenter publishPresenter;

    private WanRecyclerView wanRecyclerView;

    private ReleaseYXJAdapter adapter;

    private int page = 1;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = View.inflate(getActivity(), R.layout.fragment_release,null);

        wanRecyclerView = view.findViewById(R.id.frag_release_recyclerView);
        wanRecyclerView.setLinearLayout();
        wanRecyclerView.setPullRecyclerViewListener(this);

        adapter = new ReleaseYXJAdapter(getContext(), mList, "上架");
        adapter.setTaskBtnListener(this);
        wanRecyclerView.setAdapter(adapter);

        publishPresenter = new PublishPresenter(getActivity(), this);

        return view;
    }

    @Override
    protected void initLazyData() {
        publishPresenter.getPublishTaskList(page, 4);
    }

    @Override
    public void getPublishTaskList(List<PublishTaskBean.DataBean> datas) {
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
        mList.remove(position);
        adapter.notifyDataSetChanged();
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
        publishPresenter.getPublishTaskList(page, 4);
    }

    @Override
    public void onLoadMore() {
        page ++;
        publishPresenter.getPublishTaskList(page, 4);
    }

    @Override
    public void TaskOnClickListener(String id, int position, String taskType, String taskState) {
        switch (taskType) {
            case "1":
                Intent ptIntent = new Intent(getActivity(), PTPublishOutActivity.class);
                ptIntent.putExtra("taskId", id);
                startActivity(ptIntent);
                break;

            case "2":
            case "5":
            case "6":
                Intent shIntent = new Intent(getActivity(), SHPublishOutActivity.class);
                shIntent.putExtra("taskId", id);
                startActivity(shIntent);
                break;

            case "3":
                Intent grIntent = new Intent(getActivity(), GRPublishOutActivity.class);
                grIntent.putExtra("taskId", id);
                startActivity(grIntent);
                break;

            case "4":
                Intent gzIntent = new Intent(getActivity(), GZPublishOutActivity.class);
                gzIntent.putExtra("taskId", id);
                startActivity(gzIntent);
                break;
        }
    }

    @Override
    public void TaskOnClick2Listener(String id, int position) {
        publishPresenter.putTaskRequest(id, position);
    }
}
