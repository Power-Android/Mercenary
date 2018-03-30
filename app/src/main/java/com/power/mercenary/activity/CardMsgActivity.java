package com.power.mercenary.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.power.mercenary.R;
import com.power.mercenary.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/27.
 */

public class CardMsgActivity extends BaseActivity {

    @BindView(R.id.left_back)
    ImageView left_back;

    @BindView(R.id.title_text)
    TextView title_text;

    @BindView(R.id.tv_ydty)
    TextView tv_ydty;

    private boolean isyt;
    @BindView(R.id.cb_ty)
    CheckBox cb_ty;


    @BindView(R.id.ll_card_type)
    LinearLayout ll_card_type;

    @BindView(R.id.et_yhk_type)
    TextView et_yhk_type;

    private OptionsPickerView pvCustomOptions;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_card_msg);

        ButterKnife.bind(this);

        title_text.setText("填写银行卡信息");


        final List<String> feedbackList = new ArrayList<>();
        feedbackList.add("工商银行");
        feedbackList.add("农业银行");
        feedbackList.add("邮政储蓄银行");
        feedbackList.add("建设银行      储蓄卡");
        feedbackList.add("招商银行      信用卡");
        feedbackList.add("中国银行");
        feedbackList.add("农业银行");

        left_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_ydty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isyt){
                    cb_ty.setChecked(true);
                }else{
                    cb_ty.setChecked(false);
                }
                isyt=!isyt;
            }
        });
        ll_card_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                initCustomOptionPicker(feedbackList);
                pvCustomOptions.show();
            }
        });
    }

    private void initCustomOptionPicker(final List<String> data){
        pvCustomOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = data.get(options1);
                et_yhk_type.setText(tx);
            }
        })
                .setLayoutRes(R.layout.pickerview_custom_options, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        final TextView tvCancle = (TextView) v.findViewById(R.id.tv_cancle);

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
                .setSelectOptions(2)//默认选中项
                .setContentTextSize(20)//设置滚轮文字大小
                .setBgColor(getResources().getColor(R.color.concrete))
                .setTextColorOut(getResources().getColor(R.color.textColorDrak))
                .setDividerColor(getResources().getColor(R.color.textColorDrak))
                .setTextColorCenter(getResources().getColor(R.color.black)) //设置选中项文字颜色
                .build();
        pvCustomOptions.setPicker(data);//添加数据

    }

}
