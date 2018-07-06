package com.power.mercenary.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.power.mercenary.R;
import com.power.mercenary.base.BaseActivity;
import com.power.mercenary.presenter.FeedBackPresenter;
import com.power.mercenary.utils.TUtils;
import com.power.mercenary.view.FKTypePop;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/23.
 */

public class ProblemFeedbackActivity extends BaseActivity implements FeedBackPresenter.FeedBackCallBack {

    @BindView(R.id.left_back)
    ImageView left_back;

    @BindView(R.id.title_text)
    TextView title_text;

    @BindView(R.id.rl_fklx)
    RelativeLayout rl_fklx;

    @BindView(R.id.fk_tv)
    TextView fkTv;
    @BindView(R.id.act_feedBack_content)
    EditText etContent;
    @BindView(R.id.act_feedBack_name)
    EditText etName;
    @BindView(R.id.act_feedBack_phone)
    EditText etPhone;

    private OptionsPickerView pvCustomOptions;

    private FeedBackPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_feedback);
        ButterKnife.bind(this);

        presenter = new FeedBackPresenter(this, this);

        title_text.setText("问题反馈");

        final List<String> feedbackList = new ArrayList<>();
        feedbackList.add("任务类型");
        feedbackList.add("任务类型");
        feedbackList.add("任务类型");
        feedbackList.add("任务类型");
        feedbackList.add("任务类型");
        feedbackList.add("任务类型");
        feedbackList.add("任务类型");

        left_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        rl_fklx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initCustomOptionPicker(feedbackList);
                pvCustomOptions.show();
            }
        });

        findViewById(R.id.tv_wtfk_tcdl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etContent.getText().toString())) {
                    TUtils.showCustom(ProblemFeedbackActivity.this, "请填写您的问题");
                } else if (TextUtils.isEmpty(etName.getText().toString())) {
                    TUtils.showCustom(ProblemFeedbackActivity.this, "请输入姓名");
                } else if (TextUtils.isEmpty(etPhone.getText().toString())) {
                    TUtils.showCustom(ProblemFeedbackActivity.this, "请输入手机号/邮箱/QQ");
                } else {
                    presenter.requestFeedBack(fkTv.getText().toString(), etContent.getText().toString(), etName.getText().toString(), etPhone.getText().toString());
                }
            }
        });
    }

    private void initCustomOptionPicker(final List<String> data){
        pvCustomOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = data.get(options1);
                    fkTv.setText(tx);
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


    @Override
    public void requestFeedBack() {
        finish();
    }
}
