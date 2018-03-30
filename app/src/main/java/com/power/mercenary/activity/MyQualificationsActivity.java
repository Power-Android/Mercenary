package com.power.mercenary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.power.mercenary.R;
import com.power.mercenary.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/28.
 */

public class MyQualificationsActivity extends BaseActivity {


    @BindView(R.id.left_back)
    ImageView left_back;

    @BindView(R.id.title_text)
    TextView title_text;

    @BindView(R.id.ll_gznx)
    LinearLayout ll_gznx;

    @BindView(R.id.ll_hy)
    LinearLayout ll_hy;

    @BindView(R.id.ll_tc)
    LinearLayout ll_tc;

    @BindView(R.id.ll_zwjs)
    LinearLayout ll_zwjs;

    @BindView(R.id.ll_grsmrz)
    LinearLayout ll_grsmrz;

    @BindView(R.id.ll_gezssc)
    LinearLayout ll_gezssc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_qualifications);

        ButterKnife.bind(this);
        title_text.setText("我的资历");

        left_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ll_gznx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyQualificationsActivity.this,GZNXActivity.class);
                startActivity(intent);
            }
        });
        ll_hy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyQualificationsActivity.this,HYActivity.class);
                startActivity(intent);
            }
        });

        ll_tc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyQualificationsActivity.this,TCActivity.class);
                startActivity(intent);
            }
        });

        ll_zwjs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyQualificationsActivity.this,ZWJCActivity.class);
                startActivity(intent);
            }
        });
        ll_grsmrz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyQualificationsActivity.this,PersonalRZActivity.class);
                startActivity(intent);
            }
        });
        ll_gezssc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MyQualificationsActivity.this,CertificateUploadingActivity.class);
                startActivity(intent);

            }
        });

    }
}
