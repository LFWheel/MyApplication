package com.eeesys.frame.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastTool {
	
	private static Toast toast;
	public static final int SHORT = Toast.LENGTH_SHORT;
	public static final int LONG = Toast.LENGTH_LONG;
	
	private ToastTool()
	{
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}
	
	/**
     * 显示Toast
     * @param c 上下文
     * @param msg 显示消息
     */	
	public static void show(Context context, Object obj, int showLength){
		if(null != toast){
			toast.cancel();
		}
		if(showLength == SHORT || showLength == LONG){
			toast =  Toast.makeText(context, Tools.Object2String(context, obj), showLength);
		}else{
			toast = Toast.makeText(context, Tools.Object2String(context, obj), SHORT);
		}
		toast.show();
	}
	
	/**
     * 显示Toast
     * @param c 上下文
     * @param msg 显示消息
     */	
	public static void show(Context context, Object obj){	
		show(context, obj, SHORT);
	}
	
}
