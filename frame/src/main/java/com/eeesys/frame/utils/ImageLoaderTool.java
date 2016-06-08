package com.eeesys.frame.utils;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.eeesys.frame.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * ImageLoader第3方jar包初始化
 * 
 * @author eeesys
 * 
 */
public class ImageLoaderTool {
	private static AnimateFirstDisplayListener animateFirstDisplayListener;
	private static DisplayImageOptions options;
	private static DisplayImageOptions circleOptions,roundOptions;

	private static class AnimateFirstDisplayListener extends
			SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections
				.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}

	public static AnimateFirstDisplayListener getAnimateFirstDisplayListener() {
		if (animateFirstDisplayListener == null) {
			synchronized (ImageLoaderTool.class) {
				if (animateFirstDisplayListener == null) {
					animateFirstDisplayListener = new AnimateFirstDisplayListener();
				}
			}

		}
		return animateFirstDisplayListener;
	}

	public static DisplayImageOptions getDisplayImageOptions() {
		if (options == null) {
			synchronized (ImageLoaderTool.class) {
				if (options == null) {
					options = new DisplayImageOptions.Builder()
							.showImageOnLoading(R.drawable.loading)
							.showImageForEmptyUri(R.drawable.loading)
							.showImageOnFail(R.drawable.loading)
							.cacheInMemory(true).cacheOnDisk(true)
							.displayer(new RoundedBitmapDisplayer(0)).build();
				}
			}
		}
		return options;
	}

	public static void initImageLoader(Context context) {

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.diskCacheSize(50 * 1024 * 1024)
				// 50 Mb
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove for release app
				.build();
		ImageLoader.getInstance().init(config);
	}

	//圆形图片
	public static DisplayImageOptions getCircleDisplayImageOptions() {
		if (circleOptions == null) {
			synchronized (ImageLoaderTool.class) {
				if (circleOptions == null) {
					circleOptions = new DisplayImageOptions.Builder()
							.showImageOnLoading(R.drawable.loading)
							.showImageForEmptyUri(R.drawable.loading)
							.showImageOnFail(R.drawable.loading)
							.cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
							.displayer(new CircleBitmapDisplayer()).build();
				}
			}
		}
		return circleOptions;
	}

	//圆角图片
	public static DisplayImageOptions getRoundDisplayImageOptions() {
		if (roundOptions == null) {
			synchronized (ImageLoaderTool.class) {
				if (roundOptions == null) {
					roundOptions = new DisplayImageOptions.Builder()
							.showImageOnLoading(R.drawable.loading)
							.showImageForEmptyUri(R.drawable.loading)
							.showImageOnFail(R.drawable.loading)
							.cacheInMemory(true)
							.cacheOnDisk(true)
							.considerExifParams(true)
							.bitmapConfig(Bitmap.Config.RGB_565)
							.displayer(new RoundedBitmapDisplayer(20))
							.build();
				}
			}
		}
		return roundOptions;
	}

}
