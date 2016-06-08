package com.eeesys.frame.app;

import java.util.LinkedList;
import java.util.List;
import org.litepal.tablemanager.Connector;

import android.app.Activity;
import android.app.Application;

import com.eeesys.frame.utils.ImageLoaderTool;
import com.eeesys.frame.utils.ImageTool;
import com.nostra13.universalimageloader.core.ImageLoader;


public class CusApp extends Application {
	private List<Activity> mActivities = new LinkedList<Activity>();

	public List<Activity> getActivities() {
		return mActivities;
	} 

	public void onDestroy() {
		ImageTool.clearImageCache();
		finishActivities();
		android.os.Process.killProcess(android.os.Process.myPid());
		System.exit(0);
	}

	public void finishActivities() {
		for (Activity activity : mActivities) {
			activity.finish();
		}
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		//CrashHandler.getInstance().init(this);
		ImageLoaderTool.initImageLoader(getApplicationContext());
	}
	
	@Override
	public void onLowMemory() {
		super.onLowMemory();
		ImageTool.clearImageCache();
		ImageLoader.getInstance().clearMemoryCache();
	}

}
