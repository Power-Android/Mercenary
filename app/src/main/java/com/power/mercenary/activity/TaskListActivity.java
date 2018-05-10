package com.power.mercenary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.power.mercenary.R;
import com.power.mercenary.base.BaseActivity;
import com.power.mercenary.fragment.HomeFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TaskListActivity extends BaseActivity {

    @BindView(R.id.title_back_iv)
    ImageView titleBackIv;
    @BindView(R.id.title_content_tv)
    TextView titleContentTv;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        ButterKnife.bind(this);
        titleBackIv.setVisibility(View.VISIBLE);
        titleContentTv.setText("任务列表");
        initView();
    }

    private void initView() {

        final String type = getIntent().getStringExtra("type");

        List<String> taskList = new ArrayList<>();
        taskList.add("");
        taskList.add("");
        taskList.add("");
        taskList.add("");
        taskList.add("");

        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        TaskAdapter taskAdapter = new TaskAdapter(R.layout.item_tuijian_renwu,taskList);
        recyclerView.setAdapter(taskAdapter);
        taskAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (type){
                    case "0"://跑腿详情页
                        startActivity(new Intent(mContext,PTTaskDetailsActivity.class));
                        break;
                    case "1"://生活详情页
                        startActivity(new Intent(mContext,SHTaskDetailsActivity.class));
                        break;
                    case "2"://个人详情页
                        startActivity(new Intent(mContext,GRTaskDetailsActivity.class));
                        break;
                    case "3"://工作详情页
                        startActivity(new Intent(mContext,GZTaskDetailsActivity.class));
                        break;
                    case "4"://身心详情页
                        startActivity(new Intent(mContext,SXTaskDetailsActivity.class));
                        break;
                    case "5"://其他详情页
                        startActivity(new Intent(mContext,QTTaskDetailsActivity.class));
                        break;
                }
            }
        });
    }

    private class TaskAdapter extends BaseQuickAdapter<String,BaseViewHolder>{

        public TaskAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            TextView titleTv = helper.getView(R.id.item_title_tv);
            TextView contentTv = helper.getView(R.id.item_content_tv);
            RecyclerView tagRecyclerView = helper.getView(R.id.tag_recycler);
            tagRecyclerView.setNestedScrollingEnabled(false);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            tagRecyclerView.setLayoutManager(linearLayoutManager);
            List<String> tagList = new ArrayList<>();
            tagList.add("");
            tagList.add("");
            tagList.add("");
            TagAdapter tagAdapter = new TagAdapter(R.layout.item_tag_layout, tagList);
            tagRecyclerView.setAdapter(tagAdapter);
        }
    }

    /**
     * 任务推荐标签Adapter
     */
    public class TagAdapter extends BaseQuickAdapter<String,BaseViewHolder>{

        public TagAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            TextView tagTv = helper.getView(R.id.item_content_tv);
        }
    }

    @OnClick(R.id.title_back_iv)
    public void onViewClicked() {
        finish();
    }
}
