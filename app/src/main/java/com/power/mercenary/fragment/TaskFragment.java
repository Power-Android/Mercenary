package com.power.mercenary.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.power.mercenary.CollectionPeopleBean;
import com.power.mercenary.MyApplication;
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
import com.power.mercenary.adapter.TaskAdapter;
import com.power.mercenary.base.BaseFragment;
import com.power.mercenary.bean.CollectionBean;
import com.power.mercenary.data.EventConstants;
import com.power.mercenary.event.EventUtils;
import com.power.mercenary.presenter.CollectionPresenter;
import com.power.mercenary.view.pullrecyclerview.WanRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/27.
 */

public class TaskFragment extends BaseFragment implements CollectionPresenter.Collection, WanRecyclerView.PullRecyclerViewCallBack {

    @BindView(R.id.mRecycler)
    WanRecyclerView mRecycler;

    ArrayList<String> mList=new ArrayList<>();
    private CollectionPresenter presenter;
    private List<CollectionBean> mData = new ArrayList<>();
    private TaskAdapter changegameAdapter;
    private int page = 1;
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(),R.layout.fragment_ren,null);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        presenter = new CollectionPresenter(getActivity(),this);
        presenter.getCollectionTask();
        initData();
        return view;
    }
    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void RefreshTask(EventUtils eventUtils){
        if (eventUtils.getType()== EventConstants.TYPE_REFRESH_COLLECTION){
            page = 1;
            mData.clear();
            presenter.getCollectionTask();
        }

    }
    private void initData() {
        mRecycler.setPullRecyclerViewListener(this);
        mRecycler.setNestedScrollingEnabled(false);
        mRecycler.setLinearLayout();
        changegameAdapter = new TaskAdapter(R.layout.task_item_view, mData);
        mRecycler.setAdapter(changegameAdapter);
        changegameAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String taskType = mData.get(position).getTask_type();
                String taskId = mData.get(position).getId();
                String taskState = mData.get(position).getTask_status();
                if (TextUtils.equals(taskState, "2")) {
                    switch (taskType) {
                        case "1":
                            Intent ptIntent = new Intent(mContext, PTAcceptInTaskActivity.class);
                            ptIntent.putExtra("taskId", taskId);
                            startActivity(ptIntent);
                            break;

                        case "2":
                        case "5":
                        case "6":
                            Intent shIntent = new Intent(mContext, SHAcceptInTaskActivity.class);
                            shIntent.putExtra("taskId", taskId);
                            startActivity(shIntent);
                            break;

                        case "3":
                            Intent grIntent = new Intent(mContext, GRAcceptInTaskActivity.class);
                            grIntent.putExtra("taskId", taskId);
                            startActivity(grIntent);
                            break;

                        case "4":
                            Intent gzIntent = new Intent(mContext, GZAcceptInTaskActivity.class);
                            gzIntent.putExtra("taskId", taskId);
                            startActivity(gzIntent);
                            break;
                    }

                } else if (TextUtils.equals(taskState, "3")) {
                    switch (taskType) {
                        case "1":
                            Intent ptIntent = new Intent(mContext, PTAcceptAuditActivity.class);
                            ptIntent.putExtra("taskId", taskId);
                            startActivity(ptIntent);
                            break;

                        case "2":
                        case "5":
                        case "6":
                            Intent shIntent = new Intent(mContext, SHAcceptAuditActivity.class);
                            shIntent.putExtra("taskId", taskId);
                            startActivity(shIntent);
                            break;

                        case "3":
                            Intent grIntent = new Intent(mContext, GRAcceptAuditActivity.class);
                            grIntent.putExtra("taskId", taskId);
                            startActivity(grIntent);
                            break;

                        case "4":
                            Intent gzIntent = new Intent(mContext, GZAcceptAuditActivity.class);
                            gzIntent.putExtra("taskId", taskId);
                            startActivity(gzIntent);
                            break;
                    }

                } else if (TextUtils.equals(taskState, "6") || TextUtils.equals(taskState, "7")) {
                    switch (taskType) {
                        case "1":
                            Intent ptIntent = new Intent(mContext, PTAcceptSuccessActivity.class);
                            ptIntent.putExtra("taskId", taskId);
                            startActivity(ptIntent);
                            break;

                        case "2":
                        case "5":
                        case "6":
                            Intent shIntent = new Intent(mContext, SHAcceptSuccessActivity.class);
                            shIntent.putExtra("taskId", taskId);
                            startActivity(shIntent);
                            break;

                        case "3":
                            Intent grIntent = new Intent(mContext, GRAcceptSuccessActivity.class);
                            grIntent.putExtra("taskId", taskId);
                            startActivity(grIntent);
                            break;

                        case "4":
                            Intent gzIntent = new Intent(mContext, GZAcceptSuccessActivity.class);
                            gzIntent.putExtra("taskId", taskId);
                            startActivity(gzIntent);
                            break;
                    }
                } else {
                    switch (taskType) {
                        case "1":
                            Intent ptIntent = new Intent(mContext, PTTaskDetailsActivity.class);
                            ptIntent.putExtra("taskId", taskId);
                            startActivity(ptIntent);
                            break;

                        case "2":
                        case "5":
                        case "6":
                            Intent shIntent = new Intent(mContext, SHTaskDetailsActivity.class);
                            shIntent.putExtra("taskId", taskId);
                            startActivity(shIntent);
                            break;

                        case "3":
                            Intent grIntent = new Intent(mContext, GRTaskDetailsActivity.class);
                            grIntent.putExtra("taskId", taskId);
                            startActivity(grIntent);
                            break;

                        case "4":
                            Intent gzIntent = new Intent(mContext, GZTaskDetailsActivity.class);
                            gzIntent.putExtra("taskId", taskId);
                            startActivity(gzIntent);
                            break;
                    }
                }
            }
        });
    }

    @Override
    protected void initLazyData() {

    }

    @Override
    public void getCollectionTask(List<CollectionBean> response) {
        if (response!=null){
            mData.addAll(response);
            mRecycler.setHasMore(response.size(), 10);
        } else {
            mRecycler.setHasMore(0, 10);
        }
        changegameAdapter.notifyDataSetChanged();
        mRecycler.setStateView(mData.size());
    }

    @Override
    public void getCollectionPeople(List<CollectionPeopleBean> response) {

    }

    @Override
    public void onRefresh() {
        page = 1;
        mData.clear();
        presenter.getCollectionTask();
    }

    @Override
    public void onLoadMore() {
        page++;
        presenter.getCollectionTask();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
