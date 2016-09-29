package com.heapot.qianxun.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import com.heapot.qianxun.util.PreferenceUtil;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 15859 on 2016/9/28.
 * 修改密码
 */
public class SystemChangePassWord extends BaseActivity implements View.OnClickListener {
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
                String oldPwd = mOldPwd.getText().toString();
                //判断网络连接
                boolean isAvailable = NetworkUtils.isAvailable(this);
                if (isAvailable) {
                    Toast.makeText(SystemChangePassWord.this, "请稍等", Toast.LENGTH_SHORT).show();
                    checkPwd(oldPwd);
                } else {
                    Toast.makeText(SystemChangePassWord.this, "请检查网络", Toast.LENGTH_SHORT).show();
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
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("status").equals("success")) {
                                Toast.makeText(SystemChangePassWord.this, "验证成功", Toast.LENGTH_SHORT).show();
                                mOldPwd.setVisibility(View.GONE);
                                mOneNewPwd.setVisibility(View.VISIBLE);
                                mTwoNewPwd.setVisibility(View.VISIBLE);
                                mSure.setVisibility(View.GONE);
                                mComplete.setVisibility(View.VISIBLE);
                            } else {
                                Toast.makeText(SystemChangePassWord.this, "密码输入错误", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Logger.d(error);
                Toast.makeText(SystemChangePassWord.this, "密码输入错误", Toast.LENGTH_SHORT).show();
            }
        });
        CustomApplication.getRequestQueue().add(objectRequest);
    }

    //新密码设置
    private void update(String onePwd, String twoPwd) {
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
                                    Toast.makeText(SystemChangePassWord.this, "密码修改完成,请重新登陆", Toast.LENGTH_SHORT).show();
                                    Intent updatePwd = new Intent(SystemChangePassWord.this, LoginActivity.class);
                                    startActivity(updatePwd);
                                    finish();
                                    ActivityCollector.finishAll();
                                } else {
                                    Toast.makeText(SystemChangePassWord.this, "密码修改失败", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(SystemChangePassWord.this, "密码输入错误", Toast.LENGTH_SHORT).show();
                }
            }
            );
            CustomApplication.getRequestQueue().add(jsonObjectRequest);
        } else {
            Toast.makeText(SystemChangePassWord.this, "密码输入错误", Toast.LENGTH_SHORT).show();
        }
    }
}
