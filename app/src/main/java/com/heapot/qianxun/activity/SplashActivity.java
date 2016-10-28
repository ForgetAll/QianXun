package com.heapot.qianxun.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.blankj.utilcode.utils.NetworkUtils;
import com.heapot.qianxun.R;
import com.heapot.qianxun.application.CustomApplication;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.util.ActivityUtil;
import com.heapot.qianxun.util.ChatInfoUtils;
import com.heapot.qianxun.util.LoadTagsUtils;
import com.heapot.qianxun.util.PreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by Karl on 2016/9/19.
 * 应用启动引导页
 */
public class SplashActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks{
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
        }
        setContentView(R.layout.activity_splash);
        intView();

    }

    private void intView() {
        imageView = (ImageView) findViewById(R.id.iv_start);
        final ScaleAnimation scaleAnimation =
                new ScaleAnimation(1.0f, 1.0f, 1.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setDuration(3000);
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                imageView.setImageResource(R.mipmap.ic_start);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //这个最好在onResume里面去执行，这里考虑到还要有执行动画的时间所以放在这里执行
                methodRequestPermission();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        imageView.startAnimation(scaleAnimation);
    }

    //跳转页面，这个过程中需要进行自动登陆，如果失败自动进入Login页面重新登陆
    private void startActivity(){
        String name = PreferenceUtil.getString("phone");
        String pass = PreferenceUtil.getString("password");
        boolean isAvailable = NetworkUtils.isAvailable(this);
        if (isAvailable) {
            if (name == null || pass == null) {
               intentToActivity();
            } else {
                String url = ConstantsBean.BASE_PATH + ConstantsBean.LOGIN + "?loginName=" + name + "&password=" + pass;
                postLogin(url);
            }
        }else {
            Toast.makeText(SplashActivity.this, "网络连接不可用", Toast.LENGTH_SHORT).show();
            intentToActivity();
        }
    }
    private void postLogin(String url){
        JsonObjectRequest jsonObject = new JsonObjectRequest(
                Request.Method.POST,url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("status").equals("success")) {

                                if (response.has("content")) {
                                    JSONObject content = response.getJSONObject("content");
                                    String token = content.getString("auth-token");
                                    //设置全局变量
                                    CustomApplication.TOKEN = token;
                                    //存储到本地
                                    // 因为token只有三十分钟有效期，也就是说用户退出以后下次失效的可能性比较高，所以这里实际上没有存本地的意义
                                    PreferenceUtil.putString("token", token);
                                    //设置跳转到主页-->学术页面
                                    CustomApplication.setCurrentPage(ConstantsBean.PAGE_SCIENCE);
                                    //跳转页面,同时关闭当前页面
                                    LoadTagsUtils.getTags(SplashActivity.this,token);
                                    ChatInfoUtils.getFriendsList(token);
                                }
                            }else {
                                Toast.makeText(SplashActivity.this, "登陆失败，请重新登陆", Toast.LENGTH_SHORT).show();
                                intentToActivity();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (NetworkUtils.isAvailable(SplashActivity.this)){
                            Toast.makeText(SplashActivity.this, "网络连接不可用", Toast.LENGTH_SHORT).show();
                        }
                        intentToActivity();
                    }
                }
        );
        CustomApplication.getRequestQueue().add(jsonObject);

    }

    /**
     * 选择跳转事件
     */
    private void intentToActivity(){
        Intent intent;
        intent = new Intent(SplashActivity.this,LoginActivity.class);
        startActivity(intent);
        SplashActivity.this.finish();
    }

    @AfterPermissionGranted(ConstantsBean.PERMISSION_CODE)
    private void methodRequestPermission(){
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(this,perms)){
            //已授权，直接进入当前页面,直接后台静默登陆或者别的操作
            startActivity();

        }else {
            //未授权，请求授权
            EasyPermissions.requestPermissions(this,ConstantsBean.PERMISSION_NAME,ConstantsBean.PERMISSION_CODE,perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //该方法的目的是截断系统自带的弹窗
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        //授权成功，进行下一步操作，后台登陆或者别的操作
        Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show();
        startActivity();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        //授权失败，提示用户
        if (EasyPermissions.somePermissionPermanentlyDenied(this,perms)){
            new AppSettingsDialog.Builder(this,"拍照/读写是该应用必需的权限，如果不设置会导致不能正常使用，是否前往设置中心进行设置")
                    .setTitle("申请动态权限")
                    .setPositiveButton("前往设置")
                    .setNegativeButton("取消并退出", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SplashActivity.this.finish();
                        }
                    })
                    .setRequestCode(ConstantsBean.PERMISSION_CODE)
                    .build()
                    .show();
//            AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
//            builder.setTitle("权限申请说明");
//            builder.setMessage("拍照/读写是该应用必须的权限，请前往设置中心设置");
//            builder.setNegativeButton("取消并退出", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    finish();
//                }
//            });
//            builder.setPositiveButton("好的，前往设置", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                    startActivityForResult(intent,ConstantsBean.PERMISSION_CODE);
//                }
//            });
//            builder.show();

        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ConstantsBean.PERMISSION_CODE){
            methodRequestPermission();//再次判断
        }
    }
}
