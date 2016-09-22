package com.heapot.qianxun.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created by 15859 on 2016/9/22.
 * 广播控制器
 */
public class BroadcastUtil {
    /**
     * 发送广播
     *
     * @param context 上下文
     */
    public static void sendDataChangeBroadcase(Context context, Intent intent) {
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(context);
        manager.sendBroadcast(intent);
    }

    /**
     * 注册广播
     *
     * @param context  上下文
     * @param receiver 接收器
     */
    public static void registerDataChangeReceiver(Context context, BroadcastReceiver receiver, String action) {
        IntentFilter filter = new IntentFilter(action);
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(context);
        manager.registerReceiver(receiver, filter);
    }

    /**
     * 销毁广播
     *
     * @param context  上下文
     * @param receiver 接收器
     */
    public static void unregisterReceiver(Context context, BroadcastReceiver receiver) {
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(context);
        manager.unregisterReceiver(receiver);
    }
}
