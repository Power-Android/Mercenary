package com.power.mercenary;

import android.app.AlertDialog;
import android.app.Application;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.power.mercenary.activity.PubGerendingzhiActivity;
import com.power.mercenary.activity.PubGongzuoActivity;
import com.power.mercenary.activity.PubJiankangActivity;
import com.power.mercenary.activity.PubPaotuiActivity;
import com.power.mercenary.activity.PubQitaActivity;
import com.power.mercenary.activity.PubShenghuoActivity;
import com.power.mercenary.activity.SignInActivity;
import com.power.mercenary.base.BaseActivity;
import com.power.mercenary.base.BaseFragment;
import com.power.mercenary.bean.task.TaskDetailsBean;
import com.power.mercenary.data.EventConstants;
import com.power.mercenary.event.EventUtils;
import com.power.mercenary.fragment.HomeFragment;
import com.power.mercenary.fragment.MessageFragment;
import com.power.mercenary.fragment.MineFragment;
import com.power.mercenary.fragment.PubFragment;
import com.power.mercenary.presenter.TaskDetailsPresenter;
import com.power.mercenary.utils.SharedPreferencesUtils;
import com.power.mercenary.utils.ShearUtils;
import com.power.mercenary.view.BaseDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imlib.RongIMClient;

public class MainActivity extends BaseActivity implements View.OnClickListener {

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
    private List<String> datas;
    private List<List<String>> list1;
    private List<String> nextList = new ArrayList<>();
    private int PAOTUI = 101, SHENGHUO = 102, GERENDINGZHI = 103, GONGZUO = 104, JIANKANG = 105;
    private OptionsPickerView pvCustomOptions;
    private AlertDialog alertDialog;

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
            case R.id.ll_issue:
                if (!MyApplication.isLogin()) {
                    startActivity(new Intent(this, SignInActivity.class));
                    return;
                }
                showIssueDialog();
                break;
        }
    }

    private void showIssueDialog() {
        mBuilder = new BaseDialog.Builder(this);
        mDialog = mBuilder.setViewId(R.layout.dialog_issue)
                //设置dialogpadding
                .setPaddingdp(0, 0, 0, 0)
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
        mDialog.getView(R.id.layout_paotui).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPaotuiDialog();
                mDialog.dismiss();
            }
        });
        mDialog.getView(R.id.layout_shenghuo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showShenghuoDialog();
                mDialog.dismiss();
            }
        });
        mDialog.getView(R.id.layout_geren).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showGerenDialog();
                mDialog.dismiss();
            }
        });
        mDialog.getView(R.id.layout_gongzuo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWorkDialog();
                mDialog.dismiss();
            }
        });
        mDialog.getView(R.id.layout_jiankang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showJiankangDialog();
                mDialog.dismiss();
            }
        });
        mDialog.getView(R.id.layout_qita).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PubQitaActivity.class);
                intent.putExtra("TaskType", "6");
                intent.putExtra("ChildTaskType", "1");
                startActivity(intent);
                mDialog.dismiss();
            }
        });
        mDialog.show();
    }

    //健康
    private void showJiankangDialog() {
        final BaseDialog dialog = mBuilder.setViewId(R.layout.dialog_pub_jiankang)
                //设置dialogpadding
                .setPaddingdp(0, 0, 0, 0)
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
        dialog.getView(R.id.layout_paotui).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(MainActivity.this, PubJiankangActivity.class);
                intent4.putExtra("TaskType", "5");
                intent4.putExtra("ChildTaskType", "17");
                startActivity(intent4);
                dialog.dismiss();
            }
        });
        dialog.getView(R.id.layout_shenghuo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(MainActivity.this, PubJiankangActivity.class);
                intent4.putExtra("TaskType", "5");
                intent4.putExtra("ChildTaskType", "16");
                startActivity(intent4);
                dialog.dismiss();
            }
        });
        dialog.getView(R.id.layout_jianfei).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(MainActivity.this, PubJiankangActivity.class);
                intent4.putExtra("TaskType", "5");
                intent4.putExtra("ChildTaskType", "15");
                startActivity(intent4);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    //工作
    private void showWorkDialog() {
        final BaseDialog dialog = mBuilder.setViewId(R.layout.dialog_pub_work)
                //设置dialogpadding
                .setPaddingdp(0, 0, 0, 0)
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
        dialog.getView(R.id.layout_paotui).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(MainActivity.this, PubGongzuoActivity.class);
                intent3.putExtra("TaskType", "4");
                intent3.putExtra("ChildTaskType", "8");
                startActivity(intent3);
                dialog.dismiss();
            }
        });
        dialog.getView(R.id.layout_shenghuo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(MainActivity.this, PubGongzuoActivity.class);
                intent3.putExtra("TaskType", "4");
                intent3.putExtra("ChildTaskType", "9");
                startActivity(intent3);
                dialog.dismiss();
            }
        });
        dialog.getView(R.id.layout_geren).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(MainActivity.this, PubGongzuoActivity.class);
                intent3.putExtra("TaskType", "4");
                intent3.putExtra("ChildTaskType", "10");
                startActivity(intent3);
                dialog.dismiss();
            }
        });
        dialog.getView(R.id.layout_gongzuo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(MainActivity.this, PubGongzuoActivity.class);
                intent3.putExtra("TaskType", "4");
                intent3.putExtra("ChildTaskType", "11");
                startActivity(intent3);
                dialog.dismiss();
            }
        });
        dialog.getView(R.id.layout_jiankang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(MainActivity.this, PubGongzuoActivity.class);
                intent3.putExtra("TaskType", "4");
                intent3.putExtra("ChildTaskType", "12");
                startActivity(intent3);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    //个人定制
    private void showGerenDialog() {
        final BaseDialog dialog = mBuilder.setViewId(R.layout.dialog_pub_geren)
                //设置dialogpadding
                .setPaddingdp(0, 0, 0, 0)
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
        dialog.getView(R.id.layout_paotui).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivity.this, PubGerendingzhiActivity.class);
                intent2.putExtra("TaskType", "3");
                intent2.putExtra("ChildTaskType", "13");
                startActivity(intent2);
                dialog.dismiss();
            }
        });
        dialog.getView(R.id.layout_shenghuo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivity.this, PubGerendingzhiActivity.class);
                intent2.putExtra("TaskType", "3");
                intent2.putExtra("ChildTaskType", "14");
                startActivity(intent2);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    //跑腿
    private void showPaotuiDialog() {
        final BaseDialog dialog = mBuilder.setViewId(R.layout.dialog_pub_paotui)
                //设置dialogpadding
                .setPaddingdp(0, 0, 0, 0)
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
        dialog.getView(R.id.layout_paotui).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PubPaotuiActivity.class);
                intent.putExtra("TaskType", "1");
                intent.putExtra("ChildTaskType", "1");
                startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.getView(R.id.layout_shenghuo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "接送人暂未开放", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(MainActivity.this, PubPaotuiActivity.class);
//                intent.putExtra("TaskType", "1");
//                intent.putExtra("ChildTaskType", "2");
//                startActivity(intent);
//                dialog.dismiss();
            }
        });

        dialog.show();
    }


    //生活
    private void showShenghuoDialog() {
        final BaseDialog dialog = mBuilder.setViewId(R.layout.dialog_pub_shenghuo)
                //设置dialogpadding
                .setPaddingdp(0, 0, 0, 0)
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
        dialog.getView(R.id.layout_paotui).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this, PubShenghuoActivity.class);
                intent1.putExtra("TaskType", "2");
                intent1.putExtra("ChildTaskType", "3");
                startActivity(intent1);
                dialog.dismiss();
            }
        });
        dialog.getView(R.id.layout_shenghuo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this, PubShenghuoActivity.class);
                intent1.putExtra("TaskType", "2");
                intent1.putExtra("ChildTaskType", "4");
                startActivity(intent1);
                dialog.dismiss();
            }
        });
        dialog.getView(R.id.layout_geren).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this, PubShenghuoActivity.class);
                intent1.putExtra("TaskType", "2");
                intent1.putExtra("ChildTaskType", "5");
                startActivity(intent1);
                dialog.dismiss();
            }
        });
        dialog.getView(R.id.layout_gongzuo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this, PubShenghuoActivity.class);
                intent1.putExtra("TaskType", "2");
                intent1.putExtra("ChildTaskType", "6");
                startActivity(intent1);
                dialog.dismiss();
            }
        });
        dialog.getView(R.id.layout_jiankang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this, PubShenghuoActivity.class);
                intent1.putExtra("TaskType", "2");
                intent1.putExtra("ChildTaskType", "7");
                startActivity(intent1);
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    private void ShowPickerView() {
        list = new ArrayList<>();
        list.add("跑腿");
        list.add("生活");
        list.add("个人定制");
        list.add("工作");
        list.add("健康");
        list.add("其他");
        list1 = new ArrayList<>();
        datas = new ArrayList<>();
        datas.add("物品");
        datas.add("人员");
        list1.add(datas);
        datas = new ArrayList<>();
        datas.add("衣");
        datas.add("食");
        datas.add("住");
        datas.add("行");
        datas.add("游");
        list1.add(datas);
        datas = new ArrayList<>();
        datas.add("硬件");
        datas.add("软件");
        list1.add(datas);
        datas = new ArrayList<>();
        datas.add("仕");
        datas.add("农");
        datas.add("工");
        datas.add("商");
        datas.add("律");
        list1.add(datas);
        datas = new ArrayList<>();
        datas.add("心理");
        datas.add("健身");
        datas.add("减肥");
        list1.add(datas);
        datas = new ArrayList<>();
        datas.add("");
        list1.add(datas);
        initCustomOptionPicker(list, list1);
        pvCustomOptions.show();
    }

    private void initCustomOptionPicker(final List<String> data, final List<List<String>> data1) {
        pvCustomOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = data.get(options1);
                if (tx.equals("跑腿")) {
                    Intent intent = new Intent(MainActivity.this, PubPaotuiActivity.class);
                    intent.putExtra("TaskType", "1");
                    if (option2 == 0) {
                        intent.putExtra("ChildTaskType", "1");
                    } else if (option2 == 1) {
                        intent.putExtra("ChildTaskType", "2");
                    }
                    startActivity(intent);
                } else if (tx.equals("生活")) {
                    Intent intent1 = new Intent(MainActivity.this, PubShenghuoActivity.class);
                    intent1.putExtra("TaskType", "2");
                    if (option2 == 0) {
                        intent1.putExtra("ChildTaskType", "1");
                    } else if (option2 == 1) {
                        intent1.putExtra("ChildTaskType", "2");
                    } else if (option2 == 2) {
                        intent1.putExtra("ChildTaskType", "3");
                    } else if (option2 == 3) {
                        intent1.putExtra("ChildTaskType", "4");
                    } else if (option2 == 4) {
                        intent1.putExtra("ChildTaskType", "5");
                    }
                    startActivity(intent1);
                } else if (tx.equals("个人定制")) {
                    Intent intent2 = new Intent(MainActivity.this, PubGerendingzhiActivity.class);
                    intent2.putExtra("TaskType", "3");
                    if (option2 == 0) {
                        intent2.putExtra("ChildTaskType", "1");
                    } else if (option2 == 1) {
                        intent2.putExtra("ChildTaskType", "2");
                    }
                    startActivity(intent2);
                } else if (tx.equals("工作")) {


                    Intent intent3 = new Intent(MainActivity.this, PubGongzuoActivity.class);
                    intent3.putExtra("TaskType", "4");
                    if (option2 == 0) {
                        intent3.putExtra("ChildTaskType", "1");
                    } else if (option2 == 1) {
                        intent3.putExtra("ChildTaskType", "2");
                    } else if (option2 == 2) {
                        intent3.putExtra("ChildTaskType", "3");
                    } else if (option2 == 3) {
                        intent3.putExtra("ChildTaskType", "4");
                    } else if (option2 == 4) {
                        intent3.putExtra("ChildTaskType", "5");
                    }
                    startActivity(intent3);
                } else if (tx.equals("健康")) {
                    Intent intent4 = new Intent(MainActivity.this, PubJiankangActivity.class);
                    intent4.putExtra("TaskType", "5");
                    if (option2 == 0) {
                        intent4.putExtra("ChildTaskType", "1");
                    } else if (option2 == 1) {
                        intent4.putExtra("ChildTaskType", "2");
                    } else if (option2 == 2) {
                        intent4.putExtra("ChildTaskType", "3");
                    }
                    startActivity(intent4);
                } else if (tx.equals("其他")) {
                    Intent intent = new Intent(MainActivity.this, PubQitaActivity.class);
                    intent.putExtra("TaskType", "6");
                    intent.putExtra("ChildTaskType", "1");
                    startActivity(intent);
                }
//                fkTv.setText(tx);
            }
        })
                .setLayoutRes(R.layout.pickerview_custom_options, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        final TextView tvCancle = (TextView) v.findViewById(R.id.tv_cancle);

                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptions.returnData();
                                pvCustomOptions.dismiss();
                            }
                        });
                        tvCancle.setVisibility(View.GONE);
                    }
                })
                .setSelectOptions(0)//默认选中项
                .setContentTextSize(18)//设置滚轮文字大小
                .setBgColor(getResources().getColor(R.color.concrete))
                .setTextColorOut(getResources().getColor(R.color.textColorDrak))
                .setDividerColor(getResources().getColor(R.color.textColorDrak))
                .setTextColorCenter(getResources().getColor(R.color.black)) //设置选中项文字颜色
                .build();
        pvCustomOptions.setPicker(data, data1);//添加数据

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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {//获取用户是否点击了返回键
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        //弹出提示框
        alertExitDialog();
    }

    private void alertExitDialog() {
        BaseDialog.Builder builder = new BaseDialog.Builder(this);
        mDialog = builder.setViewId(R.layout.dialog_shenhe)
                //设置dialogpadding
                .setPaddingdp(0, 0, 0, 0)
                //设置显示位置
                .setGravity(Gravity.CENTER)
                //设置动画
                //设置dialog的宽高
                .setWidthHeightpx(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                //设置触摸dialog外围是否关闭
                .isOnTouchCanceled(true)
                //设置监听事件
                .builder();
        TextView hint_tv = mDialog.getView(R.id.hint_tv);
        TextView tv_sure = mDialog.getView(R.id.tv_sure);
        TextView tv_cancle = mDialog.getView(R.id.tv_cancle);
        hint_tv.setText("您确定要退出吗？");
        tv_sure.setOnClickListener(this);
        tv_cancle.setOnClickListener(this);
        mDialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sure:
                finish();
                System.exit(0);
            case R.id.tv_cancle:
                mDialog.dismiss();

                break;
        }
    }
}