package com.power.mercenary.adapter;

import java.util.List;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.power.mercenary.R;

public class SortContentAdapter  extends BaseAdapter{



    @Override
    public int getCount() {
        return item.size();
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

        ViewHodler viewHodler=null;
        if(view==null){

            view = View.inflate(context, R.layout.sortcontent_item_view,null);
            viewHodler = new ViewHodler();
            viewHodler.tv_sort_title = view.findViewById(R.id.tv_sort_title);
            viewHodler.iv_duihao=view.findViewById(R.id.iv_duihao);

            view.setTag(viewHodler);

        }else{
            viewHodler= (ViewHodler) view.getTag();
        }

        viewHodler.tv_sort_title.setText(item.get(i));
		if (flag == i) {
            viewHodler.tv_sort_title.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            viewHodler.iv_duihao.setVisibility(View.VISIBLE);
		} else {
            viewHodler.tv_sort_title.setTextColor(context.getResources().getColor(R.color.black));
            viewHodler.iv_duihao.setVisibility(View.INVISIBLE);
		}


        return view;
    }

    class ViewHodler{

        TextView tv_sort_title;
        ImageView iv_duihao;

    }

	private List<String> item;
	private Context context;
	private int flag=-1;

	public SortContentAdapter(Context mcontext, List<String> list,
			LayoutInflater layoutInflater, int flag) {
		// TODO Auto-generated constructor stub
		this.item = list;
		this.context = mcontext;
		this.flag = flag;
	}
//
//	@Override
//	public View getView(int arg0, View arg1, ViewGroup arg2) {
//		ViewHolder holder = ViewHolder.get(context, arg1, arg2,
//				R.layout.sort_content_item, arg0);
//		TextView tv_sort_title = holder.getView(R.id.tv_sort_title);
//		ImageView iv_duihao = holder.getView(R.id.iv_duihao);
//		tv_sort_title.setText(item.get(arg0));
//		if (flag == arg0) {
//			iv_duihao.setVisibility(View.VISIBLE);
//		} else {
//			iv_duihao.setVisibility(View.INVISIBLE);
//		}
//		return holder.getConvertView();
//	}
//
	public void amendFlag(int flag) {
		this.flag = flag;
		this.notifyDataSetChanged();
	}

}
