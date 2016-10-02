package com.heapot.qianxun.activity;

import android.os.Bundle;
import android.view.View;
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
import com.heapot.qianxun.application.CustomApplication;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.util.PreferenceUtil;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 15859 on 2016/9/28.
 * 修改密码
 */
public class SystemChangePassword extends BaseActivity implements View.OnClickListener {
    private EditText mOldPwd, mOneNewPwd, mTwoNewPwd;
    private TextView mComplete, mBack, mSure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pwd);
        findView();
    }

    private void findView() {
        mOldPwd = (EditText) findViewById(R.id.et_oldPwd);
        mComplete = (TextView) findViewById(R.id.tv_complete);
        mOneNewPwd = (EditText) findViewById(R.id.et_newPwdOne);
        mTwoNewPwd = (EditText) findViewById(R.id.et_newPwdTwo);
        mSure = (TextView) findViewById(R.id.tv_sure);
        mBack = (TextView) findViewById(R.id.tv_back);
        mComplete.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mSure.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_sure:
                //判断网络连接
                boolean isAvailable = NetworkUtils.isAvailable(this);
                if (isAvailable) {
                    //Toast.makeText(SystemChangePassword.this, "请稍等", Toast.LENGTH_SHORT).show();
                    String oldPwd = mOldPwd.getText().toString();
                    checkPwd(oldPwd);
                } else {
                    Toast.makeText(SystemChangePassword.this, "请检查网络", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_complete:
                //验证新密码
                String onePwd = mOneNewPwd.getText().toString();
                String twoPwd = mTwoNewPwd.getText().toString();
                update(onePwd, twoPwd);
                break;
        }
    }

    //验证原密码
    private void checkPwd(String oldPwd) {
        String url = ConstantsBean.BASE_PATH + ConstantsBean.CHECK_PWD + oldPwd;
        Logger.d(url);
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("status");
                            Logger.d(status);
                            Logger.d(response.getString("message"));
                            if (status.equals("success")) {
                                Toast.makeText(SystemChangePassword.this, "验证成功", Toast.LENGTH_SHORT).show();
                                mOldPwd.setVisibility(View.GONE);
                                mOneNewPwd.setVisibility(View.VISIBLE);
                                mTwoNewPwd.setVisibility(View.VISIBLE);
                                mSure.setVisibility(View.GONE);
                                mComplete.setVisibility(View.VISIBLE);
                            } else {
                                Toast.makeText(SystemChangePassword.this, "密码输入错误", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Logger.d(error);
                Toast.makeText(SystemChangePassword.this, "密码输入有误", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> headers = new HashMap<>();
                headers.put(ConstantsBean.KEY_TOKEN, CustomApplication.TOKEN);
                return headers;
            }
        };
        CustomApplication.getRequestQueue().add(objectRequest);
    }

    //新密码设置
    private void update(final String onePwd, String twoPwd) {
        if (onePwd.equals(twoPwd)) {
            String url = ConstantsBean.BASE_PATH + ConstantsBean.UPDATE_PWD + PreferenceUtil.getString("password") + "&password=" + onePwd;
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                    url,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if (response.getString("status").equals("success")) {
                                    PreferenceUtil.putString("password", onePwd);
                                    String url = ConstantsBean.BASE_PATH + ConstantsBean.LOGIN + "?loginName=" + PreferenceUtil.getString("phone") + "&password=" + onePwd;
                                    reLogin(url);
                                } else {
                                    Toast.makeText(SystemChangePassword.this, "密码修改失败", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        //修改密码之后自动登陆
                        private void reLogin(String url) {
                            JsonObjectRequest jsonObject = new JsonObjectRequest(
                                    Request.Method.POST, url, null,
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
                                                        Toast.makeText(SystemChangePassword.this, "密码修改成功", Toast.LENGTH_SHORT).show();
                                                        finish();
                                                        Logger.d("parse json ---> token:  " + token);
                                                    }
                                                } else {
                                                    Toast.makeText(SystemChangePassword.this, "后台登陆失败，请重新登陆", Toast.LENGTH_SHORT).show();

                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(SystemChangePassword.this, "后台登陆失败，请重新登陆", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                            );
                            CustomApplication.getRequestQueue().add(jsonObject);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(SystemChangePassword.this, "新密码输入有误，请重新输入", Toast.LENGTH_SHORT).show();
                }
            }
            ) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {

                    Map<String, String> headers = new HashMap<>();
                    headers.put(ConstantsBean.KEY_TOKEN, CustomApplication.TOKEN);
                    return headers;
                }
            };

            CustomApplication.getRequestQueue().add(jsonObjectRequest);
        } else {
            Toast.makeText(SystemChangePassword.this, "新密码输入有误，请重新输入", Toast.LENGTH_SHORT).show();
        }
    }
}
