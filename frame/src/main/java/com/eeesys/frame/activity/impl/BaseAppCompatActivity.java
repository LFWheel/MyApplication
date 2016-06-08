package com.eeesys.frame.activity.impl;

import android.widget.TextView;
import com.eeesys.frame.R;

/**
 * Created by ASUS on 2015/12/7.
 */
public class BaseAppCompatActivity extends MainActivity {
    @Override
    public int getLayoutId() {
        return R.layout.content_main;
    }

    @Override
    public void initContentView() {

    }

    @Override
    public void initTitleView() {
        title = (TextView) findViewById(R.id.title);
        title.setText("成功");

    }

    @Override
    public int getTitleLayout() {
        return R.layout.title;
    }
}
