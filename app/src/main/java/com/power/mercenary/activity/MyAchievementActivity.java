package com.power.mercenary.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.power.mercenary.R;
import com.power.mercenary.base.BaseActivity;
import com.power.mercenary.bean.AchieveBean;
import com.power.mercenary.presenter.AchievePresenter;
import com.power.mercenary.utils.MyUtils;
import com.power.mercenary.utils.Urls;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.power.mercenary.R.id.pb_jd;

/**
 * Created by Administrator on 2018/3/28.
 */

public class MyAchievementActivity extends BaseActivity implements AchievePresenter.AchieveCallBack {

    @BindView(R.id.left_back)
    ImageView left_back;

    @BindView(R.id.title_text)
    TextView title_text;

    @BindView(R.id.mRecycler_ywc)
    RecyclerView mRecycler_ywc;
    @BindView(R.id.rl_title_bg)
    RelativeLayout rlTitleBg;
    @BindView(R.id.img_achieve_left)
    ImageView imgAchieveLeft;
    @BindView(R.id.img_achieve_center)
    ImageView imgAchieveCenter;
    @BindView(R.id.img_achieve_right)
    ImageView imgAchieveRight;
    private YWCAdapter ywcAdapter;
    ArrayList<String> mList = new ArrayList<>();

    @BindView(R.id.mRecycler_wwc)
    RecyclerView mRecycler_wwc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_achievement);
        AchievePresenter presenter = new AchievePresenter(this, this);
        presenter.getAchieveInfo();
        ButterKnife.bind(this);
        title_text.setText("我的成就");


        left_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void getAchieveInfo(AchieveBean achieveBean) {
        List<AchieveBean.XunzhangBean> xunzhang = achieveBean.getXunzhang();
        if (xunzhang.size() <= 0) {

        } else if (xunzhang.size() == 1) {
            Glide.with(mContext).load(Urls.BASEIMGURL + xunzhang.get(0).getMedal()).into(imgAchieveCenter);
        }else if (xunzhang.size() == 2) {
            Glide.with(mContext).load(Urls.BASEIMGURL + xunzhang.get(0).getMedal()).into(imgAchieveCenter);
            Glide.with(mContext).load(Urls.BASEIMGURL + xunzhang.get(1).getMedal()).into(imgAchieveLeft);
        }
        else if (xunzhang.size() >= 3) {
            Glide.with(mContext).load(Urls.BASEIMGURL + xunzhang.get(0).getMedal()).into(imgAchieveCenter);
            Glide.with(mContext).load(Urls.BASEIMGURL + xunzhang.get(1).getMedal()).into(imgAchieveLeft);
            Glide.with(mContext).load(Urls.BASEIMGURL + xunzhang.get(2).getMedal()).into(imgAchieveRight);
        }
        mRecycler_ywc.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycler_ywc.setNestedScrollingEnabled(false);
        YWCAdapter changegameAdapter = new YWCAdapter(R.layout.ma_item_view, achieveBean.getDone_achieve());
        mRecycler_ywc.setAdapter(changegameAdapter);

        mRecycler_wwc.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycler_wwc.setNestedScrollingEnabled(false);
        WCCAdapter wccAdapter = new WCCAdapter(R.layout.wcc_item_view, achieveBean.getNot_achieve());
        mRecycler_wwc.setAdapter(wccAdapter);
    }

    public class YWCAdapter extends BaseQuickAdapter<AchieveBean.DoneAchieveBean, BaseViewHolder> {

        public YWCAdapter(@LayoutRes int layoutResId, @Nullable List<AchieveBean.DoneAchieveBean> data) {
            super(layoutResId, data);


        }

        @Override
        protected void convert(BaseViewHolder helper, AchieveBean.DoneAchieveBean item) {
            helper.setText(R.id.tv_achieve_name, item.getName());
            helper.setText(R.id.tv_achieve_content, item.getComplete());
            helper.setText(R.id.tv_achieve_time, "完成时间：" + MyUtils.getDateToStringTime(item.getGet_time()));
            Glide.with(mContext).load(Urls.BASEIMGURL + item.getMedal()).into((ImageView) helper.getView(R.id.img_achieve));
        }
    }


    public class WCCAdapter extends BaseQuickAdapter<AchieveBean.NotAchieveBean, BaseViewHolder> {

        public WCCAdapter(@LayoutRes int layoutResId, @Nullable List<AchieveBean.NotAchieveBean> data) {
            super(layoutResId, data);


        }

        @Override
        protected void convert(BaseViewHolder helper, AchieveBean.NotAchieveBean item) {
            helper.setText(R.id.tv_achieve_name, item.getName());
            helper.setText(R.id.tv_achieve_content, item.getComplete());
            helper.setText(R.id.tv_progress, item.getGet_times() + "/" + item.getNeed_times());
            helper.setProgress(pb_jd, Integer.parseInt(item.getGet_times()), Integer.parseInt(item.getNeed_times()));
            Glide.with(mContext).load(Urls.BASEIMGURL + item.getMedal()).into((ImageView) helper.getView(R.id.img_achieve));
        }
    }
}
