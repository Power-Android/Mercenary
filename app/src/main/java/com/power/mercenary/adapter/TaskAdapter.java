package com.power.mercenary.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.power.mercenary.R;
import com.power.mercenary.bean.CollectionBean;
import com.power.mercenary.utils.MercenaryUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/27.
 */

public class TaskAdapter extends BaseQuickAdapter<CollectionBean, BaseViewHolder> {


    private String[] all;

    public TaskAdapter(@LayoutRes int layoutResId, @Nullable List<CollectionBean> data) {
        super(layoutResId, data);



        }

@Override
protected void convert(BaseViewHolder helper, CollectionBean item) {
        helper.setText(R.id.tv_collction_name,item.getTask_name());
        helper.setText(R.id.tv_collction_price,"¥"+item.getPay_amount());
        helper.setText(R.id.tv_collction_table,item.getTask_description());
    List<String> tagList = MercenaryUtils.stringToList(item.getTask_tag());


    RecyclerView mRecycler_item = helper.getView(R.id.mRecycler_item);
        mRecycler_item.setNestedScrollingEnabled(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecycler_item.setLayoutManager(linearLayoutManager);

        TagAdapter tagAdapter = new TagAdapter(R.layout.item_tag_layout, tagList);
       mRecycler_item.setAdapter(tagAdapter);

        }


        /**
         * 任务推荐标签Adapter
         */
        public class TagAdapter extends BaseQuickAdapter<String,BaseViewHolder>{

                public TagAdapter(int layoutResId, @Nullable List<String> data) {
                        super(layoutResId, data);
                }

                @Override
                protected void convert(BaseViewHolder helper, String item) {
                        TextView tagTv = helper.getView(R.id.item_content_tv);
                        tagTv.setText(item);
                }
        }


        }