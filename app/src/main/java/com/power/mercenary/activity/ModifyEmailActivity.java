package com.power.mercenary.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.power.mercenary.R;
import com.power.mercenary.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/23.
 */

public class ModifyEmailActivity extends BaseActivity {

    @BindView(R.id.left_back)
    ImageView left_back;

    @BindView(R.id.title_text)
    TextView title_text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_email);

        ButterKnife.bind(this);
        title_text.setText("修改绑定邮箱");
        left_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}