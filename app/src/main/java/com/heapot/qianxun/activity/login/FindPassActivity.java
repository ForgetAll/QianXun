package com.heapot.qianxun.activity.login;

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
import com.heapot.qianxun.R;
import com.heapot.qianxun.activity.BaseActivity;
import com.heapot.qianxun.application.CustomApplication;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.util.CommonUtil;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Karl on 2016/9/20.
 * 当前页面实现找回密码功能
 */
public class FindPassActivity extends BaseActivity implements View.OnClickListener {
    private EditText phone,message,password;
    private TextView reset_phone,reset_pass,send_message;
    private Button resetPassBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);
        initView();
        initEvent();

    }
    private void initView(){
        phone = (EditText) findViewById(R.id.edt_find_phone);
        message = (EditText) findViewById(R.id.edt_find_mess);
        password = (EditText) findViewById(R.id.edt_find_password);

        reset_phone = (TextView) findViewById(R.id.txt_find_reset);
        reset_pass = (TextView) findViewById(R.id.txt_find_reset_pass);
        send_message = (TextView) findViewById(R.id.txt_find_send_message);

        resetPassBtn = (Button) findViewById(R.id.btn_find_pass);
    }
    private void initEvent(){
        resetPassBtn.setOnClickListener(this);
        send_message.setOnClickListener(this);
        reset_pass.setOnClickListener(this);
        reset_phone.setOnClickListener(this);
    }

    /**
     * 两种点击事件：
     * 1、发送验证码
     * 2、发送提交密码重置请求
     * @param v 可以通过V获取id等信息
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_find_pass:
                resetPass();
                break;
            case R.id.txt_find_send_message:
                sendMess();
                message.setFocusable(true);
                break;
            case R.id.txt_find_reset:
                phone.setText("");
                message.setText("");
                break;
            case R.id.txt_find_reset_pass:
                password.setText("");
                break;
        }
    }
    /**
     * 网络请求
     * 1、发送验证码
     * 2、重置密码
     */
    private void sendMess(){
        String postPhone = phone.getText().toString();
        //验证手机号的合法性
        boolean isPhone = CommonUtil.isMobileNO(postPhone);
        if (isPhone){
            postSendMess(postPhone);
        }else {
            Toast.makeText(FindPassActivity.this, "请检查所输入手机号", Toast.LENGTH_SHORT).show();
        }
    }
    private void postSendMess(final String postPhone){
        String url = ConstantsBean.BASE_PATH+ConstantsBean.SEND_RESET_MESSAGE+postPhone;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Logger.json(String.valueOf(response));
                        //比较简单，手动解析
                        try {
                            String status = response.getString("status");
                            if (status.equals("success")){
                                Toast.makeText(FindPassActivity.this, "发送成功，请稍等", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(FindPassActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(FindPassActivity.this, "发送失败，请检查输入后再次尝试", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        CustomApplication.getRequestQueue().add(jsonObjectRequest);
    }

    /**
     * 提交重置密码请求
     */
    private void resetPass(){
        String postPhone = phone.getText().toString();
        String postMessage = message.getText().toString();
        String postPassword = password.getText().toString();
        if (postMessage.equals("")||postPhone.equals("")||postPassword.equals("")){
            Toast.makeText(FindPassActivity.this, "所有项不能为空", Toast.LENGTH_SHORT).show();
        }else {
            String url = ConstantsBean.BASE_PATH+ConstantsBean.RESET_PASSWORD+
                    "?phone="+postPhone+
                    "&newPassword="+postPassword+
                    "&token="+postMessage;
            postResetPass(url);
        }
    }
    private void postResetPass(String url){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Logger.json(String.valueOf(response));
                        try {
                            JSONObject jsonObject = new JSONObject(String.valueOf(response));
                            String status = jsonObject.getString("status");
                            if (status.equals("success")){
                                Toast.makeText(FindPassActivity.this, "重置成功，请登陆", Toast.LENGTH_SHORT).show();
                                FindPassActivity.this.finish();
                            }else {
                                Toast.makeText(FindPassActivity.this, "重置失败,请再次尝试", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        CustomApplication.getRequestQueue().add(jsonObjectRequest);
    }

}
