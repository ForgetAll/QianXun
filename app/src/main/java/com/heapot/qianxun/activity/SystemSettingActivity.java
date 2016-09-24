package com.heapot.qianxun.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.heapot.qianxun.R;
import com.heapot.qianxun.util.ClearCacheTask;
import com.heapot.qianxun.util.UpdateUtil;
import com.heapot.qianxun.widget.ExitPopup;

/**
 * Created by 15859 on 2016/9/17.
 */
public class SystemSettingActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mBack, mCache, mVersion, mSuggest, mExit;
    private RelativeLayout mClear, mUpdate;
    private Activity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        findVew();
    }

    private void findVew() {
        mBack = (TextView) findViewById(R.id.tv_back);
        mClear = (RelativeLayout) findViewById(R.id.rl_clear);
        mCache = (TextView) findViewById(R.id.tv_cache);
        mUpdate = (RelativeLayout) findViewById(R.id.rl_update);
        mVersion = (TextView) findViewById(R.id.tv_version);
        mSuggest = (TextView) findViewById(R.id.tv_suggest);
        mExit = (TextView) findViewById(R.id.tv_exit);
        mBack.setOnClickListener(this);
        mClear.setOnClickListener(this);
        mUpdate.setOnClickListener(this);
        mSuggest.setOnClickListener(this);
        mExit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回
            case R.id.tv_back:
                finish();
                break;
            //清理缓存
            case R.id.rl_clear:
                ClearCacheTask clearCacheTask = new ClearCacheTask(activity, mCache);
                clearCacheTask.execute();
                break;
            //版本更新
            case R.id.rl_update:
                UpdateUtil.getInstance().checkUpdate(activity,mVersion,true);
                break;
            //提交意见
            case R.id.tv_suggest:
                break;
            //退出登录
            case R.id.tv_exit:
                ExitPopup exitPopup=new ExitPopup(activity);
                exitPopup.showPopupWindow();
                break;
        }
    }
}
