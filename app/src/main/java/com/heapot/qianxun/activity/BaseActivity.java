package com.heapot.qianxun.activity;

import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.heapot.qianxun.application.ActivityCollector;
import com.heapot.qianxun.helper.NetworkChangeReceiver;
import com.orhanobut.logger.Logger;

/**
 * Created by Karl on 2016/9/18.
 * 这是基类,用做统一管理
 *
 */
public class BaseActivity extends AppCompatActivity {
    public Activity activity;
    private IntentFilter intentFilter;
    private NetworkChangeReceiver networkChangeReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity=this;
        Logger.d(getClass().getSimpleName());
        ActivityCollector.addActivity(this);
//        //监听网络广播
//        getNetwork();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(networkChangeReceiver);
        ActivityCollector.removeActivity(this);
    }
    /**
     * 实时监测网络
     */
//    private void getNetwork(){
//        intentFilter = new IntentFilter();
//        intentFilter.addAction("android.net.conn.CONNECTION_CHANGE");
//        networkChangeReceiver = new NetworkChangeReceiver();
//        registerReceiver(networkChangeReceiver,intentFilter);
//    }
}
