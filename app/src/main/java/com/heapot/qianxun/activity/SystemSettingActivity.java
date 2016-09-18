package com.heapot.qianxun.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.heapot.qianxun.R;

/**
 * Created by 15859 on 2016/9/17.
 */
public class SystemSettingActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mBack,mCache,mVersion,mSuggest,mExit;
    private RelativeLayout mClear,mUpdate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        findVew();
    }

    private void findVew() {
      mBack=(TextView)  findViewById(R.id.tv_back);
     mClear=(RelativeLayout)   findViewById(R.id.rl_clear);
       mCache=(TextView) findViewById(R.id.tv_cache);
        mUpdate=(RelativeLayout)   findViewById(R.id.rl_update);
        mVersion=(TextView)  findViewById(R.id.tv_version);
        mSuggest=(TextView)  findViewById(R.id.tv_suggest);
        mExit=(TextView)  findViewById(R.id.tv_exit);
        mBack.setOnClickListener(this);
        mClear.setOnClickListener(this);
        mUpdate.setOnClickListener(this);
        mSuggest.setOnClickListener(this);
        mExit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_back:
                finish();
                break;
            case R.id.rl_clear:
                break;
            case R.id.rl_update:
                break;
            case R.id.tv_suggest:
                break;
            case R.id.tv_exit:
                break;
        }
    }
}