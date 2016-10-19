package com.heapot.qianxun.util;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.heapot.qianxun.application.CustomApplication;
import com.heapot.qianxun.bean.ConstantsBean;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

/**
 * Created by Karl on 2016/10/19.
 * 融云连接的方法
 *
 */

public class IMConnUtil {
    Context context;

    public IMConnUtil(Context context) {
        this.context = context;
    }
    public void connIM(boolean isFirst){
        if (!isFirst){
            CustomApplication.isFirstConnIM = true;
            getRongToken();
        }else {
            //已经连接，不需要再次连接
        }
    }

    /**
     * 获取当前用户在融云的token
     * @return 返回获取到的token
     */
    private String getRongToken(){
        final String token = "";
        String url = ConstantsBean.BASE_PATH+ConstantsBean.IM_TOKEN;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("status").equals("success")){
                                //获取成功，取出来token
                                String im_token = response.getString("content");
                                conn(im_token);
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
                headers.put(ConstantsBean.KEY_TOKEN, CustomApplication.TOKEN);
                return headers;
            }
        };
        CustomApplication.getRequestQueue().add(jsonObjectRequest);
        return token;
    }

    /**
     * 建立与融云服务器的连接
     * @param token
     */
    private void conn(String token){
        if (context.getApplicationInfo().packageName.equals(CustomApplication.getCurProcessName(context.getApplicationContext()))){
            /**
             * IMKit SDK调用第二步，建立与服务器的连接
             */
            RongIM.connect(token, new RongIMClient.ConnectCallback() {
                @Override
                public void onTokenIncorrect() {
                    //Token错误，重新请求token
                    getRongToken();
                }

                @Override
                public void onSuccess(String s) {
                    //连接融云成功
                    Logger.d("IM-Success-------->UserId:"+s);
//                    context.startActivity(new Intent(context, ConversationListActivity.class));

                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    Logger.d("连接失败，错误码--------->"+errorCode);
                }
            });
        }
    }

}
