package com.power.mercenary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.power.mercenary.R;
import com.power.mercenary.adapter.MyBankCardAdapter;
import com.power.mercenary.base.BaseActivity;
import com.power.mercenary.view.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/26.
 */

public class MyBankCardActivity extends BaseActivity {

    @BindView(R.id.left_back)
    ImageView left_back;

    @BindView(R.id.title_text)
    TextView title_text;
    @BindView(R.id.mlv_yhk)
    MyListView mlv_yhk;

    private List<Integer> mlist;

    private MyBankCardAdapter adapter;

    @BindView(R.id.ll_add_crad)
    LinearLayout ll_add_crad;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mybankcard);
        ButterKnife.bind(this);
        title_text.setText("我的银行卡");
        mlist = new ArrayList<>();
        for(int i=0;i<2;i++){

            mlist.add(i);

        }

        adapter = new MyBankCardAdapter(MyBankCardActivity.this,mlist);

        mlv_yhk.setAdapter(adapter);
        left_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ll_add_crad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MyBankCardActivity.this,AddCardActivity.class);
                startActivity(intent);

            }
        });
    }
}
