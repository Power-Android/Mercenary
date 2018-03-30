package com.power.mercenary.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ProgressBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.power.mercenary.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/3/29.
 */

public class WCCAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public WCCAdapter(@LayoutRes int layoutResId, @Nullable ArrayList<String> data) {
        super(layoutResId, data);



    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {


        ProgressBar pb_jd = helper.getView(R.id.pb_jd);

        pb_jd.setProgress(40);

    }
}