package com.power.mercenary.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.power.mercenary.R;


/**
 * Created by Administrator on 2018/3/2.
 */

public class AgePop extends PopupWindow implements View.OnClickListener{

    private int resId;
    private Context context;
    private LayoutInflater inflater;
    public View defaultView;
    private AgeSelectorListener ageSelectorListener;

    private TextView tv_nan,tv_nv,tv_quxiao;

    public AgePop(Context context, int resId
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
        tv_nan=defaultView.findViewById(R.id.tv_nan);
        tv_nv=defaultView.findViewById(R.id.tv_nv);
        tv_quxiao=defaultView.findViewById(R.id.tv_quxiao);
        tv_nan.setOnClickListener(this);
        tv_nv.setOnClickListener(this);
        tv_quxiao.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_nan:
                ageSelectorListener.OnNanListener();
                break;
            case R.id.tv_nv:
                ageSelectorListener.OnNVListener();
                break;
            case R.id.tv_quxiao:
                ageSelectorListener.OnCancelListener();
                break;
        }
    }


    public  interface AgeSelectorListener{

        void OnNanListener();

        void OnNVListener();

        void OnCancelListener();

    }

    public  void setOnAgeSelectorListener(AgeSelectorListener ageSelectorListener){
        this.ageSelectorListener=ageSelectorListener;
    }

}
