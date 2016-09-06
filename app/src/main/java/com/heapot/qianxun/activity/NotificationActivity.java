package com.heapot.qianxun.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.heapot.qianxun.R;
import com.heapot.qianxun.adapter.MyPageAdapter;
import com.heapot.qianxun.adapter.NotificationPageAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karl on 2016/8/29.
 */
public class NotificationActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mBack;
    private TabLayout tabLayout;
    private NotificationPageAdapter adapter;
    private List<String> list;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        initView();
        initEvent();
    }
    private void initView(){
        mBack = (ImageView) findViewById(R.id.iv_back_notification);
        tabLayout = (TabLayout) findViewById(R.id.tb_tab_notification);
        viewPager = (ViewPager) findViewById(R.id.vp_content_notification);
        list = new ArrayList<>();


    }
    private void initEvent(){
        mBack.setOnClickListener(this);
        getData();

    }
    private void getData(){
        list.add("人脉");
        list.add("探讨");
        adapter = new NotificationPageAdapter(getSupportFragmentManager(),this,list);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    public void onClick(View v) {

    }
}
