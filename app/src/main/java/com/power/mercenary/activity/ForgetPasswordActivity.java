package com.power.mercenary.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.power.mercenary.R;
import com.power.mercenary.base.BaseActivity;
import com.power.mercenary.bean.user.TokenInfo;
import com.power.mercenary.presenter.LoginPresenter;
import com.power.mercenary.utils.CountDownUtils;
import com.power.mercenary.utils.MyUtils;
import com.power.mercenary.utils.Urls;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/3/29.
 */

public class ForgetPasswordActivity extends BaseActivity implements LoginPresenter.TokenCallBack {

    @BindView(R.id.left_back)
    ImageView left_back;
    @BindView(R.id.title_text)
    TextView title_text;

    @BindView(R.id.img_wj_yj)
    ImageView img_wj_yj;
    @BindView(R.id.et_wj_mm)
    EditText et_wj_mm;
    @BindView(R.id.rl_title_bg)
    RelativeLayout rlTitleBg;
    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.edt_code)
    EditText edtCode;
    @BindView(R.id.tv_hqyzm)
    TextView tvHqyzm;
    @BindView(R.id.tv_wjmm_tcdl)
    TextView tvWjmmTcdl;

    private boolean isqh;
    private LoginPresenter presenter;
    private CountDownUtils countDownUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);
        presenter = new LoginPresenter(this, this);
        countDownUtils = new CountDownUtils(1000 * 60, 1000, tvHqyzm);
        title_text.setText("忘记密码");
        tvWjmmTcdl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ForgetPassNet();
            }
        });
        left_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        img_wj_yj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isqh) {
                    et_wj_mm.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    img_wj_yj.setImageResource(R.drawable.yj_2x);
                } else {//明文
                    et_wj_mm.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    img_wj_yj.setImageResource(R.drawable.by_2x);
                }
                isqh = !isqh;
            }
        });


    }

    private void ForgetPassNet() {
        if (TextUtils.isEmpty(edtPhone.getText().toString())) {
            Toast.makeText(mContext, "手机号不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(edtCode.getText().toString())) {
            Toast.makeText(mContext, "短信验证码不正确", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(et_wj_mm.getText().toString())) {
            Toast.makeText(mContext, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else {
            String md5 = MyUtils.getMD5("code=" + edtCode.getText().toString() + "mobile=" + edtPhone.getText().toString() + "pwd=" + et_wj_mm.getText().toString() + Urls.SECRET);
            Log.d("RegisterActivityMD5", md5 + "------");
            presenter.ForgetPassrInfo(md5, "1234", edtPhone.getText().toString(), et_wj_mm.getText().toString());
        }
    }

    @Override
    public void getTokenInfo(TokenInfo userInfo) {

    }

    @Override
    public void getCodeLoginInfo(TokenInfo userInfo) {

    }

    @Override
    public void getPassLoginInfo(TokenInfo userInfo) {

    }

    @Override
    public void getForgetPassInfo(TokenInfo userInfo) {
        Toast.makeText(mContext, "重置成功", Toast.LENGTH_SHORT).show();
        finish();
    }

    @OnClick(R.id.tv_hqyzm)
    public void onClick() {
        if (edtPhone.getText().length() < 11 || edtPhone.getText().equals("")) {
            Toast.makeText(mContext, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        countDownUtils.start();
    }
}
