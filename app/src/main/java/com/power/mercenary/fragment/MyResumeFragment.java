package com.power.mercenary.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.power.mercenary.R;
import com.power.mercenary.base.BaseFragment;
import com.power.mercenary.bean.MyRecordBean;
import com.power.mercenary.bean.MyTaskBean;
import com.power.mercenary.bean.PersonalBean;
import com.power.mercenary.presenter.PersonalPresenter;
import com.power.mercenary.utils.Urls;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/3/30.
 */

public class MyResumeFragment extends BaseFragment implements PersonalPresenter.PersonalCallBack {


    @BindView(R.id.frag_hangye)
    TextView fragHangye;
    @BindView(R.id.frag_techang)
    TextView fragTechang;
    @BindView(R.id.frag_nianxian)
    TextView fragNianxian;
    @BindView(R.id.frag_my_show)
    TextView fragMyShow;
    @BindView(R.id.frag_my_shu)
    ImageView fragMyShu;
    Unbinder unbinder;
    private PersonalPresenter presenter;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = View.inflate(getActivity(), R.layout.fragment_my_resume, null);

        ButterKnife.bind(this, view);

        String userId = getArguments().getString("userId");

        presenter = new PersonalPresenter(getActivity(), this);
        presenter.getMyRecord(userId);

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
        if (data != null) {
            fragHangye.setText(data.getIndustry());

            fragTechang.setText(data.getSpeciality());

            fragNianxian.setText(data.getWorkyear());

            fragMyShow.setText(data.getIntroduce());

            Glide.with(mContext)
                    .load(Urls.BASEIMGURL + data.getCertificate())
                    .into(fragMyShu);
        }
    }

    @Override
    public void getMyTask(MyTaskBean data) {

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

    @OnClick({R.id.frag_hangye, R.id.frag_techang, R.id.frag_nianxian, R.id.frag_my_show, R.id.frag_my_shu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.frag_hangye:
                break;
            case R.id.frag_techang:
                break;
            case R.id.frag_nianxian:
                break;
            case R.id.frag_my_show:
                break;
            case R.id.frag_my_shu:
                break;
        }
    }
}
