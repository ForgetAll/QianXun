package com.heapot.qianxun.activity;

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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.blankj.utilcode.utils.NetworkUtils;
import com.heapot.qianxun.R;
import com.heapot.qianxun.application.CustomApplication;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.util.PreferenceUtil;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Karl on 2016/9/17.
 *
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private EditText edt_name,edt_pass;
    private TextView removeData,showPass,reset,register;
    private Button login;

    private static boolean isShowPass = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initEvent();

    }
    private void initView(){
        edt_name = (EditText) findViewById(R.id.edt_login_phone);
        edt_pass = (EditText) findViewById(R.id.edt_login_password);

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

    /**
     * 登陆事件，需要判断管理员账号和普通账号
     * 由于界面没有加用户自主选择管理员还是普通用户，所以这里先进行验证
     * 先以管理员登陆，失败后以普通用户登陆
     */
    //登陆前需要检查网络状态
    private void postLogin(){
        String username = edt_name.getText().toString();
        String password = edt_pass.getText().toString();
        boolean isAvailable = NetworkUtils.isAvailable(this);
        if (isAvailable){
            Toast.makeText(LoginActivity.this, "请稍等", Toast.LENGTH_SHORT).show();
            postLogin(username,password);
        }else {
            Toast.makeText(LoginActivity.this, "请检查网络连接", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 普通用户登陆
     * @param username 用户名
     * @param password 用户密码
     */
    private void postLogin(final String username, final String password){
        String url = ConstantsBean.BASE_PATH+ConstantsBean.LOGIN+"?loginName="+username+"&password="+password;
        JsonObjectRequest jsonObject = new JsonObjectRequest(
                Request.Method.POST,
                url,
                null,
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
                                    //设置跳转到主页-->学术页面,但是因为默认就是学术作为主页，所以这里其实并没有实际作用，仅保险起见设置，可删除
                                    CustomApplication.CURRENT_PAGE = ConstantsBean.PAGE_SCIENCE;
                                    //存储到本地
                                    PreferenceUtil.putString("token", token);
                                    PreferenceUtil.putString("name", username);
                                    PreferenceUtil.putString("password", password);
                                    PreferenceUtil.putString("isAdmin","false");
                                    //跳转页面,同时关闭当前页面
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    LoginActivity.this.finish();
                                    Logger.d("parse json ---> token:  " + token);
                                }
                            }else {
                                Toast.makeText(LoginActivity.this, "登陆失败"+response.get("message"), Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(LoginActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();

                    }
                }
        );
        CustomApplication.getRequestQueue().add(jsonObject);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_remove_data:
                edt_name.setText("");
                break;
            case R.id.txt_show_pass:
                if (isShowPass){
                    //明文显示密码
                    edt_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    showPass.setText("隐藏密码");
                }else {
                    //密文显示密码
                    edt_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    showPass.setText("显示密码");
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
                Intent intentToRegister = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intentToRegister);
                break;
            case R.id.txt_reset_password:
                //找回密码
                Intent intentForgetPass = new Intent(LoginActivity.this,FindPassActivity.class);
                startActivity(intentForgetPass);
                break;
        }
    }
}
