package com.eeesys.frame.utils;

import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class ViewUtils {
	private ViewUtils()
	{
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}
	
	/**
	 * 判断是否在控件外部
	 */
	public static boolean isOutSide(View view, MotionEvent event) {
		if (view != null) {
			int[] leftTop = { 0, 0 };
			// 获取输入框当前的location位置
			view.getLocationInWindow(leftTop);
			int left = leftTop[0];
			int top = leftTop[1];
			int bottom = top + view.getHeight();
			int right = left + view.getWidth();
			if (event.getX() > left && event.getX() < right
					&& event.getY() > top && event.getY() < bottom) {
				// 点击的是输入框区域，保留点击EditText的事件
				return false;
			} else {
				return true;
			}
		}
		return false;
	}
	
	// 计算文字的宽度
		public static int computeMaxStringWidth(int currentMax, String[] strings,
				Paint p) {
			float maxWidthF = 0.0f;
			int len = strings.length;
			for (int i = 0; i < len; i++) {
				// float width = getTextWidth(p, strings[i]);
				float width = p.measureText(strings[i]);
				maxWidthF = Math.max(width, maxWidthF);
			}
			int maxWidth = (int) (maxWidthF + 0.5);
			if (maxWidth < currentMax) {
				maxWidth = currentMax;
			}
			return maxWidth;
		}

		// 计算文字宽度
		public static int getTextWidth(Paint paint, String str) {
			int iRet = 0;
			if (str != null && str.length() > 0) {
				int len = str.length();
				float[] widths = new float[len];
				paint.getTextWidths(str, widths);
				for (int j = 0; j < len; j++) {
					iRet += (int) Math.ceil(widths[j]);
				}
			}
			return iRet;
		}
}
