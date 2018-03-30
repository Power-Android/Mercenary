package com.power.mercenary.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.ContextMenu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.power.mercenary.R;
import com.power.mercenary.base.BaseActivity;
import com.power.mercenary.base.BaseFragment;
import com.power.mercenary.fragment.RegisterGRFragment;
import com.power.mercenary.fragment.RegisterQYFragment;
import com.power.mercenary.fragment.SignInMMFragment;
import com.power.mercenary.fragment.SignInYZMFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/29.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener{

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

    private boolean isyt;
    @BindView(R.id.cb_ty)
    CheckBox cb_ty;

    @BindView(R.id.tv_zc_wc)
    TextView tv_zc_wc;


    private RegisterGRFragment registerGRFragment;
    private RegisterQYFragment registerQYFragment;

    private BaseFragment baseFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        title_text.setText("注册");
        renwutjLl.setOnClickListener(this);
        tongchengLl.setOnClickListener(this);
        left_back.setOnClickListener(this);
        tv_ydty.setOnClickListener(this);
        initRenwutj();
    }

    //任务推荐Tab
    private void initRenwutj() {
        renwutjTv.setTextColor(getResources().getColor(R.color.colorPrimary));
        indicatorRenwutj.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        tongchengTv.setTextColor(getResources().getColor(R.color.textColor));
        indicatorTongcheng.setBackgroundColor(getResources().getColor(R.color.concrete));
        initRenwutjData();
    }

    //同城Tab
    private void initTongcheng() {
        renwutjTv.setTextColor(getResources().getColor(R.color.textColor));
        indicatorRenwutj.setBackgroundColor(getResources().getColor(R.color.concrete));
        tongchengTv.setTextColor(getResources().getColor(R.color.colorPrimary));
        indicatorTongcheng.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        initTongchengData();
    }

    private void initRenwutjData() {
        if(registerGRFragment==null){
            registerGRFragment = new RegisterGRFragment();
        }
        addFragments(registerGRFragment);
        tv_zc_wc.setText("完成");
    }

    private void initTongchengData() {
        if(registerQYFragment==null){
            registerQYFragment = new RegisterQYFragment();
        }
        addFragments(registerQYFragment);
        tv_zc_wc.setText("下一步");
    }
    private void addFragments(BaseFragment f) {
        // 第一步：得到fragment管理类
        FragmentManager manager = getSupportFragmentManager();
        // 第二步：开启一个事务
        FragmentTransaction transaction = manager.beginTransaction();

        if (baseFragment != null) {
            //每次把前一个fragment给隐藏了
            transaction.hide(baseFragment);
        }
        //isAdded:判断当前的fragment对象是否被加载过
        if (!f.isAdded()) {
            // 第三步：调用添加fragment的方法 第一个参数：容器的id 第二个参数：要放置的fragment的一个实例对象
            transaction.add(R.id.fl_content_zc, f);
        }
        //显示当前的fragment
        transaction.show(f);
        // 第四步：提交
        transaction.commit();
        baseFragment = f;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.renwutj_ll:
                initRenwutj();
                break;
            case R.id.tongcheng_ll:
                initTongcheng();
                break;
            case R.id.left_back:

                finish();

                break;
            case R.id.tv_ydty:
                if(isyt){
                    cb_ty.setChecked(true);
                }else{
                    cb_ty.setChecked(false);
                }
                isyt=!isyt;
                break;
        }

    }
}
