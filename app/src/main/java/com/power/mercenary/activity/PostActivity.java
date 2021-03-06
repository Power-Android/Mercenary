package com.power.mercenary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.power.mercenary.R;
import com.power.mercenary.adapter.GridViewAddImgesAdpter;
import com.power.mercenary.base.BaseActivity;
import com.power.mercenary.bean.PicNumsBean;
import com.power.mercenary.bean.TieZiDetailsBean;
import com.power.mercenary.bean.TieZiListBean;
import com.power.mercenary.http.ResponseBean;
import com.power.mercenary.presenter.PicNumsPresenter;
import com.power.mercenary.presenter.TieZiListPresenter;
import com.power.mercenary.utils.MyUtils;
import com.power.mercenary.view.MyGridView;
import com.wevey.selector.dialog.DialogInterface;
import com.wevey.selector.dialog.NormalSelectionDialog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.zelory.compressor.Compressor;

public class PostActivity extends BaseActivity implements PicNumsPresenter.PubTaskCallBack, TieZiListPresenter.TaskListCallBack {

    @BindView(R.id.title_back_iv)
    ImageView titleBackIv;
    @BindView(R.id.title_content_tv)
    TextView titleContentTv;
    @BindView(R.id.title_content_right_tv)
    TextView titleContentRightTv;
    @BindView(R.id.mygridview)
    MyGridView mygridview;
    @BindView(R.id.activity_post)
    LinearLayout activityPost;
    @BindView(R.id.edt_post_content)
    EditText edtPostContent;
    private GridViewAddImgesAdpter addImgesAdpter;
    List<LocalMedia> list = new ArrayList<>();
    private List<String> cameraList;
    private List<LocalMedia> selectList = new ArrayList<>();
    List<LocalMedia> listAll = new ArrayList<>();
    private PicNumsPresenter picNumsPresenter;
    private ArrayList<File> fileList = new ArrayList<>();
    private String imgurl;
    private TieZiListPresenter presenter;
    private String task_type_child;
    private String task_type;
    private ArrayList<String> mpostUrl =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        ButterKnife.bind(this);
        task_type_child = getIntent().getStringExtra("task_type_child");
        task_type = getIntent().getStringExtra("task_type");
        picNumsPresenter = new PicNumsPresenter(this, this);
        presenter = new TieZiListPresenter(this, this);
        initView();
    }

    private void initView() {
        titleBackIv.setVisibility(View.VISIBLE);
        titleContentTv.setText("发帖子");
        titleContentRightTv.setVisibility(View.VISIBLE);
        titleContentRightTv.setText("发布");

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
                        switch (position) {
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
        PictureSelector.create(PostActivity.this)
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
        PictureSelector.create(PostActivity.this)
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

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent intent =getIntent();
        setResult(2,intent);
        finish();
    }

    @OnClick({R.id.title_back_iv, R.id.title_content_right_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back_iv:
                Intent intent =getIntent();
                setResult(2,intent);
                finish();
                break;
            case R.id.title_content_right_tv:
                if (!edtPostContent.getText().toString().equals("")){
                    for (int i = 0; i < listAll.size(); i++) {
                        try {
                            fileList.add(new Compressor(mContext)
                                    .setMaxWidth(640)
                                    .setMaxHeight(480)
                                    .setQuality(75)
                                    .compressToFile(new File(listAll.get(i).getPath())));

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    picNumsPresenter.publishTask(fileList);//上传多张图片

                }else {
                    Toast.makeText(mContext, "请输入要发表的内容", Toast.LENGTH_SHORT).show();
                }


                break;
        }
    }

    @Override
    public void PicNums(List<PicNumsBean> response) {
        List<PicNumsBean> body = response;
        for (int i = 0; i < body.size(); i++) {
            mpostUrl.add(body.get(i).getPost());
        }
        String s = MyUtils.listToString(mpostUrl);
        presenter.getPost(task_type, task_type_child,edtPostContent.getText().toString(),s);
    }

    @Override
    public void getTaskList(List<TieZiListBean> datas) {

    }

    @Override
    public void getTaskDetails(TieZiDetailsBean datas) {

    }

    @Override
    public void getListFail() {

    }

    @Override
    public void getPubPinglun(ResponseBean datas) {

    }

    @Override
    public void getHuifu(ResponseBean datas) {

    }

    @Override
    public void getPost(ResponseBean datas) {
        Toast.makeText(mContext, "发布成功", Toast.LENGTH_SHORT).show();
        Intent intent =getIntent();
        setResult(2,intent);
        finish();
    }
}
