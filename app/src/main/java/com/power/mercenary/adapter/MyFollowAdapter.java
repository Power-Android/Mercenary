package com.power.mercenary.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.power.mercenary.CollectionPeopleBean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/26.
 */

public class MyFollowAdapter extends BaseQuickAdapter<CollectionPeopleBean, BaseViewHolder> {

    public MyFollowAdapter(@LayoutRes int layoutResId, @Nullable List<CollectionPeopleBean> data) {
        super(layoutResId, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, CollectionPeopleBean item) {
    }
}
