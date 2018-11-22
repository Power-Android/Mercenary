package com.power.mercenary.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.power.mercenary.activity.PostDetailActivity;
import com.power.mercenary.activity.WorkPubActivity;
import com.power.mercenary.base.BaseFragment;
import com.power.mercenary.bean.BannerBean;
import com.power.mercenary.bean.MainTaskBean;
import com.power.mercenary.bean.Testbean;
import com.power.mercenary.bean.TieZiDetailsBean;
import com.power.mercenary.bean.TieZiListBean;
import com.power.mercenary.http.ResponseBean;
import com.power.mercenary.presenter.MainPresenter;
import com.power.mercenary.presenter.TieZiListPresenter;
import com.power.mercenary.utils.Urls;
import com.power.mercenary.view.FluidLayout;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by power on 2018/3/21.
 */

public class PubFragment extends BaseFragment implements TieZiListPresenter.TaskListCallBack, MainPresenter.MainCallBack {
    @BindView(R.id.title_content_tv)
    TextView titleContentTv;
    @BindView(R.id.recycler_title)
    RecyclerView recyclerTitle;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.fluidlayout)
    FluidLayout fluidlayout;
    @BindView(R.id.recycler_content)
    RecyclerView recyclerContent;
    @BindView(R.id.banner)
    Banner banner;
    Unbinder unbinder;
    @BindView(R.id.title_back_iv)
    ImageView titleBackIv;
    @BindView(R.id.title_content_right_tv)
    TextView titleContentRightTv;
    @BindView(R.id.layout_hottuijian)
    LinearLayout layoutHottuijian;
    Unbinder unbinder1;
    @BindView(R.id.img_banner)
    ImageView imgBanner;
    private int scrollPosition;
    private List<String> hotNameList = new ArrayList<>();
    private String task_type = "0";
    private String task_type_child = "";
    private TieZiListPresenter presenter;
    private List<BannerBean> bannerBeans;
    private String task_title;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pub, null);
        unbinder = ButterKnife.bind(this, view);
        presenter = new TieZiListPresenter(getActivity(), this);
        presenter.getTaskList(1, task_type, "0");
        MainPresenter mainPresenter = new MainPresenter(getActivity(), this);
        mainPresenter.getBannerList(2);
        initData();
        return view;
    }

    private void initData() {
        titleBackIv.setVisibility(View.GONE);
        titleContentTv.setText("全部分类");

        List<Integer> baaaneList = new ArrayList<>();
        baaaneList.add(R.drawable.test_banner);
        baaaneList.add(R.drawable.test_banner);
        baaaneList.add(R.drawable.test_banner);
//        BannerUtils.startBanner(banner, baaaneList);
//        banner.setOnBannerListener(new OnBannerListener() {
//            @Override
//            public void OnBannerClick(int position) {
//                TUtils.showShort(mContext, bannerBeans.get(position).getUrl());
//            }
//        });

        final List<Testbean> titleList = new ArrayList<>();
        Testbean testbean0 = new Testbean();
        testbean0.setTitle("热门推荐");
        titleList.add(testbean0);
        Testbean testbean1 = new Testbean();
        testbean1.setTitle("跑腿");
        titleList.add(testbean1);
        Testbean testbean2 = new Testbean();
        testbean2.setTitle("生活");
        titleList.add(testbean2);
        Testbean testbean3 = new Testbean();
        testbean3.setTitle("个人定制");
        titleList.add(testbean3);
        Testbean testbean4 = new Testbean();
        testbean4.setTitle("工作");
        titleList.add(testbean4);
        Testbean testbean5 = new Testbean();
        testbean5.setTitle("健康");
        titleList.add(testbean5);

        recyclerTitle.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerTitle.setNestedScrollingEnabled(false);
        final TitleAdapter titleAdapter = new TitleAdapter(R.layout.item_title_recycle, titleList);
        recyclerTitle.setAdapter(titleAdapter);
        titleList.get(0).setChecked(true);
        titleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                titleList.get(scrollPosition).setChecked(false);
                scrollPosition = position;
                titleList.get(scrollPosition).setChecked(true);
                titleAdapter.notifyDataSetChanged();
                //TODO 刷新热门数据，更新流式布局文字
                if (position == 0) {
                    presenter.getTaskList(1, "0", "0");
                    hotNameList.clear();
                    layoutHottuijian.setVisibility(View.GONE);
                    RefreshHot();
                } else if (position == 1) {
                    task_type = "1";
                    presenter.getTaskList(1, task_type, "0");
                    hotNameList.clear();
                    hotNameList.add("物品");
                    hotNameList.add("人员");
                    RefreshHot();
                } else if (position == 2) {
                    task_type = "2";
                    presenter.getTaskList(1, task_type, "0");
                    hotNameList.clear();
                    hotNameList.add("衣");
                    hotNameList.add("食");
                    hotNameList.add("住");
                    hotNameList.add("行");
                    hotNameList.add("游");
                    RefreshHot();
                } else if (position == 3) {
                    task_type = "3";
                    presenter.getTaskList(1, task_type, "0");
                    hotNameList.clear();
                    hotNameList.add("硬件");
                    hotNameList.add("软件");
                    RefreshHot();
                } else if (position == 4) {
                    task_type = "4";
                    presenter.getTaskList(1, task_type, "0");
                    hotNameList.clear();
                    hotNameList.add("仕");
                    hotNameList.add("农");
                    hotNameList.add("工");
                    hotNameList.add("商");
                    hotNameList.add("律");
                    RefreshHot();
                } else if (position == 5) {
                    task_type = "5";
                    presenter.getTaskList(1, task_type, "0");
                    hotNameList.clear();
                    hotNameList.add("心理");
                    hotNameList.add("健康");
                    hotNameList.add("减肥");
                    RefreshHot();
                }
            }
        });


    }

    private void RefreshHot() {
        fluidlayout.removeAllViews();

        layoutHottuijian.setVisibility(View.VISIBLE);
        fluidlayout.setVisibility(View.VISIBLE);

        for (int i = 0; i < hotNameList.size(); i++) {
            final TextView tv = (TextView) View.inflate(mContext, R.layout.fenlei_hot_item, null);
            tv.setText(hotNameList.get(i));
            FluidLayout.LayoutParams params = new FluidLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(12, 12, 12, 12);
            fluidlayout.addView(tv, params);
            final int finalI = i;
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    startActivity(new Intent(mContext, PubListActivity.class));
                    for (int i1 = 0; i1 < hotNameList.size(); i1++) {
                        if (i1 == finalI) {
                            task_title = hotNameList.get(i1);
                            task_type_child = (finalI + 1) + "";
                        }
                    }
                    Intent intent = new Intent(mContext, WorkPubActivity.class);
                    intent.putExtra("task_type", task_type);
                    intent.putExtra("task_title", task_title);
                    intent.putExtra("task_type_child", task_type_child);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void getTaskList(final List<TieZiListBean> datas) {
        recyclerContent.setNestedScrollingEnabled(false);
        recyclerContent.setLayoutManager(new LinearLayoutManager(mContext));
        ContentAdapter adapter = new ContentAdapter(R.layout.item_content_layout, datas);
        recyclerContent.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mContext, PostDetailActivity.class);
                intent.putExtra("id", datas.get(position).getId() + "");
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public void getTaskDetails(TieZiDetailsBean datas) {

    }

    @Override
    public void getListFail() {

    }

    @Override
    public void getPubPinglun(ResponseBean datas) {

    }

    @Override
    public void getHuifu(ResponseBean datas) {

    }

    @Override
    public void getPost(ResponseBean datas) {

    }

    @Override
    public void getTaskList(MainTaskBean taskBean) {

    }

    @Override
    public void getBannerList(List<BannerBean> datas) {
//        bannerBeans = datas;
//        List<String> bannerList = new ArrayList<>();
//        if (datas != null) {
//            for (int i = 0; i < datas.size(); i++) {
//                bannerList.add(Urls.BASEIMGURL + datas.get(i).getPic());
//            }
//        }
        if (datas!=null&&datas.size()>0){
            Glide.with(mContext).load(Urls.BASEIMGURL + datas.get(0).getPic()).into(imgBanner);
        }
//        BannerUtils.startBanner(banner, bannerList);
    }

    private class ContentAdapter extends BaseQuickAdapter<TieZiListBean, BaseViewHolder> {

        public ContentAdapter(int layoutResId, @Nullable List<TieZiListBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, TieZiListBean item) {
            helper.setText(R.id.item_title_tv, "热门推荐")
                    .setText(R.id.item_content_tv, item.getPost_content())
                    .setText(R.id.item_num_tv, item.getCount());
        }
    }

    private class TitleAdapter extends BaseQuickAdapter<Testbean, BaseViewHolder> {

        public TitleAdapter(int layoutResId, @Nullable List<Testbean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Testbean item) {
            helper.setText(R.id.tv_title, item.getTitle());
            TextView tv = helper.getView(R.id.tv_title);
            if (item.isChecked()) {
                helper.getView(R.id.title_left_view).setBackgroundResource(R.color.colorPrimary);
                tv.setTextColor(getResources().getColor(R.color.colorPrimary));
            } else {
                helper.getView(R.id.title_left_view).setBackgroundResource(R.color.white);
                tv.setTextColor(getResources().getColor(R.color.textColorDrak));
            }
        }
    }

    @Override
    protected void initLazyData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
