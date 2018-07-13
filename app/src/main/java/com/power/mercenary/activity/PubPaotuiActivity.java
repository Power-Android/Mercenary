package com.power.mercenary.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.power.mercenary.R;
import com.power.mercenary.base.BaseActivity;
import com.power.mercenary.presenter.PubTaskPresenter;
import com.power.mercenary.utils.TUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PubPaotuiActivity extends BaseActivity implements PubTaskPresenter.PubTaskCallBack {

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
    @BindView(R.id.validity_songda_et)
    EditText validitySongdaEt;
    private PubTaskPresenter presenter;
    private List<String> mlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paotui_pub);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        presenter = new PubTaskPresenter(this, this);
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
        BiaoqianAdapter biaoqianAdapter = new BiaoqianAdapter(R.layout.item_tag_layout, biaoqianList);
        biaoqianRecycler.setAdapter(biaoqianAdapter);
    }


    private class BiaoqianAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

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
                /**
                 * 发布任务
                 *
                 * @param task_type        任务分类对应id
                 * @param task_type_child  子任务分类对应id
                 * @param task_name        任务名称 如果是个人定制类任务表示是定制物品名称
                 * @param task_tag         任务标签 数组键用0,1,2…表示
                 * @param task_img         任务相关图片 数组键用0,1,2…表示
                 * @param pay_amount       佣金金额 金额以分为单位
                 * @param validity_time    任务有效期 以天为单位
                 * @param task_description 任务详情
                 * @param task_purpose     任务目的
                 * @param task_request     任务要求
                 * @param itemname         物品名称
                 * @param numbers          物品数量
                 * @param transport        运输要求
                 * @param delivery_time    送达时间 时间戳格式 （1530961214）10位
                 * @param begin_address    开始地址
                 * @param end_address      目的地址
                 * @param other_request    其它要求
                 */

                if (TextUtils.isEmpty(taskNameEt.getText().toString())) {
                    Toast.makeText(mContext, "请输入任务名称", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(goodsNameEt.getText().toString())) {
                    Toast.makeText(mContext, "请输入物品名称", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(numEt.getText().toString())) {
                    Toast.makeText(mContext, "请输入物品数量", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(moneyEt.getText().toString())) {
                    Toast.makeText(mContext, "请输入佣金金额", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(taskMudiEt.getText().toString())) {
                    Toast.makeText(mContext, "请输入任务目的", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(validitySongdaEt.getText().toString())) {
                    Toast.makeText(mContext, "请输入送达时间", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(validityTimeEt.getText().toString())) {
                    Toast.makeText(mContext, "请输入有效期", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(biaoqianEt.getText().toString())) {
                    Toast.makeText(mContext, "请输入2-4个文字标签", Toast.LENGTH_SHORT).show();
                    return;
                }
                presenter.publishTask("1", "1", taskNameEt.getText().toString(), "", "", moneyEt.getText().toString(),
                        validityTimeEt.getText().toString(), "", taskMudiEt.getText().toString(), "",
                        goodsNameEt.getText().toString(), numEt.getText().toString(), "", "1530961214",
                        "开始地址", "目的地址", biaoqianEt.getText().toString());
                break;
            case R.id.transport_time_rl:
                TUtils.showShort(mContext, "点击了---送达时间");
                break;
            case R.id.start_address_tv:
                TUtils.showShort(mContext, "点击了---开始地址");
                break;
            case R.id.del_start_address_tv:
                TUtils.showShort(mContext, "点击了---删除开始地址");
                break;
            case R.id.end_address_tv:
                TUtils.showShort(mContext, "点击了---目的地址");
                break;
            case R.id.add_biaoqian_tv:
                TUtils.showShort(mContext, "点击了---添加标签");
                break;
            case R.id.del_biaoqian_tv:
                TUtils.showShort(mContext, "点击了---删除标签");
                break;
        }
    }

    @Override
    public void publishTask() {
        Toast.makeText(mContext, "发布成功", Toast.LENGTH_SHORT).show();
        Log.d("PubPaotuiActivity", "发布成功+--------");
    }

    @Override
    public void testTask() {

    }

}
