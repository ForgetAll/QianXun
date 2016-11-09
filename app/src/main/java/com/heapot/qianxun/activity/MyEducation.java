package com.heapot.qianxun.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.heapot.qianxun.R;
import com.heapot.qianxun.application.CustomApplication;
import com.heapot.qianxun.bean.AddEducationSchool;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.util.JsonUtil;
import com.heapot.qianxun.util.ToastUtil;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 15859 on 2016/11/1.
 */
public class MyEducation extends BaseActivity implements View.OnClickListener {
    private RelativeLayout rl_school, rl_profession, rl_startYear;
    private TextView tv_school, tv_profession, tv_startYear, tv_complete, tv_back;
    private int requestCode;
    private int personalStatus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education_edit);
        intView();
    }

    private void intView() {
        rl_school = (RelativeLayout) findViewById(R.id.rl_school);
        rl_school.setOnClickListener(this);
        rl_profession = (RelativeLayout) findViewById(R.id.rl_profession);
        rl_profession.setOnClickListener(this);
        rl_startYear = (RelativeLayout) findViewById(R.id.rl_startYear);
        rl_startYear.setOnClickListener(this);
        tv_school = (TextView) findViewById(R.id.tv_school);
        tv_profession = (TextView) findViewById(R.id.tv_profession);
        tv_startYear = (TextView) findViewById(R.id.tv_startYear);
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_back.setOnClickListener(this);
        tv_complete = (TextView) findViewById(R.id.tv_complete);
        tv_complete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_complete:
                complete();
                break;
            case R.id.rl_school:
                requestCode = 111;
                Intent school = new Intent(MyEducation.this, SchoolListActivity.class);
                startActivityForResult(school, requestCode);
                break;
            case R.id.rl_profession:
                requestCode = 112;
                jumpAlterActivity("");
                break;
            case R.id.rl_startYear:
                requestCode = 113;
                Intent birth = new Intent(MyEducation.this, BirthActivity.class);
                startActivityForResult(birth, requestCode);
                break;
        }
    }

    private void complete() {
        String school = tv_school.getText().toString().trim();
        String profession = tv_profession.getText().toString().trim();
        String starttea = tv_startYear.getText().toString().trim();
        if (!TextUtils.isEmpty(school) && !TextUtils.isEmpty(profession) && !TextUtils.isEmpty(starttea)) {
            AddEducationSchool addEducationSchool=new AddEducationSchool();
            addEducationSchool.setSchool(school);
            addEducationSchool.setProfession(profession);
            addEducationSchool.setFaculty("");
            addEducationSchool.setStartYear(Integer.parseInt(starttea));
            addEducationSchool.setEndYear(0);

            String body=JsonUtil.toJson(addEducationSchool);
            upData(body);
        } else {
            ToastUtil.show("请检查输入内容");
        }
    }

    private void upData(String data) {
        JSONObject json = null;
        try {
            json = new JSONObject(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = ConstantsBean.BASE_PATH + ConstantsBean.USER_EDUCATION;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, url, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Logger.d(response);
                        try {
                            String status = response.getString("status");
                            if (status.equals("success")) {
                                Toast.makeText(MyEducation.this, "成功", Toast.LENGTH_SHORT).show();
                                personalStatus=1;
                                sendBroad();
                                finish();
                            } else {
                                Toast.makeText(MyEducation.this, "更新失败", Toast.LENGTH_SHORT).show();
                                Logger.d(response.getString("message"));
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

    private void sendBroad() {
        //发送广播
        Intent intent = new Intent("com.myeducation.change");
        intent.putExtra("myeducation", personalStatus);
        switch (personalStatus) {
            case 0://没有更新不需要处理
                break;
            case 1:
                personalStatus = 0;
                break;
        }
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.sendBroadcast(intent);//发送本地广播
    }


    private void jumpAlterActivity(String info) {
        Intent intent = new Intent(MyEducation.this, PersonInfoAlterActivity.class);
        intent.putExtra(ConstantsBean.INFO, info);
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 111:
                    String schoolName = data.getStringExtra(ConstantsBean.INFO);
                    tv_school.setText(schoolName);
                    break;
                case 112:
                    String profession = data.getStringExtra(ConstantsBean.INFO);
                    tv_profession.setText(profession);
                    break;
                case 113:
                    int birth = data.getIntExtra(ConstantsBean.INFO, 1992);
                    tv_startYear.setText(String.valueOf(birth));
                    break;
            }
        }
    }
}
