package com.eeesys.frame.listview.adapter;

import java.util.Arrays;
import java.util.List;

import com.eeesys.frame.listview.inter.IAdapter;
import com.eeesys.frame.listview.inter.IProvider;
import com.eeesys.frame.listview.model.ImageModel;
import com.eeesys.frame.utils.ImageTool;
import com.eeesys.frame.utils.L;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

//自定义适配器，绑定多种item类型
public class MultipleAdapter extends BaseAdapter {
	private List<? extends IAdapter> mList;
	private LayoutInflater mInflater;
	private List<Class<? extends IProvider>> mProviders;
	private Context context;
	
	public MultipleAdapter(Context context, List<? extends IAdapter> mList, List<Class<? extends IProvider>> mProviders){
		if(mProviders == null || mProviders.size() <= 0){
			throw new RuntimeException("mProviders 不能为 null 并且 size >= 1");
		}
		this.context = context;
		this.mList = mList;
		this.mProviders = mProviders;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return mList == null ? 0 : mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList == null ? null : mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public int getItemViewType(int position) {
		if(mList == null || mList.size() <= 0){
			return 0;
		}
		Class<? extends IProvider> cls = mList.get(position).getProviderClass();
		for (int i = 0; i < mProviders.size(); i++) {
			if(cls.getName().equals(mProviders.get(i).getName())){
				return i;
			}
		}
		return 0;
	}

	@Override
	public int getViewTypeCount() {
		return mProviders.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = getProviderType(position, convertView, parent);
		return convertView;
	}
	
	private View getProviderType(int position, View convertView, ViewGroup parent){
		Class<? extends IProvider> cls = mList.get(position).getProviderClass();
		if(cls == null){
			throw new RuntimeException("实体类中getProviderClass()方法返回值不能为null");
		}
		try {
			L.d("getProviderType=" + Arrays.toString(cls.getConstructors()));
			return cls.newInstance().getItemView(mInflater, position, convertView, parent, mList.get(position));
		} catch (Exception e) {
			e.printStackTrace();
			try {
				return cls.getConstructor(Context.class).newInstance(context).getItemView(mInflater, position, convertView, parent, mList.get(position));
			} catch (Exception e1) {
				e1.printStackTrace();
			} 
		} 
		return convertView;	
	}
	
	public void clearImage(){
		for (int i = 0; i < mList.size(); i++) {
			Object v = mList.get(i);
				if(v instanceof ImageModel){
				int imageId_1 = ((ImageModel)v).imageId_1;
				int imageId_2 = ((ImageModel)v).imageId_2;
				int imageId_3 = ((ImageModel)v).imageId_3;
				if(imageId_1 != 0){
					ImageTool.removeCache(imageId_1);
				}
				if(imageId_2 != 0){
					ImageTool.removeCache(imageId_2);
				}
				if(imageId_3 != 0){
					ImageTool.removeCache(imageId_3);
				}
			}
		}
	}	

}
