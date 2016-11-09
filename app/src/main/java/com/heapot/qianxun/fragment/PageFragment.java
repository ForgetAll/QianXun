package com.heapot.qianxun.fragment;

import android.app.Activity;
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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.heapot.qianxun.R;
import com.heapot.qianxun.activity.ArticleActivity;
import com.heapot.qianxun.activity.CourseActivity;
import com.heapot.qianxun.activity.JobActivity;
import com.heapot.qianxun.adapter.MainTabAdapter;
import com.heapot.qianxun.application.CustomApplication;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.bean.MainListBean;
import com.heapot.qianxun.helper.OnRecyclerViewItemClickListener;
import com.heapot.qianxun.util.JsonUtil;
import com.orhanobut.logger.Logger;

import org.json.JSONObject;

import java.util.ArrayList;
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
    private int pageIndex = 1;//记录加载第几页的书
    private int pageSize = 6;//记录每页加载数据的大小
    private int maxPageIndex = 1;//网络请求获取到最大页码进行限制
    View mView;
    private Integer[] colorArray = {android.R.color.holo_green_light,android.R.color.holo_blue_light,android.R.color.holo_purple,android.R.color.holo_orange_light};

    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean isRefresh = false;

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private MainTabAdapter adapter;
    private List<MainListBean.ContentBean> list = new ArrayList<>();

    private Activity mActivity;

    private boolean isPre = false;

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

        return mView;

    }

    /**
     * 以下为有数据的情况
     */
    private void initView(){
        recyclerView = (RecyclerView) mView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        swipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.srl_main_fragment);
        linearLayoutManager = new LinearLayoutManager(getContext());
    }
    private void initEvent(){

//        loadData();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MainTabAdapter(getContext());
        if (adapter.isShowFooter()){
            adapter.isShowFooter(false);
        }
        adapter.setList(list);
        swipeRefreshLayout.setColorSchemeResources(colorArray[0],colorArray[1],colorArray[2],colorArray[3]);

        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView.addOnScrollListener(onScrollListener);

    }
    private void loadData(){
        String id = mId;
        if (mId != null ){
            getListWithTags(id);
        }
    }

    @Override
    public void onRefresh() {
        if (!isRefresh){
            swipeRefreshLayout.setRefreshing(false);
            pageIndex = 1;//重置页面为第一页
            list.clear();//清空list数据，放便重新赋值
            loadData();
            adapter.isShowFooter(false);
            adapter.notifyDataSetChanged();
            Toast.makeText(getContext(), "已刷新", Toast.LENGTH_SHORT).show();
        }
    }
    //获取指定标签对应的列表
    private void getListWithTags(String id){
        String url = ConstantsBean.GET_LIST_WITH_TAG+"catalogId=" +id+"&page="+ pageIndex +"&pagesize="+pageSize;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Logger.d("当前页面---->"+mPage);
                        Logger.json(String.valueOf(response));
                        MainListBean mainListBean = (MainListBean) JsonUtil.fromJson(String.valueOf(response),MainListBean.class);
                        maxPageIndex =mainListBean.getTotal_page();
                        list.addAll(mainListBean.getContent());
                        if (mainListBean.getContent().size()==0){
                            adapter.isShowFooter(false);
                        }
                        adapter.setList(list);
                        recyclerView.setAdapter(adapter);
                        adapter.setOnItemClickListener(onClickListener);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Logger.d("未知错误");
                    }
                }
        );
        CustomApplication.getRequestQueue().add(jsonObjectRequest);
    }
    /**
     * 点击事件
     */
    private OnRecyclerViewItemClickListener onClickListener = new OnRecyclerViewItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            if (CustomApplication.getCurrentPageName().equals("PAGE_SCIENCE")){
                Logger.d("跳转到文章详情，id是"+list.get(position).getId());
                Intent intent = new Intent(getActivity(), ArticleActivity.class);
                intent.putExtra("id",list.get(position).getId());
                startActivity(intent);
            }else if (CustomApplication.getCurrentPageName().equals("PAGE_RECRUIT")){
                Intent job = new Intent(getActivity(), JobActivity.class);
                job.putExtra("id",list.get(position).getId());
                startActivity(job);
            }else {
                Intent course = new Intent(getActivity(), CourseActivity.class);
                course.putExtra("id",list.get(position).getId());
                startActivity(course);
            }

        }
    };
    /**
     *上拉加载
     */
    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        private int lastVisibleItem;
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (lastVisibleItem == adapter.getItemCount()-1){
                adapter.isShowFooter(true);
                adapter.notifyDataSetChanged();
            }
            if (newState == recyclerView.SCROLL_STATE_IDLE
                    && lastVisibleItem +1 == adapter.getItemCount()
                    && adapter.isShowFooter()){
                //加载更多数据,不清空数据
                pageIndex++;
                if (pageIndex <= maxPageIndex) {
                    loadData();
                    adapter.isShowFooter(false);
                    adapter.notifyDataSetChanged();//这里不能使用setList

                    recyclerView.setAdapter(adapter);
                }else {
                    Toast.makeText(getContext(), "没有更多数据了", Toast.LENGTH_SHORT).show();
                    adapter.isShowFooter(false);
                    adapter.notifyDataSetChanged();
                }

            }

        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
        }
    };
//    懒加载实现


    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()){
            setUserVisibleHint(true);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPre = true;
    }

    @Override
    public boolean getUserVisibleHint() {
        return super.getUserVisibleHint();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isPre){
            list.clear();
            loadData();

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mActivity = null;
    }
}
