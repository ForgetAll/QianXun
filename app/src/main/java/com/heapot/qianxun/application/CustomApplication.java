package com.heapot.qianxun.application;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

/**
 * @author JackHappiness
 * @version 1.0
 * @date 2016/6/13.
 * @summary 全局配置应用
 */
public class CustomApplication extends Application {
    //获取全局上下文
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化日志工具类
        com.orhanobut.logger.Logger
                .init("QianXun")
                .methodCount(3);
    }
}



