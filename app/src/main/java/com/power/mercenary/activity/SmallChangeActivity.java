package com.power.mercenary.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.power.mercenary.R;
import com.power.mercenary.base.BaseActivity;
import com.power.mercenary.bean.MingxiBean;
import com.power.mercenary.bean.MingxiInfo;
import com.power.mercenary.bean.ValueInfo;
import com.power.mercenary.presenter.ValuePresenter;
import com.power.mercenary.utils.MyUtils;
import com.power.mercenary.view.AllPop;
import com.power.mercenary.view.DistancePop;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/3/26.
 *
 * 零钱明细
 */

public class SmallChangeActivity extends BaseActivity implements View.OnClickListener, AllPop.InstalledCapacityListener, DistancePop.DistanceSelectorListener, ValuePresenter.ValueCallBack {

    @BindView(R.id.left_back)
    ImageView left_back;

    @BindView(R.id.title_text)
    TextView title_text;
    @BindView(R.id.mRecycler_sc)
    RecyclerView mRecycler_sc;

    ArrayList<String> mList = new ArrayList<>();
    @BindView(R.id.img_Date)
    ImageView imgDate;
    @BindView(R.id.rl_title_bg)
    RelativeLayout rlTitleBg;
    @BindView(R.id.tv_month_Date)
    TextView tvMonthDate;
    @BindView(R.id.tv_shouru_price)
    TextView tvShouruPrice;
    @BindView(R.id.tv_zhichu_price)
    TextView tvZhichuPrice;
    private List<String> sortitem, distance;
    private AllPop allPop;
    private DistancePop distancePop;

    @BindView(R.id.btn_ps_dq)
    Button btn_ps_dq;

    @BindView(R.id.btn_ps_rl)
    Button btn_ps_rl;

    @BindView(R.id.btn_ps_state)
    Button btn_ps_state;
    private SmallChangeAdapter changegameAdapter;
    private ValuePresenter presenter;
    private String[] all;
    private List<MingxiInfo.AllBean> alllist;
    private List<MingxiInfo.ShouruBean> shourulist;
    private List<MingxiInfo.ZhichuBean> zhichulist;
    private List<MingxiBean> AllInfo = new ArrayList<>();


    private OptionsPickerView pvCustomOptions;
    private List<String> yearList = new ArrayList<>();
    private List<String> monthList = new ArrayList<>();
    private List<String> dayList = new ArrayList<>();
    private String currentDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_small_change);
        ButterKnife.bind(this);


        currentDate = MyUtils.getCurrentDate1();
        all = currentDate.split(",");
        tvMonthDate.setText(currentDate);

        presenter = new ValuePresenter(this, this);
        presenter.getMingxiInfo("DESC ", all[0], all[1]);
        title_text.setText("零钱明细");


        sortitem = new ArrayList<>();
        distance = new ArrayList<>();
        sortitem.add("全部");
        sortitem.add("收入");
        sortitem.add("支出");
        distance.add("时间由近及远");
        distance.add("时间由远及近");


        allPop = new AllPop(SmallChangeActivity.this, R.layout.all_item_view, sortitem, btn_ps_dq);
        distancePop = new DistancePop(SmallChangeActivity.this, R.layout.all_item_view, distance, btn_ps_rl);
        btn_ps_dq.setOnClickListener(this);
        btn_ps_rl.setOnClickListener(this);
        btn_ps_state.setOnClickListener(this);
        allPop.setOnDismissListener(onDismissListener);
        allPop.setonInstalledCapacityListener(this);
        distancePop.setOnDismissListener(onDismissListener);
        distancePop.setonDistanceSelectorListener(this);
        left_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initSelectAge(final List<String> data, final List<String> data1, final List<String> data2) {
        pvCustomOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String year = data.get(options1);
                String month = data1.get(option2);
                String days = data2.get(options3);

                presenter.getMingxiInfo("DESC ", year.substring(0, year.length() - 1), days.substring(0, days.length() - 1));
                pvCustomOptions.setSelectOptions(options1, option2);
                tvMonthDate.setText(year.substring(0, year.length() - 1) + "-" + days.substring(0, days.length() - 1));
            }
        })
                .setLayoutRes(R.layout.view_custom_age, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        final TextView tvCancle = (TextView) v.findViewById(R.id.tv_cancle);
                        final TextView tv_content = (TextView) v.findViewById(R.id.tv_content);
                        tv_content.setText("");
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptions.returnData();
                                pvCustomOptions.dismiss();

                            }
                        });

                        tvCancle.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptions.dismiss();

                            }
                        });
                    }
                })
                .setContentTextSize(20)//设置滚轮文字大小
                .setBgColor(getResources().getColor(R.color.concrete))
                .setTextColorOut(getResources().getColor(R.color.textColorDrak))
                .setDividerColor(getResources().getColor(R.color.textColorDrak))
                .setTextColorCenter(getResources().getColor(R.color.black)) //设置选中项文字颜色
                .build();
        pvCustomOptions.setNPicker(data, data1, data2);//添加数据

    }

    private PopupWindow.OnDismissListener onDismissListener = new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {
            setWindowTranslucence(1.0f);
//获得Drawable对象
            Drawable drawable1 = ContextCompat.getDrawable(SmallChangeActivity.this, R.drawable.huixia_2x);
            drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());
            btn_ps_rl.setCompoundDrawables(null, null, drawable1, null);
            btn_ps_rl.setTextColor(getResources().getColor(R.color.black));
            btn_ps_state.setTextColor(getResources().getColor(R.color.black));
            btn_ps_dq.setCompoundDrawables(null, null, drawable1, null);
            btn_ps_dq.setTextColor(getResources().getColor(R.color.black));
        }
    };

    @Override
    public void onClick(View view) {
        Drawable drawable = ContextCompat.getDrawable(SmallChangeActivity.this, R.drawable.shang_2x);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        switch (view.getId()) {
            case R.id.btn_ps_dq:

                //获得Drawable对象
                btn_ps_dq.setCompoundDrawables(null, null, drawable, null);
                btn_ps_dq.setTextColor(getResources().getColor(R.color.colorPrimary));
                btn_ps_state.setTextColor(getResources().getColor(R.color.black));
                if (allPop != null) {
                    if (allPop.isShowing()) {
                        allPop.dismiss();
                    } else {
//                        setShowPop(allPop,btn_ps_dq);
                        allPop.showAsDropDown(view);
                    }
                }


                break;
            case R.id.btn_ps_rl:

                //获得Drawable对象
                btn_ps_rl.setCompoundDrawables(null, null, drawable, null);
                btn_ps_rl.setTextColor(getResources().getColor(R.color.colorPrimary));
                btn_ps_state.setTextColor(getResources().getColor(R.color.black));
                if (distancePop != null) {
                    if (distancePop.isShowing()) {
                        distancePop.dismiss();
                    } else {
//                        setShowPop(allPop,btn_ps_dq);
                        distancePop.showAsDropDown(view);
                    }
                }

                break;
            case R.id.btn_ps_state:

                btn_ps_state.setTextColor(getResources().getColor(R.color.colorPrimary));

                break;
        }
    }

    public void setShowPop(PopupWindow popupWindow, View view) {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            setWindowTranslucence(0.3);
            popupWindow.showAsDropDown(view);
        }
    }

    //设置Window窗口的透明度
    public void setWindowTranslucence(double d) {

        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.alpha = (float) d;
        window.setAttributes(attributes);

    }

    @Override
    public void onInstalledCapacityListener(int pos) {
        if (pos == 0) {
            if (alllist.size() <= 0 || alllist == null) {
                return;
            }
            AllInfo.clear();
            for (int i = 0; i < alllist.size(); i++) {
                MingxiBean bean = new MingxiBean();
                bean.setMoney(alllist.get(i).getMoney());
                bean.setPublish_time(alllist.get(i).getPublish_time());
                bean.setTask_description(alllist.get(i).getTask_description());
                bean.setTask_name(alllist.get(i).getTask_name());
                AllInfo.add(bean);
            }
        } else if (pos == 1) {
            if (shourulist.size() <= 0 || shourulist == null) {
                return;
            }
            AllInfo.clear();
            for (int i = 0; i < shourulist.size(); i++) {
                MingxiBean bean = new MingxiBean();
                bean.setMoney(shourulist.get(i).getMoney());
                bean.setPublish_time(shourulist.get(i).getPublish_time());
                bean.setTask_description(shourulist.get(i).getTask_description());
                bean.setTask_name(shourulist.get(i).getTask_name());
                AllInfo.add(bean);
            }

        } else if (pos == 2) {
            if (zhichulist.size() <= 0 || zhichulist == null) {
                return;
            }
            AllInfo.clear();
            for (int i = 0; i < zhichulist.size(); i++) {
                MingxiBean bean = new MingxiBean();
                bean.setMoney(zhichulist.get(i).getMoney());
                bean.setPublish_time(zhichulist.get(i).getPublish_time());
                bean.setTask_description(zhichulist.get(i).getTask_description());
                bean.setTask_name(zhichulist.get(i).getTask_name());
                AllInfo.add(bean);
            }
        }
        if (changegameAdapter != null) {
            mRecycler_sc.setAdapter(changegameAdapter);
        }
    }

    @Override
    public void onDistanceSelectorListener(int pos) {
        if (pos == 0) {
            presenter.getMingxiInfo("DESC ", all[0], all[1]);
        } else {
            presenter.getMingxiInfo("ASC ", all[0], all[1]);
        }
    }


    @Override
    public void getValueInfo(ValueInfo ValueInfo) {

    }


    @Override
    public void getMingxiInfo(MingxiInfo MingxiInfo) {

        tvShouruPrice.setText("收入 "+MingxiInfo.getShouru_total());
        tvZhichuPrice.setText("支出 "+MingxiInfo.getZhichu_total());

        yearList.clear();
        monthList.clear();
        dayList.clear();
        for (int i = Integer.parseInt(MingxiInfo.getDate().getYear()); i <= Integer.parseInt(all[0]); i++) {
            yearList.add(i + "年");
        }
        monthList.add("");
        for (int i = 1; i <= 12; i++) {
            dayList.add(i + "月");
        }


        alllist = MingxiInfo.getAll();
        shourulist = MingxiInfo.getShouru();
        zhichulist = MingxiInfo.getZhichu();
        if (alllist.size() <= 0 || alllist == null) {
            mRecycler_sc.setVisibility(View.GONE);
            return;
        }
        AllInfo.clear();


        for (int i = 0; i < alllist.size(); i++) {
            MingxiBean bean = new MingxiBean();
            bean.setMoney(alllist.get(i).getMoney());
            bean.setPublish_time(alllist.get(i).getPublish_time());
            bean.setTask_description(alllist.get(i).getTask_description());
            bean.setTask_name(alllist.get(i).getTask_name());
            AllInfo.add(bean);
        }
        mRecycler_sc.setVisibility(View.VISIBLE);
        mRecycler_sc.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycler_sc.setNestedScrollingEnabled(false);
        changegameAdapter = new SmallChangeAdapter(R.layout.smallchange_item_view, AllInfo);
        mRecycler_sc.setAdapter(changegameAdapter);
    }

    @OnClick(R.id.img_Date)
    public void onClick() {
        initSelectAge(yearList, monthList, dayList);
        pvCustomOptions.show();
    }


    public class SmallChangeAdapter extends BaseQuickAdapter<MingxiBean, BaseViewHolder> {

        public SmallChangeAdapter(@LayoutRes int layoutResId, @Nullable List<MingxiBean> data) {
            super(layoutResId, data);

        }

        @Override
        protected void convert(BaseViewHolder helper, MingxiBean item) {
            helper.setText(R.id.tv_task_name, item.getTask_name());
            helper.setText(R.id.tv_task_content, item.getTask_description());
            helper.setText(R.id.tv_task_price, item.getMoney());

        }
    }
}
