package com.power.mercenary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.power.mercenary.MyApplication;
import com.power.mercenary.R;
import com.power.mercenary.base.BaseActivity;
import com.power.mercenary.bean.user.UserImgInfo;
import com.power.mercenary.bean.user.UserInfo;
import com.power.mercenary.data.CacheConstants;
import com.power.mercenary.data.EventConstants;
import com.power.mercenary.event.EventUtils;
import com.power.mercenary.presenter.UpdataPresenter;
import com.power.mercenary.utils.CacheUtils;
import com.power.mercenary.utils.DataCleanManager;
import com.power.mercenary.utils.Urls;
import com.power.mercenary.view.AgePop;
import com.power.mercenary.view.SelectorPop;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.zelory.compressor.Compressor;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/3/22.
 */

public class SetupActivity extends BaseActivity implements View.OnClickListener, UpdataPresenter.UpdataCallBack {


    @BindView(R.id.left_back)
    ImageView left_back;

    @BindView(R.id.title_text)
    TextView title_text;

    @BindView(R.id.rl_bz)
    RelativeLayout rl_bz;
    @BindView(R.id.ll_nc)
    LinearLayout ll_nc;

    @BindView(R.id.rl_zhyaq)
    RelativeLayout rl_zhyaq;

    @BindView(R.id.ll_txsc)
    LinearLayout ll_txsc;
    @BindView(R.id.rl_gywm)
    RelativeLayout rl_gywm;

    @BindView(R.id.rl_wtfk)
    RelativeLayout rl_wtfk;
    private SelectorPop selectorPop;

    private AgePop agePop;
    @BindView(R.id.ll_nl)
    LinearLayout ll_nl;
    @BindView(R.id.tv_sz_age)
    TextView tv_sz_age;

    @BindView(R.id.act_setUp_age_tv)
    TextView tvAge;
    @BindView(R.id.act_setUp_nickname_tv)
    TextView tvNickname;
    @BindView(R.id.act_setUp_name_tv)
    TextView tvName;
    @BindView(R.id.tv_tcdl)
    TextView logout;
    @BindView(R.id.act_setUp_icon)
    ImageView icon;

    private OptionsPickerView pvCustomOptions;

    private UserInfo userInfo;

    private UpdataPresenter presenter;

    private List<LocalMedia> selectList;

    private int sex;

    private TextView tvCacheNum;

    private RelativeLayout clearCache;

    private TextView showName;

    private RelativeLayout personalData;

    private TextView checkView;

    private String showAndHide = "show";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        ButterKnife.bind(this);
        title_text.setText("设置");
        selectorPop = new SelectorPop(SetupActivity.this, R.layout.selector_pop_item_view);
        agePop = new AgePop(SetupActivity.this, R.layout.nianling_pop_item_view);

        tvCacheNum = (TextView) findViewById(R.id.act_setUp_cacheNum);

        clearCache = (RelativeLayout) findViewById(R.id.act_setUp_clearCache);

        presenter = new UpdataPresenter(this, this);

        selectorPop.setOnDismissListener(onDismissListener);
        selectorPop.setOnSelectorListener(selectorListener);
        agePop.setOnDismissListener(onDismissListener);
        agePop.setOnAgeSelectorListener(ageSelectorListener);
        left_back.setOnClickListener(this);
        rl_bz.setOnClickListener(this);
        ll_txsc.setOnClickListener(this);
        rl_gywm.setOnClickListener(this);
        ll_nc.setOnClickListener(this);
        rl_zhyaq.setOnClickListener(this);
        rl_wtfk.setOnClickListener(this);
        ll_nl.setOnClickListener(this);
        findViewById(R.id.act_setUp_age).setOnClickListener(this);
        findViewById(R.id.act_setUp_nickname).setOnClickListener(this);

        showName = (TextView) findViewById(R.id.right_show_name);

        personalData = (RelativeLayout) findViewById(R.id.ll_grsmrz);

        checkView = (TextView) findViewById(R.id.tv_shiming);

        personalData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetupActivity.this, PersonalRZActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        showName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.equals(showAndHide, "hide")) {
                    if (!TextUtils.isEmpty(tvName.getText().toString())) {
                        String name = tvName.getText().toString();
                        int len = name.length() - 1;
                        StringBuffer string = new StringBuffer();
                        string.append(name.substring(0, 1));
                        for (int i = 0; i < len; i++) {
                            string.append("*");
                        }

                        tvName.setText(string.toString());
                    }
                    showAndHide = "show";
                    showName.setText("显示真实姓名");
                } else if (TextUtils.equals(showAndHide, "show")) {
                    if (!TextUtils.isEmpty(userInfo.getName())) {
                        tvName.setText(userInfo.getName());
                    }
                    showAndHide = "hide";
                    showName.setText("隐藏真实姓名");
                }
                CacheUtils.put(CacheConstants.SHOWANDHIDEREALNAME, showAndHide);
            }
        });

        List<String> ageList = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            ageList.add(i + "");
        }

        initSelectAge(ageList);

        initData();

        if (CacheUtils.get(CacheConstants.SHOWANDHIDEREALNAME) != null) {
            showAndHide = CacheUtils.get(CacheConstants.SHOWANDHIDEREALNAME);
            if (TextUtils.equals(showAndHide, "show")) {
                if (!TextUtils.isEmpty(userInfo.getName())) {
                    String name = userInfo.getName();
                    int len = name.length() - 1;
                    StringBuffer string = new StringBuffer();
                    string.append(name.substring(0, 1));
                    for (int i = 0; i < len; i++) {
                        string.append("*");
                    }

                    tvName.setText(string.toString());
                }
                showName.setText("显示真实姓名");
            }
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.loginOut();
                startActivity(new Intent(SetupActivity.this, SignInActivity.class));
                finish();
                removeAllActivitys();
//                EventBus.getDefault().post(new EventUtils(EventConstants.JUPMP_TO_MAIN));
            }
        });

        try {
            tvCacheNum.setText(DataCleanManager.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }

        clearCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataCleanManager.clearAllCache(SetupActivity.this);

                try {
                    tvCacheNum.setText(DataCleanManager.getTotalCacheSize(SetupActivity.this));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        EventBus.getDefault().register(this);
    }

    private void initData() {
        userInfo = CacheUtils.get(CacheConstants.USERINFO);

        if (userInfo == null)
            return;

        if (!TextUtils.isEmpty(userInfo.getHead_img())) {
            Glide.with(mContext)
                    .load(Urls.BASEIMGURL + userInfo.getHead_img())
                    .into(icon);
        }

        if (!TextUtils.isEmpty(userInfo.getAge())) {
            tvAge.setText(userInfo.getAge());
            pvCustomOptions.setSelectOptions(Integer.parseInt(userInfo.getAge()));
        }

        if (!TextUtils.isEmpty(userInfo.getNick_name())) {
            tvNickname.setText(userInfo.getNick_name());
        }

        if (!TextUtils.isEmpty(userInfo.getName())) {
            tvName.setText(userInfo.getName());
        }

        if (!TextUtils.isEmpty(userInfo.getSex())) {
            if (TextUtils.equals(userInfo.getSex(), "1")) {
                tv_sz_age.setText("女");
                sex = 1;
            } else {
                tv_sz_age.setText("男");
                sex = 0;
            }
        }

        switch (userInfo.getIs_check()) {
            case 0:
                checkView.setText("未审核");
                break;
            case 1:
                checkView.setText("审核中");
                break;
            case 2:
                checkView.setText("审核通过");
                break;
            case 3:
                checkView.setText("审核未通过");
                break;
        }
    }


    private void initSelectAge(final List<String> data) {
        pvCustomOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = data.get(options1);
                tvAge.setText(tx);
                pvCustomOptions.setSelectOptions(options1);
            }
        })
                .setLayoutRes(R.layout.view_custom_age, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        final TextView tvCancle = (TextView) v.findViewById(R.id.tv_cancle);

                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptions.returnData();
                                pvCustomOptions.dismiss();
                            }
                        });

                        tvCancle.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptions.dismiss();
                            }
                        });
                    }
                })
                .setContentTextSize(20)//设置滚轮文字大小
                .setBgColor(getResources().getColor(R.color.concrete))
                .setTextColorOut(getResources().getColor(R.color.textColorDrak))
                .setDividerColor(getResources().getColor(R.color.textColorDrak))
                .setTextColorCenter(getResources().getColor(R.color.black)) //设置选中项文字颜色
                .build();
        pvCustomOptions.setPicker(data);//添加数据

    }

    private AgePop.AgeSelectorListener ageSelectorListener = new AgePop.AgeSelectorListener() {
        @Override
        public void OnNanListener() {
            tv_sz_age.setText("男");
            sex = 0;
            agePop.dismiss();
        }

        @Override
        public void OnNVListener() {
            tv_sz_age.setText("女");
            sex = 1;
            agePop.dismiss();
        }

        @Override
        public void OnCancelListener() {
            agePop.dismiss();
        }
    };

    @Override
    public void onClick(View view) {

        Intent intent;
        switch (view.getId()) {
            case R.id.left_back:
                finish();
                break;
            case R.id.rl_bz:
                intent = new Intent(SetupActivity.this, HelpActivity.class);
                startActivity(intent);

                break;
            case R.id.ll_txsc:
                setShowPop(selectorPop, ll_txsc);
                break;
            case R.id.rl_gywm:
                intent = new Intent(SetupActivity.this, AboutUsActivity.class);
                startActivity(intent);

                break;
            case R.id.ll_nc:
                intent = new Intent(SetupActivity.this, ModifyNameActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_zhyaq:
                intent = new Intent(SetupActivity.this, SecurityActivity.class);
                startActivity(intent);

                break;
            case R.id.rl_wtfk:
                intent = new Intent(SetupActivity.this, ProblemFeedbackActivity.class);
                startActivity(intent);

                break;
            case R.id.ll_nl:
//                setShowPop(agePop, ll_nl);

                break;
            case R.id.act_setUp_age:
//                pvCustomOptions.show();
                break;
            case R.id.act_setUp_nickname:
//                intent = new Intent(SetupActivity.this, ModifyNicknameActivity.class);
//                startActivity(intent);
                break;
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRecevierEvent(EventUtils event) {
        switch (event.getType()) {
            case EventConstants.TYPE_SETT_REFRESH:
                //刷新用户信息
                initData();
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
        if (!TextUtils.equals(sex + "", userInfo.getSex()) || !TextUtils.equals(tvAge.getText().toString(), userInfo.getAge())) {
            presenter.updataUserInfo(tvNickname.getText().toString(),
                    tvName.getText().toString(),
                    tvAge.getText().toString(),
                    sex,
                    userInfo.getMail());
        }
    }

    private PopupWindow.OnDismissListener onDismissListener = new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            setWindowTranslucence(1.0f);
        }
    };

    private SelectorPop.SelectorListener selectorListener = new SelectorPop.SelectorListener() {
        @Override
        public void OnCarmeaListener() {
            requestCamera();
            selectorPop.dismiss();
        }

        @Override
        public void OnPhotoListener() {
            requestPhoto();
            selectorPop.dismiss();
        }

        @Override
        public void OnCancelListener() {
            selectorPop.dismiss();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void updataUserImg(UserImgInfo imgInfo) {
        if (imgInfo != null) {
            userInfo.setHead_img(imgInfo.imgurl);
            CacheUtils.put(CacheConstants.USERINFO, userInfo);

            Glide.with(mContext)
                    .load(Urls.BASEIMGURL + imgInfo.imgurl)
                    .into(icon);

            EventBus.getDefault().
                    post(new EventUtils(EventConstants.TYPE_USERINFO));
        }
    }

    @Override
    public void updataSuccess() {
        EventBus.getDefault().
                post(new EventUtils(EventConstants.TYPE_USERINFO));
    }

    private void requestPhoto() {
        // 进入相册 以下是例子：不需要的api可以不写
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.picture_default_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                .maxSelectNum(1)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片
//                .previewVideo(true)// 是否可预览视频
//                .enablePreviewAudio(true) // 是否可播放音频
//                .compressGrade(Luban.THIRD_GEAR)// luban压缩档次，默认3档 Luban.FIRST_GEAR、Luban.CUSTOM_GEAR
                .isCamera(true)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                .enableCrop(true)// 是否裁剪
                .compress(true)// 是否压缩
                .withAspectRatio(4, 4)
                .compressMode(PictureConfig.LUBAN_COMPRESS_MODE)//系统自带 or 鲁班压缩 PictureConfig.SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
//                .glideOverride(200, 200)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                //                .withAspectRatio(aspect_ratio_x, aspect_ratio_y)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示
                .isGif(false)// 是否显示gif图片
                .freeStyleCropEnabled(false)// 裁剪框是否可拖拽
                .circleDimmedLayer(true)// 是否圆形裁剪
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
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
                .scaleEnabled(true)// 裁剪是否可放大缩小图片
                //.videoQuality()// 视频录制质量 0 or 1
                //.videoSecond()//显示多少秒以内的视频or音频也可适用
                //.recordVideoSecond()//录制视频秒数 默认60s
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    private void requestCamera() {
        PictureSelector.create(this)
                .openCamera(PictureMimeType.ofImage())// 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
                .theme(R.style.picture_default_style)// 主题样式设置 具体参考 values/styles
                .enableCrop(true)// 是否裁剪
                .withAspectRatio(4, 4)
                .compress(true)// 是否压缩
                .compressMode(PictureConfig.LUBAN_COMPRESS_MODE)//系统自带 or 鲁班压缩 PictureConfig.SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
                .freeStyleCropEnabled(false)// 裁剪框是否可拖拽
                .circleDimmedLayer(true)// 是否圆形裁剪
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .scaleEnabled(true)// 裁剪是否可放大缩小图片
                //                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                //                .selectionMedia(list)// 是否传入已选图片
                //                .previewEggs(true)//预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
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
                    new Compressor(this)
                            .compressToFileAsFlowable(new File(selectList.get(0).getCutPath()))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<File>() {
                                @Override
                                public void accept(File file) {
                                    presenter.updataUserImg(file);
                                }
                            }, new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) {
                                    throwable.printStackTrace();
                                }
                            });
                    break;
            }
        }
    }
}
