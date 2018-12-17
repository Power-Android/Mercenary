package com.power.mercenary;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.multidex.MultiDex;
import android.text.LoginFilter;
import android.text.TextUtils;
import android.util.Config;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.lljjcoder.style.citylist.utils.CityListLoader;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.DBCookieStore;
import com.lzy.okgo.https.HttpsUtils;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.power.mercenary.bean.user.TokenInfo;
import com.power.mercenary.bean.user.UserInfo;
import com.power.mercenary.data.CacheConstants;
import com.power.mercenary.data.EventConstants;
import com.power.mercenary.event.EventUtils;
import com.power.mercenary.utils.CacheUtils;
import com.power.mercenary.utils.TUtils;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.message.TextMessage;
import io.rong.push.core.MessageHandleService;
import io.rong.push.notification.PushNotificationMessage;
import okhttp3.OkHttpClient;

import static io.rong.push.notification.PushNotificationMessage.*;

/**
 * Created by Administrator on 2017/8/24.
 */

public class MyApplication extends Application {
    private static MyApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        CacheUtils.init(this);
        SDKInitializer.initialize(this);
        setLogger();
        setOkGo();//OkGo----第三方网络框架
        //imageLoader
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(configuration);
        initRongClound();
        initUM();
        CityListLoader.getInstance().loadProData(this);
        Fresco.initialize(this);

    }

    private void ShearPlate() {

    }

    @Override
    public void onTrimMemory(int level) {//判断程序是否处于后台的判断方法
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_UI_HIDDEN) {//调用融云断开连接的方法让他处于后台也收到消息
            RongIMClient.getInstance().disconnect();
        }
    }



    private void initUM() {

        UMConfigure.init(this, "5a12384aa40fa3551f0001d1"
                , "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");//58edcfeb310c93091c000be2 5965ee00734be40b580001a0
        UMShareAPI.get(this);
        UMConfigure.setLogEnabled(true);
       PlatformConfig.setWeixin("wxc7c5d9c2e3cab3f4", "863814d78b2c71de6924193f1b7d1ec0");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad", "http://sns.whalecloud.com");
    }

    private void initRongClound() {
        RongIMClient.init(this);

        if (CacheUtils.get(CacheConstants.TYPE_LOGIN) != null) {
            TokenInfo tokenInfo = CacheUtils.get(CacheConstants.TYPE_LOGIN);
            if (!TextUtils.isEmpty(tokenInfo.yun_token)) {
                RongIMClient.connect(tokenInfo.yun_token, new RongIMClient.ConnectCallback() {
                    @Override
                    public void onTokenIncorrect() {
                    }

                    @Override
                    public void onSuccess(String s) {
                    }
                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {
                    }
                });
            }
        }

        RongIMClient.setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageListener() {
            @Override
            public boolean onReceived(Message message, int i) {
                MessageContent content = message.getContent();

                if (content instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) content;
                    if (CacheUtils.get(CacheConstants.MESSAGE_SWITCHBUTTON) != null) {

                        String switchStr = CacheUtils.get(CacheConstants.MESSAGE_SWITCHBUTTON);

                        if (TextUtils.equals(switchStr, "true")) {
                            RongIMClient.getInstance().deleteMessages(new int[]{message.getMessageId()}, new RongIMClient.ResultCallback<Boolean>() {
                                @Override
                                public void onSuccess(Boolean aBoolean) {

                                }

                                @Override
                                public void onError(RongIMClient.ErrorCode errorCode) {

                                }
                            });

                            return true;
                        }
                    }

                    //int   消息id
                    CacheUtils.put(CacheConstants.MESSAGEID, message.getMessageId());
                    if (CacheUtils.get(CacheConstants.IS_IN_CHAT) != null) {
                        String isChat = CacheUtils.get(CacheConstants.IS_IN_CHAT);
                        if (TextUtils.equals(isChat, message.getSenderUserId())) {
                            EventBus.getDefault().post(new EventUtils(EventConstants.TYPE_MESSAGE_IN_SHOW, message));
                        }
                    }

                    EventBus.getDefault().post(new EventUtils(EventConstants.TYPE_MESSAGE_SHOW, message));

                    Log.v("======>>", "getTargetId: " + message.getTargetId() +
                            "\ngetConversationType().getName(): " + message.getConversationType().getName() +
                            "\ngetMessageId: " + message.getMessageId() +
                            "\ngetSentTime: " + message.getSentTime() +
                            "\ngetSenderUserId: " + message.getSenderUserId() +
                            "\ngetContent: " + textMessage.getContent() +
                            "\n未读数:" + i);

//                    EventBus.getDefault().post(new EventUtils(EventConstants.TYPE_REFRESH_MESSAGE, message));

                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static Application getInstance() {
        return application;
    }

    public static Context getGloableContext() {
        return application.getApplicationContext();
    }

    /**
     * logger-----第三方日志打印
     */
    private void setLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                //                .methodCount(3)         // (Optional) How many method line to show. Default 2
                //                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
                .tag("TAG")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                //                .logStrategy(logStrategy)   // (Optional) Changes the log strategy to print out. Default LogCat
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
        /**
         * 隐藏Log日志---上线前打开注释即可
         */
        /*Logger.addLogAdapter(new AndroidLogAdapter(){
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });*/
    }

    /**
     * OkGo------第三方网络请求框架
     */
    private void setOkGo() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        HttpHeaders headers = new HttpHeaders();
        HttpParams params = new HttpParams();
        //log相关
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("TAG");
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setColorLevel(Level.SEVERE);                     //log颜色级别，决定了log在控制台显示的颜色
        builder.addInterceptor(loggingInterceptor);                                 //添加OkGo默认debug日志
        //超时时间设置，默认60秒
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);      //全局的读取超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);     //全局的写入超时时间
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);   //全局的连接超时时间

        //自动管理cookie（或者叫session的保持），以下几种任选其一就行
        //builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));            //使用sp保持cookie，如果cookie不过期，则一直有效
        builder.cookieJar(new CookieJarImpl(new DBCookieStore(this)));              //使用数据库保持cookie，如果cookie不过期，则一直有效
        //builder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));            //使用内存保持cookie，app退出后，cookie消失


        //https相关设置，以下几种方案根据需要自己设置
        //方法一：信任所有证书,不安全有风险
        HttpsUtils.SSLParams sslParams1 = HttpsUtils.getSslSocketFactory();
        //方法二：自定义信任规则，校验服务端证书
        //        HttpsUtils.SSLParams sslParams2 = HttpsUtils.getSslSocketFactory(new SafeTrustManager());
        //方法三：使用预埋证书，校验服务端证书（自签名证书）
        //HttpsUtils.SSLParams sslParams3 = HttpsUtils.getSslSocketFactory(getAssets().open("srca.cer"));
        //方法四：使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
        //HttpsUtils.SSLParams sslParams4 = HttpsUtils.getSslSocketFactory(getAssets().open("xxx.bks"), "123456", getAssets().open("yyy.cer"));
        builder.sslSocketFactory(sslParams1.sSLSocketFactory, sslParams1.trustManager);
        //配置https的域名匹配规则，详细看demo的初始化介绍，不需要就不要加入，使用不当会导致https握手失败
        //        builder.hostnameVerifier(new SafeHostnameVerifier());

        OkGo.getInstance().init(this)                              //必须调用初始化
                .setOkHttpClient(builder.build())                  //建议设置OkHttpClient，不设置将使用默认的
                .setCacheMode(CacheMode.NO_CACHE)                 //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)    //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(3)//全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
                .addCommonHeaders(headers)                         //全局公共头
                .addCommonParams(params);                          //全局公共参数

    }

    /**
     * 网络是否可用
     *
     * @param context
     * @return true 可用 false 不可用
     */
    public static boolean isNetworkAvailable(Context context) {
        try {
            ConnectivityManager cm =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            return null != info && info.isConnected();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getUserToken() {
        TokenInfo tokenInfo = CacheUtils.get(CacheConstants.TYPE_LOGIN);
        if (tokenInfo != null) {
            if (!TextUtils.isEmpty(tokenInfo.token)) {
                return tokenInfo.token;
            }
        }
        return "";
    }

    public static String getUserId() {
        UserInfo userInfo = CacheUtils.get(CacheConstants.USERINFO);
        if (userInfo != null) {
            if (!TextUtils.isEmpty(userInfo.getId())) {
                return userInfo.getId();
            }
        }
        return "";
    }

    public static boolean isLogin() {
        TokenInfo tokenInfo = CacheUtils.get(CacheConstants.TYPE_LOGIN);
        if (tokenInfo != null) {
            if (!TextUtils.isEmpty(tokenInfo.token)) {
                return true;
            }
        }
        return false;
    }

    public static void loginOut() {
        CacheUtils.removeAll();

        RongIMClient.getInstance().clearConversations(new RongIMClient.ResultCallback() {
            @Override
            public void onSuccess(Object o) {

            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        }, Conversation.ConversationType.PRIVATE);
        RongIMClient.getInstance().logout();
    }


}

