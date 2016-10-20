package com.heapot.qianxun.activity.system;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.heapot.qianxun.R;
import com.heapot.qianxun.activity.BaseActivity;
import com.heapot.qianxun.activity.LoginActivity;
import com.heapot.qianxun.application.ActivityCollector;
import com.heapot.qianxun.application.CustomApplication;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.util.ClearCacheTask;
import com.heapot.qianxun.util.FileSizeUtil;
import com.heapot.qianxun.util.PackageUtils;
import com.heapot.qianxun.util.PreferenceUtil;
import com.heapot.qianxun.util.SerializableUtils;
import com.heapot.qianxun.util.UpdateUtil;

/**
 * Created by 15859 on 2016/9/17.
 * 系统设置类
 */
public class SystemSettingActivity extends BaseActivity implements View.OnClickListener {
    private TextView mBack, mCache, mVersion, mSuggest, mExit,mUpdatePwd;
    private RelativeLayout mClear, mUpdate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        findVew();
        getData();
    }

    private void getData() {
        mVersion.setText(PackageUtils.getAppVersionName(CustomApplication.getContext()));
        //获取缓存的路径
        String path = Glide.getPhotoCacheDir(this).getPath();
        String size = FileSizeUtil.getAutoFileOrFilesSize(path);
        mCache.setText(size);
    }

    private void findVew() {
        //返回
        mBack = (TextView) findViewById(R.id.tv_back);
        //清除缓存
        mClear = (RelativeLayout) findViewById(R.id.rl_clear);
        //显示缓存
        mCache = (TextView) findViewById(R.id.tv_cache);
        //更新软件
        mUpdate = (RelativeLayout) findViewById(R.id.rl_update);
        //版本显示
        mVersion = (TextView) findViewById(R.id.tv_version);
        //意见反馈，当前为gone状态，后续添加
        mSuggest = (TextView) findViewById(R.id.tv_suggest);
        //退出登录
        mExit = (TextView) findViewById(R.id.tv_exit);
        //修改密码
        mUpdatePwd=(TextView)findViewById(R.id.tv_updatePwd);
        mBack.setOnClickListener(this);
        mClear.setOnClickListener(this);
        mUpdate.setOnClickListener(this);
        mSuggest.setOnClickListener(this);
        mExit.setOnClickListener(this);
        mUpdatePwd.setOnClickListener(this);
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
                UpdateUtil.getInstance().checkUpdate(activity, mVersion, true);
                break;
            //提交意见
            case R.id.tv_suggest:
                break;
            //修改密码
            case R.id.tv_updatePwd:
                Intent updatePwd=new Intent(SystemSettingActivity.this,SystemChangePassword.class);
                startActivity(updatePwd);
                break;
            //退出登录
            case R.id.tv_exit:
//                ExitPopup exitPopup=new ExitPopup(SystemSettingActivity.this);
//                exitPopup.showPopupWindow();
               /* PreferenceUtil.clearPreference();
                Intent intent = new Intent(SystemSettingActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();*/
//                SerializableUtils.deleteSerializable(this,ConstantsBean.SUB_FILE_NAME);
//                SerializableUtils.deleteSerializable(this,ConstantsBean.TAG_FILE_NAME);
                PreferenceUtil.clearPreference();
                SerializableUtils.deleteSerializable(activity, ConstantsBean.MY_USER_INFO);
                ActivityCollector.finishAll();
                Intent intent = new Intent(SystemSettingActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
        }
    }
}
