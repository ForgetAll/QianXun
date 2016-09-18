package com.heapot.qianxun.activity;

import android.app.Activity;
import android.os.Bundle;

import com.heapot.qianxun.application.ActivityCollector;
import com.orhanobut.logger.Logger;

/**
 * Created by Karl on 2016/9/18.
 */
public class BaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d(getClass().getSimpleName());
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
