package com.power.mercenary.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.power.mercenary.R;
import com.power.mercenary.activity.MyFollowActivity;
import com.power.mercenary.activity.MyValueActivity;
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

    @BindView(R.id.ll_wdjz)
    LinearLayout ll_wdjz;

    @BindView(R.id.rl_wdgz)
    RelativeLayout rl_wdgz;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, null);

        ButterKnife.bind(this, view);
        tvSz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SetupActivity.class);
                startActivity(intent);
            }
        });

        ll_wdjz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), MyValueActivity.class);
                startActivity(intent);

            }
        });

        rl_wdgz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MyFollowActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    protected void initLazyData() {



    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
