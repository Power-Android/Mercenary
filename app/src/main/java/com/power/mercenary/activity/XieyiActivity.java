package com.power.mercenary.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.power.mercenary.R;
import com.power.mercenary.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class XieyiActivity extends BaseActivity {

    @BindView(R.id.left_back)
    ImageView leftBack;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.rl_title_bg)
    RelativeLayout rlTitleBg;
    @BindView(R.id.activity_xieyi)
    LinearLayout activityXieyi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xieyi);
        ButterKnife.bind(this);
        titleText.setText("用户协议");
    }

    @OnClick(R.id.left_back)
    public void onClick() {
        finish();
    }
}
