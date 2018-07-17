package com.power.mercenary.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.power.mercenary.R;
import com.power.mercenary.view.SquareImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/2.
 */

public class TaskImageAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private Context context;

    public TaskImageAdapter(@LayoutRes int layoutResId, @Nullable List<String> data, Context context) {
        super(layoutResId, data);

        this.context = context;

    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        SquareImageView imageView = helper.getView(R.id.tp_item_view_iv);

        Glide.with(context)
                .load(item)
                .into(imageView);
    }
}
