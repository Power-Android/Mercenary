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

import com.lzy.okgo.model.Response;
import com.power.mercenary.R;
import com.power.mercenary.base.BaseActivity;
import com.power.mercenary.bean.VersionBean;
import com.power.mercenary.http.DialogCallback;
import com.power.mercenary.http.HttpManager;
import com.power.mercenary.http.ResponseBean;
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
                new HttpManager<ResponseBean<VersionBean>>("", this)
                        .postRequest(new DialogCallback<ResponseBean<VersionBean>>(HelpActivity.this) {
                            @Override
                            public void onSuccess(Response<ResponseBean<VersionBean>> response) {
                                if (response.body().data != null) {
                                    String version = response.body().data.version;
                                    try {
                                        String verName = getPackageManager().
                                                getPackageInfo(getPackageName(), 0).versionName;

                                        if (TextUtils.equals(verName, version)) {
                                            TUtils.showCustom(HelpActivity.this, "此版本已是最新");
                                        } else {
                                            String mAddress = "market://details?id=" + getPackageName();
                                            Intent marketIntent = new Intent("android.intent.action.VIEW");
                                            marketIntent.setData(Uri.parse(mAddress));
                                            if (marketIntent.resolveActivity(getPackageManager()) != null) { //可以接收
                                                startActivity(marketIntent);
                                            } else {
                                                TUtils.showCustom(HelpActivity.this, "您的手机上没有安装应用市场");
                                            }
                                        }
                                    } catch (PackageManager.NameNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });

                break;
            case R.id.rl_yhsz:
                intent = new Intent(HelpActivity.this, UserShouzeActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_tgsz:
                intent = new Intent(HelpActivity.this, TuiGuangActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_gywm:
                intent = new Intent(HelpActivity.this, AboutUsActivity.class);
                startActivity(intent);
                break;
        }
    }
}
