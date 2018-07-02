package com.power.mercenary.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.power.mercenary.R;
import com.power.mercenary.activity.PubListActivity;
import com.power.mercenary.base.BaseFragment;
import com.power.mercenary.bean.Testbean;
import com.power.mercenary.utils.BannerUtils;
import com.power.mercenary.utils.TUtils;
import com.power.mercenary.view.FluidLayout;
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

public class PubFragment extends BaseFragment {
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
    private int scrollPosition;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pub, null);
        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        titleContentTv.setText("全部分类");

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
        final TitleAdapter titleAdapter = new TitleAdapter(R.layout.item_title_recycle,titleList);
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
            }
        });

        fluidlayout.removeAllViews();
        final List<String> hotNameList = new ArrayList<>();
        hotNameList.add("健康");
        hotNameList.add("农");
        hotNameList.add("个人定制");
        hotNameList.add("工作");
        hotNameList.add("跑腿");
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
                    TUtils.showShort(mContext,"点击了---" + hotNameList.get(finalI));
                    startActivity(new Intent(mContext,PubListActivity.class));
                }
            });
        }

        List<Testbean> contentList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Testbean testbean = new Testbean();
            testbean.setTitle("酒馆热门推荐");
            testbean.setContent("酒馆热门推荐内容，七月七日晴，突然下起了大雪，覆盖你来的那条街。");
            testbean.setNum("10" + i);
            contentList.add(testbean);
        }
        recyclerContent.setNestedScrollingEnabled(false);
        recyclerContent.setLayoutManager(new LinearLayoutManager(mContext));
        ContentAdapter adapter = new ContentAdapter(R.layout.item_content_layout,contentList);
        recyclerContent.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                TUtils.showShort(mContext,"点击了---item"+position);
            }
        });
    }

    private class ContentAdapter extends BaseQuickAdapter<Testbean,BaseViewHolder>{

        public ContentAdapter(int layoutResId, @Nullable List<Testbean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Testbean item) {
            helper.setText(R.id.item_title_tv,item.getTitle())
                    .setText(R.id.item_content_tv,item.getContent())
                    .setText(R.id.item_num_tv,item.getNum());
        }
    }

    private class TitleAdapter extends BaseQuickAdapter<Testbean,BaseViewHolder>{

        public TitleAdapter(int layoutResId, @Nullable List<Testbean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Testbean item) {
            helper.setText(R.id.tv_title,item.getTitle());
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
