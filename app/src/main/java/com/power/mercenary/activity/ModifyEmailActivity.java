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
import com.power.mercenary.bean.user.UserImgInfo;
import com.power.mercenary.bean.user.UserInfo;
import com.power.mercenary.data.CacheConstants;
import com.power.mercenary.data.EventConstants;
import com.power.mercenary.event.EventUtils;
import com.power.mercenary.presenter.UpdataPresenter;
import com.power.mercenary.utils.CacheUtils;
import com.power.mercenary.utils.TUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/23.
 */

public class ModifyEmailActivity extends BaseActivity implements UpdataPresenter.UpdataCallBack {

    @BindView(R.id.left_back)
    ImageView left_back;

    @BindView(R.id.title_text)
    TextView title_text;

    @BindView(R.id.tv_bdyx_tcdl)
    TextView tvSubmit;

    @BindView(R.id.act_modifyEmail_et)
    EditText etEmail;

    private UserInfo userInfo;

    private UpdataPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_email);

        ButterKnife.bind(this);

        presenter = new UpdataPresenter(this, this);

        userInfo = CacheUtils.get(CacheConstants.USERINFO);

        title_text.setText("修改绑定邮箱");
        left_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(etEmail.getText().toString())) {

                    if (!isEmail(etEmail.getText().toString())) {
                        TUtils.showCustom(ModifyEmailActivity.this, "请输入正确的邮箱");
                        return;
                    }

                    userInfo.setMail(etEmail.getText().toString());
                    CacheUtils.put(CacheConstants.USERINFO, userInfo);
                    presenter.updataUserInfo(userInfo.getNick_name(), userInfo.getName(), userInfo.getAge(), Integer.parseInt(userInfo.getSex()), etEmail.getText().toString());
                } else {
                    TUtils.showCustom(ModifyEmailActivity.this, "请输入邮箱");
                }
            }
        });
    }

    @Override
    public void updataUserImg(UserImgInfo imgInfo) {

    }

    @Override
    public void updataSuccess() {
        EventBus.getDefault().post(new EventUtils(EventConstants.TYPE_USERINFO));
        finish();
    }

    public boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();
    }
}
