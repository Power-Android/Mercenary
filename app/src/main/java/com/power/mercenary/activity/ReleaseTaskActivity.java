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
import com.power.mercenary.fragment.ReleaseDPJFragment;
import com.power.mercenary.fragment.ReleaseQBFragment;
import com.power.mercenary.fragment.ReleaseRWZFragment;
import com.power.mercenary.fragment.ReleaseSHZFragment;
import com.power.mercenary.fragment.ReleaseWJDFragment;
import com.power.mercenary.fragment.ReleaseYXJFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/30.
 *
 * 发布任务的管理
 */

public class ReleaseTaskActivity extends BaseActivity implements View.OnClickListener{


    @BindView(R.id.left_back)
    ImageView left_back;

    @BindView(R.id.title_text)
    TextView title_text;


    @BindView(R.id.quan_ll)
    LinearLayout quan_ll;
    @BindView(R.id.quan_tv)
    TextView quan_tv;
    @BindView(R.id.indicator_quan)
    View indicator_quan;

    @BindView(R.id.wjd_ll)
    LinearLayout wjd_ll;
    @BindView(R.id.wjd_tv)
    TextView wjd_tv;
    @BindView(R.id.indicator_wjd)
    View indicator_wjd;

    @BindView(R.id.rwz_ll)
    LinearLayout rwz_ll;
    @BindView(R.id.rwz_tv)
    TextView rwz_tv;
    @BindView(R.id.indicator_rwz)
    View indicator_rwz;

    @BindView(R.id.shz_ll)
    LinearLayout shz_ll;
    @BindView(R.id.shz_tv)
    TextView shz_tv;
    @BindView(R.id.indicator_shz)
    View indicator_shz;

    @BindView(R.id.dpj_ll)
    LinearLayout dpj_ll;
    @BindView(R.id.dpj_tv)
    TextView dpj_tv;
    @BindView(R.id.indicator_dpj)
    View indicator_dpj;

    @BindView(R.id.yxj_ll)
    LinearLayout yxj_ll;
    @BindView(R.id.yxj_tv)
    TextView yxj_tv;
    @BindView(R.id.indicator_yxj)
    View indicator_yxj;


    @BindView(R.id.vp_fbrw)
    ViewPager vp_fbrw;


    ReleaseQBFragment releaseQBFragment;
    ReleaseWJDFragment releaseWJDFragment;
    ReleaseRWZFragment releaseRWZFragment;
    ReleaseSHZFragment releaseSHZFragment;
    ReleaseDPJFragment releaseDPJFragment;

    ReleaseYXJFragment releaseYXJFragment;

    private List<BaseFragment> list;

    private MyAdapter myAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_task);
        ButterKnife.bind(this);

        title_text.setText("发布任务的管理");

        int position = getIntent().getIntExtra("position", 0);

        list = new ArrayList<>();
        if(releaseQBFragment==null){
            releaseQBFragment = new ReleaseQBFragment();
        }
        if(releaseWJDFragment==null){
            releaseWJDFragment = new ReleaseWJDFragment();
        }
        if(releaseRWZFragment==null){
            releaseRWZFragment = new ReleaseRWZFragment();
        }
        if(releaseSHZFragment==null){
            releaseSHZFragment = new ReleaseSHZFragment();
        }
        if(releaseDPJFragment==null){
            releaseDPJFragment = new ReleaseDPJFragment();
        }
        if(releaseYXJFragment==null){
            releaseYXJFragment = new ReleaseYXJFragment();
        }
        list.add(releaseQBFragment);
        list.add(releaseWJDFragment);
        list.add(releaseRWZFragment);
        list.add(releaseSHZFragment);
        list.add(releaseDPJFragment);
        list.add(releaseYXJFragment);


        myAdapter = new MyAdapter(getSupportFragmentManager(),list);
        vp_fbrw.setAdapter(myAdapter);
        vp_fbrw.setOffscreenPageLimit(6);
        initquanbu();
        vp_fbrw.setOnPageChangeListener(pageChangeListener);
        quan_ll.setOnClickListener(this);
        wjd_ll.setOnClickListener(this);
        rwz_ll.setOnClickListener(this);
        shz_ll.setOnClickListener(this);
        dpj_ll.setOnClickListener(this);
        yxj_ll.setOnClickListener(this);

        switch (position) {
            case 0:
                initquanbu();
                break;
            case 1:
                initWJD();
                break;
            case 2:
                initRWZ();
                break;
            case 3:
                initSHZ();
                break;
            case 4:
                initDPJ();
                break;
            case 5:
                initYXJ();
                break;
        }

        left_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            switch (position){
                case 0:

                    initquanbu();

                    break;
                case 1:
                    initWJD();
                    break;
                case 2:
                    initRWZ();
                    break;
                case 3:
                    initSHZ();
                    break;
                case 4:
                    initDPJ();
                    break;
                case 5:
                    initYXJ();
                    break;
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void initquanbu() {
        quan_tv.setTextColor(getResources().getColor(R.color.colorPrimary));
        indicator_quan.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        wjd_tv.setTextColor(getResources().getColor(R.color.textColor));
        indicator_wjd.setBackgroundColor(getResources().getColor(R.color.concrete));
        rwz_tv.setTextColor(getResources().getColor(R.color.textColor));
        indicator_rwz.setBackgroundColor(getResources().getColor(R.color.concrete));
        shz_tv.setTextColor(getResources().getColor(R.color.textColor));
        indicator_shz.setBackgroundColor(getResources().getColor(R.color.concrete));
        dpj_tv.setTextColor(getResources().getColor(R.color.textColor));
        indicator_dpj.setBackgroundColor(getResources().getColor(R.color.concrete));
        yxj_tv.setTextColor(getResources().getColor(R.color.textColor));
        indicator_yxj.setBackgroundColor(getResources().getColor(R.color.concrete));
        initquanbuData();
    }

    private void initWJD() {
        quan_tv.setTextColor(getResources().getColor(R.color.textColor));
        indicator_quan.setBackgroundColor(getResources().getColor(R.color.concrete));
        wjd_tv.setTextColor(getResources().getColor(R.color.colorPrimary));
        indicator_wjd.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        rwz_tv.setTextColor(getResources().getColor(R.color.textColor));
        indicator_rwz.setBackgroundColor(getResources().getColor(R.color.concrete));
        shz_tv.setTextColor(getResources().getColor(R.color.textColor));
        indicator_shz.setBackgroundColor(getResources().getColor(R.color.concrete));
        dpj_tv.setTextColor(getResources().getColor(R.color.textColor));
        indicator_dpj.setBackgroundColor(getResources().getColor(R.color.concrete));
        yxj_tv.setTextColor(getResources().getColor(R.color.textColor));
        indicator_yxj.setBackgroundColor(getResources().getColor(R.color.concrete));
        initWJDData();
    }

    private void initRWZ() {
        quan_tv.setTextColor(getResources().getColor(R.color.textColor));
        indicator_quan.setBackgroundColor(getResources().getColor(R.color.concrete));
        wjd_tv.setTextColor(getResources().getColor(R.color.textColor));
        indicator_wjd.setBackgroundColor(getResources().getColor(R.color.concrete));
        rwz_tv.setTextColor(getResources().getColor(R.color.colorPrimary));
        indicator_rwz.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        shz_tv.setTextColor(getResources().getColor(R.color.textColor));
        indicator_shz.setBackgroundColor(getResources().getColor(R.color.concrete));
        dpj_tv.setTextColor(getResources().getColor(R.color.textColor));
        indicator_dpj.setBackgroundColor(getResources().getColor(R.color.concrete));
        yxj_tv.setTextColor(getResources().getColor(R.color.textColor));
        indicator_yxj.setBackgroundColor(getResources().getColor(R.color.concrete));
        initRWZData();
    }

    private void initSHZ() {
        quan_tv.setTextColor(getResources().getColor(R.color.textColor));
        indicator_quan.setBackgroundColor(getResources().getColor(R.color.concrete));
        wjd_tv.setTextColor(getResources().getColor(R.color.textColor));
        indicator_wjd.setBackgroundColor(getResources().getColor(R.color.concrete));
        rwz_tv.setTextColor(getResources().getColor(R.color.textColor));
        indicator_rwz.setBackgroundColor(getResources().getColor(R.color.concrete));
        shz_tv.setTextColor(getResources().getColor(R.color.colorPrimary));
        indicator_shz.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        dpj_tv.setTextColor(getResources().getColor(R.color.textColor));
        indicator_dpj.setBackgroundColor(getResources().getColor(R.color.concrete));
        yxj_tv.setTextColor(getResources().getColor(R.color.textColor));
        indicator_yxj.setBackgroundColor(getResources().getColor(R.color.concrete));
        initSHZData();
    }

    private void initDPJ() {
        quan_tv.setTextColor(getResources().getColor(R.color.textColor));
        indicator_quan.setBackgroundColor(getResources().getColor(R.color.concrete));
        wjd_tv.setTextColor(getResources().getColor(R.color.textColor));
        indicator_wjd.setBackgroundColor(getResources().getColor(R.color.concrete));
        rwz_tv.setTextColor(getResources().getColor(R.color.textColor));
        indicator_rwz.setBackgroundColor(getResources().getColor(R.color.concrete));
        shz_tv.setTextColor(getResources().getColor(R.color.textColor));
        indicator_shz.setBackgroundColor(getResources().getColor(R.color.concrete));
        dpj_tv.setTextColor(getResources().getColor(R.color.colorPrimary));
        indicator_dpj.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        yxj_tv.setTextColor(getResources().getColor(R.color.textColor));
        indicator_yxj.setBackgroundColor(getResources().getColor(R.color.concrete));
        initDPJData();
    }

    private void initYXJ() {
        quan_tv.setTextColor(getResources().getColor(R.color.textColor));
        indicator_quan.setBackgroundColor(getResources().getColor(R.color.concrete));
        wjd_tv.setTextColor(getResources().getColor(R.color.textColor));
        indicator_wjd.setBackgroundColor(getResources().getColor(R.color.concrete));
        rwz_tv.setTextColor(getResources().getColor(R.color.textColor));
        indicator_rwz.setBackgroundColor(getResources().getColor(R.color.concrete));
        shz_tv.setTextColor(getResources().getColor(R.color.textColor));
        indicator_shz.setBackgroundColor(getResources().getColor(R.color.concrete));
        dpj_tv.setTextColor(getResources().getColor(R.color.textColor));
        indicator_dpj.setBackgroundColor(getResources().getColor(R.color.concrete));
        yxj_tv.setTextColor(getResources().getColor(R.color.colorPrimary));
        indicator_yxj.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        initYXJData();
    }





    private void initquanbuData() {
        vp_fbrw.setCurrentItem(0);
    }

    private void initWJDData() {
        vp_fbrw.setCurrentItem(1);
    }

    private void initRWZData() {
        vp_fbrw.setCurrentItem(2);
    }

    private void initSHZData() {
        vp_fbrw.setCurrentItem(3);
    }
    private void initDPJData() {
        vp_fbrw.setCurrentItem(4);
    }

    private void initYXJData() {
        vp_fbrw.setCurrentItem(5);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.quan_ll:

                initquanbu();

                break;
            case R.id.wjd_ll:
                initWJD();
                break;
            case R.id.rwz_ll:
                initRWZ();
                break;
            case R.id.shz_ll:
                initSHZ();
                break;
            case R.id.dpj_ll:
                initDPJ();
                break;
            case R.id.yxj_ll:
                initYXJ();
                break;
        }

    }
}
