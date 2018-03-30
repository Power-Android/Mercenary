package com.power.mercenary.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.power.mercenary.R;
import com.power.mercenary.adapter.MyAdapter;
import com.power.mercenary.base.BaseActivity;
import com.power.mercenary.base.BaseFragment;
import com.power.mercenary.fragment.PeopleFragment;
import com.power.mercenary.fragment.TaskFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/3/27.
 */

public class MyCollection extends BaseActivity {

    @BindView(R.id.left_back)
    ImageView left_back;

    @BindView(R.id.title_text)
    TextView title_text;

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
    @BindView(R.id.vp_sc)
    ViewPager vp_sc;
    private MyAdapter adapter;

    private TaskFragment taskFragment;

    private PeopleFragment peopleFragment;
private List<BaseFragment> vplist;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collection);
        ButterKnife.bind(this);

        title_text.setText("我的收藏");
        vplist = new ArrayList<>();
        taskFragment = new TaskFragment();
        peopleFragment = new PeopleFragment();

        vplist.add(taskFragment);
        vplist.add(peopleFragment);

        adapter = new MyAdapter(getSupportFragmentManager(),vplist);
        vp_sc.setAdapter(adapter);
        vp_sc.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {


                if(position==0){
                    initRenwutj();
                }else if(position==1){
                    initTongcheng();
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        left_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

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

    }

    private void initTongchengData() {

    }

    @OnClick({R.id.renwutj_ll, R.id.tongcheng_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.renwutj_ll://任务推荐
                initRenwutj();

                vp_sc.setCurrentItem(0);

                break;
            case R.id.tongcheng_ll://同城
                initTongcheng();
                vp_sc.setCurrentItem(1);
                break;
        }
    }
}
