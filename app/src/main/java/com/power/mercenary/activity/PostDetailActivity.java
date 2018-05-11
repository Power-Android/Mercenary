package com.power.mercenary.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.power.mercenary.R;
import com.power.mercenary.base.BaseActivity;
import com.power.mercenary.utils.SoftKeyboardTool;
import com.power.mercenary.utils.TUtils;
import com.power.mercenary.view.BaseSelectPopupWindow;
import com.power.mercenary.view.NineGridTestLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PostDetailActivity extends BaseActivity {

    @BindView(R.id.title_back_iv)
    ImageView titleBackIv;
    @BindView(R.id.title_content_tv)
    TextView titleContentTv;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.nine_gridlayout)
    NineGridTestLayout nineGridlayout;
    @BindView(R.id.iv_pinglun)
    ImageView ivPinglun;
    @BindView(R.id.jump_detail_ll)
    LinearLayout jumpDetailLl;
    @BindView(R.id.pl_recyclerview)
    RecyclerView plRecyclerview;
    private String[] mUrls = new String[]{
            "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2043305675,3979419376&fm=200&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=266745161,658804068&fm=27&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=222615259,2947254622&fm=27&gp=0.jpg",
            "http://img1.imgtn.bdimg.com/it/u=950771854,530317119&fm=27&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=2557022909,3736713361&fm=27&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=1830359176,654163576&fm=200&gp=0.jpg",
    };
    private List<String> urls = new ArrayList<>();
    private BaseSelectPopupWindow popWiw;// 昵称 编辑框


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        ButterKnife.bind(this);
        titleBackIv.setVisibility(View.VISIBLE);
        titleContentTv.setText("帖子详情");
        initView();
    }

    private void initView() {
        for (int i = 0; i < mUrls.length; i++) {
            urls.add(mUrls[i]);
        }
        nineGridlayout.setIsShowAll(true);
        nineGridlayout.setUrlList(urls);

        List<String> plList = new ArrayList<>();
        plList.add("");
        plList.add("");
        plList.add("");
        plRecyclerview.setNestedScrollingEnabled(false);
        plRecyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        PinglunAdapter pinglunAdapter = new PinglunAdapter(R.layout.item_pinglun_layout,plList);
        plRecyclerview.setAdapter(pinglunAdapter);
    }

    private class PinglunAdapter extends BaseQuickAdapter<String,BaseViewHolder>{

        public PinglunAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            List<String> chatList = new ArrayList<>();
            chatList.add("");
            chatList.add("");
            chatList.add("");
            RecyclerView chatRecyclerview = helper.getView(R.id.recycler_chat);
            chatRecyclerview.setNestedScrollingEnabled(false);
            chatRecyclerview.setLayoutManager(new LinearLayoutManager(mContext));
            ChatAdapter chatAdapter = new ChatAdapter(R.layout.item_chat_layout,chatList);
            chatRecyclerview.setAdapter(chatAdapter);
            helper.getView(R.id.pl_iv).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showCommentPopWindow(view);
                }
            });
        }
    }

    private class ChatAdapter extends BaseQuickAdapter<String,BaseViewHolder>{

        public ChatAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            TextView tv_content = helper.getView(R.id.tv_content);
            SpannableString spannableString = new SpannableString("小豆：的撒娇的卡萨京东设计");
            StyleSpan styleSpan_B = new StyleSpan(Typeface.BOLD);
            spannableString.setSpan(styleSpan_B, 0, 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            tv_content.setText(spannableString);
        }
    }

    private void showCommentPopWindow(View view) {
        if (popWiw == null) {
            popWiw = new BaseSelectPopupWindow(mContext, R.layout.edit_data2);
            // popWiw.setOpenKeyboard(true);
            popWiw.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
            popWiw.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            popWiw.setShowTitle(false);
        }
        popWiw.setFocusable(true);
        //        InputMethodManager im = (InputMethodManager)
        //                mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        //        im.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        SoftKeyboardTool.showSoftKeyboard(view);
        final EditText edt = (EditText) popWiw.getContentView().findViewById(R.id.edt_content);
        final TextView tv_send = (TextView) popWiw.getContentView().findViewById(R.id.tv_send);
        edt.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (TextUtils.isEmpty(edt.getText())) {

                } else {

                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tv_send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(edt.getText().toString().trim())) {
                    String content = edt.getText().toString().trim();
                    popWiw.dismiss();
                }
            }
        });
        popWiw.showAtLocation(view, Gravity.BOTTOM
                | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    @OnClick(R.id.title_back_iv)
    public void onViewClicked() {
        finish();
    }
}
