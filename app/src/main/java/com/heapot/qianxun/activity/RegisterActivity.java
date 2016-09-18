package com.heapot.qianxun.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
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
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.util.CommonUtil;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Karl on 2016/9/18.
 */
public class RegisterActivity extends Activity implements View.OnClickListener {
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
    private void initData(){
        String name = edt_phone.getText().toString();
        if (CommonUtil.isMobileNO(name)){
            checkInfo(name);
        }else {
            Toast.makeText(RegisterActivity.this, "请检查手机号", Toast.LENGTH_SHORT).show();
            Logger.d("请检查手机号");
        }
    }

    /**
     * 验证信息
     */
    private void checkInfo(String name){

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                ConstantsBean.BASE_PATH + ConstantsBean.CHECK_LOGIN_NAME + name,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Logger.json(response.toString());
                        try {
                            JSONObject json = new JSONObject(String.valueOf(response));
                            String content = json.getString("content");
                            if (content.equals("true")){
                                sendMessage();
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
        queue.add(jsonObjectRequest);
    }
    /**
     * 发送验证码
     */
    private void sendMessage(){
        
    }
    /**
     * 提交注册
     */
    private void toRegister(){

    }

    /**
     * 点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_register_reset:
                break;
            case R.id.txt_register_reset_2:
                break;
            case R.id.txt_register_reset3:
                break;
            case R.id.txt_register_send_message:
                initData();
                break;
            case R.id.btn_register:
                break;
        }
    }
}
