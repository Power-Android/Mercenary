package com.power.mercenary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.model.Response;
import com.power.mercenary.MainActivity;
import com.power.mercenary.R;
import com.power.mercenary.base.BaseActivity;
import com.power.mercenary.bean.user.TokenInfo;
import com.power.mercenary.bean.user.UserInfo;
import com.power.mercenary.data.CacheConstants;
import com.power.mercenary.http.DialogCallback;
import com.power.mercenary.http.HttpManager;
import com.power.mercenary.http.ResponseBean;
import com.power.mercenary.presenter.LoginPresenter;
import com.power.mercenary.presenter.UserPresenter;
import com.power.mercenary.utils.CacheUtils;
import com.power.mercenary.utils.CountDownUtils;
import com.power.mercenary.utils.MyUtils;
import com.power.mercenary.utils.Urls;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imlib.RongIMClient;


/**
 * Created by Administrator on 2018/3/29.
 *
 * 登陆
 */

public class SignInActivity extends BaseActivity implements View.OnClickListener, LoginPresenter.TokenCallBack, UserPresenter.UserCallBack {


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
    @BindView(R.id.tv_dl_wc)
    TextView tvDlWc;

    @BindView(R.id.left_back)
    ImageView left_back;

    @BindView(R.id.title_text)
    TextView title_text;

    @BindView(R.id.tv_cjzh)
    TextView tv_cjzh;
    @BindView(R.id.rl_title_bg)
    RelativeLayout rlTitleBg;
    @BindView(R.id.pass_login)
    LinearLayout passLogin;
    @BindView(R.id.code_login)
    LinearLayout codeLogin;
    @BindView(R.id.et_sign_mm)
    EditText etSignMm;
    @BindView(R.id.iv_dl_yj)
    ImageView ivDlYj;
    @BindView(R.id.tv_mm_wjmm)
    TextView tvMmWjmm;
    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.edt_code)
    EditText edtCode;
    @BindView(R.id.tv_hqyzm)
    TextView tvHqyzm;
    @BindView(R.id.tv_yzm_mm)
    TextView tvYzmMm;
    @BindView(R.id.edt_pass_phone)
    EditText edtPassPhone;
    private boolean isVisible;
    private CountDownUtils countDownUtils;
    private String Tag = "";
    private LoginPresenter presenter;
    private UserPresenter userPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);

        userPresenter = new UserPresenter(this, this);

        title_text.setText("登录");
        renwutjLl.setOnClickListener(this);
        tongchengLl.setOnClickListener(this);
        left_back.setOnClickListener(this);
        tv_cjzh.setOnClickListener(this);
        tvDlWc.setOnClickListener(this);
        presenter = new LoginPresenter(this, this);
        initRenwutj();
        ivDlYj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isVisible) {
                    etSignMm.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    ivDlYj.setImageResource(R.drawable.by_2x);
                } else {//明文
                    etSignMm.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    ivDlYj.setImageResource(R.drawable.yj_2x);
                }
                isVisible = !isVisible;
            }
        });

        tvMmWjmm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ForgetPasswordActivity.class);
                startActivity(intent);

            }
        });
        countDownUtils = new CountDownUtils(1000 * 60, 1000, tvHqyzm);
        tvHqyzm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtPhone.getText().length()<11||edtPhone.getText().equals("")){
                    Toast.makeText(mContext, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                String md5 = MyUtils.getMD5("mobile=" + edtPhone.getText().toString() + Urls.SECRET);

                presenter.getCodeInfo(md5,edtPhone.getText().toString());
                countDownUtils.start();
            }
        });
        tvYzmMm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    //任务推荐Tab
    private void initRenwutj() {
        codeLogin.setVisibility(View.VISIBLE);
        passLogin.setVisibility(View.GONE);
        Tag = "code_login";
        renwutjTv.setTextColor(getResources().getColor(R.color.colorPrimary));
        indicatorRenwutj.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        tongchengTv.setTextColor(getResources().getColor(R.color.textColor));
        indicatorTongcheng.setBackgroundColor(getResources().getColor(R.color.concrete));
    }

    //同城Tab
    private void initTongcheng() {
        codeLogin.setVisibility(View.GONE);
        passLogin.setVisibility(View.VISIBLE);
        Tag = "pass_login";
        renwutjTv.setTextColor(getResources().getColor(R.color.textColor));
        indicatorRenwutj.setBackgroundColor(getResources().getColor(R.color.concrete));
        tongchengTv.setTextColor(getResources().getColor(R.color.colorPrimary));
        indicatorTongcheng.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.renwutj_ll://任务推荐
                initRenwutj();
                break;
            case R.id.tongcheng_ll://同城
                initTongcheng();
                break;
            case R.id.left_back:
                finish();
                break;
            case R.id.tv_cjzh:
                Intent intent = new Intent(SignInActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_dl_wc:
                if (Tag.equals("code_login")) {//验证码登录
                    if (TextUtils.isEmpty(edtPhone.getText().toString())) {
                        Toast.makeText(mContext, "手机号不能为空", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (TextUtils.isEmpty(edtCode.getText().toString())) {
                        Toast.makeText(mContext, "短信验证码不正确", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        String md5 = MyUtils.getMD5("code=" + edtCode.getText().toString() + "mobile=" + edtPhone.getText().toString() + Urls.SECRET);
                        presenter.getCodeLoginInfo(md5, edtPhone.getText().toString(), edtCode.getText().toString());
                        Log.d("验证码登录", md5 + "------");
                    }
                } else if (Tag.equals("pass_login")) {//密码登录
                    if (TextUtils.isEmpty(edtPassPhone.getText().toString())) {
                        Toast.makeText(mContext, "手机号不能为空", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (TextUtils.isEmpty(etSignMm.getText().toString())) {
                        Toast.makeText(mContext, "请输入密码", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        String md5 = MyUtils.getMD5("mobile=" + edtPassPhone.getText().toString() + "pwd=" + etSignMm.getText().toString() + Urls.SECRET);
                        presenter.getPassLoginInfo(md5, edtPassPhone.getText().toString(), etSignMm.getText().toString());
                    }
                }


                break;
        }

    }


    @Override
    public void getTokenInfo(TokenInfo userInfo) {

    }

    @Override
    public void getCodeLoginInfo(TokenInfo userInfo) {
        RongIMClient.connect(userInfo.yun_token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {

            }

            @Override
            public void onSuccess(String s) {
                Log.v("======>>", "RongClound connect! id:" + s);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.v("======>>", "errorCode" + errorCode.getValue() + "-----" + errorCode.getMessage());
            }
        });
        CacheUtils.put(CacheConstants.TYPE_LOGIN, userInfo);
        new HttpManager<ResponseBean<UserInfo>>("Home/UserCenter/getinfo", this)
                .addParams("token", userInfo.token)
                .postRequest(new DialogCallback<ResponseBean<UserInfo>>(this) {
                    @Override
                    public void onSuccess(Response<ResponseBean<UserInfo>> response) {
                        CacheUtils.put(CacheConstants.USERINFO, response.body().data);
                        finish();
                        UserInfo data = response.body().data;
                        if (data.getAge().equals("")||data.getAge()==null||data.getHead_img().equals("")||data.getHead_img()==null
                                ||data.getSex().equals("")||data.getSex()==null||data.getNick_name().equals("")||data.getNick_name()==null
                                ||data.getName().equals("")||data.getName()==null){
                            startActivity(new Intent(SignInActivity.this, CommitUserInfoActivity.class));
                        }else {
                            startActivity(new Intent(SignInActivity.this, MainActivity.class));
                        }
                    }
                });
//        Toast.makeText(this, "验证码登录成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getPassLoginInfo(TokenInfo userInfo) {
        RongIMClient.connect(userInfo.yun_token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {

            }

            @Override
            public void onSuccess(String s) {
                Log.v("======>>", "RongClound connect! id:" + s);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });
        CacheUtils.put(CacheConstants.TYPE_LOGIN, userInfo);
        new HttpManager<ResponseBean<UserInfo>>("Home/UserCenter/getinfo", this)
                .addParams("token", userInfo.token)
                .postRequest(new DialogCallback<ResponseBean<UserInfo>>(this) {
                    @Override
                    public void onSuccess(Response<ResponseBean<UserInfo>> response) {
                        CacheUtils.put(CacheConstants.USERINFO, response.body().data);
                        finish();
                        UserInfo data = response.body().data;
                        if (data.getAge().equals("")||data.getAge()==null||data.getHead_img().equals("")||data.getHead_img()==null
                                ||data.getSex().equals("")||data.getSex()==null||data.getNick_name().equals("")||data.getNick_name()==null
                                ||data.getName().equals("")||data.getName()==null){
                            startActivity(new Intent(SignInActivity.this, CommitUserInfoActivity.class));
                        }else {
                            startActivity(new Intent(SignInActivity.this, MainActivity.class));
                        }
                    }
                });
//        Toast.makeText(this, "密码登录成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getForgetPassInfo(TokenInfo userInfo) {

    }

    @Override
    public void getCodeInfo() {
        countDownUtils.start();
    }

    @Override
    public void getUserInfo(UserInfo userInfo) {

    }
}
