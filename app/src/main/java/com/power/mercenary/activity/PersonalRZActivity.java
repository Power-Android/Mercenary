package com.power.mercenary.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceActivity;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.kongzue.baseokhttp.HttpRequest;
import com.kongzue.baseokhttp.MultiFileRequest;
import com.kongzue.baseokhttp.listener.ResponseListener;
import com.kongzue.baseokhttp.util.Parameter;
import com.lljjcoder.style.citythreelist.CityBean;
import com.lljjcoder.style.citythreelist.ProvinceActivity;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.power.mercenary.IApi.CertificationApi;
import com.power.mercenary.MyApplication;
import com.power.mercenary.R;
import com.power.mercenary.base.BaseActivity;
import com.power.mercenary.bean.BankNameBean;
import com.power.mercenary.bean.CertificationBean;
import com.power.mercenary.bean.MyZiLiBean;
import com.power.mercenary.bean.user.UserImgInfo;
import com.power.mercenary.http.OkhtttpUtils;
import com.power.mercenary.http.ResponseBean;
import com.power.mercenary.presenter.MyZiLiPresenter;
import com.power.mercenary.presenter.UpdataPresenter;
import com.power.mercenary.utils.RetrofitManager;
import com.wevey.selector.dialog.DialogInterface;
import com.wevey.selector.dialog.NormalSelectionDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.zelory.compressor.Compressor;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2018/3/28.dd
 */

public class PersonalRZActivity extends BaseActivity implements UpdataPresenter.UpdataCallBack, MyZiLiPresenter.Collection {

    @BindView(R.id.left_back)
    ImageView leftBack;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    @BindView(R.id.ed_user_name)
    EditText edUserName;
    @BindView(R.id.ed_idcard_number)
    EditText edIdcardNumber;
    @BindView(R.id.ed_In_opening_an_account)
    EditText edInOpeningAnAccount;
    @BindView(R.id.ll_hy)
    LinearLayout llHy;
    @BindView(R.id.edt_bankcard_number)
    EditText edtBankcardNumber;
    @BindView(R.id.edt_bank_of_deposit)
    EditText edtBankOfDeposit;
    @BindView(R.id.edt_contact_person_name)
    EditText edtContactPersonName;
    @BindView(R.id.edt_contact_person_phone)
    EditText edtContactPersonPhone;
    @BindView(R.id.ll_img_Id_Card)
    LinearLayout llImgIdCard;
    @BindView(R.id.img_Id_Card111)
    SimpleDraweeView imgIdCardFront;
    @BindView(R.id.ll_idCard_reverse_side)
    LinearLayout llIdCardReverseSide;
    @BindView(R.id.img_Id_Card_reverse_side)
    SimpleDraweeView imgIdCardReverseSide;
    @BindView(R.id.ll_bankCard)
    LinearLayout llBankCard;
    @BindView(R.id.img_bankCard)
    SimpleDraweeView imgBankCard;
    @BindView(R.id.ll_idCard_hand_held)
    LinearLayout llIdCardHandHeld;
    @BindView(R.id.img_in_hand_Id_Card)
    SimpleDraweeView imgInHandIdCard;
    @BindView(R.id.tv_scz_pz)
    TextView tvSczPz;
    private List<String> cameraList;
    private List<LocalMedia> selectList = new ArrayList<>();
    private UpdataPresenter presenter;
    private MyZiLiPresenter myZiLiPresenter;

    private int num = 0;
    private String path = Environment.getExternalStorageDirectory() + "/publishedaboutI" + num + "con.png";

    private int sum = 0;
    private String pic1;
    private String pic2;
    private String pic3;
    private String pic4;
    private List<String> imgList;
    private CityBean province;
    private CityBean city;
    private CityBean area;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_rz);
        ButterKnife.bind(this);
        leftBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        presenter = new UpdataPresenter(this, this);
        myZiLiPresenter = new MyZiLiPresenter(this, this);

        initView();
    }

    private void initView() {


        edtBankOfDeposit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if (b) {

                    String bankNumber = edtBankcardNumber.getText().toString().trim();

                    if (bankNumber != "") {

                        HttpRequest.POST(PersonalRZActivity.this, "http://yb.dashuibei.com/index.php/Home/Index/get_bankname", new Parameter()
                                        .add("cardNo", bankNumber)

                                , new ResponseListener() {
                                    @Override
                                    public void onResponse(String response, Exception error) {

                                        if (error == null) {

                                            String s = response.toString();

                                            Gson gson = new Gson();

                                            BankNameBean bankNameBean = gson.fromJson(s, BankNameBean.class);

                                            edtBankOfDeposit.setText(bankNameBean.getData().getBankname());

                                        } else {

                                            Toast.makeText(PersonalRZActivity.this, "失败", Toast.LENGTH_SHORT).show();

                                        }

                                    }
                                });

                    } else {

                        return;

                    }

                }

            }
        });

        edInOpeningAnAccount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if (b) {

                    //跳转到省份列表
                    Intent intent = new Intent(PersonalRZActivity.this, ProvinceActivity.class);
                    startActivityForResult(intent, ProvinceActivity.RESULT_DATA);

                }

            }
        });

    }

    @OnClick({R.id.tv_commit, R.id.tv_scz_pz, R.id.img_Id_Card111, R.id.ll_idCard_hand_held, R.id.ll_idCard_reverse_side, R.id.ll_img_Id_Card, R.id.ll_bankCard})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_commit:

                break;
            case R.id.tv_scz_pz:
                /*if (TextUtils.isEmpty(edtBankcardNumber.getText().toString())) {
                    Toast.makeText(mContext, "请输入身份证号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (selectList.size() <= 0) {
                    Toast.makeText(mContext, "请上传证件照", Toast.LENGTH_SHORT).show();
                    return;
                }

                new Compressor(this)
                        .compressToFileAsFlowable(new File(selectList.get(0).getPath()))
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
                        });*/


                if (imgList.size() == 4) {

                    //证明图片已经上传完毕

                    //可以开始请求

                    String userToken = MyApplication.getUserToken();
                    String userName = edUserName.toString().trim();
                    String trim = edIdcardNumber.toString().trim();
                    String trim1 = edtContactPersonName.toString().trim();
                    String trim2 = edtContactPersonPhone.toString().trim();
                    String trim3 = edtBankcardNumber.toString().trim();
                    String trim4 = edtBankOfDeposit.toString().trim();
                    //需要额外的参数
/*                    HttpRequest.POST(PersonalRZActivity.this, "http://yb.dashuibei.com/index.php/Home/QmUser/new_register", new Parameter()

                                    .add("token", userToken)
                                    *//*.add("businessLicence", "ssss")*//*
                                    .add("name", userName)
                                    .add("id_card", trim)
                                    .add("faren_name", userName)
                                    .add("lianxi_name", trim1)
                                    .add("lianxi_mobile", trim2)
                                    .add("yh_card", trim3)
                                    .add("yh_name", trim1)
                                    .add("yh_khh", trim4)
                                    .add("province", province.getName())
                                    .add("city", city.getName())
                                    .add("identity_front", imgList.get(0))
                                    .add("identity_behind", imgList.get(1))
                                    .add("shouchi_img", imgList.get(2))
                                    .add("yh_img", imgList.get(3))
                            , new ResponseListener() {
                                @Override
                                public void onResponse(String response, Exception error) {

                                    //Toast.makeText(PersonalRZActivity.this,"ssss",Toast.LENGTH_SHORT).show();

                                    if (error == null) {

                                        String s = response.toString();

                                        Gson gson = new Gson();

                                        CertificationBean certificationBean = gson.fromJson(s, CertificationBean.class);

                                        if ("0".equals(certificationBean.getCode())) {

                                            Toast.makeText(PersonalRZActivity.this, "成功了", Toast.LENGTH_SHORT).show();

                                        } else {

                                            Toast.makeText(PersonalRZActivity.this, error + "", Toast.LENGTH_SHORT).show();

                                        }

                                    }

                                }
                            });*/

                    Map<String, String> map = new HashMap<>();

                    map.put("token", userToken);
                    map.put("name", userName);
                    map.put("id_card", trim);
                    map.put("businessLicence", "1500000000");
                    map.put("faren_name", userName);
                    map.put("lianxi_name", trim1);
                    map.put("lianxi_mobile", trim2);
                    map.put("yh_card", trim3);
                    map.put("yh_name", trim1);
                    map.put("yh_khh", trim4);
                    map.put("province", province.getName());
                    map.put("city", city.getName());
                    map.put("identity_front", imgList.get(0));
                    map.put("identity_behind", imgList.get(1));
                    map.put("shouchi_img", imgList.get(2));
                    map.put("yh_img", imgList.get(3));

                    OkhtttpUtils.getInstance().doPost("http://yb.dashuibei.com/index.php/Home/QmUser/new_register", map, new OkhtttpUtils.OkCallback() {
                        @Override
                        public void onFailure(Exception e) {

                            //Toast.makeText(PersonalRZActivity.this,  e.getMessage(), Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onResponse(String json) {

                            Gson gson = new Gson();

                            CertificationBean certificationBean = gson.fromJson(json, CertificationBean.class);

                            Log.e("aaa", certificationBean.getCode() + "");

                            if ("0".equals(certificationBean.getCode())) {

                                Toast.makeText(PersonalRZActivity.this, "成功了", Toast.LENGTH_SHORT).show();

                            } else {

                                Toast.makeText(PersonalRZActivity.this, "错误", Toast.LENGTH_SHORT).show();

                            }

                        }
                    });


                } else {


                }

                break;
            case R.id.img_Id_Card111:
                cameraList = new ArrayList<>();
                cameraList.add("从相册中选择");
                cameraList.add("拍照");

                showCamera();
                sum = 1;
                break;

            //上传手持身份证
            case R.id.ll_idCard_hand_held:

                sum = 4;
                cameraList = new ArrayList<>();
                cameraList.add("从相册中选择");
                cameraList.add("拍照");
                showCamera();

                break;

            //上传银行卡正面
            case R.id.ll_bankCard:

                sum = 3;
                cameraList = new ArrayList<>();
                cameraList.add("从相册中选择");
                cameraList.add("拍照");
                showCamera();
                break;

            //上传身份证反面
            case R.id.ll_idCard_reverse_side:

                sum = 2;
                cameraList = new ArrayList<>();
                cameraList.add("从相册中选择");
                cameraList.add("拍照");
                showCamera();
                break;

            //身份证正面
            case R.id.ll_img_Id_Card:

                sum = 1;
                cameraList = new ArrayList<>();
                cameraList.add("从相册中选择");
                cameraList.add("拍照");
                showCamera();

                break;

            case R.id.ed_In_opening_an_account:

                Toast.makeText(this, "点击了", Toast.LENGTH_SHORT).show();


                break;

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
                                showPhoto();
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
        PictureSelector.create(PersonalRZActivity.this)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.picture_default_style1)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                .maxSelectNum(1)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.SINGLE
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

    public void showPhoto() {

        Intent it1 = new Intent(Intent.ACTION_PICK);
        it1.setType("image/*");
        startActivityForResult(it1, 200);

    }

    private void requestCamera() {
        Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        it.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path)));
        startActivityForResult(it, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ProvinceActivity.RESULT_DATA) {
            if (resultCode == RESULT_OK) {
                if (data == null) {
                    return;
                }
                //省份结果
                province = data.getParcelableExtra("province");
                //城市结果
                city = data.getParcelableExtra("city");
                //区域结果
                area = data.getParcelableExtra("area");

                edInOpeningAnAccount.setText(province.getName() + city.getName() + area.getName());

            }
        }

        if (requestCode == 100 && resultCode == RESULT_OK) {
            Intent it = new Intent("com.android.camera.action.CROP");
            it.putExtra("crop", true);
            it.setDataAndType(Uri.fromFile(new File(path)), "image/*");
            it.putExtra("aspactX", 1);
            it.putExtra("aspactY", 1);
            it.putExtra("outputX", 80);
            it.putExtra("outputY", 80);
            it.putExtra("return-data", true);
            startActivityForResult(it, 300);

        }

        if (requestCode == 200 && resultCode == RESULT_OK) {
            Intent it = new Intent("com.android.camera.action.CROP");
            it.putExtra("crop", true);
            Uri data1 = data.getData();
            it.setDataAndType(data1, "image/*");
            it.putExtra("aspactX", 1);
            it.putExtra("aspactY", 1);
            it.putExtra("outputX", 80);
            it.putExtra("outputY", 80);
            it.putExtra("return-data", true);
            startActivityForResult(it, 300);

        }

        //裁剪完后回到设置图片
        if (requestCode == 300 && resultCode == RESULT_OK) {

            Bitmap bitmap = data.getParcelableExtra("data");


            if (sum == 1) {

                imgIdCardFront.setImageBitmap(bitmap);
                sum = 0;

                pic1 = bitmapToString(bitmap);

            }

            if (sum == 2) {

                imgIdCardReverseSide.setImageBitmap(bitmap);
                sum = 0;

                pic2 = bitmapToString(bitmap);

            }

            if (sum == 3) {

                imgBankCard.setImageBitmap(bitmap);
                sum = 0;

                pic3 = bitmapToString(bitmap);

            }

            if (sum == 4) {

                imgInHandIdCard.setImageBitmap(bitmap);
                sum = 0;

                pic4 = bitmapToString(bitmap);

            }

            imgList = new ArrayList<>();

            imgList.add(pic1);
            imgList.add(pic2);
            imgList.add(pic3);
            imgList.add(pic4);

        }

    }

    @Override
    public void updataUserImg(UserImgInfo imgInfo) {
        String imgurl = imgInfo.imgurl;
        myZiLiPresenter.setIdCard(edIdcardNumber.getText().toString(), imgurl, "");
    }

    @Override
    public void updataSuccess() {

    }

    @Override
    public void getmZiLi(MyZiLiBean response) {

    }

    @Override
    public void setmZiLi(ResponseBean response) {

    }

    @Override
    public void setIdCard(ResponseBean response) {
        Toast.makeText(mContext, "提交成功", Toast.LENGTH_SHORT).show();
        Intent intent = getIntent();
        setResult(1, intent);
        finish();

    }


    //把bitmap转换成字符串
    public static String bitmapToString(Bitmap bitmap) {
        String string = null;
        ByteArrayOutputStream btString = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, btString);
        byte[] bytes = btString.toByteArray();
        string = Base64.encodeToString(bytes, Base64.DEFAULT);
        return string;
    }

}
