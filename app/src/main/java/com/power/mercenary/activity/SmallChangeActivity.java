package com.power.mercenary.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.power.mercenary.R;
import com.power.mercenary.adapter.MyFollowAdapter;
import com.power.mercenary.adapter.SmallChangeAdapter;
import com.power.mercenary.base.BaseActivity;
import com.power.mercenary.view.AllPop;
import com.power.mercenary.view.DistancePop;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/26.
 */

public class SmallChangeActivity extends BaseActivity implements View.OnClickListener,AllPop.InstalledCapacityListener ,DistancePop.DistanceSelectorListener{

    @BindView(R.id.left_back)
    ImageView left_back;

    @BindView(R.id.title_text)
    TextView title_text;
    @BindView(R.id.mRecycler_sc)
    RecyclerView mRecycler_sc;

    ArrayList<String> mList=new ArrayList<>();
    private List<String> sortitem,distance;
    private AllPop allPop;
    private DistancePop distancePop;

    @BindView(R.id.btn_ps_dq)
    Button btn_ps_dq;

    @BindView(R.id.btn_ps_rl)
    Button btn_ps_rl;

    @BindView(R.id.btn_ps_state)
    Button btn_ps_state;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_small_change);

        ButterKnife.bind(this);
        title_text.setText("零钱明细");
        if (mList.size()<=0){
            mList.add("");
            mList.add("");
            mList.add("");
            mList.add("");
            mList.add("");
            mList.add("");
            mList.add("");
            mList.add("");
            mList.add("");
            mList.add("");
            mList.add("");
        }

        sortitem = new ArrayList<>();
        distance = new ArrayList<>();
        sortitem.add("全部");
        sortitem.add("收入");
        sortitem.add("支出");
        distance.add("时间由近及远");
        distance.add("时间由远及近");

        mRecycler_sc.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycler_sc.setNestedScrollingEnabled(false);
        SmallChangeAdapter changegameAdapter = new SmallChangeAdapter(R.layout.smallchange_item_view, mList);
        mRecycler_sc.setAdapter(changegameAdapter);
        allPop = new AllPop(SmallChangeActivity.this,R.layout.all_item_view,sortitem,btn_ps_dq);
        distancePop = new DistancePop(SmallChangeActivity.this,R.layout.all_item_view,distance,btn_ps_rl);
        btn_ps_dq.setOnClickListener(this);
        btn_ps_rl.setOnClickListener(this);
        btn_ps_state.setOnClickListener(this);
        allPop.setOnDismissListener(onDismissListener);
        allPop.setonInstalledCapacityListener(this);
        distancePop.setOnDismissListener(onDismissListener);
        distancePop.setonDistanceSelectorListener(this);
        left_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private PopupWindow.OnDismissListener onDismissListener = new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {
            setWindowTranslucence(1.0f);
//获得Drawable对象
            Drawable drawable1 = ContextCompat.getDrawable(SmallChangeActivity.this, R.drawable.huixia_2x);
            drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());
            btn_ps_rl.setCompoundDrawables(null,null, drawable1, null);
            btn_ps_rl.setTextColor(getResources().getColor(R.color.black));
            btn_ps_state.setTextColor(getResources().getColor(R.color.black));
            btn_ps_dq.setCompoundDrawables(null, null, drawable1, null);
            btn_ps_dq.setTextColor(getResources().getColor(R.color.black));
        }
    };

    @Override
    public void onClick(View view) {
        Drawable drawable = ContextCompat.getDrawable(SmallChangeActivity.this, R.drawable.shang_2x);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        switch (view.getId()){
            case R.id.btn_ps_dq:

                //获得Drawable对象
                btn_ps_dq.setCompoundDrawables(null,null,drawable, null);
                btn_ps_dq.setTextColor(getResources().getColor(R.color.colorPrimary));
                btn_ps_state.setTextColor(getResources().getColor(R.color.black));
                if(allPop!=null){
                    if(allPop.isShowing()){
                        allPop.dismiss();
                    }else{
//                        setShowPop(allPop,btn_ps_dq);
                        allPop.showAsDropDown(view);
                    }
                }


                break;
            case R.id.btn_ps_rl:

                //获得Drawable对象
                btn_ps_rl.setCompoundDrawables(null,null,drawable, null);
                btn_ps_rl.setTextColor(getResources().getColor(R.color.colorPrimary));
                btn_ps_state.setTextColor(getResources().getColor(R.color.black));
                if(distancePop!=null){
                    if(distancePop.isShowing()){
                        distancePop.dismiss();
                    }else{
//                        setShowPop(allPop,btn_ps_dq);
                        distancePop.showAsDropDown(view);
                    }
                }

                break;
            case R.id.btn_ps_state:

                btn_ps_state.setTextColor(getResources().getColor(R.color.colorPrimary));

                break;
        }
    }

    public void setShowPop(PopupWindow popupWindow, View view){
        if(popupWindow!=null&&popupWindow.isShowing()){
            popupWindow.dismiss();
        }else{
            setWindowTranslucence(0.3);
            popupWindow.showAsDropDown(view);
        }
    }
    //设置Window窗口的透明度
    public void setWindowTranslucence(double d){

        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.alpha=(float) d;
        window.setAttributes(attributes);

    }

    @Override
    public void onInstalledCapacityListener(int pos) {



    }

    @Override
    public void onDistanceSelectorListener(int pos) {

    }
}
