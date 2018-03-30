package com.power.mercenary.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.power.mercenary.R;
import com.power.mercenary.adapter.MsgAdapter;
import com.power.mercenary.adapter.MyFollowAdapter;
import com.power.mercenary.adapter.SiliaoAdapter;
import com.power.mercenary.base.BaseFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    MsgAdapter adapter;
    ArrayList<String> mList=new ArrayList<>();

    @BindView(R.id.mRecycler_msg)
    RecyclerView mRecycler_msg;

    @BindView(R.id.springView_msg)
    SpringView springView_msg;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, null);

        ButterKnife.bind(MessageFragment.this,view);
        left_back.setVisibility(View.INVISIBLE);
        title_text.setText("消息");
        initRenwutj();
        if (mList.size()<=0){
            mList.add("");
            mList.add("");
            mList.add("");
            mList.add("");
            mList.add("");
            mList.add("");
            mList.add("");
            mList.add("");
            mList.add("");
            mList.add("");
            mList.add("");
        }
        mRecycler_msg.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycler_msg.setNestedScrollingEnabled(false);
        jiuguan_ll.setOnClickListener(this);
        siliao_ll.setOnClickListener(this);
        renwutjLl.setOnClickListener(this);
        tongchengLl.setOnClickListener(this);
        initRefresh();
        return view;
    }

    //下拉刷新
    private void initRefresh() {
        //DefaultHeader/Footer是SpringView已经实现的默认头/尾之一
        //更多还有MeituanHeader、AliHeader、RotationHeader等如上图7种
        springView_msg.setType(SpringView.Type.FOLLOW);
        springView_msg.setHeader(new DefaultHeader(mContext));
        springView_msg.setFooter(new DefaultFooter(mContext));
        springView_msg.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
//                Toast.makeText(mContext,"下拉刷新中",Toast.LENGTH_SHORT).show();
                // list.clear();
                // 网络请求;
                // mStarFragmentPresenter.queryData();
                //一分钟之后关闭刷新的方法
                finishFreshAndLoad();
            }

            @Override
            public void onLoadmore() {
//                Toast.makeText(mContext,"玩命加载中...",Toast.LENGTH_SHORT).show();
                finishFreshAndLoad();
            }
        });
    }

    /**
     * 关闭加载提示
     */
    private void finishFreshAndLoad() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                springView_msg.onFinishFreshAndLoad();
            }
        }, 2000);
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
        siliao_tv.setTextColor(getResources().getColor(R.color.textColor));
        indicator_siliao.setBackgroundColor(getResources().getColor(R.color.concrete));
        initTongchengData();
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
        initJiuguanData();
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
        initSiliaoData();
    }

    private void initRenwutjData() {
        MsgAdapter changegameAdapter = new MsgAdapter(R.layout.msg_item_view, mList);
        mRecycler_msg.setAdapter(changegameAdapter);
    }

    private void initTongchengData() {
        MsgAdapter changegameAdapter = new MsgAdapter(R.layout.msg_item_view, mList);
        mRecycler_msg.setAdapter(changegameAdapter);
    }

    private  void initJiuguanData(){

        MsgAdapter changegameAdapter = new MsgAdapter(R.layout.msg_item_view, mList);
        mRecycler_msg.setAdapter(changegameAdapter);


    }

    private void initSiliaoData(){


        SiliaoAdapter changegameAdapter = new SiliaoAdapter(R.layout.siliao_msg_item_view, mList);
        mRecycler_msg.setAdapter(changegameAdapter);

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
