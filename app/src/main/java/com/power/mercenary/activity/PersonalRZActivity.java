package com.power.mercenary.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceActivity;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
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
import com.power.mercenary.utils.FileUtilcll;
import com.power.mercenary.utils.RealPathFromUriUtils;
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
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.power.mercenary.utils.RealPathFromUriUtils.getRealPathFromUri;

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
    private List<String> imgList;
    private CityBean province;
    private CityBean city;
    private CityBean area;
    private String path4 = "";
    private String path3 = "";
    private String path2 = "";
    private String path1 = "";

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

                    if (bankNumber != "" && bankNumber.length() >= 17) {

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

                //证明图片已经选择完毕
                //可以开始请求
                if (imgList.size() == 4) {

                    String userToken = MyApplication.getUserToken();
                    //Log.e("tagToken", userToken);
                    String userName = edUserName.getText().toString().trim();
                    String idCardNumber = edIdcardNumber.getText().toString().trim();
                    String contactPersonName = edtContactPersonName.getText().toString().trim();
                    String contactPersonPhone = edtContactPersonPhone.getText().toString().trim();
                    String bankcardNumber = edtBankcardNumber.getText().toString().trim();
                    String bankOfDeposit = edtBankOfDeposit.getText().toString().trim();

                    //开始拼接参数 网络请求
                    Map<String, String> map = new HashMap<>();

                    map.put("token", userToken);
                    map.put("name", userName);
                    map.put("id_card", idCardNumber);
                    //Log.e("tag身份证号", idCardNumber);
                    map.put("lianxi_name", contactPersonName);
                    map.put("lianxi_mobile", contactPersonPhone);
                    map.put("yh_card", bankcardNumber);
                    map.put("yh_name", contactPersonName);
                    map.put("yh_khh", bankOfDeposit);
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

                            // Toast.makeText(PersonalRZActivity.this, "正在上传...", Toast.LENGTH_SHORT).show();

                            Gson gson = new Gson();

                            CertificationBean certificationBean = gson.fromJson(json, CertificationBean.class);

                            //Toast.makeText(PersonalRZActivity.this, certificationBean.getMessage().getError(), Toast.LENGTH_SHORT).show();
                            //Toast.makeText(PersonalRZActivity.this, certificationBean.getCode(), Toast.LENGTH_SHORT).show();

                            Log.e("tag1",json);
                            Log.e("tag2",certificationBean.getCode()+"");
                            Log.e("tag",certificationBean.getMessage().getError()+"");

                            if ("0".equals(certificationBean.getCode())) {

                                Toast.makeText(PersonalRZActivity.this, "成功了", Toast.LENGTH_SHORT).show();

                            } else {

                                Toast.makeText(PersonalRZActivity.this, "错误", Toast.LENGTH_SHORT).show();

                                //Log.e("a4", certificationBean.getMessage().getError());
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

                                //去寻找是否已经有了相机的权限
                                if (ContextCompat.checkSelfPermission(PersonalRZActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                                    //Toast.makeText(PersonalRZActivity.this, "您申请了动态权限", Toast.LENGTH_SHORT).show();    //如果有了相机的权限有调用相机

                                    requestCamera();

                                } else {    //否则去请求相机权限
                                    ActivityCompat.requestPermissions(PersonalRZActivity.this, new String[]{Manifest.permission.CAMERA}, 100);

                                }

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

        if (requestCode == 10 && resultCode == RESULT_OK) {
            if (ContextCompat.checkSelfPermission(PersonalRZActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(PersonalRZActivity.this, "您同意了,相机权限", Toast.LENGTH_SHORT).show();

                requestCamera();

            } else {

            }
        }

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

        imgList = new ArrayList<>();

        //裁剪完后回到设置图片
        if (requestCode == 300 && resultCode == RESULT_OK) {

            Bitmap bitmap = data.getParcelableExtra("data");

            if (sum == 1) {

                imgIdCardFront.setImageBitmap(bitmap);
                sum = 0;
                path1 = FileUtilcll.saveFile(this, "pic1.jpg", bitmap);

            }

            if (sum == 2) {

                imgIdCardReverseSide.setImageBitmap(bitmap);
                sum = 0;
                path2 = FileUtilcll.saveFile(this, "pic2.jpg", bitmap);

            }

            if (sum == 3) {

                imgBankCard.setImageBitmap(bitmap);
                sum = 0;
                path3 = FileUtilcll.saveFile(this, "pic3.jpg", bitmap);

            }

            if (sum == 4) {

                imgInHandIdCard.setImageBitmap(bitmap);
                sum = 0;
                path4 = FileUtilcll.saveFile(this, "pic4.jpg", bitmap);

            }

            imgList.add(path1);
            imgList.add(path2);
            imgList.add(path3);
            imgList.add(path4);

            Log.e("path",path1);
            Log.e("path",path2);
            Log.e("path",path3);
            Log.e("path",path4);

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

    @Override
    public void onRequestPermissionsResult
            (int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {

            //如果requestCode为100 就走这里

            case 100:

                //permissions[0].equals(Manifest.permission.CAMERA)

                //grantResults[0] == PackageManager.PERMISSION_GRANTED

                //上面的俩个判断可有可无

                if (permissions[0].equals(Manifest.permission.CAMERA)) {

                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                        //如果用户同意了再去打开相机

                        Toast.makeText(PersonalRZActivity.this, "非常感谢您的同意,再会", Toast.LENGTH_SHORT).show();

                        requestCamera();

                    } else {

                        //因为第一次的对话框是系统提供的 从这以后系统不会自动弹出对话框 我们需要自己弹出一个对话框

                        //进行询问的工作

                        startAlertDiaLog();

                    }

                }

                break;

        }

    }

    //当用户第一次不同意权限时 第二次系统将不再有提示框 需要我们手写提示框
    public void startAlertDiaLog() {

        AlertDialog.Builder alert = new AlertDialog.Builder(PersonalRZActivity.this);

        alert.setTitle("说明");

        alert.setMessage("需要相机权限,去拍照");

        alert.setPositiveButton("立即开启", new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(android.content.DialogInterface dialogInterface, int i) {

                startSetting();

            }
        });

        alert.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(android.content.DialogInterface dialogInterface, int i) {
                Toast.makeText(PersonalRZActivity.this, "当您点击时,会再次询问", Toast.LENGTH_SHORT).show();
            }
        });

        alert.create();

        alert.show();

    }

    //打开设置 让用户自己打开权限
    public void startSetting() {

        Intent intent = new Intent();

        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);

        Uri uri = Uri.fromParts("package", getPackageName(), null);

        intent.setData(uri);

        startActivityForResult(intent, 10);

    }

}
