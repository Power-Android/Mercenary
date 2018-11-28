package com.power.mercenary.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.power.mercenary.R;
import com.power.mercenary.activity.details_audit_accept.GRAcceptAuditActivity;
import com.power.mercenary.activity.details_audit_accept.GZAcceptAuditActivity;
import com.power.mercenary.activity.details_audit_accept.PTAcceptAuditActivity;
import com.power.mercenary.activity.details_audit_accept.SHAcceptAuditActivity;
import com.power.mercenary.activity.details_intask_accept.GRAcceptInTaskActivity;
import com.power.mercenary.activity.details_intask_accept.GZAcceptInTaskActivity;
import com.power.mercenary.activity.details_intask_accept.PTAcceptInTaskActivity;
import com.power.mercenary.activity.details_intask_accept.SHAcceptInTaskActivity;
import com.power.mercenary.activity.details_success_accept.GRAcceptSuccessActivity;
import com.power.mercenary.activity.details_success_accept.GZAcceptSuccessActivity;
import com.power.mercenary.activity.details_success_accept.PTAcceptSuccessActivity;
import com.power.mercenary.activity.details_success_accept.SHAcceptSuccessActivity;
import com.power.mercenary.base.BaseActivity;
import com.power.mercenary.bean.HomHotBean;
import com.power.mercenary.bean.HotSearchBean;
import com.power.mercenary.bean.MainTaskBean;
import com.power.mercenary.bean.SearchHistoryEntity;
import com.power.mercenary.presenter.HomeSearchPresenter;
import com.power.mercenary.utils.MercenaryUtils;
import com.power.mercenary.utils.SpUtils;
import com.power.mercenary.view.FluidLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeSearchActivity extends BaseActivity implements View.OnClickListener, HomeSearchPresenter.HomeCallBack {

    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.iv_delete)
    ImageView ivDelete;
    @BindView(R.id.tv_wujilu)
    TextView tvWujilu;
    @BindView(R.id.recycler_lishi)
    RecyclerView recyclerLishi;
    @BindView(R.id.activity_search)
    LinearLayout activitySearch;
    @BindView(R.id.search_tv)
    EditText searchTv;
    @BindView(R.id.fluidlayout)
    FluidLayout fluidlayout;
    @BindView(R.id.relative_hot)
    RelativeLayout relativeHot;
    @BindView(R.id.all_search_layout)
    LinearLayout allSearchLayout;
    @BindView(R.id.task_recycler)
    RecyclerView taskRecycler;
    @BindView(R.id.relative_lishi)
    RelativeLayout relativeLishi;
    @BindView(R.id.tv_no_task)
    TextView tvNoTask;
    private List<SearchHistoryEntity> mHistoryList = new ArrayList<>();
    private RecyclerHistoryAdapter mHistoryAdapter;
    private int position;
    private HomeSearchPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        presenter = new HomeSearchPresenter(this, this);
        presenter.getHotSearchInfo(1);
        initData();
        initListener();
    }

    private void initHotSearch(List<HotSearchBean> hotSearchinfo) {
        fluidlayout.removeAllViews();

        fluidlayout.setVisibility(View.VISIBLE);

        for (int i = 0; i < hotSearchinfo.size(); i++) {
            final LinearLayout layout = (LinearLayout) View.inflate(mContext, R.layout.history_item, null);
            final TextView tv = layout.findViewById(R.id.tv_history);
            tv.setTextColor(Color.parseColor("#666666"));
            tv.setText(hotSearchinfo.get(i).getSearch_content());
            FluidLayout.LayoutParams params = new FluidLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(12, 12, 12, 12);
            fluidlayout.addView(layout, params);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.getTaskInfo(tv.getText().toString());
                    searchTv.setText(tv.getText().toString());
                }
            });
        }
    }

    private void initListener() {
        searchTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (searchTv.getText().toString().length() <= 0) {
                    allSearchLayout.setVisibility(View.VISIBLE);
                }
            }
        });
        searchTv.setOnKeyListener(new View.OnKeyListener() {
            @Override

            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    // 先隐藏键盘
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(HomeSearchActivity.this.getCurrentFocus()
                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                    String searchContext = searchTv.getText().toString().trim();
                    if (TextUtils.isEmpty(searchContext)) {
                        Toast.makeText(mContext, "输入框为空，请输入", Toast.LENGTH_SHORT).show();
                    } else {
                        presenter.getTaskInfo(searchContext);
//                        调用搜索方法
                        doSavehistory(searchTv.getText().toString());
                        mHistoryList = getHistory();//从本地取出来
                        if (mHistoryList != null && mHistoryList.size() > 0) {
                            if (mHistoryAdapter != null) {
                                mHistoryAdapter.setNewData(mHistoryList);//刷新一下界面
                            } else {
                                mHistoryAdapter = new RecyclerHistoryAdapter(R.layout.history_item, mHistoryList);
                                recyclerLishi.setAdapter(mHistoryAdapter);
                            }
                            tvWujilu.setVisibility(View.GONE);
                            relativeLishi.setVisibility(View.VISIBLE);

                        }
                    }
                }
                return false;
            }


        });
    }


    //判断本地数据中有没有存在搜索过的数据，查重
    private boolean isHasSelectData(String content) {
        if (mHistoryList == null || mHistoryList.size() == 0) {
            return false;
        }
        for (int i = 0; i < mHistoryList.size(); i++) {
            if (mHistoryList.get(i).getContent().equals(content)) {
                position = i;
                return true;
            }
        }
        return false;
    }

    private void doSavehistory(String content) {

        if (isHasSelectData(content)) {//查重
            mHistoryList.remove(position);
        }
        //后来搜索的文字放在集合中的第一个位置
        mHistoryList.add(0, new SearchHistoryEntity(content));

        if (mHistoryList.size() == 10) {//实现本地历史搜索记录最多不超过十个
            mHistoryList.remove(9);
        }
        //将这个mHistoryListData保存到sp中，其实sp中保存的就是这个mHistoryListData集合
        saveHistory();
    }

    /**
     * 保存历史查询记录
     */
    private void saveHistory() {
        SpUtils.putString(this, "history",
                new Gson().toJson(mHistoryList));//将java对象转换成json字符串进行保存
    }

    /**
     * 获取历史查询记录
     *
     * @return
     */
    private List<SearchHistoryEntity> getHistory() {
        String historyJson = SpUtils.getString(this, "history", "");
        if (historyJson != null && !historyJson.equals("")) {//必须要加上后面的判断，因为获取的字符串默认值就是空字符串
            //将json字符串转换成list集合
            return new Gson().fromJson(historyJson, new TypeToken<List<SearchHistoryEntity>>() {
            }.getType());
        }
        return new ArrayList<SearchHistoryEntity>();
    }

    //历史搜索
    private void initData() {
        recyclerLishi.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerLishi.setNestedScrollingEnabled(false);
        mHistoryList = getHistory();//从本地取出来
        if (mHistoryList != null && mHistoryList.size() > 0) {
            mHistoryAdapter = new RecyclerHistoryAdapter(R.layout.history_item, mHistoryList);
            recyclerLishi.setAdapter(mHistoryAdapter);
            ivDelete.setVisibility(View.VISIBLE);
        } else {
            ivDelete.setVisibility(View.GONE);
//            tvWujilu.setVisibility(View.VISIBLE);
        }

    }


    @OnClick({R.id.tv_back, R.id.iv_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.iv_delete:
                mHistoryList.clear();
                saveHistory();
                if (mHistoryAdapter != null) {
                    mHistoryAdapter.getData().clear();//如果不加这句的话第二次删除就会不生效，真不知道为什么
                    mHistoryAdapter.notifyDataSetChanged();
                    initData();
                }
                break;
        }
    }

    @Override
    public void getHotInfo(List<HomHotBean> hotInfo) {

    }

    @Override
    public void getHotSearchInfo(List<HotSearchBean> hotInfo) {
        initHotSearch(hotInfo);
    }

    @Override
    public void getTaskInfo(final List<MainTaskBean.TuijianBean> hotInfo) {
        if (hotInfo==null||hotInfo.size()<=0){
            tvNoTask.setVisibility(View.VISIBLE);
            taskRecycler.setVisibility(View.GONE);
        }else {
            tvNoTask.setVisibility(View.GONE);
            taskRecycler.setVisibility(View.VISIBLE);
            taskRecycler.setLayoutManager(new LinearLayoutManager(this));
            taskRecycler.setNestedScrollingEnabled(false);
            TuijianAdapter taskAdapter = new TuijianAdapter(R.layout.item_tuijian_renwu, hotInfo);
            taskRecycler.setAdapter(taskAdapter);
            taskAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    String taskType = hotInfo.get(position).getTask_type();
                    String taskId = hotInfo.get(position).getId();
                    String taskState = hotInfo.get(position).getTask_status();
                    if (TextUtils.equals(taskState, "2")) {
                            switch (taskType) {
                                case "1":
                                    Intent ptIntent = new Intent(HomeSearchActivity.this, PTAcceptInTaskActivity.class);
                                    ptIntent.putExtra("taskId", taskId);
                                startActivity(ptIntent);
                                break;

                            case "2":
                            case "5":
                            case "6":
                                Intent shIntent = new Intent(HomeSearchActivity.this, SHAcceptInTaskActivity.class);
                                shIntent.putExtra("taskId", taskId);
                                startActivity(shIntent);
                                break;

                            case "3":
                                Intent grIntent = new Intent(HomeSearchActivity.this, GRAcceptInTaskActivity.class);
                                grIntent.putExtra("taskId", taskId);
                                startActivity(grIntent);
                                break;

                            case "4":
                                Intent gzIntent = new Intent(HomeSearchActivity.this, GZAcceptInTaskActivity.class);
                                gzIntent.putExtra("taskId", taskId);
                                startActivity(gzIntent);
                                break;
                        }

                    } else if (TextUtils.equals(taskState, "3")) {
                        switch (taskType) {
                            case "1":
                                Intent ptIntent = new Intent(HomeSearchActivity.this, PTAcceptAuditActivity.class);
                                ptIntent.putExtra("taskId", taskId);
                                startActivity(ptIntent);
                                break;

                            case "2":
                            case "5":
                            case "6":
                                Intent shIntent = new Intent(HomeSearchActivity.this, SHAcceptAuditActivity.class);
                                shIntent.putExtra("taskId", taskId);
                                startActivity(shIntent);
                                break;

                            case "3":
                                Intent grIntent = new Intent(HomeSearchActivity.this, GRAcceptAuditActivity.class);
                                grIntent.putExtra("taskId", taskId);
                                startActivity(grIntent);
                                break;

                            case "4":
                                Intent gzIntent = new Intent(HomeSearchActivity.this, GZAcceptAuditActivity.class);
                                gzIntent.putExtra("taskId", taskId);
                                startActivity(gzIntent);
                                break;
                        }

                    } else if (TextUtils.equals(taskState, "6") || TextUtils.equals(taskState, "7")) {
                        switch (taskType) {
                            case "1":
                                Intent ptIntent = new Intent(HomeSearchActivity.this, PTAcceptSuccessActivity.class);
                                ptIntent.putExtra("taskId", taskId);
                                startActivity(ptIntent);
                                break;

                            case "2":
                            case "5":
                            case "6":
                                Intent shIntent = new Intent(HomeSearchActivity.this, SHAcceptSuccessActivity.class);
                                shIntent.putExtra("taskId", taskId);
                                startActivity(shIntent);
                                break;

                            case "3":
                                Intent grIntent = new Intent(HomeSearchActivity.this, GRAcceptSuccessActivity.class);
                                grIntent.putExtra("taskId", taskId);
                                startActivity(grIntent);
                                break;

                            case "4":
                                Intent gzIntent = new Intent(HomeSearchActivity.this, GZAcceptSuccessActivity.class);
                                gzIntent.putExtra("taskId", taskId);
                                startActivity(gzIntent);
                                break;
                        }
                    } else {
                        switch (taskType) {
                            case "1":
                                Intent ptIntent = new Intent(HomeSearchActivity.this, PTTaskDetailsActivity.class);
                                ptIntent.putExtra("taskId", taskId);
                                startActivity(ptIntent);
                                break;

                            case "2":
                            case "5":
                            case "6":
                                Intent shIntent = new Intent(HomeSearchActivity.this, SHTaskDetailsActivity.class);
                                shIntent.putExtra("taskId", taskId);
                                startActivity(shIntent);
                                break;

                            case "3":
                                Intent grIntent = new Intent(HomeSearchActivity.this, GRTaskDetailsActivity.class);
                                grIntent.putExtra("taskId", taskId);
                                startActivity(grIntent);
                                break;

                            case "4":
                                Intent gzIntent = new Intent(HomeSearchActivity.this, GZTaskDetailsActivity.class);
                                gzIntent.putExtra("taskId", taskId);
                                startActivity(gzIntent);
                                break;
                        }
                    }
                }
            });
        }
        allSearchLayout.setVisibility(View.GONE);
    }


    class RecyclerHistoryAdapter extends BaseQuickAdapter<SearchHistoryEntity, BaseViewHolder> {

        public RecyclerHistoryAdapter(@LayoutRes int layoutResId, @Nullable List<SearchHistoryEntity> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, SearchHistoryEntity item) {
            final TextView tv_history = helper.getView(R.id.tv_history);
            tv_history.setText(item.getContent());
            tv_history.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.getTaskInfo(tv_history.getText().toString());
                    searchTv.setText(tv_history.getText().toString());
                }
            });
        }
    }

    /**
     * 任务推荐Adapter
     */
    public class TuijianAdapter extends BaseQuickAdapter<MainTaskBean.TuijianBean, BaseViewHolder> {

        public TuijianAdapter(int layoutResId, @Nullable List<MainTaskBean.TuijianBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, MainTaskBean.TuijianBean item) {
            TextView titleTv = helper.getView(R.id.item_title_tv);
            TextView contentTv = helper.getView(R.id.item_content_tv);
            TextView task_type = helper.getView(R.id.item_money_tv);
            RecyclerView tagRecyclerView = helper.getView(R.id.tag_recycler);
            titleTv.setText(item.getTask_name());
            task_type.setText(searchTv.getText().toString());
            contentTv.setText(item.getTask_description());

            tagRecyclerView.setNestedScrollingEnabled(false);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            tagRecyclerView.setLayoutManager(linearLayoutManager);
            TagAdapter tagAdapter = new TagAdapter(R.layout.item_tag_layout, MercenaryUtils.stringToList(item.getTask_tag()));
            tagRecyclerView.setAdapter(tagAdapter);
        }
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
//    private void showDialog(int grary, int animationStyle) {
//        BaseDialog.Builder builder = new BaseDialog.Builder(this);
//        final BaseDialog dialog = builder.setViewId(R.layout.dialo)
//                //设置dialogpadding
//                .setPaddingdp(10, 0, 10, 0)
//                //设置显示位置
//                .setGravity(grary)
//                //设置动画
//                .setAnimation(animationStyle)
//                //设置dialog的宽高
//                .setWidthHeightpx(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
//                //设置触摸dialog外围是否关闭
//                .isOnTouchCanceled(true)
//                //设置监听事件
//                .builder();
//        dialog.show();
//        TextView tv_canel = dialog.getView(R.id.tv_canel);
//        tv_canel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //关闭dialog
//                dialog.close();
//            }
//        });
//        TextView tv_yes = dialog.getView(R.id.tv_yes);
//        tv_yes.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mHistoryList.clear();
//                saveHistory();
//                if (mHistoryAdapter != null) {
//                    mHistoryAdapter.getData().clear();//如果不加这句的话第二次删除就会不生效，真不知道为什么
//                    mHistoryAdapter.notifyDataSetChanged();
//                    initData();
//                }
//                dialog.close();
//            }
//        });
//    }

}
