package com.eeesys.myapplication.common;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.eeesys.myapplication.R;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Created by ASUS on 2016/7/25.
 */
public abstract class BaseActivity extends AppCompatActivity {
    SystemBarTintManager tintManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutReourseId());
        initWindow();
        setUpView();
        setUpData();
    }

    //设置布局id
    protected abstract int setLayoutReourseId();

    //初始化view
    protected abstract void setUpView();

    //简化findviewById
    protected <T extends View> T $(int viewId){
        return (T)findViewById(viewId);
    }

    //初始化数据
    protected void setUpData(){};

    private void initWindow(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            tintManager = new SystemBarTintManager(this);
            // enable status bar tint
            tintManager.setStatusBarTintEnabled(true);
            // enable navigation bar tint
            tintManager.setNavigationBarTintEnabled(true);

            tintManager.setTintColor(Color.parseColor("#63B8FF"));
        }
    }
}
