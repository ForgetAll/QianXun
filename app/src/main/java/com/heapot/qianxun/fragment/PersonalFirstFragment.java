package com.heapot.qianxun.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.heapot.qianxun.R;
import com.heapot.qianxun.activity.personal.MyEducation;
import com.heapot.qianxun.application.CustomApplication;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.bean.MyEducationBean;
import com.heapot.qianxun.util.JsonUtil;
import com.heapot.qianxun.util.PreferenceUtil;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 15859 on 2016/8/31.
 */
public class PersonalFirstFragment extends Fragment {
    public static final String PAGE = "PAGE";
    private TextView mNumber;
    private TextView tv_school, tv_profession, tv_startYear;
    private int requestCode;
    private String schoolName;
    //List<MyEducationBean.ContentBean> list = new ArrayList<>();
    private RelativeLayout rl_school, rl_profession, rl_startYear;
    //本地广播尝试
    private IntentFilter intentFilter;
    private RefreshEducationReceiver refreshReceiver;
    private LocalBroadcastManager localBroadcastManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.personal_first_center, container, false);
        rl_school = (RelativeLayout) mView.findViewById(R.id.rl_school);
        rl_profession = (RelativeLayout) mView.findViewById(R.id.rl_profession);
        rl_startYear = (RelativeLayout) mView.findViewById(R.id.rl_startYear);
        mNumber = (TextView) mView.findViewById(R.id.tv_phoneNumber);
        tv_school = (TextView) mView.findViewById(R.id.tv_school);
        tv_profession = (TextView) mView.findViewById(R.id.tv_profession);
        tv_startYear = (TextView) mView.findViewById(R.id.tv_startYear);
        mNumber.setText(PreferenceUtil.getString("phone"));
        loadData();

        return mView;
    }

    private void loadData() {
        String url = ConstantsBean.BASE_PATH + ConstantsBean.USER_EDUCATION;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Logger.json(String.valueOf(response));
                        Log.e("所有的Json数据：", response.toString());
                        try {
                            String status = response.getString("status");
                            if (status.equals("success")) {
                                MyEducationBean myEducationBean = (MyEducationBean) JsonUtil.fromJson(String.valueOf(response), MyEducationBean.class);
                               // List<MyEducationBean.ContentBean>     list = myEducationBean.getContent();
                              /*  Log.e("MyEducationBean教育的经历：", String.valueOf(myEducationBean.getContent().size()));
                                Log.e("教育的经历：", String.valueOf(list.size()));*/
                                if (myEducationBean.getContent().isEmpty()) {
                                    setClick();
                                } else {
                                    String school = myEducationBean.getContent().get(0).getSchool();
                                    String profession = myEducationBean.getContent().get(0).getProfession();
                                    int startyear = myEducationBean.getContent().get(0).getStartYear();
                                    tv_school.setText(school);
                                    tv_profession.setText(profession);
                                    tv_startYear.setText(String.valueOf(startyear));
                                    setNewClick();
                                }


                            } else {

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
                String token = PreferenceUtil.getString("token");
                headers.put(ConstantsBean.KEY_TOKEN, token);
                return headers;
            }
        };
        CustomApplication.getRequestQueue().add(jsonObjectRequest);
    }

    private void setNewClick() {
        rl_school.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        rl_profession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        rl_startYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void setClick() {
        rl_school.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent school = new Intent(getContext(), MyEducation.class);
                startActivity(school);
            }
        });
        rl_profession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profession = new Intent(getContext(), MyEducation.class);
                startActivity(profession);
            }
        });
        rl_startYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startyear = new Intent(getContext(), MyEducation.class);
                startActivity(startyear);
            }
        });
    }


   @Override
   public void onResume() {
       super.onResume();
       //注册本地广播
       localReceiver();
   }

    /**
     * 本地广播接收
     */
    private void localReceiver() {
        localBroadcastManager = LocalBroadcastManager.getInstance(getContext());//获取实例
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.myeducation.change");
        refreshReceiver = new RefreshEducationReceiver();
        localBroadcastManager.registerReceiver(refreshReceiver, intentFilter);
    }
    /**
     * 广播接收器
     */
    class RefreshEducationReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int personalStatus = intent.getExtras().getInt("myeducation");
            switch (personalStatus) {
                case 0://无更新,不需要操作
                    break;
                case 1:
                    loadData();
                    break;
            }
        }
    }
}
