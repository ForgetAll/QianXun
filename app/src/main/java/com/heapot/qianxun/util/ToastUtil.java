package com.heapot.qianxun.util;

import android.widget.Toast;

import com.heapot.qianxun.goodapp.CustomApplication;


/**
 * @author JackHappiness
 * @version 1.0
 * @date 2016/6/13.
 * @summary 吐司工具类
 */
public class ToastUtil {
    /**
     * 弹出吐司
     *
     * @param tip 提示的字符串
     */
    public static void show(String tip) {
        Toast.makeText(CustomApplication.context, tip, Toast.LENGTH_LONG).show();
    }

    /**
     * 弹出吐司
     *
     * @param resId 提示的资源id
     */
    public static void show(int resId) {
        Toast.makeText(CustomApplication.context, resId, Toast.LENGTH_LONG).show();
    }
}