package com.heapot.qianxun.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.heapot.qianxun.application.CustomApplication;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *Created by 15859 on 2016/9/4.
 * @summary 说明：通用工具类
 */

public class CommonUtil {
    //获取全局上下文
    public static Context context;
     /**
     * 判断是否是手机号码
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * MD5加密
     *
     * @param s 加密的字符
     * @return
     */
    public final static String getMD5String(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4',
                '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            byte[] btInput = s.getBytes();
            //获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            //使用指定的字节更新摘要
            mdInst.update(btInput);
            //获得密文
            byte[] md = mdInst.digest();
            //把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //日期转换，年月日
    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    /**
     * 判断当前应用程序是否运行
     */
    public static boolean isRunning(final Context context) {
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> list = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : list) {
            String processName = appProcess.processName;
            if (processName != null && processName.equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    //加载头像
    /*public static void loadUserImage(final ImageView imageView, String path, int defaultImage) {
        Glide.with(context).load(path).placeholder(defaultImage).error(defaultImage).diskCacheStrategy(DiskCacheStrategy.ALL)
                .bitmapTransform(new GlideCircleTransform(context)).into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                imageView.setImageDrawable(resource);
            }
        });
    }*/

    //加载图片
    public static void loadImage(final ImageView imageView, String path, int defaultImage) {
        Glide.with(CustomApplication.getContext()).load(path).placeholder(defaultImage).error(defaultImage).diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        imageView.setImageDrawable(resource);
                    }
                });
    }

    /**
     * 下载图片
     *
     * @param context 上下文
     * @param path    图片经
     */
    /*public static void downImage(Context context, final String path) {
        Glide.with(context).load(path).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                String p = Environment.getExternalStorageDirectory().getAbsolutePath() + ConstantsBean.DOWN_PATH;
                File fileCache = new File(p);
                if (!fileCache.exists()) {
                    try {
                        //创建文件夹
                        fileCache.mkdir();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                //保存Bitmap到SD卡
                File file = new File(fileCache, CommonUtil.getMD5String(path) + ".jpg");
                if (!file.exists()) {
                    ToastUtil.show("开始下载");
                    FileOutputStream stream = null;
                    try {
                        stream = new FileOutputStream(file);
                        //Bitmap压缩
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        stream.flush();
                        stream.close();
                        ToastUtil.show("下载成功");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    ToastUtil.show("已经下载");
                }
            }
        });
    }*/

    /**
     * dip转px
     */
    public static int dipToPx(Context context, float dip) {
        return (int) (dip * context.getResources().getDisplayMetrics().density + 0.5f);
    }

    /**
     * px转dip
     */
    public static int pxToDip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将sp值转换为px值
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 隐藏键盘
     *
     * @param context
     * @param view
     */
    public static void hideInputSoft(Context context, View view) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 显示键盘键盘
     *
     * @param context
     * @param view
     */
    public static void showInputSoft(Context context, View view) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    public static String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Activity context) {
        WindowManager manager = context.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        return width;
    }

    /**
     * 获取屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Activity context) {
        WindowManager manager = context.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int height2 = outMetrics.heightPixels;
        return height2;
    }
}
