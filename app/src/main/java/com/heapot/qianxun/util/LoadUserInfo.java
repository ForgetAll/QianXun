package com.heapot.qianxun.util;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.heapot.qianxun.application.CustomApplication;
import com.heapot.qianxun.bean.UserInfo;
import com.heapot.qianxun.bean.ChatUserInfoBean;
import com.heapot.qianxun.bean.ConstantsBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Karl on 2016/11/10.
 * desc: 下载用户信息
 */

public class LoadUserInfo {

    private Context context;

    private OnUserInfoListener listener;

    public LoadUserInfo(Context context, OnUserInfoListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public interface OnUserInfoListener{

        void onResponseSuccess(String id,String nickname,String icon);

        void onResponseError(String id);

    }

    public void getChatUserInfo(final String id, final String token){
        String url = ConstantsBean.BASE_PATH+ConstantsBean.IM_USER_INFO+id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String status = response.getString("status");
                            if (status.equals("success")){
                                ChatUserInfoBean userInfo =
                                        (ChatUserInfoBean) JsonUtil.fromJson(String.valueOf(response),ChatUserInfoBean.class);

                                String userId = userInfo.getContent().getId();
                                String nickname = userInfo.getContent().getNickname();
                                String icon = userInfo.getContent().getIcon();
                                listener.onResponseSuccess(userId,nickname,icon);

                            }else {
                                listener.onResponseError(id);
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
