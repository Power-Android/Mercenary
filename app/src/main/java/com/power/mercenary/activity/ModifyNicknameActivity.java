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
import com.power.mercenary.bean.user.UserInfo;
import com.power.mercenary.data.CacheConstants;
import com.power.mercenary.data.EventConstants;
import com.power.mercenary.event.EventUtils;
import com.power.mercenary.utils.CacheUtils;
import com.power.mercenary.utils.TUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * admin  2018/7/5 wan
 */
public class ModifyNicknameActivity extends BaseActivity {


    @BindView(R.id.left_back)
    ImageView back;
    @BindView(R.id.rigth_text)
    TextView save;
    @BindView(R.id.et_xg_name)
    EditText etName;

    private UserInfo userInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_nickname);

        ButterKnife.bind(this);

        userInfo = CacheUtils.get(CacheConstants.USERINFO);
    }

    @OnClick({R.id.left_back, R.id.rigth_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                finish();
                break;
            case R.id.rigth_text:
                if (!TextUtils.isEmpty(etName.getText().toString())) {
                    userInfo.setName(etName.getText().toString());
                    EventBus.getDefault().post(new EventUtils(EventConstants.TYPE_SETT_REFRESH));
                    finish();
                } else {
                    TUtils.showCustom(ModifyNicknameActivity.this, "请输入姓名");
                }
                break;
        }
    }
}
