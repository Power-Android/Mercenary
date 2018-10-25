package com.power.mercenary.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.power.mercenary.R;
import com.power.mercenary.activity.GRTaskDetailsActivity;
import com.power.mercenary.activity.GZTaskDetailsActivity;
import com.power.mercenary.activity.PTTaskDetailsActivity;
import com.power.mercenary.activity.SHTaskDetailsActivity;
import com.power.mercenary.activity.details_audit_accept.GRAcceptAuditActivity;
import com.power.mercenary.activity.details_audit_accept.GZAcceptAuditActivity;
import com.power.mercenary.activity.details_audit_accept.PTAcceptAuditActivity;
import com.power.mercenary.activity.details_audit_accept.SHAcceptAuditActivity;
import com.power.mercenary.activity.details_intask_accept.GRAcceptInTaskActivity;
import com.power.mercenary.activity.details_intask_accept.GZAcceptInTaskActivity;
import com.power.mercenary.activity.details_intask_accept.PTAcceptInTaskActivity;
import com.power.mercenary.activity.details_intask_accept.SHAcceptInTaskActivity;
import com.power.mercenary.activity.details_success_accept.GRAcceptSuccessActivity;
import com.power.mercenary.activity.details_success_accept.GZAcceptSuccessActivity;
import com.power.mercenary.activity.details_success_accept.PTAcceptSuccessActivity;
import com.power.mercenary.activity.details_success_accept.SHAcceptSuccessActivity;
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
public class AcceptTaskFragment extends BaseFragment implements WanRecyclerView.PullRecyclerViewCallBack, AcceptPresenter.AcceptCallBack, AcceptTaskAdapter.OnItemClickListener {

    private WanRecyclerView mRecyclerView;

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

        mRecyclerView = view.findViewById(R.id.frag_release_recyclerView);
        mRecyclerView.setLinearLayout();
        mRecyclerView.setPullRecyclerViewListener(this);
        adapter = new AcceptTaskAdapter(getContext(), mList,state);

        adapter.setOnItemClickListener(this);

        mRecyclerView.setAdapter(adapter);

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
            mRecyclerView.setHasMore(datas.size(), 10);
        } else {
            mRecyclerView.setHasMore(0, 10);
        }
        adapter.notifyDataSetChanged();
        mRecyclerView.setStateView(mList.size());
    }

    @Override
    public void getAcceptTaskListFail() {
        mRecyclerView.setHasMore(0, 10);
    }

    @Override
    public void onItemClickListener(String taskType, String taskId, String taskState) {
        // 0 1 2 3 6

//        if (TextUtils.equals(taskState, "1")) {
//
//
//        }

        if (TextUtils.equals(taskState, "2")) {
            switch (taskType) {
                case "1":
                    Intent ptIntent = new Intent(getActivity(), PTAcceptInTaskActivity.class);
                    ptIntent.putExtra("taskId", taskId);
                    startActivity(ptIntent);
                    break;

                case "2":
                case "5":
                case "6":
                    Intent shIntent = new Intent(getActivity(), SHAcceptInTaskActivity.class);
                    shIntent.putExtra("taskId", taskId);
                    startActivity(shIntent);
                    break;

                case "3":
                    Intent grIntent = new Intent(getActivity(), GRAcceptInTaskActivity.class);
                    grIntent.putExtra("taskId", taskId);
                    startActivity(grIntent);
                    break;

                case "4":
                    Intent gzIntent = new Intent(getActivity(), GZAcceptInTaskActivity.class);
                    gzIntent.putExtra("taskId", taskId);
                    startActivity(gzIntent);
                    break;
            }

        } else if (TextUtils.equals(taskState, "3")) {
            switch (taskType) {
                case "1":
                    Intent ptIntent = new Intent(getActivity(), PTAcceptAuditActivity.class);
                    ptIntent.putExtra("taskId", taskId);
                    startActivity(ptIntent);
                    break;

                case "2":
                case "5":
                case "6":
                    Intent shIntent = new Intent(getActivity(), SHAcceptAuditActivity.class);
                    shIntent.putExtra("taskId", taskId);
                    startActivity(shIntent);
                    break;

                case "3":
                    Intent grIntent = new Intent(getActivity(), GRAcceptAuditActivity.class);
                    grIntent.putExtra("taskId", taskId);
                    startActivity(grIntent);
                    break;

                case "4":
                    Intent gzIntent = new Intent(getActivity(), GZAcceptAuditActivity.class);
                    gzIntent.putExtra("taskId", taskId);
                    startActivity(gzIntent);
                    break;
            }

        } else if (TextUtils.equals(taskState, "6") || TextUtils.equals(taskState, "7")) {
            switch (taskType) {
                case "1":
                    Intent ptIntent = new Intent(getActivity(), PTAcceptSuccessActivity.class);
                    ptIntent.putExtra("taskId", taskId);
                    startActivity(ptIntent);
                    break;

                case "2":
                case "5":
                case "6":
                    Intent shIntent = new Intent(getActivity(), SHAcceptSuccessActivity.class);
                    shIntent.putExtra("taskId", taskId);
                    startActivity(shIntent);
                    break;

                case "3":
                    Intent grIntent = new Intent(getActivity(), GRAcceptSuccessActivity.class);
                    grIntent.putExtra("taskId", taskId);
                    startActivity(grIntent);
                    break;

                case "4":
                    Intent gzIntent = new Intent(getActivity(), GZAcceptSuccessActivity.class);
                    gzIntent.putExtra("taskId", taskId);
                    startActivity(gzIntent);
                    break;
            }
        } else {
            switch (taskType) {
                case "1":
                    Intent ptIntent = new Intent(getActivity(), PTTaskDetailsActivity.class);
                    ptIntent.putExtra("taskId", taskId);
                    startActivity(ptIntent);
                    break;

                case "2":
                case "5":
                case "6":
                    Intent shIntent = new Intent(getActivity(), SHTaskDetailsActivity.class);
                    shIntent.putExtra("taskId", taskId);
                    startActivity(shIntent);
                    break;

                case "3":
                    Intent grIntent = new Intent(getActivity(), GRTaskDetailsActivity.class);
                    grIntent.putExtra("taskId", taskId);
                    startActivity(grIntent);
                    break;

                case "4":
                    Intent gzIntent = new Intent(getActivity(), GZTaskDetailsActivity.class);
                    gzIntent.putExtra("taskId", taskId);
                    startActivity(gzIntent);
                    break;
            }
        }
    }

    @Override
    public void TuiKuanListener() {
        page = 1;
        mList.clear();
        acceptPresenter.getAcceptTaskList(page, state);
    }
}
