package com.power.mercenary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.model.Response;
import com.power.mercenary.MyApplication;
import com.power.mercenary.R;
import com.power.mercenary.base.BaseActivity;
import com.power.mercenary.data.EventConstants;
import com.power.mercenary.event.EventUtils;
import com.power.mercenary.http.HttpManager;
import com.power.mercenary.http.JsonCallback;
import com.power.mercenary.http.ResponseBean;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 *
 * 推广守则
 *
 */

public class ShouzeActivity extends BaseActivity {

    @BindView(R.id.left_back)
    ImageView leftBack;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.rl_title_bg)
    RelativeLayout rlTitleBg;
    @BindView(R.id.tv_dl_wc)
    TextView tvDlWc;
    @BindView(R.id.agree_provision_chk)
    CheckBox agreeProvisionChk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shouze);
        ButterKnife.bind(this);
        titleText.setText("推广守则");
    }

    @OnClick({R.id.left_back, R.id.tv_dl_wc})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                finish();
                break;
            case R.id.tv_dl_wc:
                if (agreeProvisionChk.isChecked()){
                    initAgree();
                }else {
                    Toast.makeText(mContext, "请勾选我已阅读推广守则", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    private void initAgree() {
        new HttpManager<ResponseBean>("Home/Promo/isagree", this)
                .addParams("token", MyApplication.getUserToken())
                .addParams("isagree","1")
                .postRequest(new JsonCallback<ResponseBean>() {
                    @Override
                    public void onSuccess(Response<ResponseBean> response) {
//                        Toast.makeText(mContext, response.body().msg, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ShouzeActivity.this, MyExtensionActivity.class);
                        startActivity(intent);
                        EventBus.getDefault().
                                post(new EventUtils(EventConstants.TYPE_USERINFO));
                        finish();
                    }

                    @Override
                    public void onError(Response<ResponseBean> response) {
                        super.onError(response);
                        Toast.makeText(mContext, response.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
