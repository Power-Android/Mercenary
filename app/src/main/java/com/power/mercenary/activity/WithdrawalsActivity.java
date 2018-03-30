package com.power.mercenary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.power.mercenary.R;
import com.power.mercenary.base.BaseActivity;
import com.power.mercenary.view.WithdrawalsPop;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/27.
 */

public class WithdrawalsActivity extends BaseActivity {

    @BindView(R.id.left_back)
    ImageView left_back;

    @BindView(R.id.title_text)
    TextView title_text;

    @BindView(R.id.tv_sqtx_tcdl)
    TextView tv_sqtx_tcdl;

    @BindView(R.id.rl_txz)
    RelativeLayout rl_txz;

    WithdrawalsPop withdrawalsPop;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_withdrawals);
        ButterKnife.bind(this);
        title_text.setText("申请提现");
        withdrawalsPop = new WithdrawalsPop(WithdrawalsActivity.this,R.layout.txz_item_view);
        withdrawalsPop.setOnDismissListener(onDismissListener);
        withdrawalsPop.setOnWithdrawalsClickListener(withdrawalsClickListener);
        left_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tv_sqtx_tcdl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WithdrawalsActivity.this,WithdrawalsDetailsActivity.class);
                startActivity(intent);
            }
        });

        rl_txz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setShowPop(withdrawalsPop,rl_txz);

            }
        });

    }

    private WithdrawalsPop.WithdrawalsClickListener withdrawalsClickListener = new WithdrawalsPop.WithdrawalsClickListener() {
        @Override
        public void OnCancelListener() {

            withdrawalsPop.dismiss();

        }

        @Override
        public void OnSelectorListener(int type) {


            withdrawalsPop.dismiss();

        }
    };



    public void setShowPop(PopupWindow popupWindow, View view){
        if(popupWindow!=null&&popupWindow.isShowing()){
            popupWindow.dismiss();
        }else{
            setWindowTranslucence(0.3);
            popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        }
    }
    //设置Window窗口的透明度
    public void setWindowTranslucence(double d){

        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.alpha=(float) d;
        window.setAttributes(attributes);

    }

    private PopupWindow.OnDismissListener onDismissListener = new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            setWindowTranslucence(1.0f);
        }
    };

}
