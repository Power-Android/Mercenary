package com.power.mercenary.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.power.mercenary.MyApplication;
import com.power.mercenary.R;
import com.power.mercenary.activity.GRTaskDetailsActivity;
import com.power.mercenary.activity.GZTaskDetailsActivity;
import com.power.mercenary.activity.PTTaskDetailsActivity;
import com.power.mercenary.activity.SHTaskDetailsActivity;
import com.power.mercenary.activity.details_appraise_publish.GRPublishAppraiseActivity;
import com.power.mercenary.activity.details_appraise_publish.GZPublishAppraiseActivity;
import com.power.mercenary.activity.details_appraise_publish.PTPublishAppraiseActivity;
import com.power.mercenary.activity.details_appraise_publish.SHPublishAppraiseActivity;
import com.power.mercenary.activity.details_audit_publish.GRPublishAuditActivity;
import com.power.mercenary.activity.details_audit_publish.GZPublishAuditActivity;
import com.power.mercenary.activity.details_audit_publish.PTPublishAuditActivity;
import com.power.mercenary.activity.details_audit_publish.SHPublishAuditActivity;
import com.power.mercenary.activity.details_intask_publish.GRPublishInTaskActivity;
import com.power.mercenary.activity.details_intask_publish.GZPublishInTaskActivity;
import com.power.mercenary.activity.details_intask_publish.PTPublishInTaskActivity;
import com.power.mercenary.activity.details_intask_publish.SHPublishInTaskActivity;
import com.power.mercenary.activity.details_out_publish.GRPublishOutActivity;
import com.power.mercenary.activity.details_out_publish.GZPublishOutActivity;
import com.power.mercenary.activity.details_out_publish.PTPublishOutActivity;
import com.power.mercenary.activity.details_out_publish.SHPublishOutActivity;
import com.power.mercenary.adapter.ReleaseQBAdapter;
import com.power.mercenary.base.BaseFragment;
import com.power.mercenary.bean.mytask.PublishTaskBean;
import com.power.mercenary.presenter.publish.PublishPresenter;
import com.power.mercenary.view.BaseDialog;
import com.power.mercenary.view.RatingBarView;
import com.power.mercenary.view.pullrecyclerview.WanRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/30.
 */

public class ReleaseQBFragment extends BaseFragment implements PublishPresenter.PublishCallBack, WanRecyclerView.PullRecyclerViewCallBack, ReleaseQBAdapter.OnClickListener {


    List<PublishTaskBean> mList = new ArrayList<>();

    private PublishPresenter publishPresenter;

    private WanRecyclerView wanRecyclerView;

    private ReleaseQBAdapter adapter;

    private int page = 1;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = View.inflate(getActivity(), R.layout.fragment_release, null);

        wanRecyclerView = view.findViewById(R.id.frag_release_recyclerView);
        wanRecyclerView.setLinearLayout();
        wanRecyclerView.setPullRecyclerViewListener(this);

        adapter = new ReleaseQBAdapter(getContext(), mList);
        adapter.setOnClickListener(this);
        wanRecyclerView.setAdapter(adapter);

        publishPresenter = new PublishPresenter(getActivity(), this);

        return view;
    }

    @Override
    protected void initLazyData() {
        publishPresenter.getPublishTaskList(page);
    }

    @Override
    public void getPublishTaskList(List<PublishTaskBean> datas) {
        if (datas != null) {
            mList.addAll(datas);
            wanRecyclerView.setHasMore(datas.size(), 10);
        } else {
            wanRecyclerView.setHasMore(0, 10);
        }
        adapter.notifyDataSetChanged();
        wanRecyclerView.setStateView(mList.size());
    }

    @Override
    public void getPublishTaskListFail() {
        wanRecyclerView.setHasMore(0, 10);
    }

    @Override
    public void putTaskRequestSuccess(int position) {
        mList.remove(position);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void outTaskRequestSuccess(int position) {
        mList.remove(position);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void auditTaskRequestSuccess(int type, int position) {
        mList.remove(position);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void appraiseRequestSuccess() {

    }

    @Override
    public void onRefresh() {
        page = 1;
        mList.clear();
        publishPresenter.getPublishTaskList(page);
    }

    @Override
    public void onLoadMore() {
        page++;
        publishPresenter.getPublishTaskList(page);
    }

    @Override
    public void onClickXGListener(String id, int position) {
    }

    @Override
    public void onClickCXListener(String id, int position) {
        publishPresenter.outTaskRequest(id, position);
    }

    @Override
    public void onClickYQListener(String id, int position) {
    }

    @Override
    public void onClickPJListener(String id, int position) {
        showSubmitPJDialog(id);
    }

    @Override
    public void onClickSJListener(String id, int position) {
        publishPresenter.putTaskRequest(id, position);
    }

    @Override
    public void onClickSHListener(String id, int position) {
        showSubmitSHDialog(id, position);
    }

    @Override
    public void TaskOnClickViewListener(String id, int position, String taskType, String taskState) {
        //1未决定，2任务中，3审核中，4已下架，5未通过后台审核，6待评价，7已评价
//        if (TextUtils.equals(taskState, "1")) {
//
//        }

        if (TextUtils.equals(taskState, "2")) {
            switch (taskType) {
                case "1":
                    Intent ptIntent = new Intent(getActivity(), PTPublishInTaskActivity.class);
                    ptIntent.putExtra("taskId", id);
                    startActivity(ptIntent);
                    break;

                case "2":
                case "5":
                case "6":
                    Intent shIntent = new Intent(getActivity(), SHPublishInTaskActivity.class);
                    shIntent.putExtra("taskId", id);
                    startActivity(shIntent);
                    break;

                case "3":
                    Intent grIntent = new Intent(getActivity(), GRPublishInTaskActivity.class);
                    grIntent.putExtra("taskId", id);
                    startActivity(grIntent);
                    break;

                case "4":
                    Intent gzIntent = new Intent(getActivity(), GZPublishInTaskActivity.class);
                    gzIntent.putExtra("taskId", id);
                    startActivity(gzIntent);
                    break;
            }
        } else if (TextUtils.equals(taskState, "3")) {
            switch (taskType) {
                case "1":
                    Intent ptIntent = new Intent(getActivity(), PTPublishAuditActivity.class);
                    ptIntent.putExtra("taskId", id);
                    startActivity(ptIntent);
                    break;

                case "2":
                case "5":
                case "6":
                    Intent shIntent = new Intent(getActivity(), SHPublishAuditActivity.class);
                    shIntent.putExtra("taskId", id);
                    startActivity(shIntent);
                    break;

                case "3":
                    Intent grIntent = new Intent(getActivity(), GRPublishAuditActivity.class);
                    grIntent.putExtra("taskId", id);
                    startActivity(grIntent);
                    break;

                case "4":
                    Intent gzIntent = new Intent(getActivity(), GZPublishAuditActivity.class);
                    gzIntent.putExtra("taskId", id);
                    startActivity(gzIntent);
                    break;
            }
        } else if (TextUtils.equals(taskState, "6")) {
            switch (taskType) {
                case "1":
                    Intent ptIntent = new Intent(getActivity(), PTPublishAppraiseActivity.class);
                    ptIntent.putExtra("taskId", id);
                    startActivity(ptIntent);
                    break;

                case "2":
                case "5":
                case "6":
                    Intent shIntent = new Intent(getActivity(), SHPublishAppraiseActivity.class);
                    shIntent.putExtra("taskId", id);
                    startActivity(shIntent);
                    break;

                case "3":
                    Intent grIntent = new Intent(getActivity(), GRPublishAppraiseActivity.class);
                    grIntent.putExtra("taskId", id);
                    startActivity(grIntent);
                    break;

                case "4":
                    Intent gzIntent = new Intent(getActivity(), GZPublishAppraiseActivity.class);
                    gzIntent.putExtra("taskId", id);
                    startActivity(gzIntent);
                    break;
            }
        } else if (TextUtils.equals(taskState, "4")) {
            switch (taskType) {
                case "1":
                    Intent ptIntent = new Intent(getActivity(), PTPublishOutActivity.class);
                    ptIntent.putExtra("taskId", id);
                    startActivity(ptIntent);
                    break;

                case "2":
                case "5":
                case "6":
                    Intent shIntent = new Intent(getActivity(), SHPublishOutActivity.class);
                    shIntent.putExtra("taskId", id);
                    startActivity(shIntent);
                    break;

                case "3":
                    Intent grIntent = new Intent(getActivity(), GRPublishOutActivity.class);
                    grIntent.putExtra("taskId", id);
                    startActivity(grIntent);
                    break;

                case "4":
                    Intent gzIntent = new Intent(getActivity(), GZPublishOutActivity.class);
                    gzIntent.putExtra("taskId", id);
                    startActivity(gzIntent);
                    break;
            }
        } else {
            switch (taskType) {
                case "1":
                    Intent ptIntent = new Intent(getActivity(), PTTaskDetailsActivity.class);
                    ptIntent.putExtra("taskId", id);
                    startActivity(ptIntent);
                    break;

                case "2":
                case "5":
                case "6":
                    Intent shIntent = new Intent(getActivity(), SHTaskDetailsActivity.class);
                    shIntent.putExtra("taskId", id);
                    startActivity(shIntent);
                    break;

                case "3":
                    Intent grIntent = new Intent(getActivity(), GRTaskDetailsActivity.class);
                    grIntent.putExtra("taskId", id);
                    startActivity(grIntent);
                    break;

                case "4":
                    Intent gzIntent = new Intent(getActivity(), GZTaskDetailsActivity.class);
                    gzIntent.putExtra("taskId", id);
                    startActivity(gzIntent);
                    break;
            }
        }
    }

    private void showSubmitSHDialog(final String actionId, final int position) {
        BaseDialog.Builder builder = new BaseDialog.Builder(getActivity());
        final BaseDialog dialog = builder.setViewId(R.layout.dialog_publish_sh)
                .setPaddingdp(20, 0, 20, 0)
                .setGravity(Gravity.CENTER)
                .setAnimation(R.style.bottom_tab_style)
                .setWidthHeightpx(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                .isOnTouchCanceled(true)
                .builder();

        dialog.getView(R.id.dialog_publish_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getView(R.id.dialog_publish_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publishPresenter.auditTaskRequest(actionId, 1, position);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void showSubmitPJDialog(final String actionId) {
        BaseDialog.Builder builder = new BaseDialog.Builder(getActivity());
        final BaseDialog dialog = builder.setViewId(R.layout.dialog_appraise)
                .setPaddingdp(20, 0, 20, 0)
                .setGravity(Gravity.CENTER)
                .setAnimation(R.style.bottom_tab_style)
                .setWidthHeightpx(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                .isOnTouchCanceled(true)
                .builder();

        final RatingBarView ratingBarView = dialog.getView(R.id.dialog_appraise_ratingBarView);

        ratingBarView.setRatingCount(5);//设置RatingBarView总数
        ratingBarView.setSelectedCount(1);//设置RatingBarView选中数
        ratingBarView.setSelectedIconResId(R.drawable.start_check);//设置RatingBarView选中的图片id
        ratingBarView.setNormalIconResId(R.drawable.start_nocheck);//设置RatingBarView正常图片id
        ratingBarView.setClickable(true);//设置RatingBarView是否可点击
        ratingBarView.setChildPadding(1);//设置RatingBarView的子view的padding
        ratingBarView.setChildMargin(10);//设置RatingBarView的子view左右之间的margin
        ratingBarView.setChildDimension(30);//设置RatingBarView的子view的宽高尺寸

        dialog.getView(R.id.dialog_appraise_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publishPresenter.appraiseRequest(actionId, ratingBarView.getSelectedCount());
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
