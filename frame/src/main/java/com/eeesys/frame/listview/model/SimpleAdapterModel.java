package com.eeesys.frame.listview.model;

public class SimpleAdapterModel {

	public int layoutId;
	public String[] from;
	public int[] to;
	public int[] strings;
	
	
	public SimpleAdapterModel(int layoutId, String[] from, int[] to) {
		this(layoutId, from, to, null);
	}


	public SimpleAdapterModel(int layoutId, String[] from, int[] to,
			int[] strings) {
		super();
		this.layoutId = layoutId;
		this.from = from;
		this.to = to;
		this.strings = strings;
	}
	
	 

}
