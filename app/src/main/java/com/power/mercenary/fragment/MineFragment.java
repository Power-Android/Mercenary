package com.power.mercenary.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.power.mercenary.R;
import com.power.mercenary.activity.SetupActivity;
import com.power.mercenary.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by power on 2018/3/21.
 */

public class MineFragment extends BaseFragment {


    @BindView(R.id.tv_sz)
    TextView tvSz;
    Unbinder unbinder;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, null);

        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    protected void initLazyData() {


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick(R.id.tv_sz)
    public void onViewClicked() {

        Intent intent = new Intent(getActivity(), SetupActivity.class);
        startActivity(intent);

    }
}
