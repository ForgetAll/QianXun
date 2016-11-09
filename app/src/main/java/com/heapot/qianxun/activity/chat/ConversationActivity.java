package com.heapot.qianxun.activity.chat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.heapot.qianxun.R;

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
}
