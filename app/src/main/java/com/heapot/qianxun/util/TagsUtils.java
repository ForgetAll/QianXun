package com.heapot.qianxun.util;

import android.content.Context;
import android.media.session.MediaSession;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.heapot.qianxun.activity.Subscription;
import com.heapot.qianxun.application.CustomApplication;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.bean.SubBean;
import com.heapot.qianxun.helper.OnRecyclerViewItemClickListener;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Karl on 2016/10/4.
 * desc: 提交、取消
 */
public class TagsUtils {

    private Context context;

    onPostResponseListener listener;

    public TagsUtils(Context context, onPostResponseListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public interface onPostResponseListener{

        void onSubResponse(String id,int pos,int flag);

        void onCancelResponse(String id,int pos,int flag);

        void onPostError();
    }

    public void postSub(final String id, final int pos, final int flag){
        String url = ConstantsBean.BASE_PATH+ConstantsBean.POST_SUBSCRIPTION+id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("status");
                            if (status.equals("success")){
                                listener.onSubResponse(id,pos,flag);
                            }else {
                                listener.onPostError();
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
                String token = PreferenceUtil.getString("token");
                map.put(ConstantsBean.KEY_TOKEN, token);
                return map;
            }
        };
        CustomApplication.getRequestQueue().add(jsonObjectRequest);
    }
    /**
     * 取消订阅
     * @param id 所需要取消订阅标签的id
     */
    public void deleteSub(final String id, final int pos, final int flag){
        Logger.d("取消标签");
        String url = ConstantsBean.BASE_PATH+ConstantsBean.CANCEL_SUBSCRIPTION+id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.DELETE, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("status");
                            if (status.equals("success")){
                                Logger.d("取消成功");
                                listener.onCancelResponse(id,pos,flag);
                            }else {
                                Logger.d("取消失败");
                                listener.onPostError();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onPostError();
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<>();
                String token = PreferenceUtil.getString("token");
                headers.put(ConstantsBean.KEY_TOKEN,token);
                return headers;
            }
        };
        CustomApplication.getRequestQueue().add(jsonObjectRequest);
    }

}
