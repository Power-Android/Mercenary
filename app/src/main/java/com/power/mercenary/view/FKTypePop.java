package com.power.mercenary.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.power.mercenary.R;



/**
 * Created by Administrator on 2018/3/2.
 */

public class FKTypePop extends PopupWindow implements View.OnClickListener
        {

    private int resId;
    private Context context;
    private LayoutInflater inflater;
    public View defaultView;


    public FKTypePop(Context context, int resId
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

    //获取当前picker是否wrap
//    private void getWrapState() {
//        if (picker.getWrapSelectorWheelAbsolutely()) {
//            button1.setText(R.string.switch_wrap_mode_true);
//        } else {
//            button1.setText(R.string.switch_wrap_mode_false);
//        }
//    }


    /**
     *
     * @return pop的View
     */
    public View getDefaultView() {
        return defaultView;
    }


    @Override
    public void onClick(View view) {

    }

}
