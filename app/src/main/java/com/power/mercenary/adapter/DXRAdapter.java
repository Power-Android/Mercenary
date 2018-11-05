package com.power.mercenary.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.power.mercenary.bean.task.ApplyListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/28.
 */

public class DXRAdapter extends BaseQuickAdapter<ApplyListBean, BaseViewHolder> {

    public DXRAdapter(@LayoutRes int layoutResId, @Nullable List<ApplyListBean> data) {
        super(layoutResId, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, ApplyListBean item) {

    }
}