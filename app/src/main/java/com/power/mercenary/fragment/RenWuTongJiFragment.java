package com.power.mercenary.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.power.mercenary.R;
import com.power.mercenary.adapter.MyFollowAdapter;
import com.power.mercenary.adapter.RenWuTongJiAdapter;
import com.power.mercenary.base.BaseFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/30.
 */

public class RenWuTongJiFragment extends BaseFragment {


    @BindView(R.id.mRecycler_rwtj)
    RecyclerView mRecycler_rwtj;
    ArrayList<String> mList=new ArrayList<>();

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_renwu_tj,null);
        ButterKnife.bind(this,view);


        if (mList.size()<=0){
            mList.add("");
            mList.add("");
            mList.add("");
        }
        mRecycler_rwtj.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycler_rwtj.setNestedScrollingEnabled(false);
        RenWuTongJiAdapter changegameAdapter = new RenWuTongJiAdapter(R.layout.rw_tj_item_view, mList);
        mRecycler_rwtj.setAdapter(changegameAdapter);

        return view;
    }

    @Override
    protected void initLazyData() {

    }
}
