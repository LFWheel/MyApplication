package com.eeesys.frame.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * 可上下滚动的listview
 * 
 * @author wjk
 * 
 */
public class CustomListView extends ListView implements Runnable {
	private float mFirstDownY = 0f; // 第一次按下时的坐标
	private int mDistance = 0; // 偏移量
	private int mStep = 0; // 回滚速度
	private boolean mpostive = false; // 记录上拉或下拉
	private boolean isFirst = true; // 是否是第一次显示listview中第一个或最后一个子view
	private int bottom, top; // 初始化时listview的顶部与底部坐标
	private int speed = 1;

	public CustomListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public CustomListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomListView(Context context) {
		super(context);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		top = t;
		bottom = b;
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			isFirst = true;
			if (mFirstDownY == 0f && mDistance == 0) {
				mFirstDownY = ev.getRawY();
			}
			break;
		case MotionEvent.ACTION_CANCEL:

			break;
		case MotionEvent.ACTION_UP:
			if (mDistance != 0) {
				mStep = 1;
				mpostive = (mDistance > 0);
				this.post(this);
				return true;
			}
			mDistance = 0;
			mFirstDownY = 0;
			isFirst = true;
			break;

		case MotionEvent.ACTION_MOVE:
			/*
			 * 若 第一次按下的Y坐标不为0,并且移动时产生不为0的距离，就产生滑动距离一半的空白ListView,
			 * 显示到页面上，若按下坐标为0即相当于没有按下，没有按下滑动自然不会产生。距离还是初始值0
			 */	
			
			if (mFirstDownY != 0f) {
				mDistance = (int) (mFirstDownY - ev.getRawY());
				if ((mDistance < -30 && getFirstVisiblePosition() == 0
						&& getChildAt(0) != null && getChildAt(0).getTop() == top)
						|| (mDistance > 30
								&& getLastVisiblePosition() == getCount() - 1
								&& getChildAt(getLastVisiblePosition()
										- getFirstVisiblePosition()) != null && getChildAt(
								getLastVisiblePosition()
										- getFirstVisiblePosition())
								.getBottom() <= bottom)) {
					if (isFirst) {
						isFirst = false;
						mFirstDownY = ev.getRawY();
						mDistance = 0;
						break;
					}
					
					mDistance /= 2;
					scrollTo(0, mDistance);
					return true;// 同样的，消耗掉这一触摸事件
				}
			}
			mDistance = 0;
			break;
		}	
		return super.onTouchEvent(ev);
	}

	@Override
	public void run() {
		setPressed(false);
		setFocusable(false);
		setFocusableInTouchMode(false);
		mDistance += (mDistance > 0 ? -mStep : mStep);
		scrollTo(0, mDistance);
		if ((mDistance >= 0 && !mpostive) || (mpostive && mDistance <= 0)) {
			scrollTo(0, 0);
			mDistance = 0;
			mFirstDownY = 0;
			mStep = 0;
			speed = 1;
			return;
		}
		mStep += (2 * speed );
		speed ++;
		this.postDelayed(this, 10);
	}

}
