package com.heapot.qianxun.util;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.heapot.qianxun.activity.ArticleActivity;
import com.heapot.qianxun.application.CustomApplication;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.bean.Friend;
import com.heapot.qianxun.bean.FriendRequestBean;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.rong.imkit.RongIM;

/**
 * Created by Karl on 2016/10/22.
 * desc: 好友信息请求，
 * 包括加好友、同意好友请求、好友请求列表.
 *
 */

public class ChatInfoUtils {

    /**
     * 请求加好友
     */
    public static void onRequestAddFriend(final Context context, final String userId, final String userName){
        //先转换请求体参数
        String json = "{\"userId\":\"" +userId+"\"}";
        final JSONObject requestBody = getBody(json);
        String url = ConstantsBean.BASE_PATH+ConstantsBean.IM_POST_ADD_FRIENDS_REQUEST;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, url, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("status").equals("success")){
                                Logger.d("向对方申请加好友信息发送成功");
                            }else {
                                Logger.d("加好友申请信息失败--->"+response.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Logger.d("加好友申请信息发送失败--->"+error);
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String>  headers  = new HashMap<>();
                headers.put(ConstantsBean.KEY_TOKEN, CustomApplication.TOKEN);
                return headers;
            }
        };
        CustomApplication.getRequestQueue().add(jsonObjectRequest);

    }

    /**
     * 查看收到的加好友请求列表
     */
    public static void getAddFriendsRequestList(final String token){
        String url = ConstantsBean.BASE_PATH+ConstantsBean.IM_GET_FRIENDS_REQUEST_LIST;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("status").equals("success")){
                                Logger.d("获取请求好友列表成功");
                                //请求成功，取出来id，然后循环添加
                                FriendRequestBean friendRequestBean = (FriendRequestBean) JsonUtil.fromJson(String.valueOf(response),FriendRequestBean.class);
                                List<FriendRequestBean.ContentBean> list = friendRequestBean.getContent();
                                if (list.size() > 0) {
                                    Logger.d("好友请求列表不为空");
                                    //遍历请求,这里不做错误处理，如果添加失败，因为排除了用户拒绝，所以只能是网络原因，下次重启再请求就好
                                    for (FriendRequestBean.ContentBean i : list) {
                                        admitAddFriendRequest(i.getUserId(),token);
                                    }
                                }else {
                                    Logger.d("好友请求列表为空，直接加载好友列表");
                                    //没有好友请求，直接加载好友列表
                                    getFriendsList(token);
                                }
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
                Map<String,String>  headers  = new HashMap<>();
                headers.put(ConstantsBean.KEY_TOKEN, CustomApplication.TOKEN);
                return headers;
            }
        };
        CustomApplication.getRequestQueue().add(jsonObjectRequest);
    }

    /**
     * 同意加好友
     * @param id 加好友请求id
     * @param token 拿到的token
     */
    public static void admitAddFriendRequest(String id, final String token){
        String url = ConstantsBean.BASE_PATH+ConstantsBean.IM_POST_ADMIT_ADD_FRIEND+id;
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.PUT, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("status").equals("success")){
                                Logger.d("同意加好友,开始加载好友列表");
                                getFriendsList(token);//获取好友列表
                            }else {
                                Logger.d("同意加好友失败-->"+response.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Logger.d("同意加好友发送失败"+error);
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String>  headers  = new HashMap<>();
                if (CustomApplication.TOKEN.equals("")) {
                    headers.put(ConstantsBean.KEY_TOKEN, CustomApplication.TOKEN);
                }else {
                    headers.put(ConstantsBean.KEY_TOKEN, token);
                }
                return headers;
            }
        };
        CustomApplication.getRequestQueue().add(jsonObjectRequest);
    }

    public static JSONObject getBody(String str){

        JSONObject json = null;
        try {
            json = new JSONObject(str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static void getFriendsList(String token){
        String url = ConstantsBean.BASE_PATH+ConstantsBean.IM_GET_FRIEND_LIST;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //获取好友列表成功
                            if (response.getString("status").equals("success")){
                                Logger.d("请求成功,加载好友列表");
                                Logger.json(String.valueOf(response));
                                //把数据存到本地
                                Friend friendList = (Friend) JsonUtil.fromJson(String.valueOf(response),Friend.class);
                                List<Friend.ContentBean> friends = friendList.getContent();
                                if (friends.size() > 0){
                                    SerializableUtils.setSerializable(CustomApplication.getContext(),ConstantsBean.IM_FRIEND,friends);
                                }else {
                                    //暂无好友,所以不需要处理

                                }
                            }else {
                                Logger.d("加载好友列表失败---->"+response.getString("message"));
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
                Map<String,String>  headers  = new HashMap<>();
                headers.put(ConstantsBean.KEY_TOKEN, CustomApplication.TOKEN);
                return headers;
            }
        };
        CustomApplication.getRequestQueue().add(jsonObjectRequest);
    }

}
