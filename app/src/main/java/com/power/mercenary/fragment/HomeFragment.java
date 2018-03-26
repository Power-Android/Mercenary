package com.power.mercenary.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.power.mercenary.R;
import com.power.mercenary.base.BaseFragment;
import com.power.mercenary.bean.Testbean;
import com.power.mercenary.utils.BannerUtils;
import com.power.mercenary.utils.TUtils;
import com.power.mercenary.view.MyPageIndicator;
import com.power.mercenary.view.PageGridView;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by power on 2018/3/21.
 */

public class HomeFragment extends BaseFragment {
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
    Unbinder unbinder1;
    private int width;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
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
        BannerUtils.startBanner(banner, baaaneList);
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                TUtils.showShort(mContext, "点击了Banner" + position);
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
        initRenwutj();
        tuijianRecycler.setNestedScrollingEnabled(false);
        tuijianRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        List<String> tuijianList = new ArrayList<>();
        tuijianList.add("");
        tuijianList.add("");
        TuijianAdapter tuijianAdapter = new TuijianAdapter(R.layout.item_tuijian_renwu,tuijianList);
        tuijianRecycler.setAdapter(tuijianAdapter);
        //----------------------------结束-----------------------------------------------
        //----------------------------热门帖子--------------------------------------------

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

    }

    private void initTongchengData() {

    }

    /**
     * 任务推荐Adapter
     */
    public class TuijianAdapter extends BaseQuickAdapter<String,BaseViewHolder>{

        public TuijianAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            TextView titleTv = helper.getView(R.id.item_title_tv);
            TextView contentTv = helper.getView(R.id.item_content_tv);
            RecyclerView tagRecyclerView = helper.getView(R.id.tag_recycler);
            tagRecyclerView.setNestedScrollingEnabled(false);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            tagRecyclerView.setLayoutManager(linearLayoutManager);
            List<String> tagList = new ArrayList<>();
            tagList.add("");
            tagList.add("");
            tagList.add("");
            TagAdapter tagAdapter = new TagAdapter(R.layout.item_tag_layout, tagList);
            tagRecyclerView.setAdapter(tagAdapter);
        }
    }

    /**
     * 任务推荐标签Adapter
     */
    public class TagAdapter extends BaseQuickAdapter<String,BaseViewHolder>{

        public TagAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            TextView tagTv = helper.getView(R.id.item_content_tv);
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
            TUtils.showShort(mContext, "点击了---" + mData.get(position).getTitle());
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
        unbinder.unbind();
    }

    @OnClick({R.id.location_tv, R.id.search_tv,R.id.renwutj_ll, R.id.tongcheng_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.location_tv://定位
                break;
            case R.id.search_tv://搜索
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
