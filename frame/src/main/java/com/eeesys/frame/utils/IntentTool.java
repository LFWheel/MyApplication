package com.eeesys.frame.utils;

import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.eeesys.frame.model.BundleParam;

public class IntentTool {
	static final String TAG = "com.android.frame.bundle";
	
	/**
	 * 把参数放入意图
	 * @param activity 当前Activity对象
	 * @param destClass 要跳转的Activity.class
	 * @param param  需要携带的参数
	 * @return
	 */
	public static Intent getIntent(Context context, Class<?> destClass, HashMap<String, Object> param){
		Intent intent = new Intent(context, destClass);
		if(param != null && param.size() > 0){
			intent.putExtra(TAG, new BundleParam().convert(param));
		}
		return intent;
	}
	
	/**
	 * 从意图中获取参数
	 * @param activity 当前activity对象
	 * @return
	 */
	public static HashMap<String, Object> getParameter(Activity activity){
		BundleParam param = getBundleParam(activity);
		return getMap(param);
	}

	public static HashMap<String, Object> getMap(BundleParam param) {
		if(param == null){
			return new HashMap<String, Object>();
		}else{
			return param.getMap();
		}
	}
	
	/**
	 * 从意图中获取参数
	 * @param intent 意图
	 * @return
	 */
	public static HashMap<String, Object> getParameter(Intent intent){
		BundleParam param = getBundleParam(intent);
		return getMap(param);
	}
	
	public static BundleParam getBundleParam(Activity activity){
		return (BundleParam) activity.getIntent().getParcelableExtra(TAG);
	}
	
	public static BundleParam getBundleParam(Intent intent){
		return (BundleParam) intent.getParcelableExtra(TAG);
	}
}
