package com.power.mercenary.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.power.mercenary.R;
import com.power.mercenary.base.BaseFragment;
import com.power.mercenary.bean.MyRecordBean;
import com.power.mercenary.bean.MyTaskBean;
import com.power.mercenary.bean.PersonalBean;
import com.power.mercenary.presenter.PersonalPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/3/30.
 */

public class RenWuTongJiFragment extends BaseFragment implements PersonalPresenter.PersonalCallBack {

    @BindView(R.id.act_task_starNum1)
    TextView starNum1;
    @BindView(R.id.act_task_starNum2)
    TextView starNum2;
    @BindView(R.id.act_task_starNum3)
    TextView starNum3;
    @BindView(R.id.act_task_starNum4)
    TextView starNum4;
    @BindView(R.id.act_task_starNum5)
    TextView starNum5;
    @BindView(R.id.act_task_starNum)
    TextView starNum;
    @BindView(R.id.act_task_finish_starNum1)
    TextView finishStarNum1;
    @BindView(R.id.act_task_finish_starNum2)
    TextView finishStarNum2;
    @BindView(R.id.act_task_finish_starNum3)
    TextView finishStarNum3;
    @BindView(R.id.act_task_finish_starNum4)
    TextView finishStarNum4;
    @BindView(R.id.act_task_finish_starNum5)
    TextView finishStarNum5;
    @BindView(R.id.act_task_finish_starNum)
    TextView finishStarNum;
    Unbinder unbinder;
    private PersonalPresenter presenter;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_renwu_tj, null);
        ButterKnife.bind(this, view);

        String userId = getArguments().getString("userId");

        presenter = new PersonalPresenter(getActivity(), this);
        presenter.getMyTask(userId);

        return view;
    }

    @Override
    protected void initLazyData() {

    }

    @Override
    public void getPersonalData(PersonalBean data) {

    }

    @Override
    public void getMyRecord(MyRecordBean data) {

    }

    @Override
    public void getMyTask(MyTaskBean data) {
        if (data != null) {
            if (data.getPublish() != null) {
                starNum1.setText(data.getPublish().getOne() + "");
                starNum2.setText(data.getPublish().getTow() + "");
                starNum3.setText(data.getPublish().getThree() + "");
                starNum4.setText(data.getPublish().getFore() + "");
                starNum5.setText(data.getPublish().getFive() + "");
                starNum.setText(data.getPublish().getTotal() + "");
            }

            if (data.getReceive() != null) {
                finishStarNum1.setText(data.getReceive().getOne() + "");
                finishStarNum2.setText(data.getReceive().getTow() + "");
                finishStarNum3.setText(data.getReceive().getThree() + "");
                finishStarNum4.setText(data.getReceive().getFore() + "");
                finishStarNum5.setText(data.getReceive().getFive() + "");
                finishStarNum.setText(data.getReceive().getTotal() + "");
            }
        }
    }

    @Override
    public void requestAttention() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
