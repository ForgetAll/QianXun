package com.heapot.qianxun.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;

import com.heapot.qianxun.R;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.bean.UpdateResultBean;
import com.heapot.qianxun.util.LogUtils;
import com.heapot.qianxun.util.PackageUtils;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;

import java.io.File;

/**
 * Created by 15859 on 2016/9/23.
 */
public class UpdateService extends Service {
    private NotificationManager manger;

    /**
     * 服务在创建时调用，在创建的时候只执行一次
     */
    @Override
    public void onCreate() {
        super.onCreate();
        manger = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    /**
     * 服务停止时调用
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        manger.cancel(2);
    }

    /**
     * 每一次系统开启服务的时候调用
     *
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        UpdateResultBean.ContentBean bean= (UpdateResultBean.ContentBean) intent.getSerializableExtra(ConstantsBean.APP_UEL);
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        //禁止用户点击删除按钮删除
        builder.setAutoCancel(false);
        //禁止滑动删除
        builder.setOngoing(true);
        builder.setShowWhen(false);
        builder.setOngoing(true);
        builder.setShowWhen(false);
        //下载
        Ion.with(this).load(bean.getAppUrl()).progress(new ProgressCallback() {
            @Override
            public void onProgress(long downloaded, long total) {
                LogUtils.e("onProgress",downloaded+"/"+total+"/"+(100*downloaded/total));
                builder.setContentTitle("仟询"+"  已下载"+(100*downloaded/total)+"%");
                builder.setProgress((int)total, (int)downloaded, false);
                Notification notification = builder.build();
                manger.notify(2, notification);
            }
        }).write(new File(Environment.getExternalStorageDirectory(), "goodapp.apk"))
                .setCallback(new FutureCallback<File>() {
                    @Override
                    public void onCompleted(Exception e, File result) {
                        manger.cancel(2);
                        PackageUtils.install(UpdateService.this, result.getAbsolutePath());
                    }
                });
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 绑定的时候调用，与外界交互使用
     *
     * @param intent
     * @return
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}

