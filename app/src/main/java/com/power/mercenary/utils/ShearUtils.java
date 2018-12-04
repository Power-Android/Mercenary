package com.power.mercenary.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * @author BySevenroup小小
 * @create 2018/12/3 14:17
 * @Description
 */
public class ShearUtils {

    private static ClipboardManager mClipboardManager;
    private static ClipData.Item item;

    public static String isShearContent(Context context) {
        if (context != null) {
            mClipboardManager = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
            ClipData mClipData = mClipboardManager.getPrimaryClip();
            if (null != mClipData) {
                item = mClipData.getItemAt(0);
                if (TextUtils.isEmpty(item.getText().toString())){
                    return null;
                }else{
                    return item.getText().toString();
                }
            }

        }
        return null;
    }
    //给剪切板赋值为空
    public  static  void fuShear(Context context,String str){
        mClipboardManager.setText(str);
    }
}
