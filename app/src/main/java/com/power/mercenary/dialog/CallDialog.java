package com.power.mercenary.dialog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.power.mercenary.R;
import com.power.mercenary.view.BaseDialog;

/**
 * admin  2018/8/15 wan
 */
public class CallDialog {
    public static void showComplaintDialog(final Context context) {
        BaseDialog.Builder builder = new BaseDialog.Builder(context);
        final BaseDialog dialog = builder.setViewId(R.layout.dialog_publish_sh)
                .setPaddingdp(20, 0, 20, 0)
                .setGravity(Gravity.CENTER)
                .setAnimation(R.style.bottom_tab_style)
                .setWidthHeightpx(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                .isOnTouchCanceled(true)
                .builder();

        TextView textView = dialog.getView(R.id.dialog_title);

        textView.setText(context.getString(R.string.is_call) + context.getString(R.string.phone) + "?");

        dialog.getView(R.id.dialog_publish_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getView(R.id.dialog_publish_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + context.getString(R.string.phone));
                intent.setData(data);
                context.startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
