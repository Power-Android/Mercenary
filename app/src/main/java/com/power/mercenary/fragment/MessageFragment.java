package com.power.mercenary.fragment;

import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.service.carrier.CarrierMessagingService;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.power.mercenary.R;
import com.power.mercenary.adapter.frag.FragmentVPagerAdapter;
import com.power.mercenary.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Conversation.ConversationType;

import static io.rong.imlib.model.Conversation.ConversationType.*;

/**
 * Created by power on 2018/3/21.
 */

public class MessageFragment extends BaseFragment implements View.OnClickListener{
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
    @BindView(R.id.jiuguan_tv)
    TextView jiuguan_tv;
    @BindView(R.id.indicator_jiuguan)
    View indicator_jiuguan;
    @BindView(R.id.siliao_tv)
    TextView siliao_tv;
    @BindView(R.id.indicator_siliao)
    View indicator_siliao;
    @BindView(R.id.jiuguan_ll)
    LinearLayout jiuguan_ll;
    @BindView(R.id.siliao_ll)
    LinearLayout siliao_ll;
    private FragmentVPagerAdapter vPagerAdapter;
    private List<Fragment> fragments;
    private ViewPager viewPager;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, null);

        ButterKnife.bind(MessageFragment.this,view);
        left_back.setVisibility(View.INVISIBLE);
        title_text.setText("消息");

        viewPager = view.findViewById(R.id.frag_message_viewPager);

        fragments = new ArrayList<>();
        fragments.add(new MessageTaskFragment());
        fragments.add(new MessagePrivateFragment());
        fragments.add(new MessageTavernFragment());
        fragments.add(new MessageSystemFragment());

        vPagerAdapter = new FragmentVPagerAdapter(getChildFragmentManager(), fragments);
        viewPager.setAdapter(vPagerAdapter);

        viewPager.setOffscreenPageLimit(4);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 2://酒馆
                        initJiuguan();
                        break;
                    case 3://私聊
                        initSiliao();
                        break;
                    case 0://任务推荐
                        initRenwutj();
                        break;
                    case 1://同城
                        initTongcheng();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        initRenwutj();

        jiuguan_ll.setOnClickListener(this);
        siliao_ll.setOnClickListener(this);
        renwutjLl.setOnClickListener(this);
        tongchengLl.setOnClickListener(this);

        //获取未读消息数
        RongIMClient.getInstance().getTotalUnreadCount(new RongIMClient.ResultCallback<Integer>() {
            @Override
            public void onSuccess(Integer integer) {
                Integer mMessageUnreadCount = integer;
                Log.i("tttttttttttttt", "onSuccess: "+mMessageUnreadCount);
            }
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
            }
        });
        return view;
    }
    //任务推荐Tab
    private void initRenwutj() {
        renwutjTv.setTextColor(getResources().getColor(R.color.colorPrimary));
        indicatorRenwutj.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        tongchengTv.setTextColor(getResources().getColor(R.color.textColor));
        indicatorTongcheng.setBackgroundColor(getResources().getColor(R.color.concrete));
        jiuguan_tv.setTextColor(getResources().getColor(R.color.textColor));
        indicator_jiuguan.setBackgroundColor(getResources().getColor(R.color.concrete));
        siliao_tv.setTextColor(getResources().getColor(R.color.textColor));
        indicator_siliao.setBackgroundColor(getResources().getColor(R.color.concrete));
        viewPager.setCurrentItem(0);
    }

    //同城Tab
    private void initTongcheng() {
        renwutjTv.setTextColor(getResources().getColor(R.color.textColor));
        indicatorRenwutj.setBackgroundColor(getResources().getColor(R.color.concrete));
        tongchengTv.setTextColor(getResources().getColor(R.color.colorPrimary));
        indicatorTongcheng.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        jiuguan_tv.setTextColor(getResources().getColor(R.color.textColor));
        indicator_jiuguan.setBackgroundColor(getResources().getColor(R.color.concrete));
        siliao_tv.setTextColor(getResources().getColor(R.color.textColor));
        indicator_siliao.setBackgroundColor(getResources().getColor(R.color.concrete));
        viewPager.setCurrentItem(1);
    }

    private void initJiuguan() {
        renwutjTv.setTextColor(getResources().getColor(R.color.textColor));
        indicatorRenwutj.setBackgroundColor(getResources().getColor(R.color.concrete));
        tongchengTv.setTextColor(getResources().getColor(R.color.textColor));
        indicatorTongcheng.setBackgroundColor(getResources().getColor(R.color.concrete));
        jiuguan_tv.setTextColor(getResources().getColor(R.color.colorPrimary));
        indicator_jiuguan.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        siliao_tv.setTextColor(getResources().getColor(R.color.textColor));
        indicator_siliao.setBackgroundColor(getResources().getColor(R.color.concrete));
        viewPager.setCurrentItem(2);
    }

    private void initSiliao() {
        renwutjTv.setTextColor(getResources().getColor(R.color.textColor));
        indicatorRenwutj.setBackgroundColor(getResources().getColor(R.color.concrete));
        tongchengTv.setTextColor(getResources().getColor(R.color.textColor));
        indicatorTongcheng.setBackgroundColor(getResources().getColor(R.color.concrete));
        jiuguan_tv.setTextColor(getResources().getColor(R.color.textColor));
        indicator_jiuguan.setBackgroundColor(getResources().getColor(R.color.concrete));
        siliao_tv.setTextColor(getResources().getColor(R.color.colorPrimary));
        indicator_siliao.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        viewPager.setCurrentItem(3);
    }

    @Override
    protected void initLazyData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.jiuguan_ll://酒馆
                initJiuguan();
                break;
            case R.id.siliao_ll://私聊
                initSiliao();
                break;
            case R.id.renwutj_ll://任务推荐
                initRenwutj();
                break;
            case R.id.tongcheng_ll://同城
                initTongcheng();
                break;
        }
    }
}

//    private void initRenwutjData() {
//        MsgAdapter changegameAdapter = new MsgAdapter(R.layout.msg_item_view, mList);
//        mRecycler_msg.setAdapter(changegameAdapter);
//    }
//
//    private void initTongchengData() {
//        MsgAdapter changegameAdapter = new MsgAdapter(R.layout.msg_item_view, mList);
//        mRecycler_msg.setAdapter(changegameAdapter);
//    }
//
//    private  void initJiuguanData(){
//
//        MsgAdapter changegameAdapter = new MsgAdapter(R.layout.msg_item_view, mList);
//        mRecycler_msg.setAdapter(changegameAdapter);
//
//
//    }
//
//    private void initSiliaoData(){
//
//
//        SiliaoAdapter changegameAdapter = new SiliaoAdapter(R.layout.siliao_msg_item_view, mList);
//        mRecycler_msg.setAdapter(changegameAdapter);
//
//    }
