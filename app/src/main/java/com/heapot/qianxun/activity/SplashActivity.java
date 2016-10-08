package com.heapot.qianxun.activity;

import android.content.Intent;
import android.os.Bundle;
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
import com.heapot.qianxun.util.LoadTagsUtils;
import com.heapot.qianxun.util.PreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Karl on 2016/9/19.
 * 应用启动引导页
 */
public class SplashActivity extends BaseActivity {
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        imageView = (ImageView) findViewById(R.id.iv_start);

        final ScaleAnimation scaleAnimation =
                new ScaleAnimation(1.0f, 1.0f, 1.0f, 1.0f,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setDuration(4000);
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                imageView.setImageResource(R.mipmap.ic_start);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity();
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
                        Toast.makeText(SplashActivity.this, "发生未知错误", Toast.LENGTH_SHORT).show();
                        intentToActivity();
                    }
                }
        );
        CustomApplication.getRequestQueue().add(jsonObject);

    }

    /**
     * 动态选择跳转事件
     */
    private void intentToActivity(){
        Intent intent;
        intent = new Intent(SplashActivity.this,LoginActivity.class);
        startActivity(intent);
        SplashActivity.this.finish();
    }
}
