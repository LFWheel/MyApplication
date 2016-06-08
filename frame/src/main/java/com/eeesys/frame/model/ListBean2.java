package com.eeesys.frame.model;

import com.eeesys.frame.listview.inter.IAdapter;
import com.eeesys.frame.listview.inter.IProvider;

public class ListBean2 implements IAdapter {
	String name;
	int photo;
	public ListBean2(String name, int photo) {
		super();
		this.name = name;
		this.photo = photo;
	}

	@Override
	public Class<? extends IProvider> getProviderClass() {

		return ListAdapter2.class;
	}

}
