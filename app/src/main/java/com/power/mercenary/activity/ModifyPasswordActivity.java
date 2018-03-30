package com.power.mercenary.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.power.mercenary.R;
import com.power.mercenary.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/23.
 */

public class ModifyPasswordActivity extends BaseActivity {

    @BindView(R.id.left_back)
    ImageView left_back;
    @BindView(R.id.title_text)
    TextView title_text;

    @BindView(R.id.img_yj)
    ImageView img_yj;
    @BindView(R.id.et_xg_mm)
    EditText et_xg_mm;

    private boolean isqh;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_modify_password);
        ButterKnife.bind(this);

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

                if(isqh){
                    et_xg_mm.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    img_yj.setImageResource(R.drawable.yj_2x);
                }else{//明文
                    et_xg_mm.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    img_yj.setImageResource(R.drawable.by_2x);
                }
                isqh=!isqh;
            }
        });
    }
}
