package com.eeesys.frame.activity.inter;

public interface TitleWrapper {	
	/**
	 * 初始化title视图
	 */
	public void initTitleView();
	
	/**
	 * xml创建title视图
	 * @return 资源文件id
	 */
	public int getTitleViewId();
	
	/** 
	 * 是否自定义标题
	 * @return 是否需要自定义标题，false不需要 true需要
	 */
	public boolean hasCustomTitle();
	
	/** 
	 * 是否有标题
	 * @return 是否有标题，false不需要 true需要
	 */
	public boolean hasTitle();

}
