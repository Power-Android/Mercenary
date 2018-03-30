package com.power.mercenary.fragment;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.power.mercenary.R;
import com.power.mercenary.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/29.
 */

public class RegisterQYFragment extends BaseFragment {

    @BindView(R.id.img_wj_yj)
    ImageView img_wj_yj;
    @BindView(R.id.et_wj_mm)
    EditText et_wj_mm;

    private boolean isqh;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = View.inflate(getActivity(), R.layout.fragment_register_gr,null);

        ButterKnife.bind(this,view);
        img_wj_yj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isqh){
                    et_wj_mm.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    img_wj_yj.setImageResource(R.drawable.yj_2x);
                }else{//明文
                    et_wj_mm.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    img_wj_yj.setImageResource(R.drawable.by_2x);
                }
                isqh=!isqh;
            }
        });

        return view;
    }

    @Override
    protected void initLazyData() {

    }
}
