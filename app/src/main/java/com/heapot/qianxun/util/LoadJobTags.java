package com.heapot.qianxun.util;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.heapot.qianxun.application.CustomApplication;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.bean.MyTagBean;
import com.heapot.qianxun.bean.TagsBean;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Karl on 2016/11/10.
 * desc:
 */

public class LoadJobTags {
    private Context context;
    static OnLoadJobListener listener;

    public LoadJobTags(Context context) {
        this.context =context;

    }

    public void setOnLoadTagListener(OnLoadJobListener listener){
        LoadJobTags.listener = listener;
    }

    /**
     * 加载全部标签并存储到本地，加载成功以后请求加载已订阅标签
     *
     */
    public  void getTags( final int flag){

        String url = ConstantsBean.BASE_PATH + ConstantsBean.CATALOGS;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        Logger.json(String.valueOf(response));
                        List<TagsBean.ContentBean> tagsList = new ArrayList<>();
                        try {
                            String status = response.getString("status");
                            if (status.equals("success")) {
                                TagsBean tagsBean = (TagsBean) JsonUtil.fromJson(String.valueOf(response),TagsBean.class);
                                SerializableUtils.setSerializable(context,ConstantsBean.TAG_FILE_NAME,tagsBean.getContent());
                                if (tagsBean.getContent()!= null) {
                                    tagsList.addAll(tagsBean.getContent());
                                    listener.onLoadAllSuccess(tagsList, flag);
                                }
//                                getUserTag(token,flag);
                            }else {
                                listener.onLoadFailed(response.getString("message"));
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
                String token = PreferenceUtil.getString("token");
                Logger.d("token"+token);
                headers.put(ConstantsBean.KEY_TOKEN,token);
                return headers;
            }
        };
        CustomApplication.getRequestQueue().add(jsonObjectRequest);
    }
    //获取已订阅标签
    public void getUserTag(final String token, final int flag){
        String url = ConstantsBean.BASE_PATH+ConstantsBean.SUBSCRIBE_CATALOGS;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Logger.json(String.valueOf(response));
                        List<MyTagBean.ContentBean.RowsBean> list = new ArrayList<>();
                        try {
                            String status = response.getString("status");
                            if (status.equals("success")) {
                                MyTagBean myTagBean = (MyTagBean) JsonUtil.fromJson(String.valueOf(response),MyTagBean.class);
                                if (myTagBean.getContent().getRows() != null) {
                                    list.addAll(myTagBean.getContent().getRows());
                                    Logger.d(list.size());
                                    listener.onLoadSuccess(list,flag);
                                }
                            }else {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Logger.d("加载失败");
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





    public interface OnLoadJobListener{

        void onLoadAllSuccess(List<TagsBean.ContentBean> list,int flag);

        void onLoadSuccess(List<MyTagBean.ContentBean.RowsBean> list,int flag);

        void onLoadFailed(String message);
    }
}
