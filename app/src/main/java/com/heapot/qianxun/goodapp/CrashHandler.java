package com.heapot.qianxun.goodapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.heapot.qianxun.activity.MainActivity;
import com.heapot.qianxun.util.ActivityUtil;
import com.heapot.qianxun.util.SDCardUtil;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *Created by 15859 on 2016/9/3.
 * @summary 说明：全局异常捕获类
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private String cacheInfo;
    private Context mContext;
    private Thread.UncaughtExceptionHandler mdeUncaughtExceptionHandler;
    private Map<String, String> info = new HashMap<String, String>();
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    private static CrashHandler exceptionHandler;
    private static final String TAG = "CaughtHandler";

    /**
     * 当UncaughtException发生时会转入该方法
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mdeUncaughtExceptionHandler != null) {
            // 如果自定义的没有处理则让系统默认的异常处理器来处理
            mdeUncaughtExceptionHandler.uncaughtException(thread, ex);
        } else {
          /*  try {
                // 如果处理了，让程序继续运行3秒再退出，保证文件保存并上传到服务器
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
            }*/
            //关闭所有activity
            ActivityUtil.removeAll();
            Intent intent = new Intent(mContext, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
            android.os.Process.killProcess(android.os.Process.myPid());
        }

    }

    private CrashHandler(Context context) {
        init(context);
    }

    private boolean handleException(final Throwable ex) {
        if (ex == null)
            return false;
        // 收集设备参数信息
        // 保存日志文件
        collectDeviceInfo(mContext);
        saveCrashInfoToSdCard(ex);
        if (cacheInfo != null) {
            // new UploadCaught(mContext).execute(cacheInfo);
        }
        return true;
    }

    public void collectDeviceInfo(Context context) {
        try {
            PackageManager pm = context.getPackageManager();// 获得包管理器
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);// 得到该应用的信息，即主Activity
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                info.put("versionName", versionName);
                info.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        Field[] fields = Build.class.getDeclaredFields();// 反射机制
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                info.put(field.getName(), field.get("").toString());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveCrashInfoToSdCard(Throwable ex) {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : info.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "</br>");
        }
        Writer writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        ex.printStackTrace(pw);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(pw);
            cause = cause.getCause();
        }
        pw.close();
        String result = writer.toString();
        sb.append(result);
        long timetamp = System.currentTimeMillis();
        String time = format.format(new Date());
        String fileName = "crash-" + time + "-" + timetamp + ".log";
        cacheInfo = sb.toString();
        SDCardUtil.saveFileToSDCardPrivateCacheDir(cacheInfo.getBytes(), fileName, mContext);
    }

    public static CrashHandler getInstance(Context context) {

        if (exceptionHandler == null) {
            exceptionHandler = new CrashHandler(context);
        }

        return exceptionHandler;
    }

    public void init(Context context) {
        this.mContext = context;
        // 获取系统默认的UncaughtException处理器
        mdeUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }
}
