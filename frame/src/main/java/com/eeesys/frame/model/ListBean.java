package com.eeesys.frame.model;

import java.io.Serializable;

import com.eeesys.frame.listview.inter.IAdapter;
import com.eeesys.frame.listview.inter.IProvider;

public class ListBean implements IAdapter, Serializable{
	
	private static final long serialVersionUID = 1L;
	String name;
	int photo;
	

	public ListBean(String name, int photo) {
		super();
		this.name = name;
		this.photo = photo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPhoto() {
		return photo;
	}

	public void setPhoto(int photo) {
		this.photo = photo;
	}

	@Override
	public Class<? extends IProvider> getProviderClass() {
		
		return ListAdapter1.class;
	}

}
