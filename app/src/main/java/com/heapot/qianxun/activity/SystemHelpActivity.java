package com.heapot.qianxun.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.heapot.qianxun.R;
import com.heapot.qianxun.util.ActivityUtil;

/**
 * Created by 15859 on 2016/9/17.
 * 系统帮助类
 */
public class SystemHelpActivity extends BaseActivity implements View.OnClickListener {
    private TextView mAbout,mHelp;
    private TextView tv_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        findView();
    }

    private void findView() {
        tv_back=(TextView) findViewById(R.id.tv_back);
       mAbout=(TextView) findViewById(R.id.tv_about);
       mHelp=(TextView) findViewById(R.id.tv_help);
        mAbout.setOnClickListener(this);
        mHelp.setOnClickListener(this);
        tv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_about:
                ActivityUtil.jumpActivity(this,SystemAboutUs.class);
                break;
            case R.id.tv_help:
                ActivityUtil.jumpActivity(this,SystemUserHelpActivity.class);
                break;
        }
    }
}
