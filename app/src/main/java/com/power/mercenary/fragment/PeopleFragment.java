package com.power.mercenary.fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.power.mercenary.CollectionPeopleBean;
import com.power.mercenary.R;
import com.power.mercenary.base.BaseFragment;
import com.power.mercenary.bean.CollectionBean;
import com.power.mercenary.presenter.CollectionPresenter;
import com.power.mercenary.utils.Urls;
import com.power.mercenary.view.CircleImageView;
import com.power.mercenary.view.pullrecyclerview.WanRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/27.
 */

public class PeopleFragment extends BaseFragment implements WanRecyclerView.PullRecyclerViewCallBack, CollectionPresenter.Collection {


    @BindView(R.id.mRecycler)
    WanRecyclerView mRecycler;


    ArrayList<String> mList = new ArrayList<>();
    private MyFollowAdapter changegameAdapter;
    private CollectionPresenter presenter;
    private List<CollectionPeopleBean> mData = new ArrayList<>();
    private int page = 1;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = View.inflate(getActivity(), R.layout.fragment_ren, null);
        ButterKnife.bind(this, view);
        presenter = new CollectionPresenter(getActivity(),this);
        presenter.getCollectionPeople();
        initData();
        return view;
    }
    private void initData() {
        mRecycler.setPullRecyclerViewListener(this);
        mRecycler.setNestedScrollingEnabled(false);
        mRecycler.setLinearLayout();
        changegameAdapter = new MyFollowAdapter(R.layout.myfollow_item_view, mData);
        mRecycler.setAdapter(changegameAdapter);
    }
    @Override
    protected void initLazyData() {

    }

    @Override
    public void onRefresh() {
        page = 1;
        mData.clear();
        presenter.getCollectionPeople();
    }

    @Override
    public void onLoadMore() {
        page++;
        presenter.getCollectionPeople();
    }

    @Override
    public void getCollectionTask(List<CollectionBean> response) {

    }

    @Override
    public void getCollectionPeople(List<CollectionPeopleBean> response) {
        if (response!=null){
            mData.addAll(response);
            mRecycler.setHasMore(response.size(), 10);
        } else {
            mRecycler.setHasMore(0, 10);
        }
        changegameAdapter.notifyDataSetChanged();
        mRecycler.setStateView(mData.size());
    }
    public class MyFollowAdapter extends BaseQuickAdapter<CollectionPeopleBean, BaseViewHolder> {

        public MyFollowAdapter(@LayoutRes int layoutResId, @Nullable List<CollectionPeopleBean> data) {
            super(layoutResId, data);

        }

        @Override
        protected void convert(BaseViewHolder helper, CollectionPeopleBean item) {
            Glide.with(mContext).load(Urls.BASEIMGURL+item.getHead_img()).into((CircleImageView) helper.getView(R.id.img_collction_head));
            helper.setText(R.id.tv_collction_name,item.getName());
        }
    }
}