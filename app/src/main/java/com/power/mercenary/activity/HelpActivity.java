package com.power.mercenary.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.power.mercenary.R;
import com.power.mercenary.base.BaseActivity;
import com.power.mercenary.utils.TUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/3/22.
 * 帮助
 */

public class HelpActivity extends BaseActivity {
    @BindView(R.id.left_back)
    ImageView left_back;

    @BindView(R.id.title_text)
    TextView title_text;
    @BindView(R.id.rl_wtfk)
    RelativeLayout rlWtfk;
    @BindView(R.id.rl_jcgx)
    RelativeLayout rlJcgx;
    @BindView(R.id.rl_yhsz)
    RelativeLayout rlYhsz;
    @BindView(R.id.rl_tgsz)
    RelativeLayout rlTgsz;
    @BindView(R.id.rl_gywm)
    RelativeLayout rlGywm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_help);
        ButterKnife.bind(this);
        title_text.setText("帮助");
        left_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @OnClick({R.id.rl_wtfk, R.id.rl_jcgx, R.id.rl_yhsz, R.id.rl_tgsz, R.id.rl_gywm})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.rl_wtfk:
                intent = new Intent(HelpActivity.this, ProblemFeedbackActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_jcgx:
                try {
                    String verName = getPackageManager().
                            getPackageInfo(getPackageName(), 0).versionName;

//                    if (TextUtils.equals(verName, response.body().getDatas().getVersionNumber())) {
//                        TUtils.showFail(SettingActivity.this, getString(R.string.max_version));
//                    } else{
                        String mAddress = "market://details?id=" + getPackageName();
                        Intent marketIntent = new Intent("android.intent.action.VIEW");
                        marketIntent.setData(Uri.parse(mAddress));
                        if (marketIntent.resolveActivity(getPackageManager()) != null) { //可以接收
                            startActivity(marketIntent);
                        } else {
                            TUtils.showCustom(HelpActivity.this, "此版本已是最新");
                        }
//                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.rl_yhsz:
                break;
            case R.id.rl_tgsz:
                break;
            case R.id.rl_gywm:
                intent = new Intent(HelpActivity.this, AboutUsActivity.class);
                startActivity(intent);
                break;
        }
    }
}
