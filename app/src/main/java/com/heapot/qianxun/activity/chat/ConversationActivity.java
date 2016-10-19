package com.heapot.qianxun.activity.chat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.heapot.qianxun.R;

/**
 * Created by Karl on 2016/10/19.
 * desc: 会话界面
 */

public class ConversationActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation);
    }
}
