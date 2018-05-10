package com.power.mercenary.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.TextView;

import com.mcxtzhang.indexlib.IndexBar.widget.IndexBar;
import com.mcxtzhang.indexlib.suspension.SuspensionDecoration;
import com.power.mercenary.R;
import com.power.mercenary.adapter.CityAdapter;
import com.power.mercenary.adapter.HeaderRecyclerAndFooterWrapperAdapter;
import com.power.mercenary.adapter.ViewHolder;
import com.power.mercenary.base.BaseActivity;
import com.power.mercenary.bean.CityBean;
import com.power.mercenary.utils.DividerItemDecoration;
import com.power.mercenary.utils.SoftKeyboardTool;

import java.util.ArrayList;
import java.util.List;

public class LocationActivity extends BaseActivity {

    private RecyclerView cityRecycler;
    private LinearLayoutManager mManager;
    private HeaderRecyclerAndFooterWrapperAdapter mHeaderAdapter;
    private SuspensionDecoration mDecoration;
    /**
     * 右侧边栏导航区域
     */
    private IndexBar mIndexBar;

    /**
     * 显示指示器DialogText
     */
    private TextView mTvSideBarHint;
    private List<CityBean> cityList;
    private CityAdapter cityAdapter;
    private List<String> hotList;
    private TextView locationTv;
    private EditText searchEt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        initView();
    }

    private void initView() {
        cityRecycler = (RecyclerView) findViewById(R.id.city_recycler);
        cityRecycler.setLayoutManager(mManager = new LinearLayoutManager(mContext));
        cityAdapter = new CityAdapter(this,cityList);
        mHeaderAdapter = new HeaderRecyclerAndFooterWrapperAdapter(cityAdapter) {

            @Override
            protected void onBindHeaderHolder(ViewHolder holder, int headerPos, int layoutId, Object o) {
                searchEt = holder.getView(R.id.search_et);
                locationTv = holder.getView(R.id.location_tv);
            }
        };
        mHeaderAdapter.setHeaderView(R.layout.item_head_mylocation, "");
        cityRecycler.setAdapter(mHeaderAdapter);
        cityRecycler.addItemDecoration(mDecoration = new SuspensionDecoration(this, cityList).setHeaderViewCount(mHeaderAdapter.getHeaderViewCount()));

        //如果add两个，那么按照先后顺序，依次渲染。
        cityRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

        //使用indexBar
        mTvSideBarHint = (TextView) findViewById(R.id.tvSideBarHint);//HintTextView
        mIndexBar = (IndexBar) findViewById(R.id.indexBar);//IndexBar

        mIndexBar.setmPressedShowTextView(mTvSideBarHint)//设置HintTextView
                .setNeedRealIndex(true)//设置需要真实的索引
                .setmLayoutManager(mManager);//设置RecyclerView的LayoutManager
        initDatas(getResources().getStringArray(R.array.provinces));
    }

    /**
     * 组织数据源
     * @param data
     * @return
     */
    private void initDatas(final String[] data) {
        cityList = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            CityBean cityBean = new CityBean();
            cityBean.setCity(data[i]);//设置城市名称
            cityList.add(cityBean);
        }

        mIndexBar.setmSourceDatas(cityList)//设置数据
                .setHeaderViewCount(mHeaderAdapter.getHeaderViewCount())//设置HeaderView数量
                .invalidate();

        cityAdapter.setDatas(cityList);
        mHeaderAdapter.notifyDataSetChanged();
        mDecoration.setmDatas(cityList);
    }

}
