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
import com.heapot.qianxun.adapter.MainTabAdapter;
import com.heapot.qianxun.helper.OnRecyclerViewItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karl on 2016/8/25.
 * 每个模块（学术招聘培训）对应页面下每个Tab对应的列表
 *
 */
public class PageFragment extends Fragment {
    public static final String PAGE = "PAGE";
    private int mPage;
    View mView;

    private RecyclerView recyclerView;
    private MainTabAdapter adapter;
    private List<String> list = new ArrayList<>();



    public static PageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(PAGE,page);
        PageFragment pageFragment = new PageFragment();
        pageFragment.setArguments(args);
        return pageFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(PAGE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.layout_list,container,false);
        initView();
        initEvent();
        return mView;
    }

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
