package com.power.mercenary.dialog;


import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.power.mercenary.MyApplication;
import com.power.mercenary.R;
import com.power.mercenary.utils.TUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

/**
 * admin  2018/7/26 wan
 */
public class ShareDialog1 extends PopupWindow {

    private static final String TAG = "ShareDialog1";

    private Activity activity;
    private LayoutInflater inflater;
    public View defaultView;

    private LinearLayout weixin;
    private LinearLayout weixinP;
    private LinearLayout qq;
    private LinearLayout qzone;
    private LinearLayout sina;
    private TextView cancel;

    public ShareDialog1(Activity activity, String title, String content) {
        super(activity);
        this.activity = activity;
        initPopupWindow(title, content);
    }

    public void initPopupWindow(final String title, final String content) {
        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        defaultView = inflater.inflate(R.layout.sharing_pop_item_view, null);
        defaultView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        setContentView(defaultView);

        weixin = defaultView.findViewById(R.id.dialog_share_wx);
        weixinP = defaultView.findViewById(R.id.dialog_share_pyq);
        qq = defaultView.findViewById(R.id.dialog_share_qq);
        qzone = defaultView.findViewById(R.id.dialog_share_qzone);
        sina = defaultView.findViewById(R.id.dialog_share_wb);
        cancel = defaultView.findViewById(R.id.dialog_share_cancel);

        weixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWx(title, content);
            }
        });

        weixinP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPyq(title, content);
            }
        });

        qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showQQ(title, content);
            }
        });

        qzone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showQzone(title, content);
            }
        });

        sina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSina(title, content);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(FrameLayout.LayoutParams.WRAP_CONTENT);
        setAnimationStyle(R.style.popwin_anim_style);
        setBackgroundDrawable(new ColorDrawable(activity.getResources().getColor(android.R.color.transparent)));
        setFocusable(true);
        // setOutsideTouchable(true);
        update();

    }


    /**
     * @return pop的View
     */
    public View getDefaultView() {
        return defaultView;
    }

    public void showQQ( String title, String content) {
        UMImage image = new UMImage(activity, "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=4267222417,1017407570&fm=200&gp=0.jpg");//网络图片
        UMImage thumb = new UMImage(activity, R.drawable.yongbingicon);
        image.setThumb(thumb);
        UMWeb web = new UMWeb("http://yb.dashuibei.com/register/extension.html?token="+ MyApplication.getUserToken());
        web.setTitle(title + "");//标题
        web.setThumb(thumb);  //缩略图
        web.setDescription(content + "");//描述
        //注意在新浪平台，缩略图属于必传参数，否则会报错
        ShareAction shareAction = new ShareAction(activity);
        shareAction
                .setPlatform(SHARE_MEDIA.QQ)//传入平台
                .withText("hello")//分享内容
                .withMedia(image)
                .withMedia(web)
                .setCallback(shareListener)
                .share();
    }

    public void showWx( String title, String content) {

        //Log.e(TAG, "showWx: 你进来了" );

        UMImage image = new UMImage(activity, "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=4267222417,1017407570&fm=200&gp=0.jpg");//网络图片

        UMImage thumb = new UMImage(activity, R.drawable.yongbingicon);
        image.setThumb(thumb);
        UMWeb web = new UMWeb("http://yb.dashuibei.com/register/extension.html?token="+ MyApplication.getUserToken());
        web.setTitle(title + "");//标题
        web.setThumb(thumb);  //缩略图
        web.setDescription(content + "");//描述

        //Log.e(TAG, "showWx: "+content.length() );

        //注意在新浪平台，缩略图属于必传参数，否则会报错
        new ShareAction(activity)
                .setPlatform(SHARE_MEDIA.WEIXIN)//传入平台
                .withText("hello")//分享内容
                .withMedia(image)
                .withMedia(web)
                .setCallback(shareListener)//回调监听器
                .share();

    }

    public void showPyq( String title, String content) {
        UMImage image = new UMImage(activity, "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=4267222417,1017407570&fm=200&gp=0.jpg");//网络图片
        UMImage thumb = new UMImage(activity, R.drawable.yongbingicon);
        image.setThumb(thumb);
        UMWeb web = new UMWeb("http://yb.dashuibei.com/register/extension.html?token="+ MyApplication.getUserToken());
        web.setTitle(title + "");//标题
        web.setThumb(thumb);  //缩略图
        web.setDescription(content + "");//描述
        //注意在新浪平台，缩略图属于必传参数，否则会报错
        ShareAction shareAction = new ShareAction(activity);
        shareAction
                .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)//传入平台
                .withText("hello")//分享内容
                .withMedia(image)
                .withMedia(web)
                .setCallback(shareListener)
                .share();
    }

    public void showQzone( String title, String content) {
        UMImage image = new UMImage(activity, "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=4267222417,1017407570&fm=200&gp=0.jpg");//网络图片
        UMImage thumb = new UMImage(activity, R.drawable.yongbingicon);
        image.setThumb(thumb);
        UMWeb web = new UMWeb("http://yb.dashuibei.com/register/extension.html?token="+ MyApplication.getUserToken());
        web.setTitle(title + "");//标题
        web.setThumb(thumb);  //缩略图
        web.setDescription(content + "");//描述
        //注意在新浪平台，缩略图属于必传参数，否则会报错
        ShareAction shareAction = new ShareAction(activity);
        shareAction
                .setPlatform(SHARE_MEDIA.QZONE)//传入平台
                .withText("hello")//分享内容
                .withMedia(image)
                .withMedia(web)
                .setCallback(shareListener)
                .share();
    }

    public void showSina( String title, String content) {
        UMImage image = new UMImage(activity, "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=4267222417,1017407570&fm=200&gp=0.jpg");//网络图片
        UMImage thumb = new UMImage(activity, R.drawable.yongbingicon);
        image.setThumb(thumb);
        UMWeb web = new UMWeb("http://yb.dashuibei.com/register/extension.html?token="+ MyApplication.getUserToken());
        web.setTitle(title + "");//标题
        web.setThumb(thumb);  //缩略图
        web.setDescription(content + "");//描述
        //注意在新浪平台，缩略图属于必传参数，否则会报错
        ShareAction shareAction = new ShareAction(activity);
        shareAction
                .setPlatform(SHARE_MEDIA.SINA)//传入平台
                .withText("hello")//分享内容
                .withMedia(image)
                .withMedia(web)
                .setCallback(shareListener)
                .share();
    }

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

            Log.e(TAG, "onStart:  开始运行" );


        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {

            Log.e(TAG, "onStart:  成功了" );

            //Toast.makeText(activity,"成功了",Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Log.e(TAG, "onStart:  失败了" );
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Log.e(TAG, "onStart:  取消了" );

        }
    };
}
