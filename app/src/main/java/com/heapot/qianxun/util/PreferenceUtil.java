package com.heapot.qianxun.util;

/**
 * Created by 15859 on 2016/9/5.
 */

import android.content.SharedPreferences;

import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.application.CustomApplication;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by 15859 on 2016/9/.7
 * @summary 偏好设置工具类
 */
public class PreferenceUtil {
    /**
     * 存放布尔值
     *
     * @param key
     * @param value
     */
    public static void putBoolean(String key, boolean value) {
        SharedPreferences sp = CustomApplication.context.getSharedPreferences(ConstantsBean.CONFIG_NAME, MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(key, value);
        edit.commit();
    }

    /**
     * 获取布尔值
     *
     * @param key      键
     * @param defValue 默认值
     */
    public static boolean getBoolean(String key, boolean defValue) {
        SharedPreferences sp = CustomApplication.context.getSharedPreferences(ConstantsBean.CONFIG_NAME, MODE_PRIVATE);
        return sp.getBoolean(key, defValue);
    }

    /**
     * 存放字符串
     *
     * @param key
     * @param value
     */
    public static void putString(String key, String value) {
        SharedPreferences sp = CustomApplication.getContext().getSharedPreferences(ConstantsBean.CONFIG_NAME, MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.commit();
    }

    /**
     * 获取字符串
     *
     * @param key 键
     */
    public static String getString(String key) {
        SharedPreferences sp = CustomApplication.context.getSharedPreferences(ConstantsBean.CONFIG_NAME, MODE_PRIVATE);
        return sp.getString(key, null);
    }

    public static Long getLong(String key){
        SharedPreferences sp = CustomApplication.context.getSharedPreferences(ConstantsBean.CONFIG_NAME,MODE_PRIVATE);
        return sp.getLong(key,0);
    }

    public static void putLong(String key,Long value){
        SharedPreferences sp = CustomApplication.getContext().getSharedPreferences(ConstantsBean.CONFIG_NAME, MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putLong(key,value);
        edit.commit();
    }

    /**
     * 清空偏好设置
     */
    public static void clearPreference() {
        SharedPreferences sp = CustomApplication.context.getSharedPreferences(ConstantsBean.CONFIG_NAME, MODE_PRIVATE);
        sp.edit().clear().commit();
    }
}
