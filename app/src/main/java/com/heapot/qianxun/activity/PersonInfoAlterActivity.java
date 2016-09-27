package com.heapot.qianxun.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.heapot.qianxun.R;
import com.heapot.qianxun.bean.ConstantsBean;

/**
 * Created by 15859 on 2016/9/3.
 * 修改名字签名界面
 *
 */
public class PersonInfoAlterActivity extends BaseActivity implements View.OnClickListener {
    private EditText mInfo;
    private TextView mComplete,mBack;

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
      mComplete=(TextView)  findViewById(R.id.tv_complete);
        mBack=(TextView)  findViewById(R.id.tv_back);
        mComplete.setOnClickListener(this);
        mBack.setOnClickListener(this);

    }


    protected void getData() {
    }


    protected void setData() {
        /*backClick();
        setLeftTextView("信息修改");
        setRightTextView("完成").setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info = etInfo.getText().toString();
                Intent intent = new Intent();
                intent.putExtra(ConstantsBean.INFO, info);
                setResult(RESULT_OK, intent);
                finish();
            }
        });*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_complete:
                String info = mInfo.getText().toString();
                Intent intent = new Intent();
                intent.putExtra(ConstantsBean.INFO, info);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }

    }
}