package com.heapot.qianxun.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.blankj.utilcode.utils.NetworkUtils;
import com.heapot.qianxun.R;
import com.heapot.qianxun.application.CustomApplication;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.util.PreferenceUtil;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Karl on 2016/9/19.
 * 应用启动引导页
 */
public class SplashActivity extends Activity {
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        imageView = (ImageView) findViewById(R.id.iv_start);

        final ScaleAnimation scaleAnimation =
                new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setDuration(3000);
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                imageView.setImageResource(R.drawable.splash);
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

        String name = PreferenceUtil.getString("name");
        String pass = PreferenceUtil.getString("password");
        String token = PreferenceUtil.getString("token");
        String isAdmin = PreferenceUtil.getString("isAdmin");
        Logger.d(name+","+pass+","+isAdmin);

        boolean isAvailable = NetworkUtils.isAvailable(this);
        if (!isAvailable){
            Toast.makeText(SplashActivity.this, "网络连接不可用", Toast.LENGTH_SHORT).show();
        }
        if (name == null|| pass == null || token == null){
            Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
            startActivity(intent);
        }else {
            //判断，如果本地存储是管理员账户则直接以管理员账户登陆，否则以普通用户登陆
            if (isAdmin.equals("true")){
                String url = ConstantsBean.BASE_PATH+"admin/"+ConstantsBean.LOGIN+"?username="+name+"&password="+pass;
                postLoginAdmin(url);
            }else {
                String url = ConstantsBean.BASE_PATH+ConstantsBean.LOGIN+"?loginName="+name+"&password="+pass;
                postLoginClient(url);
            }
        }
    }
    private void postLoginClient(String url){
        JsonObjectRequest jsonObject = new JsonObjectRequest(
                Request.Method.POST,url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject json = new JSONObject(String.valueOf(response));
                            if (json.getString("status").equals("success")) {
                                if (json.has("content")) {
                                    JSONObject content = json.getJSONObject("content");
                                    String token = content.getString("auth-token");
                                    //设置全局变量
                                    CustomApplication.TOKEN = token;
                                    //存储到本地
                                    PreferenceUtil.putString("token", token);
                                    //设置跳转到主页-->学术页面
                                    CustomApplication.CURRENT_PAGE = ConstantsBean.PAGE_SCIENCE;
                                    //设置全局变量isAdmin
                                    CustomApplication.isAdmin = false;
                                    //跳转页面,同时关闭当前页面
                                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    SplashActivity.this.finish();
                                    Logger.d("parse json ---> token:  " + token);
                                }
                            }else {
                                Toast.makeText(SplashActivity.this, "登陆失败，请重新登陆", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Logger.d(error);
                        Toast.makeText(SplashActivity.this, "登陆失败，请重新登陆", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }
                }
        );
        CustomApplication.getRequestQueue().add(jsonObject);

    }
    private void postLoginAdmin(String url){

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject json = new JSONObject(String.valueOf(response));
                            //首先判断登陆请求返回状态，成功则进行下一步
                            if (json.getString("status").equals("success")) {
                                Toast.makeText(SplashActivity.this, "登陆成功请稍等", Toast.LENGTH_SHORT).show();
                                if (json.has("content")) {
                                    String token = json.getString("content");
                                    //设置全局变量
                                    CustomApplication.TOKEN = token;
                                    //存储到本地
                                    PreferenceUtil.putString("token", token);
                                    //设置跳转到主页-->学术页面
                                    CustomApplication.CURRENT_PAGE = ConstantsBean.PAGE_SCIENCE;
                                    //设置全局变量
                                    CustomApplication.isAdmin = true;
                                    //跳转页面,同时关闭当前页面
                                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    SplashActivity.this.finish();
                                    Logger.d("parse json ---> token:  " + token);
                                }
                            }else {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SplashActivity.this, "服务器错误", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<>();
                headers.put("X-Requested-With","XMLHttpRequest");
                return headers;
            }

//            @Override
//            public String getBodyContentType() {
//
//                return String.format("application/x-www-form-urlencoded; charset=%s", "utf-8");
//            }

        };
        CustomApplication.getRequestQueue().add(jsonObjectRequest);
    }
}
