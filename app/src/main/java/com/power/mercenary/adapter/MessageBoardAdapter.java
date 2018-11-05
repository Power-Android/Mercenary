package com.power.mercenary.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.power.mercenary.bean.task.MsgListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/29.
 */

public class MessageBoardAdapter extends BaseQuickAdapter<MsgListBean, BaseViewHolder> {

    public MessageBoardAdapter(@LayoutRes int layoutResId, @Nullable List<MsgListBean> data) {
        super(layoutResId, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, MsgListBean item) {

    }
}
