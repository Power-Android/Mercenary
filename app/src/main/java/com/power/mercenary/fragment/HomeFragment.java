package com.power.mercenary.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.power.mercenary.MyApplication;
import com.power.mercenary.R;
import com.power.mercenary.activity.GRTaskDetailsActivity;
import com.power.mercenary.activity.GZTaskDetailsActivity;
import com.power.mercenary.activity.LocationActivity;
import com.power.mercenary.activity.PTTaskDetailsActivity;
import com.power.mercenary.activity.PostDetailActivity;
import com.power.mercenary.activity.QTTaskDetailsActivity;
import com.power.mercenary.activity.SHTaskDetailsActivity;
import com.power.mercenary.activity.SignInActivity;
import com.power.mercenary.activity.TaskListActivity;
//import com.power.mercenary.activity.TestActivity;
import com.power.mercenary.activity.TestActivity;
import com.power.mercenary.activity.WorkPubActivity;
import com.power.mercenary.base.BaseFragment;
import com.power.mercenary.bean.BannerBean;
import com.power.mercenary.bean.CitySelectBean;
import com.power.mercenary.bean.MainTaskBean;
import com.power.mercenary.bean.NineGridTestModel;
import com.power.mercenary.bean.Testbean;
import com.power.mercenary.data.CacheConstants;
import com.power.mercenary.data.EventConstants;
import com.power.mercenary.event.EventUtils;
import com.power.mercenary.presenter.MainPresenter;
import com.power.mercenary.presenter.TaskListPresenter;
import com.power.mercenary.utils.BannerUtils;
import com.power.mercenary.utils.CacheUtils;
import com.power.mercenary.utils.MercenaryUtils;
import com.power.mercenary.utils.TUtils;
import com.power.mercenary.view.MyPageIndicator;
import com.power.mercenary.view.NineGridTestLayout;
import com.power.mercenary.view.PageGridView;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.rong.imlib.model.Message;

/**
 * Created by power on 2018/3/21.
 */

public class HomeFragment extends BaseFragment implements MainPresenter.MainCallBack {
    @BindView(R.id.location_tv)
    TextView locationTv;
    @BindView(R.id.search_tv)
    TextView searchTv;
    @BindView(R.id.banner)
    Banner banner;
    Unbinder unbinder;
    @BindView(R.id.pageGridView)
    PageGridView pageGridView;
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

    private MainPresenter mainPresenter;
    private List<MainTaskBean.TuijianBean> tuijianList;
    private TuijianAdapter tuijianAdapter;

    private List<NineGridTestModel> mList = new ArrayList<>();
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

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        unbinder = ButterKnife.bind(this, view);

        mainPresenter = new MainPresenter(getActivity(), this);
        mainPresenter.getTaskList();
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
                TUtils.showShort(mContext, bannerBeans.get(position).getUrl());

            }
        });
        //----------------------------结束---------------------------------------------

        //----------------------------横向分页------------------------------------------
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
        NineGridTestModel model1 = new NineGridTestModel();
        model1.urlList.add(mUrls[0]);
        mList.add(model1);

        NineGridTestModel model2 = new NineGridTestModel();
        model2.urlList.add(mUrls[0]);
        model2.urlList.add(mUrls[1]);
        mList.add(model2);

        NineGridTestModel model6 = new NineGridTestModel();
        for (int i = 0; i < 9; i++) {
            model6.urlList.add(mUrls[i]);
        }
        mList.add(model6);

        NineGridTestModel model7 = new NineGridTestModel();
        for (int i = 3; i < 7; i++) {
            model7.urlList.add(mUrls[i]);
        }
        mList.add(model7);

        NineGridTestModel model8 = new NineGridTestModel();
        for (int i = 3; i < 6; i++) {
            model8.urlList.add(mUrls[i]);
        }
        mList.add(model8);
        postRecycler.setNestedScrollingEnabled(false);
        postRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        MyAdapter myAdapter = new MyAdapter(R.layout.item_circle_friend, mList);
        postRecycler.setAdapter(myAdapter);
        //-------------------------------结束-------——--------------------------------------------

        initRenwutj();
    }

    @Override
    public void getTaskList(MainTaskBean taskBean) {
        this.taskBean = taskBean;
        initRenwutjData();
    }

    @Override
    public void getBannerList(List<BannerBean> datas) {
        bannerBeans = datas;
        List<String> bannerList = new ArrayList<>();
        if (datas != null) {
            for (int i = 0; i < datas.size(); i++) {
                bannerList.add(datas.get(i).getPic());
            }
        }
        BannerUtils.startBanner(banner, bannerList);
    }

    private class MyAdapter extends BaseQuickAdapter<NineGridTestModel, BaseViewHolder> {

        public MyAdapter(@LayoutRes int layoutResId, @Nullable List<NineGridTestModel> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, NineGridTestModel item) {
            NineGridTestLayout nineGridlayout = helper.getView(R.id.nine_gridlayout);
            nineGridlayout.setIsShowAll(mList.get(helper.getAdapterPosition()).isShowAll);
            nineGridlayout.setUrlList(mList.get(helper.getAdapterPosition()).urlList);
            helper.getView(R.id.jump_detail_ll).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(mContext, PostDetailActivity.class));
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
        if (taskBean != null && taskBean.getTuijian() != null) {
            tuijianList.addAll(taskBean.getTuijian());
        }
        tuijianAdapter.notifyDataSetChanged();
    }

    private void initTongchengData() {
        if (taskBean != null && taskBean.getSame_city() != null) {
            tuijianList.addAll(taskBean.getSame_city());
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
            TextView titleTv = helper.getView(R.id.item_title_tv);
            TextView contentTv = helper.getView(R.id.item_content_tv);
            RecyclerView tagRecyclerView = helper.getView(R.id.tag_recycler);
            titleTv.setText(item.getTask_name());
            contentTv.setText(item.getTask_description());

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
//            TUtils.showShort(mContext, "点击了---" + mData.get(position).getTitle());

            if (!MyApplication.isLogin()) {
                startActivity(new Intent(mContext, SignInActivity.class));
                return;
            }

            Intent intent = new Intent(mContext, TaskListActivity.class);
            intent.putExtra("type", (position + 1) + "");
            startActivity(intent);
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
                startActivity(new Intent(mContext, TestActivity.class));
                break;
            case R.id.renwutj_ll://任务推荐
                tuijianList.clear();
                initRenwutj();
                break;
            case R.id.tongcheng_ll://同城
                tuijianList.clear();
                initTongcheng();
                break;
            case R.id.post_rl://热门帖子
                startActivity(new Intent(mContext, WorkPubActivity.class));
                break;
        }
    }
}
