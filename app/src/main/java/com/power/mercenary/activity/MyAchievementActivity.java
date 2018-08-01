package com.power.mercenary.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.power.mercenary.R;
import com.power.mercenary.adapter.WCCAdapter;
import com.power.mercenary.adapter.YWCAdapter;
import com.power.mercenary.base.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/28.
 */

public class MyAchievementActivity extends BaseActivity {

    @BindView(R.id.left_back)
    ImageView left_back;

    @BindView(R.id.title_text)
    TextView title_text;

    @BindView(R.id.mRecycler_ywc)
    RecyclerView mRecycler_ywc;
    private YWCAdapter ywcAdapter;
    ArrayList<String> mList=new ArrayList<>();

    @BindView(R.id.mRecycler_wwc)
    RecyclerView mRecycler_wwc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_achievement);

        ButterKnife.bind(this);
        title_text.setText("我的成就");

        if (mList.size()<=0){
            mList.add("");
            mList.add("");
            mList.add("");
            mList.add("");
            mList.add("");
        }
        mRecycler_ywc.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycler_ywc.setNestedScrollingEnabled(false);
        YWCAdapter changegameAdapter = new YWCAdapter(R.layout.ma_item_view, mList);
        mRecycler_ywc.setAdapter(changegameAdapter);

        mRecycler_wwc.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycler_wwc.setNestedScrollingEnabled(false);
        WCCAdapter wccAdapter = new WCCAdapter(R.layout.wcc_item_view, mList);
        mRecycler_wwc.setAdapter(wccAdapter);
        left_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
