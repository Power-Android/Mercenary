package com.power.mercenary.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.power.mercenary.MyApplication;
import com.power.mercenary.R;
import com.power.mercenary.activity.GRTaskDetailsActivity;
import com.power.mercenary.activity.GZTaskDetailsActivity;
import com.power.mercenary.activity.HomeSearchActivity;
import com.power.mercenary.activity.LocationActivity;
import com.power.mercenary.activity.PTTaskDetailsActivity;
import com.power.mercenary.activity.PostDetailActivity;
import com.power.mercenary.activity.SHTaskDetailsActivity;
import com.power.mercenary.activity.SignInActivity;
import com.power.mercenary.activity.TaskListActivity;
import com.power.mercenary.activity.WorkPubActivity;
import com.power.mercenary.base.BaseFragment;
import com.power.mercenary.bean.BannerBean;
import com.power.mercenary.bean.CitySelectBean;
import com.power.mercenary.bean.HomHotBean;
import com.power.mercenary.bean.HotSearchBean;
import com.power.mercenary.bean.MainTaskBean;
import com.power.mercenary.bean.NineGridTestModel;
import com.power.mercenary.bean.Testbean;
import com.power.mercenary.data.EventConstants;
import com.power.mercenary.event.EventUtils;
import com.power.mercenary.presenter.HomeSearchPresenter;
import com.power.mercenary.presenter.MainPresenter;
import com.power.mercenary.utils.BannerUtils;
import com.power.mercenary.utils.MercenaryUtils;
import com.power.mercenary.utils.MyUtils;
import com.power.mercenary.utils.TUtils;
import com.power.mercenary.utils.Urls;
import com.power.mercenary.view.BaseDialog;
import com.power.mercenary.view.MyPageIndicator;
import com.power.mercenary.view.NineGridTestLayout;
import com.power.mercenary.view.PageGridView;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

//import com.power.mercenary.activity.TestActivity;
//import com.power.mercenary.activity.TestActivity;

/**
 * Created by power on 2018/3/21.
 */

public class HomeFragment extends BaseFragment implements MainPresenter.MainCallBack, HomeSearchPresenter.HomeCallBack {
    @BindView(R.id.location_tv)
    TextView locationTv;
    @BindView(R.id.search_tv)
    TextView searchTv;
    @BindView(R.id.banner)
    Banner banner;
    Unbinder unbinder;
    @BindView(R.id.pageGridView)
    PageGridView pageGridView;
    @BindView(R.id.pageindicator)
    MyPageIndicator pageindicator;
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
    @BindView(R.id.tuijian_recycler)
    RecyclerView tuijianRecycler;
    @BindView(R.id.post_rl)
    RelativeLayout postRl;
    @BindView(R.id.post_recycler)
    RecyclerView postRecycler;
    Unbinder unbinder1;
    private int width;

    private BaseDialog mDialog;
    private BaseDialog.Builder mBuilder;

    private MainPresenter mainPresenter;
    private List<MainTaskBean.TuijianBean> tuijianList;
    private TuijianAdapter tuijianAdapter;

    private List<NineGridTestModel> mList = new ArrayList<>();
    private List<String> mUrlList = new ArrayList<>();

    private String[] mUrls = new String[]{"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=202447557,2967022603&fm=27&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=104961686,3757525983&fm=27&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=569334953,1638673400&fm=27&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2043305675,3979419376&fm=200&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=266745161,658804068&fm=27&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=222615259,2947254622&fm=27&gp=0.jpg",
            "http://img1.imgtn.bdimg.com/it/u=950771854,530317119&fm=27&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=2557022909,3736713361&fm=27&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=1830359176,654163576&fm=200&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=4193964417,1586871857&fm=27&gp=0.jpg",
    };
    private MainTaskBean taskBean;

    private List<BannerBean> bannerBeans;

    private List<String> nextList = new ArrayList<>();

    private int PAOTUI = 101, SHENGHUO = 102, GERENDINGZHI = 103, GONGZUO = 104, JIANKANG = 105;
    private HomeSearchPresenter presenter;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        unbinder = ButterKnife.bind(this, view);
        presenter = new HomeSearchPresenter(getActivity(),this);
        presenter.getHotInfo(1);
        mainPresenter = new MainPresenter(getActivity(), this);
        mainPresenter.getTaskList();
        initData();
        EventBus.getDefault().register(this);
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRecevierEvent(EventUtils event) {
        switch (event.getType()) {
            case EventConstants.TYPE_CITY_SELECT:
                CitySelectBean selectBean = (CitySelectBean) event.getData();
                locationTv.setText(selectBean.cityName);
                break;
        }
    }

    @Override
    protected void initLazyData() {

    }

    private void initData() {
        //---------------------------设置Banner----------------------------------------
        List<Integer> baaaneList = new ArrayList<>();
        baaaneList.add(R.drawable.test_banner);
        baaaneList.add(R.drawable.test_banner);
        baaaneList.add(R.drawable.test_banner);

        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                TUtils.showShort(mContext, bannerBeans.get(position).getUrl());

            }
        });
        //----------------------------结束---------------------------------------------

        //----------------------------横向分页------------------------------------------
        List<Testbean> gridPageList = new ArrayList<>();
        Testbean testbean0 = new Testbean();
        testbean0.setImg(R.drawable.pt_iv);
        testbean0.setTitle("跑腿");
        gridPageList.add(testbean0);
        Testbean testbean1 = new Testbean();
        testbean1.setImg(R.drawable.sh_iv);
        testbean1.setTitle("生活");
        gridPageList.add(testbean1);
        Testbean testbean2 = new Testbean();
        testbean2.setImg(R.drawable.grdz_iv);
        testbean2.setTitle("个人定制");
        gridPageList.add(testbean2);
        Testbean testbean3 = new Testbean();
        testbean3.setImg(R.drawable.gz_iv);
        testbean3.setTitle("工作");
        gridPageList.add(testbean3);
        Testbean testbean4 = new Testbean();
        testbean4.setImg(R.drawable.sx_iv);
        testbean4.setTitle("身心");
        gridPageList.add(testbean4);
        Testbean testbean5 = new Testbean();
        testbean5.setImg(R.drawable.qt_iv);
        testbean5.setTitle("其他");
        gridPageList.add(testbean5);

        width = getResources().getDisplayMetrics().widthPixels / 5;
        pageGridView.setNestedScrollingEnabled(false);
        GridPageAdapter gridPageAdapter = new GridPageAdapter(gridPageList);
        pageGridView.setAdapter(gridPageAdapter);
        pageGridView.setOnItemClickListener(gridPageAdapter);
        //设置分页指示器
        pageGridView.setPageIndicator(pageindicator);
        //----------------------------结束----------------------------------------------
        //----------------------------推荐----------------------------------------------

        tuijianRecycler.setNestedScrollingEnabled(false);
        tuijianRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        tuijianList = new ArrayList<>();
        tuijianAdapter = new TuijianAdapter(R.layout.item_tuijian_renwu, tuijianList);
        tuijianRecycler.setAdapter(tuijianAdapter);
        tuijianAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (tuijianList.get(position).getTask_type()) {
                    case "1":
                        Intent ptIntent = new Intent(getActivity(), PTTaskDetailsActivity.class);
                        ptIntent.putExtra("taskId", tuijianList.get(position).getId());
                        startActivity(ptIntent);
                        break;

                    case "2":
                    case "5":
                    case "6":
                        Intent shIntent = new Intent(getActivity(), SHTaskDetailsActivity.class);
                        shIntent.putExtra("taskId", tuijianList.get(position).getId());
                        startActivity(shIntent);
                        break;

                    case "3":
                        Intent grIntent = new Intent(getActivity(), GRTaskDetailsActivity.class);
                        grIntent.putExtra("taskId", tuijianList.get(position).getId());
                        startActivity(grIntent);
                        break;

                    case "4":
                        Intent gzIntent = new Intent(getActivity(), GZTaskDetailsActivity.class);
                        gzIntent.putExtra("taskId", tuijianList.get(position).getId());
                        startActivity(gzIntent);
                        break;
                }
            }
        });
        //----------------------------结束-----------------------------------------------
        //----------------------------热门帖子--------------------------------------------
//        NineGridTestModel model1 = new NineGridTestModel();
//        model1.urlList.add(mUrls[0]);
//        mList.add(model1);
//
//        NineGridTestModel model2 = new NineGridTestModel();
//        model2.urlList.add(mUrls[0]);
//        model2.urlList.add(mUrls[1]);
//        mList.add(model2);
//
//        NineGridTestModel model6 = new NineGridTestModel();
//        for (int i = 0; i < 9; i++) {
//            model6.urlList.add(mUrls[i]);
//        }
//        mList.add(model6);
//
//        NineGridTestModel model7 = new NineGridTestModel();
//        for (int i = 3; i < 7; i++) {
//            model7.urlList.add(mUrls[i]);
//        }
//        mList.add(model7);
//
//        NineGridTestModel model8 = new NineGridTestModel();
//        for (int i = 3; i < 6; i++) {
//            model8.urlList.add(mUrls[i]);
//        }
//        mList.add(model8);

        //-------------------------------结束-------——--------------------------------------------

        initRenwutj();
    }

    @Override
    public void getTaskList(MainTaskBean taskBean) {
        this.taskBean = taskBean;
        initRenwutjData();
    }

    @Override
    public void getBannerList(List<BannerBean> datas) {
        bannerBeans = datas;
        List<String> bannerList = new ArrayList<>();
        if (datas != null) {
            for (int i = 0; i < datas.size(); i++) {
                bannerList.add(datas.get(i).getPic());
            }
        }
        BannerUtils.startBanner(banner, bannerList);
    }

    @Override
    public void getHotInfo(List<HomHotBean> hotInfo) {
        postRecycler.setNestedScrollingEnabled(false);
        postRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        MyAdapter myAdapter = new MyAdapter(R.layout.item_circle_friend, hotInfo);
        postRecycler.setAdapter(myAdapter);
    }

    @Override
    public void getHotSearchInfo(List<HotSearchBean> hotInfo) {

    }

    @Override
    public void getTaskInfo(List<MainTaskBean.TuijianBean> hotInfo) {

    }

    private class MyAdapter extends BaseQuickAdapter<HomHotBean, BaseViewHolder> {

        public MyAdapter(@LayoutRes int layoutResId, @Nullable List<HomHotBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, final HomHotBean item) {
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

    private void initRenwutjData() {
        if (taskBean != null && taskBean.getTuijian() != null) {
            tuijianList.addAll(taskBean.getTuijian());
        }
        tuijianAdapter.notifyDataSetChanged();
    }

    private void initTongchengData() {
        if (taskBean != null && taskBean.getSame_city() != null) {
            tuijianList.addAll(taskBean.getSame_city());
        }
        tuijianAdapter.notifyDataSetChanged();
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
            RecyclerView tagRecyclerView = helper.getView(R.id.tag_recycler);
            titleTv.setText(item.getTask_name());
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

    /**
     * 横向分页Adapter
     */
    public class GridPageAdapter extends PageGridView.PagingAdapter<MyVH> implements PageGridView.OnItemClickListener {
        List<Testbean> mData = new ArrayList<>();
        int positon;

        public GridPageAdapter(List<Testbean> data) {
            this.mData.addAll(data);
        }

        @Override
        public MyVH onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_grid_page, parent, false);
            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.height = width;
            params.width = width;
            view.setLayoutParams(params);
            return new MyVH(view);
        }

        @Override
        public void onBindViewHolder(MyVH holder, int position) {
            this.positon = position;
            holder.tv_title.setText(mData.get(position).getTitle());
            Glide.with(mContext).load(mData.get(position).getImg()).into(holder.icon);
        }

        @Override
        public void onItemClick(PageGridView pageGridView, int position) {
//            TUtils.showShort(mContext, "点击了---" + mData.get(position).getTitle());

            if (!MyApplication.isLogin()) {
                startActivity(new Intent(mContext, SignInActivity.class));
                return;
            }

            switch (position) {
                case 0://跑腿
                    nextList.clear();
                    nextList.add("物品");
                    nextList.add("人员");
                    showNextIssueDialog(60, 60, 2, PAOTUI);
                    break;
                case 1://生活
                    nextList.clear();
                    nextList.add("衣");
                    nextList.add("食");
                    nextList.add("住");
                    nextList.add("行");
                    nextList.add("游");
                    showNextIssueDialog(20, 20, 3, SHENGHUO);
                    break;
                case 2://个人定制
                    nextList.clear();
                    nextList.add("硬件");
                    nextList.add("软件");
                    showNextIssueDialog(60, 60, 2, GERENDINGZHI);
                    break;
                case 3://工作
                    nextList.clear();
                    nextList.add("仕");
                    nextList.add("农");
                    nextList.add("工");
                    nextList.add("商");
                    nextList.add("律");
                    showNextIssueDialog(20, 20, 3, GONGZUO);
                    break;
                case 4://健康
                    nextList.clear();
                    nextList.add("心理");
                    nextList.add("健身");
                    nextList.add("减肥");
                    showNextIssueDialog(20, 20, 3, JIANKANG);
                    break;
                case 5://其他
                    mDialog.dismiss();
                    Intent intent = new Intent(mContext, TaskListActivity.class);
                    intent.putExtra("type", "6");
                    startActivity(intent);
                    break;
            }
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        @Override
        public List getData() {
            return mData;
        }

        @Override
        public Object getEmpty() {
            return new Testbean();
        }
    }

    private void showNextIssueDialog(int left, int right, int spanCount, final int pubType) {
        mBuilder = new BaseDialog.Builder(mContext);
        mDialog = mBuilder.setViewId(R.layout.dialog_issue)
                //设置dialogpadding
                .setPaddingdp(left, 0, right, 40)
                //设置显示位置
                .setGravity(Gravity.BOTTOM)
                //设置动画
                .setAnimation(R.style.Bottom_Top_aniamtion)
                //设置dialog的宽高
                .setWidthHeightpx(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                //设置触摸dialog外围是否关闭
                .isOnTouchCanceled(true)
                //设置监听事件
                .builder();

        RecyclerView issueNextRecycler = mDialog.getView(R.id.issue_recycler);
        issueNextRecycler.setLayoutManager(new GridLayoutManager(mContext, spanCount));
        issueNextRecycler.setNestedScrollingEnabled(false);
        IssueNextAdapter issueNextAdapter = new IssueNextAdapter(R.layout.item_issue_layout, nextList);
        issueNextRecycler.setAdapter(issueNextAdapter);
        issueNextAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mContext, TaskListActivity.class);
                switch (pubType) {
                    case 101://跑腿
                        mDialog.dismiss();
                        intent.putExtra("type", "1");
                        if (position == 0) {
                            intent.putExtra("child", "1");
                        } else if (position == 1) {
                            intent.putExtra("child", "2");
                        }
                        startActivity(intent);
                        break;
                    case 102://生活
                        mDialog.dismiss();
                        intent.putExtra("type", "2");
                        if (position == 0) {
                            intent.putExtra("child", "1");
                        } else if (position == 1) {
                            intent.putExtra("child", "2");
                        } else if (position == 2) {
                            intent.putExtra("child", "3");
                        } else if (position == 3) {
                            intent.putExtra("child", "4");
                        } else if (position == 4) {
                            intent.putExtra("child", "5");
                        }
                        startActivity(intent);
                        break;
                    case 103://个人定制
                        mDialog.dismiss();
                        intent.putExtra("type", "3");
                        if (position == 0) {
                            intent.putExtra("child", "1");
                        } else if (position == 1) {
                            intent.putExtra("child", "2");
                        }
                        startActivity(intent);
                        break;
                    case 104://工作
                        mDialog.dismiss();
                        intent.putExtra("type", "4");
                        if (position == 0) {
                            intent.putExtra("child", "1");
                        } else if (position == 1) {
                            intent.putExtra("child", "2");
                        } else if (position == 2) {
                            intent.putExtra("child", "3");
                        } else if (position == 3) {
                            intent.putExtra("child", "4");
                        } else if (position == 4) {
                            intent.putExtra("child", "5");
                        }
                        startActivity(intent);
                        break;
                    case 105://健康
                        mDialog.dismiss();
                        intent.putExtra("type", "5");
                        if (position == 0) {
                            intent.putExtra("child", "1");
                        } else if (position == 1) {
                            intent.putExtra("child", "2");
                        } else if (position == 2) {
                            intent.putExtra("child", "3");
                        }
                        startActivity(intent);
                        break;
                }
            }
        });
        mDialog.show();
    }

    private class IssueNextAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public IssueNextAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.item_type_tv, item)
                    .addOnClickListener(R.id.item_type_tv);
        }
    }

    /**
     * 横向分页Viewholder
     */
    public static class MyVH extends RecyclerView.ViewHolder {
        public TextView tv_title;
        public ImageView icon;

        public MyVH(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.item_title_tv);
            icon = (ImageView) itemView.findViewById(R.id.item_icon_iv);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        unbinder.unbind();
    }

    @OnClick({R.id.location_tv, R.id.search_tv, R.id.renwutj_ll, R.id.tongcheng_ll, R.id.post_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.location_tv://定位
                startActivity(new Intent(mContext, LocationActivity.class));
                break;
            case R.id.search_tv://搜索
                startActivity(new Intent(mContext, HomeSearchActivity.class));
                break;
            case R.id.renwutj_ll://任务推荐
                tuijianList.clear();
                initRenwutj();
                break;
            case R.id.tongcheng_ll://同城
                tuijianList.clear();
                initTongcheng();
                break;
            case R.id.post_rl://热门帖子
                startActivity(new Intent(mContext, WorkPubActivity.class));
                break;
        }
    }
}
