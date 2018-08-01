package com.power.mercenary.view.chatrefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ListView;
import android.widget.Toast;

/**
 * admin  2018/7/24 wan
 */
public class PullListView extends ListView {
    private float first;

    private Context mContext;

    public PullListView(Context context) {
        super(context);
        this.mContext = mContext;
    }

    public PullListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = mContext;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                first = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float now = ev.getY();

                if (now - first > 10) {
                    Log.v("======>>", "开始下拉加载");
                }

                break;
        }
        return super.onTouchEvent(ev);
    }
}
