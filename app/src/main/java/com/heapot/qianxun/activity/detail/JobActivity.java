package com.heapot.qianxun.activity.detail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.blankj.utilcode.utils.NetworkUtils;
import com.heapot.qianxun.R;
import com.heapot.qianxun.activity.BaseActivity;
import com.heapot.qianxun.application.CustomApplication;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.bean.Resume;
import com.heapot.qianxun.util.JsonUtil;
import com.heapot.qianxun.util.PreferenceUtil;
import com.heapot.qianxun.util.network.LoadResume;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.rong.imkit.RongIM;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;
import io.rong.message.TextMessage;

/**
 * Created by Karl on 2016/10/11.
 *
 */

public class JobActivity extends BaseActivity{
    private TextView mToolBarTitle,mSave;
    private ImageView mBack;
    private WebView webView;
    private WebSettings webSettings;


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 101){
                String id = msg.getData().getString("id");
                String title = msg.getData().getString("title");
                String image = msg.getData().getString("icon");
                String userId = PreferenceUtil.getString("id");
                String response = msg.getData().getString("response");
//                Logger.json(response);

//                Logger.d(forChat);
                if (!userId.equals(id)){
                    RongIM.getInstance().refreshUserInfoCache(new UserInfo(id,title, Uri.parse(image)));
                    if (RongIM.getInstance() != null){
//                        RongIM.getInstance().startPrivateChat(JobActivity.this, id, title);
                        //用户开始聊天后默认加好友
                        getResume(id,title,response);

                    }
                }


            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);
        initView();
    }
    private void initView(){
        mToolBarTitle = (TextView) findViewById(R.id.txt_title);
        mToolBarTitle.setText("招聘详情");
        mSave = (TextView) findViewById(R.id.txt_btn_function);
        mSave.setVisibility(View.GONE);
        mBack = (ImageView) findViewById(R.id.iv_btn_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.clearCache(true);
                webView.destroy();
                finish();
            }
        });

        webView  = (WebView) findViewById(R.id.wv_job);
        Intent intent = getIntent();
        String id = intent.getExtras().getString("id");
        initWeb();
        String url = "http://sijiache.heapot.com/Tabs/userPage/?type=job&id="+id+"&device=android";
        webView.loadUrl(url);
        webView.setWebChromeClient(new WebChromeClient() {
        });
    }
    private void initWeb(){

        webSettings = webView.getSettings();
        boolean isConnected = NetworkUtils.isAvailable(this);
        if (isConnected) {
            webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        } else {
            webSettings.setCacheMode(webSettings.LOAD_CACHE_ONLY);

        }
        webSettings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(this, "android");
    }

    @JavascriptInterface
    public void toChat(String id,String title,String icon,String response){
        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putString("id",id);
        bundle.putString("title",title);
        bundle.putString("icon",icon);
        bundle.putString("response",response);
        message.setData(bundle);
        message.what = 101;
        handler.sendMessage(message);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.clearCache(true);
//        webView.destroy();

    }

    private String parseResponse(String response,String resume){
        String forChat = "";
        try {
            JSONObject str = new JSONObject(response);
            if (str.getInt("whatFor") == 1){
                forChat = str.getString("fromWhere");
                if (!resume.equals("")){
                    forChat = forChat + ",以下是我的简历内容："+resume;
                }

                return forChat;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return forChat;

    }

    private void sendFirstMessage(String mTargetId,String msg){
        TextMessage myTextMessage = TextMessage.obtain(msg);
        io.rong.imlib.model.Message myMessage =
                io.rong.imlib.model.Message.obtain(mTargetId, Conversation.ConversationType.PRIVATE,myTextMessage);

        RongIM.getInstance().sendMessage(myMessage, null, null, new IRongCallback.ISendMessageCallback() {
            @Override
            public void onAttached(io.rong.imlib.model.Message message) {
                Logger.d("存储成功"+message);
            }

            @Override
            public void onSuccess(io.rong.imlib.model.Message message) {
                Logger.d("发送成功"+message);
            }

            @Override
            public void onError(io.rong.imlib.model.Message message, RongIMClient.ErrorCode errorCode) {
                Logger.d("发送失败"+message);
            }
        });

    }

    private void getResume(final String id,final String title,final String desc){
        String url = ConstantsBean.BASE_PATH + ConstantsBean.RESUME_INFO;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        RongIM.getInstance().startPrivateChat(JobActivity.this, id, title);
                        try {
                            String status = response.getString("status");
                            if (status.equals("success")){
                                Resume resume = (Resume) JsonUtil.fromJson(String.valueOf(response),Resume.class);
                                Logger.d(resume.getContent().getTotal());
                                int total = resume.getContent().getTotal();
                                if (total > 0){
                                    Logger.d(resume.getContent().getRows().get(0).getResume());
                                    String forChat = parseResponse(desc,resume.getContent().getRows().get(0).getResume());
                                    sendFirstMessage(id, forChat);
                                }else {
                                    String forChat = parseResponse(desc,"");
                                    sendFirstMessage(id, forChat);
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
                String token = PreferenceUtil.getString("token");
                Map<String,String> headers = new HashMap<>();
                headers.put("x-auth-token",token);

                return headers;

            }
        };

        CustomApplication.getRequestQueue().add(jsonObjectRequest);
    }

}
