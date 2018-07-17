package com.power.mercenary;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.power.mercenary.activity.PubGerendingzhiActivity;
import com.power.mercenary.activity.PubGongzuoActivity;
import com.power.mercenary.activity.PubJiankangActivity;
import com.power.mercenary.activity.PubPaotuiActivity;
import com.power.mercenary.activity.PubQitaActivity;
import com.power.mercenary.activity.PubShenghuoActivity;
import com.power.mercenary.activity.SignInActivity;
import com.power.mercenary.base.BaseActivity;
import com.power.mercenary.base.BaseFragment;
import com.power.mercenary.data.EventConstants;
import com.power.mercenary.event.EventUtils;
import com.power.mercenary.fragment.HomeFragment;
import com.power.mercenary.fragment.MessageFragment;
import com.power.mercenary.fragment.MineFragment;
import com.power.mercenary.fragment.PubFragment;
import com.power.mercenary.view.BaseDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.fl_content)
    FrameLayout flContent;
    @BindView(R.id.iv_home)
    ImageView ivHome;
    @BindView(R.id.tv_home)
    TextView tvHome;
    @BindView(R.id.ll_home)
    LinearLayout llHome;
    @BindView(R.id.iv_pub)
    ImageView ivPub;
    @BindView(R.id.tv_pub)
    TextView tvPub;
    @BindView(R.id.ll_pub)
    LinearLayout llPub;
    @BindView(R.id.iv_issue)
    ImageView ivIssue;
    @BindView(R.id.tv_issue)
    TextView tvIssue;
    @BindView(R.id.ll_issue)
    LinearLayout llIssue;
    @BindView(R.id.iv_message)
    ImageView ivMessage;
    @BindView(R.id.tv_message)
    TextView tvMessage;
    @BindView(R.id.ll_message)
    LinearLayout llMessage;
    @BindView(R.id.iv_mine)
    ImageView ivMine;
    @BindView(R.id.tv_mine)
    TextView tvMine;
    @BindView(R.id.ll_mine)
    LinearLayout llMine;
    @BindView(R.id.ll_tab)
    LinearLayout llTab;
    private BaseFragment baseFragment;
    private HomeFragment homeFragment;
    private PubFragment pubFragment;
    private MessageFragment messageFragment;
    private MineFragment mineFragment;
    private BaseDialog mDialog;
    private BaseDialog.Builder mBuilder;
    private List<String> list;
    private List<String> nextList = new ArrayList<>();
    private int PAOTUI = 101, SHENGHUO = 102, GERENDINGZHI = 103, GONGZUO = 104, JIANKANG = 105;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        homeFragment = new HomeFragment();
        addFragments(homeFragment);

        EventBus.getDefault().register(this);
    }

    private void addFragments(BaseFragment f) {
        // 第一步：得到fragment管理类
        FragmentManager manager = getSupportFragmentManager();
        // 第二步：开启一个事务
        FragmentTransaction transaction = manager.beginTransaction();

        if (baseFragment != null) {
            //每次把前一个fragment给隐藏了
            transaction.hide(baseFragment);
        }
        //isAdded:判断当前的fragment对象是否被加载过
        if (!f.isAdded()) {
            // 第三步：调用添加fragment的方法 第一个参数：容器的id 第二个参数：要放置的fragment的一个实例对象
            transaction.add(R.id.fl_content, f);
        }
        //显示当前的fragment
        transaction.show(f);
        // 第四步：提交
        transaction.commit();
        baseFragment = f;
    }

    @OnClick({R.id.ll_home, R.id.ll_pub, R.id.ll_issue, R.id.ll_message, R.id.ll_mine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_home:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                }
                addFragments(homeFragment);
                ivHome.setImageResource(R.drawable.home_true);
                ivPub.setImageResource(R.drawable.pub_false);
                ivIssue.setImageResource(R.drawable.issue_bg);
                ivMessage.setImageResource(R.drawable.message_false);
                ivMine.setImageResource(R.drawable.mine_false);
                tvHome.setTextColor(getResources().getColor(R.color.colorPrimary));
                tvPub.setTextColor(getResources().getColor(R.color.textcolor_tab));
                tvIssue.setTextColor(getResources().getColor(R.color.textcolor_tab));
                tvMessage.setTextColor(getResources().getColor(R.color.textcolor_tab));
                tvMine.setTextColor(getResources().getColor(R.color.textcolor_tab));
                break;
            case R.id.ll_pub:
                if (pubFragment == null) {
                    pubFragment = new PubFragment();
                }
                addFragments(pubFragment);
                ivHome.setImageResource(R.drawable.home_false);
                ivPub.setImageResource(R.drawable.pub_true);
                ivIssue.setImageResource(R.drawable.issue_bg);
                ivMessage.setImageResource(R.drawable.message_false);
                ivMine.setImageResource(R.drawable.mine_false);
                tvHome.setTextColor(getResources().getColor(R.color.textcolor_tab));
                tvPub.setTextColor(getResources().getColor(R.color.colorPrimary));
                tvIssue.setTextColor(getResources().getColor(R.color.textcolor_tab));
                tvMessage.setTextColor(getResources().getColor(R.color.textcolor_tab));
                tvMine.setTextColor(getResources().getColor(R.color.textcolor_tab));
                break;
            case R.id.ll_issue:
                if (!MyApplication.isLogin()) {
                    startActivity(new Intent(this, SignInActivity.class));
                    return;
                }

                list = new ArrayList<>();
                list.add("跑腿");
                list.add("生活");
                list.add("个人定制");
                list.add("工作");
                list.add("健康");
                list.add("其他");
                showIssueDialog();
                break;
            case R.id.ll_message:
                if (!MyApplication.isLogin()) {
                    startActivity(new Intent(this, SignInActivity.class));
                    return;
                }
                if (messageFragment == null) {
                    messageFragment = new MessageFragment();
                }
                addFragments(messageFragment);
                ivHome.setImageResource(R.drawable.home_false);
                ivPub.setImageResource(R.drawable.pub_false);
                ivIssue.setImageResource(R.drawable.issue_bg);
                ivMessage.setImageResource(R.drawable.message_true);
                ivMine.setImageResource(R.drawable.mine_false);
                tvHome.setTextColor(getResources().getColor(R.color.textcolor_tab));
                tvPub.setTextColor(getResources().getColor(R.color.textcolor_tab));
                tvIssue.setTextColor(getResources().getColor(R.color.textcolor_tab));
                tvMessage.setTextColor(getResources().getColor(R.color.colorPrimary));
                tvMine.setTextColor(getResources().getColor(R.color.textcolor_tab));
                break;
            case R.id.ll_mine:
                if (!MyApplication.isLogin()) {
                    startActivity(new Intent(this, SignInActivity.class));
                    return;
                }
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                }
                addFragments(mineFragment);
                ivHome.setImageResource(R.drawable.home_false);
                ivPub.setImageResource(R.drawable.pub_false);
                ivIssue.setImageResource(R.drawable.issue_bg);
                ivMessage.setImageResource(R.drawable.message_false);
                ivMine.setImageResource(R.drawable.mine_true);
                tvHome.setTextColor(getResources().getColor(R.color.textcolor_tab));
                tvPub.setTextColor(getResources().getColor(R.color.textcolor_tab));
                tvIssue.setTextColor(getResources().getColor(R.color.textcolor_tab));
                tvMessage.setTextColor(getResources().getColor(R.color.textcolor_tab));
                tvMine.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
        }
    }

    private void showIssueDialog() {
        mBuilder = new BaseDialog.Builder(this);
        mDialog = mBuilder.setViewId(R.layout.dialog_issue)
                //设置dialogpadding
                .setPaddingdp(20, 0, 20, 30)
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

        RecyclerView issueRecycler = mDialog.getView(R.id.issue_recycler);
        issueRecycler.setLayoutManager(new GridLayoutManager(this, 3));
        issueRecycler.setNestedScrollingEnabled(false);
        IssueAdapter issueAdapter = new IssueAdapter(R.layout.item_issue_layout, list);
        issueRecycler.setAdapter(issueAdapter);
        issueAdapter.setOnItemChildClickListener(this);
        mDialog.show();
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        mDialog.dismiss();
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
                Intent intent = new Intent(MainActivity.this, PubQitaActivity.class);
                intent.putExtra("TaskType", "6");
                intent.putExtra("ChildTaskType", "1");
                startActivity(intent);
                break;
        }
    }

    private void showNextIssueDialog(int left, int right, int spanCount, final int pubType) {
        mBuilder = new BaseDialog.Builder(this);
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
        issueNextRecycler.setLayoutManager(new GridLayoutManager(this, spanCount));
        issueNextRecycler.setNestedScrollingEnabled(false);
        IssueNextAdapter issueNextAdapter = new IssueNextAdapter(R.layout.item_issue_layout, nextList);
        issueNextRecycler.setAdapter(issueNextAdapter);
        issueNextAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (pubType) {
                    case 101://跑腿发布页面
                        mDialog.dismiss();
                        Intent intent = new Intent(MainActivity.this, PubPaotuiActivity.class);
                        intent.putExtra("TaskType", "1");
                        if (position == 0) {
                            intent.putExtra("ChildTaskType", "1");
                        } else if (position == 1) {
                            intent.putExtra("ChildTaskType", "2");
                        }
                        startActivity(intent);
                        break;
                    case 102://生活发布页
                        mDialog.dismiss();
                        Intent intent1 = new Intent(MainActivity.this, PubShenghuoActivity.class);
                        intent1.putExtra("TaskType", "2");
                        if (position == 0) {
                            intent1.putExtra("ChildTaskType", "1");
                        } else if (position == 1) {
                            intent1.putExtra("ChildTaskType", "2");
                        } else if (position == 2) {
                            intent1.putExtra("ChildTaskType", "3");
                        } else if (position == 3) {
                            intent1.putExtra("ChildTaskType", "4");
                        } else if (position == 4) {
                            intent1.putExtra("ChildTaskType", "5");
                        }
                        startActivity(intent1);
                        break;
                    case 103://个人定制发布页面
                        mDialog.dismiss();
                        Intent intent2 = new Intent(MainActivity.this, PubGerendingzhiActivity.class);
                        intent2.putExtra("TaskType", "3");
                        if (position == 0) {
                            intent2.putExtra("ChildTaskType", "1");
                        } else if (position == 1) {
                            intent2.putExtra("ChildTaskType", "2");
                        }
                        startActivity(intent2);
                        break;
                    case 104://工作发布页
                        mDialog.dismiss();
                        Intent intent3 = new Intent(MainActivity.this, PubGongzuoActivity.class);
                        intent3.putExtra("TaskType", "4");
                        if (position == 0) {
                            intent3.putExtra("ChildTaskType", "1");
                        } else if (position == 1) {
                            intent3.putExtra("ChildTaskType", "2");
                        } else if (position == 2) {
                            intent3.putExtra("ChildTaskType", "3");
                        } else if (position == 3) {
                            intent3.putExtra("ChildTaskType", "4");
                        } else if (position == 4) {
                            intent3.putExtra("ChildTaskType", "5");
                        }
                        startActivity(intent3);
                        break;
                    case 105://健康发布页面
                        mDialog.dismiss();
                        Intent intent4 = new Intent(MainActivity.this, PubJiankangActivity.class);
                        intent4.putExtra("TaskType", "5");
                        if (position == 0) {
                            intent4.putExtra("ChildTaskType", "1");
                        } else if (position == 1) {
                            intent4.putExtra("ChildTaskType", "2");
                        } else if (position == 2) {
                            intent4.putExtra("ChildTaskType", "3");
                        }
                        startActivity(intent4);
                        break;
                }
            }
        });
        mDialog.show();
    }

    private class IssueAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public IssueAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.item_type_tv, item)
                    .addOnClickListener(R.id.item_type_tv);
        }
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRecevierEvent(EventUtils event) {
        switch (event.getType()) {
            case EventConstants.JUPMP_TO_MAIN:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                }
                addFragments(homeFragment);
                ivHome.setImageResource(R.drawable.home_true);
                ivPub.setImageResource(R.drawable.pub_false);
                ivIssue.setImageResource(R.drawable.issue_bg);
                ivMessage.setImageResource(R.drawable.message_false);
                ivMine.setImageResource(R.drawable.mine_false);
                tvHome.setTextColor(getResources().getColor(R.color.colorPrimary));
                tvPub.setTextColor(getResources().getColor(R.color.textcolor_tab));
                tvIssue.setTextColor(getResources().getColor(R.color.textcolor_tab));
                tvMessage.setTextColor(getResources().getColor(R.color.textcolor_tab));
                tvMine.setTextColor(getResources().getColor(R.color.textcolor_tab));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
