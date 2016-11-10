package com.heapot.qianxun.application;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.heapot.qianxun.bean.ConstantsBean;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;

import io.rong.imkit.RongIM;

/**
 *Created by 15859 on 2016/9/4.
 * 全局配置应用
 */
public class CustomApplication extends Application {
    //获取全局上下文
    public static Context context;
    private static RequestQueue requestQueue;

    private static final boolean DEV_MODE = true;

    @Override
    public void onCreate() {
        super.onCreate();
        //监测内存泄露
        LeakCanary.install(this);

        //初始化Bugly
        CrashReport.initCrashReport(getApplicationContext(),"900057726",false);

        //初始化日志工具类
        com.orhanobut.logger.Logger
                .init("QianXun")
                .methodCount(3);
        //初始化上下文
        context = getApplicationContext();

        //初始化融云
        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext()))){
            RongIM.init(this);
        }

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
    /**
     * 初始化StrictMode工具类,
     * 系统检测出主线程违例的情况并作出相应反映
     */
    private void initStrictMode(){
        if (DEV_MODE){
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectCustomSlowCalls()//自定义耗时操作
                    .detectDiskReads()//磁盘读取操作
                    .detectDiskWrites()//磁盘写入操作
                    .detectNetwork()//网络操作，以上可以用detectAll()来代替
                    .penaltyDialog()//弹出违规对话框
                    .penaltyLog()//在Logcat中打印异常信息
                    .penaltyFlashScreen()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectActivityLeaks()
                    .detectLeakedClosableObjects()
                    .detectLeakedSqlLiteObjects()
                    .penaltyLog()
                    .penaltyDeath()
                    .build());
        }
    }



}



