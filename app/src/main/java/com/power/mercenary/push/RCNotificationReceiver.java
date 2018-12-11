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
    /*private Intent intent;
    private static final String TAG = "RCNotificationReceiver";
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==0){
                Context context = (Context) msg.obj;

                context.getApplicationContext().startActivity(intent);
            }
        }
    };*/


    @Override
    public boolean onNotificationMessageArrived(Context context, PushNotificationMessage pushNotificationMessage) {
        Log.i("pushid", "刘彪"+pushNotificationMessage.getPushId());

        return false;
    }

    @Override
    public boolean onNotificationMessageClicked(Context context, PushNotificationMessage pushNotificationMessage) {
      /*  intent = new Intent();
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
        Log.i(TAG, "onNotificationMessageClicked: 111111111111111111111");
        Uri.Builder uriBuilder = Uri.parse("rong://" + context.getPackageName()).buildUpon();
        uriBuilder.appendPath("push_message")
                .appendQueryParameter("targetId", pushNotificationMessage.getTargetId())
                .appendQueryParameter("pushData", pushNotificationMessage.getPushData())
                .appendQueryParameter("pushId", pushNotificationMessage.getPushId())
                .appendQueryParameter("extra", pushNotificationMessage.getExtra());
            Message message = new Message();
            message.obj = context;
            message.what = 0;
        handler.sendMessage(message);*/

        return false;
    }
}
