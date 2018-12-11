package com.power.mercenary.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.power.mercenary.R;
import com.power.mercenary.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class WebActivity extends BaseActivity {


    /**
     *
     * Web页面
     *
     *
     */


    @BindView(R.id.left_back)
    FrameLayout imgBeak;
    @BindView(R.id.title_text)
    TextView webTitle;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        String result = getIntent().getStringExtra("result");
        String title = getIntent().getStringExtra("title");
        webView = (WebView) findViewById(R.id.webView);
        webTitle.setText(title);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO Auto-generated method stub
                if (newProgress == 100) {
//                    Toast.makeText(WebActivity.this, "网页加载完成", Toast.LENGTH_SHORT).show();

                } else {

                }
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                Toast.makeText(WebActivity.this, "加载出错了", Toast.LENGTH_SHORT).show();
            }
        });
        webView.loadUrl(result);
    }
    @OnClick(R.id.left_back)
    public void onViewClicked() {
        finish();
    }
    public static void invoke(Context context, String result, String title){
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("result", result);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }
}
