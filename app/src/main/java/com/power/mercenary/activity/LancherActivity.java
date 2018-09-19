package com.power.mercenary.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
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

//    private void go() {
//        OkGo.get(MyContants.BASEURL+"Startpage/startPage")
//                .tag(LancherActivity.this)
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(String s, Call call, Response response) {
//                        Gson gson = new Gson();
//                        LauncherBean launcherBean = new Gson().fromJson(s, LauncherBean.class);
//                        if (launcherBean != null && launcherBean.getDatas() != null && launcherBean.getDatas().getImg1() != null) {
//                            String img3 = launcherBean.getDatas().getImg1();
//                            Glide.with(LancherActivity.this).load(img3)
//                                    .into(iv_launcher);
//                        }
//
//                        mHandler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                boolean guide = SpUtils.getBoolean(LancherActivity.this, "guide", false);
//                                if (!guide) {
//                                    Intent intent = new Intent(LancherActivity.this, GuidePageActivity.class);
//                                    startActivity(intent);
//                                    finish();
//
//                                } else {
//                                    Intent intent = new Intent(LancherActivity.this, MainActivity.class);
//                                    startActivity(intent);
//                                    finish();
//
//                                }
//                            }
//                        }, 1500);
//                    }
//
//                    @Override
//                    public void onError(Call call, Response response, Exception e) {
//                        Toast.makeText(LancherActivity.this, "请检查网络或重试", Toast.LENGTH_SHORT).show();
//                        Log.e("请求失败", "失败原因：" + response);
//                    }
//                });
//    }
}
