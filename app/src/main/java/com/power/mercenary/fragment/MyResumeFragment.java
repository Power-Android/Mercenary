package com.power.mercenary.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.power.mercenary.R;
import com.power.mercenary.base.BaseFragment;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/30.
 */

public class MyResumeFragment extends BaseFragment {



    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = View.inflate(getActivity(), R.layout.fragment_my_resume,null);

        ButterKnife.bind(this,view);

        return view;
    }

    @Override
    protected void initLazyData() {

    }
}
