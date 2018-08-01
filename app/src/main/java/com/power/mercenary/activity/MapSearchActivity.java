package com.power.mercenary.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.power.mercenary.R;
import com.power.mercenary.base.BaseActivity;
import com.power.mercenary.bean.PoiBean;
import com.power.mercenary.data.EventRefreshContants;
import com.power.mercenary.utils.MyLocationListenner;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapSearchActivity extends BaseActivity implements OnGetPoiSearchResultListener {

    @BindView(R.id.title_back_iv)
    ImageView titleBackIv;
    @BindView(R.id.title_content_right_tv)
    TextView titleContentRightTv;
    @BindView(R.id.bmapView)
    MapView bmapView;
    @BindView(R.id.map_list_recycler)
    RecyclerView mapListRecycler;
    @BindView(R.id.search_tv)
    EditText searchTv;
    private LocationClient mLocClient;
    private MyLocationListenner myListener = new MyLocationListenner();
    private BaiduMap mBaiduMap;
    private List<PoiBean> mPoiinfo = new ArrayList<>();
    private int scrollPosition;
    private BiaoqianAdapter biaoqianAdapter;
    private String searchContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_address);
        ButterKnife.bind(this);
        searchContent = getIntent().getStringExtra("searchContent");
        searchTv.setText(searchContent);
        initMap();
        searchTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(searchContent)){
                    mLocClient.registerLocationListener(new BDLocationListener() {
                        @Override
                        public void onReceiveLocation(BDLocation bdLocation) {
                            if (!TextUtils.isEmpty(searchContent)){
                                searchNeayBy(searchContent,bdLocation.getLongitude(), bdLocation.getLatitude());
                            }

                        }
                    });
                }
            }
        });
    }

    @OnClick({R.id.title_back_iv, R.id.title_content_right_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back_iv:
                finish();
                break;
            case R.id.title_content_right_tv:
                if (!TextUtils.isEmpty(searchContent)){
                    mLocClient.registerLocationListener(new BDLocationListener() {
                        @Override
                        public void onReceiveLocation(BDLocation bdLocation) {
                            if (!TextUtils.isEmpty(searchContent)){
                                searchNeayBy(searchContent,bdLocation.getLongitude(), bdLocation.getLatitude());
                            }

                        }
                    });
                }
                break;
        }
    }

    private void initMap() {
        bmapView.removeViewAt(1);//隐藏logo
        bmapView.removeViewAt(2);//隐藏比例尺
        bmapView.showZoomControls(false);// 隐藏缩放控件
        mBaiduMap = bmapView.getMap();
        mBaiduMap.clear();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(17).build()));   // 设置级别

        // 定位初始化
        mLocClient = new LocationClient(getApplicationContext()); // 声明LocationClient类
        mLocClient.registerLocationListener(myListener);// 注册定位监听接口

        /**
         * 设置定位参数
         */
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 设置定位模式
//		option.setScanSpan(5000);// 设置发起定位请求的间隔时间,ms
        option.setNeedDeviceDirect(true);// 设置返回结果包含手机的方向
        option.setOpenGps(true);
        option.setAddrType("all");// 返回的定位结果包含地址信息
        option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
        option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
        mLocClient.setLocOption(option);
        mLocClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                if (!TextUtils.isEmpty(searchContent)){
                    searchNeayBy(searchContent,bdLocation.getLongitude(), bdLocation.getLatitude());
                }

            }
        });
        mLocClient.start(); // 调用此方法开始定位


    }

    private void searchNeayBy(String searchContent,double mlongitude, double mlatitude) {
        // POI初始化搜索模块，注册搜索事件监听
        PoiSearch mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);
        PoiNearbySearchOption poiNearbySearchOption = new PoiNearbySearchOption();

        poiNearbySearchOption.keyword(searchContent);
        poiNearbySearchOption.location(new LatLng(mlatitude, mlongitude));
        poiNearbySearchOption.radius(500);  // 检索半径，单位是米
        poiNearbySearchOption.pageCapacity(20);  // 默认每页10条
        mPoiSearch.searchNearby(poiNearbySearchOption);  // 发起附近检索请求
    }

    @Override
    public void onGetPoiResult(PoiResult result) {
        // 获取POI检索结果
        if (result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {// 没有找到检索结果
            Toast.makeText(MapSearchActivity.this, "未找到结果", Toast.LENGTH_LONG).show();
            return;
        }

        if (result.error == SearchResult.ERRORNO.NO_ERROR) {// 检索结果正常返回
            if (result != null) {
                if (result.getAllPoi() != null && result.getAllPoi().size() > 0) {
                    List<PoiInfo> allPoi = result.getAllPoi();
                    for (int i = 0; i < allPoi.size(); i++) {
                        PoiBean poiBean = new PoiBean();
                        poiBean.setPoiInfo(allPoi.get(i));
                        mPoiinfo.add(poiBean);
                    }
                    mapListRecycler.setNestedScrollingEnabled(false);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    mapListRecycler.setLayoutManager(linearLayoutManager);
                    biaoqianAdapter = new BiaoqianAdapter(R.layout.map_location_layout, mPoiinfo);
                    mapListRecycler.setAdapter(biaoqianAdapter);
                    mPoiinfo.get(0).setChecked(true);
                    biaoqianAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            mPoiinfo.get(scrollPosition).setChecked(false);
                            scrollPosition = position;
                            mPoiinfo.get(scrollPosition).setChecked(true);
                            biaoqianAdapter.notifyDataSetChanged();
                            String address = mPoiinfo.get(scrollPosition).getPoiInfo().address;
                            EventBus.getDefault().postSticky(new EventRefreshContants(address));
                            finish();
                        }
                    });
                }
            }
        }

    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

    }

    private class BiaoqianAdapter extends BaseQuickAdapter<PoiBean, BaseViewHolder> {

        public BiaoqianAdapter(int layoutResId, @Nullable List<PoiBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, PoiBean item) {
            ImageView img_ischecked = helper.getView(R.id.img_ischecked);
            if (item.isChecked()) {
                img_ischecked.setVisibility(View.VISIBLE);
            } else {
                img_ischecked.setVisibility(View.INVISIBLE);
            }
            helper.setText(R.id.tv_location_address, item.getPoiInfo().address + "");
        }
    }
}
