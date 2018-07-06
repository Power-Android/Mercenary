package com.power.mercenary.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.power.mercenary.R;
import com.power.mercenary.base.BaseActivity;
import com.power.mercenary.bean.user.TokenInfo;
import com.power.mercenary.bean.user.UserInfo;
import com.power.mercenary.data.CacheConstants;
import com.power.mercenary.data.EventConstants;
import com.power.mercenary.event.EventUtils;
import com.power.mercenary.presenter.AccountPresenter;
import com.power.mercenary.utils.CacheUtils;
import com.power.mercenary.utils.CountDownUtils;
import com.power.mercenary.utils.TUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/23.
 * 修改绑定手机
 */

public class ModifyPhoneActivity extends BaseActivity implements AccountPresenter.AccountCallBack {

    @BindView(R.id.left_back)
    ImageView left_back;

    @BindView(R.id.title_text)
    TextView title_text;

    @BindView(R.id.tv_hqyzm)
    TextView tv_hqyzm;
    @BindView(R.id.act_modify_phone_et)
    EditText etPhone;
    @BindView(R.id.act_modify_phoneCode_et)
    EditText etCode;

    private CountDownUtils countDownUtils;

    private AccountPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_phone);
        ButterKnife.bind(this);

        presenter = new AccountPresenter(this, this);

        title_text.setText("修改绑定手机");
        countDownUtils = new CountDownUtils(1000*60,1000,tv_hqyzm);
        left_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tv_hqyzm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countDownUtils.start();
            }
        });

        findViewById(R.id.tv_bdsj_tcdl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etPhone.getText().toString())) {
                    TUtils.showCustom(ModifyPhoneActivity.this, "请输入手机号");
                } else if (TextUtils.isEmpty(etCode.getText().toString())) {
                    TUtils.showCustom(ModifyPhoneActivity.this, "请输入验证码");
                } else {
                    presenter.changePhone(etPhone.getText().toString(), etCode.getText().toString());
                }
            }
        });
    }

    @Override
    public void changePassword(TokenInfo tokenInfo) {

    }

    @Override
    public void changePhone(TokenInfo tokenInfo) {
        CacheUtils.put(CacheConstants.TYPE_LOGIN, tokenInfo);
        EventBus.getDefault().post(new EventUtils(EventConstants.TYPE_MODIFY_PHONE, etPhone.getText().toString()));
        EventBus.getDefault().post(new EventUtils(EventConstants.TYPE_USERINFO));
        finish();
    }
}
