package com.power.mercenary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.power.mercenary.R;
import com.power.mercenary.base.BaseActivity;
import com.power.mercenary.view.SelectorPop;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/22.
 */

public class SetupActivity extends BaseActivity implements View.OnClickListener{


    @BindView(R.id.left_back)
    ImageView left_back;

    @BindView(R.id.title_text)
    TextView title_text;

    @BindView(R.id.rl_bz)
    RelativeLayout rl_bz;
@BindView(R.id.ll_nc)
LinearLayout ll_nc;

    @BindView(R.id.rl_zhyaq)
    RelativeLayout rl_zhyaq;

    @BindView(R.id.ll_txsc)
    LinearLayout ll_txsc;
    @BindView(R.id.rl_gywm)
    RelativeLayout rl_gywm;

    @BindView(R.id.rl_wtfk)
    RelativeLayout rl_wtfk;
    private SelectorPop selectorPop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        ButterKnife.bind(this);
        title_text.setText("设置");
        selectorPop = new SelectorPop(SetupActivity.this,R.layout.selector_pop_item_view);
        selectorPop.setOnDismissListener(onDismissListener);
        left_back.setOnClickListener(this);
        rl_bz.setOnClickListener(this);
        ll_txsc.setOnClickListener(this);
        rl_gywm.setOnClickListener(this);
        ll_nc.setOnClickListener(this);
        rl_zhyaq.setOnClickListener(this);
        rl_wtfk.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        Intent intent;
        switch (view.getId()){
            case R.id.left_back:
                finish();
                break;
            case R.id.rl_bz:

                intent = new Intent(SetupActivity.this,HelpActivity.class);
                startActivity(intent);

                break;
            case R.id.ll_txsc:
                setShowPop(selectorPop,ll_txsc);
                break;
            case R.id.rl_gywm:

                intent = new Intent(SetupActivity.this,AboutUsActivity.class);
                startActivity(intent);

                break;
            case R.id.ll_nc:
                intent = new Intent(SetupActivity.this,ModifyNameActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_zhyaq:

                intent = new Intent(SetupActivity.this,SecurityActivity.class);

                startActivity(intent);

                break;
            case R.id.rl_wtfk:

                intent = new Intent(SetupActivity.this,ProblemFeedbackActivity.class);

                startActivity(intent);

                break;
        }

    }
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
