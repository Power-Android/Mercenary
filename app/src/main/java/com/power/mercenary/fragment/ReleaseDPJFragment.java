package com.power.mercenary.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.power.mercenary.R;
import com.power.mercenary.activity.details_appraise_publish.GRPublishAppraiseActivity;
import com.power.mercenary.activity.details_appraise_publish.GZPublishAppraiseActivity;
import com.power.mercenary.activity.details_appraise_publish.PTPublishAppraiseActivity;
import com.power.mercenary.activity.details_appraise_publish.SHPublishAppraiseActivity;
import com.power.mercenary.activity.details_out_publish.GRPublishOutActivity;
import com.power.mercenary.activity.details_out_publish.GZPublishOutActivity;
import com.power.mercenary.activity.details_out_publish.PTPublishOutActivity;
import com.power.mercenary.activity.details_out_publish.SHPublishOutActivity;
import com.power.mercenary.adapter.ReleaseYXJAdapter;
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

public class ReleaseDPJFragment extends BaseFragment implements WanRecyclerView.PullRecyclerViewCallBack, PublishPresenter.PublishCallBack, ReleaseYXJAdapter.TaskBtnListener {

    List<PublishTaskBean> mList=new ArrayList<>();

    private PublishPresenter publishPresenter;

    private WanRecyclerView wanRecyclerView;

    private ReleaseYXJAdapter adapter;

    private int page = 1;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = View.inflate(getActivity(), R.layout.fragment_release,null);

        wanRecyclerView = view.findViewById(R.id.frag_release_recyclerView);
        wanRecyclerView.setLinearLayout();
        wanRecyclerView.setPullRecyclerViewListener(this);

        adapter = new ReleaseYXJAdapter(getContext(), mList, "评价");
        adapter.setTaskBtnListener(this);
        wanRecyclerView.setAdapter(adapter);

        publishPresenter = new PublishPresenter(getActivity(), this);

        return view;
    }

    @Override
    protected void initLazyData() {
        publishPresenter.getPublishTaskList(page, 6);
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

    }

    @Override
    public void outTaskRequestSuccess(int position) {

    }

    @Override
    public void auditTaskRequestSuccess(int type, int position) {

    }

    @Override
    public void appraiseRequestSuccess() {

    }

    @Override
    public void onRefresh() {
        page = 1;
        mList.clear();
        publishPresenter.getPublishTaskList(page, 6);
    }

    @Override
    public void onLoadMore() {
        page ++;
        publishPresenter.getPublishTaskList(page, 6);
    }

    @Override
    public void TaskOnClickListener(String id, int position, String taskType, String taskState) {
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
    }

    @Override
    public void TaskOnClick2Listener(String id, int position) {
        showSubmitPJDialog(id);
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
