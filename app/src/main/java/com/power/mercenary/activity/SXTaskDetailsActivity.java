package com.power.mercenary.activity;

import android.os.Handler;
import android.support.annotation.Nullable;
import android.os.Bundle;
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
import com.lzy.okgo.model.Response;
import com.power.mercenary.R;
import com.power.mercenary.base.BaseActivity;
import com.power.mercenary.bean.PayBean;
import com.power.mercenary.bean.task.ApplyListBean;
import com.power.mercenary.bean.task.MsgBean;
import com.power.mercenary.bean.task.MsgListBean;
import com.power.mercenary.bean.task.TaskDetailsBean;
import com.power.mercenary.http.ResponseBean;
import com.power.mercenary.presenter.TaskDetailsPresenter;
import com.power.mercenary.view.SharingPop;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 *
 * 任务详情
 *
 */

public class SXTaskDetailsActivity extends BaseActivity implements View.OnClickListener, TaskDetailsPresenter.TaskDetailsCallBack {

    @BindView(R.id.recycler_task_tag)
    RecyclerView recycler_task_tag;
    @BindView(R.id.recycler_content)
    RecyclerView recycler_content;
    @BindView(R.id.renwutj_tv)
    TextView renwutjTv;
    @BindView(R.id.indicator_renwutj)
    View indicatorRenwutj;
    @BindView(R.id.renwutj_ll)
    LinearLayout renwutjLl;
    @BindView(R.id.tongcheng_tv)
    TextView tongchengTv;
    @BindView(R.id.indicator_tongcheng)
    View indicatorTongcheng;
    @BindView(R.id.tongcheng_ll)
    LinearLayout tongchengLl;
    @BindView(R.id.tuijian_tab_ll)
    LinearLayout tuijianTabLl;
    ArrayList<String> mList=new ArrayList<>();
    @BindView(R.id.recycler_liu_yan)
    RecyclerView recycler_liu_yan;
    @BindView(R.id.springView_rwsx)
    SpringView springView_rwsx;
    @BindView(R.id.iv_right_fx)
    ImageView iv_right_fx;
    private SharingPop sharingPop;
    @BindView(R.id.left_back)
    ImageView left_back;
    private String taskId;
    private TaskDetailsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sxtask_details);
        ButterKnife.bind(this);

        taskId = getIntent().getStringExtra("taskId");

        presenter = new TaskDetailsPresenter(this, this);
        presenter.getTaskDetails(taskId);
        presenter.getApplyList(taskId, 0);
        presenter.getMsgList(taskId, 1);

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
        }
        recycler_content.setLayoutManager(new LinearLayoutManager(mContext));
        recycler_content.setNestedScrollingEnabled(false);
        initRenwutj();

        recycler_liu_yan.setLayoutManager(new LinearLayoutManager(mContext));
        recycler_liu_yan.setNestedScrollingEnabled(false);
//        MessageBoardAdapter changegameAdapter = new MessageBoardAdapter(R.layout.message_board_iten_view, mList);
//        recycler_liu_yan.setAdapter(changegameAdapter);
        sharingPop = new SharingPop(SXTaskDetailsActivity.this,R.layout.sharing_pop_item_view);
        sharingPop.setOnDismissListener(onDismissListener);
        initRefresh();
        renwutjLl.setOnClickListener(this);
        tongchengLl.setOnClickListener(this);
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

    //任务推荐Tab
    private void initRenwutj() {
        renwutjTv.setTextColor(getResources().getColor(R.color.colorPrimary));
        indicatorRenwutj.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        tongchengTv.setTextColor(getResources().getColor(R.color.textColor));
        indicatorTongcheng.setBackgroundColor(getResources().getColor(R.color.concrete));
        initRenwutjData();
    }

    //同城Tab
    private void initTongcheng() {
        renwutjTv.setTextColor(getResources().getColor(R.color.textColor));
        indicatorRenwutj.setBackgroundColor(getResources().getColor(R.color.concrete));
        tongchengTv.setTextColor(getResources().getColor(R.color.colorPrimary));
        indicatorTongcheng.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        initTongchengData();
    }

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

    private void initRenwutjData() {


//        YBMRAdapter changegameAdapter = new YBMRAdapter(R.layout.ybmr_item_view, mList);
//        recycler_content.setAdapter(changegameAdapter);

    }

    private void initTongchengData() {
//        DXRAdapter changegameAdapter = new DXRAdapter(R.layout.dxr_item_view, mList);
//        recycler_content.setAdapter(changegameAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.renwutj_ll://任务推荐
                initRenwutj();
                break;
            case R.id.tongcheng_ll://同城
                initTongcheng();
                break;
            case R.id.iv_right_fx:

                setShowPop(sharingPop,iv_right_fx);

                break;
            case R.id.left_back:
                finish();
                break;
        }
    }

    @Override
    public void getTaskDetails(TaskDetailsBean datas) {

    }

    @Override
    public void publishMsg(MsgBean datas) {

    }

    @Override
    public void getApplyList(List<ApplyListBean> datas) {

    }

    @Override
    public void getMsgList(List<MsgListBean> datas) {

    }

    @Override
    public void applyRequest() {

    }

    @Override
    public void changePeople(Response<ResponseBean<Void>> response, String avatar, String name) {

    }

    @Override
    public void changeCollection() {

    }

    @Override
    public void getMsgListFail() {

    }

    @Override
    public void toPayRequest(PayBean data) {

    }

    @Override
    public void AddJiedan() {

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
