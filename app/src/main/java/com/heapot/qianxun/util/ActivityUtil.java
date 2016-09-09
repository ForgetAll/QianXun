package com.heapot.qianxun.util;

/**
 * Created by 15859 on 2016/9/5.
 */

import android.app.Activity;
import android.content.Intent;
import android.util.DisplayMetrics;

import java.lang.ref.WeakReference;
import java.util.Stack;

/**
 *Created by 15859 on 2016/9/3.
 * @summary Activity工具类
 */
public class ActivityUtil {
    /**
     * 跳转Activity
     *
     * @param activity 当前activity
     * @param cls      目标activity
     */
    public static void jumpActivity(Activity activity, Class cls) {
        Intent intent = new Intent(activity, cls);
        activity.startActivity(intent);
    }

    /**
     * 获取屏幕宽度
     *
     * @param activity
     * @return
     */
    public static int getWindowWidth(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.widthPixels;     // 屏幕宽度（像素）
    }

    /**
     * 获取屏幕高度
     *
     * @param activity
     * @return
     */
    public static int getWindowHeight(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.heightPixels;     // 屏幕高度（像素）
    }

    /***
     * 寄存整个应用Activity
     **/
    private static final Stack<WeakReference<Activity>> activitys2 = new Stack<WeakReference<Activity>>();

    /**
     * 将Activity压入Application栈
     *
     * @param task 将要压入栈的Activity对象
     */
    public static void pushTask(WeakReference<Activity> task) {
        activitys2.push(task);
    }

    /**
     * 将传入的Activity对象从栈中移除
     *
     * @param task
     */
    public  static void removeTask(WeakReference<Activity> task) {
        activitys2.remove(task);
    }

    /**
     * 根据指定位置从栈中移除Activity
     *
     * @param taskIndex Activity栈索引
     */
    public  static void removeTask(int taskIndex) {
        if (activitys2.size() > taskIndex)
            activitys2.remove(taskIndex);
    }

    /**
     * 将栈中Activity移除至栈顶
     */
    public static void removeToTop() {
        int end = activitys2.size();
        int start = 1;
        for (int i = end - 1; i >= start; i--) {
            if (!activitys2.get(i).get().isFinishing()) {
                activitys2.get(i).get().finish();
            }
        }

    }

    /**
     * 移除全部（用于整个应用退出）
     */
    public static void removeAll() {
        // finish所有的Activity
        for (WeakReference<Activity> task : activitys2) {
            if (!task.get().isFinishing()) {
                task.get().finish();
            }
        }
    }
}
