package com.heapot.qianxun.helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

import com.blankj.utilcode.utils.NetworkUtils;

/**
 * Created by Karl on 2016/9/24.
 * 利用广播实现实时监测网络状况
 *
 */
public class NetworkChangeReceiver extends BroadcastReceiver {
    private boolean networkState;
    @Override
    public void onReceive(Context context, Intent intent) {
        //想要实时监测需要开启Service
        networkState = NetworkUtils.isAvailable(context);
        if (networkState){
            switch (NetworkUtils.getNetWorkType(context)){
                case 1:
                    Toast.makeText(context, "Wifi状态，请放心使用", Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    Toast.makeText(context, "当前是4G网络", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(context, "当前是3G网络", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(context, "当前是2G网络", Toast.LENGTH_SHORT).show();
                    break;
                case 5:
                    //NETWORK_NO，我也不知道这是什么网络
                    break;
                default:
                    //未知网络
                    break;
            }
        }else {
            Toast.makeText(context, "网络不可用", Toast.LENGTH_SHORT).show();
        }

    }
}
