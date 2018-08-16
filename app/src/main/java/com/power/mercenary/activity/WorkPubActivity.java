package com.power.mercenary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.power.mercenary.MyApplication;
import com.power.mercenary.R;
import com.power.mercenary.base.BaseActivity;
import com.power.mercenary.bean.NineGridTestModel;
import com.power.mercenary.bean.TieZiDetailsBean;
import com.power.mercenary.bean.TieZiListBean;
import com.power.mercenary.http.ResponseBean;
import com.power.mercenary.presenter.TieZiListPresenter;
import com.power.mercenary.utils.MyUtils;
import com.power.mercenary.utils.Urls;
import com.power.mercenary.view.NineGridTestLayout;
import com.power.mercenary.view.pullrecyclerview.WanRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WorkPubActivity extends BaseActivity implements TieZiListPresenter.TaskListCallBack, WanRecyclerView.PullRecyclerViewCallBack {

    @BindView(R.id.title_back_iv)
    ImageView titleBackIv;
    @BindView(R.id.title_content_tv)
    TextView titleContentTv;
    @BindView(R.id.recyclerView)
    WanRecyclerView recyclerView;
    @BindView(R.id.title_content_right_tv)
    TextView titleContentRightTv;
    @BindView(R.id.iv_photo)
    ImageView ivPhoto;
    private int page = 1;
    private List<NineGridTestModel> mList = new ArrayList<>();
    private List<TieZiListBean> mData = new ArrayList<>();
    private List<String> mUrlList = new ArrayList<>();

    private MyAdapter myAdapter;
    private TieZiListPresenter presenter;
    private String task_type_child;
    private String task_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_pub);
        ButterKnife.bind(this);
        titleBackIv.setVisibility(View.VISIBLE);
        titleContentTv.setText("工作酒馆");
        task_type_child = getIntent().getStringExtra("task_type_child");
        task_type = getIntent().getStringExtra("task_type");

        presenter = new TieZiListPresenter(this,this);
        presenter.getTaskList(page, task_type, task_type_child);
        initView();
    }

    private void initView() {

        recyclerView.setPullRecyclerViewListener(this);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLinearLayout();
        myAdapter = new MyAdapter(R.layout.item_circle_friend, mData);
        recyclerView.setAdapter(myAdapter);

    }

    @OnClick({R.id.title_back_iv, R.id.iv_photo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back_iv:
                finish();
                break;




            case R.id.iv_photo:
                if (!MyApplication.isLogin()) {
                    startActivity(new Intent(this, SignInActivity.class));
                    return;
                }
                Intent intent = new Intent(mContext,PostActivity.class);
                intent.putExtra("task_type_child",task_type_child);
                intent.putExtra("task_type",task_type);
                startActivityForResult(intent,1);
                break;
        }
    }

    @Override
    public void getTaskList(List<TieZiListBean> datas) {
        if (datas != null) {
            mData.addAll(datas);
            recyclerView.setHasMore(datas.size(), 10);
        } else {
            recyclerView.setHasMore(0, 10);
        }
        myAdapter.notifyDataSetChanged();
        recyclerView.setStateView(mData.size());
    }

    @Override
    public void getTaskDetails(TieZiDetailsBean datas) {

    }

    @Override
    public void getListFail() {
        recyclerView.setHasMore(0, 10);
    }

    @Override
    public void getPubPinglun(ResponseBean datas) {

    }

    @Override
    public void getHuifu(ResponseBean datas) {

    }

    @Override
    public void getPost(ResponseBean datas) {

    }

    @Override
    public void onRefresh() {
        page = 1;
        mData.clear();
        presenter.getTaskList(page, task_type, task_type_child);
    }

    @Override
    public void onLoadMore() {
        page++;
        presenter.getTaskList(page, task_type, task_type_child);
    }

    private class MyAdapter extends BaseQuickAdapter<TieZiListBean, BaseViewHolder> {

        public MyAdapter(@LayoutRes int layoutResId, @Nullable List<TieZiListBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, final TieZiListBean item) {

            helper.setText(R.id.tv_name,item.getPost_user_name());
            helper.setText(R.id.tv_time, MyUtils.getDateToStringTime(item.getCreate_time()));
            helper.setText(R.id.tv_content,item.getPost_content());
            helper.setText(R.id.tv_pinglun,item.getCount());
            Glide.with(mContext).load(Urls.BASEIMGURL+item.getPost_user_headimg()).into((ImageView) helper.getView(R.id.item_image));

            NineGridTestLayout nineGridlayout = helper.getView(R.id.nine_gridlayout);
            nineGridlayout.setIsShowAll(item.isShowAll);
            String post_img = item.getPost_img();

            String[] all=post_img.split("\\|");

            mUrlList.clear();
            for (int i = 0; i < all.length; i++) {
                mUrlList.add(Urls.BASEIMGURL+all[i]);
            }
            item.setUrlList(mUrlList);
            nineGridlayout.setUrlList(item.getUrlList());
            helper.getView(R.id.jump_detail_ll).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext,PostDetailActivity.class);
                    intent.putExtra("id",item.getId()+"");
                    startActivityForResult(intent,1);
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1&&resultCode==2){
            page = 1;
            mData.clear();
            presenter.getTaskList(page, task_type, task_type_child);
        }
    }
}
