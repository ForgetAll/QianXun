package com.heapot.qianxun.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.heapot.qianxun.R;

/**
 * Created by 15859 on 2016/9/27.
 * 用户帮助类
 */
public class SystemUserHelpActivity  extends BaseActivity implements View.OnClickListener {
    private TextView tv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_about_use);
        findView();
    }

    private void findView() {
        tv_back=(TextView)    findViewById(R.id.tv_back);
        tv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_back:
                finish();
                break;
        }
    }
}
