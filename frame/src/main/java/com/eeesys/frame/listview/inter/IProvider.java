package com.eeesys.frame.listview.inter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//提供不同布局的item
public interface IProvider {
	
	public View getItemView(LayoutInflater mInflater,int position, View convertView, ViewGroup parent, IAdapter iAdapter);

}
