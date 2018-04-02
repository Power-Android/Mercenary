package com.power.mercenary.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.widget.SpringView;
import com.power.mercenary.R;
import com.power.mercenary.adapter.DXRAdapter;
import com.power.mercenary.adapter.MessageBoardAdapter;
import com.power.mercenary.adapter.TaskImageAdapter;
import com.power.mercenary.adapter.YBMRAdapter;
import com.power.mercenary.base.BaseActivity;
import com.power.mercenary.view.SharingPop;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/29.
 */

public class JKTaskZhonDetailsActivity extends BaseActivity implements View.OnClickListener{


    @BindView(R.id.recycler_task_tag)
    RecyclerView recycler_task_tag;


    ArrayList<String> mList=new ArrayList<>();
    ArrayList<String> list_imgs=new ArrayList<>();
    @BindView(R.id.recycler_liu_yan)
    RecyclerView recycler_liu_yan;

    @BindView(R.id.springView_rwsx)
    SpringView springView_rwsx;

    @BindView(R.id.iv_right_fx)
    ImageView iv_right_fx;
    private SharingPop sharingPop;

    @BindView(R.id.left_back)
    ImageView left_back;


    @BindView(R.id.recycler_img)
    RecyclerView recycler_img;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jk_task_zhon_details);
        ButterKnife.bind(this);

        recycler_task_tag.setNestedScrollingEnabled(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycler_task_tag.setLayoutManager(linearLayoutManager);
        List<String> tagList = new ArrayList<>();
        tagList.add("");
        tagList.add("");
        tagList.add("");
        TagAdapter tagAdapter = new TagAdapter(R.layout.item_tag_layout, tagList);
        recycler_task_tag.setAdapter(tagAdapter);

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

        for(int i=0;i<5;i++){
            list_imgs.add("");
        }

        recycler_img.setLayoutManager(new GridLayoutManager(mContext,3));
        recycler_img.setNestedScrollingEnabled(false);
        TaskImageAdapter imageAdapter = new TaskImageAdapter(R.layout.tp_item_view, list_imgs);
        recycler_img.setAdapter(imageAdapter);

        recycler_liu_yan.setLayoutManager(new LinearLayoutManager(mContext));
        recycler_liu_yan.setNestedScrollingEnabled(false);
        MessageBoardAdapter changegameAdapter = new MessageBoardAdapter(R.layout.message_board_iten_view, mList);
        recycler_liu_yan.setAdapter(changegameAdapter);
        sharingPop = new SharingPop(JKTaskZhonDetailsActivity.this,R.layout.sharing_pop_item_view);
        sharingPop.setOnDismissListener(onDismissListener);
        initRefresh();
        iv_right_fx.setOnClickListener(this);
        left_back.setOnClickListener(this);
    }

    private PopupWindow.OnDismissListener onDismissListener = new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            setWindowTranslucence(1.0f);
        }
    };


    //下拉刷新
    private void initRefresh() {
        //DefaultHeader/Footer是SpringView已经实现的默认头/尾之一
        //更多还有MeituanHeader、AliHeader、RotationHeader等如上图7种
        springView_rwsx.setType(SpringView.Type.FOLLOW);
//        springView_rwsx.setHeader(new DefaultHeader(mContext));
        springView_rwsx.setFooter(new DefaultFooter(mContext));
        springView_rwsx.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
//                Toast.makeText(mContext,"下拉刷新中",Toast.LENGTH_SHORT).show();
                // list.clear();
                // 网络请求;
                // mStarFragmentPresenter.queryData();
                //一分钟之后关闭刷新的方法
                finishFreshAndLoad();
            }

            @Override
            public void onLoadmore() {
//                Toast.makeText(mContext,"玩命加载中...",Toast.LENGTH_SHORT).show();
                finishFreshAndLoad();
            }
        });
    }

    /**
     * 关闭加载提示
     */
    private void finishFreshAndLoad() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                springView_rwsx.onFinishFreshAndLoad();
            }
        }, 2000);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_right_fx:

                setShowPop(sharingPop,iv_right_fx);

                break;
            case R.id.left_back:
                finish();
                break;
        }
    }

    /**
     * 任务推荐标签Adapter
     */
    public class TagAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

        public TagAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            TextView tagTv = helper.getView(R.id.item_content_tv);
        }
    }
}
