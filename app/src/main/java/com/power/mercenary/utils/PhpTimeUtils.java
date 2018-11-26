package com.power.mercenary.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author BySevenroup小小
 * @create 2018/11/26 11:36
 * @Description
 */
public class PhpTimeUtils {
    public static String phptime(long time) {//处理php接口中十位时间戳安卓转换不合格的静态类
        Date date = new Date(time * 1000);
        long nowTime = System.currentTimeMillis();
        SimpleDateFormat sdf = null;
        if (nowTime - time < 1000 * 60 * 60 * 24) {
            sdf = new SimpleDateFormat("HH:mm");// 1
        } else {
            sdf = new SimpleDateFormat("MM月dd日 HH:mm");
        }
        return sdf.format(date);
    }
}
