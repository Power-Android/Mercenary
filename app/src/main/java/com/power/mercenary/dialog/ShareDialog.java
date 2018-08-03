package com.power.mercenary.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.power.mercenary.R;
import com.power.mercenary.utils.TUtils;
import com.power.mercenary.view.BaseDialog;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

/**
 * admin  2018/7/26 wan
 */
public class ShareDialog extends PopupWindow {

    private Activity activity;
    private LayoutInflater inflater;
    public View defaultView;

    private LinearLayout weixin;
    private LinearLayout weixinP;
    private LinearLayout qq;
    private LinearLayout qzone;
    private LinearLayout sina;
    private TextView cancel;

    public ShareDialog(Activity activity, String title, String content, String img, String id) {
        super(activity);
        this.activity = activity;
        initPopupWindow(title, content, img, id);
    }

    public void initPopupWindow(final String title, final String content, final String img, final String id) {
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
                TUtils.showCustom(activity, "点击微信");
                showWx(img, title, content, id);
            }
        });

        weixinP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TUtils.showCustom(activity, "点击微信朋友圈");
                showPyq(img, title, content, id);
            }
        });

        qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TUtils.showCustom(activity, "点击QQ");
                showQQ(img, title, content, id);
            }
        });

        qzone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TUtils.showCustom(activity, "点击QQ空间");
                showQzone(img, title, content, id);
            }
        });

        sina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TUtils.showCustom(activity, "点击微博");
                showSina(img, title, content, id);
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

    public void showQQ(String pic, String title, String content, String id) {
        UMImage image = new UMImage(activity, "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=4267222417,1017407570&fm=200&gp=0.jpg");//网络图片
        UMImage thumb = new UMImage(activity, pic + "");
        image.setThumb(thumb);
        UMWeb web = new UMWeb("http://114.215.83.139/chushishare/fresh.html?id=" + id);
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
                .setCallback(listener)
                .share();
    }

    public void showWx(String pic, String title, String content, String id) {
        UMImage image = new UMImage(activity, "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=4267222417,1017407570&fm=200&gp=0.jpg");//网络图片
        UMImage thumb = new UMImage(activity, pic + "");
        image.setThumb(thumb);
        UMWeb web = new UMWeb("http://114.215.83.139/chushishare/fresh.html?id=" + id);
        web.setTitle(title + "");//标题
        web.setThumb(thumb);  //缩略图
        web.setDescription(content + "");//描述
        //注意在新浪平台，缩略图属于必传参数，否则会报错
        ShareAction shareAction = new ShareAction(activity);
        shareAction
                .setPlatform(SHARE_MEDIA.WEIXIN)//传入平台
                .withText("hello")//分享内容
                .withMedia(image)
                .withMedia(web)
                .setCallback(listener)
                .share();
    }

    public void showPyq(String pic, String title, String content, String id) {
        UMImage image = new UMImage(activity, "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=4267222417,1017407570&fm=200&gp=0.jpg");//网络图片
        UMImage thumb = new UMImage(activity, pic + "");
        image.setThumb(thumb);
        UMWeb web = new UMWeb("http://114.215.83.139/chushishare/fresh.html?id=" + id);
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
                .setCallback(listener)
                .share();
    }

    public void showQzone(String pic, String title, String content, String id) {
        UMImage image = new UMImage(activity, "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=4267222417,1017407570&fm=200&gp=0.jpg");//网络图片
        UMImage thumb = new UMImage(activity, pic + "");
        image.setThumb(thumb);
        UMWeb web = new UMWeb("http://114.215.83.139/chushishare/fresh.html?id=" + id);
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
                .setCallback(listener)
                .share();
    }

    public void showSina(String pic, String title, String content, String id) {
        UMImage image = new UMImage(activity, "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=4267222417,1017407570&fm=200&gp=0.jpg");//网络图片
        UMImage thumb = new UMImage(activity, pic + "");
        image.setThumb(thumb);
        UMWeb web = new UMWeb("http://114.215.83.139/chushishare/fresh.html?id=" + id);
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
                .setCallback(listener)
                .share();
    }

    UMShareListener listener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            TUtils.showCustom(activity, "onStart");
        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            TUtils.showCustom(activity, "");
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            TUtils.showCustom(activity, "" + throwable.getMessage());
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            TUtils.showCustom(activity, "");
        }
    };
}
