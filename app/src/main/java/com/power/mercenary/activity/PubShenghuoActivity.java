package com.power.mercenary.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.okgo.model.Response;
import com.power.mercenary.MyApplication;
import com.power.mercenary.R;
import com.power.mercenary.base.BaseActivity;
import com.power.mercenary.bean.PayBean;
import com.power.mercenary.bean.mytask.PublishTaskBean;
import com.power.mercenary.bean.task.ApplyListBean;
import com.power.mercenary.bean.task.MsgBean;
import com.power.mercenary.bean.task.MsgListBean;
import com.power.mercenary.bean.task.TaskDetailsBean;
import com.power.mercenary.http.DialogCallback;
import com.power.mercenary.http.HttpManager;
import com.power.mercenary.http.ResponseBean;
import com.power.mercenary.presenter.PubTaskPresenter;
import com.power.mercenary.presenter.TaskDetailsPresenter;
import com.power.mercenary.presenter.publish.PublishPresenter;
import com.power.mercenary.utils.MyUtils;
import com.power.mercenary.utils.TUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 任务
 */


public  class PubShenghuoActivity extends BaseActivity implements PubTaskPresenter.PubTaskCallBack, RadioGroup.OnCheckedChangeListener,PublishPresenter.PublishCallBack ,TaskDetailsPresenter.TaskDetailsCallBack{

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
    @BindView(R.id.Rb)
    RadioButton checkbox;
    @BindView(R.id.task_money_et)
    EditText taskMoneyEt;
    @BindView(R.id.validity_time_et)
    EditText validityTimeEt;
    @BindView(R.id.add_biaoqian_tv)
    TextView addBiaoqianTv;
    @BindView(R.id.biaoqian_et)
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
    @BindView(R.id.newbiaoqian_recycler)
    RecyclerView newbiaoqianRecycler;
    @BindView(R.id.add_newbiaoqian_tv)
    TextView addNewbiaoqianTv;
    @BindView(R.id.Rb_no)
    RadioButton checkboxNo;
    @BindView(R.id.saixuan_Rg)
    RadioGroup saixuanRg;
    private ArrayList<String> requireList;
    private ArrayList<String> biaoqianList;
    private RequireAdapter requireAdapter;
    private PubTaskPresenter presenter;
    private List<String> mlist = new ArrayList<>();
    private HashMap<Integer, String> mHashMap = new HashMap<>();
    private ImageView img_del_table;
    private String IsdelTable = "";
    private String taskType;
    private String childTaskType;
    private NewbqAdapter newbqAdapter;
    private String task_shaixuan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shenghuo_pub);
        ButterKnife.bind(this);
        taskType = getIntent().getStringExtra("TaskType");
        childTaskType = getIntent().getStringExtra("ChildTaskType");
        initView();
        presenter = new PubTaskPresenter(this, this);

    }

    private void initView() {
        saixuanRg.setOnCheckedChangeListener(this);

        taskMudiEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (taskMudiEt.getText().toString().length() > 200) {
                    Toast.makeText(mContext, "最多可输入200字", Toast.LENGTH_SHORT).show();
                    return;
                }
                mudiZishuTv.setText(taskMudiEt.getText().toString().length() + "/200");
            }
        });
        taskDetailEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (taskDetailEt.getText().toString().length() >= 200) {
                    Toast.makeText(mContext, "最多可输入200字", Toast.LENGTH_SHORT).show();
                    return;
                }
                detailZishuTv.setText(taskDetailEt.getText().toString().length() + "/200");
            }
        });
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
        biaoqianList.add("");
        newbiaoqianRecycler.setNestedScrollingEnabled(false);
        newbiaoqianRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        newbqAdapter = new NewbqAdapter(R.layout.item_require_layout, biaoqianList);
        newbiaoqianRecycler.setAdapter(newbqAdapter);
    }


    @Override
    public void testTask() {
        Toast.makeText(mContext, "成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.Rb:
                task_shaixuan = "1";
                break;
            case R.id.Rb_no:
                task_shaixuan = "0";
                break;
        }
    }

    @Override
    public void getPublishTaskList(List<PublishTaskBean.DataBean> datas) {
        //请求支付接口
        TaskDetailsPresenter taskDetailsPresenter = new TaskDetailsPresenter(this, this);
        taskDetailsPresenter.toPay(datas.get(0).getId());

    }

    @Override
    public void getPublishTaskListFail() {

    }

    @Override
    public void putTaskRequestSuccess(int position) {

    }

    @Override
    public void outTaskRequestSuccess(int position) {

    }

    @Override
    public void auditTaskRequestSuccess(int type, int position) {

    }

    @Override
    public void appraiseRequestSuccess() {

    }

    @Override
    public void getTaskDetails(TaskDetailsBean datas) {

    }

    @Override
    public void publishMsg(MsgBean datas) {

    }

    @Override
    public void getApplyList(List<ApplyListBean> datas) {

    }

    @Override
    public void getMsgList(List<MsgListBean> datas) {

    }

    @Override
    public void applyRequest() {

    }

    @Override
    public void changePeople(Response<ResponseBean<Void>> response, String avatar, String name) {

    }

    @Override
    public void changeCollection() {

    }

    @Override
    public void getMsgListFail() {

    }

    @Override
    public void toPayRequest(PayBean data) {
            WebActivity.invoke(this,data.getUrl(),"");
            finish();
            Toast.makeText(this,"发布成功",Toast.LENGTH_LONG).show();

    }

    @Override
    public void AddJiedan() {

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
                    requireList.remove(helper.getAdapterPosition());
                    requireAdapter.notifyDataSetChanged();


                }
            });
            helper.setText(R.id.item_name_tv, "要求" + num);
        }
    }

    private class NewbqAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public NewbqAdapter(int layoutResId, @Nullable List<String> data) {
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
                        biaoqianList.set(helper.getAdapterPosition(), item_content_et.getText().toString());
                    }
                }
            });

            item_del_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    biaoqianList.remove(helper.getAdapterPosition());
                    newbqAdapter.notifyDataSetChanged();


                }
            });
            helper.setText(R.id.item_name_tv, "要求" + num);
        }
    }


    @OnClick({R.id.title_back_iv, R.id.title_content_right_tv, R.id.add_require_tv, R.id.add_biaoqian_tv,
            R.id.del_biaoqian_tv, R.id.detail_zishu_tv, R.id.add_newbiaoqian_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back_iv:
                finish();
                break;
            case R.id.title_content_right_tv:
//                presenter.test(requireList);

                if (TextUtils.isEmpty(taskNameEt.getText().toString())) {
                    Toast.makeText(mContext, "请输入任务名称", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(taskMudiEt.getText().toString())) {
                    Toast.makeText(mContext, "请输入任务目的", Toast.LENGTH_SHORT).show();
                    return;
                } else if (requireList.size() == 1 && requireList.get(0).equals("")) {
                    Toast.makeText(mContext, "请输入任务要求", Toast.LENGTH_SHORT).show();
                    return;
                }else if (checkbox.isChecked() == false && checkboxNo.isChecked()==false) {
                    TUtils.showCustom(this,"请选择筛选要求");
                    return;

                } else if (TextUtils.isEmpty(taskMoneyEt.getText().toString())) {
                    Toast.makeText(mContext, "请输入佣金金额", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(validityTimeEt.getText().toString())) {
                    Toast.makeText(mContext, "请输入任务有效期", Toast.LENGTH_SHORT).show();
                    return;
                } else if (biaoqianList.size() <= 0) {
                    Toast.makeText(mContext, "请输入任务标签", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(taskDetailEt.getText().toString())) {
                    Toast.makeText(mContext, "请输入任务详情介绍", Toast.LENGTH_SHORT).show();
                    return;
                }

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
                String s = MyUtils.listToString(requireList);
                String s1 = MyUtils.listToString(biaoqianList);
                    presenter.publishTask("", taskType, childTaskType, task_shaixuan, taskNameEt.getText().toString(), s1, "", (Double.parseDouble(taskMoneyEt.getText().toString()) * 100) + "",
                            validityTimeEt.getText().toString(), taskDetailEt.getText().toString(), taskMudiEt.getText().toString(), s,
                            "", "", "", "1530961214",
                            "开始地址", "目的地址", "");
                break;
            case R.id.add_require_tv:
                requireList.add("");
                requireAdapter.notifyItemInserted(requireList.size() - 1);
                break;
            case R.id.add_newbiaoqian_tv:
                biaoqianList.add("");
                newbqAdapter.notifyItemInserted(biaoqianList.size() - 1);
                break;
            case R.id.add_biaoqian_tv:
                break;
            case R.id.del_biaoqian_tv:
                break;
            case R.id.detail_zishu_tv:
                break;
        }
    }

    @Override
    public void publishTask() {
        if (TextUtils.equals(task_shaixuan,"0")){
            Toast.makeText(mContext, "请先支付", Toast.LENGTH_SHORT).show();
            //发发布完任务之后请求自己发布的任务的接口获取任务的id
            PublishPresenter publishPresenter = new PublishPresenter(this, this);
            publishPresenter.getPublishTaskList(10);
        }else{
            finish();
            Toast.makeText(this,"发布完成",Toast.LENGTH_LONG).show();
        }

    }

}
