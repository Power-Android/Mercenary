package com.power.mercenary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.power.mercenary.R;
import com.power.mercenary.base.BaseActivity;
import com.power.mercenary.bean.OneCheckBean;
import com.power.mercenary.view.CommonPopupWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PubListActivity extends BaseActivity {

    @BindView(R.id.title_back_iv)
    ImageView titleBackIv;
    @BindView(R.id.title_content_tv)
    TextView titleContentTv;
    @BindView(R.id.menu_left)
    LinearLayout menuLeft;
    @BindView(R.id.menu_right)
    LinearLayout menuRight;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.line1)
    LinearLayout line1;
    private CommonPopupWindow popupWindow;
    private List<OneCheckBean> leftList = new ArrayList<>();
    private List<OneCheckBean> righttList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pub_list);
        ButterKnife.bind(this);

        leftList.add(new OneCheckBean(false,"佣金"));
        leftList.add(new OneCheckBean(false,"时间"));
        leftList.add(new OneCheckBean(false,"距离"));

        righttList.add(new OneCheckBean(false,"跑腿"));
        righttList.add(new OneCheckBean(false,"生活"));
        righttList.add(new OneCheckBean(false,"工作"));
        righttList.add(new OneCheckBean(false,"个人定制"));
        righttList.add(new OneCheckBean(false,"健康"));
        righttList.add(new OneCheckBean(false,"其他"));

        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        List<String> tuijianList = new ArrayList<>();
        tuijianList.add("");
        tuijianList.add("");
        tuijianList.add("");
        tuijianList.add("");
        tuijianList.add("");
        tuijianList.add("");
        tuijianList.add("");
        TuijianAdapter tuijianAdapter = new TuijianAdapter(R.layout.item_tuijian_renwu,tuijianList);
        recyclerView.setAdapter(tuijianAdapter);
        tuijianAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(mContext, QTTaskDetailsActivity.class));
            }
        });
    }

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

    public class TagAdapter extends BaseQuickAdapter<String,BaseViewHolder>{

        public TagAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            TextView tagTv = helper.getView(R.id.item_content_tv);
        }
    }

    @OnClick(R.id.title_back_iv)
    public void onTitleBackIvClicked() {
        finish();
    }

    @OnClick(R.id.menu_left)
    public void onMenuLeftClicked() {
        showDownPop(line1);
    }

    @OnClick(R.id.menu_right)
    public void onMenuRightClicked() {
        showDownPop1(line1);
    }

    //向下弹出
    public void showDownPop(View view) {
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        popupWindow = new CommonPopupWindow.Builder(PubListActivity.this)
                .setView(R.layout.popup_menu_left)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setAnimationStyle(R.style.AnimDown)
                .setBackGroundLevel(0.8f)
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(View view, int layoutResId) {
                        RecyclerView leftRecyclerview = view.findViewById(R.id.left_recycler);
                        leftRecyclerview.setNestedScrollingEnabled(false);
                        leftRecyclerview.setLayoutManager(new LinearLayoutManager(PubListActivity.this));
                        final MenuAdapter menuAdapter = new MenuAdapter(R.layout.item_menu_left,leftList);
                        leftRecyclerview.setAdapter(menuAdapter);
                        menuAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                for (int i = 0; i < leftList.size(); i++) {
                                    if (position == i){
                                        leftList.get(i).setChecked(true);
                                    }else {
                                        leftList.get(i).setChecked(false);
                                    }
                                }
                                menuAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                })
                .setOutsideTouchable(true)
                .create();
        popupWindow.showAsDropDown(view);
    }

    //向下弹出
    public void showDownPop1(View view) {
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        popupWindow = new CommonPopupWindow.Builder(PubListActivity.this)
                .setView(R.layout.popup_menu_right)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setAnimationStyle(R.style.AnimDown)
                .setBackGroundLevel(0.8f)
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(View view, int layoutResId) {
                        RecyclerView rightRecyclerview = view.findViewById(R.id.right_recycler);
                        rightRecyclerview.setNestedScrollingEnabled(false);
                        rightRecyclerview.setLayoutManager(new LinearLayoutManager(PubListActivity.this));
                        final MenuAdapter1 menuAdapter1 = new MenuAdapter1(R.layout.item_menu_right,righttList);
                        rightRecyclerview.setAdapter(menuAdapter1);
                        menuAdapter1.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                for (int i = 0; i < righttList.size(); i++) {
                                    if (position == i){
                                        righttList.get(i).setChecked(true);
                                    }else {
                                        righttList.get(i).setChecked(false);
                                    }
                                }
                                menuAdapter1.notifyDataSetChanged();
                            }
                        });
                    }
                })
                .setOutsideTouchable(true)
                .create();
        popupWindow.showAsDropDown(view);
    }

    private class MenuAdapter extends BaseQuickAdapter<OneCheckBean,BaseViewHolder>{

        public MenuAdapter(int layoutResId, @Nullable List<OneCheckBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, OneCheckBean item) {
            final TextView nameTv = helper.getView(R.id.item_name_tv);
            helper.setText(R.id.item_name_tv,item.getName());
            helper.setChecked(R.id.item_isCheck,item.isChecked());
            helper.setVisible(R.id.item_isCheck,item.isChecked());
            if (item.isChecked()){
                nameTv.setTextColor(getResources().getColor(R.color.colorPrimary));
            }else {
                nameTv.setTextColor(getResources().getColor(R.color.textColor));
            }

        }
    }

    private class MenuAdapter1 extends BaseQuickAdapter<OneCheckBean,BaseViewHolder>{

        public MenuAdapter1(int layoutResId, @Nullable List<OneCheckBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, OneCheckBean item) {
            final TextView nameTv = helper.getView(R.id.item_name_tv);
            helper.setText(R.id.item_name_tv,item.getName());
            helper.setChecked(R.id.item_isCheck,item.isChecked());
            helper.setVisible(R.id.item_isCheck,item.isChecked());
            if (item.isChecked()){
                nameTv.setTextColor(getResources().getColor(R.color.colorPrimary));
            }else {
                nameTv.setTextColor(getResources().getColor(R.color.textColor));
            }
        }
    }
}
