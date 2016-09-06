package com.heapot.qianxun.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.heapot.qianxun.R;

/**
 * Created by 15859 on 2016/9/3.
 * 修改名字签名界面
 *
 */
public class PersonInfoAlterActivity extends AppCompatActivity {
    private EditText etInfo;

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
        etInfo = (EditText) findViewById(R.id.et_info);
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
}