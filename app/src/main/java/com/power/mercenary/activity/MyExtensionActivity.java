package com.power.mercenary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import com.power.mercenary.MyApplication;
import com.power.mercenary.R;
import com.power.mercenary.base.BaseActivity;
import com.power.mercenary.dialog.ShareDialog1;
import com.power.mercenary.view.SharingPop;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/28.
 */

public class MyExtensionActivity extends BaseActivity {

    @BindView(R.id.my_tg_fx)
    TextView my_tg_fx;

    @BindView(R.id.left_back)
    ImageView left_back;
    @BindView(R.id.webView)
    WebView webView;

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

                ShareDialog1 dialog = new ShareDialog1(MyExtensionActivity.this, "佣兵天下", "佣兵旨在为优秀的个人自由创业者提升服务质量，简化沟通，让优秀的创业者更专心的为用户提供更好的服务，更搞笑的打造自己的品牌。");
                dialog.setOnDismissListener(onDismissListener);
                setShowPop(dialog, my_tg_fx);

            }
        });
        initWeb();

    }

    private PopupWindow.OnDismissListener onDismissListener = new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            setWindowTranslucence(1.0f);
        }
    };

    private void initWeb() {
        webView.loadUrl("http://yb.dashuibei.com/register/extension.html?token="+ MyApplication.getUserToken());

        WebSettings setTtings= webView.getSettings();
        //设置可以加载JavaScript的代码
        setTtings.setJavaScriptEnabled(true);
        //优先加载缓存
        setTtings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //不加载缓存
        setTtings.setCacheMode(WebSettings.LOAD_NO_CACHE);




        //是否支持缩放，true是支持 false是不支持
        setTtings.setSupportZoom(true);

        //这个方法使用后，网页就会在自己浏览器中显示出来
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

        });
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //super.onProgressChanged(view, newProgress);
                if(newProgress==100){
                    //数据加载完毕

                    //这里面我们可以将进度条或者对话框dismiss掉
                }else{
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
            Toast.makeText(MyExtensionActivity.this,"成功了",Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(MyExtensionActivity.this,"失败"+t.getMessage(),Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(MyExtensionActivity.this,"取消了",Toast.LENGTH_LONG).show();

        }
    };
}
