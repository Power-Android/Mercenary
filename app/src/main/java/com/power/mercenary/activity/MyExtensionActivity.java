package com.power.mercenary.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.power.mercenary.MyApplication;
import com.power.mercenary.R;
import com.power.mercenary.base.BaseActivity;
import com.power.mercenary.dialog.ShareDialog1;
import com.power.mercenary.view.SharingPop;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/28.
 */

public class MyExtensionActivity extends BaseActivity {


    private static final String TAG = "MyExtensionActivity";

    @BindView(R.id.my_tg_fx)
    TextView my_tg_fx;

    @BindView(R.id.left_back)
    ImageView left_back;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.ig_view)
    ImageView igView;

    private SharingPop sharingPop;
    private LayoutInflater inflater;
    public View defaultView;

    private LinearLayout weixin;
    private LinearLayout weixinP;
    private LinearLayout qq;
    private LinearLayout qzone;
    private LinearLayout sina;
    private TextView cancel;
    private int i;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_extension);
        ButterKnife.bind(this);
        left_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        my_tg_fx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap = JtMethod();//截图方法
               /* ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                byte[] bytes = outputStream.toByteArray();
                Glide.with(MyExtensionActivity.this)
                        .load(bytes)
                        .into(igView);*/
                ShareDialog1 dialog = new ShareDialog1(MyExtensionActivity.this, "佣兵天下", "佣兵旨在为优秀的个人自由创业者提升服务质量，简化沟通，让优秀的创业者更专心的为用户提供更好的服务，更搞笑的打造自己的品牌。",bitmap);
                dialog.setOnDismissListener(onDismissListener);
                setShowPop(dialog, my_tg_fx);
            }
        });
        initWeb();

        UMShareAPI.get(MyExtensionActivity.this).deleteOauth(MyExtensionActivity.this, SHARE_MEDIA.WEIXIN, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {

                Log.e(TAG, "onComplete: 成功删除");

            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {

            }
        });

    }

    private PopupWindow.OnDismissListener onDismissListener = new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            setWindowTranslucence(1.0f);
        }
    };

    private Bitmap JtMethod() {
        // 获取windows中最顶层的view
        View view = getWindow().getDecorView();
        view.buildDrawingCache();

        // 获取状态栏高度
        Rect rect = new Rect();
        view.getWindowVisibleDisplayFrame(rect);
        int statusBarHeights = rect.top;
        Display display = getWindowManager().getDefaultDisplay();

        // 获取屏幕宽和高
        int widths = display.getWidth();
        int heights = display.getHeight();
        Log.i("liubiao", "宽" + widths + "高" + heights);
        // 允许当前窗口保存缓存信息
        view.setDrawingCacheEnabled(true);

        // 去掉状态栏
        Bitmap bmp = Bitmap.createBitmap(view.getDrawingCache(), 0,
                statusBarHeights, widths, heights - statusBarHeights-30);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 10, bos);

        byte[] bytes = bos.toByteArray();
        bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        // 销毁缓存信息
        view.destroyDrawingCache();

        return bmp;

    }

    private void initWeb() {
        webView.loadUrl("http://yb.dashuibei.com/register/extension.html?token=" + MyApplication.getUserToken());

        WebSettings setTtings = webView.getSettings();
        //设置可以加载JavaScript的代码
        setTtings.setJavaScriptEnabled(true);
        //优先加载缓存
        setTtings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //不加载缓存
        setTtings.setCacheMode(WebSettings.LOAD_NO_CACHE);


        //是否支持缩放，true是支持 false是不支持
        setTtings.setSupportZoom(true);

        //这个方法使用后，网页就会在自己浏览器中显示出来
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    //数据加载完毕

                    //这里面我们可以将进度条或者对话框dismiss掉
                } else {
                    //数据正在加载
                    //这里面我们将进度条或者对话框show出来

                }
            }


        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(MyExtensionActivity.this, "成功了", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(MyExtensionActivity.this, "失败" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(MyExtensionActivity.this, "取消了", Toast.LENGTH_LONG).show();

        }
    };
}
