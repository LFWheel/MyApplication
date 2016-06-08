package com.eeesys.frame.listview.abs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eeesys.frame.listview.inter.IAdapter;
import com.eeesys.frame.listview.inter.IProvider;
import com.eeesys.frame.listview.inter.ViewHolder;

public abstract class OneTypeAdapter<T> implements IProvider, ViewHolder<T> {
	public Context context;

	public OneTypeAdapter() {
		super();
	}

	public OneTypeAdapter(Context context) {
		super();
		this.context = context;
	}

	public abstract int getLayoutId();

	public abstract void showView(T t, IAdapter iAdapter);

	@SuppressWarnings("unchecked")
	@Override
	public View getItemView(LayoutInflater mInflater, int position,
			View convertView, ViewGroup parent, IAdapter iAdapter) {
		T t = null;
		if (convertView == null) {
			t = getViewHolder();
			convertView = mInflater.inflate(getLayoutId(), parent, false);
			initViewHolder(t, convertView);
			convertView.setTag(t);
		} else {
			t = (T) convertView.getTag();
		}
		showView(t, iAdapter);
		return convertView;
	}

}
