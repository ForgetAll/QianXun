package com.heapot.qianxun.activity.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.heapot.qianxun.R;
import com.orhanobut.logger.Logger;

/**
 * Created by Karl on 2016/10/19.
 * desc: 会话界面
 */

public class ConversationActivity extends FragmentActivity {
    private TextView mTitle;
    private ImageView mBack;
    private String mTargetId;
    private String mName;
    private Intent intent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation);
//        intent = getIntent();
//        getIntentData(intent);
//        setActionBar();
    }
    /**
     * 从Intent中拿到融云传递的Uri
     */
    private void getIntentData(Intent intent){

        if (intent.getData().getQueryParameter("title") == null){
            setActionBarTitle("未知联系人");
        }else {
            setActionBarTitle("联系人头像"+intent.getData().getQueryParameter("title"));
        }
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
    }
    /**
     * 设置actionBar title
     */
    private void setActionBarTitle(String name){
        mTitle.setText(name);
    }
}
