package com.power.mercenary.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.power.mercenary.R;
import com.power.mercenary.adapter.ReleaseDPJAdapter;
import com.power.mercenary.adapter.ReleaseRWZAdapter;
import com.power.mercenary.adapter.task.ReleaseWJDAdapter;
import com.power.mercenary.base.BaseFragment;
import com.power.mercenary.bean.mytask.PublishTaskBean;
import com.power.mercenary.presenter.publish.PublishPresenter;
import com.power.mercenary.view.pullrecyclerview.WanRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/30.
 */

public class ReleaseRWZFragment extends BaseFragment implements PublishPresenter.PublishCallBack, WanRecyclerView.PullRecyclerViewCallBack {


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
        publishPresenter.getPublishTaskList(page, 2);
    }

    @Override
    public void onLoadMore() {
        page ++;
        publishPresenter.getPublishTaskList(page, 2);
    }
}
