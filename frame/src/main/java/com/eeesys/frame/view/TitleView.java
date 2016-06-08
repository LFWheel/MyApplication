package com.eeesys.frame.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.eeesys.frame.R;

public class TitleView extends FrameLayout{
	
	public static final int LEFT = 0, RIGHT = 1, WHOLE = 2, TEXTVIEW = -1, IMAGEVIEW = -2;
	private LayoutInflater inflater;
	private int layoutId, titleId, lTextViewId, rTextViewId, lImageButtonId, rImageButtonId, lImageId, rImageId, lTextId, rTextId, titleTextId;
	private Context context;
	private String titleText = null, lText = null, rText = null;
	private TextView title_text, lTextView, rTextView;
	private ImageButton lImageButton, rImageButton;

	public TitleView(Context context) {
		this(context, null);
	}

	public TitleView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public TitleView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TitleView);
		layoutId = a.getResourceId(R.styleable.TitleView_layoutId, 0);
		if(layoutId == 0){
			throw new RuntimeException("layoutId不能为0");
		}
		titleId = a.getResourceId(R.styleable.TitleView_titleId, 0);
		lTextViewId = a.getResourceId(R.styleable.TitleView_lTextViewId, 0);
		rTextViewId = a.getResourceId(R.styleable.TitleView_rTextViewId, 0);
		lImageButtonId = a.getResourceId(R.styleable.TitleView_lImageButtonId, 0);
		rImageButtonId = a.getResourceId(R.styleable.TitleView_rImageButtonId, 0);
		lImageId = a.getResourceId(R.styleable.TitleView_lImageId, 0);
		rImageId = a.getResourceId(R.styleable.TitleView_rImageId, 0);
		TypedValue text = new TypedValue();
		//lTextId = a.getResourceId(R.styleable.TitleView_lTextId, 0);
		//rTextId = a.getResourceId(R.styleable.TitleView_rTextId, 0);
		a.getValue(R.styleable.TitleView_lTextId, text);
		if(text != null){
			lTextId = text.resourceId;
			lText = (String) text.string;			
		}
		text = new TypedValue();
		a.getValue(R.styleable.TitleView_rTextId, text);
		if(text != null){
			rTextId = text.resourceId;
			rText = (String) text.string;
		}
		text = new TypedValue();
		a.getValue(R.styleable.TitleView_titleTextId, text);
		if(text != null){
			titleTextId = text.resourceId;
			titleText = (String) text.string;	
		}
		a.recycle();
		initView(context);
	}

	@SuppressWarnings("deprecation")
	private void initView(Context context) {
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(layoutId, null, false);
		if(titleId != 0){
			title_text = (TextView) view.findViewById(titleId);
			title_text.setVisibility(View.VISIBLE);
			if(titleTextId != 0){
				title_text.setText(getResources().getString(titleTextId));
			}
			if(titleText != null){
				title_text.setText(titleText);
			}
		}
		if(lTextViewId != 0){
			lTextView = (TextView) view.findViewById(lTextViewId);
			lTextView.setVisibility(View.VISIBLE);
			if(lImageId != 0){
				lTextView.setCompoundDrawablePadding(2);
				lTextView.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(lImageId), null, null, null);
			}
			if(lTextId != 0){
				lTextView.setText(getResources().getString(lTextId));
			}
			if(lText != null){
				lTextView.setText(lText);
			}
		}
		if(rTextViewId != 0){
			rTextView = (TextView) view.findViewById(rTextViewId);
			rTextView.setVisibility(View.VISIBLE);
			if(rImageId != 0){
				rTextView.setCompoundDrawablePadding(2);
				rTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(rImageId), null);
			}
			if(rTextId != 0){
				rTextView.setText(getResources().getString(rTextId));
			}
			if(rText != null){
				rTextView.setText(rText);
			}
		}
		if(lImageButtonId != 0){
			lImageButton = (ImageButton) view.findViewById(lImageButtonId);
			lImageButton.setVisibility(View.VISIBLE);
			lImageButton.setBackgroundDrawable(null);
			if(lImageId != 0){
				lImageButton.setImageResource(lImageId);
			}
		}
		if(rImageButtonId != 0){
			rImageButton = (ImageButton) view.findViewById(rImageButtonId);
			rImageButton.setVisibility(View.VISIBLE);
			rImageButton.setBackgroundDrawable(null);
			if(rImageId != 0){
				rImageButton.setImageResource(rImageId);
			}
		}
		addView(view);
	}
	
	/**
	 * 设置标题
	 * @param id
	 */
	public void setTitle(String title){
		if(title_text != null)
			title_text.setText(title);
	}

	/**
	 * 设置标题
	 * @param id
	 */
	public void setTitle(int id){
		setTitle(context.getString(id));
	}
	
	/**
	 * 为左右设置不同的值
	 * @param id
	 * @param position
	 */
	public void setText(String text, int position){
		if(position == LEFT && lTextView != null){
			lTextView.setText(text);
		}
		if(position == RIGHT && rTextView != null){
			rTextView.setText(text);
		}	
	}

	/**
	 * 为左右设置不同的值
	 * @param id
	 * @param position
	 */
	public void setText(int id, int position){
		setText(context.getString(id), position);
	}
	
	/**
	 * 为左右设置不同的图片
	 * @param id
	 * @param position
	 * @param type
	 */
	public void setImage(int id, int position, int type){
		setImage(getResources().getDrawable(id), position, type);
	}
	
	/**
	 * 为左右设置不同的图片
	 * @param id
	 * @param position
	 * @param type
	 */
	@SuppressWarnings("deprecation")
	public void setImage(Drawable drawable, int position, int type){
		if(position == LEFT && lTextView != null && drawable != null && type == TEXTVIEW){
			lTextView.setCompoundDrawablePadding(2);
			lTextView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
		}
		if(position == RIGHT && rTextView != null && drawable != null && type == TEXTVIEW){
			rTextView.setCompoundDrawablePadding(2);
			rTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
		}
		if(position == LEFT && lImageButton != null && drawable != null && type == IMAGEVIEW){
			lImageButton.setBackgroundDrawable(null);
			lImageButton.setImageDrawable(drawable);
		}
		if(position == RIGHT && rImageButton != null && drawable != null && type == IMAGEVIEW){
			rImageButton.setBackgroundDrawable(null);
			rImageButton.setImageDrawable(drawable);
		}
	}
	
	/**
	 * 为左右设置不同的事件
	 * @param l
	 * @param position
	 * @param type
	 */
	public void setOnTextViewListener(OnClickListener l, int position, int type){
		if(lTextView != null && position == LEFT && type == TEXTVIEW && l != null){
			lTextView.setOnClickListener(l);
		}
		if(rTextView != null && position == RIGHT && type == TEXTVIEW && l != null){
			rTextView.setOnClickListener(l);
		}
		if(lImageButton != null && position == LEFT && type == IMAGEVIEW && l != null){
			lImageButton.setOnClickListener(l);
		}
		if(rImageButton != null && position == RIGHT && type == IMAGEVIEW && l != null){
			rImageButton.setOnClickListener(l);
		}
	}
	
	/**
	 * 设置可见性
	 * @param visibility
	 * @param position
	 * @param type
	 */
	public void setVisibility(int visibility, int position, int type){
		if(lTextView != null && position == LEFT && type == TEXTVIEW){
			lTextView.setVisibility(visibility);
		}
		if(rTextView != null && position == RIGHT && type == TEXTVIEW){
			rTextView.setVisibility(visibility);
		}
		if(lImageButton != null && position == LEFT && type == IMAGEVIEW){
			lImageButton.setVisibility(visibility);
		}
		if(rImageButton != null && position == RIGHT && type == IMAGEVIEW){
			rImageButton.setVisibility(visibility);
		}
	}

	/**
	 * 获取左右的textview
	 * @param position
	 * @return
	 */
	public TextView getTextView(int position) {
		if(position == LEFT)
			return lTextView;
		else
			return rTextView;
	}

	/**
	 * 获取左右的ImageButton
	 * @param position
	 * @return
	 */
	public ImageButton getlImageButton(int position) {
		if(position == LEFT)
			return lImageButton;
		else
			return rImageButton;
	}
	
}
