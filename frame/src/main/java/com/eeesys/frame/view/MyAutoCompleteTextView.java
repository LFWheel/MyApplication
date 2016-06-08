package com.eeesys.frame.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

public class MyAutoCompleteTextView extends AutoCompleteTextView {

	@SuppressWarnings("unused")
	private int mThreshold;

	public MyAutoCompleteTextView(Context context) {
		super(context);
	}

	public MyAutoCompleteTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyAutoCompleteTextView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void setThreshold(int threshold) {
		if(threshold<0){
			threshold=0;
		}
		mThreshold = threshold;
	}

	@Override
	public boolean enoughToFilter() {
		return true;
	}


}
