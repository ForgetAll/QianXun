package com.heapot.qianxun.activity.create;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.heapot.qianxun.R;
import com.heapot.qianxun.activity.BaseActivity;
import com.heapot.qianxun.bean.ConstantsBean;

/**
 * Created by 15859 on 2016/10/10.
 */
public class CreateJobDescribe extends BaseActivity implements View.OnClickListener {
    private EditText mInfo;
    private TextView mComplete, mBack;

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
                String info = mInfo.getText().toString().trim();
                if (!info.isEmpty()){
                    Intent intent = new Intent();
                    intent.putExtra("describe", info);
                    setResult(RESULT_OK, intent);
                    finish();
                }else {
                    Toast.makeText(CreateJobDescribe.this,"输入内容不能为空",Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

}
