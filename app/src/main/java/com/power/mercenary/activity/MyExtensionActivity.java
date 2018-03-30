package com.power.mercenary.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.power.mercenary.R;
import com.power.mercenary.base.BaseActivity;
import com.power.mercenary.view.SharingPop;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/28.
 */

public class MyExtensionActivity extends BaseActivity {


@BindView(R.id.my_tg_fx)
    TextView my_tg_fx;

    @BindView(R.id.left_back)
    ImageView left_back;

    private SharingPop sharingPop;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_extension);
        ButterKnife.bind(this);

        sharingPop = new SharingPop(MyExtensionActivity.this,R.layout.sharing_pop_item_view);
        sharingPop.setOnDismissListener(onDismissListener);
        left_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        my_tg_fx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setShowPop(sharingPop,my_tg_fx);

            }
        });

    }


    private PopupWindow.OnDismissListener onDismissListener = new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            setWindowTranslucence(1.0f);
        }
    };

}
