package com.power.mercenary.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.power.mercenary.R;

import java.util.List;

/**
 * Created by Administrator on 2018/3/26.
 */

public class MyBankCardAdapter extends BaseAdapter {

    private Context mcontext;
    private List<Integer> mlist;

    public MyBankCardAdapter(Context mcontext,List<Integer> mlist){
            this.mcontext=mcontext;
            this.mlist=mlist;
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHodler viewHodler = null;

        if(view==null){

            view=View.inflate(mcontext, R.layout.mybankcard_item_view,null);
            viewHodler = new ViewHodler();

            view.setTag(viewHodler);
        }else{

            viewHodler= (ViewHodler) view.getTag();

        }

        return view;
    }

    class ViewHodler{


    }

}
