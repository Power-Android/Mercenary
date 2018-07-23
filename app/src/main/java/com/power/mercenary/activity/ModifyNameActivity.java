package com.power.mercenary.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/3/23.
 */

public class ModifyNameActivity extends BaseActivity implements UpdataPresenter.UpdataCallBack{

    @BindView(R.id.left_back)
    ImageView left_back;
    @BindView(R.id.rigth_text)
    TextView rigthText;
    @BindView(R.id.et_xg_name)
    EditText etXgName;

    private UserInfo userInfo;
    private UpdataPresenter presenter;

    private boolean isSave = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_name);
        ButterKnife.bind(this);

        userInfo = CacheUtils.get(CacheConstants.USERINFO);

        left_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

        presenter = new UpdataPresenter(this, this);

        etXgName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.toString().replace(" ", "").length() >= 4 && s.toString().replace(" ", "").length() <= 20) {
                    isSave = true;
                } else {
                    isSave = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick(R.id.rigth_text)
    public void onViewClicked() {
        if (!TextUtils.isEmpty(etXgName.getText().toString().replace(" ", "")) && isSave) {

            presenter.updataUserInfo(etXgName.getText().toString().replace(" ", ""),
                    userInfo.getName(),
                    userInfo.getAge(),
                    Integer.parseInt(userInfo.getSex()),
                    userInfo.getMail());
        } else if (!isSave) {
            TUtils.showCustom(ModifyNameActivity.this, "请输入昵称4-20个字符");
        } else {
            TUtils.showCustom(ModifyNameActivity.this, "请输入昵称");
        }
    }

    @Override
    public void updataUserImg(UserImgInfo imgInfo) {

    }

    @Override
    public void updataSuccess() {
        userInfo.setNick_name(etXgName.getText().toString().replace(" ", ""));
        EventBus.getDefault().post(new EventUtils(EventConstants.TYPE_USERINFO));
        EventBus.getDefault().post(new EventUtils(EventConstants.TYPE_SETT_REFRESH));
        finish();
    }
}
