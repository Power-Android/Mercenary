package com.power.mercenary.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.kongzue.baseokhttp.HttpRequest;
import com.kongzue.baseokhttp.listener.ResponseListener;
import com.kongzue.baseokhttp.util.Parameter;
import com.power.mercenary.MainActivity;
import com.power.mercenary.MyApplication;
import com.power.mercenary.R;
import com.power.mercenary.base.BaseActivity;
import com.power.mercenary.bean.BankNameBean;
import com.power.mercenary.bean.CertificationBean;
import com.power.mercenary.bean.GetBankNameBean;
import com.power.mercenary.bean.GetQmprivnce;
import com.power.mercenary.bean.MyZiLiBean;
import com.power.mercenary.bean.UpLoadPicBean;
import com.power.mercenary.bean.user.UserImgInfo;
import com.power.mercenary.http.OkhtttpUtils;
import com.power.mercenary.http.ResponseBean;
import com.power.mercenary.pickerview.CharacterPickerWindow;
import com.power.mercenary.pickerview.OnOptionChangedListener;
import com.power.mercenary.presenter.MyZiLiPresenter;
import com.power.mercenary.presenter.UpdataPresenter;
import com.power.mercenary.utils.CompressImageUtils;
import com.power.mercenary.utils.FileUtilcll;
import com.power.mercenary.utils.Urls;
import com.wevey.selector.dialog.DialogInterface;
import com.wevey.selector.dialog.NormalSelectionDialog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
    private String path4 = "";
    private String path3 = "";
    private String path2 = "";
    private String path1 = "";
    private static final String TAG = "PersonalRZActivity";
    private OptionsPickerView pvCustomOptions;

    //效验字段
    private boolean isRequest = true;
    //标识认证成功和失败的值
    private boolean RenZflag = true;
    private List<String> list;

    final List<String> provinceList = new ArrayList<>();      //省
    final List<List<String>> cityList = new ArrayList<>();    //城市的集合

    private GetQmprivnce getQmprivnce;

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

                    Map<String, String> map = new HashMap<>();

                    OkhtttpUtils.getInstance().doPost("http://yb.dashuibei.com/index.php/Home/Index/get_qmbank", map, new OkhtttpUtils.OkCallback() {
                        @Override
                        public void onFailure(Exception e) {

                        }

                        @Override
                        public void onResponse(String json) {

                            Log.e(TAG, "onResponse: " + json);

                            Gson gson = new Gson();

                            GetBankNameBean getBankNameBean = gson.fromJson(json, GetBankNameBean.class);

                            int code = getBankNameBean.getCode();

                            if (code == 0) {

                                list = new ArrayList<>();

                                //循环出所有的银行的名字

                                List<GetBankNameBean.DataBean> data = getBankNameBean.getData();

                                for (int i = 0; i < data.size(); i++) {

                                    list.add(data.get(i).getBankname());

                                }

                                initPickerViewBank(list);

                            } else {

                                Toast.makeText(PersonalRZActivity.this, getBankNameBean.getMsg(), Toast.LENGTH_SHORT).show();

                            }

                        }
                    });

                }

            }
        });

        edInOpeningAnAccount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {


                if (b) {        //获取焦点的时候请求网络

                    //选项选择器
                    final CharacterPickerWindow mOptions = new CharacterPickerWindow(PersonalRZActivity.this);

                    //初始化选项数据
                    mOptions.setPicker(provinceList, cityList);

                    mOptions.setMaxTextSize(16);

                    //监听确定选择按钮
                    mOptions.setOnoptionsSelectListener(new OnOptionChangedListener() {
                        @Override
                        public void onOptionChanged(int options1, int option2, int options3) {
                            // TODO 处理选择结果

                            edInOpeningAnAccount.setText(provinceList.get(options1) + cityList.get(options1).get(option2));

                            setBackgroundAlpha(1.0f);
                        }
                    });
                    mOptions.showAtLocation(view, Gravity.BOTTOM, 0, 0);

                    setBackgroundAlpha(0.8f);

                    mOptions.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {

                            mOptions.dismiss();

                            setBackgroundAlpha(1.0f);

                        }
                    });

                }

            }
        });

        //每次点击按钮时,把上一次存放进集合的数据清空
        imgList = new ArrayList<>();
        imgList.clear();

        //把省市联动的数据写在外面 点击再去加载数据会造成卡顿

        Map<String, String> map = new HashMap<>();

        OkhtttpUtils.getInstance().doPost("http://yb.dashuibei.com/index.php/Home/Index/get_qmprivnce", map, new OkhtttpUtils.OkCallback() {

            @Override
            public void onFailure(Exception e) {

            }

            @Override
            public void onResponse(String json) {

                Gson gson = new Gson();

                getQmprivnce = gson.fromJson(json, GetQmprivnce.class);

                int code = getQmprivnce.getCode();

                if (code == 0) {
                    List<GetQmprivnce.DataBean> data = getQmprivnce.getData();

                    //因为寻找省下 所有的市 所以需要循环 取出省 市
                    for (int i = 0; i < data.size(); i++) {
                        //循环得到每一个省
                        provinceList.add(data.get(i).getProvince());

                        //Log.e(TAG, "onResponse   省: "+ data.get(i).getProvince());

                        //得到市的集合 循环
                        List<GetQmprivnce.DataBean.CityBean> city = data.get(i).getCity();
                        List<String> childList = new ArrayList<>();
                        for (int j = 0; j < city.size(); j++) {

                            //遍历省下的每一个市 放入集合 看看可以吗
                            childList.add(city.get(j).getCity());

                            //Log.e(TAG, "onResponse   市: "+ city.get(j).getCity());

                        }

                        cityList.add(childList);

                    }

                }

            }
        });

        //监听银行卡号的EdText 请求网络
        edtBankcardNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String bankNumber = edtBankcardNumber.getText().toString().trim();

                if (bankNumber.length() == 19) {

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

                }

            }
        });

    }

    public void initPickerViewBank(final List<String> bankList) {

        pvCustomOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = bankList.get(options1);

                edtBankOfDeposit.setText(tx);
            }
        })
                .setLayoutRes(R.layout.bank_choice_item_layout, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.bank_tv_finish);
                        final TextView tvCancle = (TextView) v.findViewById(R.id.bank_tv_cancle);

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
                .setSelectOptions(0)//默认选中项
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确定按钮文字
                .setContentTextSize(20)//设置滚轮文字大小
                .setBgColor(getResources().getColor(R.color.concrete))
                .setTextColorOut(getResources().getColor(R.color.textColorDrak))
                .setDividerColor(getResources().getColor(R.color.textColorDrak))
                .setTextColorCenter(getResources().getColor(R.color.black)) //设置选中项文字颜色
                .build();
        pvCustomOptions.setPicker(bankList);//添加数据
        pvCustomOptions.show();

    }

    @OnClick({R.id.tv_commit, R.id.tv_scz_pz, R.id.img_Id_Card111, R.id.ll_idCard_hand_held, R.id.ll_idCard_reverse_side, R.id.ll_img_Id_Card, R.id.ll_bankCard})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_commit:

                break;
            case R.id.tv_scz_pz:
               // if (RenZflag){

                    Authen();
               // }else{
                  //  Toast.makeText(PersonalRZActivity.this,"请重新检查您的个人信息",Toast.LENGTH_LONG).show();
              //  }

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

    private void Authen() {
        String userToken = MyApplication.getUserToken();
        //Log.e("tagToken", userToken);
        String userName = edUserName.getText().toString().trim();
        String idCardNumber = edIdcardNumber.getText().toString().trim();
        String contactPersonName = edtContactPersonName.getText().toString().trim();
        String contactPersonPhone = edtContactPersonPhone.getText().toString().trim();
        String bankcardNumber = edtBankcardNumber.getText().toString().trim();
        String bankOfDeposit = edtBankOfDeposit.getText().toString().trim();
        //*******inOpeningAnAccount  开户地区字段不能.trim();(去空) 因为是以 空格分割的字符串
        String inOpeningAnAccount = edInOpeningAnAccount.getText().toString();
        String[] split = inOpeningAnAccount.split("\\s");       //以空格分割字符串

        Log.e("tag4", bankOfDeposit);

        //效验姓名
        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(PersonalRZActivity.this, "姓名不能为空", Toast.LENGTH_SHORT).show();
            isRequest = false;
            return;

        } else {

            isRequest = true;

        }

        //效验身份证号
        if (TextUtils.isEmpty(idCardNumber)) {

            Toast.makeText(PersonalRZActivity.this, "身份证号不能为空", Toast.LENGTH_SHORT).show();
            isRequest = false;
            return;
        } else {

            isRequest = true;

        }
        //效验身份证号
        if (idCardNumber.length() > 18 || idCardNumber.length() < 18) {

            Toast.makeText(PersonalRZActivity.this, "身份证号必须为18位", Toast.LENGTH_SHORT).show();
            isRequest = false;
            return;
        } else {

            isRequest = true;

        }


        //效验银行卡号
        if (TextUtils.isEmpty(bankcardNumber)) {

            Toast.makeText(PersonalRZActivity.this, "银行卡号不能为空", Toast.LENGTH_SHORT).show();
            isRequest = false;
            return;
        } else {

            isRequest = true;

        }
//效验银行卡卡号
        if (bankcardNumber.length() < 16) {

            Toast.makeText(PersonalRZActivity.this, "卡号不能小于16位数字", Toast.LENGTH_SHORT).show();
            isRequest = false;
            return;
        } else {
            isRequest = true;
        }
        //效验开户行
        if (TextUtils.isEmpty(bankOfDeposit)) {

            Toast.makeText(PersonalRZActivity.this, "银行卡号不能为空", Toast.LENGTH_SHORT).show();
            isRequest = false;
            return;
        } else {

            isRequest = true;

        }
        //效验联系人姓名
        if (TextUtils.isEmpty(contactPersonName)) {

            Toast.makeText(PersonalRZActivity.this, "姓名不能为空", Toast.LENGTH_SHORT).show();
            isRequest = false;
            return;
        } else {

            isRequest = true;

        }
        //效验联系人姓名
        if (contactPersonName.length() > 20) {

            Toast.makeText(PersonalRZActivity.this, "姓名不能大于20个汉字", Toast.LENGTH_SHORT).show();
            isRequest = false;
            return;
        } else {

            isRequest = true;

        }

        //效验联系人手机号
        if (TextUtils.isEmpty(contactPersonPhone)) {

            Toast.makeText(PersonalRZActivity.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
            isRequest = false;
            return;
        } else {

            isRequest = true;

        }
        //效验联系人手机号
        if (contactPersonPhone.length() != 11) {

            Toast.makeText(PersonalRZActivity.this, "手机号必须为11位数字", Toast.LENGTH_SHORT).show();
            isRequest = false;
            return;
        } else {

            isRequest = true;

        }


        //Log.e(TAG, "onViewClicked: " + imgList.size() + "    " + isRequest);

        //证明图片已经选择完毕
        //可以开始请求
        if (isRequest == true) {

            if (imgList.size() >= 4) {

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
                map.put("province", split[0]);
                map.put("city", split[1]);
                map.put("identity_front", imgList.get(0));
                map.put("identity_behind", imgList.get(1));
                map.put("shouchi_img", imgList.get(2));
                map.put("yh_img", imgList.get(3));
                Log.i("authon", Urls.BASEIMGURL+imgList.get(2));
                OkhtttpUtils.getInstance().doPost("http://yb.dashuibei.com/index.php/Home/QmUser/new_register", map, new OkhtttpUtils.OkCallback() {
                    @Override
                    public void onFailure(Exception e) {

                        //Toast.makeText(PersonalRZActivity.this,  e.getMessage(), Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onResponse(String json) {

                        Gson gson = new Gson();
                        CertificationBean certificationBean = gson.fromJson(json, CertificationBean.class);

                        int code = certificationBean.getCode();
                        if (code == 0) {
                          //  RenZflag = false;

                            Toast.makeText(PersonalRZActivity.this, "实名成功,即将跳转......", Toast.LENGTH_SHORT).show();

                            new Thread(new Runnable() {
                                @Override
                                public void run() {

                                    try {
                                        Thread.sleep(2000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();

                            //认证完成 跳转页面
                            Intent intent = new Intent(PersonalRZActivity.this, MainActivity.class);

                            startActivity(intent);

                        } else if (code!=2){
                                Toast.makeText(PersonalRZActivity.this, certificationBean.getMessage().getResult().getMessage() + "", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(PersonalRZActivity.this, certificationBean.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                Toast.makeText(PersonalRZActivity.this, "请上传照片", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(PersonalRZActivity.this, "请检查您的个人信息是否有误", Toast.LENGTH_SHORT).show();

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


    public void startAlertDiaLog() {    //当用户第一次不同意权限时 第二次系统将不再有提示框 需要我们手写提示框

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


    public void startSetting() {        //打开设置 让用户自己打开权限

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

                                            imgList.add(imgurl);

                                            sum = 0;

                                            isFirst = 0;

                                        } else {

                                            isFirst = 1;

                                            imgList.add(imgurl);

                                            sum = 0;


                                        }

                                    }
                                    if (sum == 2) {

                                        if (isFirst == 2) {

                                            imgList.remove(imgList.size() - 1);

                                            imgList.add(imgurl);

                                            sum = 0;

                                            isFirst = 0;

                                        } else {

                                            isFirst = 2;

                                            imgList.add(imgurl);

                                            sum = 0;

                                        }
                                    }
                                    if (sum == 3) {

                                        if (isFirst == 3) {

                                            imgList.remove(imgList.size() - 1);

                                            imgList.add(imgurl);

                                            sum = 0;

                                            isFirst = 0;

                                        } else {

                                            isFirst = 3;

                                            imgList.add(imgurl);

                                            sum = 0;

                                        }
                                    }
                                    if (sum == 4) {

                                        if (isFirst == 4) {

                                            imgList.remove(imgList.size() - 1);

                                            imgList.add(imgurl);

                                            sum = 0;

                                            isFirst = 0;

                                        } else {

                                            isFirst = 4;

                                            imgList.add(imgurl);

                                            sum = 0;

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

    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams layoutParams = PersonalRZActivity.this.getWindow().getAttributes();
        layoutParams.alpha = bgAlpha;
        PersonalRZActivity.this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        PersonalRZActivity.this.getWindow().setAttributes(layoutParams);
    }

}
