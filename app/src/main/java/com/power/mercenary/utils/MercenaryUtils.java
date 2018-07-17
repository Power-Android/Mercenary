package com.power.mercenary.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * admin  2018/7/11 wan
 */
public class MercenaryUtils {

    public static List<String> stringToList(String strList){
        String d[] = strList.split("\\|");
        List<String> list = new ArrayList();

        for (int i = 0; i < d.length; i++) {
            list.add(d[i]);
        }
        return list;
    }

    public static List<String> string2ToList(String strList){
        String d[] = strList.split(",");
        List<String> list = new ArrayList();

        for (int i = 0; i < d.length; i++) {
            list.add(d[i]);
        }
        return list;
    }
}
