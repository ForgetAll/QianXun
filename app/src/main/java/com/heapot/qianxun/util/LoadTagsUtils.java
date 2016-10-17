package com.heapot.qianxun.util;

import android.content.Context;
import android.content.Intent;

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
    static List<SubscribedBean.ContentBean.RowsBean> subList = new ArrayList<>();
    static List<TagsBean.ContentBean> tagsList = new ArrayList<>();
    /**
     * 加载全部标签并存储到本地，加载成功以后请求加载已订阅标签
     * @param token token
     */
    public static void getTags(final Context context, final String token){

        String url = ConstantsBean.BASE_PATH + ConstantsBean.CATALOGS;
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
                                List<Integer> posList = new ArrayList<>();
                                //获取三个主页的id
                                for (int i = 0; i < tagsList.size(); i++) {
                                    if (tagsList.get(i).getPid() == null){
                                        posList.add(i);
                                    }
                                }
                                for (int i = 0; i < posList.size(); i++) {
                                    if (tagsList.get(i).getCode().equals("articles")){
                                        CustomApplication.PAGE_ARTICLES_ID = tagsList.get(posList.get(i)).getId();
                                    }else if (tagsList.get(i).equals("jobs")){
                                        CustomApplication.PAGE_JOBS_ID =  tagsList.get(posList.get(i)).getId();
                                    }else if (tagsList.get(i).getCode().equals("activities")){
                                        CustomApplication.PAGE_ACTIVITIES_ID = tagsList.get(posList.get(i)).getId();
                                    }
                                }
                                Logger.d("所有数据List："+tagsList.size()+"，获取到的主页id下标集合POSList："+posList.size());
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
                Map<String,String> headers = new HashMap<>();
                headers.put(ConstantsBean.KEY_TOKEN,token);
                return headers;
            }
        };
        CustomApplication.getRequestQueue().add(jsonObjectRequest);
    }

}
