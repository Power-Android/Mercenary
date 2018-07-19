package com.power.mercenary.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.power.mercenary.R;
import com.power.mercenary.base.BaseActivity;
import com.power.mercenary.bean.mytask.StatisticsListBean;
import com.power.mercenary.bean.mytask.StatisticsNumBean;
import com.power.mercenary.presenter.StatisticsPresenter;
import com.power.mercenary.view.pullrecyclerview.WanRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/3/28.
 */

public class TaskStatisticsActivity extends BaseActivity implements StatisticsPresenter.StatisticsCallBack, WanRecyclerView.PullRecyclerViewCallBack {

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
    @BindView(R.id.act_task_star_recyclerView)
    WanRecyclerView recyclerView;
    private StatisticsPresenter presenter;
    private int state;
    private int page = 1;

    private List<StatisticsListBean> mList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_statistics);
        ButterKnife.bind(this);

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
        recyclerView.setLinearLayout();
        recyclerView.setPullRecyclerViewListener(this);

//        RenWuAdapter changegameAdapter = new RenWuAdapter(R.layout.renwu_item_view, mList);


    }

    @OnClick({R.id.act_task_star_layout1, R.id.act_task_star_layout2, R.id.act_task_star_layout3, R.id.act_task_star_layout4, R.id.act_task_star_layout5, R.id.act_task_star_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.act_task_star_layout1:
                show();
                actTaskStar1.setTextColor(Color.parseColor("#038eff"));
                actTaskStarNum1.setTextColor(Color.parseColor("#038eff"));
                actTaskStarView1.setVisibility(View.VISIBLE);
                if (state == 1) {
                    presenter.getTaskStatisticsAcceptList(page, 1);
                } else {
                    presenter.getTaskStatisticsPublishList(page, 1);
                }
                break;
            case R.id.act_task_star_layout2:
                show();
                actTaskStar2.setTextColor(Color.parseColor("#038eff"));
                actTaskStarNum2.setTextColor(Color.parseColor("#038eff"));
                actTaskStarView2.setVisibility(View.VISIBLE);
                if (state == 1) {
                    presenter.getTaskStatisticsAcceptList(page, 2);
                } else {
                    presenter.getTaskStatisticsPublishList(page, 2);
                }
                break;
            case R.id.act_task_star_layout3:
                show();
                actTaskStar3.setTextColor(Color.parseColor("#038eff"));
                actTaskStarNum3.setTextColor(Color.parseColor("#038eff"));
                actTaskStarView3.setVisibility(View.VISIBLE);
                if (state == 1) {
                    presenter.getTaskStatisticsAcceptList(page, 3);
                } else {
                    presenter.getTaskStatisticsPublishList(page, 3);
                }
                break;
            case R.id.act_task_star_layout4:
                show();
                actTaskStar4.setTextColor(Color.parseColor("#038eff"));
                actTaskStarNum4.setTextColor(Color.parseColor("#038eff"));
                actTaskStarView4.setVisibility(View.VISIBLE);
                if (state == 1) {
                    presenter.getTaskStatisticsAcceptList(page, 4);
                } else {
                    presenter.getTaskStatisticsPublishList(page, 4);
                }
                break;
            case R.id.act_task_star_layout5:
                show();
                actTaskStar5.setTextColor(Color.parseColor("#038eff"));
                actTaskStarNum5.setTextColor(Color.parseColor("#038eff"));
                actTaskStarView5.setVisibility(View.VISIBLE);
                if (state == 1) {
                    presenter.getTaskStatisticsAcceptList(page, 5);
                } else {
                    presenter.getTaskStatisticsPublishList(page, 5);
                }
                break;
            case R.id.act_task_star_layout:
                show();
                actTaskStar.setTextColor(Color.parseColor("#038eff"));
                actTaskStarNum.setTextColor(Color.parseColor("#038eff"));
                actTaskStarView.setVisibility(View.VISIBLE);
                if (state == 1) {
                    presenter.getTaskStatisticsAcceptList(page, 6);
                } else {
                    presenter.getTaskStatisticsPublishList(page, 6);
                }
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

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }
}
