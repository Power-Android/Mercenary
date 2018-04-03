package com.power.mercenary.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.power.mercenary.R;
import com.power.mercenary.base.BaseActivity;
import com.power.mercenary.utils.TUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PubPaotuiActivity extends BaseActivity {

    @BindView(R.id.title_back_iv)
    ImageView titleBackIv;
    @BindView(R.id.title_content_tv)
    TextView titleContentTv;
    @BindView(R.id.title_content_right_tv)
    TextView titleContentRightTv;
    @BindView(R.id.task_name_et)
    EditText taskNameEt;
    @BindView(R.id.goods_name_et)
    EditText goodsNameEt;
    @BindView(R.id.num_et)
    EditText numEt;
    @BindView(R.id.money_et)
    EditText moneyEt;
    @BindView(R.id.mudi_zishu_tv)
    TextView mudiZishuTv;
    @BindView(R.id.task_mudi_et)
    EditText taskMudiEt;
    @BindView(R.id.transport_time_rl)
    RelativeLayout transportTimeRl;
    @BindView(R.id.validity_time_et)
    EditText validityTimeEt;
    @BindView(R.id.start_address_tv)
    TextView startAddressTv;
    @BindView(R.id.del_start_address_tv)
    TextView delStartAddressTv;
    @BindView(R.id.end_address_tv)
    TextView endAddressTv;
    @BindView(R.id.add_biaoqian_tv)
    TextView addBiaoqianTv;
    @BindView(R.id.biaoqian_et)
    EditText biaoqianEt;
    @BindView(R.id.del_biaoqian_tv)
    TextView delBiaoqianTv;
    @BindView(R.id.biaoqian_recycler)
    RecyclerView biaoqianRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paotui_pub);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        titleBackIv.setVisibility(View.VISIBLE);
        titleContentTv.setText("发布任务");
        titleContentRightTv.setVisibility(View.VISIBLE);
        titleContentRightTv.setText("发布");

        List<String> biaoqianList = new ArrayList<>();
        biaoqianList.add("");
        biaoqianList.add("");
        biaoqianList.add("");
        biaoqianRecycler.setNestedScrollingEnabled(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        biaoqianRecycler.setLayoutManager(linearLayoutManager);
        BiaoqianAdapter biaoqianAdapter = new BiaoqianAdapter(R.layout.item_tag_layout,biaoqianList);
        biaoqianRecycler.setAdapter(biaoqianAdapter);
    }

    private class BiaoqianAdapter extends BaseQuickAdapter<String,BaseViewHolder>{

        public BiaoqianAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {

        }
    }

    @OnClick({R.id.title_back_iv, R.id.title_content_right_tv, R.id.transport_time_rl, R.id.start_address_tv,
            R.id.del_start_address_tv, R.id.end_address_tv, R.id.add_biaoqian_tv, R.id.del_biaoqian_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back_iv:
                finish();
                break;
            case R.id.title_content_right_tv:
                TUtils.showShort(mContext,"点击了---发布");
                break;
            case R.id.transport_time_rl:
                TUtils.showShort(mContext,"点击了---送达时间");
                break;
            case R.id.start_address_tv:
                TUtils.showShort(mContext,"点击了---开始地址");
                break;
            case R.id.del_start_address_tv:
                TUtils.showShort(mContext,"点击了---删除开始地址");
                break;
            case R.id.end_address_tv:
                TUtils.showShort(mContext,"点击了---目的地址");
                break;
            case R.id.add_biaoqian_tv:
                TUtils.showShort(mContext,"点击了---添加标签");
                break;
            case R.id.del_biaoqian_tv:
                TUtils.showShort(mContext,"点击了---删除标签");
                break;
        }
    }
}
