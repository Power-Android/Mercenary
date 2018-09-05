package com.power.mercenary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.power.mercenary.R;
import com.power.mercenary.base.BaseActivity;
import com.power.mercenary.bean.MyZiLiBean;
import com.power.mercenary.http.ResponseBean;
import com.power.mercenary.presenter.MyZiLiPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/28.
 */

public class MyQualificationsActivity extends BaseActivity implements MyZiLiPresenter.Collection {


    @BindView(R.id.left_back)
    ImageView left_back;
    @BindView(R.id.title_text)
    TextView title_text;
    @BindView(R.id.ll_gznx)
    LinearLayout ll_gznx;
    @BindView(R.id.ll_hy)
    LinearLayout ll_hy;
    @BindView(R.id.ll_tc)
    LinearLayout ll_tc;
    @BindView(R.id.ll_zwjs)
    LinearLayout ll_zwjs;
    @BindView(R.id.ll_grsmrz)
    LinearLayout ll_grsmrz;
    @BindView(R.id.ll_gezssc)
    LinearLayout ll_gezssc;
    @BindView(R.id.tv_hangye_name)
    TextView tvHangyeName;
    @BindView(R.id.tv_years)
    TextView tvYears;
    @BindView(R.id.tv_techang_name)
    TextView tvTechangName;
    @BindView(R.id.tv_jieshao)
    TextView tvJieshao;
    @BindView(R.id.tv_shiming)
    TextView tvShiming;
    @BindView(R.id.tv_zhengshu)
    TextView tvZhengshu;
    @BindView(R.id.tv_register_data)
    TextView tvRegisterData;
    private MyZiLiPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_qualifications);
        presenter = new MyZiLiPresenter(this,this);
        presenter.getmZiLi();
        ButterKnife.bind(this);
        title_text.setText("我的资历");
        initData();
        InitListener();

    }
    private void initData() {

    }
    private void InitListener() {
        left_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ll_gznx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyQualificationsActivity.this, GZNXActivity.class);
                startActivityForResult(intent,0);
            }
        });
        ll_hy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyQualificationsActivity.this, HYActivity.class);
                startActivityForResult(intent,0);
            }
        });

        ll_tc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyQualificationsActivity.this, TCActivity.class);
                startActivityForResult(intent,0);
            }
        });

        ll_zwjs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyQualificationsActivity.this, ZWJCActivity.class);
                startActivityForResult(intent,0);
            }
        });
        ll_grsmrz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyQualificationsActivity.this, PersonalRZActivity.class);
                startActivityForResult(intent,0);
            }
        });
        ll_gezssc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MyQualificationsActivity.this, CertificateUploadingActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==0&&resultCode==1){
            presenter.getmZiLi();
        }
    }

    @Override
    public void getmZiLi(MyZiLiBean response) {
        tvHangyeName.setText(response.getIndustry());
        tvYears.setText(response.getWorkyear());
        tvTechangName.setText(response.getSpeciality());
        tvJieshao.setText(response.getIntroduce());

        // is_check	实名认证状态 0:未审核 1：审核中 2：审核通过 3：审核未通过
        if (response.getIs_check().equals("0")){
            tvShiming.setText("未审核");
        }else if (response.getIs_check().equals("1")){
            tvShiming.setText("审核中");
        }else if (response.getIs_check().equals("2")){
            tvShiming.setText("审核通过");
        }else if (response.getIs_check().equals("3")){
            tvShiming.setText("审核未通过");
        }

        //is_up   实名认证状态 0:未上传 1：已上传
        if (response.getIs_up().equals("0")){
            tvZhengshu.setText("未上传");
        }else if (response.getIs_check().equals("1")){
            tvZhengshu.setText("已上传");
        }
        tvRegisterData.setText(response.getDays()+"天");

    }

    @Override
    public void setmZiLi(ResponseBean response) {

    }

    @Override
    public void setIdCard(ResponseBean response) {

    }

}
