package com.heapot.qianxun.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.heapot.qianxun.R;

/**
 * Created by 15859 on 2016/9/17.
 */
public class SystemHelpActivity extends BaseActivity{
    private TextView mAbout,mHelp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        findView();
    }

    private void findView() {
       mAbout=(TextView) findViewById(R.id.tv_about);
       mHelp=(TextView) findViewById(R.id.tv_help);
    }
}
