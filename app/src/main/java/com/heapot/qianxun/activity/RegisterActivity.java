package com.heapot.qianxun.activity;

import android.content.Intent;
import android.os.Bundle;
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
import com.heapot.qianxun.application.ActivityCollector;
import com.heapot.qianxun.application.CustomApplication;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.util.CommonUtil;
import com.heapot.qianxun.util.PreferenceUtil;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Karl on 2016/9/18.
 * 注册页面,只能注册普通用户
 *
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private EditText edt_phone,edt_name,edt_password,edt_mess;
    private TextView sendMessage,resetName,resetPass,resetPhone;
    private Button mRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initEvent();
    }

    /**
     * 初始化界面
     */
    private void initView(){
        edt_phone = (EditText) findViewById(R.id.edt_register_phone);
        edt_name = (EditText) findViewById(R.id.edt_register_name);
        edt_password = (EditText) findViewById(R.id.edt_register_password);
        edt_mess = (EditText) findViewById(R.id.edt_register_mess);

        resetPhone = (TextView) findViewById(R.id.txt_register_reset);
        resetName = (TextView) findViewById(R.id.txt_register_reset_2);
        resetPass = (TextView) findViewById(R.id.txt_register_reset3);

        sendMessage = (TextView) findViewById(R.id.txt_register_send_message);

        mRegister = (Button) findViewById(R.id.btn_register);

    }

    /**
     * 注册事件
     */
    private void initEvent(){
        resetPass.setOnClickListener(this);
        resetPhone.setOnClickListener(this);
        resetName.setOnClickListener(this);
        sendMessage.setOnClickListener(this);
        mRegister.setOnClickListener(this);

    }
    /**
     * 点击事件
     * @param v 获取id
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_register_reset:
                edt_phone.setText("");//清空手机号
                break;
            case R.id.txt_register_reset_2:
                edt_name.setText("");//清空名称
                break;
            case R.id.txt_register_reset3:
                edt_password.setText("");//清空密码
                break;
            case R.id.txt_register_send_message:
                sendMessage();//发送验证码
                break;
            case R.id.btn_register:
                postRegister();//注册请求
                break;
        }
    }
    private void sendMessage(){
        boolean isAvailable = NetworkUtils.isAvailable(this);
        if (isAvailable) {
            String phone = edt_phone.getText().toString();
            if (CommonUtil.isMobileNO(phone)) {
                checkInfo(phone);
            } else {
                Toast.makeText(RegisterActivity.this, "请检查手机号", Toast.LENGTH_SHORT).show();
                Logger.d("请检查手机号");
            }
        }else {
            Toast.makeText(RegisterActivity.this, "请检查网络连接", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 验证信息
     */
    private void checkInfo(final String phone){
        String url = ConstantsBean.BASE_PATH + ConstantsBean.CHECK_LOGIN_NAME + phone;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Logger.json(response.toString());
                        try {
                            String content = response.getString("content");
                            Logger.d(content);
                            if (content.equals("true")){
                                //通过验证发送验证码
                                sendMessage(phone);
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
                        Toast.makeText(RegisterActivity.this, "发送失败，请重新尝试", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        CustomApplication.requestQueue.add(jsonObjectRequest);

    }

    /**
     * 通过验证后发送验证码
     * @param phone 发送验证码提供所需手机号
     */
    private void sendMessage(String phone){
        String url = ConstantsBean.BASE_PATH + ConstantsBean.SEND_MESSAGE + phone;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Logger.json(response.toString());
                        try {
                            String status = response.getString("status");
                            if (status.equals("success")){
                                sendMessage.setText("请稍等");
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
                    }
                }

        );
        CustomApplication.getRequestQueue().add(jsonObjectRequest);
    }

    /**
     * 提交注册
     * phone作为登陆名因此本地存储为name，用户名存储为nickname
     *
     */
    private void postRegister(){
        String phone = edt_phone.getText().toString();
        String nickname = edt_name.getText().toString();
        String pass = edt_password.getText().toString();
        String token = edt_mess.getText().toString();//这里的token是验证码
        if (phone.equals("") || nickname.equals("") || pass.equals("") || token.equals("")){
            Toast.makeText(RegisterActivity.this, "所有项不能为空", Toast.LENGTH_SHORT).show();
        }else {
            postRegister(phone,pass,token,nickname);
        }
    }

    /**
     * 提交登陆请求
     * @param phone 手机号
     * @param pass 密码
     * @param message 验证码
     * @param nickname 昵称
     */
    private void postRegister(final String phone, final String pass, String message, final String nickname){
        String url = ConstantsBean.BASE_PATH + ConstantsBean.REGISTER
                + "?phone=" + phone
                + "&password" + pass
                + "&nickname="+nickname
                + "&token" + message;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Logger.json(String.valueOf(response));
                        try {
                            String status = response.getString("status");
                            if (status.equals("success")){
                                //保存账户信息到本地,因为这里以手机号为主，所以将手机号作为登陆名存储
                                PreferenceUtil.putString("name",phone);
                                PreferenceUtil.putString("password",pass);
                                PreferenceUtil.putString("nickname",nickname);
                                //注册成功，跳转页面
                                Intent intent = new Intent(RegisterActivity.this,Subscription.class);
                                startActivity(intent);
                                //关闭登录和注册页面，因为开始只有这两个活动，完全可以使用finishAll()
                                ActivityCollector.finishAll();
                            }else {
                                Toast.makeText(RegisterActivity.this, "注册失败："+response.getString("message"), Toast.LENGTH_SHORT).show();
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
                    }
                }
        );
        CustomApplication.getRequestQueue().add(jsonObjectRequest);
    }


}
