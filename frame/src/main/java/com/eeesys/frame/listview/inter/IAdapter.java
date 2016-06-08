package com.eeesys.frame.listview.inter;

//获取是由哪一个item绑定数据的IProvider类
public interface IAdapter {
	
	public  Class<? extends IProvider> getProviderClass();

}
