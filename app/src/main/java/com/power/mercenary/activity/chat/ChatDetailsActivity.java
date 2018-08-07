package com.power.mercenary.activity.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.model.Response;
import com.power.mercenary.MyApplication;
import com.power.mercenary.R;
import com.power.mercenary.base.BaseActivity;
import com.power.mercenary.data.EventConstants;
import com.power.mercenary.event.EventUtils;
import com.power.mercenary.http.HttpManager;
import com.power.mercenary.http.JsonCallback;
import com.power.mercenary.http.ResponseBean;
import com.power.mercenary.utils.TUtils;
import com.power.mercenary.view.BaseDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;

/**
 * admin  2018/7/25 wan
 */
public class ChatDetailsActivity extends BaseActivity {

    @BindView(R.id.left_back)
    FrameLayout leftBack;
    @BindView(R.id.act_chat_details_query)
    RelativeLayout query;
    @BindView(R.id.act_chat_details_clear)
    RelativeLayout clear;
    @BindView(R.id.act_chat_details_queryAll)
    RelativeLayout queryAll;

    private String userId;
    private String imgUrls;

    private ArrayList<Message> messages;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_details);
        ButterKnife.bind(this);

        userId = getIntent().getStringExtra("userId");
        imgUrls = getIntent().getStringExtra("imgUrls");

        messages = getIntent().getParcelableArrayListExtra("messageList");

        EventBus.getDefault().register(this);
    }

    @OnClick({R.id.act_chat_details_query, R.id.act_chat_details_clear, R.id.act_chat_details_queryAll, R.id.left_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                finish();
                break;
            case R.id.act_chat_details_query:
                Intent intent = new Intent(this, ChatSearchActivity.class);
                intent.putParcelableArrayListExtra("messageList", messages);
                intent.putExtra("userId", userId);
                intent.putExtra("imgUrls", imgUrls);
                startActivity(intent);
                break;
            case R.id.act_chat_details_clear:
                showClearDialog();
                break;
            case R.id.act_chat_details_queryAll:
                showQueryDialog();
                break;
        }
    }

    private void showClearDialog() {
        BaseDialog.Builder builder = new BaseDialog.Builder(this);
        final BaseDialog dialog = builder.setViewId(R.layout.dialog_publish_sh)
                .setPaddingdp(20, 0, 20, 0)
                .setGravity(Gravity.CENTER)
                .setAnimation(R.style.bottom_tab_style)
                .setWidthHeightpx(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                .isOnTouchCanceled(true)
                .builder();

        TextView textView = dialog.getView(R.id.dialog_title);

        textView.setText("是否确认清空聊天记录?");

        dialog.getView(R.id.dialog_publish_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getView(R.id.dialog_publish_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RongIMClient.getInstance().clearMessages(Conversation.ConversationType.PRIVATE, userId, new RongIMClient.ResultCallback<Boolean>() {
                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        TUtils.showCustom(ChatDetailsActivity.this, "清除成功");
                        EventBus.getDefault().post(new EventUtils(EventConstants.TYPE_CLEAR_ALL_HISTORY));
                        new HttpManager<ResponseBean<Void>>("Home/YbTest/message_add", this)
                                .addParams("token", MyApplication.getUserToken())
                                .addParams("toUserId", userId)
                                .addParams("objectName", "TxtMsg")
                                .addParams("content", " ")
                                .postRequest(new JsonCallback<ResponseBean<Void>>() {
                                    @Override
                                    public void onSuccess(Response<ResponseBean<Void>> response) {
                                        EventBus.getDefault().post(new EventUtils(EventConstants.TYPE_MESSAGE_SHOW_NULL, userId));
                                        finish();
                                    }
                                });
                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {

                    }
                });
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void showQueryDialog() {
        BaseDialog.Builder builder = new BaseDialog.Builder(this);
        final BaseDialog dialog = builder.setViewId(R.layout.dialog_publish_sh)
                .setPaddingdp(20, 0, 20, 0)
                .setGravity(Gravity.CENTER)
                .setAnimation(R.style.bottom_tab_style)
                .setWidthHeightpx(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                .isOnTouchCanceled(true)
                .builder();

        TextView textView = dialog.getView(R.id.dialog_title);

        textView.setText("是否获取全部聊天记录?");

        dialog.getView(R.id.dialog_publish_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getView(R.id.dialog_publish_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EventBus.getDefault().post(new EventUtils(EventConstants.TYPE_REFRESH_ITEM, 0));
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRecevierEvent(EventUtils event) {
        switch (event.getType()) {
            case EventConstants.TYPE_MESSAGE_SELECT:
                finish();
                break;

            case EventConstants.TYPE_REFRESH_ITEM_SUCESS:
                int size = (int) event.getData();
                if (size != 0) {
                    EventBus.getDefault().post(new EventUtils(EventConstants.TYPE_REFRESH_ITEM, 0));
                } else {
                    showQueryAllDialog();
                }
                break;
        }
    }

    private void showQueryAllDialog() {
        BaseDialog.Builder builder = new BaseDialog.Builder(this);
        final BaseDialog dialog = builder.setViewId(R.layout.dialog_publish_sh)
                .setPaddingdp(20, 0, 20, 0)
                .setGravity(Gravity.CENTER)
                .setAnimation(R.style.bottom_tab_style)
                .setWidthHeightpx(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                .isOnTouchCanceled(true)
                .builder();

        TextView textView = dialog.getView(R.id.dialog_title);
        TextView cancel = dialog.getView(R.id.dialog_publish_cancel);
        TextView submit = dialog.getView(R.id.dialog_publish_submit);

        textView.setText("聊天信息已获取,是否去查看?");
        submit.setText("去查看");

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
