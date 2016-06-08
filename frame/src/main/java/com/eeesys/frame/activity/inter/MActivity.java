package com.eeesys.frame.activity.inter;

/**
 * Created by ASUS on 2015/12/7.
 */
public interface MActivity {
    /**
     * 获得布局
     */
    public int getLayoutId();

    /**
     * 初始化视图
     */
    public void initContentView();

    /**
     * 控制activity是否需要title
     * @return
     */
    public boolean hasTitle();

    /**
     * 初始化title视图
     */
    public void initTitleView();

    /**
     * 获得title布局
     */
    public int getTitleLayout();
}
