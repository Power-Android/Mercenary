package com.power.mercenary.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.power.mercenary.base.BaseFragment;

import java.util.List;

/**
 * Created by Administrator on 2018/3/27.
 */

public class MyAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> mlist;

    public MyAdapter(FragmentManager fm,List<BaseFragment> mlist) {
        super(fm);
        this.mlist=mlist;
    }

    @Override
    public Fragment getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public int getCount() {
        return mlist.size();
    }
}
