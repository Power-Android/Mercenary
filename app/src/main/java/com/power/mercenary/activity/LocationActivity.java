package com.power.mercenary.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.mcxtzhang.indexlib.IndexBar.widget.IndexBar;
import com.mcxtzhang.indexlib.suspension.SuspensionDecoration;
import com.power.mercenary.R;
import com.power.mercenary.adapter.CityAdapter;
import com.power.mercenary.adapter.HeaderRecyclerAndFooterWrapperAdapter;
import com.power.mercenary.adapter.ViewHolder;
import com.power.mercenary.base.BaseActivity;
import com.power.mercenary.bean.CityBean;
import com.power.mercenary.bean.CitySelectBean;
import com.power.mercenary.bean.LocationBean;
import com.power.mercenary.data.EventConstants;
import com.power.mercenary.event.EventUtils;
import com.power.mercenary.presenter.LocationPresenter;
import com.power.mercenary.utils.DividerItemDecoration;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class LocationActivity extends BaseActivity implements LocationPresenter.LocationCallBack, CityAdapter.OnItemClickListener {

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

    private TextView title;

    private LocationPresenter presenter;

    private boolean isPerform = false;

    private LocationClient mLocClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        initView();
    }

    private void initView() {
        cityList = new ArrayList<>();

        title = (TextView) findViewById(R.id.title_content_tv);

        cityRecycler = (RecyclerView) findViewById(R.id.city_recycler);
        cityRecycler.setLayoutManager(mManager = new LinearLayoutManager(mContext));
        cityAdapter = new CityAdapter(this, cityList);
        cityAdapter.setOnItemClickListener(this);
        mHeaderAdapter = new HeaderRecyclerAndFooterWrapperAdapter(cityAdapter) {

            @Override
            protected void onBindHeaderHolder(ViewHolder holder, int headerPos, int layoutId, Object o) {
                searchEt = holder.getView(R.id.search_et);
                locationTv = holder.getView(R.id.location_tv);
                locationTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CitySelectBean bean = new CitySelectBean();
                        bean.cityName = locationTv.getText().toString();
                        EventBus.getDefault().post(new EventUtils(EventConstants.TYPE_CITY_SELECT, bean));
                        finish();
                    }
                });

                searchEt.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (!isPerform) {
                            isPerform = true;
                            presenter.getCityList(searchEt.getText().toString());
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                searchEt.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if(keyCode == KeyEvent.KEYCODE_DEL && searchEt.getText().toString().length() == 0 && isPerform) {
                            presenter.getCityList(searchEt.getText().toString());
                            return true;
                        }
                        return false;
                    }
                });
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

        initLocation();

        cityList.clear();
        presenter = new LocationPresenter(this, this);
        presenter.getCityList("");

        cityRecycler.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:// 空闲状态

                        break;
                    case RecyclerView.SCROLL_STATE_SETTLING:// 滚动状态关闭软键盘
                        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                                .hideSoftInputFromWindow(getCurrentFocus()
                                                .getWindowToken(),
                                        InputMethodManager.HIDE_NOT_ALWAYS);
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING:// 触摸后滚动关闭软键盘
                        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                                .hideSoftInputFromWindow(getCurrentFocus()
                                                .getWindowToken(),
                                        InputMethodManager.HIDE_NOT_ALWAYS);
                        break;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });


        findViewById(R.id.title_back_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initLocation() {
        mLocClient = new LocationClient(this);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 设置定位模式
        option.setNeedDeviceDirect(true);// 设置返回结果包含手机的方向
        option.setOpenGps(true);
        option.setAddrType("all");// 返回的定位结果包含地址信息
        option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
        option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
        option.setIsNeedLocationPoiList(true);
        mLocClient.setLocOption(option);
        mLocClient.start();
        mLocClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                double longitude = bdLocation.getLongitude();
                double latitude = bdLocation.getLatitude();
                String country = bdLocation.getCountry();
                String city = bdLocation.getCity();
                title.setText("当前城市-" + city.replace("市", ""));
                locationTv.setText(city.replace("市", "")+"市");
            }
        });
    }

    /**
     * 组织数据源
     *
     *
     * 0
     *
     * @return
     */
    private void initDatas(List<LocationBean> datas) {

        for (int i = 0; i < datas.size(); i++) {
            CityBean cityBean = new CityBean();
            cityBean.setCity(datas.get(i).getCity());//设置城市名称
            cityBean.setId(datas.get(i).getId());
            cityList.add(cityBean);
        }

        mIndexBar.setNeedRealIndex(true)
                .setmSourceDatas(cityList)//设置数据
                .setHeaderViewCount(mHeaderAdapter.getHeaderViewCount())//设置HeaderView数量
                .invalidate();

        cityAdapter.setDatas(cityList);
        mHeaderAdapter.notifyDataSetChanged();
        mDecoration.setmDatas(cityList);
    }

    @Override
    public void getCityList(List<LocationBean> datas) {
        isPerform = false;
        cityList.clear();
        if (datas != null) {
            initDatas(datas);
        }
    }

    @Override
    public void getCityListFail() {
        isPerform = false;
    }

    @Override
    public void onItemClickListener(String cityName, String cityId) {
        CitySelectBean bean = new CitySelectBean();
        bean.cityName = cityName;
        bean.cityId = cityId;
        EventBus.getDefault().post(new EventUtils(EventConstants.TYPE_CITY_SELECT, bean));
        finish();
    }
}
