package com.power.mercenary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.power.mercenary.R;
import com.power.mercenary.adapter.GridViewAddImgesAdpter;
import com.power.mercenary.base.BaseActivity;
import com.power.mercenary.view.BaseDialog;
import com.power.mercenary.view.MyGridView;
import com.wevey.selector.dialog.DialogInterface;
import com.wevey.selector.dialog.NormalSelectionDialog;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PubGerendingzhiActivity extends BaseActivity {

    @BindView(R.id.title_back_iv)
    ImageView titleBackIv;
    @BindView(R.id.title_content_tv)
    TextView titleContentTv;
    @BindView(R.id.title_content_right_tv)
    TextView titleContentRightTv;
    @BindView(R.id.task_name_et)
    EditText taskNameEt;
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.task_money_et)
    EditText taskMoneyEt;
    @BindView(R.id.require_recycler)
    RecyclerView requireRecycler;
    @BindView(R.id.add_require_tv)
    TextView addRequireTv;
    @BindView(R.id.add_biaoqian_tv)
    TextView addBiaoqianTv;
    @BindView(R.id.biaoqian_et)
    EditText biaoqianEt;
    @BindView(R.id.del_biaoqian_tv)
    TextView delBiaoqianTv;
    @BindView(R.id.biaoqian_recycler)
    RecyclerView biaoqianRecycler;
    @BindView(R.id.des_zishu_tv)
    TextView desZishuTv;
    @BindView(R.id.task_des_et)
    EditText taskDesEt;
    @BindView(R.id.mygridview)
    MyGridView mygridview;
    private List<String> requireList;
    private List<String> biaoqianList;
    private RequireAdapter requireAdapter;
    private BiaoqianAdapter biaoqianAdapter;
    private List<String> cameraList;
    private List<LocalMedia> selectList = new ArrayList<>();
    List<LocalMedia> list = new ArrayList<>();
    List<LocalMedia> listAll = new ArrayList<>();
    private GridViewAddImgesAdpter addImgesAdpter;
    private BaseDialog mDialog;
    private BaseDialog.Builder mBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pub_gerendingzhi);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        titleBackIv.setVisibility(View.VISIBLE);
        titleContentTv.setText("发布任务");
        titleContentRightTv.setVisibility(View.VISIBLE);
        titleContentRightTv.setText("发布");

        requireList = new ArrayList<>();
        requireList.add("");
        requireRecycler.setNestedScrollingEnabled(false);
        requireRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        requireAdapter = new RequireAdapter(R.layout.item_require_layout, requireList);
        requireRecycler.setAdapter(requireAdapter);

        biaoqianList = new ArrayList<>();
        biaoqianList.add("");
        biaoqianList.add("");
        biaoqianList.add("");
        biaoqianRecycler.setNestedScrollingEnabled(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        biaoqianRecycler.setLayoutManager(linearLayoutManager);
        biaoqianAdapter = new BiaoqianAdapter(R.layout.item_tag_layout, biaoqianList);
        biaoqianRecycler.setAdapter(biaoqianAdapter);

        /**
         * 添加照片adapter
         */
        addImgesAdpter = new GridViewAddImgesAdpter(list, this);
        mygridview.setAdapter(addImgesAdpter);
        mygridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                cameraList = new ArrayList<>();
                cameraList.add("从相册中选择");
                cameraList.add("拍照");
                showCamera();
            }
        });
    }

    private class RequireAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

        public RequireAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            int num = helper.getAdapterPosition() + 1;
            helper.setText(R.id.item_name_tv,"要求"+ num);
        }
    }

    private class BiaoqianAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

        public BiaoqianAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {

        }
    }

    private void showCamera() {
        new NormalSelectionDialog.Builder(this).setlTitleVisible(false)   //设置是否显示标题
                .setItemHeight(55)  //设置item的高度
                .setItemWidth(0.9f)  //屏幕宽度*0.9
                .setItemTextColor(R.color.textColorDrak)  //设置item字体颜色
                .setItemTextSize(16)  //设置item字体大小
                .setCancleButtonText("取消")  //设置最底部“取消”按钮文本
                .setOnItemListener(new DialogInterface.OnItemClickListener<NormalSelectionDialog>() {

                    @Override
                    public void onItemClick(NormalSelectionDialog dialog, View button, int
                            position) {
                        switch (position){
                            case 0://从相册选择
                                requestPhoto();
                                break;
                            case 1://拍照
                                requestCamera();
                                break;
                        }
                        dialog.dismiss();
                    }
                })
                .setCanceledOnTouchOutside(true)  //设置是否可点击其他地方取消dialog
                .build()
                .setDatas(cameraList)
                .show();
    }

    private void requestPhoto() {
        // 进入相册 以下是例子：不需要的api可以不写
        PictureSelector.create(PubGerendingzhiActivity.this)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.picture_default_style1)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                .maxSelectNum(9)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片
                .previewVideo(false)// 是否可预览视频
                .enablePreviewAudio(false) // 是否可播放音频
                .compressGrade(Luban.THIRD_GEAR)// luban压缩档次，默认3档 Luban.FIRST_GEAR、Luban.CUSTOM_GEAR
                .isCamera(true)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                .enableCrop(false)// 是否裁剪
                .compress(true)// 是否压缩
                .compressMode(PictureConfig.SYSTEM_COMPRESS_MODE)//系统自带 or 鲁班压缩 PictureConfig.SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .glideOverride(200, 200)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
//                .withAspectRatio(aspect_ratio_x, aspect_ratio_y)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示
                .isGif(false)// 是否显示gif图片
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                .circleDimmedLayer(false)// 是否圆形裁剪
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .openClickSound(false)// 是否开启点击声音
//                .selectionMedia(list)// 是否传入已选图片
//                        .videoMaxSecond(15)
//                        .videoMinSecond(10)
                //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                //.cropCompressQuality(90)// 裁剪压缩质量 默认100
                //.compressMaxKB()//压缩最大值kb compressGrade()为Luban.CUSTOM_GEAR有效
                //.compressWH() // 压缩宽高比 compressGrade()为Luban.CUSTOM_GEAR有效
                //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                //.rotateEnabled() // 裁剪是否可旋转图片
                //.scaleEnabled()// 裁剪是否可放大缩小图片
                //.videoQuality()// 视频录制质量 0 or 1
                //.videoSecond()//显示多少秒以内的视频or音频也可适用
                //.recordVideoSecond()//录制视频秒数 默认60s
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    private void requestCamera() {
        PictureSelector.create(PubGerendingzhiActivity.this)
                .openCamera(PictureMimeType.ofImage())// 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
                .theme(R.style.picture_default_style)// 主题样式设置 具体参考 values/styles
                .glideOverride(200, 200)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
//                .selectionMedia(list)// 是否传入已选图片
                .previewEggs(true)//预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    listAll.addAll(selectList);
                    selectList.clear();
                    addImgesAdpter.setList(listAll);
                    addImgesAdpter.notifyDataSetChanged();
                    break;
            }
        }
    }

    @OnClick({R.id.title_back_iv, R.id.title_content_right_tv, R.id.add_require_tv, R.id.add_biaoqian_tv,
            R.id.del_biaoqian_tv, R.id.des_zishu_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back_iv:
                finish();
                break;
            case R.id.title_content_right_tv:
                break;
            case R.id.add_require_tv:
                break;
            case R.id.add_biaoqian_tv:
                break;
            case R.id.del_biaoqian_tv:
                break;
            case R.id.des_zishu_tv:
                break;
        }
    }
}
