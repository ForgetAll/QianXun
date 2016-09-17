package com.heapot.qianxun.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.heapot.qianxun.R;
import com.heapot.qianxun.application.CustomApplication;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.util.PreferenceUtil;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Karl on 2016/9/17.
 */
public class LoginActivity extends Activity implements View.OnClickListener {
    private EditText edt_name,edt_pass;
    private TextView removeData,showPass,reset,register;
    private Button login;

    private static boolean isDisplayPass = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initEvent();

    }
    private void initView(){
        edt_name = (EditText) findViewById(R.id.edt_phone);
        edt_pass = (EditText) findViewById(R.id.edt_password);

        removeData = (TextView) findViewById(R.id.txt_remove_data);
        showPass = (TextView) findViewById(R.id.txt_show_pass);

        login = (Button) findViewById(R.id.btn_login);

        register = (TextView) findViewById(R.id.txt_login_to_register);
        reset = (TextView) findViewById(R.id.txt_reset_password);

    }
    private void initEvent(){
        removeData.setOnClickListener(this);
        showPass.setOnClickListener(this);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        reset.setOnClickListener(this);

    }
    private void toLogin(){
        //https://qinxi1992.xicp.net/login?loginName=fankarl&password=123456
        String name = edt_name.getText().toString();
        String pass = edt_pass.getText().toString();
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObject = new JsonObjectRequest(
                Request.Method.POST,
                ConstantsBean.BASE_PATH+ConstantsBean.LOGIN+"?loginName="+name+"&password="+pass,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(LoginActivity.this, "登陆成功请稍等", Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject json = new JSONObject(String.valueOf(response));
                            if (json.has("content")){
                                JSONObject content = json.getJSONObject("content");
                                String token = content.getString("auth-token");
                                //设置全局变量
                                CustomApplication.TOKEN = token;
                                //存储到本地
                                PreferenceUtil.putString("token",token);
                                //跳转页面,同时关闭当前页面
                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(intent);
                                LoginActivity.this.finish();
                                Logger.d("parse json ---> token:  "+token);
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
                        Toast.makeText(LoginActivity.this, "登陆失败，请检查输入信息或者网络状态", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        queue.add(jsonObject);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_remove_data:
                edt_name.setText("");
                break;
            case R.id.txt_show_pass:
                if (isDisplayPass){
                    //明文显示密码
                    edt_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    showPass.setText("隐藏密码");
                }else {
                    //密文显示密码
                    edt_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    showPass.setText("显示密码");
                }
                isDisplayPass = !isDisplayPass;
                edt_pass.postInvalidate();
                break;
            case R.id.btn_login:
                toLogin();
                break;
            case R.id.txt_login_to_register:
                break;
            case R.id.txt_reset_password:
                break;
        }
    }
}
