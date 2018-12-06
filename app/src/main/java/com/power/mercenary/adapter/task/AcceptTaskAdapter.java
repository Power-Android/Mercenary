package com.power.mercenary.adapter.task;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.model.Response;
import com.power.mercenary.MyApplication;
import com.power.mercenary.R;
import com.power.mercenary.activity.TaskListActivity;
import com.power.mercenary.bean.SuccessBean;
import com.power.mercenary.bean.mytask.AcceptTaskBean;
import com.power.mercenary.http.DialogCallback;
import com.power.mercenary.http.HttpManager;
import com.power.mercenary.http.ResponseBean;
import com.power.mercenary.utils.DoubleUtils;
import com.power.mercenary.utils.MercenaryUtils;
import com.power.mercenary.utils.MyUtils;
import com.power.mercenary.utils.SharedPreferencesUtils;
import com.power.mercenary.view.BaseDialog;

import java.text.DecimalFormat;
import java.util.List;

/**
 * admin  2018/7/18 wan
 */
public class AcceptTaskAdapter extends RecyclerView.Adapter implements SeekBar.OnSeekBarChangeListener {


    private static final String TAG = "AcceptTaskAdapter";
    private Context context;
    private int state;
    private List<AcceptTaskBean> data;

    private OnItemClickListener onItemClickListener;
    private EditText edt_pub_people;
    private EditText edt_shou_people;
    private TextView tv_all_price;
    private CheckBox cb_all_parice;
    private CheckBox cb_zdy_parice;
    private TextView tv_pingtai_peice;
    private BaseDialog mDialog;
    private BaseDialog.Builder mBuilder;
    private int tuiType;
    private boolean Zzt = false;
    private TextView tv_pub_name;
    private String text;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public AcceptTaskAdapter(Context context, List<AcceptTaskBean> data, int state) {
        this.context = context;
        this.data = data;
        this.state = state;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view_accept_task, null);
        return new AcceptViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof AcceptViewHolder) {
            AcceptViewHolder viewHolder = (AcceptViewHolder) holder;

            viewHolder.price.setText("￥" + data.get(position).getPay_amount());

            viewHolder.title.setText(data.get(position).getTask_name());

            viewHolder.content.setText(data.get(position).getTask_description());
            if (data.get(position).getTask_status().equals("7") || data.get(position).getTask_status().equals("6")) {
                viewHolder.imageView.setVisibility(View.VISIBLE);
                if (data.get(position).getSettle_status().equals("3")) {
                    viewHolder.tuikuan.setVisibility(View.VISIBLE);
                    viewHolder.tuikuan.setText("已退款");
                    viewHolder.tuikuan.setEnabled(false);
                }
            } else if (data.get(position).getTask_status().equals("2")) {//任务中
                viewHolder.tuikuan.setVisibility(View.VISIBLE);
                if (!data.get(position).getRefuse_cause().equals("")) {//拒绝原因不为空的时候进行给控件赋值
                    viewHolder.layoutJujue.setVisibility(View.VISIBLE);
                    viewHolder.tvJujue.setText(data.get(position).getRefuse_cause());
                    boolean flag = (boolean) SharedPreferencesUtils.getParam(context, "flag", false);
                    if (flag == false) {
                        viewHolder.tuikuan.setText("退款");
                    } else if (flag == true) {
                        viewHolder.tuikuan.setText("未通过");
                        viewHolder.tuikuan.setEnabled(false);


                    }

                    //viewHolder.tuikuan.setEnabled(true);
                }
            } else if (data.get(position).getTask_status().equals("3")) {//审核中
                if (data.get(position).getSettle_status().equals("2")) {
                    viewHolder.tuikuan.setVisibility(View.VISIBLE);
                    viewHolder.tuikuan.setText("退款中");
                    viewHolder.tuikuan.setEnabled(false);
                }
                if (data.get(position).getIs_yanqi().equals("1")) {//是否延期
                    viewHolder.tuikuan.setText("已延期处理");
                    viewHolder.tuikuan.setVisibility(View.VISIBLE);
                    viewHolder.tuikuan.setEnabled(false);
                    viewHolder.layout_yanqi.setVisibility(View.VISIBLE);
                    viewHolder.tvCause.setText(data.get(position).getYanqi_reason());
                    viewHolder.tvDays.setText(data.get(position).getYanqi_days());
                    viewHolder.tvYanTime.setText(MyUtils.getDateToStringTime(data.get(position).getYanqi_start()));
                }
            } else {
                viewHolder.imageView.setVisibility(View.GONE);
            }

            viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClickListener(data.get(position).getTask_type(), data.get(position).getId(), data.get(position).getTask_status());
                }
            });
            viewHolder.tuikuan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showIssueDialog(position);
                }
            });
            viewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            TaskListActivity.TagAdapter tagAdapter = new TaskListActivity.TagAdapter(R.layout.item_tag_layout, MercenaryUtils.stringToList(data.get(position).getTask_tag()));
            viewHolder.recyclerView.setAdapter(tagAdapter);
        }
    }

    private void showIssueDialog(final int position) {
        mBuilder = new BaseDialog.Builder(context);
        mDialog = mBuilder.setViewId(R.layout.dialog_tuikuan)
                //设置dialogpadding
                .setPaddingdp(0, 0, 0, 0)
                //设置显示位置
                .setGravity(Gravity.CENTER)
                //设置动画
                .setAnimation(R.style.Bottom_Top_aniamtion)
                //设置dialog的宽高
                .setWidthHeightpx(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                //设置触摸dialog外围是否关闭
                .isOnTouchCanceled(true)
                //设置监听事件
                .builder();
        cb_all_parice = mDialog.getView(R.id.cb_all_parice);
        cb_zdy_parice = mDialog.getView(R.id.cb_zdy_parice);
        tv_pub_name = mDialog.getView(R.id.tv_pub_name);
        final LinearLayout all_data_layout = mDialog.getView(R.id.all_data_layout);
        final LinearLayout pingtai_layout = mDialog.getView(R.id.pingtai_layout);
        tv_all_price = mDialog.getView(R.id.tv_all_price);
        final TextView tv_zfpt_price = mDialog.getView(R.id.tv_zfpt_price);
        final SeekBar seekbar = mDialog.getView(R.id.seekbar);
        edt_pub_people = mDialog.getView(R.id.edt_pub_people);
        edt_shou_people = mDialog.getView(R.id.edt_shou_people);
        tv_pingtai_peice = mDialog.getView(R.id.tv_pingtai_peice);
        TextView tv_sure = mDialog.getView(R.id.tv_sure);
        TextView tv_cancle = mDialog.getView(R.id.tv_cancle);
        final LinearLayout layout_jieshou_people = mDialog.getView(R.id.layout_jieshou_people);
        final LinearLayout layout_all_price = mDialog.getView(R.id.layout_all_price);

        final String s = tv_zfpt_price.getText().toString();

        String[] oldPrice = s.split("\\(");

        String price = oldPrice[0];
        final String[] newPrice = price.split("\\)");
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new HttpManager<ResponseBean<SuccessBean>>("Home/MyTask/tuikuan", this)
                        .addParams("token", MyApplication.getUserToken())
                        .addParams("id", data.get(position).getId())
                        .addParams("tuikuan_type", tuiType)
                        .addParams("ticheng", Double.parseDouble(tv_pingtai_peice.getText().toString()) * 100 + "")
                        .addParams("zfpt_ticheng", Double.parseDouble(newPrice[0]) * 100 + "")
                        .addParams("fafang_money", Double.parseDouble(edt_shou_people.getText().toString()) * 100 + "")
                        .addParams("fabu_money", Double.parseDouble(edt_pub_people.getText().toString()) * 100 + "")
                        .postRequest(new DialogCallback<ResponseBean<SuccessBean>>((Activity) context) {
                            @Override
                            public void onSuccess(Response<ResponseBean<SuccessBean>> response) {
                                SharedPreferencesUtils.setParam(context, "flag", false);
                                onItemClickListener.TuiKuanListener();
                                mDialog.dismiss();
                            }

                            @Override
                            public void onError(Response<ResponseBean<SuccessBean>> response) {
                                super.onError(response);
                            }
                        });
            }
        });
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });
        DecimalFormat df = new java.text.DecimalFormat("0.00");
        String format = df.format(((Double.parseDouble(data.get(position).getPay_amount()) * 100 - Double.parseDouble(data.get(position).getPay_amount()) * 0.6)) / 100);
        edt_pub_people.setText(format);
        edt_shou_people.setText("0");

        tv_pub_name.setText(data.get(position).getFabu_name());

        double payAmout = Double.parseDouble(data.get(position).getPay_amount());
        double profit = (Double.parseDouble("0.6"));

        Double mul = DoubleUtils.mul(payAmout, profit);

        double sun = 0;

        sun = mul * (Double.parseDouble("0.01"));

        String profit_Value = DoubleUtils.getValue(sun);

        tv_zfpt_price.setText("(" + profit_Value + ")");

        int allPrice = (int) (Double.parseDouble(data.get(position).getPay_amount()) - Double.parseDouble(data.get(position).getPay_amount()) * 0.006);
        int o = (int) (((Double.parseDouble(data.get(position).getPay_amount())) * 100) - 100);
        int seekMaxNumber = 100;
        if (allPrice < 0.9) {
            seekMaxNumber = 9;
        }
        if (allPrice < 99.9 && allPrice > 0.9) {
            seekMaxNumber = 99;
        }
        if (allPrice > 99.9) {
            seekMaxNumber = 100;
        }
        double NumPrice = (Double.parseDouble(data.get(position).getPay_amount()) - Double.parseDouble(data.get(position).getPay_amount()) * 0.006);
        double twoDecimal = getTwoDecimal(NumPrice);
        tv_all_price.setText(twoDecimal + "");
        seekbar.setMax(seekMaxNumber);
        seekbar.setProgress(0);
      /*  seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int progress = seekBar.getProgress();

                Log.e(TAG, "onProgressChanged: " + progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

                //修改当进度条改变 有负数的情况
                int progress = seekBar.getProgress();

                Log.e(TAG, "onProgressChanged: " + progress);
                if (progress < 0) {

                    seekBar.setProgress(0);
                }

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });*/

        seekbar.setOnSeekBarChangeListener(this);
        mDialog.getView(R.id.all_price_tuikuan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cb_all_parice.setChecked(true);
                cb_zdy_parice.setChecked(false);
                all_data_layout.setVisibility(View.VISIBLE);
                pingtai_layout.setVisibility(View.GONE);
                layout_jieshou_people.setVisibility(View.GONE);
                layout_all_price.setVisibility(View.GONE);
                DecimalFormat df = new java.text.DecimalFormat("0.00");
                String format = df.format(((Double.parseDouble(data.get(position).getPay_amount()) * 100 - Double.parseDouble(data.get(position).getPay_amount()) * 0.6)) / 100);
                edt_pub_people.setText(format);
                edt_shou_people.setText("0");
//                edt_pub_people.setText((Double.parseDouble(data.get(position).getPay_amount()) - Double.parseDouble(data.get(position).getPay_amount()) * 0.006) + "");
                tuiType = 1;
            }
        });
        mDialog.getView(R.id.zdy_price_tuikuan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cb_all_parice.setChecked(false);
                cb_zdy_parice.setChecked(true);
                all_data_layout.setVisibility(View.VISIBLE);
                pingtai_layout.setVisibility(View.VISIBLE);
                layout_jieshou_people.setVisibility(View.VISIBLE);
                layout_all_price.setVisibility(View.VISIBLE);
                tuiType = 2;
            }
        });
        mDialog.show();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        if (cb_zdy_parice.isChecked()) {
            DecimalFormat df1 = new java.text.DecimalFormat("0.00");
            String format2 = df1.format((Double.parseDouble(tv_all_price.getText().toString()) * 100 - (Double.parseDouble(tv_all_price.getText().toString()) * 100 - seekBar.getProgress())) / 100);
            edt_pub_people.setText(format2);//发布人金额
            DecimalFormat df = new java.text.DecimalFormat("0.00");
            String format = df.format(((Double.parseDouble(tv_all_price.getText().toString()) * 100 - seekBar.getProgress()) * 0.044) / 100);
            tv_pingtai_peice.setText(format);//平台金额
            String format1 = df.format(((Double.parseDouble(tv_all_price.getText().toString()) * 100 - seekBar.getProgress()) - (Double.parseDouble(tv_pingtai_peice.getText().toString()) * 100)) / 100);
            edt_shou_people.setText(format1);//接受者金额

        }

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (cb_all_parice.isChecked()) {
//            edt_pub_people.setText(seekBar.getProgress() + "");
//            DecimalFormat df = new java.text.DecimalFormat("0.00");
//            String format = df.format(Double.parseDouble(tv_all_price.getText().toString()) - seekBar.getProgress());
//            edt_shou_people.setText(format);
        } else {
            DecimalFormat df1 = new java.text.DecimalFormat("0.00");
            String format2 = df1.format((Double.parseDouble(tv_all_price.getText().toString()) * 100 - (Double.parseDouble(tv_all_price.getText().toString()) * 100 - seekBar.getProgress())) / 100);
            edt_pub_people.setText(format2);
            DecimalFormat df = new java.text.DecimalFormat("0.00");
            String format = df.format(((Double.parseDouble(tv_all_price.getText().toString()) * 100 - seekBar.getProgress()) * 0.044) / 100);
            tv_pingtai_peice.setText(format);
            String format1 = df.format(((Double.parseDouble(tv_all_price.getText().toString()) * 100 - seekBar.getProgress()) - (Double.parseDouble(tv_pingtai_peice.getText().toString()) * 100)) / 100);
            edt_shou_people.setText(format1);
        }
    }

    class AcceptViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        TextView price;

        TextView content;

        RecyclerView recyclerView;

        ImageView imageView;

        LinearLayout mView;
        LinearLayout layoutJujue;
        LinearLayout layout_yanqi;
        TextView tvCause;
        TextView tvDays;
        TextView tvYanTime;
        TextView tuikuan;
        TextView tvJujue;

        public AcceptViewHolder(View itemView) {
            super(itemView);

            mView = itemView.findViewById(R.id.item_view_accept_layout);
            layoutJujue = itemView.findViewById(R.id.layout_jujue);
            tvJujue = itemView.findViewById(R.id.tv_jujue);
            layout_yanqi = itemView.findViewById(R.id.layout_yanqi);
            tvCause = itemView.findViewById(R.id.tv_cause);
            tvDays = itemView.findViewById(R.id.tv_days);
            tvYanTime = itemView.findViewById(R.id.tv_yanqi_time);
            title = itemView.findViewById(R.id.item_view_accept_title);
            price = itemView.findViewById(R.id.item_view_accept_price);
            content = itemView.findViewById(R.id.item_view_accept_content);
            recyclerView = itemView.findViewById(R.id.item_view_accept_recyclerView);
            imageView = itemView.findViewById(R.id.item_view_accept_success);
            tuikuan = itemView.findViewById(R.id.item_wjd_view_tuikuan);
        }
    }

    public interface OnItemClickListener {
        void onItemClickListener(String taskType, String taskId, String taskState);

        void TuiKuanListener();
    }

    private double getTwoDecimal(double num) {
        DecimalFormat dFormat = new DecimalFormat("#.00");
        String yearString = dFormat.format(num);
        Double temp = Double.valueOf(yearString);
        return temp;
    }


}
