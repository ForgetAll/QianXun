package com.heapot.qianxun.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.blankj.utilcode.utils.NetworkUtils;
import com.heapot.qianxun.R;
import com.heapot.qianxun.activity.BaseActivity;
import com.heapot.qianxun.activity.MainActivity;
import com.heapot.qianxun.application.CustomApplication;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.bean.MyUserBean;
import com.heapot.qianxun.util.CommonUtil;
import com.heapot.qianxun.util.JsonUtil;
import com.heapot.qianxun.util.PreferenceUtil;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

/**
 * Created by Karl on 2016/9/18.
 * 注册页面,只能注册普通用户
 * 发送验证码----提交注册----提交登陆----连接融云服务器----跳转主页
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
            Boolean isPhone = CommonUtil.isMobileNO(phone);
            if (isPhone) {
                String url = ConstantsBean.BASE_PATH + ConstantsBean.CHECK_LOGIN_NAME + phone;
                checkInfo(url,phone);
            } else {
                Toast.makeText(RegisterActivity.this, "请检查手机号", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(RegisterActivity.this, "请检查网络连接", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkInfo(final String url, final String phone){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Logger.json(String.valueOf(response));
                        try {
                            String status = response.getString("status");
                            if (status.equals("success")){
                                Toast.makeText(RegisterActivity.this, "验证成功,正在发送验证码", Toast.LENGTH_SHORT).show();
                                sendMessage(phone);
                                sendMessage.setClickable(false);

                            }else {
                                Toast.makeText(RegisterActivity.this, "该手机号已注册", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        CustomApplication.getRequestQueue().add(jsonObjectRequest);

    }
    private void sendMessage(String phone){
        String url = ConstantsBean.BASE_PATH + ConstantsBean.SEND_MESSAGE + phone;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        checkMessageResponse(response);
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

    private void checkMessageResponse(JSONObject response){
        try {
            String status = response.getString("status");
            if (status.equals("success")){
                Toast.makeText(RegisterActivity.this, "请注意查收短信", Toast.LENGTH_SHORT).show();
                sendMessage.setText("重新发送");
            }else {
                Toast.makeText(RegisterActivity.this, "发送失败，请重新尝试", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void postRegister(){
        String phone = edt_phone.getText().toString();
        String nickname = edt_name.getText().toString();
        String pass = edt_password.getText().toString();
        String token = edt_mess.getText().toString();
        if (phone.equals("") || nickname.equals("") || pass.equals("") || token.equals("")){
            Toast.makeText(RegisterActivity.this, "所有项不能为空", Toast.LENGTH_SHORT).show();
        }else {
            postRegister(phone,pass,token,nickname);
        }
    }

    private void postRegister(final String phone, final String pass, final String message, final String nickname){
        String url = ConstantsBean.BASE_PATH + ConstantsBean.REGISTER
                + "?phone=" + phone
                + "&password=" + pass
                + "&nickname="+nickname
                + "&token=" + message;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseRegisterResponse(response,phone,pass,nickname);

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

    private void parseRegisterResponse(JSONObject response,String username,String password,String nickname){
        try {
            String status = response.getString("status");
            if (status.equals("success")){
                postLogin(username,password);
                PreferenceUtil.putString("phone",username);
                PreferenceUtil.putString("password",password);
                PreferenceUtil.putString("nickname",nickname);
            }else {
                Toast.makeText(RegisterActivity.this, "注册失败："+response.getString("message"), Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private void postLogin(final String username, final String password) {
        String url = ConstantsBean.BASE_PATH + ConstantsBean.LOGIN + "?loginName=" + username + "&password=" + password;
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
                        Toast.makeText(RegisterActivity.this, "登陆失败"+error.getMessage(), Toast.LENGTH_SHORT).show();

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
                    getUserInfo();
                }
            } else {
                Toast.makeText(RegisterActivity.this, "登陆失败" + response.get("message"), Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getUserInfo() {
        String url = ConstantsBean.BASE_PATH + ConstantsBean.PERSONAL_INFO;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Logger.d(response);
                        try {
                            String status = response.getString("status");
                            if (status.equals("success")) {
                                MyUserBean myUserBean = (MyUserBean) JsonUtil.fromJson(String.valueOf(response), MyUserBean.class);
                                MyUserBean.ContentBean userBean = myUserBean.getContent();
                                PreferenceUtil.putString(ConstantsBean.USER_PHONE, myUserBean.getContent().getPhone());
                                PreferenceUtil.putString(ConstantsBean.nickName, myUserBean.getContent().getNickname());
                                PreferenceUtil.putString(ConstantsBean.userAutograph, myUserBean.getContent().getDescription());
                                PreferenceUtil.putString(ConstantsBean.userImage, myUserBean.getContent().getIcon());
                                PreferenceUtil.putString(ConstantsBean.USER_ID, myUserBean.getContent().getId());
                                PreferenceUtil.putString("name",userBean.getName());
                                Log.e("用户ID",myUserBean.getContent().getId());


                            } else {
                                Toast.makeText(RegisterActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
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
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put(ConstantsBean.KEY_TOKEN, getAppToken());
                return headers;
            }
        };
        CustomApplication.getRequestQueue().add(jsonObjectRequest);

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
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    finish();

                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {

                }
            });
        }
    }

}
