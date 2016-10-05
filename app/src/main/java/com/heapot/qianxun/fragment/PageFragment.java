package com.heapot.qianxun.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.heapot.qianxun.R;
import com.heapot.qianxun.activity.ArticleActivity;
import com.heapot.qianxun.activity.Subscription;
import com.heapot.qianxun.adapter.MainTabAdapter;
import com.heapot.qianxun.application.CustomApplication;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.bean.MainListBean;
import com.heapot.qianxun.helper.OnRecyclerViewItemClickListener;
import com.heapot.qianxun.util.CommonUtil;
import com.heapot.qianxun.util.JsonUtil;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Karl on 2016/8/25.
 * 每个模块（学术招聘培训）对应页面下每个Tab对应的列表
 * 1、先实现下拉刷新
 *
 */
public class PageFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    public static final String PAGE = "PAGE";
    public static final String ID = "PAGE_ID";
    private int mPage;
    private String mId;//记录当前页面对应标签的id
    private int pageNum = 1;//记录加载第几页的书
    private int pageSize = 6;//记录每页加载数据的大小
    private int maxPageNum = 1;//网络请求获取到最大页码进行限制
    View mView;

    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean isRefresh = false;

    private RecyclerView recyclerView;
    private MainTabAdapter adapter;
    private List<MainListBean.ContentBean> list = new ArrayList<>();

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
        mView = inflater.inflate(R.layout.layout_list,container,false);
        initView();
        initEvent();
        Logger.d("OnCreate-");
        return mView;

    }

    /**
     * 以下为有数据的情况
     */
    private void initView(){
        recyclerView = (RecyclerView) mView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        recyclerView.setHasFixedSize(true);
        swipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.srl_main_fragment);
    }
    private void initEvent(){
        loadData();
        adapter = new MainTabAdapter(getContext(),list);
        //添加点击事件
        adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                Intent intent = new Intent(getActivity(), ArticleActivity.class);
//                startActivity(intent);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_green_light,
                android.R.color.holo_blue_light,
                android.R.color.holo_purple,
                android.R.color.holo_orange_light
        );
    }
    /**
     * 模拟数据
     */
    private void loadData(){
        Logger.d("LoadData");
        String id = mId;
        if (mId != null ){
            getListWithTags(id);
        }
    }

    @Override
    public void onRefresh() {
        if (!isRefresh){
            swipeRefreshLayout.setRefreshing(false);
            pageNum = 1;//重置页面为第一页
            list.clear();//清空list数据，放便重新赋值
            loadData();
            Toast.makeText(getContext(), "已刷新", Toast.LENGTH_SHORT).show();
        }
    }
    //获取指定标签对应的列表
    private void getListWithTags(String id){
        Logger.d(id);
        String url = ConstantsBean.GET_LIST_WITH_TAG+"catalogId=" +id+"&page="+pageNum+"&pagesize="+pageSize;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        MainListBean mainListBean = (MainListBean) JsonUtil.fromJson(String.valueOf(response),MainListBean.class);
                        list.addAll(mainListBean.getContent());
                        Logger.d("请求数据,获取到集合的大小"+list.size());
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        CustomApplication.getRequestQueue().add(jsonObjectRequest);
    }
}
