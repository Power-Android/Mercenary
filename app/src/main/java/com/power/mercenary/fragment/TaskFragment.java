package com.power.mercenary.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.power.mercenary.CollectionPeopleBean;
import com.power.mercenary.R;
import com.power.mercenary.adapter.TaskAdapter;
import com.power.mercenary.base.BaseFragment;
import com.power.mercenary.bean.CollectionBean;
import com.power.mercenary.presenter.CollectionPresenter;
import com.power.mercenary.view.pullrecyclerview.WanRecyclerView;

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
        presenter = new CollectionPresenter(getActivity(),this);
        presenter.getCollectionTask();
        initData();
        return view;
    }

    private void initData() {
        mRecycler.setPullRecyclerViewListener(this);
        mRecycler.setNestedScrollingEnabled(false);
        mRecycler.setLinearLayout();
        changegameAdapter = new TaskAdapter(R.layout.task_item_view, mData);
        mRecycler.setAdapter(changegameAdapter);
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
}
