package com.heapot.qianxun.util.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.heapot.qianxun.application.CustomApplication;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.bean.MainListBean;
import com.heapot.qianxun.util.JsonUtil;
import com.heapot.qianxun.util.PreferenceUtil;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karl on 2016/11/11.
 * desc: 主页列表数据请求
 */

public class LoadTagsList {

    private Context context;

    private onLoadTagsListListener listListener;

    private List<MainListBean.ContentBean> list = new ArrayList<>();

    public void setOnLoadTagsListListener(onLoadTagsListListener listListener){
        this.listListener = listListener;
    }

    public LoadTagsList(Context context) {
        this.context = context;
    }

    public void getTagsList(String url,int flag){
        //判断是否超过30分钟
        Long time = PreferenceUtil.getLong("time");
        Long currentTime = System.currentTimeMillis();
        if ((currentTime -time)/(1000*60) >= 30){
            postLogin(flag,url);
        }else {
            getList(flag,url);
        }
    }
    private void postLogin(final int flag, final String listUrl) {
        final String username = PreferenceUtil.getString("phone");
        final String password = PreferenceUtil.getString("password");
        String url = ConstantsBean.BASE_PATH + ConstantsBean.LOGIN + "?loginName=" + username + "&password=" + password;
        JsonObjectRequest jsonObject = new JsonObjectRequest(
                Request.Method.POST, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        parseResponse(response,flag,listUrl);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Logger.d(error);

                    }
                }
        );
        CustomApplication.getRequestQueue().add(jsonObject);
    }

    private void parseResponse(JSONObject response,int flag,String url){
        try {
            if (response.getString("status").equals("success")) {
                if (response.has("content")) {
                    JSONObject content = response.getJSONObject("content");
                    String token = content.getString("auth-token");
                    //存储到本地
                    PreferenceUtil.putString("token", token);
                    long time = System.currentTimeMillis();
                    PreferenceUtil.putLong("time",time);
                    getList(flag,url);
                }
            } else {
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void getList(final int flag, String url){

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseListResponse(response,flag);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listListener.onError();
                    }
                }
        );

        CustomApplication.getRequestQueue().add(jsonObjectRequest);

    }

    private void parseListResponse(JSONObject response,int flag){
        list.clear();
        try {
            int index = response.getInt("total_page");
            String result = response.getString("return_code");
            if (result.equals("success")){
                MainListBean mainListBean = (MainListBean) JsonUtil.fromJson(String.valueOf(response),MainListBean.class);
                if (mainListBean != null){
                    list.addAll(mainListBean.getContent());
                }
                listListener.onSuccessResponse(list,index,flag);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public interface onLoadTagsListListener{

        void onSuccessResponse(List<MainListBean.ContentBean> list,int totalIndex,int flag);

        void onErrorResponse();

        void onError();

    }
}
