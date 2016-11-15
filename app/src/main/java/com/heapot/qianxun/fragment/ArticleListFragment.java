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
import android.widget.Toast;

import com.heapot.qianxun.R;
import com.heapot.qianxun.activity.detail.ArticleActivity;
import com.heapot.qianxun.adapter.ArticleListAdapter;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.bean.MainListBean;
import com.heapot.qianxun.util.network.LoadTagsList;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karl on 2016/11/11.
 * desc:
 */

public class ArticleListFragment extends Fragment
        implements LoadTagsList.onLoadTagsListListener, ArticleListAdapter.OnArticleListItemClickListener {

    private static final String CURRENT_PAGE = "CURRENT_PAGE";

    private static final String CURRENT_PAGE_ID = "CURRENT_PAGE_ID";

    private static final int PAGE_SIZE = 6;

    private int mPage;

    private String mPageId = "";

    private int mCurrentIndex = 1;

    private int mMaxIndex =0;

    private View mView;

    private LoadTagsList loadTagsList;

    private RecyclerView mRecyclerView;

    private LinearLayoutManager layoutManager;

    private ArticleListAdapter mAdapter;

    private SwipeRefreshLayout mRefresh;

    private List<MainListBean.ContentBean> mList = new ArrayList<>();

    private boolean isRefresh = false;

    private boolean isLoadMore = false;

    private Integer[] refreshColor = {android.R.color.holo_green_light,android.R.color.holo_blue_light,android.R.color.holo_purple,android.R.color.holo_orange_light};

    private boolean isPre = false;


    /**
     * 单例，返回当前Fragment类型
     * @param page 当前第几个tab
     * @param id 当前Fragment
     * @return ArticleListFragment
     */
    public static ArticleListFragment getInstance(int page,String id){
        Bundle args = new Bundle();
        args.putInt(CURRENT_PAGE,page);
        args.putString(CURRENT_PAGE_ID,id);
        ArticleListFragment articleListFragment = new ArticleListFragment();
        articleListFragment.setArguments(args);

        return articleListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //这里用于取数据
        mPage = getArguments().getInt(CURRENT_PAGE);
        mPageId = getArguments().getString(CURRENT_PAGE_ID);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_layout_list,container,false);
        Logger.d("当前页面是"+mPage);
        initView();
        initEvent();

        return mView;
    }

    private void initView(){
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.rv_main_list);
        layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRefresh = (SwipeRefreshLayout) mView.findViewById(R.id.srl_main_refresh);

    }

    private void initEvent(){
        loadTagsList = new LoadTagsList(getContext());
        loadTagsList.setOnLoadTagsListListener(this);
        mRefresh.setColorSchemeResources(refreshColor[0],refreshColor[1],refreshColor[2],refreshColor[3]);

        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isRefresh) {
                    isRefresh = true;
                    mRefresh.setRefreshing(true);
                    initData(2);
                }
            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && (layoutManager.findLastVisibleItemPosition() == layoutManager.getItemCount() -1)
                        && !isLoadMore){
                    isLoadMore = true;
                    initData(1);
                }

            }
        });


        if (mPageId != null) {
            initData(0);
        }

    }

    private void initData(int flag){
        if (flag == 1){
            mCurrentIndex ++;

        } else{

            mCurrentIndex = 1;
            isLoadMore = false;
        }

        String url = ConstantsBean.GET_LIST_WITH_TAG+"catalogId=" +mPageId+"&page="+ mCurrentIndex +"&pagesize="+PAGE_SIZE;
        loadTagsList.getTagsList(url,flag);
    }


    @Override
    public void onSuccessResponse(List<MainListBean.ContentBean> list, int totalIndex, int flag) {
        mMaxIndex = totalIndex;
        if (list!= null){
            loadData(flag,list);
        }
    }

    @Override
    public void onErrorResponse() {

    }

    @Override
    public void onError() {

    }


    /**
     * 刷新的集中形式
     * @param flag 0：初始化刷新  1，上拉加载  2，下拉刷新
     * @param list
     */
    private void loadData(int flag,List<MainListBean.ContentBean> list){
        if (flag == 0 || flag == 2){
            mList.clear();
            mList.addAll(list);
        }else if (flag ==1){
            mList.addAll(list);
        }

        if (mCurrentIndex > mMaxIndex){

            isLoadMore = true;

            //实际上走不到这一步
            if (flag == 0 ){
                if (mAdapter == null){
                    mAdapter = new ArticleListAdapter(getActivity(), list);
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.setOnArticleListClickListener(this);
                }
            }else if (flag == 1){

                Toast.makeText(getActivity(), "没有更多数据了", Toast.LENGTH_SHORT).show();

            }else if (flag ==2){

            }

        }else if (mCurrentIndex == mMaxIndex){

            isLoadMore = false;

            mList.addAll(list);

            if (flag == 0) {

                mAdapter = new ArticleListAdapter(getActivity(), list);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.setOnArticleListClickListener(this);


            }else if (flag == 1){

                mAdapter.setMoreData(list);

            }else if (flag == 2){

                mAdapter.setData(list);
                mRefresh.setRefreshing(false);
                isRefresh = false;
            }

        }else {
            isLoadMore = false;
            mList.addAll(list);
            Logger.d("mList:"+mList.size()+",list:"+list.size());

            if (flag == 0) {
                if (mAdapter == null) {
                    mAdapter = new ArticleListAdapter(getActivity(), list);
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.setOnArticleListClickListener(this);
                }

            }else if (flag == 1){

                mAdapter.setMoreData(list);

            }else if (flag == 2){
                mAdapter.setData(list);
                mRefresh.setRefreshing(false);
                isRefresh = false;
            }
        }

    }


    @Override
    public void onItemClick(View view, int position) {

        Intent intent = new Intent();
        intent.setClass(getActivity(), ArticleActivity.class);
        intent.putExtra("id",mList.get(position).getId());
        startActivity(intent);

    }

    /**
     * 实现懒加载
     */


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
            initData(0);
        }
    }
}
