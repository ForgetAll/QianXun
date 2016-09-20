package com.heapot.qianxun.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.heapot.qianxun.R;
import com.heapot.qianxun.application.CustomApplication;
import com.heapot.qianxun.bean.ConstantsBean;
import com.orhanobut.logger.Logger;

import org.json.JSONObject;

/**
 * Created by Karl on 2016/9/20.
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
                break;
            case R.id.txt_find_send_message:
                postSendMess();
                break;
        }
    }
    /**
     * 网络请求
     * 1、发送验证码
     * 2、重置密码
     */
    private void postSendMess(){
        String postPhone = phone.getText().toString();
        String url = ConstantsBean.BASE_PATH+"findPasswordByPhone?phone="+postPhone;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Logger.json(String.valueOf(response));

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
