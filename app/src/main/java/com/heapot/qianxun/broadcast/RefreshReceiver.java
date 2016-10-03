package com.heapot.qianxun.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Karl on 2016/10/3.
 * desc: 广播接收器
 *
 */
public class RefreshReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "该刷新了！", Toast.LENGTH_SHORT).show();
    }
}
