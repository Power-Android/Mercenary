package com.power.mercenary.activity.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.liaoinstan.springview.widget.SpringView;
import com.power.mercenary.R;
import com.power.mercenary.adapter.chat.ChatPushAdapter;
import com.power.mercenary.base.BaseActivity;
import com.power.mercenary.view.chatrefresh.ChatRefreshHeader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * admin  2018/7/26 wan
 */
public class ChatPushActivity extends BaseActivity implements SpringView.OnFreshListener {

    @BindView(R.id.left_back)
    FrameLayout leftBack;
    @BindView(R.id.title_text)
    TextView titleTv;
    @BindView(R.id.act_chat_msgList)
    ListView listView;
    @BindView(R.id.act_chat_springView)
    SpringView mSpringView;

    private ChatPushAdapter adapter;

    private List<String> mList;

    private String title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_push);
        ButterKnife.bind(this);

        title = getIntent().getStringExtra("title");

        titleTv.setText(title);

        mSpringView = (SpringView) findViewById(R.id.act_chat_springView);

        mSpringView.setListener(this);
        mSpringView.setType(SpringView.Type.FOLLOW);
        mSpringView.setHeader(new ChatRefreshHeader(mContext));

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

    public static void invoke(Context context, String title){
        Intent intent = new Intent(context, ChatPushActivity.class);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }
}
