package com.heapot.qianxun.activity;

import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.heapot.qianxun.application.ActivityCollector;
import com.heapot.qianxun.helper.NetworkChangeReceiver;
import com.heapot.qianxun.util.PreferenceUtil;
import com.heapot.qianxun.view.BaseView;
import com.orhanobut.logger.Logger;

/**
 * Created by Karl on 2016/9/18.
 * 这是基类,用做统一管理
 *
 */
public class BaseActivity extends AppCompatActivity implements BaseView{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public String getAppToken() {
        String token = PreferenceUtil.getString("token");
        if (token != null){
            return token;
        }
        return "";
    }

    @Override
    public String getChatToken() {
        String token = PreferenceUtil.getString("chat_token");
        if (token != null){
            return token;
        }

        return "";
    }
}
