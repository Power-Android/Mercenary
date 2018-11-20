package com.power.mercenary.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.power.mercenary.MyApplication;
import com.power.mercenary.R;
import com.power.mercenary.activity.details_audit_publish.GRPublishAuditActivity;
import com.power.mercenary.activity.details_audit_publish.GZPublishAuditActivity;
import com.power.mercenary.activity.details_audit_publish.PTPublishAuditActivity;
import com.power.mercenary.activity.details_audit_publish.SHPublishAuditActivity;
import com.power.mercenary.adapter.ReleaseSHZAdapter;
import com.power.mercenary.base.BaseFragment;
import com.power.mercenary.bean.mytask.PublishTaskBean;
import com.power.mercenary.http.OkhtttpUtils;
import com.power.mercenary.presenter.publish.PublishPresenter;
import com.power.mercenary.utils.TUtils;
import com.power.mercenary.view.pullrecyclerview.WanRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/30.
 */

public class ReleaseSHZFragment  extends BaseFragment implements WanRecyclerView.PullRecyclerViewCallBack, PublishPresenter.PublishCallBack, ReleaseSHZAdapter.TaskBtnListener {

    List<PublishTaskBean.DataBean> mList=new ArrayList<>();

    private PublishPresenter publishPresenter;

    private WanRecyclerView wanRecyclerView;

    private ReleaseSHZAdapter adapter;

    private int page = 1;



    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = View.inflate(getActivity(), R.layout.fragment_release,null);

        wanRecyclerView = view.findViewById(R.id.frag_release_recyclerView);
        wanRecyclerView.setLinearLayout();
        wanRecyclerView.setPullRecyclerViewListener(this);

        adapter = new ReleaseSHZAdapter(getContext(), mList);

        adapter.setTaskBtnListener(this);
        wanRecyclerView.setAdapter(adapter);

        publishPresenter = new PublishPresenter(getActivity(), this);


        return view;
    }

    @Override
    protected void initLazyData() {
        publishPresenter.getPublishTaskList(page, 3);
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

    }

    @Override
    public void outTaskRequestSuccess(int position) {

    }

    @Override
    public void auditTaskRequestSuccess(int type, int position) {
        TUtils.showCustom(getContext(), "审核成功");
    }

    @Override
    public void appraiseRequestSuccess() {

    }

    @Override
    public void onRefresh() {
        page = 1;
        mList.clear();
        publishPresenter.getPublishTaskList(page, 3);

    }

    @Override
    public void onLoadMore() {
        page ++;
        publishPresenter.getPublishTaskList(page, 3);
    }

    @Override
    public void TaskOnClickListener() {
        page = 1;
        mList.clear();
        publishPresenter.getPublishTaskList(page, 3);
    }

    @Override
    public void TaskOnClickViewListener(String id, int position, String taskType, String taskState) {
        switch (taskType) {
            case "1":
                Intent ptIntent = new Intent(getActivity(), PTPublishAuditActivity.class);
                ptIntent.putExtra("taskId", id);
                startActivity(ptIntent);
                break;

            case "2":
            case "5":
            case "6":
                Intent shIntent = new Intent(getActivity(), SHPublishAuditActivity.class);
                shIntent.putExtra("taskId", id);
                startActivity(shIntent);
                break;

            case "3":
                Intent grIntent = new Intent(getActivity(), GRPublishAuditActivity.class);
                grIntent.putExtra("taskId", id);
                startActivity(grIntent);
                break;

            case "4":
                Intent gzIntent = new Intent(getActivity(), GZPublishAuditActivity.class);
                gzIntent.putExtra("taskId", id);
                startActivity(gzIntent);
                break;
        }
    }
}
