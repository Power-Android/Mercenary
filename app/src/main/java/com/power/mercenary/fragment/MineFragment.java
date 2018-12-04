package com.power.mercenary.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzy.okgo.model.Response;
import com.power.mercenary.MyApplication;
import com.power.mercenary.R;
import com.power.mercenary.activity.AcceptTaskActivity;
import com.power.mercenary.activity.MyAchievementActivity;
import com.power.mercenary.activity.MyCollection;
import com.power.mercenary.activity.MyExtensionActivity;
import com.power.mercenary.activity.MyQualificationsActivity;
import com.power.mercenary.activity.MyValueActivity;
import com.power.mercenary.activity.ReleaseTaskActivity;
import com.power.mercenary.activity.SetupActivity;
import com.power.mercenary.activity.ShouzeActivity;
import com.power.mercenary.activity.TaskStatisticsActivity;
import com.power.mercenary.base.BaseFragment;
import com.power.mercenary.bean.AcceptBean;
import com.power.mercenary.bean.PublishBean;
import com.power.mercenary.bean.user.UserInfo;
import com.power.mercenary.data.CacheConstants;
import com.power.mercenary.data.EventConstants;
import com.power.mercenary.event.EventUtils;
import com.power.mercenary.http.HttpManager;
import com.power.mercenary.http.JsonCallback;
import com.power.mercenary.http.ResponseBean;
import com.power.mercenary.presenter.UserPresenter;
import com.power.mercenary.utils.CacheUtils;
import com.power.mercenary.utils.Urls;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by power on 2018/3/21.
 */

public class MineFragment extends BaseFragment implements UserPresenter.UserCallBack {
    @BindView(R.id.tv_sz)
    TextView tvSz;
    @BindView(R.id.frag_mine_money)
    TextView money;
    @BindView(R.id.ll_wdjz)
    LinearLayout ll_wdjz;
    @BindView(R.id.rl_wdgz)
    RelativeLayout rl_wdgz;
    @BindView(R.id.rl_wdsc)
    RelativeLayout rl_wdsc;
    @BindView(R.id.rl_wdtg)
    RelativeLayout rl_wdtg;
    @BindView(R.id.rl_wdzl)
    RelativeLayout rl_wdzl;
    @BindView(R.id.tv_jsrw)
    TextView tv_jsrw;
    @BindView(R.id.tv_fbrw)
    TextView tv_fbrw;
    @BindView(R.id.rl_cj)
    RelativeLayout rl_cj;
    @BindView(R.id.ll_wjd)
    LinearLayout ll_wjd;
    @BindView(R.id.ll_rwz)
    LinearLayout ll_rwz;
    @BindView(R.id.ll_shz)
    LinearLayout ll_shz;
    @BindView(R.id.ll_dpj)
    LinearLayout ll_dpj;
    @BindView(R.id.frag_mine_ybm)
    LinearLayout fragMineYbm;
    @BindView(R.id.frag_mine_rwz)
    LinearLayout fragMineRwz;
    @BindView(R.id.frag_mine_shz)
    LinearLayout fragMineShz;
    @BindView(R.id.frag_mine_ywc)
    LinearLayout fragMineYwc;
    Unbinder unbinder;
    @BindView(R.id.mine_accept_wjd)
    TextView acceptWjd;
    @BindView(R.id.mine_accept_rwz)
    TextView acceptRwz;
    @BindView(R.id.mine_accept_shz)
    TextView acceptShz;
    @BindView(R.id.mine_accept_dpj)
    TextView acceptDpj;
    @BindView(R.id.mine_publish_ybm)
    TextView publishYbm;
    @BindView(R.id.mine_publish_rwz)
    TextView publishRwz;
    @BindView(R.id.mine_publish_shz)
    TextView publishShz;
    @BindView(R.id.mine_publish_ywc)
    TextView publishYwc;

    private UserPresenter userPresenter;

    private ImageView icon;
    private TextView name;
    private String isagree;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, null);

        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        icon = view.findViewById(R.id.frag_mine_icon);
        name = view.findViewById(R.id.frag_mine_name);

        userPresenter = new UserPresenter(getActivity(), this);
        userPresenter.getUserInfo();

        tvSz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SetupActivity.class);
                startActivity(intent);
            }
        });

        ll_wdjz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), MyValueActivity.class);
                startActivity(intent);

            }
        });

        initAccept();
        initPublish();

//        rl_wdgz.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), MyFollowActivity.class);
//                startActivity(intent);
//            }
//        });

        rl_wdsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), MyCollection.class);
                startActivity(intent);

            }
        });

        rl_wdtg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isagree.equals("0")) {
                    Intent intent = new Intent(getActivity(), ShouzeActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), MyExtensionActivity.class);
                    startActivity(intent);
                }
            }
        });

        rl_wdzl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(getActivity(), MyQualificationsActivity.class);
                startActivity(intent);

            }
        });
        tv_jsrw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), TaskStatisticsActivity.class);
                intent.putExtra("state", 1);
                startActivity(intent);

            }
        });
        tv_fbrw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TaskStatisticsActivity.class);
                intent.putExtra("state", 2);
                startActivity(intent);
            }
        });
        rl_cj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), MyAchievementActivity.class);
                startActivity(intent);

            }
        });
        ll_wjd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ReleaseTaskActivity.class);
                intent.putExtra("position", 1);
                startActivity(intent);
            }
        });

        ll_rwz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ReleaseTaskActivity.class);
                intent.putExtra("position", 2);
                startActivity(intent);
            }
        });
        ll_shz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ReleaseTaskActivity.class);
                intent.putExtra("position", 3);
                startActivity(intent);
            }
        });

        ll_dpj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ReleaseTaskActivity.class);
                intent.putExtra("position", 4);
                startActivity(intent);
            }
        });

        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SetupActivity.class);
//                Intent intent = new Intent(getActivity(), CommitUserInfoActivity.class);

                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    protected void initLazyData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        unbinder.unbind();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRecevierEvent(EventUtils event) {
        switch (event.getType()) {
            case EventConstants.TYPE_USERINFO:
                //刷新用户信息
                userPresenter.getUserInfo();
                initAccept();
                initPublish();
                break;
        }
    }

    private void initPublish() {
        new HttpManager<ResponseBean<PublishBean>>("Home/MyTask/fabu_num", this)
                .addParams("token", MyApplication.getUserToken())
                .postRequest(new JsonCallback<ResponseBean<PublishBean>>() {
                    @Override
                    public void onSuccess(Response<ResponseBean<PublishBean>> response) {
                        if (response != null && response.body() != null && response.body().data != null) {
                            if (response.body().data.getRwz() > 0) {
                                acceptRwz.setText(response.body().data.getRwz() + "");
                                acceptRwz.setVisibility(View.VISIBLE);
                            } else {
                                acceptRwz.setVisibility(View.GONE);
                            }

                            if (response.body().data.getShz() > 0) {
                                acceptShz.setText(response.body().data.getShz() + "");
                                acceptShz.setVisibility(View.VISIBLE);
                            } else {
                                acceptShz.setVisibility(View.GONE);
                            }

                            if (response.body().data.getDpj() > 0) {
                                acceptDpj.setText(response.body().data.getDpj() + "");
                                acceptDpj.setVisibility(View.VISIBLE);
                            } else {
                                acceptDpj.setVisibility(View.GONE);
                            }

                            if (response.body().data.getWjd() > 0) {
                                acceptWjd.setText(response.body().data.getWjd() + "");
                                acceptWjd.setVisibility(View.VISIBLE);
                            } else {
                                acceptWjd.setVisibility(View.GONE);
                            }
                        }
                    }
                });
    }

    private void initAccept() {
        new HttpManager<ResponseBean<AcceptBean>>("Home/MyTask/jieshou_num", this)
                .addParams("token", MyApplication.getUserToken())
                .postRequest(new JsonCallback<ResponseBean<AcceptBean>>() {
                    @Override
                    public void onSuccess(Response<ResponseBean<AcceptBean>> response) {
                        if (response != null && response.body() != null && response.body().data != null) {
                            if (response.body().data.getRwz() > 0) {
                                publishRwz.setText(response.body().data.getRwz() + "");
                                publishRwz.setVisibility(View.VISIBLE);
                            } else {
                                publishRwz.setVisibility(View.GONE);
                            }

                            if (response.body().data.getShz() > 0) {
                                publishShz.setText(response.body().data.getShz() + "");
                                publishShz.setVisibility(View.VISIBLE);
                            } else {
                                publishShz.setVisibility(View.GONE);
                            }

                            if (response.body().data.getYbm() > 0) {
                                publishYbm.setText(response.body().data.getYbm() + "");
                                publishYbm.setVisibility(View.VISIBLE);
                            } else {
                                publishYbm.setVisibility(View.GONE);
                            }

                            if (response.body().data.getYwc() > 0) {
                                publishYwc.setText(response.body().data.getYwc() + "");
                                publishYwc.setVisibility(View.VISIBLE);
                            } else {
                                publishYwc.setVisibility(View.GONE);
                            }
                        }
                    }
                });
    }

    @Override
    public void getUserInfo(UserInfo userInfo) {
        if (userInfo != null) {
            CacheUtils.put(CacheConstants.USERINFO, userInfo);

            if (!TextUtils.isEmpty(userInfo.getHead_img()) && !TextUtils.isEmpty(userInfo.getNick_name()) &&
                    !TextUtils.isEmpty(userInfo.money)
                    ) {

                //加载用户的头像
                Glide.with(mContext)
                        .load(Urls.BASEIMGURL + userInfo.getHead_img())
                        .into(icon);

                //加载用户的名字
                name.setText(userInfo.getNick_name());

                //加载用户的价值(money)
                money.setText(userInfo.money);

                isagree = userInfo.isagree;

            } else {

            }

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick({R.id.frag_mine_ybm, R.id.frag_mine_rwz, R.id.frag_mine_shz, R.id.frag_mine_ywc})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.frag_mine_ybm:
                Intent intent = new Intent(getActivity(), AcceptTaskActivity.class);
                intent.putExtra("position", 1);
                startActivity(intent);
                break;
            case R.id.frag_mine_rwz:
                Intent intent1 = new Intent(getActivity(), AcceptTaskActivity.class);
                intent1.putExtra("position", 2);
                startActivity(intent1);
                break;
            case R.id.frag_mine_shz:
                Intent intent2 = new Intent(getActivity(), AcceptTaskActivity.class);
                intent2.putExtra("position", 3);
                startActivity(intent2);
                break;
            case R.id.frag_mine_ywc:
                Intent intent3 = new Intent(getActivity(), AcceptTaskActivity.class);
                intent3.putExtra("position", 4);
                startActivity(intent3);
                break;
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //刷新用户消息
        userPresenter.getUserInfo();

    }

    @Override
    public void onResume() {
        super.onResume();



    }
}
