package com.power.mercenary.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.power.mercenary.R;
import com.power.mercenary.adapter.MyFollowAdapter;
import com.power.mercenary.base.BaseFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/27.
 */

public class PeopleFragment extends BaseFragment {


    @BindView(R.id.mRecycler)
    RecyclerView mRecycler;

    @BindView(R.id.springView)
    SpringView springView;

    ArrayList<String> mList=new ArrayList<>();


    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = View.inflate(getActivity(),R.layout.fragment_ren,null);
        ButterKnife.bind(this, view);

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
        mRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycler.setNestedScrollingEnabled(false);
        MyFollowAdapter changegameAdapter = new MyFollowAdapter(R.layout.myfollow_item_view, mList);
        mRecycler.setAdapter(changegameAdapter);
        initRefresh();

        return view;
    }

    @Override
    protected void initLazyData() {

    }

    //下拉刷新
    private void initRefresh() {
        //DefaultHeader/Footer是SpringView已经实现的默认头/尾之一
        //更多还有MeituanHeader、AliHeader、RotationHeader等如上图7种
        springView.setType(SpringView.Type.FOLLOW);
        springView.setHeader(new DefaultHeader(mContext));
        springView.setFooter(new DefaultFooter(mContext));
        springView.setListener(new SpringView.OnFreshListener() {
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
                springView.onFinishFreshAndLoad();
            }
        }, 2000);
    }
}
