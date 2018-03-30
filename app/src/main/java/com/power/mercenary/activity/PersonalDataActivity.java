package com.power.mercenary.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.power.mercenary.R;
import com.power.mercenary.base.BaseActivity;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/29.
 */

public class PersonalDataActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);
        ButterKnife.bind(this);

    }
}
