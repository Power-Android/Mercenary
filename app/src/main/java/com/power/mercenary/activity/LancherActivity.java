package com.power.mercenary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.power.mercenary.MainActivity;
import com.power.mercenary.R;
import com.power.mercenary.base.BaseActivity;


/**
 * Created by Administrator on 2017/8/24.
 */

public class LancherActivity extends BaseActivity {
    private Handler mHandler = new Handler();
    private ImageView iv_launcher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_launcher);
        iv_launcher = (ImageView) findViewById(R.id.iv_launcher);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                    Intent intent = new Intent(LancherActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

            }
        }, 1500);
    }


}
