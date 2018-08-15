package com.power.mercenary.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.power.mercenary.R;
import com.power.mercenary.base.BaseActivity;
import com.power.mercenary.bean.user.TokenInfo;
import com.power.mercenary.presenter.AccountPresenter;
import com.power.mercenary.utils.CountDownUtils;
import com.power.mercenary.utils.TUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/23.
 */

public class ModifyPasswordActivity extends BaseActivity implements AccountPresenter.AccountCallBack {

    @BindView(R.id.left_back)
    ImageView left_back;
    @BindView(R.id.title_text)
    TextView title_text;
    @BindView(R.id.tv_bdmm_tcdl)
    TextView submit;
    @BindView(R.id.tv_hqyzm)
    TextView tv_hqyzm;

    @BindView(R.id.img_yj)
    ImageView img_yj;
    @BindView(R.id.et_xg_mm)
    EditText et_xg_mm;
    @BindView(R.id.act_modify_password_phone)
    EditText phone;
    @BindView(R.id.act_modify_password_code)
    EditText code;

    private boolean isqh;

    private AccountPresenter presenter;
    private CountDownUtils countDownUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_modify_password);
        ButterKnife.bind(this);

        presenter = new AccountPresenter(this, this);

        title_text.setText("修改密码");
        left_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        img_yj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isqh) {
                    et_xg_mm.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    img_yj.setImageResource(R.drawable.yj_2x);
                } else {//明文
                    et_xg_mm.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    img_yj.setImageResource(R.drawable.by_2x);
                }
                isqh = !isqh;
            }
        });

        countDownUtils = new CountDownUtils(1000*60,1000,tv_hqyzm);

        tv_hqyzm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(phone.getText().toString())) {
                    countDownUtils.start();
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(phone.getText().toString())) {
                    TUtils.showCustom(ModifyPasswordActivity.this, "请输入手机号");
                } else if (TextUtils.isEmpty(code.getText().toString())) {
                    TUtils.showCustom(ModifyPasswordActivity.this, "请输入验证码");
                } else if (TextUtils.isEmpty(et_xg_mm.getText().toString())) {
                    TUtils.showCustom(ModifyPasswordActivity.this, "请输入密码");
                } else {
                    presenter.changePassword(phone.getText().toString(), et_xg_mm.getText().toString(), code.getText().toString());
                }
            }
        });
    }

    @Override
    public void changePassword(TokenInfo tokenInfo) {
//        CacheUtils.put(CacheConstants.TYPE_LOGIN, tokenInfo);
//        finish();
    }

    @Override
    public void changePhone(TokenInfo tokenInfo) {

    }
}
