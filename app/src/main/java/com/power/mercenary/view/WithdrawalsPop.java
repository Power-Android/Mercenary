package com.power.mercenary.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.power.mercenary.R;

/**
 * Created by Administrator on 2018/3/27.
 */

public class WithdrawalsPop extends PopupWindow {


    private int resId;
    private Context context;
    private LayoutInflater inflater;
    public View defaultView;

    private TextView tv_qx;
    private RelativeLayout rl_zfb,rl_wx,rl_yl;

    private WithdrawalsClickListener withdrawalsClickListener;
    public WithdrawalsPop(Context context, int resId
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
        tv_qx=defaultView.findViewById(R.id.tv_qx);
        rl_zfb=defaultView.findViewById(R.id.rl_zfb);
        rl_wx=defaultView.findViewById(R.id.rl_wx);
        rl_yl=defaultView.findViewById(R.id.rl_yl);
        tv_qx.setOnClickListener(onClickListener);
        rl_zfb.setOnClickListener(onClickListener);
        rl_wx.setOnClickListener(onClickListener);
        rl_yl.setOnClickListener(onClickListener);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(FrameLayout.LayoutParams.WRAP_CONTENT);
        setAnimationStyle(R.style.popwin_anim_style);
        setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(android.R.color.transparent)));
        setFocusable(true);
        // setOutsideTouchable(true);
        update();

    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        switch (view.getId()){
            case R.id.tv_qx:

                withdrawalsClickListener.OnCancelListener();

                break;
            case R.id.rl_zfb:

                withdrawalsClickListener.OnSelectorListener(0);

                break;
            case R.id.rl_wx:

                withdrawalsClickListener.OnSelectorListener(1);

                break;
            case R.id.rl_yl:

                withdrawalsClickListener.OnSelectorListener(2);

                break;

        }

        }
    };


    public  interface  WithdrawalsClickListener{

        void OnCancelListener();

        void OnSelectorListener(int type);

    }


    public void setOnWithdrawalsClickListener(WithdrawalsClickListener withdrawalsClickListener){
        this.withdrawalsClickListener=withdrawalsClickListener;
    }


    /**
     *
     * @return pop的View
     */
    public View getDefaultView() {
        return defaultView;
    }


}
