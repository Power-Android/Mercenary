package com.power.mercenary.activity.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.lzy.okgo.model.Response;
import com.power.mercenary.MainActivity;
import com.power.mercenary.MyApplication;
import com.power.mercenary.R;
import com.power.mercenary.adapter.chat.ChatMsgAdapter;
import com.power.mercenary.base.BaseActivity;
import com.power.mercenary.bean.ObtainUserInfo;
import com.power.mercenary.data.CacheConstants;
import com.power.mercenary.data.EventConstants;
import com.power.mercenary.event.EventUtils;
import com.power.mercenary.fragment.HomeFragment;
import com.power.mercenary.http.HttpManager;
import com.power.mercenary.http.JsonCallback;
import com.power.mercenary.http.ResponseBean;
import com.power.mercenary.presenter.ChatPresenter;
import com.power.mercenary.utils.CacheUtils;
import com.power.mercenary.utils.ShearUtils;
import com.power.mercenary.utils.SoftKeyboardTool;
import com.power.mercenary.utils.TUtils;
import com.power.mercenary.view.chatrefresh.ChatRefreshHeader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.message.TextMessage;

/**
 * admin  2018/7/23 wan
 */
public class ChatActivity extends BaseActivity implements SpringView.OnFreshListener, ChatPresenter.ChatCallBack {

    @BindView(R.id.title_text)
    TextView title;
    @BindView(R.id.act_chat_msgList)
    ListView listView;
    @BindView(R.id.act_chat_input)
    EditText input;
    @BindView(R.id.act_chat_send)
    TextView send;
    @BindView(R.id.act_chat_inputLayout)
    LinearLayout layout;

    private String userId;

    private ArrayList<Message> mList;

    private ChatMsgAdapter msgAdapter;
    private SpringView mSpringView;

    private long historyTime = 0;

    private int maxCount = 0;
    private ChatPresenter chatPresenter;

    private String imgUrls;

    private String name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        userId = getIntent().getStringExtra("userId");

        imgUrls = getIntent().getStringExtra("imgUrls");

        name = getIntent().getStringExtra("name");

        title.setText(name);
        CacheUtils.put(CacheConstants.IS_IN_CHAT, userId);

        chatPresenter = new ChatPresenter(this, this);
//        chatPresenter.getUserInfo(userId);

        mList = new ArrayList<>();
        msgAdapter = new ChatMsgAdapter(this, mList);
        listView.setAdapter(msgAdapter);

        mSpringView = (SpringView) findViewById(R.id.act_chat_springView);

        mSpringView.setListener(this);
        mSpringView.setType(SpringView.Type.FOLLOW);
        mSpringView.setHeader(new ChatRefreshHeader(mContext));

        obtainCache();

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView arg0, int arg1) {
                switch (arg1) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:// 空闲状态

                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:// 滚动状态关闭软键盘
                        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                                .hideSoftInputFromWindow(getCurrentFocus()
                                                .getWindowToken(),
                                        InputMethodManager.HIDE_NOT_ALWAYS);
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:// 触摸后滚动关闭软键盘
                        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                                .hideSoftInputFromWindow(getCurrentFocus()
                                                .getWindowToken(),
                                        InputMethodManager.HIDE_NOT_ALWAYS);
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
            }
        });

        EventBus.getDefault().register(this);
    }

    @OnClick({R.id.left_back, R.id.right_btn, R.id.act_chat_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                finish();
                break;
            case R.id.right_btn:
                Intent intent = new Intent(this, ChatDetailsActivity.class);
                intent.putParcelableArrayListExtra("messageList", mList);
                intent.putExtra("userId", userId);
                intent.putExtra("imgUrls", imgUrls);
                startActivity(intent);
                break;
            case R.id.act_chat_send:
                if (!TextUtils.isEmpty(input.getText().toString().trim())) {
                    sendMessAge(input.getText().toString(), userId);
                    input.setText("");
                } else {
                    TUtils.showCustom(this, "发送不能为空");
                }
                break;
        }
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRecevierEvent(EventUtils event) {
        switch (event.getType()) {
            case EventConstants.TYPE_MESSAGE_SELECT:
                listView.setSelection((int) event.getData());
                break;
            case EventConstants.TYPE_MESSAGE_IN_SHOW:
                mList.add((Message) event.getData());
                msgAdapter.notifyDataSetChanged();
                maxCount++;
                CacheUtils.put(CacheConstants.MESSAGEID, maxCount);
                break;
            case EventConstants.TYPE_REFRESH_ITEM:
                getHistoryList();
                break;
            case EventConstants.TYPE_CLEAR_ALL_HISTORY:
                mList.clear();
                msgAdapter.notifyDataSetChanged();
                break;
            case EventConstants.TYPE_MESSAGE_SHOW_NULL:
                finish();
                break;
        }
    }

    /**
     * 发送消息
     *
     * @param content
     * @param targetId
     */
    private void sendMessAge(final String content, String targetId) {

        TextMessage message = TextMessage.obtain(content);

        RongIMClient.getInstance().sendMessage(Conversation.ConversationType.PRIVATE, targetId, message, null, null, new IRongCallback.ISendMessageCallback() {
            @Override
            public void onAttached(Message message) {
                // 消息成功存到本地数据库的回调
                Log.v("======>>", "保存本地数据库");
            }

            @Override
            public void onSuccess(Message message) {
                // 消息发送成功的回调
                chatPresenter.addMessage(userId, content);
                mList.add(message);
                msgAdapter.notifyDataSetChanged();
                maxCount++;
                CacheUtils.put(CacheConstants.MESSAGEID, maxCount);
                Log.i("shujuku", "send user id" + message.getSenderUserId() + " user id " + message.getTargetId());
                EventBus.getDefault().post(new EventUtils(EventConstants.TYPE_MESSAGE_SHOW_MINE, message));
            }

            @Override
            public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                // 消息发送失败的回调
                String info = "";
                switch (errorCode.getValue()) {
                    case 31000:
                        info = "超时";
                        break;
                    case -1:
                        info = "未知错误";
                        break;
                    case 405:
                        info = "在黑名单中，无法向对方发送消息";
                        break;
                    case 21406:
                        info = "不在该讨论组中";
                        break;
                    case 22406:
                        info = "不在该群组中";
                        break;
                    case 23406:
                        info = "不在该聊天室中";
                        break;
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        RongIMClient.getInstance().getRemoteHistoryMessages(Conversation.ConversationType.PRIVATE, userId, historyTime, 20, new RongIMClient.ResultCallback<List<Message>>() {
            @Override
            public void onSuccess(List<Message> messages) {
                listView.setTranscriptMode(ListView.TRANSCRIPT_MODE_NORMAL);
                if (messages != null && messages.size() > 0) {
                    Collections.reverse(messages);
                    historyTime = messages.get(0).getSentTime();
                    mList.addAll(0, messages);
                    msgAdapter.notifyDataSetChanged();
                    listView.setSelection(0);
                }
                mSpringView.onFinishFreshAndLoad();
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.v("======>>", "getRemoteHistoryMessages" + errorCode.getMessage() + "----" + errorCode.getValue());
                mSpringView.onFinishFreshAndLoad();
            }
        });
    }


    private void getHistoryList() {
        RongIMClient.getInstance().getRemoteHistoryMessages(Conversation.ConversationType.PRIVATE, userId, historyTime, 40, new RongIMClient.ResultCallback<List<Message>>() {
            @Override
            public void onSuccess(List<Message> messages) {
                listView.setTranscriptMode(ListView.TRANSCRIPT_MODE_NORMAL);
                if (messages != null && messages.size() > 0) {
                    Collections.reverse(messages);
                    historyTime = messages.get(0).getSentTime();
                    mList.addAll(0, messages);
                    msgAdapter.notifyDataSetChanged();
                    listView.setSelection(0);
                    EventBus.getDefault().post(new EventUtils(EventConstants.TYPE_REFRESH_ITEM_SUCESS, messages.size()));
                } else {
                    EventBus.getDefault().post(new EventUtils(EventConstants.TYPE_REFRESH_ITEM_SUCESS, 0));
                    final Message msg = mList.get(mList.size() - 1);
                    MessageContent content = msg.getContent();
                    if (content instanceof TextMessage) {
                        TextMessage textMessage = (TextMessage) content;
                        new HttpManager<ResponseBean<Void>>("Home/YbTest/message_add", this)
                                .addParams("token", MyApplication.getUserToken())
                                .addParams("toUserId", userId)
                                .addParams("objectName", "TxtMsg")
                                .addParams("content", textMessage.getContent())
                                .postRequest(new JsonCallback<ResponseBean<Void>>() {
                                    @Override
                                    public void onSuccess(Response<ResponseBean<Void>> response) {
                                        EventBus.getDefault().post(new EventUtils(EventConstants.TYPE_MESSAGE_SHOW_RESRESH));
                                    }
                                });
                    }
                }
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.v("======>>", "getRemoteHistoryMessages" + errorCode.getMessage() + "----" + errorCode.getValue());

            }
        });
    }

    @Override
    public void onLoadmore() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void getUserInfo(ObtainUserInfo userInfo) {
        if (userInfo != null) {
            msgAdapter.setUserHeader(userInfo.getHead_img());
            title.setText(userInfo.getName());
            obtainCache();
        }
    }

    private void obtainCache() {
        int oldestMessageId = -1;

        if (CacheUtils.get(CacheConstants.MESSAGEID) != null) {
            maxCount = CacheUtils.get(CacheConstants.MESSAGEID);
            oldestMessageId = maxCount + 1;
            Log.v("======>>", "historyMessages -- count" + maxCount);
        }

        msgAdapter.setUserHeader(imgUrls);

        RongIMClient.getInstance().getHistoryMessages(Conversation.ConversationType.PRIVATE, userId, oldestMessageId, Integer.MAX_VALUE, new RongIMClient.ResultCallback<List<Message>>() {
            @Override
            public void onSuccess(List<Message> messages) {
                // 10 9 8 7 6 5 4 3 2 1
                if (messages != null && messages.size() > 0) {
                    Collections.reverse(messages);
                    historyTime = messages.get(0).getSentTime();
                    mList.addAll(messages);
                    msgAdapter.notifyDataSetChanged();
                    listView.setSelection(msgAdapter.getAdapterListSize());
                    Log.v("======>>", "historyMessages -- messages.size" + messages.size());
                    Log.v("======>>", "historyMessages -- messages.get(0).getMessageId" + messages.get(0).getMessageId());
                    Log.v("======>>", "historyMessages -- messages.get(messages.size() - 1).getMessageId" + messages.get(messages.size() - 1).getMessageId());
                } else {
                    onRefresh();
                }
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        CacheUtils.put(CacheConstants.IS_IN_CHAT, null);
    }

    public static void invoke(Context context, String userId, String imgUrls, String name) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra("userId", userId);
        intent.putExtra("imgUrls", imgUrls);
        intent.putExtra("name", name);
        context.startActivity(intent);
    }
}
