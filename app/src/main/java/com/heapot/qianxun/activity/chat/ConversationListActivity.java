package com.heapot.qianxun.activity.chat;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.heapot.qianxun.R;

/**
 * Created by Karl on 2016/10/19.
 * desc: 会话列表
 *
 */

public class ConversationListActivity extends FragmentActivity{
    private TextView mTitle;
    private ImageView mBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversationlist);
        setActionBar();

    }
    /**
     * 设置ActionBar
     */
    private void setActionBar(){
        mBack = (ImageView) findViewById(R.id.iv_btn_back);
        mTitle = (TextView) findViewById(R.id.txt_title);
        mTitle.setText("会话列表");
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}
