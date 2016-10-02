package com.heapot.qianxun.util;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.heapot.qianxun.activity.MainActivity;
import com.heapot.qianxun.application.ActivityCollector;
import com.heapot.qianxun.application.CustomApplication;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.bean.SubscribedBean;
import com.heapot.qianxun.bean.TagsBean;
import com.heapot.qianxun.helper.SerializableUtils;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Karl on 2016/10/2.
 * 用于统一加载标签
 *
 */
public class LoadTagsUtils {
    private static List<SubscribedBean.ContentBean.RowsBean> subList = new ArrayList<>();
    private List<TagsBean.ContentBean> tagsList = new ArrayList<>();
    /**
     * 加载全部标签并存储到本地，加载成功以后请求加载已订阅标签
     * @param token token
     */
    private void getTags(final Context context, final String token){
        String url = ConstantsBean.BASE_PATH + ConstantsBean.ORG_CODE+ConstantsBean.CATALOGS;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Logger.json(String.valueOf(response));
                        try {
                            String status = response.getString("status");
                            if (status.equals("success")) {
                                TagsBean jsonBean = (TagsBean) JsonUtil.fromJson(String.valueOf(response), TagsBean.class);
                                tagsList.addAll(jsonBean.getContent());
                                SerializableUtils.setSerializable(context, ConstantsBean.TAG_FILE_NAME, tagsList);
                                getSubTags(context,token);
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
                Map<String,String> headers = new HashMap<>();
                headers.put(ConstantsBean.KEY_TOKEN,token);
                return headers;
            }
        };
        CustomApplication.getRequestQueue().add(jsonObjectRequest);
    }
    /**
     * 加载已订阅标签，存入本地
     * @param token token
     */
    public static void getSubTags(final Context context, final String token){
        String url = ConstantsBean.BASE_PATH+ConstantsBean.GET_SUBSCRIBED;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("status");
                            if (status.equals("success")){
                                //获取列表成功，加载列表
                                SubscribedBean subBean = (SubscribedBean) JsonUtil.fromJson(String.valueOf(response),SubscribedBean.class);
                                subList.addAll(subBean.getContent().getRows());
                                SerializableUtils.setSerializable(context,ConstantsBean.SUB_FILE_NAME,subList);
                                Intent intent = new Intent(context,MainActivity.class);
                                context.startActivity(intent);
                                ActivityCollector.finishAll();

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
                Map<String, String> map = new HashMap<>();
                map.put(ConstantsBean.KEY_TOKEN,token);
                return map;
            }
        };
        CustomApplication.getRequestQueue().add(jsonObjectRequest);
    }


}
