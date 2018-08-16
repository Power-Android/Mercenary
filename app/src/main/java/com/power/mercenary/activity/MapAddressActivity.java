package com.power.mercenary.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapAddressActivity extends BaseActivity implements OnGetPoiSearchResultListener {

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
    private LocationManager locationManager;
    private BaiduMap mBaiduMap;
    private double mlongitude;
    private double mlatitude;
    private List<PoiBean> mPoiinfo = new ArrayList<>();
    private int scrollPosition;
    private BiaoqianAdapter biaoqianAdapter;
    private LocationManager lm;//【位置管理】
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_address);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
//        checkPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)
//         checkPermissionGranted(Manifest.permission.ACCESS_COARSE_LOCATION)
//        if (isLocationEnabled()){
//            Toast.makeText(mContext, "未开启定位权限", Toast.LENGTH_SHORT).show();
//        }else {

//        }
        lm = (LocationManager) this.getSystemService(this.LOCATION_SERVICE);
        boolean ok = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (ok) {//开了定位服务
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                // 没有权限，申请权限。
//                        Toast.makeText(this, "没有权限", Toast.LENGTH_SHORT).show();
                Intent intent =new Intent();
                //定位服务页面
                intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                //如果页面无法打开，进入设置页面
//                intent.setAction(Settings.ACTION_SETTINGS);
            } else {
                initMap();
                // 有权限了，去放肆吧。
//                        Toast.makeText(this, "有权限", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "系统检测到未开启GPS定位服务", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent, 1315);
            finish();
        }

    }
//    private boolean checkPermissionGranted(String permission) {
//        return this.checkPermission(permission, Process.myPid(), Process.myUid()) == PackageManager.PERMISSION_GRANTED;
//    }
public boolean isLocationEnabled() {
        int locationMode = 0;
        String locationProviders;
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                 try {
                         locationMode = Settings.Secure.getInt(getContentResolver(), Settings.Secure.LOCATION_MODE);
                     } catch (Settings.SettingNotFoundException e) {
                         e.printStackTrace();
                         return false;
                     }
                 return locationMode != Settings.Secure.LOCATION_MODE_OFF;
             } else {
                 locationProviders = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                 return !TextUtils.isEmpty(locationProviders);
             }
     }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.title_back_iv, R.id.title_content_right_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back_iv:
                finish();
                break;
            case R.id.title_content_right_tv:
                Intent intent = new Intent(mContext, MapSearchActivity.class);
                intent.putExtra("searchContent", searchTv.getText().toString());
                startActivity(intent);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEvent(EventRefreshContants eventBean) {
        if (eventBean.getmAddress() != null) {
            finish();
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
                searchNeayBy(bdLocation.getLongitude(), bdLocation.getLatitude());

                LatLng ll = new LatLng(bdLocation.getLatitude(),
                        bdLocation.getLongitude());
                mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(ll));

            }
        });
        mLocClient.start(); // 调用此方法开始定位


    }

    private void searchNeayBy(double mlongitude, double mlatitude) {
        // POI初始化搜索模块，注册搜索事件监听
        PoiSearch mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);
        PoiNearbySearchOption poiNearbySearchOption = new PoiNearbySearchOption();

        poiNearbySearchOption.keyword("大厦");
        poiNearbySearchOption.location(new LatLng(mlatitude, mlongitude));
        poiNearbySearchOption.radius(500);  // 检索半径，单位是米
        poiNearbySearchOption.pageCapacity(20);  // 默认每页10条
        mPoiSearch.searchNearby(poiNearbySearchOption);  // 发起附近检索请求
    }

    @Override
    public void onGetPoiResult(PoiResult result) {
        // 获取POI检索结果
        if (result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {// 没有找到检索结果
            Toast.makeText(MapAddressActivity.this, "未找到结果", Toast.LENGTH_LONG).show();
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
