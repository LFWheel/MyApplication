package com.eeesys.frame.listview.abs;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.eeesys.frame.listview.inter.ViewHolder;

public abstract class BaseAdapterTool<T, V> extends BaseAdapter implements ViewHolder<T> {
	
	protected List<V> list;
	protected Context context;
	private LayoutInflater mInflater;
		
	public BaseAdapterTool(Context context, List<V> list) {
		this.list = list;
		this.context = context;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public abstract int getLayoutId();
	
	public abstract void showView(T t, V v);

	@SuppressWarnings("unchecked")
	public View getItemView(int position,
			View convertView, ViewGroup parent) {
		T t = null;
		if(convertView == null){
			t = getViewHolder();
			int layoutId = getLayoutId();
			if(layoutId > 0){
				convertView = mInflater.inflate(getLayoutId(), parent, false);
			}else{
				convertView = getContentView();
			}
			initViewHolder(t, convertView);
			convertView.setTag(t);
		}else{
			t = (T) convertView.getTag();
		}
		showView(t, list.get(position));
		return convertView;
	}

	public View getContentView() {
		return null;
	}

	@Override
	public int getCount() {
		return list == null ? 0 : list.size();
	}

	@Override
	public Object getItem(int position) {
		return list == null ? null : list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {	
		return getItemView(position, convertView, parent);
	}

}
