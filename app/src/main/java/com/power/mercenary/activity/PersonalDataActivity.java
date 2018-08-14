package com.power.mercenary.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.power.mercenary.R;
import com.power.mercenary.activity.chat.ChatActivity;
import com.power.mercenary.adapter.MsgAdapter;
import com.power.mercenary.adapter.MyAdapter;
import com.power.mercenary.base.BaseActivity;
import com.power.mercenary.base.BaseFragment;
import com.power.mercenary.bean.MyRecordBean;
import com.power.mercenary.bean.MyTaskBean;
import com.power.mercenary.bean.PersonalBean;
import com.power.mercenary.fragment.MyAchievementFragment;
import com.power.mercenary.fragment.MyResumeFragment;
import com.power.mercenary.fragment.RenWuTongJiFragment;
import com.power.mercenary.presenter.PersonalPresenter;
import com.power.mercenary.utils.TimeUtils;
import com.power.mercenary.utils.Urls;
import com.power.mercenary.view.CircleImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/29.
 */

public class PersonalDataActivity extends BaseActivity implements View.OnClickListener, PersonalPresenter.PersonalCallBack {


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

    private TextView name;

    private TextView time;

    private CircleImageView icon;

    private List<BaseFragment> list;

    private PersonalPresenter presenter;

    private String userId;

    private TextView fans;

    private TextView tvValue;

    private TextView follow;

    private ImageView ivCollection;

    private LinearLayout collection;

    private LinearLayout privateChat;

    private int isFollow;
    private PersonalBean data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);
        ButterKnife.bind(this);
        list = new ArrayList<>();
        title_text.setText("个人资料");

        userId = getIntent().getStringExtra("userId");

        name = (TextView) findViewById(R.id.act_personal_name);

        icon = (CircleImageView) findViewById(R.id.act_personal_icon);

        time = (TextView) findViewById(R.id.act_personal_time);

        fans = (TextView) findViewById(R.id.act_personal_fans);

        tvValue = (TextView) findViewById(R.id.act_personal_value);

        follow = (TextView) findViewById(R.id.act_personal_follow);

        ivCollection = (ImageView) findViewById(R.id.act_personal_collectionIv);

        collection = (LinearLayout) findViewById(R.id.act_personal_collection);

        privateChat = (LinearLayout) findViewById(R.id.act_personal_privateChat);

        presenter = new PersonalPresenter(this, this);
        presenter.getPersonalData(userId);
//        presenter.getMyRecord(userId);
//        presenter.getMyTask(userId);

        if (myResumeFragment == null) {
            myResumeFragment = new MyResumeFragment();
            Bundle bundle = new Bundle();
            bundle.putString("userId", userId);
            myResumeFragment.setArguments(bundle);

        }
        if (renWuTongJiFragment == null) {
            renWuTongJiFragment = new RenWuTongJiFragment();
            Bundle bundle = new Bundle();
            bundle.putString("userId", userId);
            renWuTongJiFragment.setArguments(bundle);
        }

//        if(myAchievementFragment==null){
//            myAchievementFragment=new MyAchievementFragment();
//        }
        list.add(myResumeFragment);
        list.add(renWuTongJiFragment);
//        list.add(myAchievementFragment);

        MyAdapter myAdapter = new MyAdapter(getSupportFragmentManager(), list);
        vp_grzl.setAdapter(myAdapter);

        initRenwutj();
        jiuguan_ll.setOnClickListener(this);
        renwutjLl.setOnClickListener(this);
        tongchengLl.setOnClickListener(this);
        vp_grzl.setOnPageChangeListener(pageChangeListener);
        left_back.setOnClickListener(this);

        collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFollow == 1) {
                    ivCollection.setImageDrawable(getResources().getDrawable(R.drawable.w_sc_2x));
                    presenter.requestAttention(userId, 2);
                    follow.setText("关注");
                    isFollow = 0;
                } else {
                    ivCollection.setImageDrawable(getResources().getDrawable(R.drawable.y_sc_2x));
                    presenter.requestAttention(userId, 1);
                    follow.setText("已关注");
                    isFollow = 1;
                }
            }
        });


        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFollow == 1) {
                    ivCollection.setImageDrawable(getResources().getDrawable(R.drawable.w_sc_2x));
                    presenter.requestAttention(userId, 2);
                    follow.setText("关注");
                    isFollow = 0;
                } else {
                    ivCollection.setImageDrawable(getResources().getDrawable(R.drawable.y_sc_2x));
                    presenter.requestAttention(userId, 1);
                    follow.setText("已关注");
                    isFollow = 1;
                }
            }
        });

        privateChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatActivity.invoke(PersonalDataActivity.this, data.getId(), data.getHead_img(), data.getName());
            }
        });
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


        }

        @Override
        public void onPageSelected(int position) {


            switch (position) {
                case 0:

                    initRenwutj();

                    break;
                case 1:
                    initTongcheng();
                    break;
                case 2:
//                    initJiuguan();
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
//                initJiuguan();
                Intent intent = new Intent(this, MyAchievementActivity.class);
                intent.putExtra("state", 1);
                startActivity(intent);
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

    private void initJiuguanData() {

        vp_grzl.setCurrentItem(2);

    }

    @Override
    public void getPersonalData(PersonalBean data) {
        if (data != null) {

            this.data = data;

            name.setText(data.getName());

            Glide.with(this)
                    .load(Urls.BASEIMGURL + data.getHead_img())
                    .into(icon);

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            time.setText("注册于" + format.format(new Date(data.getCreate_time())));

            fans.setText("粉丝：" + data.getFans());

            tvValue.setText("当前总价值：" + data.getTotal() + "元");

            isFollow = data.getIscare();

            switch (data.getIscare()) {
                case 1:
                    ivCollection.setImageDrawable(getResources().getDrawable(R.drawable.y_sc_2x));
                    follow.setText("已关注");
                    break;
                case 0:
                    ivCollection.setImageDrawable(getResources().getDrawable(R.drawable.w_sc_2x));
                    follow.setText("关注");
                    break;
            }
        }
    }

    @Override
    public void getMyRecord(MyRecordBean data) {

    }

    @Override
    public void getMyTask(MyTaskBean data) {

    }

    @Override
    public void requestAttention() {

    }

    public static void invoke(Context context, String userId) {
        Intent intent = new Intent(context, PersonalDataActivity.class);
        intent.putExtra("userId", userId);
        context.startActivity(intent);
    }
}
