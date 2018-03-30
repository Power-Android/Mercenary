package com.power.mercenary.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.power.mercenary.R;
import com.power.mercenary.activity.ForgetPasswordActivity;
import com.power.mercenary.base.BaseFragment;
import com.power.mercenary.utils.CountDownUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/29.
 */

public class SignInYZMFragment extends BaseFragment {

    @BindView(R.id.tv_hqyzm)
    TextView tv_hqyzm;

    @BindView(R.id.tv_yzm_mm)
    TextView tv_yzm_mm;

    private CountDownUtils countDownUtils;
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view  =View.inflate(getActivity(), R.layout.fragment_yzm_phone,null);
        ButterKnife.bind(this,view);

        countDownUtils = new CountDownUtils(1000*60,1000,tv_hqyzm);
        tv_hqyzm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countDownUtils.start();
            }
        });
        tv_yzm_mm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    protected void initLazyData() {

    }
}
