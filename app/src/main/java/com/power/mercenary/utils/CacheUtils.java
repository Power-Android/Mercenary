package com.power.mercenary.utils;

import android.app.Application;
import android.util.Log;

import java.util.HashMap;

import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.LogInterceptor;
import com.orhanobut.hawk.NoEncryption;


/**
 * Created by   admin on 2018/5/2.
 */

public class CacheUtils {
    /**
     * 内存缓存
     */
    private static HashMap<String, Object> cache = new HashMap<>();

    public static void init(Application app) {
        Hawk.init(app)
                .setEncryption(new NoEncryption())
                .setLogInterceptor(new LogInterceptor() {
                    @Override
                    public void onLog(String message) {
                        //打包时把这个注释
                        Log.v("======>", message);
                    }
                })
//                .setEncryptionMethod(HawkBuilder.EncryptionMethod.MEDIUM)
//                .setStorage(HawkBuilder.newSqliteStorage(app))
//                .setLogLevel(LogLevel.FULL)
                .build();
    }


    public static synchronized void put(String key, Object obj) {
        if (Hawk.put(key, obj)) {
            cache.put(key, obj);
        }
    }


    public static synchronized <T> T get(String key) {
        T obj = (T) cache.get(key);
        if (obj != null) {
            return obj;
        }
        return Hawk.get(key);
    }

    public static void remove(String key) {
        Hawk.delete(key);
        if (cache != null) {
            cache.remove(key);
        }
    }

    public static void removeAll() {
        Hawk.deleteAll();
        if (cache != null) {
            cache.clear();
        }
    }
}
