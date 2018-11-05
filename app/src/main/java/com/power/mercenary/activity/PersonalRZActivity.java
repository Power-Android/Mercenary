package com.power.mercenary.activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUriExposedException;
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

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.listener.CustomListener;
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
import com.power.mercenary.MainActivity;
import com.power.mercenary.MyApplication;
import com.power.mercenary.R;
import com.power.mercenary.base.BaseActivity;
import com.power.mercenary.bean.BankNameBean;
import com.power.mercenary.bean.CertificationBean;
import com.power.mercenary.bean.GetQmprivnce;
import com.power.mercenary.bean.MyZiLiBean;
import com.power.mercenary.bean.UpLoadPicBean;
import com.power.mercenary.bean.user.UserImgInfo;
import com.power.mercenary.http.OkhtttpUtils;
import com.power.mercenary.http.ResponseBean;
import com.power.mercenary.presenter.MyZiLiPresenter;
import com.power.mercenary.presenter.UpdataPresenter;
import com.power.mercenary.utils.CompressImageUtils;
import com.power.mercenary.utils.FileUtilcll;
import com.power.mercenary.utils.RealPathFromUriUtils;
import com.power.mercenary.utils.RetrofitManager;
import com.wevey.selector.dialog.DialogInterface;
import com.wevey.selector.dialog.NormalSelectionDialog;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.zelory.compressor.Compressor;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.leefeng.citypicker.CityPicker;
import me.leefeng.citypicker.CityPickerListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.http.Url;

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
    private UpdataPresenter presenter;
    private MyZiLiPresenter myZiLiPresenter;
    private int num = 0;
    private String path = Environment.getExternalStorageDirectory() + "/HeadPortrait" + (num++) + ".jpg";
    private int sum = 0;
    private List<String> imgList;
    private CityBean province;
    private CityBean city;
    private CityBean area;
    private String path4 = "";
    private String path3 = "";
    private String path2 = "";
    private String path1 = "";
    private static final String TAG = "PersonalRZActivity";
    private OptionsPickerView pvCustomOptions;

    private CityPicker cityPicker;


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

                //获取焦点的时候请求网络
                if (b) {


                    cityPicker.show();

                   /* final List<String> provinceList = new ArrayList<>();      //省
                    final List<List<String>> cityList = new ArrayList<>();    //城市的集合
                    final List<String> childCityList = new ArrayList<>();     //每一个城市

                    Map<String, String> map = new HashMap<>();

                    OkhtttpUtils.getInstance().doPost("http://yb.dashuibei.com/index.php/Home/Index/get_qmprivnce", map, new OkhtttpUtils.OkCallback() {
                        @Override
                        public void onFailure(Exception e) {

                        }

                        @Override
                        public void onResponse(String json) {

                            Gson gson = new Gson();

                            GetQmprivnce getQmprivnce = gson.fromJson(json, GetQmprivnce.class);

                            int code = getQmprivnce.getCode();

                            if (code == 0) {

                                List<GetQmprivnce.DataBean> data = getQmprivnce.getData();

                                //因为找寻找省下 所有的市 所以需要循环 取出省 市

                                for (int i = 0; i < data.size(); i++) {
                                    //循环得到每一个省
                                    provinceList.add(data.get(i).getProvince());

                                    //得到市的集合 循环
                                    List<GetQmprivnce.DataBean.CityBean> city = data.get(i).getCity();

                                    for (int j = 0; j < city.size(); j++) {

                                        //遍历省下的每一个市 放入集合
                                        childCityList.add(city.get(j).getCity());

                                    }

                                    cityList.add(childCityList);

                                }

                                *//*initPickerView(provinceList, cityList);*//*

                            }

                        }
                    });*/


                }

            }
        });

        //每次点击按钮时,把上一次存放进集合的数据清空
        imgList = new ArrayList<>();
        imgList.clear();
        //Log.e(TAG, "onViewClicked: " + imgList.size());


        cityPicker = new CityPicker(PersonalRZActivity.this, new CityPickerListener() {
            @Override
            public void getCity(String name) {

                edInOpeningAnAccount.setText(name);

            }
        });

    }

/*    private void initPickerView(final List<String> provinceList, final List<List<String>> cityList) {
        pvCustomOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = provinceList.get(options1) +
                        cityList.get(option2);

                edInOpeningAnAccount.setText(tx);
            }
        })
                .setLayoutRes(R.layout.city_choice_item, new CustomListener() {
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
                .setSelectOptions(2)//默认选中项
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确定按钮文字
                .setContentTextSize(20)//设置滚轮文字大小
                .setLinkage(true)

                .setBgColor(getResources().getColor(R.color.concrete))
                .setTextColorOut(getResources().getColor(R.color.textColorDrak))
                .setDividerColor(getResources().getColor(R.color.textColorDrak))
                .setTextColorCenter(getResources().getColor(R.color.black)) //设置选中项文字颜色
                .build();
        pvCustomOptions.setPicker(provinceList, cityList);//添加数据
        pvCustomOptions.show();

    }*/

    @OnClick({R.id.tv_commit, R.id.tv_scz_pz, R.id.img_Id_Card111, R.id.ll_idCard_hand_held, R.id.ll_idCard_reverse_side, R.id.ll_img_Id_Card, R.id.ll_bankCard})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_commit:

                break;
            case R.id.tv_scz_pz:

                String userToken = MyApplication.getUserToken();
                //Log.e("tagToken", userToken);
                String userName = edUserName.getText().toString().trim();
                String idCardNumber = edIdcardNumber.getText().toString().trim();
                String contactPersonName = edtContactPersonName.getText().toString().trim();
                String contactPersonPhone = edtContactPersonPhone.getText().toString().trim();
                String bankcardNumber = edtBankcardNumber.getText().toString().trim();
                String bankOfDeposit = edtBankOfDeposit.getText().toString().trim();
                String inOpeningAnAccount = edInOpeningAnAccount.getText().toString();

                Log.e("tag4", bankOfDeposit);

                boolean isRequest = true;

                //效验姓名
                if (TextUtils.isEmpty(userName)) {

                    Toast.makeText(PersonalRZActivity.this, "姓名不能为空", Toast.LENGTH_SHORT);
                    isRequest = false;

                }

                //效验身份证号
                if (TextUtils.isEmpty(idCardNumber)) {

                    Toast.makeText(PersonalRZActivity.this, "银行卡号不能为空", Toast.LENGTH_SHORT);
                    isRequest = false;
                }

                //效验联系人姓名
                if (TextUtils.isEmpty(contactPersonName)) {

                    Toast.makeText(PersonalRZActivity.this, "姓名不能为空", Toast.LENGTH_SHORT);
                    isRequest = false;
                }

                //效验联系人手机号
                if (TextUtils.isEmpty(contactPersonPhone)) {

                    Toast.makeText(PersonalRZActivity.this, "手机号不能为空", Toast.LENGTH_SHORT);
                    isRequest = false;
                }

                //效验银行卡号
                if (TextUtils.isEmpty(bankcardNumber)) {

                    Toast.makeText(PersonalRZActivity.this, "银行卡号不能为空", Toast.LENGTH_SHORT);
                    isRequest = false;
                }

                //效验开户行
                if (TextUtils.isEmpty(bankOfDeposit)) {

                    Toast.makeText(PersonalRZActivity.this, "银行卡号不能为空", Toast.LENGTH_SHORT);
                    isRequest = false;
                }

                //效验联系人手机号
                if (contactPersonPhone.length() != 11) {

                    Toast.makeText(PersonalRZActivity.this, "手机号必须为11位数字", Toast.LENGTH_SHORT);
                    isRequest = false;
                }

                //效验联系人姓名
                if (contactPersonName.length() > 20) {

                    Toast.makeText(PersonalRZActivity.this, "姓名不能大于20个汉字", Toast.LENGTH_SHORT);
                    isRequest = false;
                }

                //效验银行卡卡号
                if (bankcardNumber.length() < 16) {

                    Toast.makeText(PersonalRZActivity.this, "卡号不能小于16位数字", Toast.LENGTH_SHORT);
                    isRequest = false;
                }

                //效验身份证号
                if (idCardNumber.length() > 18 || idCardNumber.length() < 18) {

                    Toast.makeText(PersonalRZActivity.this, "身份证号必须为18位", Toast.LENGTH_SHORT);
                    isRequest = false;
                }

                Log.e(TAG, "onViewClicked: " + imgList.size() + "    " + isRequest);

                //证明图片已经选择完毕
                //可以开始请求
                if (imgList.size() == 4 && isRequest == true) {

                    //开始拼接参数 网络请求
                    Map<String, String> map = new HashMap<>();

                    map.put("token", userToken);
                    map.put("name", userName);
                    map.put("id_card", idCardNumber);
                    //Log.e("tag身份证号", idCardNumber);
                    map.put("lianxi_name", contactPersonName);
                    map.put("lianxi_mobile", contactPersonPhone);
                    map.put("yh_card", bankcardNumber);
                    map.put("yh_name", userName);
                    map.put("yh_khh", bankOfDeposit);
                    String[] split = inOpeningAnAccount.split("\\s");
                    map.put("province", split[0]);
                    map.put("city", split[1]);
                    map.put("identity_front", imgList.get(0));
                    map.put("identity_behind", imgList.get(1));
                    map.put("shouchi_img", imgList.get(2));
                    map.put("yh_img", imgList.get(3));

                    //Log.e(TAG, "onViewClicked  token: " + userToken);
                   /* Log.e(TAG, "onViewClicked: "+ userName);
                    Log.e(TAG, "onViewClicked: "+ idCardNumber);
                    Log.e(TAG, "onViewClicked: "+ contactPersonName);
                    Log.e(TAG, "onViewClicked: "+ contactPersonPhone);
                    Log.e(TAG, "onViewClicked: "+ bankcardNumber);
                    Log.e(TAG, "onViewClicked: "+ contactPersonName);
                    Log.e(TAG, "onViewClicked: "+ bankOfDeposit);
                    Log.e(TAG, "onViewClicked: "+ province.getName());
                    Log.e(TAG, "onViewClicked: "+ city.getName());*/
                    Log.e(TAG, "onViewClicked  token: " + userToken);
                    Log.e(TAG, "onViewClicked  名字           name: " + userName);
                    Log.e(TAG, "onViewClicked  身份证号       id_card: " + idCardNumber);
                    Log.e(TAG, "onViewClicked  联系人名字     lianxi_name: " + contactPersonName);
                    Log.e(TAG, "onViewClicked  联系人手机号    lianxi_mobile: " + contactPersonPhone);
                    Log.e(TAG, "onViewClicked  银行卡号        yh_card: " + bankcardNumber);
                    Log.e(TAG, "onViewClicked  开户人的名字     yh_name: " + userName);
                    Log.e(TAG, "onViewClicked  开户行          yh_khh: " + bankOfDeposit);
                    Log.e(TAG, "onViewClicked  省              province: " + split[0]);
                    Log.e(TAG, "onViewClicked  市              city: " + split[1]);
                    Log.e(TAG, "onViewClicked   身份证正面      identity_front: " + imgList.get(0));
                    Log.e(TAG, "onViewClicked: 身份证反面       identity_behind:" + imgList.get(1));
                    Log.e(TAG, "onViewClicked:  银行卡照片      shouchi_img:" + imgList.get(2));
                    Log.e(TAG, "onViewClicked:  手持银行卡照片   yh_img:" + imgList.get(3));

                    OkhtttpUtils.getInstance().doPost("http://yb.dashuibei.com/index.php/Home/QmUser/new_register", map, new OkhtttpUtils.OkCallback() {
                        @Override
                        public void onFailure(Exception e) {

                            //Toast.makeText(PersonalRZActivity.this,  e.getMessage(), Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onResponse(String json) {

                            // Toast.makeText(PersonalRZActivity.this, "正在上传...", Toast.LENGTH_SHORT).show();

                            Gson gson = new Gson();
                            Log.e("tag1", json);
                            CertificationBean certificationBean = gson.fromJson(json, CertificationBean.class);

                            Log.e("tag2", certificationBean.getCode() + "");

                            if ("0".equals(certificationBean.getCode())) {

                                //Toast.makeText(PersonalRZActivity.this, "成功了", Toast.LENGTH_SHORT).show();

                                //认证完成 跳转页面
                                Intent intent = new Intent(PersonalRZActivity.this, MainActivity.class);

                                startActivity(intent);

                            } else {

                                //Toast.makeText(PersonalRZActivity.this, certificationBean.getMessage().getResult().getMessage(), Toast.LENGTH_SHORT).show();

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

            final Bitmap bitmap = data.getParcelableExtra("data");

            if (sum == 1) {

                //对图片进行等比例压缩
                Bitmap newBitmap = CompressImageUtils.pressScaleCompress(bitmap);
                //把bitmap对象转换为String
                path1 = FileUtilcll.saveFile(this, "pic1.jpg", newBitmap);
                //利用Fresco展示图片
                imgIdCardFront.setImageBitmap(newBitmap);
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        File file = new File(path1);

                        upImage(file);

                    }
                }).start();

            }

            if (sum == 2) {

                Bitmap newBitmap = CompressImageUtils.pressScaleCompress(bitmap);
                path2 = FileUtilcll.saveFile(this, "pic2.jpg", newBitmap);

                imgIdCardReverseSide.setImageBitmap(newBitmap);

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        File file = new File(path2);

                        upImage(file);

                    }
                }).start();

            }

            if (sum == 3) {

                Bitmap newBitmap = CompressImageUtils.pressScaleCompress(bitmap);
                path3 = FileUtilcll.saveFile(this, "pic3.jpg", newBitmap);
                imgBankCard.setImageBitmap(newBitmap);

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        File file = new File(path3);

                        upImage(file);

                    }
                }).start();
            }

            if (sum == 4) {

                final Bitmap newBitmap = CompressImageUtils.pressScaleCompress(bitmap);
                path4 = FileUtilcll.saveFile(this, "pic4.jpg", newBitmap);
                imgInHandIdCard.setImageBitmap(newBitmap);

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        File file = new File(path4);
                        upImage(file);

                    }
                }).start();
            }

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

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void upImage(File fileName) {

        OkHttpClient client = new OkHttpClient();
        // form 表单形式上传
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (fileName != null) {
            // MediaType.parse() 里面是上传的文件类型。
            RequestBody body = RequestBody.create(MediaType.parse("image/*"), fileName);
            String filename = fileName.getName();
            // 参数分别为， 请求key ，文件名称 ， RequestBody
            requestBody.addFormDataPart("image", filename, body).addFormDataPart("token", MyApplication.getUserToken());
        }

        Request request = new Request.Builder()
                .url("http://yb.dashuibei.com/index.php/Home/UserCenter/singleimgup")
                .post(requestBody.build()).build();
        // readTimeout("请求超时时间" , 时间单位);
        client.newBuilder().readTimeout(5000, TimeUnit.MILLISECONDS).build().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    if (response != null && response.isSuccessful()) {
                        final String json = response.body().string();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Gson gson = new Gson();

                                UpLoadPicBean upLoadPicBean = gson.fromJson(json, UpLoadPicBean.class);

                                int code = upLoadPicBean.getCode();

                                int isFirst = 0;

                                if (code == 0) {

                                    String imgurl = upLoadPicBean.getData().getImgurl();

                                    if (sum == 1) {

                                        if (isFirst == 1) {

                                            imgList.remove(imgList.size() - 1);

                                            imgList.notifyAll();

                                            imgList.add(imgurl);

                                            sum = 0;


                                            isFirst = 0;

                                        } else {

                                            isFirst = 1;

                                            imgList.add(imgurl);

                                            sum = 0;
                                            imgurl = "";

                                        }

                                    }
                                    if (sum == 2) {

                                        if (isFirst == 2) {

                                            imgList.remove(imgList.size() - 1);

                                            imgList.notifyAll();

                                            imgList.add(imgurl);

                                            sum = 0;

                                            isFirst = 0;

                                        } else {

                                            isFirst = 2;

                                            imgList.add(imgurl);

                                            sum = 0;
                                            imgurl = "";

                                        }
                                    }
                                    if (sum == 3) {

                                        if (isFirst == 3) {

                                            imgList.remove(imgList.size() - 1);

                                            imgList.notifyAll();

                                            imgList.add(imgurl);

                                            sum = 0;

                                            isFirst = 0;

                                        } else {

                                            isFirst = 3;

                                            imgList.add(imgurl);

                                            sum = 0;
                                            imgurl = "";

                                        }
                                    }
                                    if (sum == 4) {

                                        if (isFirst == 4) {

                                            imgList.remove(imgList.size() - 1);

                                            imgList.notifyAll();

                                            imgList.add(imgurl);

                                            sum = 0;

                                            isFirst = 0;

                                        } else {

                                            isFirst = 4;

                                            imgList.add(imgurl);

                                            sum = 0;
                                            imgurl = "";

                                        }
                                    }

                                    Toast.makeText(PersonalRZActivity.this, "图片上传成功", Toast.LENGTH_SHORT).show();

                                } else {

                                    Toast.makeText(PersonalRZActivity.this, upLoadPicBean.getMsg(), Toast.LENGTH_SHORT).show();

                                }

                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();

                }

            }
        });

    }

    @Override
    public void onBackPressed() {
        if (cityPicker.isShow()) {
            cityPicker.close();
            return;
        }
        super.onBackPressed();
    }

}
