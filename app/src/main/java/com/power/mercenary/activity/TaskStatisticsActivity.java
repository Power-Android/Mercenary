package com.power.mercenary.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.power.mercenary.R;
import com.power.mercenary.adapter.frag.FragmentVPagerAdapter;
import com.power.mercenary.adapter.task.TaskStatisticsAdapter;
import com.power.mercenary.base.BaseActivity;
import com.power.mercenary.bean.mytask.StatisticsListBean;
import com.power.mercenary.bean.mytask.StatisticsNumBean;
import com.power.mercenary.fragment.StatisticsFragment;
import com.power.mercenary.presenter.StatisticsPresenter;
import com.power.mercenary.view.pullrecyclerview.WanRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/3/28.
 *
 * 日期选择器
 */

public class TaskStatisticsActivity extends BaseActivity implements StatisticsPresenter.StatisticsCallBack {


    @BindView(R.id.left_back)
    ImageView left_back;

    @BindView(R.id.title_text)
    TextView title_text;
    @BindView(R.id.act_task_star1)
    TextView actTaskStar1;
    @BindView(R.id.act_task_starNum1)
    TextView actTaskStarNum1;
    @BindView(R.id.act_task_starView1)
    View actTaskStarView1;
    @BindView(R.id.act_task_star_layout1)
    LinearLayout actTaskStarLayout1;
    @BindView(R.id.act_task_star2)
    TextView actTaskStar2;
    @BindView(R.id.act_task_starNum2)
    TextView actTaskStarNum2;
    @BindView(R.id.act_task_starView2)
    View actTaskStarView2;
    @BindView(R.id.act_task_star_layout2)
    LinearLayout actTaskStarLayout2;
    @BindView(R.id.act_task_star3)
    TextView actTaskStar3;
    @BindView(R.id.act_task_starNum3)
    TextView actTaskStarNum3;
    @BindView(R.id.act_task_starView3)
    View actTaskStarView3;
    @BindView(R.id.act_task_star_layout3)
    LinearLayout actTaskStarLayout3;
    @BindView(R.id.act_task_star4)
    TextView actTaskStar4;
    @BindView(R.id.act_task_starNum4)
    TextView actTaskStarNum4;
    @BindView(R.id.act_task_starView4)
    View actTaskStarView4;
    @BindView(R.id.act_task_star_layout4)
    LinearLayout actTaskStarLayout4;
    @BindView(R.id.act_task_star5)
    TextView actTaskStar5;
    @BindView(R.id.act_task_starNum5)
    TextView actTaskStarNum5;
    @BindView(R.id.act_task_starView5)
    View actTaskStarView5;
    @BindView(R.id.act_task_star_layout5)
    LinearLayout actTaskStarLayout5;
    @BindView(R.id.act_task_star)
    TextView actTaskStar;
    @BindView(R.id.act_task_starNum)
    TextView actTaskStarNum;
    @BindView(R.id.act_task_starView)
    View actTaskStarView;
    @BindView(R.id.act_task_star_layout)
    LinearLayout actTaskStarLayout;

    private StatisticsPresenter presenter;
    private int state;
    private int page = 1;

    private List<Fragment> mList;

    private StatisticsFragment fragment1;
    private StatisticsFragment fragment2;
    private StatisticsFragment fragment3;
    private StatisticsFragment fragment4;
    private StatisticsFragment fragment5;
    private StatisticsFragment fragment6;

    private ViewPager vp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_statistics);
        ButterKnife.bind(this);

        vp = (ViewPager) findViewById(R.id.act_task_star_vp);

        state = getIntent().getIntExtra("state", 0);

        presenter = new StatisticsPresenter(this, this);

        switch (state) {
            case 1:
                title_text.setText("接受任务统计");
                presenter.getTaskStatisticsAcceptNum();
                break;
            case 2:
                title_text.setText("发布任务统计");
                presenter.getTaskStatisticsPublishNum();
                break;
        }

        mList = new ArrayList<>();

        if (fragment1 == null) {
            fragment1 = new StatisticsFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("frag", 1);
            bundle.putInt("state", state);
            fragment1.setArguments(bundle);
        }

        if (fragment2 == null) {
            fragment2 = new StatisticsFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("frag", 2);
            bundle.putInt("state", state);
            fragment2.setArguments(bundle);
        }

        if (fragment3 == null) {
            fragment3 = new StatisticsFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("frag", 3);
            bundle.putInt("state", state);
            fragment3.setArguments(bundle);
        }

        if (fragment4 == null) {
            fragment4 = new StatisticsFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("frag", 4);
            bundle.putInt("state", state);
            fragment4.setArguments(bundle);
        }

        if (fragment5 == null) {
            fragment5 = new StatisticsFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("frag", 5);
            bundle.putInt("state", state);
            fragment5.setArguments(bundle);
        }

        if (fragment6 == null) {
            fragment6 = new StatisticsFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("frag", 6);
            bundle.putInt("state", state);
            fragment6.setArguments(bundle);
        }

        mList.add(fragment1);
        mList.add(fragment2);
        mList.add(fragment3);
        mList.add(fragment4);
        mList.add(fragment5);
        mList.add(fragment6);

        FragmentVPagerAdapter fragmentVPagerAdapter = new FragmentVPagerAdapter(getSupportFragmentManager(), mList);
        vp.setAdapter(fragmentVPagerAdapter);
        vp.setOffscreenPageLimit(6);

        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        show();
                        actTaskStar1.setTextColor(Color.parseColor("#038eff"));
                        actTaskStarNum1.setTextColor(Color.parseColor("#038eff"));
                        actTaskStarView1.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        show();
                        actTaskStar2.setTextColor(Color.parseColor("#038eff"));
                        actTaskStarNum2.setTextColor(Color.parseColor("#038eff"));
                        actTaskStarView2.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        show();
                        actTaskStar3.setTextColor(Color.parseColor("#038eff"));
                        actTaskStarNum3.setTextColor(Color.parseColor("#038eff"));
                        actTaskStarView3.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        show();
                        actTaskStar4.setTextColor(Color.parseColor("#038eff"));
                        actTaskStarNum4.setTextColor(Color.parseColor("#038eff"));
                        actTaskStarView4.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        show();
                        actTaskStar5.setTextColor(Color.parseColor("#038eff"));
                        actTaskStarNum5.setTextColor(Color.parseColor("#038eff"));
                        actTaskStarView5.setVisibility(View.VISIBLE);
                        break;
                    case 5:
                        show();
                        actTaskStar.setTextColor(Color.parseColor("#038eff"));
                        actTaskStarNum.setTextColor(Color.parseColor("#038eff"));
                        actTaskStarView.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
//        RenWuAdapter changegameAdapter = new RenWuAdapter(R.layout.renwu_item_view, mList);


        left_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick({R.id.act_task_star_layout1, R.id.act_task_star_layout2, R.id.act_task_star_layout3, R.id.act_task_star_layout4, R.id.act_task_star_layout5, R.id.act_task_star_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.act_task_star_layout1:
                show();
                actTaskStar1.setTextColor(Color.parseColor("#038eff"));
                actTaskStarNum1.setTextColor(Color.parseColor("#038eff"));
                actTaskStarView1.setVisibility(View.VISIBLE);
                vp.setCurrentItem(0);
                break;
            case R.id.act_task_star_layout2:
                show();
                actTaskStar2.setTextColor(Color.parseColor("#038eff"));
                actTaskStarNum2.setTextColor(Color.parseColor("#038eff"));
                actTaskStarView2.setVisibility(View.VISIBLE);
                vp.setCurrentItem(1);
                break;
            case R.id.act_task_star_layout3:
                show();
                actTaskStar3.setTextColor(Color.parseColor("#038eff"));
                actTaskStarNum3.setTextColor(Color.parseColor("#038eff"));
                actTaskStarView3.setVisibility(View.VISIBLE);
                vp.setCurrentItem(2);
                break;
            case R.id.act_task_star_layout4:
                show();
                actTaskStar4.setTextColor(Color.parseColor("#038eff"));
                actTaskStarNum4.setTextColor(Color.parseColor("#038eff"));
                actTaskStarView4.setVisibility(View.VISIBLE);
                vp.setCurrentItem(3);
                break;
            case R.id.act_task_star_layout5:
                show();
                actTaskStar5.setTextColor(Color.parseColor("#038eff"));
                actTaskStarNum5.setTextColor(Color.parseColor("#038eff"));
                actTaskStarView5.setVisibility(View.VISIBLE);
                vp.setCurrentItem(4);
                break;
            case R.id.act_task_star_layout:
                show();
                actTaskStar.setTextColor(Color.parseColor("#038eff"));
                actTaskStarNum.setTextColor(Color.parseColor("#038eff"));
                actTaskStarView.setVisibility(View.VISIBLE);
                vp.setCurrentItem(5);
                break;
        }
    }

    private void show(){
        actTaskStar1.setTextColor(Color.parseColor("#666666"));
        actTaskStarNum1.setTextColor(Color.parseColor("#666666"));
        actTaskStarView1.setVisibility(View.GONE);
        actTaskStar2.setTextColor(Color.parseColor("#666666"));
        actTaskStarNum2.setTextColor(Color.parseColor("#666666"));
        actTaskStarView2.setVisibility(View.GONE);
        actTaskStar3.setTextColor(Color.parseColor("#666666"));
        actTaskStarNum3.setTextColor(Color.parseColor("#666666"));
        actTaskStarView3.setVisibility(View.GONE);
        actTaskStar4.setTextColor(Color.parseColor("#666666"));
        actTaskStarNum4.setTextColor(Color.parseColor("#666666"));
        actTaskStarView4.setVisibility(View.GONE);
        actTaskStar5.setTextColor(Color.parseColor("#666666"));
        actTaskStarNum5.setTextColor(Color.parseColor("#666666"));
        actTaskStarView5.setVisibility(View.GONE);
        actTaskStar.setTextColor(Color.parseColor("#666666"));
        actTaskStarNum.setTextColor(Color.parseColor("#666666"));
        actTaskStarView.setVisibility(View.GONE);
    }

    @Override
    public void getTaskStatisticsPublishNum(StatisticsNumBean data) {
        if (data != null) {
            actTaskStarNum1.setText(data.getOne() + "");
            actTaskStarNum2.setText(data.getTow() + "");
            actTaskStarNum3.setText(data.getThree() + "");
            actTaskStarNum4.setText(data.getFore() + "");
            actTaskStarNum5.setText(data.getFive() + "");
            actTaskStarNum.setText(data.getTotal() + "");
        }
    }

    @Override
    public void getTaskStatisticsAcceptNum(StatisticsNumBean data) {
        if (data != null) {
            actTaskStarNum1.setText(data.getOne() + "");
            actTaskStarNum2.setText(data.getTow() + "");
            actTaskStarNum3.setText(data.getThree() + "");
            actTaskStarNum4.setText(data.getFore() + "");
            actTaskStarNum5.setText(data.getFive() + "");
            actTaskStarNum.setText(data.getTotal() + "");
        }
    }

    @Override
    public void getTaskStatisticsPublishList(List<StatisticsListBean> data) {

    }

    @Override
    public void getTaskStatisticsAcceptList(List<StatisticsListBean> data) {

    }
}
