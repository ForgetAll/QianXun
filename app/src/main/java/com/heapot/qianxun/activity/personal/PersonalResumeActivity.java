package com.heapot.qianxun.activity.personal;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.heapot.qianxun.R;
import com.heapot.qianxun.activity.BaseActivity;
import com.heapot.qianxun.bean.Resume;
import com.heapot.qianxun.util.network.LoadResume;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created by Karl on 2016/11/21.
 * desc:个人简历页面
 */

public class PersonalResumeActivity extends BaseActivity implements LoadResume.OnLoadResumeListener {

    private ImageView mBack;

    private TextView mTitle,mSave;

    private EditText mResume;

    private LoadResume loadResume;

    private boolean isCreate = false;

    private String id = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_resume);
        initView();
        initEvent();


    }

    private void initView(){

        mBack = (ImageView) findViewById(R.id.iv_btn_back);
        mTitle = (TextView) findViewById(R.id.txt_title);
        mSave = (TextView) findViewById(R.id.txt_btn_function);
        mResume = (EditText) findViewById(R.id.edt_resume);

    }

    private void initEvent(){


        mTitle.setText("个人简历");

        mSave.setVisibility(View.VISIBLE);

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = mResume.getText().toString();
                if (content.equals("")){
                    Toast.makeText(PersonalResumeActivity.this, "不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    if (isCreate) {
                        String json = "{\"resume\":\""+content+"\"}";
                        loadResume.addResume(json);
                    } else {
                        if (!id.equals("")) {
                            String json = "{\"id\":\"" + id + "\"," + "\"resume\":\"" + content + "\"}";
                            Logger.json(json);
                            loadResume.updateResume(json);
                        }else {
                            Logger.d("没有拿到id");
                        }
                        loadResume.updateResume(content);
                    }
                }
            }
        });

        loadResume = new LoadResume(this);
        loadResume.setOnLoadResumeListener(this);
        initData();

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonalResumeActivity.this.finish();
            }
        });
    }

    private void initData(){

        loadResume.loadResumeList();
    }

    @Override
    public void onListSuccess(int total, List<Resume.ContentBean.RowsBean> list) {
        if (total == 0){
            isCreate = true;
            mSave.setText("创建");
            mResume.setHint("创建你的第一份简历吧");
        }else {
            mSave.setText("更新");
            if (list != null) {
                mResume.setText(list.get(0).getResume());
                id = list.get(0).getId();
            }
        }
    }

    @Override
    public void onAddResumeSuccess() {

        Toast.makeText(this, "创建成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateResumeSuccess() {

        Toast.makeText(this, "更新成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void checkoutResumeSuccess() {

    }

    @Override
    public void onError() {
        Toast.makeText(this, "操作失败", Toast.LENGTH_SHORT).show();
    }
}
