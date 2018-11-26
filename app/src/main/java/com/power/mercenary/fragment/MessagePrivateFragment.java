package com.power.mercenary.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.power.mercenary.MyApplication;
import com.power.mercenary.R;
import com.power.mercenary.activity.chat.ChatActivity;
import com.power.mercenary.adapter.message.MessagePrivateAdapter;
import com.power.mercenary.base.BaseFragment;
import com.power.mercenary.bean.MsgPrivateBean;
import com.power.mercenary.data.EventConstants;
import com.power.mercenary.event.EventUtils;
import com.power.mercenary.presenter.MessagePresenter;
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

    private boolean isRefresh = false;

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
//        lists.clear();
//        adapter.notifyDataSetChanged();
        presenter.getMessagePrivateList(page);
    }

    @Override
    public void onLoadMore() {
        page++;
        presenter.getMessagePrivateList(page);
    }

    @Override
    public void onItemClickListener(MsgPrivateBean msgPrivateBean, int position) {
        presenter.setMessageState(msgPrivateBean.getId());
        String[] strings = {"18", "20", "21", "22", "23"};
        String[] imgUrl = {"http://imgsrc.baidu.com/imgad/pic/item/0df3d7ca7bcb0a466e41231d6163f6246b60afb7.jpg", "http://p1.qzone.la/upload/20150218/x5alew4n.jpg", "http://att.bbs.duowan.com/forum/201411/09/152152z6vxvha6kmkwokx6.jpg", "http://imgsrc.baidu.com/forum/pic/item/0b46f21fbe096b63c7397ec70c338744ebf8ac00.jpg", "https://img5.duitang.com/uploads/item/201506/14/20150614205903_85zCv.jpeg"};
        int i = (int) (Math.random() * strings.length);
        if (TextUtils.equals(msgPrivateBean.getFromuserid(), MyApplication.getUserId())) {
            ChatActivity.invoke(getActivity(), msgPrivateBean.getTouserid(), msgPrivateBean.getTouserhead_img(), msgPrivateBean.getTouser_name());
        } else {
            ChatActivity.invoke(getActivity(), msgPrivateBean.getFromuserid(), msgPrivateBean.getFromuserhead_img(), msgPrivateBean.getFromuser_name());
        }

        lists.get(position).setRead_status("1");
        adapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRecevierEvent(EventUtils event) {
        switch (event.getType()) {
//            case EventConstants.TYPE_MESSAGE_SHOW_MINE:
//                Message message = (Message) event.getData();
//                MessageContent messageContent = message.getContent();
//                MsgPrivateBean privateBean = null;
//                if (messageContent instanceof TextMessage) {
//                    TextMessage textContent = (TextMessage) messageContent;
//                    for (int i = 0; i < lists.size(); i++) {
//                        if (TextUtils.equals(lists.get(i).getTouserid(), message.getTargetId())) {
//                            String header = lists.get(i).getFromuserhead_img();
//                            String name = lists.get(i).getFromuser_name();
//                            String uid = lists.get(i).getFromuserid();
//                            lists.get(i).setFromuserid(lists.get(i).getTouserid());
//                            lists.get(i).setFromuser_name(lists.get(i).getTouser_name());
//                            lists.get(i).setFromuserhead_img(lists.get(i).getTouserhead_img());
//                            lists.get(i).setTouserid(uid);
//                            lists.get(i).setTouser_name(name);
//                            lists.get(i).setTouserhead_img(header);
//                            lists.get(i).setContent(textContent.getContent());
//                            lists.get(i).setRead_status("1");
//                            lists.get(i).setMsgtime(System.currentTimeMillis());
//                            privateBean = lists.get(i);
//                            lists.remove(i);
//                        } else if (TextUtils.equals(lists.get(i).getFromuserid(), message.getTargetId())) {
//                            lists.get(i).setContent(textContent.getContent());
//                            lists.get(i).setRead_status("1");
//                            privateBean = lists.get(i);
//                            lists.remove(i);
//                        }
//                    }
//                }
//                lists.add(0, privateBean);
//                adapter.notifyDataSetChanged();
//                break;

//            case EventConstants.TYPE_MESSAGE_SHOW_NULL:
//                String id = (String) event.getData();
//                for (int i = 0; i < lists.size(); i++) {
//                    if (TextUtils.equals(lists.get(i).getFromuserid(), id) || TextUtils.equals(lists.get(i).getTouserid(), id)) {
//                        lists.get(i).setContent("");
//                    }
//                }
//                adapter.notifyDataSetChanged();
//                break;
            case EventConstants.TYPE_MESSAGE_SHOW:
//                Message msg = (Message) event.getData();
//                MessageContent content = msg.getContent();
//                MsgPrivateBean msgPrivateBean = null;
//                if (content instanceof TextMessage) {
//                    TextMessage textMessage = (TextMessage) content;
//                    for (int i = 0; i < lists.size(); i++) {
//                        if (TextUtils.equals(lists.get(i).getTouserid(), msg.getTargetId())) {
//                            String header = lists.get(i).getFromuserhead_img();
//                            String name = lists.get(i).getFromuser_name();
//                            String uid = lists.get(i).getFromuserid();
//                            lists.get(i).setFromuserid(lists.get(i).getTouserid());
//                            lists.get(i).setFromuser_name(lists.get(i).getTouser_name());
//                            lists.get(i).setFromuserhead_img(lists.get(i).getTouserhead_img());
//                            lists.get(i).setTouserid(uid);
//                            lists.get(i).setTouser_name(name);
//                            lists.get(i).setTouserhead_img(header);
//                            lists.get(i).setContent(textMessage.getContent());
//                            lists.get(i).setRead_status("0");
//                            lists.get(i).setMsgtime(System.currentTimeMillis());
//                            msgPrivateBean = lists.get(i);
//                            lists.remove(i);
//                            lists.add(0, msgPrivateBean);
//                            adapter.notifyDataSetChanged();
//                            return;
//                        } else if (TextUtils.equals(lists.get(i).getFromuserid(), msg.getTargetId())) {
//                            lists.get(i).setContent(textMessage.getContent());
//                            lists.get(i).setRead_status("0");
//                            msgPrivateBean = lists.get(i);
//                            lists.remove(i);
//                            lists.add(0, msgPrivateBean);
//                            adapter.notifyDataSetChanged();
//                            return;
//                        }
//                    }
//                    if (!isRefresh) {
//                        page = 1;
//                        lists.clear();
//                        presenter.getMessagePrivateList(page);
//                        isRefresh = true;
//                    }
//                }
//                break;
            case EventConstants.TYPE_MESSAGE_SHOW_NULL:
            case EventConstants.TYPE_MESSAGE_SHOW_MINE:
            case EventConstants.TYPE_MESSAGE_SHOW_RESRESH:
                if (!isRefresh) {
                    page = 1;
//                    lists.clear();
//                    adapter.notifyDataSetChanged();
                    presenter.getMessagePrivateList(page);
                    isRefresh = true;
                }
                break;
        }
    }

    @Override
    public void getMessagePrivateList(List<MsgPrivateBean> data) {
        if (data != null) {
//            if (lists.size() == 0) {
            lists.clear();
            lists.addAll(data);
            adapter.notifyDataSetChanged();
            mRecyclerView.setHasMore(data.size(), 20);
//            }
//            else {
//                int index = 0;
//
//                for (int i = 0; i < data.size(); i++) {
//                    int isFind = 1;
////                    if (!TextUtils.equals(data.get(i).getFromuserid(), MyApplication.getUserId())) {
//                    for (int j = 0; j < lists.size(); j++) {
//                        if (TextUtils.equals(data.get(i).getFromuserid(), lists.get(j).getTouserid()) || TextUtils.equals(data.get(i).getFromuserid(), lists.get(j).getFromuserid())) {
//                            if (!TextUtils.equals(data.get(i).getContent(), lists.get(j).getContent())) {
//                                lists.remove(j);
//                                lists.add(index, data.get(i));
//                                index++;
//                                adapter.notifyDataSetChanged();
//                                break;
//                            }
//                        }
//                        isFind++;
//                    }
//
//                    if (isFind == lists.size()) {
//                        lists.add(index, data.get(i));
//                        index++;
//                        adapter.notifyDataSetChanged();
//                    }
////                    }
//                }
//                mRecyclerView.setHasMore(data.size(), 20);
//            }
        } else {
            lists.clear();
            adapter.notifyDataSetChanged();
            mRecyclerView.setHasMore(0, 20);
        }
        mRecyclerView.setStateView(lists.size());
        isRefresh = false;
    }

    @Override
    public void getMessagePrivateListFail() {
        mRecyclerView.setHasMore(0, 20);
        isRefresh = false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
