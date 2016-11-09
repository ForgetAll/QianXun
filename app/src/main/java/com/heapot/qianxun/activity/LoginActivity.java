package com.heapot.qianxun.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.blankj.utilcode.utils.NetworkUtils;
import com.heapot.qianxun.R;
import com.heapot.qianxun.application.CustomApplication;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.util.CommonUtil;
import com.heapot.qianxun.util.PreferenceUtil;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

/**
 * Created by Karl on 2016/9/17.
 * 注册页面
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private EditText edt_name, edt_pass;
    private TextView reset, register;
    private ImageView removeData, showPass;
    private Button login;


    private static boolean isShowPass = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initEvent();

    }

    private void initView() {
        edt_name = (EditText) findViewById(R.id.edt_login_phone);
        edt_pass = (EditText) findViewById(R.id.edt_login_password);

        removeData = (ImageView) findViewById(R.id.txt_remove_data);
        showPass = (ImageView) findViewById(R.id.txt_show_pass);
        login = (Button) findViewById(R.id.btn_login);
        register = (TextView) findViewById(R.id.txt_login_to_register);
        reset = (TextView) findViewById(R.id.txt_reset_password);


    }

    private void initEvent() {
        removeData.setOnClickListener(this);
        showPass.setOnClickListener(this);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        reset.setOnClickListener(this);

    }


    private void postLogin() {
        String username = edt_name.getText().toString();
        String password = edt_pass.getText().toString();
        boolean isAvailable = NetworkUtils.isAvailable(this);
        Boolean isPhone = CommonUtil.isMobileNO(username);
        if (isAvailable) {
            if (isPhone) {
                Toast.makeText(LoginActivity.this, "请稍等", Toast.LENGTH_SHORT).show();
                postLogin(username, password);
            }else {
                Toast.makeText(LoginActivity.this, "请检查手机号", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(LoginActivity.this, "请检查网络连接", Toast.LENGTH_SHORT).show();
        }
    }

    private void postLogin(final String username, final String password) {
        String url = ConstantsBean.BASE_PATH + ConstantsBean.LOGIN + "?loginName=" + username + "&password=" + password;
        Logger.d(url);
        JsonObjectRequest jsonObject = new JsonObjectRequest(
                Request.Method.POST, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        parseResponse(response,username,password);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Logger.d(error);
                        Toast.makeText(LoginActivity.this, "登陆失败"+error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
        );
        CustomApplication.getRequestQueue().add(jsonObject);
    }

    private void parseResponse(JSONObject response,String username,String password){
        try {
            if (response.getString("status").equals("success")) {
                if (response.has("content")) {
                    JSONObject content = response.getJSONObject("content");
                    String token = content.getString("auth-token");
                    //存储到本地
                    PreferenceUtil.putString("token", token);
                    PreferenceUtil.putString("phone", username);
                    PreferenceUtil.putString("password", password);
                    getIMToken(token);
                }
            } else {
                Toast.makeText(LoginActivity.this, "登陆失败" + response.get("message"), Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getIMToken(final String token){
        if (getChatToken().equals("")) {
            String url = ConstantsBean.BASE_PATH + ConstantsBean.IM_TOKEN;
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            parseChatTokenResponse(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }
            ) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put(ConstantsBean.KEY_TOKEN, token);
                    return headers;
                }
            };
            CustomApplication.getRequestQueue().add(jsonObjectRequest);
        }else {
            String chat_token = getChatToken();
            connChat(chat_token);
        }
    }

    private void parseChatTokenResponse(JSONObject response){
        try {
            if (response.getString("status").equals("success")){

                String im_token = response.getString("content");
                connChat(im_token);
            }else {

            }
        } catch (JSONException e) {
            e.printStackTrace();
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
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();

                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {

                }
            });
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_remove_data:
                edt_name.setText("");
                break;
            case R.id.txt_show_pass:
                if (isShowPass) {
                    //明文显示密码
                    edt_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    showPass.setImageResource(R.mipmap.ic_show_pasword);
                } else {
                    //密文显示密码
                    edt_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    showPass.setImageResource(R.mipmap.ic_hide_password);
                }
                isShowPass = !isShowPass;
                edt_pass.postInvalidate();
                break;
            case R.id.btn_login:
                //提交登陆请求,登陆成功跳转到主页
                postLogin();
                break;
            case R.id.txt_login_to_register:
                //跳转注册页面
                Intent intentToRegister = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intentToRegister);
                break;
            case R.id.txt_reset_password:
                //找回密码
                Intent intentForgetPass = new Intent(LoginActivity.this, FindPassActivity.class);
                startActivity(intentForgetPass);
                break;
        }
    }

}
