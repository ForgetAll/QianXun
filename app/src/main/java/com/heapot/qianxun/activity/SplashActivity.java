package com.heapot.qianxun.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.heapot.qianxun.R;
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
//        //获取是否已经进入引导界面
//        isEnter = PreferenceUtil.getBoolean(ConstantsBean.KEY_SPLASH, false);
//        if (!isEnter) {
//            ActivityUtil.jumpActivity(this, GuideActivity.class);
//            finish();
//        }
        setContentView(R.layout.activity_splash);
        imageView = (ImageView) findViewById(R.id.iv_start);
        Glide.with(this).load(R.mipmap.ic_start).into(imageView);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                chooseToActivity();
            }
        },3000);

    }

    private void  chooseToActivity(){
        String token = getAppToken();
        String chat_token= getChatToken();
        Intent intent = new Intent();
        if (token.equals("") || chat_token.equals("")){
            intent.setClass(this,LoginActivity.class);
        }else {
            connChat(chat_token);
            intent.setClass(this,MainActivity.class);
        }
        startActivity(intent);
        this.finish();
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


//
//    @AfterPermissionGranted(ConstantsBean.PERMISSION_CODE)
//    private void methodRequestPermission(){
//        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA};
//        if (EasyPermissions.hasPermissions(this,perms)){
//            //已授权，直接进入当前页面,直接后台静默登陆或者别的操作
//
//        }else {
//            //未授权，请求授权
//            EasyPermissions.requestPermissions(this,ConstantsBean.PERMISSION_NAME,ConstantsBean.PERMISSION_CODE,perms);
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        //该方法的目的是截断系统自带的弹窗
//        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this);
//    }
//
//    @Override
//    public void onPermissionsGranted(int requestCode, List<String> perms) {
//        //授权成功，进行下一步操作，后台登陆或者别的操作
//        Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show();
//
//    }
//
//    @Override
//    public void onPermissionsDenied(int requestCode, List<String> perms) {
//        //授权失败，提示用户
//        if (EasyPermissions.somePermissionPermanentlyDenied(this,perms)){
//            new AppSettingsDialog.Builder(this,"拍照/读写是该应用必需的权限，如果不设置会导致不能正常使用，是否前往设置中心进行设置")
//                    .setTitle("申请动态权限")
//                    .setPositiveButton("前往设置")
//                    .setNegativeButton("取消并退出", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            SplashActivity.this.finish();
//                        }
//                    })
//                    .setRequestCode(ConstantsBean.PERMISSION_CODE)
//                    .build()
//                    .show();
//
//        }
//
//    }
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == ConstantsBean.PERMISSION_CODE){
//            methodRequestPermission();//再次判断
//        }
//    }
}
