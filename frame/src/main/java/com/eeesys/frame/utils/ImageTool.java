package com.eeesys.frame.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

/**
 * 图片缓存、图片缩放、释放图片资源
 */
public class ImageTool {
	static final String TAG = "ImageCache";
	static final LruCache<Object, BitmapDrawable> memoryCache; 
	static {
		int maxMemory = (int) Runtime.getRuntime().maxMemory();
		int cacheSize = maxMemory / 8;
		memoryCache = new LruCache<Object, BitmapDrawable>(cacheSize) {

			@Override
			protected int sizeOf(Object key, BitmapDrawable value) {
				Bitmap bmp = value.getBitmap();
				return bmp.getRowBytes() * bmp.getHeight();
			}
		};
	}

	/**
	 * 加入缓存
	 * @param key
	 * @param value
	 */
	public static void addToCache(Object key, BitmapDrawable value) {
		if (null == getCache(key)) {
			memoryCache.put(key, value);
		}
	}
    
	public static BitmapDrawable getCache(Object key) {
		return memoryCache.get(key);
	}
	
	public static BitmapDrawable getCache(Context context, int resourceId, boolean isScale ) {
		BitmapDrawable drawable = getCache(resourceId);
		if(drawable != null){
			return drawable;
		}else{
			setImageCache(context, resourceId, isScale);
			return getCache(resourceId);
		}
	}

	public static void removeCache(Object key) {
		if (memoryCache != null) {
			BitmapDrawable bmp = memoryCache.remove(key);
			if (bmp != null) {
				bmp.getBitmap().recycle();
				L.d(key + "释放");
			}
		}
	}

	public static void clearImageCache() {
		if (memoryCache != null) {
			memoryCache.evictAll();
		}
	}
	
	/**
	 * 对图片简单处理后放入缓存
	 * @param c
	 * @param resourceId
	 * @param isScale 
	 */
	private static void setImageCache(Context c, int resourceId, boolean isScale){
		if(getCache(resourceId) == null){
			L.d(resourceId + "加入缓存");
			addToCache(resourceId, getBitmapDrawable(c, resourceId, isScale));
		}
	}
	
	/**
	 * 让图片缩放到指定大小后放入内存
	 * @param c
	 * @param resourceId
	 * @param width
	 * @param height
	 */
	private static void setImageCache_adjustView(Context c, int resourceId, int width, int height){
		if(getCache(resourceId) == null){
			addToCache(resourceId, scale(c, resourceId, width, height));
		}
	}
	
	/**
	 * 给imageview设置图片
	 * @param c
	 * @param resourceId 图片资源id
	 * @param view    imageview
	 * @param isScale 图片是否需要简单处理
	 */
	public static void setImageBitmapDrawable(Context c, int resourceId, ImageView view,
			boolean isScale){
		setImageCache(c, resourceId, isScale);
		view.setImageDrawable(getCache(resourceId));
	}
	
	/**
	 * 给imageview设置图片,该方法会对图片做适应view大小的缩放
	 * @param c
	 * @param resourceId 图片资源id
	 * @param view  imageview
	 * @param width 图片要缩放的宽度
	 * @param height 图片要缩放的高度
	 */
	public static void setImageBitmapDrawable_adjustView(Context c, int resourceId, ImageView view, int width, int height){
		setImageCache_adjustView(c, resourceId, width, height);
		view.setImageDrawable(getCache(resourceId));
	}
	
	/**
	 * 给imageview设置背景
	 * @param c
	 * @param resourceId 图片资源id
	 * @param view    imageview
	 * @param isScale 图片是否需要简单处理
	 */
	@SuppressWarnings("deprecation")
	public static void setImageBackgroundDrawable(Context c, int resourceId, ImageView view,
			boolean isScale){
		setImageCache(c, resourceId, isScale);
		view.setBackgroundDrawable(getCache(resourceId));
	}
	
	/**
	 * 给imageview设置背景,该方法会对图片做适应view大小的缩放
	 * @param c
	 * @param resourceId 图片资源id
	 * @param view  imageview
	 * @param width 图片要缩放的宽度
	 * @param height 图片要缩放的高度
	 */
	@SuppressWarnings("deprecation")
	public static void setImageBackgroundDrawable_adjustView(Context c, int resourceId, ImageView view, int width, int height){
		setImageCache_adjustView(c, resourceId, width, height);
		view.setBackgroundDrawable(getCache(resourceId));
	}
	
	
	/**
	 * 为控件添加可变背景
	 * 
	 * @param c
	 *            上下文
	 * @param resourceId
	 *            资源id
	 * @param view
	 *            控件
	 */
	@SuppressWarnings("deprecation")
	public static void setStateListDrawable(Context c, int resourceId, View view) {
		if (view != null) {
			Drawable d = c.getResources().getDrawable(resourceId);
			if (d != null) {
				view.setBackgroundDrawable(d);
			}
		}
	}
	
	/**
	 * 为ImageView添加可变图片
	 * 
	 * @param c
	 *            上下文
	 * @param resourceId
	 *            资源id
	 * @param view
	 *            控件
	 */
	public static void setStateListDrawable(Context c, int resourceId, ImageView view) {
		if (view != null) {
			Drawable d = c.getResources().getDrawable(resourceId);
			if (d != null) {
				view.setImageDrawable(d);
			}
		}
	}
	
	/**
	 * 释放图片资源(可变图片) 
	 * @param view
	 */
	@SuppressWarnings("deprecation")
	public static void recyleStateListDrawable(View view) {
		if (view != null) {
			StateListDrawable bd = (StateListDrawable) view.getBackground();
			view.setBackgroundResource(0);
			view.setBackgroundDrawable(null);
			if (bd != null) {
				bd.setCallback(null);
				((BitmapDrawable) bd.getCurrent()).getBitmap().recycle();
			}
		}
	}
	
	public static void recyleStateListDrawable(ImageView imageView) {
		if (imageView != null) {
			StateListDrawable bd = (StateListDrawable) imageView.getDrawable();
			imageView.setImageResource(0);
			imageView.setImageDrawable(null);
			if (bd != null) {
				bd.setCallback(null);
				((BitmapDrawable) bd.getCurrent()).getBitmap().recycle();
			}
		}
	}

	/**
	 * 获取图片
	 * 
	 * @param c
	 *            上下文
	 * @param resourceId
	 *            资源id
	 * @param view
	 *            控件
	 */
	public static BitmapDrawable getBitmapDrawable(Context c, int resourceId, boolean isScale) {
		BitmapDrawable bd = null;
		if (isScale) {
			bd = scale(c, resourceId);
		} else {
			Bitmap map = BitmapFactory.decodeResource(c.getResources(),
					resourceId);
			bd = new BitmapDrawable(c.getResources(), map);
		}
		return bd;
	}

	/**
	 * 对图片资源进行设置
	 * 
	 * @param c
	 *            上下文
	 * @param id
	 *            资源ID
	 */
	private static BitmapDrawable scale(Context c, int id) {
		Options opt = new Options();
		opt.inJustDecodeBounds = false;
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		Bitmap map = BitmapFactory.decodeResource(c.getResources(), id, opt);
		return new BitmapDrawable(c.getResources(), map);
	}

	/**
	 * 
	 * @param c
	 *            上下文
	 * @param id
	 *            资源ID
	 * @param image
	 * @return BitmapDrawable
	 */
	public static BitmapDrawable scale(Context c, int id, int width, int height) {
		Options opt = new Options();
		opt.inJustDecodeBounds = true;
		Bitmap map = BitmapFactory.decodeResource(c.getResources(), id, opt);
		int bitmapWidth = opt.outWidth;
		int bitmapHeight = opt.outHeight;

		int scaleX = bitmapWidth / width;
		int scaleY = bitmapHeight / height;
		int scale = 1;
		if (scaleX > scaleY && scaleY > 1) {
			scale = scaleX;
		}
		if (scaleY > scaleX && scaleX > 1) {
			scale = scaleY;
		}
		opt.inSampleSize = scale;
		opt.inJustDecodeBounds = false;
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		map = BitmapFactory.decodeResource(c.getResources(), id, opt);
		return new BitmapDrawable(c.getResources(), map);
	}
	
	
	/**
	 * 输出图片Uri
	 * 
	 * @param context
	 * @return
	 */
	public static Uri getOutputMediaFileUri(Context context) {
		return Uri.fromFile(getOutputMediaFile(context));
	}

	/**
	 * 输出图片路径
	 * 
	 * @param context
	 * @return
	 */
	public static File getOutputMediaFile(Context context) {

		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				context.getPackageName());

		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d(context.getPackageName(), "failed to create directory");
				return null;
			}
		}
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA)
				.format(new Date());
		File mediaFile = new File(mediaStorageDir.getPath() + File.separator
				+ "IMG_" + timeStamp + ".jpg");

		return mediaFile;
	}

	/**
	 * 得到图片路径
	 * 
	 * @param uri
	 * @param activity
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String getImageLoad(Uri uri, Activity activity) {
		String[] proj = { MediaStore.Images.Media.DATA };
		String imagePath = null;
		Cursor cursor = activity.managedQuery(uri, proj, null, null, null);
		if (cursor != null) {
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			if (cursor.getCount() > 0 && cursor.moveToFirst()) {
				imagePath = cursor.getString(column_index);
			}
		}
		return imagePath;
	}

}
