package com.power.mercenary.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TabHost;
import android.widget.TextView;

import com.power.mercenary.R;


/**
 * Created by Administrator on 2018/3/2.
 */

public class SelectorPop extends PopupWindow implements View.OnClickListener{

    private int resId;
    private Context context;
    private LayoutInflater inflater;
    public View defaultView;
    private SelectorListener selectorListener;


    public SelectorPop(Context context, int resId) {
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

        TextView cameraTv = defaultView.findViewById(R.id.camera_tv);
        TextView photoTv = defaultView.findViewById(R.id.photo_tv);
        TextView cancleTv = defaultView.findViewById(R.id.cancle_tv);

        cameraTv.setOnClickListener(this);
        photoTv.setOnClickListener(this);
        cancleTv.setOnClickListener(this);

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

    public  interface SelectorListener{

        void OnCarmeaListener();

        void OnPhotoListener();

        void OnCancelListener();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.camera_tv:
                selectorListener.OnCarmeaListener();
                break;
            case R.id.photo_tv:
                selectorListener.OnPhotoListener();
                break;
            case R.id.cancle_tv:
                selectorListener.OnCancelListener();
                break;
        }
    }

    public  void setOnSelectorListener(SelectorListener selectorListener){
        this.selectorListener = selectorListener;
    }

}
