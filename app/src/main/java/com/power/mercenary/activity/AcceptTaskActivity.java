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
import com.power.mercenary.fragment.AcceptTaskFragment;
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
 * admin  2018/7/18 wan
 */
public class AcceptTaskActivity extends BaseActivity implements View.OnClickListener {

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

    @BindView(R.id.vp_fbrw)
    ViewPager vp_fbrw;


    AcceptTaskFragment fragment1;
    AcceptTaskFragment fragment2;
    AcceptTaskFragment fragment3;
    AcceptTaskFragment fragment4;
    AcceptTaskFragment fragment5;

    private List<BaseFragment> list;

    private MyAdapter myAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_task);
        ButterKnife.bind(this);

        title_text.setText("接受任务的管理");

        int position = getIntent().getIntExtra("position", 0);

        list = new ArrayList<>();

        if (fragment1 == null) {
            fragment1 = new AcceptTaskFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("state", 0);
            fragment1.setArguments(bundle);
        }

        if (fragment2 == null) {
            fragment2 = new AcceptTaskFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("state", 1);
            fragment2.setArguments(bundle);
        }

        if (fragment3 == null) {
            fragment3 = new AcceptTaskFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("state", 2);
            fragment3.setArguments(bundle);
        }

        if (fragment4 == null) {
            fragment4 = new AcceptTaskFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("state", 3);
            fragment4.setArguments(bundle);
        }

        if (fragment5 == null) {
            fragment5 = new AcceptTaskFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("state", 6);
            fragment5.setArguments(bundle);
        }

        list.add(fragment1);
        list.add(fragment2);
        list.add(fragment3);
        list.add(fragment4);
        list.add(fragment5);

        myAdapter = new MyAdapter(getSupportFragmentManager(),list);
        vp_fbrw.setAdapter(myAdapter);
        vp_fbrw.setOffscreenPageLimit(5);
        initquanbu();
        vp_fbrw.setOnPageChangeListener(pageChangeListener);
        quan_ll.setOnClickListener(this);
        wjd_ll.setOnClickListener(this);
        rwz_ll.setOnClickListener(this);
        shz_ll.setOnClickListener(this);
        dpj_ll.setOnClickListener(this);

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
        initDPJData();
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
        }

    }
}
