package com.power.mercenary.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.power.mercenary.R;
import com.power.mercenary.adapter.MsgAdapter;
import com.power.mercenary.adapter.MyAdapter;
import com.power.mercenary.base.BaseActivity;
import com.power.mercenary.base.BaseFragment;
import com.power.mercenary.fragment.MyAchievementFragment;
import com.power.mercenary.fragment.MyResumeFragment;
import com.power.mercenary.fragment.RenWuTongJiFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/29.
 */

public class PersonalDataActivity extends BaseActivity implements View.OnClickListener{


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

    @BindView(R.id.jiuguan_tv)
    TextView jiuguan_tv;

    @BindView(R.id.indicator_jiuguan)
    View indicator_jiuguan;

    @BindView(R.id.jiuguan_ll)
    LinearLayout jiuguan_ll;

    @BindView(R.id.vp_grzl)
    ViewPager vp_grzl;

    @BindView(R.id.left_back)
    ImageView left_back;

    @BindView(R.id.title_text)
    TextView title_text;


    MyResumeFragment myResumeFragment;
    RenWuTongJiFragment renWuTongJiFragment;
    MyAchievementFragment myAchievementFragment;

    private List<BaseFragment> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);
        ButterKnife.bind(this);
        list = new ArrayList<>();
        title_text.setText("个人资料");
        if(myResumeFragment==null){
            myResumeFragment = new MyResumeFragment();

        }
        if(renWuTongJiFragment==null){
            renWuTongJiFragment = new RenWuTongJiFragment();

        }

        if(myAchievementFragment==null){
            myAchievementFragment=new MyAchievementFragment();
        }
        list.add(myResumeFragment);
        list.add(renWuTongJiFragment);
        list.add(myAchievementFragment);

        MyAdapter myAdapter = new MyAdapter(getSupportFragmentManager(),list);
        vp_grzl.setAdapter(myAdapter);

        initRenwutj();
        jiuguan_ll.setOnClickListener(this);
        renwutjLl.setOnClickListener(this);
        tongchengLl.setOnClickListener(this);
        vp_grzl.setOnPageChangeListener(pageChangeListener);
        left_back.setOnClickListener(this);
    }


    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {



        }

        @Override
        public void onPageSelected(int position) {


            switch (position){
                case 0:

                    initRenwutj();

                    break;
                case 1:
                    initTongcheng();
                    break;
                case 2:
                    initJiuguan();
                    break;
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.jiuguan_ll://
                initJiuguan();
                break;
            case R.id.renwutj_ll://
                initRenwutj();
                break;
            case R.id.tongcheng_ll://
                initTongcheng();
                break;
            case R.id.left_back:
                finish();
                break;
        }
    }

    //任务推荐Tab
    private void initRenwutj() {
        renwutjTv.setTextColor(getResources().getColor(R.color.colorPrimary));
        indicatorRenwutj.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        tongchengTv.setTextColor(getResources().getColor(R.color.textColor));
        indicatorTongcheng.setBackgroundColor(getResources().getColor(R.color.concrete));
        jiuguan_tv.setTextColor(getResources().getColor(R.color.textColor));
        indicator_jiuguan.setBackgroundColor(getResources().getColor(R.color.concrete));
        initRenwutjData();
    }

    //同城Tab
    private void initTongcheng() {
        renwutjTv.setTextColor(getResources().getColor(R.color.textColor));
        indicatorRenwutj.setBackgroundColor(getResources().getColor(R.color.concrete));
        tongchengTv.setTextColor(getResources().getColor(R.color.colorPrimary));
        indicatorTongcheng.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        jiuguan_tv.setTextColor(getResources().getColor(R.color.textColor));
        indicator_jiuguan.setBackgroundColor(getResources().getColor(R.color.concrete));
        initTongchengData();
    }

    private void initJiuguan() {
        renwutjTv.setTextColor(getResources().getColor(R.color.textColor));
        indicatorRenwutj.setBackgroundColor(getResources().getColor(R.color.concrete));
        tongchengTv.setTextColor(getResources().getColor(R.color.textColor));
        indicatorTongcheng.setBackgroundColor(getResources().getColor(R.color.concrete));
        jiuguan_tv.setTextColor(getResources().getColor(R.color.colorPrimary));
        indicator_jiuguan.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        initJiuguanData();
    }

    private void initRenwutjData() {

        vp_grzl.setCurrentItem(0);

    }

    private void initTongchengData() {

        vp_grzl.setCurrentItem(1);
    }

    private  void initJiuguanData(){

        vp_grzl.setCurrentItem(2);

    }
}
