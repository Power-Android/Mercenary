package com.power.mercenary.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.power.mercenary.R;
import com.power.mercenary.activity.ForgetPasswordActivity;
import com.power.mercenary.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/29.
 */

public class SignInMMFragment extends BaseFragment {

@BindView(R.id.iv_dl_yj)
    ImageView iv_dl_yj;

    @BindView(R.id.et_sign_mm)
    EditText et_sign_mm;
    @BindView(R.id.tv_mm_wjmm)
    TextView tv_mm_wjmm;

    private boolean isqh;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = View.inflate(getActivity(), R.layout.fragment_sign_in_mm,null);
        ButterKnife.bind(this,view);
        iv_dl_yj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isqh){
                    et_sign_mm.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    iv_dl_yj.setImageResource(R.drawable.by_2x);
                }else{//明文
                    et_sign_mm.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    iv_dl_yj.setImageResource(R.drawable.yj_2x);
                }
                isqh=!isqh;
            }
        });

        tv_mm_wjmm.setOnClickListener(new View.OnClickListener() {
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
