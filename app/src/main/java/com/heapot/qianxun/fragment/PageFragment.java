package com.heapot.qianxun.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.heapot.qianxun.R;
import com.heapot.qianxun.activity.ArticleActivity;
import com.heapot.qianxun.activity.Subscription;
import com.heapot.qianxun.adapter.MainTabAdapter;
import com.heapot.qianxun.application.CustomApplication;
import com.heapot.qianxun.helper.OnRecyclerViewItemClickListener;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karl on 2016/8/25.
 * 每个模块（学术招聘培训）对应页面下每个Tab对应的列表
 *
 */
public class PageFragment extends Fragment {
    public static final String PAGE = "PAGE";
    public static final String ID = "PAGE_ID";
    private int mPage;
    private String mId;
    View mView;

    private RecyclerView recyclerView;
    private MainTabAdapter adapter;
    private List<String> list = new ArrayList<>();

    //空数据
    private TextView textView;

    public static PageFragment newInstance(int page,String id) {
        Bundle args = new Bundle();
        args.putInt(PAGE,page);
        args.putString(ID,id);
        PageFragment pageFragment = new PageFragment();
        pageFragment.setArguments(args);
        return pageFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(PAGE);
        mId = getArguments().getString(ID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mPage == -1){
            mView = inflater.inflate(R.layout.layout_empty_data,container,false);

        }else {
            mView = inflater.inflate(R.layout.layout_list,container,false);
            initView();
            initEvent();
        }
        return mView;
    }
    /**
     * 以下为空数据的情况
     */
    private void initEmptyView(){
        textView = (TextView) mView.findViewById(R.id.txt_main_to_sub);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Subscription.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 以下为有数据的情况
     */
    private void initView(){
        recyclerView = (RecyclerView) mView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        recyclerView.setHasFixedSize(true);


    }
    private void initEvent(){
        adapter = new MainTabAdapter(getContext(),list);
        loadData();
        recyclerView.setAdapter(adapter);
        //添加点击事件
        adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(), "点击了", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), ArticleActivity.class);
                startActivity(intent);
            }
        });
    }
    /**
     * 模拟数据
     */
    private void loadData(){
        for (int i = 0; i < 20; i++) {
            list.add("Tab #"+mPage+" Item #"+i);
        }
    }

}
