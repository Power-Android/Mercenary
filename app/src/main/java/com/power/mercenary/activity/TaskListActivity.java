package com.power.mercenary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.power.mercenary.R;
import com.power.mercenary.adapter.task.TaskListAdapter;
import com.power.mercenary.base.BaseActivity;
import com.power.mercenary.bean.task.TaskDetailsBean;
import com.power.mercenary.bean.task.TaskListBean;
import com.power.mercenary.presenter.TaskListPresenter;
import com.power.mercenary.view.pullrecyclerview.WanRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 * 任务列表
 *
 */

public class TaskListActivity extends BaseActivity implements TaskListPresenter.TaskListCallBack, TaskListAdapter.onItemClickListener, WanRecyclerView.PullRecyclerViewCallBack {

    @BindView(R.id.title_back_iv)
    ImageView titleBackIv;
    @BindView(R.id.title_content_tv)
    TextView titleContentTv;
    @BindView(R.id.act_taskList_recyclerView)
    WanRecyclerView recyclerView;

    private String type;

    private TaskListPresenter presenter;

    private TaskListAdapter adapter;

    private List<TaskListBean> data;

    private int page = 1;
    private String taskChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        ButterKnife.bind(this);
        titleBackIv.setVisibility(View.VISIBLE);
        titleContentTv.setText("任务列表");

        type = getIntent().getStringExtra("type");

        taskChild = getIntent().getStringExtra("child");

        presenter = new TaskListPresenter(this, this);
        presenter.getTaskList(page, type, taskChild);

        initView();
    }

    private void initView() {

        recyclerView.setPullRecyclerViewListener(this);
        recyclerView.setLinearLayout();
        data = new ArrayList<>();
        adapter = new TaskListAdapter(this, data);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

    }

    @Override
    public void getTaskList(List<TaskListBean> datas) {
        if (datas != null) {
            data.addAll(datas);
            recyclerView.setHasMore(datas.size(), 10);
        } else {
            recyclerView.setHasMore(0, 10);
        }
        adapter.notifyDataSetChanged();
        recyclerView.setStateView(data.size());
    }

    @Override
    public void getTaskDetails(TaskDetailsBean datas) {

    }

    @Override
    public void getListFail() {
        recyclerView.setHasMore(0, 10);
    }

    @Override
    public void onTaskClickListener(String itemId) {
        switch (type) {
            case "1"://跑腿详情页
                Intent pt = new Intent(mContext, PTTaskDetailsActivity.class);
                pt.putExtra("taskId", itemId);
                startActivity(pt);
                break;
            case "2"://生活详情页
                Intent sh = new Intent(mContext, SHTaskDetailsActivity.class);
                sh.putExtra("taskId", itemId);
                startActivity(sh);
                break;
            case "3"://个人详情页
                Intent gr = new Intent(mContext, GRTaskDetailsActivity.class);
                gr.putExtra("taskId", itemId);
                startActivity(gr);
                break;
            case "4"://工作详情页
                Intent gz = new Intent(mContext, GZTaskDetailsActivity.class);
                gz.putExtra("taskId", itemId);
                startActivity(gz);
                break;
            case "5"://身心详情页
                Intent sx = new Intent(mContext, SHTaskDetailsActivity.class);
                sx.putExtra("taskId", itemId);
                startActivity(sx);
                break;
            case "6"://其他详情页
                Intent qt = new Intent(mContext, SHTaskDetailsActivity.class);
                qt.putExtra("taskId", itemId);
                startActivity(qt);
                break;
        }
    }

    @Override
    public void onRefresh() {
        page = 1;
        data.clear();
        presenter.getTaskList(page, type, taskChild);
    }

    @Override
    public void onLoadMore() {
        page ++;
        presenter.getTaskList(page, type, taskChild);
    }

    /**
     * 任务推荐标签Adapter
     */
    public static class TagAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public TagAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            TextView tagTv = helper.getView(R.id.item_content_tv);
            tagTv.setText(item);
        }
    }

    @OnClick(R.id.title_back_iv)
    public void onViewClicked() {
        finish();
    }
}
