package com.power.mercenary.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.power.mercenary.R;
import com.power.mercenary.fragment.HomeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/30.
 */

public class ReleaseDPJAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public ReleaseDPJAdapter(@LayoutRes int layoutResId, @Nullable ArrayList<String> data) {
        super(layoutResId, data);



    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        RecyclerView tagRecyclerView = helper.getView(R.id.rv_tag);
        tagRecyclerView.setNestedScrollingEnabled(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        tagRecyclerView.setLayoutManager(linearLayoutManager);
        List<String> tagList = new ArrayList<>();
        tagList.add("");
        tagList.add("");
        tagList.add("");
        TagAdapter tagAdapter = new TagAdapter(R.layout.item_tag_layout, tagList);
        tagRecyclerView.setAdapter(tagAdapter);
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
        }
    }



}
