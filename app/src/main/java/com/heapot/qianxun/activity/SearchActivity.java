package com.heapot.qianxun.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.heapot.qianxun.R;
import com.heapot.qianxun.popupwindow.SearchListView;

/**
 * Created by 15859 on 2016/9/12.
 */
public class SearchActivity extends Activity implements View.OnClickListener, View.OnKeyListener {
    private EditText et_search;
    private TextView tv_tip, tv_clear;
    private SearchListView listView;
    private ImageView mBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //查找控件
        findView();
        //获取数据
        getData();
        //设置数据
        setData();
    }

    private void findView() {
        et_search = (EditText) findViewById(R.id.et_search);
        tv_tip = (TextView) findViewById(R.id.tv_tip);
        listView = (com.heapot.qianxun.popupwindow.SearchListView) findViewById(R.id.listView);
        tv_clear = (TextView) findViewById(R.id.tv_clear);
        mBack = (ImageView) findViewById(R.id.iv_back);
        mBack.setOnClickListener(this);
        tv_clear.setOnClickListener(this);
        et_search.setOnKeyListener(this);
        // 调整EditText左边的搜索按钮的大小
        Drawable drawable = getResources().getDrawable(R.drawable.search);
        drawable.setBounds(0, 0, 60, 60);// 第一0是距左边距离，第二0是距上边距离，60分别是长宽
        et_search.setCompoundDrawables(drawable, null, null, null);// 只放左边
    }

    private void getData() {

    }

    private void setData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_clear:

                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.et_search:

                break;
        }

    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {// 修改回车键功能
// 先隐藏键盘
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                    getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
// 按完搜索键后将当前查询的关键字保存起来,如果该关键字已经存在就不执行保存
            boolean hasData=hasData(et_search.getText().toString().trim());

        }
        return false;
    }

    private boolean hasData(String trim) {
        return false;
    }
}
