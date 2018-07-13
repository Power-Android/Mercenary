package com.power.mercenary.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.power.mercenary.R.id.biaoqian_et;

public class PubShenghuoActivity extends BaseActivity implements PubTaskPresenter.PubTaskCallBack {

    @BindView(R.id.title_back_iv)
    ImageView titleBackIv;
    @BindView(R.id.title_content_tv)
    TextView titleContentTv;
    @BindView(R.id.title_content_right_tv)
    TextView titleContentRightTv;
    @BindView(R.id.task_name_et)
    EditText taskNameEt;
    @BindView(R.id.mudi_zishu_tv)
    TextView mudiZishuTv;
    @BindView(R.id.task_mudi_et)
    EditText taskMudiEt;
    @BindView(R.id.require_recycler)
    RecyclerView requireRecycler;
    @BindView(R.id.add_require_tv)
    TextView addRequireTv;
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.task_money_et)
    EditText taskMoneyEt;
    @BindView(R.id.validity_time_et)
    EditText validityTimeEt;
    @BindView(R.id.add_biaoqian_tv)
    TextView addBiaoqianTv;
    @BindView(biaoqian_et)
    EditText biaoqianEt;
    @BindView(R.id.del_biaoqian_tv)
    TextView delBiaoqianTv;
    @BindView(R.id.biaoqian_recycler)
    RecyclerView biaoqianRecycler;
    @BindView(R.id.detail_zishu_tv)
    TextView detailZishuTv;
    @BindView(R.id.task_detail_et)
    EditText taskDetailEt;
    @BindView(R.id.view_01)
    TextView view01;
    @BindView(R.id.view_02)
    TextView view02;
    @BindView(R.id.view_03)
    TextView view03;
    @BindView(R.id.relative_table)
    RelativeLayout relativeTable;
    private ArrayList<String> requireList;
    private ArrayList<String> biaoqianList;
    private RequireAdapter requireAdapter;
    private BiaoqianAdapter biaoqianAdapter;
    private PubTaskPresenter presenter;
    private List<String> mlist = new ArrayList<>();
    private HashMap<Integer, String> mHashMap = new HashMap<>();
    private ImageView img_del_table;
    private String IsdelTable="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shenghuo_pub);
        ButterKnife.bind(this);
        initView();
        presenter = new PubTaskPresenter(this, this);
    }

    private void initView() {
        titleBackIv.setVisibility(View.VISIBLE);
        titleContentTv.setText("发布任务");
        titleContentRightTv.setVisibility(View.VISIBLE);
        titleContentRightTv.setText("发布");

        requireList = new ArrayList<>();
        requireList.add("");
        requireRecycler.setNestedScrollingEnabled(false);
        requireRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        requireAdapter = new RequireAdapter(R.layout.item_require_layout, requireList);
        requireRecycler.setAdapter(requireAdapter);

        biaoqianList = new ArrayList<>();
        biaoqianRecycler.setNestedScrollingEnabled(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        biaoqianRecycler.setLayoutManager(linearLayoutManager);
        biaoqianAdapter = new BiaoqianAdapter(R.layout.task_table_layout, biaoqianList);
        biaoqianRecycler.setAdapter(biaoqianAdapter);
        if (biaoqianList.size() <= 0) {
            relativeTable.setVisibility(View.GONE);
        }

    }



    @Override
    public void testTask() {
        Toast.makeText(mContext, "成功", Toast.LENGTH_SHORT).show();
    }

    private class RequireAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public RequireAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, final String item) {
            final int num = helper.getAdapterPosition() + 1;
            ImageView item_del_iv = helper.getView(R.id.item_del_iv);
            final EditText item_content_et = helper.getView(R.id.item_content_et);
            if (item != null) {
                item_content_et.setText(item);
            }
            item_content_et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (!item_content_et.getText().toString().equals("")) {
                        requireList.set(helper.getAdapterPosition(), item_content_et.getText().toString());
                    }
                }
            });

            item_del_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "helper.getAdapterPosition():" + helper.getAdapterPosition() + "---------");
                    Log.d(TAG, "requireList.size():" + requireList.size() + "---------");
                    requireList.remove(helper.getAdapterPosition());
                    requireAdapter.notifyDataSetChanged();


                }
            });
            helper.setText(R.id.item_name_tv, "要求" + num);
        }
    }

    private class BiaoqianAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public BiaoqianAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, String item) {
            helper.setText(R.id.item_content_tv, item);
            img_del_table = helper.getView(R.id.img_del_table);
            if (IsdelTable.equals("1")){
                img_del_table.setVisibility(View.VISIBLE);
            }else {
                img_del_table.setVisibility(View.GONE);
            }
            helper.setOnClickListener(R.id.img_del_table, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    IsdelTable="2";
                    biaoqianList.remove(helper.getAdapterPosition());
                    biaoqianAdapter.notifyDataSetChanged();
                    if (biaoqianList.size() <= 0) {
                        relativeTable.setVisibility(View.GONE);
                    }
                }
            });

        }
    }

    @OnClick({R.id.title_back_iv, R.id.title_content_right_tv, R.id.add_require_tv, R.id.add_biaoqian_tv,
            R.id.del_biaoqian_tv, R.id.detail_zishu_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back_iv:
                finish();
                break;
            case R.id.title_content_right_tv:
//                presenter.test(requireList);

//                if (TextUtils.isEmpty(taskNameEt.getText().toString())) {
//                    Toast.makeText(mContext, "请输入任务名称", Toast.LENGTH_SHORT).show();
//                    return;
//                } else if (TextUtils.isEmpty(taskMudiEt.getText().toString())) {
//                    Toast.makeText(mContext, "请输入任务目的", Toast.LENGTH_SHORT).show();
//                    return;
//                } else if (requireList.size()<=0) {
//                    Toast.makeText(mContext, "请输入任务要求", Toast.LENGTH_SHORT).show();
//                    return;
//                } else if (TextUtils.isEmpty(taskMoneyEt.getText().toString())) {
//                    Toast.makeText(mContext, "请输入佣金金额", Toast.LENGTH_SHORT).show();
//                    return;
//                } else if (TextUtils.isEmpty(validityTimeEt.getText().toString())) {
//                    Toast.makeText(mContext, "请输入任务有效期", Toast.LENGTH_SHORT).show();
//                    return;
//                } else if (biaoqianList.size()<=0) {
//                    Toast.makeText(mContext, "请输入任务标签", Toast.LENGTH_SHORT).show();
//                    return;
//                }else if (TextUtils.isEmpty(taskDetailEt.getText().toString())) {
//                    Toast.makeText(mContext, "请输入任务详情介绍", Toast.LENGTH_SHORT).show();
//                    return;
//                }
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
                String s = listToString(requireList);
                String s1 = listToString(biaoqianList);

                presenter.publishTask("1", "1", taskNameEt.getText().toString(), s1, "", taskMoneyEt.getText().toString(),
                        validityTimeEt.getText().toString(), taskDetailEt.getText().toString(), taskMudiEt.getText().toString(), s,
                        "", "", "", "1530961214",
                        "开始地址", "目的地址", "");
                break;
            case R.id.add_require_tv:
                requireList.add("");
                requireAdapter.notifyItemInserted(requireList.size() - 1);
                break;
            case R.id.add_biaoqian_tv:
                if (biaoqianList.size()>=5){
                    Toast.makeText(mContext, "最多可添加五个标签", Toast.LENGTH_SHORT).show();
                    biaoqianEt.setText("");
                    return;
                }
                if (biaoqianEt.getText().length() < 2) {
                    Toast.makeText(mContext, "请输入2-4个文字标签", Toast.LENGTH_SHORT).show();
                    return;
                }
                biaoqianList.add(biaoqianEt.getText().toString());
                biaoqianEt.setText("");
                if (biaoqianList.size() > 0) {
                    relativeTable.setVisibility(View.VISIBLE);
                }
                biaoqianAdapter.notifyDataSetChanged();
                break;
            case R.id.del_biaoqian_tv:
                IsdelTable="1";
                img_del_table.setVisibility(View.VISIBLE);
                biaoqianAdapter.notifyDataSetChanged();
                break;
            case R.id.detail_zishu_tv:
                break;
        }
    }
    @Override
    public void publishTask() {
        Toast.makeText(mContext, "发布成功", Toast.LENGTH_SHORT).show();
    }
    public String listToString(ArrayList<String> stringList){
        if (stringList == null) {
            return null;
        }
        StringBuilder result=new StringBuilder();
        boolean flag=false;
        for (String string : stringList) {
            if (flag) {
                result.append("|"); // 分隔符
            }else {
                flag=true;
            }
            result.append(string);
        }
        return result.toString();
    }
}
