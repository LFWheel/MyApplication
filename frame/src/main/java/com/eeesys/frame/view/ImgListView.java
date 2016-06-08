package com.eeesys.frame.view;

import com.eeesys.frame.R;
import com.eeesys.frame.utils.ImageTool;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

/**
 * listview头图片下拉放大， 使用时务必调用方法setImageId或setImageBitmap设置图片 否则报错 
 */
public class ImgListView extends ListView {

	private static final int BACK_SCALE = 0;
	private boolean isHaveHead = false;// 头部是否有图片
	private float scaleY = 0;
	private boolean isBacking = false;// 是否处在回弹状态
	private int displayWidth, displayHeight;
	private float bottomMargin;
	private Context mContext;
	private View headerView;
	private ImageView imageView;
	/** 用于记录拖拉图片移动的坐标位置 */
	private Matrix matrix = new Matrix();
	/** 用于记录图片要进行拖拉时候的坐标位置 */
	private Matrix currentMatrix = new Matrix();
	private Matrix defaultMatrix = new Matrix();
	private float imgHeight,imgWidth;
	/** 记录是拖拉照片模式还是放大缩小照片模式 0:拖拉模式，1：放大 */
	private int mode = 0;// 初始状态
	/** 拖拉照片模式 */
	private final int MODE_DRAG = 1;
	/** 用于记录开始时候的坐标位置 */
	private PointF startPoint = new PointF();

	private int mImageId;
	private RelativeLayout re;

	private AttributeSet attrs;
	private Options opts ;

	public ImgListView(Context context) {
		super(context);
		this.mContext = context;
		initView();
	}

	public ImgListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		this.attrs = attrs;		
		initView();
	}

	public ImgListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.mContext = context;
		this.attrs = attrs;
		initView();
	}

	public void setAdapter(ListAdapter adapter) {
		super.setAdapter(adapter);
	}

	public void addHeaderView(View v) {
		super.addHeaderView(v);
	}

	/**
	 * 初始化图片
	 */
	private void initView() {
		/* 取得屏幕分辨率大小 */
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager mWm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
		mWm.getDefaultDisplay().getMetrics(dm);
		displayWidth = dm.widthPixels;
		displayHeight = dm.heightPixels;
		TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.ImgListView);
		mImageId = a.getResourceId(R.styleable.ImgListView_headimage, 0);
		bottomMargin = a.getDimension(R.styleable.ImgListView_bottomMargin, 0);
		a.recycle();
		if (mImageId != 0) {
			opts = new Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeResource(getResources(), mImageId, opts);
			initHead();
		}
	}

	@SuppressLint("InflateParams") 
	private void initHead() {
		LayoutInflater inflater = LayoutInflater.from(mContext);
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH){
			headerView = inflater.inflate(R.layout.top_img_1, null);
		}else{
			headerView = inflater.inflate(R.layout.top_img_2, null);
		}
		re = (RelativeLayout) headerView.findViewById(R.id.personal_parent);
		imageView = (ImageView) headerView.findViewById(R.id.imageView);
		ImageTool.setImageBitmapDrawable(mContext, mImageId, imageView, true);
		float scale = (float) displayWidth / (float) opts.outWidth;// 1080/1800
		matrix.postScale(scale, scale, 0, 0);
		imageView.setImageMatrix(matrix);
		defaultMatrix.set(matrix);
		imgHeight = displayHeight * 2 / 7;
		imgWidth = scale * opts.outWidth;
		FrameLayout.LayoutParams relativeLayout = new FrameLayout.LayoutParams((int) imgWidth, (int) imgHeight);
		imageView.setLayoutParams(relativeLayout);
		relativeLayout = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		relativeLayout.topMargin = (int) (imgHeight - bottomMargin);
		relativeLayout.leftMargin = (int) bottomMargin;
		relativeLayout.gravity = Gravity.LEFT;
		re.setLayoutParams(relativeLayout);
		this.addHeaderView(headerView);
		isHaveHead = true;
	}

	public View getHeaderView() {
		return headerView;
	}

	/**
	 * 向下滑动让图片变大
	 * 
	 * @param event
	 * @return
	 */
	@SuppressLint("ClickableViewAccessibility") 
	public boolean onTouchEvent(MotionEvent event) {

		if (!isHaveHead) {// 无头部图片
			return super.onTouchEvent(event);
		}
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		// 手指压下屏幕
		case MotionEvent.ACTION_DOWN:
			if (isBacking) {
				return super.onTouchEvent(event);
			}
			int[] location = new int[2];
			imageView.getLocationInWindow(location);
			if (location[1] >= 0) {
				mode = MODE_DRAG;
				// 记录ImageView当前的移动位置
				currentMatrix.set(imageView.getImageMatrix());
				startPoint.set(event.getX(), event.getY());
			}
			break;
		// 手指在屏幕上移动，改事件会被不断触发
		case MotionEvent.ACTION_MOVE:
			// 拖拉图片
			if (mode == MODE_DRAG) {
				float dy = event.getY() - startPoint.y; // 得到y轴的移动距离
				// 在没有移动之前的位置上进行移动
				if (dy / 2 + imgHeight <= 1.5 * imgHeight) {
					matrix.set(currentMatrix);
					float scale = (dy / 2 + imgHeight) / (imgHeight);// 得到缩放倍数
					if (dy > 0) {
						scaleY = dy;
						matrix.postScale(scale, scale, imgWidth/2, 0);
						imageView.setImageMatrix(matrix);
						FrameLayout.LayoutParams relativeLayout = new FrameLayout.LayoutParams((int) (scale * imgWidth), (int) (scale * imgHeight));
						imageView.setLayoutParams(relativeLayout);
						relativeLayout = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
						relativeLayout.topMargin = (int) (scale * imgHeight - bottomMargin);
						relativeLayout.leftMargin = (int) bottomMargin;
						relativeLayout.gravity = Gravity.LEFT;
						re.setLayoutParams(relativeLayout);
						
					}
				}
			}
			break;
		// 手指离开屏幕
		case MotionEvent.ACTION_UP:
			// 当触点离开屏幕，图片还原
			mHandler.sendEmptyMessage(BACK_SCALE);
		case MotionEvent.ACTION_POINTER_UP:
			mode = 0;
			break;
		}

		return super.onTouchEvent(event);
	}

	@SuppressLint("HandlerLeak") 
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what){
			case BACK_SCALE:
				float scale = (scaleY / 2 + imgHeight) / (imgHeight);// 得到缩放倍数
				if (scaleY > 0) {
					isBacking = true;
					matrix.set(currentMatrix);
					FrameLayout.LayoutParams relativeLayout = new FrameLayout.LayoutParams((int) (scale * imgWidth), (int) (scale * imgHeight));
					imageView.setLayoutParams(relativeLayout);
					relativeLayout = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					relativeLayout.topMargin = (int) (scale * imgHeight - bottomMargin);
					relativeLayout.leftMargin = (int) bottomMargin;
					relativeLayout.gravity = Gravity.LEFT;
					re.setLayoutParams(relativeLayout);
					matrix.postScale(scale, scale, imgWidth / 2, 0);
					imageView.setImageMatrix(matrix);
					scaleY = (float) (scaleY / 2 - 1);
					mHandler.sendEmptyMessageDelayed(BACK_SCALE, 20);
				} else {
					scaleY = 0;
					FrameLayout.LayoutParams relativeLayout = new FrameLayout.LayoutParams((int) imgWidth, (int) imgHeight);
					imageView.setLayoutParams(relativeLayout);
					relativeLayout = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					relativeLayout.topMargin = (int) (imgHeight - bottomMargin);
					relativeLayout.leftMargin = (int) bottomMargin;
					relativeLayout.gravity = Gravity.LEFT;
					re.setLayoutParams(relativeLayout);
					matrix.set(defaultMatrix);
					imageView.setImageMatrix(matrix);
					isBacking = false;
				}
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
	};
}
