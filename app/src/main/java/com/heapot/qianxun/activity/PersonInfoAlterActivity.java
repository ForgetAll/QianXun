package com.heapot.qianxun.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.heapot.qianxun.R;
import com.heapot.qianxun.bean.ConstantsBean;

/**
 * Created by 15859 on 2016/9/3.
 * 修改名字签名界面
 */
public class PersonInfoAlterActivity extends BaseActivity implements View.OnClickListener {
    private EditText mInfo;
    private TextView mComplete, mBack;
    private int personalStatus = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_alter);
        //查找控件
        initView();
        //获取数据
        getData();
        //设置数据
        setData();
    }


    protected void initView() {
        mInfo = (EditText) findViewById(R.id.et_info);
        String info = getIntent().getStringExtra(ConstantsBean.INFO);
        mInfo.setText(info);
        mComplete = (TextView) findViewById(R.id.tv_complete);
        mBack = (TextView) findViewById(R.id.tv_back);
        mComplete.setOnClickListener(this);
        mBack.setOnClickListener(this);

    }


    protected void getData() {
    }


    protected void setData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_complete:
               // personalStatus=1;
                String info = mInfo.getText().toString();
                Intent intent = new Intent();
                intent.putExtra(ConstantsBean.INFO, info);
                setResult(RESULT_OK, intent);
               // sendBroadcast();
                finish();
                break;
        }

    }

    private void sendBroadcast() {
        //发送广播
        Intent intent = new Intent("com.personal.change");
        intent.putExtra("personalStatus",personalStatus);
        switch (personalStatus){
            case 0://没有更新不需要处理
                break;
            case 1:
                personalStatus=0;
                break;
        }
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.sendBroadcast(intent);//发送本地广播
       PersonInfoAlterActivity.this.finish();
    }
}