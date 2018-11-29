package com.power.mercenary.activity.details_success_accept;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.widget.SpringView;
import com.lzy.okgo.model.Response;
import com.power.mercenary.MyApplication;
import com.power.mercenary.R;
import com.power.mercenary.activity.PersonalDataActivity;
import com.power.mercenary.activity.SignInActivity;
import com.power.mercenary.activity.WebActivity;
import com.power.mercenary.activity.chat.ChatActivity;
import com.power.mercenary.adapter.task.DetailsMsgAdapter;
import com.power.mercenary.adapter.task.DetailsPeopleAdapter;
import com.power.mercenary.base.BaseActivity;
import com.power.mercenary.bean.PayBean;
import com.power.mercenary.bean.task.ApplyListBean;
import com.power.mercenary.bean.task.MsgBean;
import com.power.mercenary.bean.task.MsgListBean;
import com.power.mercenary.bean.task.TaskDetailsBean;
import com.power.mercenary.data.EventConstants;
import com.power.mercenary.dialog.CallDialog;
import com.power.mercenary.dialog.ShareDialog;
import com.power.mercenary.event.EventUtils;
import com.power.mercenary.http.DialogCallback;
import com.power.mercenary.http.HttpManager;
import com.power.mercenary.http.ResponseBean;
import com.power.mercenary.presenter.TaskDetailsPresenter;
import com.power.mercenary.utils.MercenaryUtils;
import com.power.mercenary.utils.MyUtils;
import com.power.mercenary.utils.SpUtils;
import com.power.mercenary.utils.TUtils;
import com.power.mercenary.utils.Urls;
import com.power.mercenary.view.CircleImageView;
import com.power.mercenary.view.MaxHeightRecyclerView;
import com.power.mercenary.view.SharingPop;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/3/29.
 */

public class GZAcceptSuccessActivity extends BaseActivity implements View.OnClickListener, TaskDetailsPresenter.TaskDetailsCallBack, DetailsPeopleAdapter.DetailsPepCallBack {


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
    ArrayList<String> mList = new ArrayList<>();

    @BindView(R.id.recycler_liu_yan)
    MaxHeightRecyclerView recycler_liu_yan;

    @BindView(R.id.springView_rwsx)
    SpringView springView_rwsx;

    @BindView(R.id.iv_right_fx)
    ImageView iv_right_fx;
    @BindView(R.id.act_task_detaiils_icon)
    CircleImageView ivIcon;
    @BindView(R.id.act_task_detaiils_name)
    TextView tvName;
    @BindView(R.id.act_task_detaiils_time)
    TextView tvTime;
    @BindView(R.id.act_task_detaiils_price)
    TextView tvPrice;
    @BindView(R.id.act_task_detaiils_collectionBtn)
    LinearLayout collectionBtn;
    @BindView(R.id.act_task_detaiils_complainBtn)
    LinearLayout complainBtn;
    @BindView(R.id.act_task_detaiils_publishBtn)
    TextView publishBtn;
    @BindView(R.id.act_task_details_scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.act_gz_details_taskGoal)
    TextView tvTaskGoal;
    @BindView(R.id.act_gz_details_taskAsk)
    TextView tvTaskAsk;
    @BindView(R.id.act_gz_details_taskDetails)
    TextView tvTaskDetails;

    @BindView(R.id.act_task_details_sMsg)
    RelativeLayout actTaskDetailsSMsg;
    @BindView(R.id.act_task_detaiils_privateMsg)
    CircleImageView actTaskDetaiilsPrivateMsg;
    @BindView(R.id.act_task_detaiils_privateName)
    TextView actTaskDetaiilsPrivateName;
    @BindView(R.id.act_task_detaiils_privateBtn)
    TextView actTaskDetaiilsPrivateBtn;

    private SharingPop sharingPop;

    private int collectionState = 0;

    @BindView(R.id.left_back)
    ImageView left_back;

    private int page = 0;
    private int msgPage = 1;

    private ImageView ivBtnCollection;

    private String taskId;
    private TaskDetailsPresenter presenter;

    private DetailsPeopleAdapter peopleAdapter;

    private TextView tvTitle;

    private DetailsMsgAdapter msgAdapter;
    private List<MsgListBean> msgListBeanList;

    private EditText etMsg;

    private String taskState;
    private String publisherId;
    private TaskDetailsBean taskDetailsBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gz_task_details);
        ButterKnife.bind(this);

        tvTitle = (TextView) findViewById(R.id.act_task_detaiils_title);

        etMsg = (EditText) findViewById(R.id.act_task_detaiils_msg);

        ivBtnCollection = (ImageView) findViewById(R.id.act_task_detaiils_collectionBtn_icon);

        taskId = getIntent().getStringExtra("taskId");

        msgListBeanList = new ArrayList<>();
        msgAdapter = new DetailsMsgAdapter(this, msgListBeanList);

        presenter = new TaskDetailsPresenter(this, this);
        presenter.getTaskDetails(taskId);
        presenter.getApplyList(taskId, 0);
        presenter.getMsgList(taskId, msgPage);

        recycler_task_tag.setNestedScrollingEnabled(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycler_task_tag.setLayoutManager(linearLayoutManager);

        recycler_content.setLayoutManager(new LinearLayoutManager(mContext));
        recycler_content.setNestedScrollingEnabled(false);

        recycler_liu_yan.setLayoutManager(new LinearLayoutManager(mContext));
        recycler_liu_yan.setNestedScrollingEnabled(false);

        recycler_liu_yan.setAdapter(msgAdapter);

        sharingPop = new SharingPop(GZAcceptSuccessActivity.this, R.layout.sharing_pop_item_view);
        sharingPop.setOnDismissListener(onDismissListener);
        initRefresh();
        renwutjLl.setOnClickListener(this);
        tongchengLl.setOnClickListener(this);
        iv_right_fx.setOnClickListener(this);
        left_back.setOnClickListener(this);

        etMsg.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (KeyEvent.KEYCODE_ENTER == keyCode && KeyEvent.ACTION_DOWN == event.getAction()) {
                    presenter.publishMsg(taskId, etMsg.getText().toString(), "");
                    return true;
                }
                return false;
            }
        });

        publishBtn.setText("已完成");
        publishBtn.setOnClickListener(null);
        tuijianTabLl.setVisibility(View.GONE);
        recycler_content.setVisibility(View.GONE);
        actTaskDetailsSMsg.setVisibility(View.VISIBLE);

        actTaskDetaiilsPrivateBtn.setOnClickListener(this);

        ivIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonalDataActivity.invoke(GZAcceptSuccessActivity.this, taskDetailsBean.getPublisher_id());
            }
        });
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
    }

    //同城Tab
    private void initTongcheng() {
        renwutjTv.setTextColor(getResources().getColor(R.color.textColor));
        indicatorRenwutj.setBackgroundColor(getResources().getColor(R.color.concrete));
        tongchengTv.setTextColor(getResources().getColor(R.color.colorPrimary));
        indicatorTongcheng.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
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
            }

            @Override
            public void onLoadmore() {
//                Toast.makeText(mContext,"玩命加载中...",Toast.LENGTH_SHORT).show();
                msgPage++;

                if (msgListBeanList.size() < 10) {
                    msgListBeanList.clear();
                    msgPage = 1;
                }

                presenter.getMsgList(taskId, msgPage);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.renwutj_ll://任务推荐
                page = 0;
                presenter.getApplyList(taskId, page);
                initRenwutj();
                break;
            case R.id.tongcheng_ll://同城
                page = 1;
                presenter.getApplyList(taskId, page);
                initTongcheng();
                break;
            case R.id.iv_right_fx:
                ShareDialog dialog = new ShareDialog(this, taskDetailsBean.getTask_name(), taskDetailsBean.getTask_description(), taskDetailsBean.getId());
                dialog.setOnDismissListener(onDismissListener);;
                setShowPop(dialog, iv_right_fx);
                break;
            case R.id.left_back:
                finish();
                break;

            case R.id.act_task_detaiils_privateBtn:
                //私信
                if (taskDetailsBean != null && taskDetailsBean.getXuanding() != null) {
                    if (!TextUtils.equals(MyApplication.getUserId(), taskDetailsBean.getXuanding().getId())) {
//                    PersonalDataActivity.invoke(this, publisherId);
                        ChatActivity.invoke(this, taskDetailsBean.getXuanding().getId(), taskDetailsBean.getXuanding().getHead_img(), taskDetailsBean.getXuanding().getName());
                    }
                }
                break;
            case R.id.act_task_detaiils_publishBtn:
                if (!MyApplication.isLogin()) {
                    startActivity(new Intent(this, SignInActivity.class));
                    return;
                }

                new HttpManager<ResponseBean<Void>>("Home/MyTask/tijiao", this)
                        .addParams("token", MyApplication.getUserToken())
                        .addParams("id", taskId)
                        .postRequest(new DialogCallback<ResponseBean<Void>>(this) {
                            @Override
                            public void onSuccess(Response<ResponseBean<Void>> response) {
                                TUtils.showCustom(GZAcceptSuccessActivity.this, "提交成功");
                            }
                        });
                break;
        }
    }

    @Override
    public void getTaskDetails(TaskDetailsBean datas) {
        if (datas != null) {

            taskDetailsBean = datas;

            Glide.with(this)
                    .load(Urls.BASEIMGURL + datas.getHead_img())
                    .into(ivIcon);

            tvTitle.setText(datas.getTask_name());

//获取任务所在地
            String address = SpUtils.getString(this, "address", "");
            tvName.setText(datas.getNick_name()+"("+address+")");
            tvTime.setText(MyUtils.getDateToString(datas.getPublish_time()));

            tvPrice.setText("￥" + datas.getPay_amount());

            collectionState = datas.getCollect();

            tvTaskGoal.setText(datas.getTask_purpose());

            List<String> stringList = MercenaryUtils.stringToList(datas.getTask_request());

            StringBuffer taskRequest = new StringBuffer();

            for (int i = 0; i < stringList.size(); i++) {
                if (i != stringList.size() - 1) {
                    taskRequest.append(stringList.get(i) + "\n");
                } else {
                    taskRequest.append(stringList.get(i));
                }
            }

            tvTaskAsk.setText(taskRequest);

            tvTaskDetails.setText(datas.getTask_description());

            TagAdapter tagAdapter = new TagAdapter(R.layout.item_tag_layout, MercenaryUtils.stringToList(datas.getTask_tag()));
            recycler_task_tag.setAdapter(tagAdapter);

            switch (datas.getCollect()) {
                case 1:
                    ivBtnCollection.setImageDrawable(getResources().getDrawable(R.drawable.y_sc_2x));
                    break;
                case 0:
                    ivBtnCollection.setImageDrawable(getResources().getDrawable(R.drawable.w_sc_2x));
                    break;
            }

            taskState = datas.getTask_status();

            publisherId = datas.getPublisher_id();

            //            switch (taskState) {
//                case "2":
//                    publishBtn.setText("任务中");
//                    publishBtn.setOnClickListener(null);
//                    tuijianTabLl.setVisibility(View.GONE);
//                    recycler_content.setVisibility(View.GONE);
//                    actTaskDetailsSMsg.setVisibility(View.VISIBLE);
//                    if (datas.getXuanding() != null) {
//                        Glide.with(this)
//                                .load(datas.getXuanding().getHead_img())
//                                .into(actTaskDetaiilsPrivateMsg);
//
//                        actTaskDetaiilsPrivateName.setText(datas.getXuanding().getName());
//                    }
//                    break;
//                case "3":
//                    publishBtn.setText("审核中");
//                    publishBtn.setOnClickListener(null);
//                    tuijianTabLl.setVisibility(View.GONE);
//                    recycler_content.setVisibility(View.GONE);
//                    actTaskDetailsSMsg.setVisibility(View.VISIBLE);
//                    if (datas.getXuanding() != null) {
//                        Glide.with(this)
//                                .load(datas.getXuanding().getHead_img())
//                                .into(actTaskDetaiilsPrivateMsg);
//
//                        actTaskDetaiilsPrivateName.setText(datas.getXuanding().getName());
//                    }
//                    break;
//                case "6":
//                    publishBtn.setText("待评价");
//                    publishBtn.setOnClickListener(null);
//                    tuijianTabLl.setVisibility(View.GONE);
//                    recycler_content.setVisibility(View.GONE);
//                    actTaskDetailsSMsg.setVisibility(View.VISIBLE);
//                    if (datas.getXuanding() != null) {
//                        Glide.with(this)
//                                .load(datas.getXuanding().getHead_img())
//                                .into(actTaskDetaiilsPrivateMsg);
//
//                        actTaskDetaiilsPrivateName.setText(datas.getXuanding().getName());
//                    }
//                    break;
//            }

            if (datas.getXuanding() != null) {
                Glide.with(this)
                        .load(Urls.BASEIMGURL + datas.getXuanding().getHead_img())
                        .into(actTaskDetaiilsPrivateMsg);

                actTaskDetaiilsPrivateName.setText(datas.getXuanding().getName());
            }
        }
    }

    @Override
    public void publishMsg(MsgBean datas) {
        msgPage++;

        if (msgListBeanList.size() < 10) {
            msgListBeanList.clear();
            msgPage = 1;
        }

        presenter.getMsgList(taskId, msgPage);

        etMsg.setText("");
        TUtils.showCustom(this, "发表成功");
    }

    @Override

    public void getApplyList(List<ApplyListBean> datas) {
        if (datas != null) {
            peopleAdapter = new DetailsPeopleAdapter(this, datas, page);
            recycler_content.setAdapter(peopleAdapter);
            peopleAdapter.setDetailsPepCallBack(this);
        }
    }

    @Override
    public void getMsgList(List<MsgListBean> datas) {
        if (datas != null && datas.size() > 0) {
            msgListBeanList.addAll(datas);
        } else {
            msgPage--;
        }
        msgAdapter.notifyDataSetChanged();
//        if (isFirstLoad) {
//            scrollView.scrollTo(0, Integer.MAX_VALUE);
//            isFirstLoad = true;
//        }
        springView_rwsx.onFinishFreshAndLoad();
    }

    @Override
    public void applyRequest() {
        presenter.getApplyList(taskId, page);
        publishBtn.setText("已报名");
        publishBtn.setOnClickListener(null);
    }

    @Override
    public void changePeople(Response<ResponseBean<Void>> response, String avatar, String name) {
        if (response != null && response.body() != null) {
            if (response.body().code == 101) {
                presenter.toPay(taskId);
            } else {
//                publishBtn.setText("任务中");
//                publishBtn.setOnClickListener(null);
//                tuijianTabLl.setVisibility(View.GONE);
//                recycler_content.setVisibility(View.GONE);
//                actTaskDetailsSMsg.setVisibility(View.VISIBLE);
//                Glide.with(this)
//                        .load(Urls.BASEIMGURL + avatar)
//                        .into(actTaskDetaiilsPrivateMsg);
//
//                actTaskDetaiilsPrivateName.setText(name);

                presenter.getApplyList(taskId, page);
                TUtils.showCustom(this, "操作成功");
            }
        }
    }

    @Override
    public void changeCollection() {
        EventBus.getDefault().post(new EventUtils(EventConstants.TYPE_REFRESH_COLLECTION));
    }

    @Override
    public void getMsgListFail() {
        springView_rwsx.onFinishFreshAndLoad();
    }

    @Override
    public void toPayRequest(PayBean data) {
        WebActivity.invoke(this, data.getUrl(), getString(R.string.pay_title));
    }

    @OnClick({R.id.act_task_detaiils_collectionBtn, R.id.act_task_detaiils_complainBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.act_task_detaiils_collectionBtn:
                if (!MyApplication.isLogin()) {
                    startActivity(new Intent(this, SignInActivity.class));
                    return;
                }

                if (collectionState == 1) {
                    ivBtnCollection.setImageDrawable(getResources().getDrawable(R.drawable.w_sc_2x));
                    presenter.changeCollection(taskId, 2);
                    collectionState = 0;
                } else {
                    ivBtnCollection.setImageDrawable(getResources().getDrawable(R.drawable.y_sc_2x));
                    presenter.changeCollection(taskId, 1);
                    collectionState = 1;
                }

                break;
            case R.id.act_task_detaiils_complainBtn:
                CallDialog.showComplaintDialog(this);
                break;
        }
    }

    @Override
    public void selectedPeople(String id, int state, String avatar, String name, String userId) {
        if (state == 2) {
            //选定 弹个界面
//            if (TextUtils.equals(MyApplication.getUserId(), publisherId)) {
                presenter.changePeople(id, state, taskId, avatar, name);
//            } else {
//                TUtils.showCustom(this, "只有发布者可以更改");
//            }
        } else {
            presenter.changePeople(id, state, taskId, avatar, name);
        }
    }

    @Override
    public void queryInformation(String userId) {

    }

    /**
     * 任务推荐标签Adapter
     */
    public class TagAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public TagAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            TextView tagTv = helper.getView(R.id.item_content_tv);
            tagTv.setText(item);
        }
    }
}
