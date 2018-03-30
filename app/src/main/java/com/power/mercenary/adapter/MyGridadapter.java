package com.power.mercenary.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.power.mercenary.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/28.
 */

public class MyGridadapter extends BaseAdapter {

    private Context mcontext;
    private List<Integer> mlist;

    private List<Boolean> zt;
    public MyGridadapter(Context mcontext,List<Integer> mlist){

        this.mcontext=mcontext;
        this.mlist=mlist;
        zt = new ArrayList<>();
        for(int i=0;i<mlist.size();i++){


            if(i==0){
                zt.add(true);
            }else{
                zt.add(false);
            }

        }

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

        view=View.inflate(mcontext, R.layout.xinji_item_view,null);

        TextView tv_xin = view.findViewById(R.id.tv_xin);
        TextView tv_xin_num = view.findViewById(R.id.tv_xin_num);
        View view_xian=view.findViewById(R.id.view_xian);

        if(zt.get(i)){

            tv_xin.setTextColor(mcontext.getResources().getColor(R.color.colorPrimary));
            tv_xin_num.setTextColor(mcontext.getResources().getColor(R.color.colorPrimary));

            view_xian.setVisibility(View.VISIBLE);

        }else{
            tv_xin.setTextColor(mcontext.getResources().getColor(R.color.black));
            tv_xin_num.setTextColor(mcontext.getResources().getColor(R.color.black));

            view_xian.setVisibility(View.INVISIBLE);
        }



        return view;
    }

    public  void setUpdata(int pos){

        zt.clear();
        for(int i=0;i<mlist.size();i++){


            if(i==pos){
                zt.add(true);
            }else{
                zt.add(false);
            }

        }


        notifyDataSetChanged();


    }

}
