package com.eeesys.myapplication.common;

import android.app.Application;

import cn.bmob.v3.Bmob;

/**
 * Created by ASUS on 2016/6/8.
 */
public class CusApplication extends Application {
    public CusApplication() {
        super();
    }

    @Override
    public void onCreate() {
        Bmob.initialize(this,Config.BMOBKEY);
        super.onCreate();
    }
}
