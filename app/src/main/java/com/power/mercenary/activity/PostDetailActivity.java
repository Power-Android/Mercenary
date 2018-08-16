package com.power.mercenary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.power.mercenary.MyApplication;
import com.power.mercenary.R;
import com.power.mercenary.base.BaseActivity;
import com.power.mercenary.bean.TieZiDetailsBean;
import com.power.mercenary.bean.TieZiListBean;
import com.power.mercenary.http.ResponseBean;
import com.power.mercenary.presenter.TieZiListPresenter;
import com.power.mercenary.utils.KeyboardUtils;
import com.power.mercenary.utils.MyUtils;
import com.power.mercenary.utils.SoftKeyboardStateHelper;
import com.power.mercenary.utils.Urls;
import com.power.mercenary.view.BaseSelectPopupWindow;
import com.power.mercenary.view.CircleImageView;
import com.power.mercenary.view.NineGridTestLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PostDetailActivity extends BaseActivity implements TieZiListPresenter.TaskListCallBack {

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
    @BindView(R.id.title_content_right_tv)
    TextView titleContentRightTv;
    @BindView(R.id.img_details_head)
    CircleImageView imgDetailsHead;
    @BindView(R.id.tv_pinglun)
    TextView tvPinglun;
    @BindView(R.id.send)
    TextView send;
    @BindView(R.id.ed_pinglun)
    EditText edPinglun;
    @BindView(R.id.relative_keyboard)
    RelativeLayout relativeKeyboard;
    private BaseSelectPopupWindow popWiw;// 昵称 编辑框
    private String id;
    private TieZiListPresenter presenter;
    private List<String> mUrlList = new ArrayList<>();
    private PinglunAdapter pinglunAdapter;
    private ChatAdapter chatAdapter;
    private boolean isPinglun = true;
    private String liuyanid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        ButterKnife.bind(this);
        titleBackIv.setVisibility(View.VISIBLE);
        titleContentTv.setText("帖子详情");
        id = getIntent().getStringExtra("id");
        presenter = new TieZiListPresenter(this, this);
        presenter.getTaskDetails(id);

        SoftKeyboardStateHelper softKeyboardStateHelper = new SoftKeyboardStateHelper(findViewById(R.id.ed_pinglun));
        softKeyboardStateHelper.addSoftKeyboardStateListener(new SoftKeyboardStateHelper.SoftKeyboardStateListener() {
            @Override
            public void onSoftKeyboardOpened(int keyboardHeightInPx) {
                //键盘打开
                if (isPinglun){
                    edPinglun.setHint("请输入评论内容");
                }else {
                    edPinglun.setHint("请输入回复内容");
                }
            }
            @Override
            public void onSoftKeyboardClosed() {
                //键盘关闭
                isPinglun = true;
                edPinglun.setText("");
                edPinglun.setHint("请输入评论内容");

            }
        });
    }




    private void initView(List<TieZiDetailsBean.LiuyanBean> liuyan) {
        plRecyclerview.setNestedScrollingEnabled(false);
        plRecyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        pinglunAdapter = new PinglunAdapter(R.layout.item_pinglun_layout, liuyan);
        plRecyclerview.setAdapter(pinglunAdapter);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent intent =getIntent();
        setResult(2,intent);
        finish();
    }

    @OnClick({R.id.title_back_iv, R.id.send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back_iv:
                Intent intent =getIntent();
                setResult(2,intent);
                finish();
                break;
            case R.id.send:
                if (!MyApplication.isLogin()) {
                    startActivity(new Intent(this, SignInActivity.class));
                    return;
                }
                if (TextUtils.isEmpty(edPinglun.getText().toString())) {
                    if (isPinglun){
                        Toast.makeText(mContext, "请输入评论内容", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(mContext, "请输入回复内容", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    if (isPinglun){
                        presenter.getPubPinglun(id, edPinglun.getText().toString());
                        KeyboardUtils.hideKeyboard(edPinglun);
                    }else {
                        presenter.getHuifu(liuyanid, edPinglun.getText().toString());
                        KeyboardUtils.hideKeyboard(edPinglun);
                    }

                }
                break;
        }
    }

    private class PinglunAdapter extends BaseQuickAdapter<TieZiDetailsBean.LiuyanBean, BaseViewHolder> {

        public PinglunAdapter(int layoutResId, @Nullable List<TieZiDetailsBean.LiuyanBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final TieZiDetailsBean.LiuyanBean item) {
            Glide.with(mContext).load(Urls.BASEIMGURL + item.getLiuyan_user_headimg()).into((CircleImageView) helper.getView(R.id.item_pic_iv));
            helper.setText(R.id.item_name_tv, item.getLiuyan_user_name());
            helper.setText(R.id.tv_time, MyUtils.getDateToStringTime(item.getCreate_time()));
            helper.setText(R.id.item_content_tv, item.getLiuyan_content());
            List<TieZiDetailsBean.LiuyanBean.HuifuBean> huifu = item.getHuifu();
            RecyclerView chatRecyclerview = helper.getView(R.id.recycler_chat);
            if (huifu.size()<=0){
                chatRecyclerview.setVisibility(View.GONE);
            }else {
                chatRecyclerview.setVisibility(View.VISIBLE);
            }
            chatRecyclerview.setNestedScrollingEnabled(false);
            chatRecyclerview.setLayoutManager(new LinearLayoutManager(mContext));
            chatAdapter = new ChatAdapter(R.layout.item_pinglun1_layout, item.getHuifu());
            chatRecyclerview.setAdapter(chatAdapter);
            helper.getView(R.id.pl_iv).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    edPinglun.setHint("请输入回复内容");
                    liuyanid = item.getId();
                    isPinglun=false;
                    KeyboardUtils.showKeyboard(edPinglun);

                }
            });
        }
    }

    private class ChatAdapter extends BaseQuickAdapter<TieZiDetailsBean.LiuyanBean.HuifuBean, BaseViewHolder> {

        public ChatAdapter(int layoutResId, @Nullable List<TieZiDetailsBean.LiuyanBean.HuifuBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, TieZiDetailsBean.LiuyanBean.HuifuBean item) {
            helper.setText(R.id.tv_name, item.getHuifu_user_name() + ":");
            helper.setText(R.id.tv_content, item.getHuifu_content());
        }
    }
    @Override
    public void getTaskList(List<TieZiListBean> datas) {

    }

    @Override
    public void getTaskDetails(TieZiDetailsBean datas) {
        Glide.with(mContext).load(Urls.BASEIMGURL + datas.getPost_user_headimg()).into(imgDetailsHead);
        tvName.setText(datas.getPost_user_name());
        tvTime.setText(MyUtils.getDateToStringTime(datas.getCreate_time()));
        tvContent.setText(datas.getPost_content());
        tvPinglun.setText(datas.getLiuyan().size() + "评论");
        String post_img = datas.getPost_img();
        String[] all=post_img.split("\\|");
        mUrlList.clear();
        for (int i = 0; i < all.length; i++) {
            mUrlList.add(Urls.BASEIMGURL+all[i]);
        }
        nineGridlayout.setIsShowAll(true);
        nineGridlayout.setUrlList(mUrlList);

        initView(datas.getLiuyan());
    }

    @Override
    public void getListFail() {

    }

    @Override
    public void getPubPinglun(ResponseBean datas) {
        Toast.makeText(mContext, "评论成功", Toast.LENGTH_SHORT).show();
        edPinglun.setText("");
        presenter.getTaskDetails(id);
    }

    @Override
    public void getHuifu(ResponseBean datas) {
        Toast.makeText(mContext, "回复成功", Toast.LENGTH_SHORT).show();
        isPinglun = true;
        edPinglun.setText("");
        presenter.getTaskDetails(id);
        chatAdapter.notifyDataSetChanged();
    }

    @Override
    public void getPost(ResponseBean datas) {

    }

}
