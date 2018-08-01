package com.power.mercenary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.power.mercenary.R;
import com.power.mercenary.base.BaseActivity;
import com.power.mercenary.bean.MyZiLiBean;
import com.power.mercenary.http.ResponseBean;
import com.power.mercenary.presenter.MyZiLiPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/3/28.
 */

public class ZWJCActivity extends BaseActivity implements MyZiLiPresenter.Collection {


    @BindView(R.id.left_back)
    ImageView left_back;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.edt_jieshao)
    EditText edtJieshao;
    private MyZiLiPresenter presenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zwjs);
        ButterKnife.bind(this);
        presenter = new MyZiLiPresenter(this,this);
        left_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @OnClick(R.id.tv_save)
    public void onViewClicked() {
        if (TextUtils.isEmpty(edtJieshao.getText().toString())){
            Toast.makeText(mContext, "请输入自我介绍", Toast.LENGTH_SHORT).show();
        }else {
            presenter.setmZiLi("","","",edtJieshao.getText().toString(),"");
        }
    }

    @Override
    public void getmZiLi(MyZiLiBean response) {

    }

    @Override
    public void setmZiLi(ResponseBean response) {
        Intent intent = getIntent();
        setResult(1,intent);
        finish();
    }

    @Override
    public void setIdCard(ResponseBean response) {

    }

}
