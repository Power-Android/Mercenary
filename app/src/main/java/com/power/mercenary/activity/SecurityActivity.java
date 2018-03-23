package com.power.mercenary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.power.mercenary.R;
import com.power.mercenary.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/23.
 */

public class SecurityActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.left_back)
    ImageView left_back;
    @BindView(R.id.title_text)
    TextView title_text;

    @BindView(R.id.rl_xgbdsj)
    RelativeLayout rl_xgbdsj;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);
        ButterKnife.bind(this);
        title_text.setText("账号与安全");
        left_back.setOnClickListener(this);
        rl_xgbdsj.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        Intent intent;
        switch (view.getId()){
            case R.id.left_back:
                finish();
                break;
            case R.id.rl_xgbdsj:

                intent = new Intent(SecurityActivity.this,ModifyPhoneActivity.class);
                startActivity(intent);

                break;
        }

    }
}
