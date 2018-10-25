package com.power.mercenary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.power.mercenary.R;
import com.power.mercenary.base.BaseActivity;
import com.power.mercenary.bean.user.UserInfo;
import com.power.mercenary.data.CacheConstants;
import com.power.mercenary.data.EventConstants;
import com.power.mercenary.event.EventUtils;
import com.power.mercenary.utils.CacheUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2018/3/23.
 *
 * 修改密码
 */

public class SecurityActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.left_back)
    ImageView left_back;
    @BindView(R.id.title_text)
    TextView title_text;

    @BindView(R.id.rl_xgmm)
    RelativeLayout rl_xgmm;
    @BindView(R.id.rl_xgbdsj)
    RelativeLayout rl_xgbdsj;
    @BindView(R.id.rl_xgbdyx)
    RelativeLayout rl_xgbdyx;
    @BindView(R.id.act_secuity_phone)
    TextView tvPhone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);
        ButterKnife.bind(this);
        title_text.setText("账号与安全");
        left_back.setOnClickListener(this);
        rl_xgbdsj.setOnClickListener(this);
        rl_xgbdyx.setOnClickListener(this);
        rl_xgmm.setOnClickListener(this);

        UserInfo userInfo = CacheUtils.get(CacheConstants.USERINFO);
        if (userInfo != null && !TextUtils.isEmpty(userInfo.getMobile())) {
            tvPhone.setText(userInfo.getMobile());
        }

        EventBus.getDefault().register(this);
    }

    @Override
    public void onClick(View view) {

        Intent intent;
        switch (view.getId()) {
            case R.id.left_back:
                finish();
                break;
            case R.id.rl_xgbdsj:

                intent = new Intent(SecurityActivity.this, ModifyPhoneActivity.class);
                startActivity(intent);

                break;
            case R.id.rl_xgbdyx:

                intent = new Intent(SecurityActivity.this, ModifyEmailActivity.class);
                startActivity(intent);

                break;
            case R.id.rl_xgmm:

                intent = new Intent(SecurityActivity.this, ModifyPasswordActivity.class);
                startActivity(intent);

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRecevierEvent(EventUtils event) {
        switch (event.getType()) {
            case EventConstants.TYPE_MODIFY_PHONE:
                tvPhone.setText((String)event.getData());
                break;
        }
    }

}
