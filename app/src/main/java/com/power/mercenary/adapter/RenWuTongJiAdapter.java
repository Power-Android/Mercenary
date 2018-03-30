package com.power.mercenary.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.power.mercenary.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/3/30.
 */

public class RenWuTongJiAdapter extends BaseQuickAdapter<String, BaseViewHolder> {



public RenWuTongJiAdapter(@LayoutRes int layoutResId, @Nullable ArrayList<String> data) {
        super(layoutResId, data);



        }

@Override
protected void convert(BaseViewHolder helper, String item) {


        RecyclerView mRecycler_fbd = helper.getView(R.id.mRecycler_fbd);
        ArrayList<String> mList=new ArrayList<>();
        if (mList.size()<=0){
                mList.add("");
                mList.add("");
                mList.add("");
                mList.add("");
                mList.add("");
                mList.add("");
        }
        mRecycler_fbd.setLayoutManager(new GridLayoutManager(mContext,mList.size()));
        mRecycler_fbd.setNestedScrollingEnabled(false);
        RenWuXinjiAdapter changegameAdapter = new RenWuXinjiAdapter(R.layout.rwtj_item_view, mList);
        mRecycler_fbd.setAdapter(changegameAdapter);



        }


        public class RenWuXinjiAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

                public RenWuXinjiAdapter(@LayoutRes int layoutResId, @Nullable ArrayList<String> data) {
                        super(layoutResId, data);



                }

                @Override
                protected void convert(BaseViewHolder helper, String item) {







                }






        }



}
