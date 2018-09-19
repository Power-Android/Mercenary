package com.power.mercenary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

/**
 * Created by Administrator on 2018/3/29.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener, LoginPresenter.TokenCallBack {

    @BindView(R.id.renwutj_tv)
    TextView renwutjTv;
    @BindView(R.id.indicator_renwutj)
    View indicatorRenwutj;
    @BindView(R.id.renwutj_ll)
    LinearLayout renwutjLl;
    @BindView(R.id.tongcheng_tv)
    TextView tongchengTv;
    @BindView(R.id.indicator_tongcheng)
    View indicatorTongcheng;
    @BindView(R.id.tongcheng_ll)
    LinearLayout tongchengLl;
    @BindView(R.id.tuijian_tab_ll)
    LinearLayout tuijianTabLl;

    @BindView(R.id.left_back)
    ImageView left_back;

    @BindView(R.id.title_text)
    TextView title_text;

    @BindView(R.id.tv_ydty)
    TextView tv_ydty;
    @BindView(R.id.rl_title_bg)
    RelativeLayout rlTitleBg;
    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.edt_code)
    EditText edtCode;
    @BindView(R.id.tv_hqyzm)
    TextView tvHqyzm;
    @BindView(R.id.edt_new_pass)
    EditText edtNewPass;
    @BindView(R.id.img_wj_yj)
    ImageView imgWjYj;
    @BindView(R.id.cb_ty)
    CheckBox cb_ty;

    @BindView(R.id.tv_zc_wc)
    TextView tv_zc_wc;
    @BindView(R.id.tv_people_xieyi)
    TextView tvPeopleXieyi;
    private LoginPresenter presenter;
    private boolean isyt;
    String loginType = "";

    private CountDownUtils countDownUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        title_text.setText("注册");
        renwutjLl.setOnClickListener(this);
        tongchengLl.setOnClickListener(this);
        tv_zc_wc.setOnClickListener(this);
        left_back.setOnClickListener(this);
        tv_ydty.setOnClickListener(this);
        tvPeopleXieyi.setOnClickListener(this);
        tvHqyzm.setOnClickListener(this);
        presenter = new LoginPresenter(this, this);
        countDownUtils = new CountDownUtils(1000 * 60, 1000, tvHqyzm);
        initRenwutj();
    }

    //任务推荐Tab
    private void initRenwutj() {
        renwutjTv.setTextColor(getResources().getColor(R.color.colorPrimary));
        indicatorRenwutj.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        tongchengTv.setTextColor(getResources().getColor(R.color.textColor));
        indicatorTongcheng.setBackgroundColor(getResources().getColor(R.color.concrete));
        tv_zc_wc.setText("完成");
    }

    //同城Tab
    private void initTongcheng() {
        renwutjTv.setTextColor(getResources().getColor(R.color.textColor));
        indicatorRenwutj.setBackgroundColor(getResources().getColor(R.color.concrete));
        tongchengTv.setTextColor(getResources().getColor(R.color.colorPrimary));
        indicatorTongcheng.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        tv_zc_wc.setText("下一步");
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.renwutj_ll:
                initRenwutj();
                break;
            case R.id.tv_people_xieyi:
                startActivity(new Intent(this,XieyiActivity.class));
                break;
            case R.id.tv_hqyzm:
                if (edtPhone.getText().length() < 11 || edtPhone.getText().equals("")) {
                    Toast.makeText(mContext, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                countDownUtils.start();
                break;
            case R.id.tongcheng_ll:
                initTongcheng();
                break;
            case R.id.left_back:
                finish();
                break;
            case R.id.tv_zc_wc:

                if (tv_zc_wc.getText().equals("完成")) { //个人
                    loginType = "0";
                    RegisterNet(loginType);
                } else if (tv_zc_wc.getText().equals("下一步")) {//企业
                    loginType = "1";
                    RegisterNet(loginType);
                }
                break;
            case R.id.tv_ydty:
                if (isyt) {
                    cb_ty.setChecked(true);
                } else {
                    cb_ty.setChecked(false);
                }
                isyt = !isyt;
                break;
        }

    }

    private void RegisterNet(String loginType) {
        if (TextUtils.isEmpty(edtPhone.getText().toString())) {
            Toast.makeText(mContext, "手机号不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(edtCode.getText().toString())) {
            Toast.makeText(mContext, "短信验证码不正确", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(edtNewPass.getText().toString())) {
            Toast.makeText(mContext, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else {
            String md5 = MyUtils.getMD5("code=" + edtCode.getText().toString() + "mobile=" + edtPhone.getText().toString() + "pwd=" + edtNewPass.getText().toString() + "user_type=" + loginType + Urls.SECRET);
            Log.d("RegisterActivityMD5", md5 + "------");
            presenter.getUserInfo(md5, edtCode.getText().toString(), edtPhone.getText().toString(), "0", edtNewPass.getText().toString());
        }
    }


    @Override
    public void getTokenInfo(TokenInfo userInfo) {
        if (loginType.equals("0")) {
            Toast.makeText(mContext, "注册成功", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(mContext, "企业注册成功", Toast.LENGTH_SHORT).show();
            finish();
        }
        Log.d("RegisterActivityToken", userInfo.token + "--------");
    }

    @Override
    public void getCodeLoginInfo(TokenInfo userInfo) {

    }

    @Override
    public void getPassLoginInfo(TokenInfo userInfo) {

    }

    @Override
    public void getForgetPassInfo(TokenInfo userInfo) {

    }

    @Override
    public void getCodeInfo() {

    }

}
