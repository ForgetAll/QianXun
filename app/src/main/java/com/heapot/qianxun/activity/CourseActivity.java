package com.heapot.qianxun.activity;

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

import com.blankj.utilcode.utils.NetworkUtils;
import com.heapot.qianxun.R;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.bean.MyUserBean;
import com.heapot.qianxun.util.SerializableUtils;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

/**
 * Created by Karl on 2016/11/6.
 * desc: 培训页面
 */

public class CourseActivity extends BaseActivity {

    private TextView mTitle;
    private ImageView mBack;

    private WebView webView;
    private WebSettings webSettings;

    private boolean isConnected = false;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 101){
                String id = msg.getData().getString("id");
                String title = msg.getData().getString("title");
                String image = msg.getData().getString("icon");

                Object object = SerializableUtils.getSerializable(CourseActivity.this, ConstantsBean.MY_USER_INFO);
                if (object != null){
                    MyUserBean.ContentBean myUserBean = (MyUserBean.ContentBean) object;
                    if (!myUserBean.getId().equals(id)){
                        RongIM.getInstance().refreshUserInfoCache(new UserInfo(id,title, Uri.parse(image)));
                        if (RongIM.getInstance() != null){
                            //用户开始聊天后默认加好友
//                            ChatInfoUtils.onRequestAddFriend(CourseActivity.this,id,title);
                            RongIM.getInstance().startPrivateChat(CourseActivity.this,id,title);
                        }
                    }

                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        initView();
        initEvent();

    }

    private void initView(){
        webView = (WebView) findViewById(R.id.wv_course);
        mTitle = (TextView) findViewById(R.id.txt_title);
        mBack = (ImageView) findViewById(R.id.iv_btn_back);
        mTitle.setText("课程详情");
    }

    private void initEvent(){
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.clearCache(true);
                webView.destroy();
                finish();
            }
        });
        Intent intent = getIntent();
        String id = intent.getExtras().getString("id");

      //  String url = "http://sijiache.heapot.com/Tabs/userPage/?type=train&id="+id+"&device=android";
        String url ="http://sijiache.heapot.com/Tabs/userPage/article/?articleId=" + id + "&device=android";

        //初始化webView
        initSettings();
        webView.loadUrl(url);
        webView.setWebChromeClient(new WebChromeClient(){});

    }


    private void initSettings() {
        webSettings = webView.getSettings();
        isConnected = NetworkUtils.isAvailable(this);
        if (isConnected) {
            webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        } else {
            webSettings.setCacheMode(webSettings.LOAD_CACHE_ONLY);

        }
        webSettings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(this, "android");
    }

    @JavascriptInterface
    public void toChat(String id,String title,String icon){
        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putString("id",id);
        bundle.putString("title",title);
        bundle.putString("icon",icon);
        message.setData(bundle);
        message.what = 101;
        handler.sendMessage(message);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.clearCache(true);
        webView.destroy();
    }
}
