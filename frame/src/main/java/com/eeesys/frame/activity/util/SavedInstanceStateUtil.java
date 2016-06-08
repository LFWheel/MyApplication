package com.eeesys.frame.activity.util;

import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;

import com.eeesys.frame.model.BundleParam;
import com.eeesys.frame.utils.IntentTool;


public class SavedInstanceStateUtil {
	private final static String TAG = "com.eeesys.frame.activity.impl.savedInstanceState";
	private SavedInstanceStateUtil(){
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}
	
	/**
	 * activity销毁后再建时从savedInstanceState中取值
	 * @param bundle
	 * @param savedInstanceState
	 */
	public static void getBundle(HashMap<String, Object> bundle, Bundle savedInstanceState) {
		if(savedInstanceState != null && savedInstanceState.containsKey(TAG) && savedInstanceState.getParcelable(TAG) != null){
			if(((BundleParam)savedInstanceState.getParcelable(TAG)).getMap() != null)
			    bundle = ((BundleParam)savedInstanceState.getParcelable(TAG)).getMap();
		}
	}
	
	/**
	 * 从上个界面传来的参数不为空时，activity销毁时保存状态
	 * @param savedInstanceState
	 * @param activity
	 */
	public static void stayBundle(Bundle savedInstanceState, Activity activity){
		savedInstanceState.putParcelable(TAG, IntentTool.getBundleParam(activity));
	}

}
