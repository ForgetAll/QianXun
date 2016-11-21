package com.heapot.qianxun.activity.detail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.heapot.qianxun.bean.CommitBean;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.bean.Resume;
import com.heapot.qianxun.util.JsonUtil;
import com.heapot.qianxun.util.PreferenceUtil;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.rong.imkit.RongIM;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;
import io.rong.message.TextMessage;

/**
 * Created by Karl on 2016/10/11.
 */

public class JobActivity extends BaseActivity {

    private TextView mTitle, mSave;

    private ImageView mBack;

    private WebView webView;

    private WebSettings webSettings;

    private String jobId = "";

    private String refId = "";

    private TextView quote_txt, sendMessage;

    private LinearLayout input_layout, quote_layout;

    private ImageView clearQuote;

    private EditText input_comment;

    private static final int MSG_SHOW_INPUT = 0;//显示输入框
    private static final int MSG_HIDE_INPUT = 1;//隐藏输入框
    private static final int MSG_UPDATE = 3;//更新引用
    private static final int MSG_COMMENT = 4;//添加评论
    private static final int MSG_TO_CHAT = 5;//跳转聊天


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SHOW_INPUT:
                    input_layout.setVisibility(View.VISIBLE);
                    break;
                case MSG_HIDE_INPUT:
                    if (quote_txt.getText().toString().equals("") && input_comment.getText().toString().equals("")) {
                        input_layout.setVisibility(View.GONE);
                        quote_layout.setVisibility(View.GONE);
                    }
                    break;
                case MSG_UPDATE:
                    if (input_layout.getVisibility() == View.GONE) {
                        input_layout.setVisibility(View.VISIBLE);
                    }
                    String data = msg.getData().getString("quote");
                    if (data != null) {
                        if (quote_layout.getVisibility() == View.GONE) {
                            quote_layout.setVisibility(View.VISIBLE);
                        }
                        String content = "";
                        String id = "";
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            JSONObject inputData = jsonObject.getJSONObject("inputData");
                            content = inputData.getString("refContent");
                            id = inputData.getString("refId");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        refId = id;
                        quote_txt.setText(content);
                    }
                    input_comment.setFocusable(true);
                    break;
                case MSG_TO_CHAT:
                    Logger.d("聊天页面");
                    String id = msg.getData().getString("id");
                    String title = msg.getData().getString("title");
                    String image = msg.getData().getString("icon");
                    String userId = PreferenceUtil.getString("id");
                    String response = msg.getData().getString("response");
                    if (!userId.equals(id)) {
                        RongIM.getInstance().refreshUserInfoCache(new UserInfo(id, title, Uri.parse(image)));
                        if (RongIM.getInstance() != null) {
                            //用户开始聊天后默认加好友
                            getResume(id, title, response);

                        }
                    }
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);
        initView();
        initEvent();
    }

    private void initView() {
        mTitle = (TextView) findViewById(R.id.txt_title);
        mSave = (TextView) findViewById(R.id.txt_btn_function);
        mBack = (ImageView) findViewById(R.id.iv_btn_back);

        webView = (WebView) findViewById(R.id.wv_job);

        quote_layout = (LinearLayout) findViewById(R.id.ll_quote);
        input_layout = (LinearLayout) findViewById(R.id.ll_input);

        quote_txt = (TextView) findViewById(R.id.txt_articles_quote);
        clearQuote = (ImageView) findViewById(R.id.iv_clear_quote);

        input_comment = (EditText) findViewById(R.id.edt_content_input);
        sendMessage = (TextView) findViewById(R.id.txt_send_mess);


    }

    private void initEvent() {

        Intent intent = getIntent();

        String id = intent.getExtras().getString("id");

        jobId = id;

        String url = "http://sijiache.heapot.com/Tabs/userPage/?type=job&id=" + id + "&device=android";

        mTitle.setText("招聘详情");

        mSave.setVisibility(View.GONE);

        input_layout.setVisibility(View.GONE);

        quote_layout.setVisibility(View.GONE);

        input_comment.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);


        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String content = input_comment.getText().toString();
                if (content != null && !content.equals("")) {

                    Toast.makeText(JobActivity.this, "评论发送中", Toast.LENGTH_SHORT).show();

                    postComment(content, jobId);

                } else {

                    Toast.makeText(JobActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();

                }

            }
        });

        clearQuote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                quote_txt.setText("");

                refId = "";

                quote_layout.setVisibility(View.GONE);
            }
        });

        initWeb();

        webView.loadUrl(url);
        webView.setWebChromeClient(new WebChromeClient() {
        });

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.clearCache(true);
                finish();
            }
        });
    }

    private void initWeb() {

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
    public void toChat(String id, String title, String icon, String response) {

        Message message = new Message();

        Bundle bundle = new Bundle();

        bundle.putString("id", id);

        bundle.putString("title", title);

        bundle.putString("icon", icon);

        bundle.putString("response", response);

        message.setData(bundle);

        message.what = MSG_TO_CHAT;

        handler.sendMessage(message);

    }

    /**
     * 输入框显示与隐藏
     *
     * @param str 传入参数
     */
    @JavascriptInterface
    public void showInput(String str) {
        handler.sendEmptyMessage(MSG_SHOW_INPUT);

    }

    @JavascriptInterface
    public void hideInput() {

        handler.sendEmptyMessage(MSG_HIDE_INPUT);

    }

    /**
     * 更新引用
     *
     * @param data 引用评论id
     */
    @JavascriptInterface
    public void updateQuote(String data) {
        Logger.json(data);

        Message message = new Message();

        message.what = MSG_UPDATE;

        Bundle bundle = new Bundle();

        bundle.putString("quote", data);

        message.setData(bundle);

        handler.sendMessage(message);
    }

    /**
     * 清空输入框
     */
    private void clearInput(boolean isClear) {
        if (isClear) {
            handler.sendEmptyMessage(MSG_COMMENT);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        webView.clearCache(true);

    }

    private String parseResponse(String response, String resume) {
        String forChat = "";
        try {
            JSONObject str = new JSONObject(response);
            if (str.getInt("whatFor") == 1) {
                forChat = str.getString("fromWhere");
                if (!resume.equals("")) {
                    forChat = forChat + ",以下是我的简历内容：" + resume;
                }

                return forChat;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return forChat;

    }

    private void sendFirstMessage(String mTargetId, String msg) {

        TextMessage myTextMessage = TextMessage.obtain(msg);

        io.rong.imlib.model.Message myMessage =
                io.rong.imlib.model.Message.obtain(mTargetId, Conversation.ConversationType.PRIVATE, myTextMessage);

        RongIM.getInstance().sendMessage(myMessage, null, null, new IRongCallback.ISendMessageCallback() {
            @Override
            public void onAttached(io.rong.imlib.model.Message message) {
                Logger.d("存储成功" + message);
            }

            @Override
            public void onSuccess(io.rong.imlib.model.Message message) {
                Logger.d("发送成功" + message);
            }

            @Override
            public void onError(io.rong.imlib.model.Message message, RongIMClient.ErrorCode errorCode) {
                Logger.d("发送失败" + message);
            }
        });

    }

    private void getResume(final String id, final String title, final String desc) {
        String url = ConstantsBean.BASE_PATH + ConstantsBean.RESUME_INFO;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        RongIM.getInstance().startPrivateChat(JobActivity.this, id, title);
                        try {
                            String status = response.getString("status");
                            if (status.equals("success")) {
                                Resume resume = (Resume) JsonUtil.fromJson(String.valueOf(response), Resume.class);
                                Logger.d(resume.getContent().getTotal());
                                int total = resume.getContent().getTotal();
                                if (total > 0) {
                                    Logger.d(resume.getContent().getRows().get(0).getResume());
                                    String forChat = parseResponse(desc, resume.getContent().getRows().get(0).getResume());
                                    sendFirstMessage(id, forChat);
                                } else {
                                    String forChat = parseResponse(desc, "");
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
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = PreferenceUtil.getString("token");
                Map<String, String> headers = new HashMap<>();
                headers.put("x-auth-token", token);

                return headers;

            }
        };

        CustomApplication.getRequestQueue().add(jsonObjectRequest);
    }

    private void postComment(String content, String article) {
        String url = ConstantsBean.BASE_PATH + ConstantsBean.ADD_COMMENT;
        String body;
        if (refId.equals("")) {
            body = "{\"articleId\":\"" + article + "\",\"content\":\"" + content + "\"}";
        } else {
            body = "{\"refId\":\"" + refId + "\",\"articleId\":\"" + article + "\",\"content\":\"" + content + "\"}";
        }
        JSONObject json = null;
        try {
            json = new JSONObject(body);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Logger.json(String.valueOf(json));
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, url, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Logger.json(String.valueOf(response));
                        try {
                            String status = response.getString("status");
                            if (status.equals("success")) {
                                Toast.makeText(JobActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                                CommitBean commitBean = (CommitBean) JsonUtil.fromJson(String.valueOf(response),CommitBean.class);
                                String commitId = commitBean.getContent().getId();
                                //发送成功
                                input_comment.setText("");//清空数据

                                quote_txt.setText("");

                                refId = "";

                                quote_layout.setVisibility(View.GONE);

                                //刷新评论
                                webView.loadUrl("javascript:fillComment(\""+commitId+"\")");

                            } else {
                                Toast.makeText(JobActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(JobActivity.this, "发送失败", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put(ConstantsBean.KEY_TOKEN, getAppToken());
                return map;
            }
        };
        CustomApplication.getRequestQueue().add(jsonObjectRequest);

    }


}
