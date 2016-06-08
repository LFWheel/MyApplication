package com.eeesys.frame.utils;

import android.content.Context;
import android.hardware.SensorManager;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * 获得手机服务工具类
 * 
 * @author wjk
 * 
 */
public class PhoneService {

	/**
	 * 获得窗口服务
	 * 
	 * @param c
	 *            上下文
	 * @return 窗口服务
	 */

	public static WindowManager getWindowService(Context c) {
		return (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
	}

	/**
	 * 获得LayoutInflater服务
	 * 
	 * @param c
	 *            上下文
	 * @return LayoutInflater
	 */
	public static LayoutInflater getLayoutInflaterService(Context c) {
		return (LayoutInflater) c
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/**
	 * 获得传感器服务
	 * 
	 * @param c
	 *            上下文
	 * @return LayoutInflater
	 */
	public static SensorManager getSensorManager(Context c) {
		return (SensorManager) c.getSystemService(Context.SENSOR_SERVICE);
	}

	/**
	 * 获得软键盘服务
	 * 
	 * @param c
	 *            上下文
	 * @return LayoutInflater
	 */
	public static InputMethodManager getSoftInput(Context c) {
		return (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
	}

}
