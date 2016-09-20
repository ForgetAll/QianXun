package com.heapot.qianxun.application;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.heapot.qianxun.bean.ConstantsBean;

/**
 *Created by 15859 on 2016/9/4.
 * @summary 全局配置应用
 */
public class CustomApplication extends Application {
    //获取全局上下文
    public static Context context;

    public static String TOKEN = "";

    public static String CURRENT_PAGE = ConstantsBean.PAGE_SCIENCE;

    public static boolean isAdmin = false;

    public static RequestQueue requestQueue;



    @Override
    public void onCreate() {
        super.onCreate();
        //初始化日志工具类
        com.orhanobut.logger.Logger
                .init("QianXun")
                .methodCount(3);
        //初始化上下文
        context = getApplicationContext();

        //初始化全局异常捕获
        CrashHandler.getInstance(context).init(context);
    }

    public static Context getContext() {
        return context;
    }

    /**
     * 添加Volley请求
     */
    public static RequestQueue getRequestQueue(){
        if (requestQueue != null){
            return requestQueue;
        }
        return  requestQueue = Volley.newRequestQueue(context);
    }


    /**
     * 获得当前进程的名字
     *
     * @param context
     * @return 进程号
     */
    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }
}



