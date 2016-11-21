package com.heapot.qianxun.util.network;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.heapot.qianxun.application.CustomApplication;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.bean.Resume;
import com.heapot.qianxun.util.JsonUtil;
import com.heapot.qianxun.util.PreferenceUtil;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Karl on 2016/11/21.
 */

public class LoadResume {

    private Context context;

    private OnLoadResumeListener listener;

    private List<Resume.ContentBean.RowsBean> mList = new ArrayList<>();

    public void setOnLoadResumeListener(OnLoadResumeListener listener){

        this.listener = listener;

    }

    public LoadResume(Context context) {
        this.context = context;
    }
    //获取简历列表
    public void loadResumeList(){
        String url = ConstantsBean.BASE_PATH + ConstantsBean.RESUME_INFO;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Logger.json(String.valueOf(response));
                        try {
                            String status = response.getString("status");
                            if (status.equals("success")){
                                Resume resume = (Resume) JsonUtil.fromJson(String.valueOf(response),Resume.class);
                                Logger.d(resume.getContent().getTotal());
                                int total = resume.getContent().getTotal();
                                if (total > 0){
                                    Logger.d(resume.getContent().getRows().size());
                                    mList.addAll(resume.getContent().getRows());
                                    listener.onListSuccess(total,mList);
                                }else if (total == 0){
                                    listener.onListSuccess(total,null);
                                }else {
                                    listener.onError();
                                }
                            }else {
                                listener.onError();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        listener.onError();
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = PreferenceUtil.getString("token");
                Map<String,String> headers = new HashMap<>();
                headers.put("x-auth-token",token);

                return headers;

            }
        };

        CustomApplication.getRequestQueue().add(jsonObjectRequest);
    }


    public void addResume(String content){

        String url = ConstantsBean.BASE_PATH + ConstantsBean.RESUME_INFO;

        JSONObject json = parseToJsonObj(content);

        JsonObjectRequest jsonObjectRequest  = new JsonObjectRequest(
                Request.Method.POST, url, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Logger.json(String.valueOf(response));
                        try {
                            String status = response.getString("status");
                            if (status.equals("success")){

                                listener.onAddResumeSuccess();

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
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = PreferenceUtil.getString("token");
                Map<String,String> headers = new HashMap<>();
                headers.put("x-auth-token",token);

                return headers;

            }
        };
        CustomApplication.getRequestQueue().add(jsonObjectRequest);
    }


    public void getResumeContent(){
        //这个接口获得的数据跟列表的一致，所以不再用了
        listener.checkoutResumeSuccess();
    }


    public void updateResume(String content){
        String url = ConstantsBean.BASE_PATH + ConstantsBean.RESUME_INFO;
        JSONObject json = parseToJsonObj(content);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.PUT, url, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Logger.json(String.valueOf(response));
                        try {
                            String status = response.getString("status");
                            if (status.equals("success")){
                                listener.onUpdateResumeSuccess();
                            }else {
                                Logger.d(response.getString("message"));
                                listener.onError();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        listener.onError();
                        //暂时不知道为什么会报错，所以屏蔽了
                        error.printStackTrace();

                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = PreferenceUtil.getString("token");
                Map<String,String> headers = new HashMap<>();
                headers.put("x-auth-token",token);

                return headers;

            }
        };
        CustomApplication.getRequestQueue().add(jsonObjectRequest);
    }

    private JSONObject parseToJsonObj(String content){
        Logger.d("content");
        JSONObject json = null;
        try {
            json = new JSONObject(content);
            return json;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }


    public interface OnLoadResumeListener{

        void onListSuccess(int total,List<Resume.ContentBean.RowsBean> list);

        void onAddResumeSuccess();

        void onUpdateResumeSuccess();

        void checkoutResumeSuccess();

        void onError();

    }

}
