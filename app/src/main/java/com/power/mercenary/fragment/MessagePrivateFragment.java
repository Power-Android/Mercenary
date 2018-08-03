package com.power.mercenary.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.power.mercenary.MyApplication;
import com.power.mercenary.R;
import com.power.mercenary.activity.chat.ChatActivity;
import com.power.mercenary.adapter.message.MessagePrivateAdapter;
import com.power.mercenary.base.BaseFragment;
import com.power.mercenary.bean.MsgPrivateBean;
import com.power.mercenary.data.EventConstants;
import com.power.mercenary.event.EventUtils;
import com.power.mercenary.presenter.MessagePresenter;
import com.power.mercenary.utils.Urls;
import com.power.mercenary.view.pullrecyclerview.WanRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * admin  2018/7/23 wan
 */
public class MessagePrivateFragment extends BaseFragment implements WanRecyclerView.PullRecyclerViewCallBack, MessagePrivateAdapter.OnItemClickListener, MessagePresenter.MessageCallBack {

    private MessagePrivateAdapter adapter;

    private WanRecyclerView mRecyclerView;

    private MessagePresenter presenter;

    private List<MsgPrivateBean> lists;

    private int page = 1;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_message_list, null);

        mRecyclerView = view.findViewById(R.id.frag_messageList_recyclerView);
        mRecyclerView.setLinearLayout();
        mRecyclerView.setPullRecyclerViewListener(this);

        lists = new ArrayList<>();
        adapter = new MessagePrivateAdapter(getContext(), lists);
        adapter.setOnItemClickListener(this);

        mRecyclerView.setAdapter(adapter);

        presenter = new MessagePresenter(getActivity(), this);
        presenter.getMessagePrivateList(page);

        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    protected void initLazyData() {

    }

    @Override
    public void onRefresh() {
        page = 1;
        lists.clear();
        adapter.notifyDataSetChanged();
        presenter.getMessagePrivateList(page);
    }

    @Override
    public void onLoadMore() {
        page++;
        presenter.getMessagePrivateList(page);
    }

    @Override
    public void onItemClickListener(MsgPrivateBean msgPrivateBean) {
        String[] strings = {"18", "20", "21", "22", "23"};
        String[] imgUrl = {"http://imgsrc.baidu.com/imgad/pic/item/0df3d7ca7bcb0a466e41231d6163f6246b60afb7.jpg", "http://p1.qzone.la/upload/20150218/x5alew4n.jpg", "http://att.bbs.duowan.com/forum/201411/09/152152z6vxvha6kmkwokx6.jpg", "http://imgsrc.baidu.com/forum/pic/item/0b46f21fbe096b63c7397ec70c338744ebf8ac00.jpg", "https://img5.duitang.com/uploads/item/201506/14/20150614205903_85zCv.jpeg"};
        int i = (int) (Math.random() * strings.length);
        if (TextUtils.equals(msgPrivateBean.getFromuserid(), MyApplication.getUserId())) {
            ChatActivity.invoke(getActivity(), msgPrivateBean.getTouserid(), msgPrivateBean.getTouserhead_img(), msgPrivateBean.getTouser_name());
        } else {
            ChatActivity.invoke(getActivity(), msgPrivateBean.getFromuserid(), msgPrivateBean.getFromuserhead_img(), msgPrivateBean.getFromuser_name());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRecevierEvent(EventUtils event) {
        switch (event.getType()) {
            case EventConstants.TYPE_MESSAGE_SHOW:
                page = 1;
                lists.clear();
                adapter.notifyDataSetChanged();
                presenter.getMessagePrivateList(page);
                break;
        }
    }

    @Override
    public void getMessagePrivateList(List<MsgPrivateBean> data) {
        if (data != null) {
            lists.addAll(data);
            mRecyclerView.setHasMore(data.size(), 20);
        } else {
            mRecyclerView.setHasMore(0, 20);
        }
        adapter.notifyDataSetChanged();
        mRecyclerView.setStateView(lists.size());
    }

    @Override
    public void getMessagePrivateListFail() {
        mRecyclerView.setHasMore(0, 20);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
