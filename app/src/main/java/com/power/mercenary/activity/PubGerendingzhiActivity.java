package com.power.mercenary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.lzy.okgo.model.Response;
import com.power.mercenary.R;
import com.power.mercenary.adapter.GridViewAddImgesAdpter;
import com.power.mercenary.base.BaseActivity;
import com.power.mercenary.bean.PayBean;
import com.power.mercenary.bean.PicNumsBean;
import com.power.mercenary.bean.mytask.PublishTaskBean;
import com.power.mercenary.bean.task.ApplyListBean;
import com.power.mercenary.bean.task.MsgBean;
import com.power.mercenary.bean.task.MsgListBean;
import com.power.mercenary.bean.task.TaskDetailsBean;
import com.power.mercenary.http.ResponseBean;
import com.power.mercenary.presenter.PicNumsPresenter;
import com.power.mercenary.presenter.PubTaskPresenter;
import com.power.mercenary.presenter.TaskDetailsPresenter;
import com.power.mercenary.presenter.publish.PublishPresenter;
import com.power.mercenary.utils.MyUtils;
import com.power.mercenary.view.BaseDialog;
import com.power.mercenary.view.MyGridView;
import com.wevey.selector.dialog.DialogInterface;
import com.wevey.selector.dialog.NormalSelectionDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PubGerendingzhiActivity extends BaseActivity implements PubTaskPresenter.PubTaskCallBack, PicNumsPresenter.PubTaskCallBack, RadioGroup.OnCheckedChangeListener,PublishPresenter.PublishCallBack ,TaskDetailsPresenter.TaskDetailsCallBack {

    @BindView(R.id.title_back_iv)
    ImageView titleBackIv;
    @BindView(R.id.title_content_tv)
    TextView titleContentTv;
    @BindView(R.id.title_content_right_tv)
    TextView titleContentRightTv;
    @BindView(R.id.task_name_et)
    EditText taskNameEt;
    @BindView(R.id.Rb)
    RadioButton checkbox;
    @BindView(R.id.Rb_no)
    RadioButton checkbox_no;
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
    @BindView(R.id.view_01)
    TextView view01;
    @BindView(R.id.view_02)
    TextView view02;
    @BindView(R.id.relative_table)
    RelativeLayout relativeTable;
    @BindView(R.id.newbiaoqian_recycler)
    RecyclerView newbiaoqianRecycler;
    @BindView(R.id.add_newbiaoqian_tv)
    TextView addNewbiaoqianTv;
    @BindView(R.id.saixuan_Rg)
    RadioGroup saixuanRg;
    private ArrayList<String> requireList;
    private ArrayList<String> biaoqianList;
    private RequireAdapter requireAdapter;
    private BiaoqianAdapter biaoqianAdapter;
    private List<String> cameraList;
    private List<LocalMedia> selectList = new ArrayList<>();
    private ArrayList<File> fileList = new ArrayList<>();
    List<LocalMedia> list = new ArrayList<>();
    List<LocalMedia> listAll = new ArrayList<>();
    private GridViewAddImgesAdpter addImgesAdpter;
    private BaseDialog mDialog;
    private BaseDialog.Builder mBuilder;
    private PubTaskPresenter presenter;
    private ImageView img_del_table;
    private String IsdelTable = "";
    private String taskType;
    private String childTaskType;
    private PicNumsPresenter picNumsPresenter;
    private String imgurl;
    private ArrayList<String> mpostUrl = new ArrayList<>();
    private NewbqAdapter newbqAdapter;
    private String task_shaixuan = "1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pub_gerendingzhi);
        ButterKnife.bind(this);
        taskType = getIntent().getStringExtra("TaskType");
        childTaskType = getIntent().getStringExtra("ChildTaskType");
        initView();
        presenter = new PubTaskPresenter(this, this);
        picNumsPresenter = new PicNumsPresenter(this, this);
    }

    private void initView() {
        saixuanRg.setOnCheckedChangeListener(this);
        taskDesEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (taskDesEt.getText().toString().length() > 200) {
                    Toast.makeText(mContext, "最多可输入200字", Toast.LENGTH_SHORT).show();
                    return;
                }
                desZishuTv.setText(taskDesEt.getText().toString().length() + "/200");
            }
        });

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
        newbiaoqianRecycler.setNestedScrollingEnabled(false);
        newbiaoqianRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        newbqAdapter = new NewbqAdapter(R.layout.item_require_layout, biaoqianList);
        newbiaoqianRecycler.setAdapter(newbqAdapter);
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

    @Override
    public void PicNums(List<PicNumsBean> response) {
        List<PicNumsBean> body = response;
        for (int i = 0; i < body.size(); i++) {
            mpostUrl.add(body.get(i).getPost());
        }
        imgurl = MyUtils.listToString(mpostUrl);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.Rb:
                task_shaixuan = "1";
                break;
            case R.id.Rb_no:
                task_shaixuan = "0";
                break;
        }
    }

    @Override
    public void getTaskDetails(TaskDetailsBean datas) {

    }

    @Override
    public void publishMsg(MsgBean datas) {

    }

    @Override
    public void getApplyList(List<ApplyListBean> datas) {

    }

    @Override
    public void getMsgList(List<MsgListBean> datas) {

    }

    @Override
    public void applyRequest() {

    }

    @Override
    public void changePeople(Response<ResponseBean<Void>> response, String avatar, String name) {

    }

    @Override
    public void changeCollection() {

    }

    @Override
    public void getMsgListFail() {

    }

    @Override
    public void toPayRequest(PayBean data) {
        WebActivity.invoke(this,data.getUrl(),"");
        finish();
        Toast.makeText(this,"发布成功",Toast.LENGTH_LONG).show();
    }

    @Override
    public void getPublishTaskList(List<PublishTaskBean.DataBean> datas) {
        //请求支付接口
        TaskDetailsPresenter taskDetailsPresenter = new TaskDetailsPresenter(this, this);
        taskDetailsPresenter.toPay(datas.get(0).getId());
    }

    @Override
    public void getPublishTaskListFail() {

    }

    @Override
    public void putTaskRequestSuccess(int position) {

    }

    @Override
    public void outTaskRequestSuccess(int position) {

    }

    @Override
    public void auditTaskRequestSuccess(int type, int position) {

    }

    @Override
    public void appraiseRequestSuccess() {

    }

    private class NewbqAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public NewbqAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, final String item) {
            final int num = helper.getAdapterPosition() + 1;
            ImageView item_del_iv = helper.getView(R.id.item_del_iv);
            final EditText item_content_et = helper.getView(R.id.item_content_et);
            if (item != null) {
                item_content_et.setText(item);
            }
            item_content_et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (!item_content_et.getText().toString().equals("")) {
                        biaoqianList.set(helper.getAdapterPosition(), item_content_et.getText().toString());
                    }
                }
            });

            item_del_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    biaoqianList.remove(helper.getAdapterPosition());
                    newbqAdapter.notifyDataSetChanged();


                }
            });
            helper.setText(R.id.item_name_tv, "要求" + num);
        }
    }


    private class RequireAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public RequireAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, String item) {
            final int num = helper.getAdapterPosition() + 1;
            ImageView item_del_iv = helper.getView(R.id.item_del_iv);
            final EditText item_content_et = helper.getView(R.id.item_content_et);
            if (item != null) {
                item_content_et.setText(item);
            }
            item_content_et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (!item_content_et.getText().toString().equals("")) {
                        requireList.set(helper.getAdapterPosition(), item_content_et.getText().toString());
                    }
                }
            });

            item_del_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "helper.getAdapterPosition():" + helper.getAdapterPosition() + "---------");
                    Log.d(TAG, "requireList.size():" + requireList.size() + "---------");
                    requireList.remove(helper.getAdapterPosition());
                    requireAdapter.notifyDataSetChanged();


                }
            });
            helper.setText(R.id.item_name_tv, "要求" + num);
        }
    }

    private class BiaoqianAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public BiaoqianAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, String item) {
            helper.setText(R.id.item_content_tv, item);
            img_del_table = helper.getView(R.id.img_del_table);
            if (IsdelTable.equals("1")) {
                img_del_table.setVisibility(View.VISIBLE);
            } else {
                img_del_table.setVisibility(View.GONE);
            }
            helper.setOnClickListener(R.id.img_del_table, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    IsdelTable = "2";
                    biaoqianList.remove(helper.getAdapterPosition());
                    biaoqianAdapter.notifyDataSetChanged();
                    if (biaoqianList.size() <= 0) {
                        relativeTable.setVisibility(View.GONE);
                    }
                }
            });

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
            R.id.del_biaoqian_tv, R.id.des_zishu_tv,R.id.add_newbiaoqian_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back_iv:
                finish();
                break;
            case R.id.title_content_right_tv:
                if (TextUtils.isEmpty(taskNameEt.getText().toString())) {
                    Toast.makeText(mContext, "请输入定制物品", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(taskMoneyEt.getText().toString())) {
                    Toast.makeText(mContext, "请输入佣金金额", Toast.LENGTH_SHORT).show();
                    return;
                } else if (requireList.size() == 1 && requireList.get(0).equals("")) {
                    Toast.makeText(mContext, "请输入任务要求", Toast.LENGTH_SHORT).show();
                    return;
                } else if (biaoqianList.size() <= 0) {
                    Toast.makeText(mContext, "请输入任务标签", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(taskDesEt.getText().toString())) {
                    Toast.makeText(mContext, "请输入任务详细介绍", Toast.LENGTH_SHORT).show();
                    return;
                } else if (listAll.size() <= 0) {
                    Toast.makeText(mContext, "请选择任务相关图片", Toast.LENGTH_SHORT).show();
                    return;
                }

                /**
                 * 发布任务
                 *
                 * @param task_type        任务分类对应id
                 * @param task_type_child  子任务分类对应id
                 * @param task_name        任务名称 如果是个人定制类任务表示是定制物品名称
                 * @param task_tag         任务标签 数组键用0,1,2…表示
                 * @param task_img         任务相关图片 数组键用0,1,2…表示
                 * @param pay_amount       佣金金额 金额以分为单位
                 * @param validity_time    任务有效期 以天为单位
                 * @param task_description 任务详情
                 * @param task_purpose     任务目的
                 * @param task_request     任务要求
                 * @param itemname         物品名称
                 * @param numbers          物品数量
                 * @param transport        运输要求
                 * @param delivery_time    送达时间 时间戳格式 （1530961214）10位
                 * @param begin_address    开始地址
                 * @param end_address      目的地址
                 * @param other_request    其它要求
                 */
                for (int i = 0; i < listAll.size(); i++) {
                    File file = new File(listAll.get(i).getPath());
                    Log.d("PubGerendingzhiActivity", "file:" + file + "--------");
                    fileList.add(file);
                }

                String s = MyUtils.listToString(requireList);
                String s1 = MyUtils.listToString(biaoqianList);
//                String s2 = MyUtils.listToString(fileList);
                //判断筛选与不筛选按钮的状态是否选中0无需筛选接单人 1筛选接单人

                picNumsPresenter.publishTask(fileList);//上传多张图片

                presenter.publishTask("", taskType, childTaskType, task_shaixuan,taskNameEt.getText().toString(), s1, imgurl, (Double.parseDouble(taskMoneyEt.getText().toString())*100)+"",
                        "", taskDesEt.getText().toString(), "", s,
                        "", "", "", "",
                        "", "", "");
                break;
            case R.id.add_newbiaoqian_tv:
                biaoqianList.add("");
                newbqAdapter.notifyItemInserted(biaoqianList.size() - 1);
                break;
            case R.id.add_require_tv:
                requireList.add("");
                requireAdapter.notifyItemInserted(requireList.size() - 1);
                break;
            case R.id.add_biaoqian_tv:
                if (biaoqianList.size() >= 5) {
                    Toast.makeText(mContext, "最多可添加五个标签", Toast.LENGTH_SHORT).show();
                    biaoqianEt.setText("");
                    return;
                }
                if (biaoqianEt.getText().length() < 2) {
                    Toast.makeText(mContext, "请输入2-4个文字标签", Toast.LENGTH_SHORT).show();
                    return;
                }
                biaoqianList.add(biaoqianEt.getText().toString());
                biaoqianEt.setText("");
                if (biaoqianList.size() > 0) {
                    relativeTable.setVisibility(View.VISIBLE);
                }
                biaoqianAdapter.notifyDataSetChanged();
                break;
            case R.id.del_biaoqian_tv:
                IsdelTable = "1";
                img_del_table.setVisibility(View.VISIBLE);
                biaoqianAdapter.notifyDataSetChanged();
                break;
            case R.id.des_zishu_tv:
                break;
        }
    }

    @Override
    public void publishTask() {
        if (TextUtils.equals(task_shaixuan,"0")){
            Toast.makeText(mContext, "请先支付", Toast.LENGTH_SHORT).show();
            //发发布完任务之后请求自己发布的任务的接口获取任务的id
            PublishPresenter publishPresenter = new PublishPresenter(this, this);
            publishPresenter.getPublishTaskList(1);
        }else{
            finish();
            Toast.makeText(this,"发布完成",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void testTask() {

    }

}
