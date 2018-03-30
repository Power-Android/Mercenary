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
import com.power.mercenary.view.AgePop;
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

    private AgePop agePop;
    @BindView(R.id.ll_nl)
    LinearLayout ll_nl;
    @BindView(R.id.tv_sz_age)
    TextView tv_sz_age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        ButterKnife.bind(this);
        title_text.setText("设置");
        selectorPop = new SelectorPop(SetupActivity.this,R.layout.selector_pop_item_view);
        agePop = new AgePop(SetupActivity.this,R.layout.nianling_pop_item_view);
        selectorPop.setOnDismissListener(onDismissListener);
        agePop.setOnDismissListener(onDismissListener);
        agePop.setOnAgeSelectorListener(ageSelectorListener);
        left_back.setOnClickListener(this);
        rl_bz.setOnClickListener(this);
        ll_txsc.setOnClickListener(this);
        rl_gywm.setOnClickListener(this);
        ll_nc.setOnClickListener(this);
        rl_zhyaq.setOnClickListener(this);
        rl_wtfk.setOnClickListener(this);
        ll_nl.setOnClickListener(this);
    }

    private AgePop.AgeSelectorListener ageSelectorListener=new AgePop.AgeSelectorListener() {
        @Override
        public void OnNanListener() {
            tv_sz_age.setText("男");


            agePop.dismiss();
        }

        @Override
        public void OnNVListener() {
            tv_sz_age.setText("女");
            agePop.dismiss();
        }

        @Override
        public void OnCancelListener() {
            agePop.dismiss();
        }
    };

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
            case R.id.ll_nl:
                setShowPop(agePop,ll_nl);

                break;
        }

    }


    private PopupWindow.OnDismissListener onDismissListener = new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            setWindowTranslucence(1.0f);
        }
    };
}
