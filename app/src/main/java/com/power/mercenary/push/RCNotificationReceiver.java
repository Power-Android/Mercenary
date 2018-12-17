package com.power.mercenary.push;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.power.mercenary.utils.SpUtils;
import com.power.mercenary.utils.TUtils;

import org.json.JSONException;
import org.json.JSONObject;


import io.rong.push.notification.PushMessageReceiver;
import io.rong.push.notification.PushNotificationMessage;

import static com.umeng.socialize.utils.DeviceConfig.context;

/**
 * admin  2018/8/15 wan
 */
public class RCNotificationReceiver extends PushMessageReceiver {



    @Override
    public boolean onNotificationMessageArrived(Context context, PushNotificationMessage pushNotificationMessage) {
        Log.i("liubiao", "onNotificationMessageArrived: "+pushNotificationMessage.getSenderId());
        return false;
    }

    @Override
    public boolean onNotificationMessageClicked(Context context, PushNotificationMessage pushNotificationMessage) {
        return false;
    }
}
