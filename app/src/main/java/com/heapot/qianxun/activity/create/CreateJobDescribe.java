package com.heapot.qianxun.activity.create;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.heapot.qianxun.R;
import com.heapot.qianxun.activity.BaseActivity;

/**
 * Created by 15859 on 2016/10/10.
 */
public class CreateJobDescribe extends BaseActivity implements View.OnClickListener {
    private EditText mInfo;
    private TextView mComplete, mBack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_job_new_describe);
        //查找控件
        initView();
        //获取数据
        getData();
        //设置数据
        setData();
    }


    protected void initView() {
        String describeTitle=getIntent().getStringExtra("describeTitle");
        mInfo = (EditText) findViewById(R.id.et_info);
        mInfo.setText(describeTitle);
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
                String describe = mInfo.getText().toString().trim();
                Log.e("输入的内容",describe);
                if (!describe.isEmpty()){
                    Intent intent = new Intent();
                    intent.putExtra("describe", describe);
                    setResult(RESULT_OK, intent);
                    finish();
                }else {
                    Toast.makeText(CreateJobDescribe.this,"输入内容不能为空",Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

}
