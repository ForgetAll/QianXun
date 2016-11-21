package com.heapot.qianxun.activity.chat;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.heapot.qianxun.R;

import io.rong.imkit.RongIM;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.message.TextMessage;

/**
 * Created by Karl on 2016/10/19.
 * desc: 会话界面
 */

public class ConversationActivity extends FragmentActivity {
    private TextView mTitle;
    private ImageView mBack;
    private String mTargetId;
    private String mName;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation);
        setActionBar();
//        sendFirstMessage();
    }
    /**
     * 设置actionBar事件
     */
    private void setActionBar(){
        mTitle = (TextView) findViewById(R.id.txt_title);
        mBack = (ImageView) findViewById(R.id.iv_btn_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mName = getIntent().getData().getQueryParameter("title");
        mTargetId = getIntent().getData().getQueryParameter("targetId");
        setActionBarTitle(mName);
    }
    /**
     * 设置actionBar title
     */
    private void setActionBarTitle(String name){
        mTitle.setText(name);
    }

    private void sendFirstMessage(){
        TextMessage myTextMessage = TextMessage.obtain("hello，你好");
        io.rong.imlib.model.Message myMessage =
                io.rong.imlib.model.Message.obtain(mTargetId, Conversation.ConversationType.PRIVATE,myTextMessage);

        RongIM.getInstance().sendMessage(myMessage, null, null, new IRongCallback.ISendMessageCallback() {
            @Override
            public void onAttached(io.rong.imlib.model.Message message) {

            }

            @Override
            public void onSuccess(io.rong.imlib.model.Message message) {
                Toast.makeText(ConversationActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(io.rong.imlib.model.Message message, RongIMClient.ErrorCode errorCode) {

            }
        });

    }
}
