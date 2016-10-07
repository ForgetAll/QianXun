package com.heapot.qianxun.activity.create;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.heapot.qianxun.R;
import com.heapot.qianxun.activity.BaseActivity;
import com.heapot.qianxun.application.CustomApplication;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.bean.MyUserBean;
import com.heapot.qianxun.bean.SubBean;
import com.heapot.qianxun.bean.SubscribedBean;
import com.heapot.qianxun.bean.TagsBean;
import com.heapot.qianxun.helper.SerializableUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Karl on 2016/10/7.
 * desc：创建项目的时候选择分类
 *
 */
public class SortList extends BaseActivity implements View.OnClickListener {
    private ImageView back;
    private TextView title;
    private ListView listView;
    private List<TagsBean.ContentBean> list = new ArrayList<>();
    private List<TagsBean.ContentBean> tagList = new ArrayList<>();//这里tagList的意思是对应的二级标题
    int current = 0;
    String pid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_list);
        initView();
        initEvent();
    }
    private void initView(){
        title = (TextView) findViewById(R.id.txt_title);
        back = (ImageView) findViewById(R.id.iv_btn_back);
        listView = (ListView) findViewById(R.id.lv_sort_list);

        //一些初始化设置
        title.setText("选择分类");
        back.setOnClickListener(this);

        Intent intent = getIntent();
        current = intent.getExtras().getInt("create_status");//根据current判断当前是创建文章还是别的
        switch (current){
            case 0:
                pid = CustomApplication.PAGE_ARTICLES_ID;
                break;
            case 1:
                break;
            case 2:
                break;
            default:
                pid = CustomApplication.PAGE_ARTICLES_ID;
                break;
        }

    }
    private void initEvent(){
        loadData();
    }

    /**
     * 初始化数据
     */
    private void loadData(){
        Object object = SerializableUtils.getSerializable(this, ConstantsBean.TAG_FILE_NAME);
        list.addAll((Collection<? extends TagsBean.ContentBean>) object);
        List<Integer> posList = new ArrayList<>();
        //获取当前页面的二级标题
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getPid() != null && list.get(i).getPid().equals(pid)){
                posList.add(i);
            }
        }
        for (int i = 0; i < posList.size(); i++) {
            tagList.add(list.get(posList.get(i)));
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                this.finish();//直接返回
                break;

        }
    }


}
