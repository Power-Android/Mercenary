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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * admin  2018/7/5 wan
 */
public class ModifyNicknameActivity extends BaseActivity implements UpdataPresenter.UpdataCallBack {


    @BindView(R.id.left_back)
    ImageView back;
    @BindView(R.id.rigth_text)
    TextView save;
    @BindView(R.id.et_xg_name)
    EditText etName;

    private UserInfo userInfo;
    private UpdataPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_nickname);

        ButterKnife.bind(this);

        userInfo = CacheUtils.get(CacheConstants.USERINFO);

        presenter = new UpdataPresenter(this, this);
    }

    @OnClick({R.id.left_back, R.id.rigth_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                finish();
                break;
            case R.id.rigth_text:
                if (!TextUtils.isEmpty(etName.getText().toString()) ) {

                    presenter.updataUserInfo(userInfo.getNick_name(),
                            etName.getText().toString(),
                            userInfo.getAge(),
                            Integer.parseInt(userInfo.getSex()),
                            userInfo.getMail());
                } else {
                    TUtils.showCustom(ModifyNicknameActivity.this, "请输入姓名");
                }
                break;
        }
    }

    @Override
    public void updataUserImg(UserImgInfo imgInfo) {

    }

    @Override
    public void updataSuccess() {
        userInfo.setName(etName.getText().toString());
        EventBus.getDefault().post(new EventUtils(EventConstants.TYPE_USERINFO));
        EventBus.getDefault().post(new EventUtils(EventConstants.TYPE_SETT_REFRESH));
        finish();
    }
}
