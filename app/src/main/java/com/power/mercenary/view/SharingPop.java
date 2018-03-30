package com.power.mercenary.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

import com.power.mercenary.R;


/**
 * Created by Administrator on 2018/3/2.
 */

public class SharingPop extends PopupWindow {

    private int resId;
    private Context context;
    private LayoutInflater inflater;
    public View defaultView;

    public SharingPop(Context context, int resId
    ) {
        super(context);
        this.context = context;
        this.resId = resId;
        initPopupWindow();
    }

    public void initPopupWindow() {
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        defaultView = inflater.inflate(this.resId, null);
        defaultView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        setContentView(defaultView);

        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(FrameLayout.LayoutParams.WRAP_CONTENT);
         setAnimationStyle(R.style.popwin_anim_style);
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


}
