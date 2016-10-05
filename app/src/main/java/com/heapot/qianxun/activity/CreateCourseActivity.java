package com.heapot.qianxun.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.heapot.qianxun.R;

/**
 * Created by 15859 on 2016/9/30.
 */
public class CreateCourseActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_back, tv_complete, tv_classTitle, tv_classContent, tv_classChoose;
    private ImageView iv_class, iv_begin,iv_end,iv_number,iv_describe,iv_describeChoose;
    private TextView tv_beginTitle, tv_beginContent, tv_beginChoose;
    private TextView tv_endTitle,tv_endContent,tv_endChoose;
    private TextView tv_numberTitle,tv_numberContent,tv_numberChoose;
    private TextView tv_describeTitle,tv_describeContent;
    private TextView tv_more;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_course);
        findView();
    }

    private void findView() {
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_complete = (TextView) findViewById(R.id.tv_complete);
        tv_back.setOnClickListener(this);
        tv_complete.setOnClickListener(this);

        iv_class = (ImageView) findViewById(R.id.iv_class);
        tv_classTitle = (TextView) findViewById(R.id.tv_classTitle);
        tv_classContent = (TextView) findViewById(R.id.tv_classContent);
        tv_classChoose = (TextView) findViewById(R.id.tv_classChoose);
        tv_classChoose.setOnClickListener(this);

        iv_begin = (ImageView) findViewById(R.id.iv_begin);
        tv_beginTitle = (TextView) findViewById(R.id.tv_beginTitle);
        tv_beginContent = (TextView) findViewById(R.id.tv_beginContent);
        tv_beginChoose = (TextView) findViewById(R.id.tv_beginChoose);
        tv_beginChoose.setOnClickListener(this);

        iv_end = (ImageView) findViewById(R.id.iv_end);
        tv_endTitle = (TextView) findViewById(R.id.tv_endTitle);
        tv_endContent = (TextView) findViewById(R.id.tv_endContent);
        tv_endChoose = (TextView) findViewById(R.id.tv_endChoose);
        tv_endChoose.setOnClickListener(this);

        iv_number = (ImageView) findViewById(R.id.iv_number);
        tv_numberTitle = (TextView) findViewById(R.id.tv_numberTitle);
        tv_numberContent = (TextView) findViewById(R.id.tv_numberContent);
        tv_numberChoose = (TextView) findViewById(R.id.tv_numberChoose);
        tv_numberChoose.setOnClickListener(this);

        iv_describe = (ImageView) findViewById(R.id.iv_describe);
        tv_describeTitle = (TextView) findViewById(R.id.tv_describeTitle);
        tv_describeContent = (TextView) findViewById(R.id.tv_describeContent);
        iv_describeChoose = (ImageView) findViewById(R.id.iv_describeChoose);
        iv_describeChoose.setOnClickListener(this);

       tv_more=(TextView) findViewById(R.id.tv_more);
        tv_more.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_complete:
                break;
            case R.id.tv_classChoose:
                break;
            case R.id.tv_beginChoose:
             setBeginTime();
                break;
            case R.id.tv_endChoose:
                break;
            case R.id.tv_numberChoose:
                break;
            case R.id.iv_describeChoose:
                break;
            case R.id.tv_more:
                break;
        }
    }

    private void setBeginTime() {

    }
}
