package com.power.mercenary.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.io.ByteArrayOutputStream;

public class CompressImageUtils {

    //pressScaleCompress
    //按比例压缩
    public static Bitmap pressScaleCompress(Bitmap bitmap) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();  //创建一个字节数组输出流对象
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, os); //通过bitmap中的compress,将图片压缩到os流对象中.
        //其中第二个参数quality,为100表示不压缩,如果为80,表示压缩百分之20.
        byte[] bt = os.toByteArray(); //将流对象转行成数组
        Bitmap bitmap1 = BitmapFactory.decodeByteArray(bt, 0, bt.length); //将字节数组转换成bitmap图片

        return bitmap1;
    }

    public static Bitmap pressScaleWidthHeightCompress(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postScale(0.6f, 0.6f);  //matrix 设置为0.6f 就是对宽高缩放二分之一
        Bitmap bitmap1 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        return bitmap1;
    }

}
