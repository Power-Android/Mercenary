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
import com.power.mercenary.activity.MyAchievementActivity;
import com.power.mercenary.activity.MyCollection;
import com.power.mercenary.activity.MyExtensionActivity;
import com.power.mercenary.activity.MyFollowActivity;
import com.power.mercenary.activity.MyQualificationsActivity;
import com.power.mercenary.activity.MyValueActivity;
import com.power.mercenary.activity.ReleaseTaskActivity;
import com.power.mercenary.activity.SetupActivity;
import com.power.mercenary.activity.TaskStatisticsActivity;
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

    @BindView(R.id.rl_wdsc)
    RelativeLayout rl_wdsc;

    @BindView(R.id.rl_wdtg)
    RelativeLayout rl_wdtg;

    @BindView(R.id.rl_wdzl)
    RelativeLayout rl_wdzl;

    @BindView(R.id.tv_jsrw)
    TextView tv_jsrw;

    @BindView(R.id.tv_fbrw)
    TextView tv_fbrw;
    @BindView(R.id.rl_cj)
    RelativeLayout rl_cj;

    @BindView(R.id.ll_wjd)
    LinearLayout ll_wjd;
    @BindView(R.id.ll_rwz)
    LinearLayout ll_rwz;
    @BindView(R.id.ll_shz)
    LinearLayout ll_shz;
    @BindView(R.id.ll_dpj)
    LinearLayout ll_dpj;

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

        rl_wdsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), MyCollection.class);
                startActivity(intent);

            }
        });

        rl_wdtg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), MyExtensionActivity.class);
                startActivity(intent);

            }
        });

        rl_wdzl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(getActivity(), MyQualificationsActivity.class);
                startActivity(intent);



            }
        });
        tv_jsrw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), TaskStatisticsActivity.class);
                intent.putExtra("title","接收任务统计");
                startActivity(intent);

            }
        });
        tv_fbrw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TaskStatisticsActivity.class);
                intent.putExtra("title","发布任务统计");
                startActivity(intent);
            }
        });
        rl_cj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), MyAchievementActivity.class);
                startActivity(intent);

            }
        });
        ll_wjd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ReleaseTaskActivity.class);
                startActivity(intent);
            }
        });

        ll_rwz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ReleaseTaskActivity.class);
                startActivity(intent);
            }
        });
        ll_shz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ReleaseTaskActivity.class);
                startActivity(intent);
            }
        });

        ll_dpj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ReleaseTaskActivity.class);
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
