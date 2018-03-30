package com.power.mercenary.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.power.mercenary.R;
import com.power.mercenary.base.BaseActivity;
import com.power.mercenary.view.SelectorPop;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/28.
 */

public class CertificateUploadingActivity extends BaseActivity {

    @BindView(R.id.left_back)
    ImageView left_back;

    @BindView(R.id.title_text)
    TextView title_text;

    @BindView(R.id.tv_zs_sc)
    TextView tv_zs_sc;

    private SelectorPop selectorPop;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificate_uploading);
        ButterKnife.bind(this);

        title_text.setText("证书上传");

        selectorPop = new SelectorPop(CertificateUploadingActivity.this,R.layout.selector_pop_item_view);
        selectorPop.setOnDismissListener(onDismissListener);

        left_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tv_zs_sc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setShowPop(selectorPop,tv_zs_sc);

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
