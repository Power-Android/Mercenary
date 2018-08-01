package com.power.mercenary.activity.chat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.power.mercenary.R;
import com.power.mercenary.adapter.chat.ChatSearchAdapter;
import com.power.mercenary.base.BaseActivity;
import com.power.mercenary.data.EventConstants;
import com.power.mercenary.event.EventUtils;
import com.power.mercenary.utils.TUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.message.TextMessage;

/**
 * admin  2018/7/25 wan
 */
public class ChatSearchActivity extends BaseActivity implements ChatSearchAdapter.OnItemClickListener {

    @BindView(R.id.left_back)
    ImageView leftBack;
    @BindView(R.id.act_chat_search_editText)
    EditText editText;
    @BindView(R.id.act_chat_search_recyclerView)
    RecyclerView mRecyclerView;

    private String userId;
    private String imgUrls;

    private ArrayList<Message> messages;

    private ChatSearchAdapter adapter;

    private List<Message> data;

    private List<Integer> integers = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_search);
        ButterKnife.bind(this);

        userId = getIntent().getStringExtra("userId");
        imgUrls = getIntent().getStringExtra("imgUrls");
        messages = getIntent().getParcelableArrayListExtra("messageList");

        data = new ArrayList<>();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (KeyEvent.KEYCODE_ENTER == keyCode && KeyEvent.ACTION_DOWN == event.getAction()) {

                    data.clear();
                    integers.clear();

                    if (messages != null) {
                        for (int i = 0; i < messages.size(); i++) {
                            MessageContent content = messages.get(i).getContent();
                            if (content instanceof TextMessage) {
                                TextMessage textMessage = (TextMessage) content;

                                if (textMessage.getContent().indexOf(editText.getText().toString()) != -1) {
                                    data.add(messages.get(i));
                                    integers.add(i);
                                }
                            }
                        }

                        adapter = new ChatSearchAdapter(ChatSearchActivity.this, data, imgUrls, ChatSearchActivity.this);
                        mRecyclerView.setAdapter(adapter);

                        if (data.size() == 0) {
                            TUtils.showCustom(ChatSearchActivity.this, "无搜索结果");
                        }
                    }

                    return true;
                }
                return false;
            }
        });

    }

    @OnClick({R.id.left_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                finish();
                break;
        }
    }

    @Override
    public void onItemClickListener(int position) {
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(getCurrentFocus()
                                .getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

        EventBus.getDefault().post(new EventUtils(EventConstants.TYPE_MESSAGE_SELECT, integers.get(position)));
        finish();
    }
}
