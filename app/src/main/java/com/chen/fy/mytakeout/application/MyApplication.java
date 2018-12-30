package com.chen.fy.mytakeout.application;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePal;

/**
 * 全局获取当前app的Context的方法
 */
public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        LitePal.initialize(context);  //初始化LitePal,让其在内部自动获取到当前的Context
    }

    public static Context getContext(){
        return context;
    }
}
