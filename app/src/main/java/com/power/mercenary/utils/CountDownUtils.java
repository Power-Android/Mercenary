package com.power.mercenary.utils;

import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;

/**
 * 倒计时工具类 edittext 设置点击hint消失
 * 
 * @author ls
 * 
 */
public class CountDownUtils extends CountDownTimer {
	private TextView tv;

	/** 倒计时 **/
	public CountDownUtils(long millisInFuture, long countDownInterval,
			TextView tv) {
		super(millisInFuture, countDownInterval);// 参数依次为�?�时�?,和计时的时间间隔
		// TODO Auto-generated constructor stub
		this.tv = tv;
	}

	// 计时过程显示
	@Override
	public void onTick(long millisUntilFinished) {
		// TODO Auto-generated method stub
		tv.setEnabled(false);
		tv.setText("重新获取("+millisUntilFinished / 1000+"s)");
	}

	// 计时结束
	@Override
	public void onFinish() {
		// TODO Auto-generated method stub
		tv.setText("重新验证");
		tv.setEnabled(true);
	}

}
