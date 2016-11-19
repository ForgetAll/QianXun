package com.heapot.qianxun.activity.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.heapot.qianxun.R;
import com.heapot.qianxun.activity.BaseActivity;
import com.heapot.qianxun.activity.MainActivity;
import com.heapot.qianxun.activity.login.LoginActivity;
import com.heapot.qianxun.application.CustomApplication;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.util.ActivityUtil;
import com.heapot.qianxun.util.PreferenceUtil;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

/**
 * Created by Karl on 2016/9/19.
 * 应用启动引导页
 *
 * implements EasyPermissions.PermissionCallbacks
 */
public class SplashActivity extends BaseActivity {
    private ImageView imageView;
    private boolean isEnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //获取是否已经进入引导界面
        isEnter = PreferenceUtil.getBoolean(ConstantsBean.KEY_SPLASH, false);
        if (!isEnter) {
            ActivityUtil.jumpActivity(this, GuideActivity.class);
            finish();
        }else {
            setContentView(R.layout.activity_splash);
            imageView = (ImageView) findViewById(R.id.iv_start);
            Glide.with(this).load(R.mipmap.ic_start).into(imageView);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    chooseToActivity();
                }
            }, 3000);
        }

    }

    private void  chooseToActivity(){
        String token = getAppToken();
        String chat_token= getChatToken();
        Intent intent = new Intent();
        if (token.equals("") || chat_token.equals("")){
            intent.setClass(this,LoginActivity.class);
            startActivity(intent);
            this.finish();
        }else {
            connChat(chat_token);
//            intent.setClass(this,MainActivity.class);
        }

    }

    private void connChat(final String token){
        if (getApplicationInfo().packageName.equals(CustomApplication.getCurProcessName(getApplicationContext()))){
            /**
             * IMKit SDK调用第二步，建立与服务器的连接
             */
            RongIM.connect(token, new RongIMClient.ConnectCallback() {
                @Override
                public void onTokenIncorrect() {

                }

                @Override
                public void onSuccess(String s) {
                    PreferenceUtil.putString("chat_token",token);
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {

                }
            });
        }
    }
}
