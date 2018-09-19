package com.power.mercenary.activity;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.power.mercenary.R;
import com.power.mercenary.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TuiGuangActivity extends BaseActivity {

    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.left_back)
    ImageView leftBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tui_guang);
        ButterKnife.bind(this);
        initWeb();
    }

    @OnClick(R.id.left_back)
    public void onViewClicked() {
        finish();
    }
    private void initWeb() {
        webView.loadUrl("http://yb.dashuibei.com/register/zhunze.html?type=2");

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

}
