package com.eeesys.frame.activity.impl;

import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.eeesys.frame.activity.inter.TActivity;
import com.eeesys.frame.activity.inter.TitleWrapper;
import com.eeesys.frame.activity.util.SavedInstanceStateUtil;
import com.eeesys.frame.app.CusApp;
import com.eeesys.frame.utils.IntentTool;

public abstract class JActivity extends Activity implements TActivity,
		TitleWrapper {
	protected HashMap<String, Object> bundle // 从上个页面传递来的map集合，用于存放参数
			, param; // 传递到下个页面的参数放入该map

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		initOther();
		super.onCreate(savedInstanceState);
		SavedInstanceStateUtil.getBundle(bundle, savedInstanceState);
		createView();
	}
	
	protected void initOther(){
		((CusApp)getApplication()).getActivities().add(this);
		param = new HashMap<String, Object>();
		bundle = IntentTool.getParameter(this);
	}

	/**
	 * 创建视图 如果getLayoutId()<= 0，则手动创建，否则就通过layoutId创建
	 */
	private void createView() {
		requestWindowFeature();
		int layoutId = getLayoutId();
		if(layoutId > 0){
			setContentView(layoutId);
		}
		initContentView();
		if(hasCustomTitle()){
			setWindowFeature();
		}
		initTitleView();
	}
	
	//通过xml创建title
	private void setWindowFeature() {
		int layoutId = getTitleViewId();
		if(layoutId <= 0){
			throw new RuntimeException("getTitleViewId()返回一个资源id");
		}
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, layoutId);
	}

	// 设置窗体属性
	protected void requestWindowFeature() {
	}	

	@Override
	public abstract boolean hasCustomTitle();

	@Override
	public abstract int getTitleViewId();
	
	@Override
	public abstract void initTitleView();

	@Override
	public abstract int getLayoutId();
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		SavedInstanceStateUtil.stayBundle(outState, this);
	}

}
