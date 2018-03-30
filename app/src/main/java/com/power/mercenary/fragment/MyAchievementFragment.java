package com.power.mercenary.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.power.mercenary.R;
import com.power.mercenary.adapter.WCCAdapter;
import com.power.mercenary.adapter.YWCAdapter;
import com.power.mercenary.base.BaseFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/30.
 */

public class MyAchievementFragment extends BaseFragment {

    @BindView(R.id.mRecycler_ywc)
    RecyclerView mRecycler_ywc;
    private YWCAdapter ywcAdapter;
    ArrayList<String> mList=new ArrayList<>();

    @BindView(R.id.mRecycler_wwc)
    RecyclerView mRecycler_wwc;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = View.inflate(getActivity(), R.layout.fragment_my_achievement,null);

        ButterKnife.bind(this,view);

        if (mList.size()<=0){
            mList.add("");
            mList.add("");
            mList.add("");
            mList.add("");
            mList.add("");
        }
        mRecycler_ywc.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycler_ywc.setNestedScrollingEnabled(false);
        YWCAdapter changegameAdapter = new YWCAdapter(R.layout.ma_item_view, mList);
        mRecycler_ywc.setAdapter(changegameAdapter);

        mRecycler_wwc.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycler_wwc.setNestedScrollingEnabled(false);
        WCCAdapter wccAdapter = new WCCAdapter(R.layout.wcc_item_view, mList);
        mRecycler_wwc.setAdapter(wccAdapter);

        return view;
    }

    @Override
    protected void initLazyData() {

    }
}
