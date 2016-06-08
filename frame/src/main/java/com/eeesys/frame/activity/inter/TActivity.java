package com.eeesys.frame.activity.inter;

public interface TActivity {
	
	/**通过布局文件id获取View对象
	 * @return 返回资源文件id
	 */	
	public int getLayoutId();
	
	/**
	 * 初始化视图
	 */
	public void initContentView();
	
	
	
}
