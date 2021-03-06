package com.power.mercenary.utils;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * admin  2018/7/11 wan
 */
public class MercenaryUtils {

    public static List<String> stringToList(String strList){
        if (TextUtils.isEmpty(strList))
            return null;

        String d[] = strList.split("\\|");
        List<String> list = new ArrayList();

        for (int i = 0; i < d.length; i++) {
            list.add(d[i]);
        }
        return list;
    }

    public static List<String> string3ToList(String strList){
        if (TextUtils.isEmpty(strList))
            return null;

        String d[] = strList.split("\\|");
        List<String> list = new ArrayList();

        for (int i = 0; i < d.length; i++) {
            list.add(Urls.BASEIMGURL + d[i]);
        }
        return list;
    }

    public static List<String> string2ToList(String strList){
        if (TextUtils.isEmpty(strList))
            return null;

        String d[] = strList.split(",");
        List<String> list = new ArrayList();

        for (int i = 0; i < d.length; i++) {
            list.add(d[i]);
        }
        return list;
    }
}
