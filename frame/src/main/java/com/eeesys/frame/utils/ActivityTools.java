package com.eeesys.frame.utils;

import android.app.Activity;
import android.content.Intent;

public class ActivityTools {
	
	private ActivityTools() {
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");

	}
	
	public static void startActivity(Activity activity, Intent intent, int... anim){
		activity.startActivity(intent);
		if(anim.length == 1){
			activity.overridePendingTransition(anim[0], 0);
		}else if(anim.length == 2){
			activity.overridePendingTransition(anim[0], anim[1]);
		}
	}
	
	public static void finishActivity(Activity activity, Class<?> classType, int... anim){
		Intent intent = new Intent(activity, classType);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		activity.startActivity(intent);
		if(anim.length == 1){
			activity.overridePendingTransition(anim[0], 0);
		}else if(anim.length == 2){
			activity.overridePendingTransition(anim[0], anim[1]);
		}
	}

}
