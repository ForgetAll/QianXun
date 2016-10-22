package com.heapot.qianxun.helper.listener;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.heapot.qianxun.activity.ArticleActivity;
import com.heapot.qianxun.activity.MainActivity;
import com.heapot.qianxun.application.CustomApplication;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.bean.Friend;
import com.heapot.qianxun.bean.MyUserBean;
import com.heapot.qianxun.util.JsonUtil;
import com.heapot.qianxun.util.SerializableUtils;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.rong.imkit.RongIM;

/**
 * Created by Karl on 2016/10/21.
 * desc:获取用户信息
 *
 */

public class LoadUserInfo {
    static Context context;
    static OnResponseListener listener = null;

    public LoadUserInfo(Context context) {
        this.context = context;
    }

    public static void setOnResponseListener(OnResponseListener listener){
        LoadUserInfo.listener = listener;
    }

    public static void getUserInfoFromNet(String id){

    }
}
