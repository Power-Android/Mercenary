package com.power.mercenary.activity.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.liaoinstan.springview.widget.SpringView;
import com.power.mercenary.R;
import com.power.mercenary.adapter.chat.ChatPushAdapter;
import com.power.mercenary.base.BaseActivity;
import com.power.mercenary.bean.MsgDetailsBean;
import com.power.mercenary.presenter.MessageDetailsPresenter;
import com.power.mercenary.view.chatrefresh.ChatRefreshHeader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * admin  2018/7/26 wan
 */
public class ChatPushActivity extends BaseActivity implements SpringView.OnFreshListener, MessageDetailsPresenter.DetailsCallBack {

    @BindView(R.id.left_back)
    FrameLayout leftBack;
    @BindView(R.id.title_text)
    TextView titleTv;
    @BindView(R.id.act_chat_msgList)
    ListView listView;
    @BindView(R.id.act_chat_springView)
    SpringView mSpringView;

    private ChatPushAdapter adapter;

    private List<MsgDetailsBean> mList;

    private String title;
    private String msgId;
    private String msgType;
    private String tavernType;

    MessageDetailsPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        title = getIntent().getStringExtra("title");
        msgId = getIntent().getStringExtra("msgId");
        msgType = getIntent().getStringExtra("msgType");
        tavernType = getIntent().getStringExtra("tavernType");

        String type;

        if (!TextUtils.isEmpty(tavernType)) {
            if (TextUtils.equals(tavernType, "1")) {
                type = "msg";
            } else if (TextUtils.equals(tavernType, "2")){
                type = "reply";
            }
        }

        presenter = new MessageDetailsPresenter(this, this);
        presenter.getMessageDetails(msgType, tavernType, msgId);

        titleTv.setText(title);

        mSpringView = (SpringView) findViewById(R.id.act_chat_springView);

        mSpringView.setListener(this);
        mSpringView.setType(SpringView.Type.FOLLOW);
//        mSpringView.setHeader(new ChatRefreshHeader(mContext));

        mList = new ArrayList<>();
        adapter = new ChatPushAdapter(this, mList);
        listView.setAdapter(adapter);

    }

    @OnClick(R.id.left_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadmore() {

    }

    public static void invoke(Context context, String title, String msgId, String msgType, String tavernType){
        Intent intent = new Intent(context, ChatPushActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("msgId", msgId);
        intent.putExtra("msgType", msgType);
        intent.putExtra("tavernType", tavernType);
        context.startActivity(intent);
    }

    @Override
    public void getMessageDetails(MsgDetailsBean data) {
        if (data != null) {
            mList.add(data);
        }

        adapter.notifyDataSetChanged();
    }
}
