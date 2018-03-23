package com.power.mercenary;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.power.mercenary.base.BaseActivity;
import com.power.mercenary.base.BaseFragment;
import com.power.mercenary.fragment.HomeFragment;
import com.power.mercenary.fragment.MessageFragment;
import com.power.mercenary.fragment.MineFragment;
import com.power.mercenary.fragment.PubFragment;
import com.power.mercenary.utils.TUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        homeFragment = new HomeFragment();
        addFragments(homeFragment);
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
                if (homeFragment == null){
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
                if (pubFragment == null){
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
                TUtils.showShort(mContext,"点击了---发布");
                break;
            case R.id.ll_message:
                if (messageFragment == null){
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
                if (mineFragment == null){
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
}
