package com.power.mercenary.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.power.mercenary.MyApplication;
import com.power.mercenary.R;
import com.power.mercenary.activity.CommitUserInfoActivity;
import com.power.mercenary.activity.GRTaskDetailsActivity;
import com.power.mercenary.activity.GZTaskDetailsActivity;
import com.power.mercenary.activity.HomeSearchActivity;
import com.power.mercenary.activity.LocationActivity;
import com.power.mercenary.activity.PTTaskDetailsActivity;
import com.power.mercenary.activity.PersonalDataActivity;
import com.power.mercenary.activity.PostDetailActivity;
import com.power.mercenary.activity.SHTaskDetailsActivity;
import com.power.mercenary.activity.TaskListActivity;
import com.power.mercenary.activity.WebActivity;
import com.power.mercenary.activity.WorkPubActivity;
import com.power.mercenary.base.BaseFragment;
import com.power.mercenary.bean.BannerBean;
import com.power.mercenary.bean.CitySelectBean;
import com.power.mercenary.bean.HomHotBean;
import com.power.mercenary.bean.HotSearchBean;
import com.power.mercenary.bean.MainTaskBean;
import com.power.mercenary.bean.NineGridTestModel;
import com.power.mercenary.bean.ObtainUserInfoBean;
import com.power.mercenary.bean.Testbean;
import com.power.mercenary.data.EventConstants;
import com.power.mercenary.event.EventUtils;
import com.power.mercenary.http.OkhtttpUtils;
import com.power.mercenary.presenter.HomeSearchPresenter;
import com.power.mercenary.presenter.MainPresenter;
import com.power.mercenary.utils.BannerUtils;
import com.power.mercenary.utils.MercenaryUtils;
import com.power.mercenary.utils.MyUtils;
import com.power.mercenary.utils.SpUtils;
import com.power.mercenary.utils.TUtils;
import com.power.mercenary.utils.Urls;
import com.power.mercenary.view.BaseDialog;
import com.power.mercenary.view.MyPageIndicator;
import com.power.mercenary.view.NineGridTestLayout;
import com.power.mercenary.view.PageGridView;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

//import com.power.mercenary.activity.TestActivity;
//import com.power.mercenary.activity.TestActivity;

/**
 * Created by power on 2018/3/21.
 */

public class HomeFragment extends BaseFragment implements MainPresenter.MainCallBack, HomeSearchPresenter.HomeCallBack {
    @BindView(R.id.location_tv)
    TextView locationTv;
    @BindView(R.id.search_tv)
    TextView searchTv;
    @BindView(R.id.banner)
    Banner banner;
    Unbinder unbinder;
    @BindView(R.id.pageGridView)
    PageGridView pageGridView;
    @BindView(R.id.pageGridView2)
    GridView pageGridView2;
    @BindView(R.id.pageindicator)
    MyPageIndicator pageindicator;
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
    @BindView(R.id.tuijian_recycler)
    RecyclerView tuijianRecycler;
    @BindView(R.id.post_rl)
    RelativeLayout postRl;
    @BindView(R.id.post_recycler)
    RecyclerView postRecycler;
    Unbinder unbinder1;
    private int width;

    private BaseDialog mDialog;
    private BaseDialog.Builder mBuilder;

    private MainPresenter mainPresenter;
    private List<MainTaskBean.TuijianBean> tuijianList;
    private List<MainTaskBean.TuijianBean> taskList = new ArrayList<>();
    private List<MainTaskBean.TuijianBean> tongchengList = new ArrayList<>();
    private TuijianAdapter tuijianAdapter;

    private List<NineGridTestModel> mList = new ArrayList<>();
    private List<String> mUrlList = new ArrayList<>();

    private boolean isShow = false;

    private String[] mUrls = new String[]{"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=202447557,2967022603&fm=27&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=104961686,3757525983&fm=27&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=569334953,1638673400&fm=27&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2043305675,3979419376&fm=200&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=266745161,658804068&fm=27&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=222615259,2947254622&fm=27&gp=0.jpg",
            "http://img1.imgtn.bdimg.com/it/u=950771854,530317119&fm=27&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=2557022909,3736713361&fm=27&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=1830359176,654163576&fm=200&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=4193964417,1586871857&fm=27&gp=0.jpg",
    };
    private MainTaskBean taskBean;

    private List<BannerBean> bannerBeans;

    private List<String> nextList = new ArrayList<>();

    private int oneAndTwo = 0;

    private int PAOTUI = 101, SHENGHUO = 102, GERENDINGZHI = 103, GONGZUO = 104, JIANKANG = 105;
    private HomeSearchPresenter presenter;
    private GridPage2Adapter gridPageAdapter2;
    private ArrayList<Testbean> gridPageList2;
    private int stateNum = -1;


    private static final String TAG = "HomeFragment";

    private TextView tvConfirm;

    private TextView tvCancle;

    private PopupWindow window;
    private LocationClient mLocClient;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        unbinder = ButterKnife.bind(this, view);
        presenter = new HomeSearchPresenter(getActivity(), this);
        presenter.getHotInfo(1);
        mainPresenter = new MainPresenter(getActivity(), this);
        mainPresenter.getTaskList(locationTv.getText().toString());
        mainPresenter.getBannerList(1);
        initData();
        EventBus.getDefault().register(this);
        return view;
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRecevierEvent(EventUtils event) {
        switch (event.getType()) {
            case EventConstants.TYPE_CITY_SELECT:
                CitySelectBean selectBean = (CitySelectBean) event.getData();
                locationTv.setText(selectBean.cityName);
                tuijianList.clear();
                mainPresenter.getTaskList(selectBean.cityName);
                break;
        }
    }

    @Override
    protected void initLazyData() {




    }

    private void initData() {
                //---------------------------设置Banner----------------------------------------
        List<Integer> baaaneList = new ArrayList<>();
        baaaneList.add(R.drawable.test_banner);
        baaaneList.add(R.drawable.test_banner);
        baaaneList.add(R.drawable.test_banner);

        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                WebActivity.invoke(mContext, bannerBeans.get(position).getUrl(), "详情");
            }
        });
        //----------------------------结束---------------------------------------------

        //----------------------------横向分页------------------------------------------
        gridPageList2 = new ArrayList<>();
        List<Testbean> gridPageList = new ArrayList<>();
        Testbean testbean0 = new Testbean();
        testbean0.setImg(R.drawable.pt_iv);
        testbean0.setTitle("跑腿");
        gridPageList.add(testbean0);
        Testbean testbean1 = new Testbean();
        testbean1.setImg(R.drawable.sh_iv);
        testbean1.setTitle("生活");
        gridPageList.add(testbean1);
        Testbean testbean2 = new Testbean();
        testbean2.setImg(R.drawable.grdz_iv);
        testbean2.setTitle("个人定制");
        gridPageList.add(testbean2);
        Testbean testbean3 = new Testbean();
        testbean3.setImg(R.drawable.gz_iv);
        testbean3.setTitle("工作");
        gridPageList.add(testbean3);
        Testbean testbean4 = new Testbean();
        testbean4.setImg(R.drawable.sx_iv);
        testbean4.setTitle("身心");
        gridPageList.add(testbean4);
        Testbean testbean5 = new Testbean();
        testbean5.setImg(R.drawable.qt_iv);
        testbean5.setTitle("其他");
        gridPageList.add(testbean5);

        width = getResources().getDisplayMetrics().widthPixels / 5;
        pageGridView.setNestedScrollingEnabled(false);
        GridPageAdapter gridPageAdapter = new GridPageAdapter(gridPageList);
        pageGridView.setAdapter(gridPageAdapter);
        pageGridView.setOnItemClickListener(gridPageAdapter);
        //设置分页指示器
        pageGridView.setPageIndicator(pageindicator);
        gridPageAdapter2 = new GridPage2Adapter(gridPageList2);
        pageGridView2.setAdapter(gridPageAdapter2);
        //----------------------------结束----------------------------------------------
        //----------------------------推荐----------------------------------------------

        tuijianRecycler.setNestedScrollingEnabled(false);
        tuijianRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        tuijianList = new ArrayList<>();
        tuijianAdapter = new TuijianAdapter(R.layout.item_tuijian_renwu, tuijianList);
        tuijianRecycler.setAdapter(tuijianAdapter);
        tuijianAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (tuijianList.get(position).getTask_type()) {
                    case "1":
                        Intent ptIntent = new Intent(getActivity(), PTTaskDetailsActivity.class);
                        ptIntent.putExtra("taskId", tuijianList.get(position).getId());
                        startActivity(ptIntent);
                        break;

                    case "2":
                    case "5":
                    case "6":
                        Intent shIntent = new Intent(getActivity(), SHTaskDetailsActivity.class);
                        shIntent.putExtra("taskId", tuijianList.get(position).getId());
                        startActivity(shIntent);
                        break;

                    case "3":
                        Intent grIntent = new Intent(getActivity(), GRTaskDetailsActivity.class);
                        grIntent.putExtra("taskId", tuijianList.get(position).getId());
                        startActivity(grIntent);
                        break;

                    case "4":
                        Intent gzIntent = new Intent(getActivity(), GZTaskDetailsActivity.class);
                        gzIntent.putExtra("taskId", tuijianList.get(position).getId());
                        startActivity(gzIntent);
                        break;
                }
            }
        });
        //----------------------------结束-----------------------------------------------
        //----------------------------热门帖子--------------------------------------------
//        NineGridTestModel model1 = new NineGridTestModel();
//        model1.urlList.add(mUrls[0]);
//        mList.add(model1);
//
//        NineGridTestModel model2 = new NineGridTestModel();
//        model2.urlList.add(mUrls[0]);
//        model2.urlList.add(mUrls[1]);
//        mList.add(model2);
//
//        NineGridTestModel model6 = new NineGridTestModel();
//        for (int i = 0; i < 9; i++) {
//            model6.urlList.add(mUrls[i]);
//        }
//        mList.add(model6);
//
//        NineGridTestModel model7 = new NineGridTestModel();
//        for (int i = 3; i < 7; i++) {
//            model7.urlList.add(mUrls[i]);
//        }
//        mList.add(model7);
//
//        NineGridTestModel model8 = new NineGridTestModel();
//        for (int i = 3; i < 6; i++) {
//            model8.urlList.add(mUrls[i]);
//        }
//        mList.add(model8);

        //-------------------------------结束-------——--------------------------------------------

        initRenwutj();

        String userToken = MyApplication.getUserToken();        //获取用户Token

        if (!TextUtils.isEmpty(userToken)) {         //效验用户Token是否为空 为空说明没有登录 没登陆不做任何请求

            //请求用户信息接口  判断是否为空 为空弹出框引导用户完善信息
            Map<String, String> map = new HashMap<>();

            map.put("token", userToken);

            OkhtttpUtils.getInstance().doPost("http://yb.dashuibei.com/index.php/Home/UserCenter/getinfo", map, new OkhtttpUtils.OkCallback() {
                @Override
                public void onFailure(Exception e) {

                }

                @Override
                public void onResponse(String json) {

                    Gson gson = new Gson();

                    ObtainUserInfoBean obtainUserInfoBean = gson.fromJson(json, ObtainUserInfoBean.class);

                    int code = obtainUserInfoBean.getCode();

                    if (code == 0) {

                        ObtainUserInfoBean.DataBean data = obtainUserInfoBean.getData();        //获取数据类
                        String head_img = data.getHead_img();       //用户头像
                        String mobile = data.getMobile();           //用户手机号
                        String age = data.getAge();                 //用户年龄
                        String nick_name = data.getNick_name();     //用户昵称
                        String name = data.getName();               //用户真实姓名
                        // ||为只有要一个条件满足 都会走下面的代码
                        if (
                                TextUtils.isEmpty(head_img)                  //效验头像是否为空
                                        || TextUtils.isEmpty(mobile)        //效验手机号是否为空
                                        || TextUtils.isEmpty(age)           //效验年龄是否为空
                                        || TextUtils.isEmpty(nick_name)     //效验昵称是否为空
                                        || TextUtils.isEmpty(name)          //效验真是姓名是否为空

                                ) {

                            //引导用户 完善信息

                            //加载布局
                            View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.go_perfect_myinfo_layout, null, false);
                            //findViewById
                            tvConfirm = contentView.findViewById(R.id.window_tv_sure);
                            tvCancle = contentView.findViewById(R.id.window_tv_cancle);
                            tvConfirm.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //Toast.makeText(getActivity(), "您点击了PropupWindow的文字", Toast.LENGTH_SHORT);

                                    window.dismiss();
                                    setBackgroundAlpha(1.0f);
                                    Intent intent = new Intent(getActivity(), CommitUserInfoActivity.class);
                                    startActivity(intent);

                                }
                            });

                            tvCancle.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //关闭PropupWindow
                                    window.dismiss();
                                    setBackgroundAlpha(1.0f);

                                }
                            });

                            //第一个：布局
                            // 第二个：布局的宽 自动填充
                            // 第三:布局的高 自动填充
                            //PropupWindow 的颜色

                            window = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            //是否响应外部点击事件
                            window.setOutsideTouchable(true);
                            window.setTouchable(true);
                            //PropupWindow 显示的地方
                            window.showAtLocation(contentView, Gravity.CENTER, 0, 0);

                            setBackgroundAlpha(0.8f);

                        }

                    }


                }
            });

        }
      initLocation();
    }

    private void initLocation() {
        mLocClient = new LocationClient(getActivity());
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
                locationTv.setText(city.replace("市", ""));
                SpUtils.putString(getActivity(),"address",city.replace("市", ""));

            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();



    }

    @Override
    public void getTaskList(MainTaskBean taskBean) {
//        this.taskBean = taskBean;
        if (taskBean == null)
            return;

        taskList.clear();
        tongchengList.clear();

        taskList.addAll(taskBean.getTuijian());
        tongchengList.addAll(taskBean.getSame_city());

        if (oneAndTwo == 1) {
            initTongchengData();
        } else {
            initRenwutjData();
        }
    }

    @Override
    public void getBannerList(List<BannerBean> datas) {
        bannerBeans = datas;
        List<String> bannerList = new ArrayList<>();
        if (datas != null) {
            for (int i = 0; i < datas.size(); i++) {
                bannerList.add(Urls.BASEIMGURL + datas.get(i).getPic());
            }
        }
        BannerUtils.startBanner(banner, bannerList);
    }

    @Override
    public void getHotInfo(List<HomHotBean> hotInfo) {
        postRecycler.setNestedScrollingEnabled(false);
        postRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        MyAdapter myAdapter = new MyAdapter(R.layout.item_circle_friend, hotInfo);
        postRecycler.setAdapter(myAdapter);
    }

    @Override
    public void getHotSearchInfo(List<HotSearchBean> hotInfo) {

    }

    @Override
    public void getTaskInfo(List<MainTaskBean.TuijianBean> hotInfo) {

    }

    private class MyAdapter extends BaseQuickAdapter<HomHotBean, BaseViewHolder> {

        public MyAdapter(@LayoutRes int layoutResId, @Nullable List<HomHotBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, final HomHotBean item) {

            helper.setText(R.id.tv_name, item.getPost_user_name());
            helper.setText(R.id.tv_time, MyUtils.getDateToStringTime(item.getCreate_time()));
            helper.setText(R.id.tv_content, item.getPost_content());
            helper.setText(R.id.tv_pinglun, item.getCount());
            Glide.with(mContext).load(Urls.BASEIMGURL + item.getPost_user_headimg()).into((ImageView) helper.getView(R.id.item_image));

            NineGridTestLayout nineGridlayout = helper.getView(R.id.nine_gridlayout);
            nineGridlayout.setIsShowAll(item.isShowAll);
            String post_img = item.getPost_img();

            String[] all = post_img.split("\\|");

            mUrlList.clear();
            for (int i = 0; i < all.length; i++) {
                mUrlList.add(Urls.BASEIMGURL + all[i]);
            }
            item.setUrlList(mUrlList);
            nineGridlayout.setUrlList(item.getUrlList());
            helper.getView(R.id.jump_detail_ll).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, PostDetailActivity.class);
                    intent.putExtra("id", item.getId() + "");
                    startActivityForResult(intent, 1);
                }
            });
            helper.getView(R.id.item_image).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, PersonalDataActivity.class);
                    intent.putExtra("userId", item.getPost_user_id() + "");
                    startActivity(intent);
                }
            });
        }
    }

    //任务推荐Tab
    private void initRenwutj() {
        renwutjTv.setTextColor(getResources().getColor(R.color.colorPrimary));
        indicatorRenwutj.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        tongchengTv.setTextColor(getResources().getColor(R.color.textColor));
        indicatorTongcheng.setBackgroundColor(getResources().getColor(R.color.concrete));
        initRenwutjData();
    }

    //同城Tab
    private void initTongcheng() {
        renwutjTv.setTextColor(getResources().getColor(R.color.textColor));
        indicatorRenwutj.setBackgroundColor(getResources().getColor(R.color.concrete));
        tongchengTv.setTextColor(getResources().getColor(R.color.colorPrimary));
        indicatorTongcheng.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        initTongchengData();
    }

    private void initRenwutjData() {
        if (taskList != null) {
            tuijianList.addAll(taskList);
        }
        tuijianAdapter.notifyDataSetChanged();
    }

    private void initTongchengData() {
        if (tongchengList != null) {
            tuijianList.addAll(tongchengList);
        }
        tuijianAdapter.notifyDataSetChanged();
    }

    /**
     * 任务推荐Adapter
     */
    public class TuijianAdapter extends BaseQuickAdapter<MainTaskBean.TuijianBean, BaseViewHolder> {

        public TuijianAdapter(int layoutResId, @Nullable List<MainTaskBean.TuijianBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, MainTaskBean.TuijianBean item) {
            TextView money = helper.getView(R.id.item_money_tv);
            TextView titleTv = helper.getView(R.id.item_title_tv);
            TextView contentTv = helper.getView(R.id.item_content_tv);
            RecyclerView tagRecyclerView = helper.getView(R.id.tag_recycler);
            titleTv.setText(item.getTask_name());
            contentTv.setText(item.getTask_description());
            money.setText("￥" + item.getPay_amount());

            tagRecyclerView.setNestedScrollingEnabled(false);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            tagRecyclerView.setLayoutManager(linearLayoutManager);
            TagAdapter tagAdapter = new TagAdapter(R.layout.item_tag_layout, MercenaryUtils.stringToList(item.getTask_tag()));
            tagRecyclerView.setAdapter(tagAdapter);
        }
    }

    /**
     * 任务推荐标签Adapter
     */
    public class TagAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public TagAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            TextView tagTv = helper.getView(R.id.item_content_tv);
            tagTv.setText(item);
        }
    }

    /**
     * 横向分页Adapter
     */
    public class GridPageAdapter extends PageGridView.PagingAdapter<MyVH> implements PageGridView.OnItemClickListener {
        List<Testbean> mData = new ArrayList<>();
        int positon;

        public GridPageAdapter(List<Testbean> data) {
            this.mData.addAll(data);
        }

        @Override
        public MyVH onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_grid_page, parent, false);
            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.height = width;
            params.width = width;
            view.setLayoutParams(params);
            return new MyVH(view);
        }

        @Override
        public void onBindViewHolder(MyVH holder, int position) {
            this.positon = position;
            holder.tv_title.setText(mData.get(position).getTitle());
            Glide.with(mContext).load(mData.get(position).getImg()).into(holder.icon);
        }

        @Override
        public void onItemClick(PageGridView pageGridView, int position) {
            if (position == 5) {
                Intent intent = new Intent(mContext, TaskListActivity.class);
                intent.putExtra("type", "6");
                startActivity(intent);
                return;
            }

            gridPageList2.clear();

            if (stateNum != position || pageGridView2.getVisibility() == View.GONE) {
                pageGridView2.setVisibility(View.VISIBLE);
                switch (position) {
                    case 0://跑腿
                        stateNum = 1;
                        Testbean testbean0 = new Testbean();
                        testbean0.setImg(R.drawable.wupin);
                        testbean0.setTitle("物品");
                        gridPageList2.add(testbean0);
                        Testbean testbean1 = new Testbean();
                        testbean1.setImg(R.drawable.renyuan);
                        testbean1.setTitle("接送人");
                        gridPageList2.add(testbean1);
                        break;
                    case 1://生活
                        Testbean testbean01 = new Testbean();
                        testbean01.setImg(R.drawable.yi);
                        testbean01.setTitle("衣");
                        gridPageList2.add(testbean01);
                        Testbean testbean11 = new Testbean();
                        testbean11.setImg(R.drawable.shi);
                        testbean11.setTitle("食");
                        gridPageList2.add(testbean11);
                        Testbean testbean21 = new Testbean();
                        testbean21.setImg(R.drawable.zhu);
                        testbean21.setTitle("住");
                        gridPageList2.add(testbean21);
                        Testbean testbean31 = new Testbean();
                        testbean31.setImg(R.drawable.xing);
                        testbean31.setTitle("行");
                        gridPageList2.add(testbean31);
                        Testbean testbean41 = new Testbean();
                        testbean41.setImg(R.drawable.you);
                        testbean41.setTitle("游");
                        gridPageList2.add(testbean41);
                        break;
                    case 2://个人定制
                        Testbean testbean02 = new Testbean();
                        testbean02.setImg(R.drawable.yingjian);
                        testbean02.setTitle("硬件");
                        gridPageList2.add(testbean02);
                        Testbean testbean12 = new Testbean();
                        testbean12.setImg(R.drawable.ruanjian);
                        testbean12.setTitle("软件");
                        gridPageList2.add(testbean12);
                        break;
                    case 3://工作
                        Testbean testbean03 = new Testbean();
                        testbean03.setImg(R.drawable.shishu);
                        testbean03.setTitle("仕");
                        gridPageList2.add(testbean03);
                        Testbean testbean13 = new Testbean();
                        testbean13.setImg(R.drawable.nong);
                        testbean13.setTitle("农");
                        gridPageList2.add(testbean13);
                        Testbean testbean23 = new Testbean();
                        testbean23.setImg(R.drawable.gong);
                        testbean23.setTitle("工");
                        gridPageList2.add(testbean23);
                        Testbean testbean33 = new Testbean();
                        testbean33.setImg(R.drawable.shang);
                        testbean33.setTitle("商");
                        gridPageList2.add(testbean33);
                        Testbean testbean43 = new Testbean();
                        testbean43.setImg(R.drawable.lv);
                        testbean43.setTitle("律");
                        gridPageList2.add(testbean43);
                        break;
                    case 4://健康
                        Testbean testbean04 = new Testbean();
                        testbean04.setImg(R.drawable.xinli);
                        testbean04.setTitle("心理");
                        gridPageList2.add(testbean04);
                        Testbean testbean14 = new Testbean();
                        testbean14.setImg(R.drawable.jianshen);
                        testbean14.setTitle("健身");
                        gridPageList2.add(testbean14);
                        Testbean testbean24 = new Testbean();
                        testbean24.setImg(R.drawable.jianfei);
                        testbean24.setTitle("减肥");
                        gridPageList2.add(testbean24);
                        break;
                }
            } else {
                pageGridView2.setVisibility(View.GONE);
            }
            stateNum = position;
            gridPageAdapter2.notifyDataSetChanged();

//
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        @Override
        public List getData() {
            return mData;
        }

        @Override
        public Object getEmpty() {
            return new Testbean();
        }
    }

    private void showNextIssueDialog(int left, int right, int spanCount, final int pubType) {
        mBuilder = new BaseDialog.Builder(mContext);
        mDialog = mBuilder.setViewId(R.layout.dialog_issue)
                //设置dialogpadding
                .setPaddingdp(left, 0, right, 40)
                //设置显示位置
                .setGravity(Gravity.CENTER)
                //设置动画
                .setAnimation(R.style.Bottom_Top_aniamtion)
                //设置dialog的宽高
                .setWidthHeightpx(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                //设置触摸dialog外围是否关闭
                .isOnTouchCanceled(true)
                //设置监听事件
                .builder();

//        RecyclerView issueNextRecycler = mDialog.getView(R.id.issue_recycler);
//        issueNextRecycler.setLayoutManager(new GridLayoutManager(mContext, spanCount));
//        issueNextRecycler.setNestedScrollingEnabled(false);
        IssueNextAdapter issueNextAdapter = new IssueNextAdapter(R.layout.item_issue_layout, nextList);
//        issueNextRecycler.setAdapter(issueNextAdapter);
        issueNextAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mContext, TaskListActivity.class);
                switch (pubType) {
                    case 101://跑腿
                        mDialog.dismiss();
                        intent.putExtra("type", "1");
                        if (position == 0) {
                            intent.putExtra("child", "1");
                        } else if (position == 1) {
                            intent.putExtra("child", "2");
                        }
                        startActivity(intent);
                        break;
                    case 102://生活
                        mDialog.dismiss();
                        intent.putExtra("type", "2");
                        if (position == 0) {
                            intent.putExtra("child", "3");
                        } else if (position == 1) {
                            intent.putExtra("child", "4");
                        } else if (position == 2) {
                            intent.putExtra("child", "5");
                        } else if (position == 3) {
                            intent.putExtra("child", "6");
                        } else if (position == 4) {
                            intent.putExtra("child", "7");
                        }
                        startActivity(intent);
                        break;
                    case 103://个人定制
                        mDialog.dismiss();
                        intent.putExtra("type", "3");
                        if (position == 0) {
                            intent.putExtra("child", "13");
                        } else if (position == 1) {
                            intent.putExtra("child", "14");
                        }
                        startActivity(intent);
                        break;
                    case 104://工作
                        mDialog.dismiss();
                        intent.putExtra("type", "4");
                        if (position == 0) {
                            intent.putExtra("child", "8");
                        } else if (position == 1) {
                            intent.putExtra("child", "9");
                        } else if (position == 2) {
                            intent.putExtra("child", "10");
                        } else if (position == 3) {
                            intent.putExtra("child", "11");
                        } else if (position == 4) {
                            intent.putExtra("child", "12");
                        }
                        startActivity(intent);
                        break;
                    case 105://健康
                        mDialog.dismiss();
                        intent.putExtra("type", "5");
                        if (position == 0) {
                            intent.putExtra("child", "17");
                        } else if (position == 1) {
                            intent.putExtra("child", "16");
                        } else if (position == 2) {
                            intent.putExtra("child", "15");
                        }
                        startActivity(intent);
                        break;
                }
            }
        });
        mDialog.show();
    }

    private class IssueNextAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public IssueNextAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.item_type_tv, item)
                    .addOnClickListener(R.id.item_type_tv);
        }
    }

    /**
     * 横向分页Viewholder
     */
    public static class MyVH extends RecyclerView.ViewHolder {
        public TextView tv_title;
        public ImageView icon;

        public MyVH(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.item_title_tv);
            icon = (ImageView) itemView.findViewById(R.id.item_icon_iv);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        unbinder.unbind();
    }

    @OnClick({R.id.location_tv, R.id.search_tv, R.id.renwutj_ll, R.id.tongcheng_ll, R.id.post_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.location_tv://定位
                startActivity(new Intent(mContext, LocationActivity.class));
                break;
            case R.id.search_tv://搜索
                startActivity(new Intent(mContext, HomeSearchActivity.class));
                break;
            case R.id.renwutj_ll://任务推荐
                oneAndTwo = 0;
                tuijianList.clear();
                initRenwutj();
                break;
            case R.id.tongcheng_ll://同城
                oneAndTwo = 1;
                tuijianList.clear();
                initTongcheng();
                break;
            case R.id.post_rl://热门帖子
                startActivity(new Intent(mContext, WorkPubActivity.class));
                break;
        }
    }

    public class GridPage2Adapter extends BaseAdapter {
        List<Testbean> mData;

        public GridPage2Adapter(List<Testbean> mData) {
            this.mData = mData;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_grid_page, parent, false);
//            ViewGroup.LayoutParams params = convertView.getLayoutParams();
//            params.height = width;
//            params.width = width;
//            convertView.setLayoutParams(params);
            holder.tv_title = convertView.findViewById(R.id.item_title_tv);
            holder.icon = convertView.findViewById(R.id.item_icon_iv);
            holder.tv_title.setText(mData.get(position).getTitle());
            Glide.with(mContext).load(mData.get(position).getImg()).into(holder.icon);
            holder.layout = convertView.findViewById(R.id.yigedalayout);
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, TaskListActivity.class);
                    switch (stateNum + 1) {
                        case 1://跑腿
                            intent.putExtra("type", "1");
                            if (position == 0) {
                                intent.putExtra("child", "1");
                            } else if (position == 1) {
                                intent.putExtra("child", "2");
                            }
                            startActivity(intent);
                            break;
                        case 2://生活
                            intent.putExtra("type", "2");
                            if (position == 0) {
                                intent.putExtra("child", "3");
                            } else if (position == 1) {
                                intent.putExtra("child", "4");
                            } else if (position == 2) {
                                intent.putExtra("child", "5");
                            } else if (position == 3) {
                                intent.putExtra("child", "6");
                            } else if (position == 4) {
                                intent.putExtra("child", "7");
                            }
                            startActivity(intent);
                            break;
                        case 3://个人定制
                            intent.putExtra("type", "3");
                            if (position == 0) {
                                intent.putExtra("child", "13");
                            } else if (position == 1) {
                                intent.putExtra("child", "14");
                            }
                            startActivity(intent);
                            break;
                        case 4://工作
                            intent.putExtra("type", "4");
                            if (position == 0) {
                                intent.putExtra("child", "8");
                            } else if (position == 1) {
                                intent.putExtra("child", "9");
                            } else if (position == 2) {
                                intent.putExtra("child", "10");
                            } else if (position == 3) {
                                intent.putExtra("child", "11");
                            } else if (position == 4) {
                                intent.putExtra("child", "12");
                            }
                            startActivity(intent);
                            break;
                        case 5://健康
                            intent.putExtra("type", "5");
                            if (position == 0) {
                                intent.putExtra("child", "17");
                            } else if (position == 1) {
                                intent.putExtra("child", "16");
                            } else if (position == 2) {
                                intent.putExtra("child", "15");
                            }
                            startActivity(intent);
                            break;
                    }
                }
            });
            return convertView;
        }

        class ViewHolder {
            TextView tv_title;
            ImageView icon;
            LinearLayout layout;
        }
    }

    private void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams layoutParams = getActivity().getWindow().getAttributes();
        layoutParams.alpha = bgAlpha;
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getActivity().getWindow().setAttributes(layoutParams);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser){


        }
    }
}
