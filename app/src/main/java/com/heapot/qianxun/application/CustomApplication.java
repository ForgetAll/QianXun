package com.heapot.qianxun.application;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.heapot.qianxun.bean.ConstantsBean;

/**
 *Created by 15859 on 2016/9/4.
 * 全局配置应用
 */
public class CustomApplication extends Application {
    //获取全局上下文
    public static Context context;

    public static String TOKEN = "";

    private static String CURRENT_PAGE = ConstantsBean.PAGE_SCIENCE;

    private static RequestQueue requestQueue;

    //测试，利用全局变量传值个人信息
    public static String user_nickName = "";
    public static String user_icon = "";
    public static String user_quote = "";

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
     *  获取主页当前页面名称
     * @return 返回字符串
     */
    public static String getCurrentPageName(){
        return CURRENT_PAGE;
    }

    /**
     * 设置主页当前页面名称
     * @param name
     */
    public static void setCurrentPage(String name){
        CURRENT_PAGE = name;
    }

    /**
     * 获得当前进程的名字
     *
     * @param context 上下文
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



