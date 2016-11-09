package com.heapot.qianxun.util;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Karl on 2016/11/8.
 * desc: Volley工具类
 */

public class VolleyUtils {

    private Context context;

    public VolleyUtils(Context context) {
        this.context = context;
    }

    //自定义接口
    public interface VolleyCallBack {
        void setDataFromVolley(String json);
    }

    /**
     * Volley的get请求
     *
     * @param url
     * @param callBack
     */
    public void getRequestJson(String url, final VolleyCallBack callBack) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callBack.setDataFromVolley(volleyError.toString());
            }
        }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();

                return headers;
            }
        };
        requestQueue.add(stringRequest);
    }


    /**
     * Volley的post请求
     *
     * @param url
     * @param map
     * @param callBack
     */

    public void postRequestJson(String url, final HashMap<String, String> map, final VolleyCallBack callBack) {

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();

                return headers;
            }
        };
        requestQueue.add(stringRequest);
    }

}
