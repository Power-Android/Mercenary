package com.power.mercenary.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.power.mercenary.R;
import com.power.mercenary.adapter.SortContentAdapter;

import java.util.List;


/**
 * Created by Administrator on 2018/3/2.
 */

public class AllPop extends PopupWindow {

    private int resId;
    private Context context;
    private LayoutInflater inflater;
    public View defaultView;
    private SortContentAdapter sortContentAdapter;
    private List<String> sortitem;
    private Button btn_px;
    private InstalledCapacityListener installedCapacityListener;
    public AllPop(Context context, int resId,
                List<String> sortitem,
                Button btn_px) {
        super(context);
        this.context = context;
        this.resId = resId;
        this.sortitem = sortitem;
        this.btn_px = btn_px;
        initPopupWindow();
    }

    public void initPopupWindow() {
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        defaultView = inflater.inflate(this.resId, null);
        defaultView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        setContentView(defaultView);
        ListView lv_sort_pop = (ListView) defaultView
                .findViewById(R.id.lv_sort_pop);
        sortContentAdapter = new SortContentAdapter(context, sortitem,
                LayoutInflater.from(context), -1);
        lv_sort_pop.setAdapter(sortContentAdapter);
        // lv_sort_pop.setSelection(0);
        lv_sort_pop.setOnItemClickListener(sortlistener);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // setAnimationStyle(R.style.classify_pw);
        setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(android.R.color.transparent)));
        setFocusable(true);
        // setOutsideTouchable(true);
        update();

    }

    /**
     *
     * @return pop的View
     */
    public View getDefaultView() {
        return defaultView;
    }

    private AdapterView.OnItemClickListener sortlistener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // TODO Auto-generated method stub
            sortContentAdapter.amendFlag(position);
            dismiss();
			btn_px.setText(sortitem.get(position));
            installedCapacityListener.onInstalledCapacityListener(position);
        }
    };

    public interface InstalledCapacityListener{
        void onInstalledCapacityListener(int pos);
    }

    public void setonInstalledCapacityListener(InstalledCapacityListener installedCapacityListener){
        this.installedCapacityListener=installedCapacityListener;
    }

}
