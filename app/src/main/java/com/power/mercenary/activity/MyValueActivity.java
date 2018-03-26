package com.power.mercenary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.power.mercenary.R;
import com.power.mercenary.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/26.
 */

public class MyValueActivity extends BaseActivity {

    @BindView(R.id.left_back)
    ImageView left_back;

    @BindView(R.id.rl_yhk)
    RelativeLayout rl_yhk;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myvalue);
        ButterKnife.bind(this);

        left_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        rl_yhk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MyValueActivity.this,MyBankCardActivity.class);
                startActivity(intent);

            }
        });
    }
}
