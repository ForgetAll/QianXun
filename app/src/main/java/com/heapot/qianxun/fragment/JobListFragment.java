package com.heapot.qianxun.fragment;

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
import com.heapot.qianxun.adapter.JobListAdapter;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.bean.MainListBean;
import com.heapot.qianxun.util.network.LoadTagsList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karl on 2016/11/14.
 * desc:
 */

public class JobListFragment extends Fragment implements LoadTagsList.onLoadTagsListListener, JobListAdapter.OnJobListItemClickListener {

    private View mView;

    private static final String CURRENT_PAGE_NUM = "CURRENT_PAGE_NUM";

    private static final String CURRENT_PAGE_ID = "CURRENT_PAGE_ID";

    private static int PAGE_SIZE = 8;

    private int mPage;

    private String mPageId = "";

    private int mMaxIndex = 0;

    private int mCurrentIndex = 1;

    private LoadTagsList loadTagsList;

    private RecyclerView mRecyclerView;

    private LinearLayoutManager mLayoutManager;

    private JobListAdapter mAdapter;

    private SwipeRefreshLayout mRefresh;

    private List<MainListBean.ContentBean> mList = new ArrayList<>();

    private boolean isRefresh = false;

    private boolean isLoadMore = false;

    private Integer[] refreshColor = {
            android.R.color.holo_green_light,
            android.R.color.holo_blue_light,
            android.R.color.holo_purple,
            android.R.color.holo_orange_light};


    public static JobListFragment getInstance(int page,String pageId){
        Bundle bundle = new Bundle();
        bundle.putInt(CURRENT_PAGE_NUM,page);
        bundle.putString(CURRENT_PAGE_ID,pageId);
        JobListFragment jobListFragment = new JobListFragment();
        jobListFragment.setArguments(bundle);
        return jobListFragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(CURRENT_PAGE_NUM);
        mPageId = getArguments().getString(CURRENT_PAGE_ID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_layout_list,container,false);
        initView();
        initEvent();

        return mView;
    }

    private void initView(){
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.rv_main_list);
        mRefresh = (SwipeRefreshLayout) mView.findViewById(R.id.srl_main_refresh);
    }

    private void initEvent(){
        mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        loadTagsList = new LoadTagsList(getActivity());
        loadTagsList.setOnLoadTagsListListener(this);
        mRefresh.setColorSchemeResources(refreshColor[0],refreshColor[1],refreshColor[2],refreshColor[3]);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isRefresh){
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
                        && mLayoutManager.findLastVisibleItemPosition() == mLayoutManager.getItemCount() -1
                        && !isLoadMore){

                    isLoadMore = true;
                    initData(1);
                }

            }
        });

        if (mPageId != null){
            initData(0);
        }

    }

    private void initData(int flag){
        if (flag == 0 || flag == 2 || mCurrentIndex > mMaxIndex){
            mCurrentIndex =1;
        }
        if (flag == 1){
            mCurrentIndex ++;
        }
        String url = ConstantsBean.GET_LIST_WITH_TAG+"catalogId=" +mPageId+"&page="+ mCurrentIndex +"&pagesize="+PAGE_SIZE;
        loadTagsList.getTagsList(url,flag);
    }

    @Override
    public void onSuccessResponse(List<MainListBean.ContentBean> list, int totalIndex, int flag) {
        mMaxIndex = totalIndex;
        if (list!=null){
            loadData(flag,list);
        }
    }

    @Override
    public void onErrorResponse() {

    }

    @Override
    public void onError() {

    }

    private void loadData(int flag,List<MainListBean.ContentBean> list){

        if (flag == 0){
            mList.clear();
        }

        if (flag == 1){
            isLoadMore = false;
        }

        if (flag == 2){
            mList.clear();
            mRefresh.setRefreshing(false);
            isRefresh = false;
        }

        if (mCurrentIndex > mMaxIndex){

            if (flag == 0){
                if (mAdapter == null){
                    mAdapter = new JobListAdapter(getActivity(),mList);
                    mRecyclerView.setAdapter(mAdapter);
                }
            }

            if (flag ==1 || flag ==2){

            }
        }else {
            mList.addAll(list);

            if (flag == 0){
                if (mAdapter == null){
                    mAdapter = new JobListAdapter(getActivity(),mList);
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.setOnJobListItemClickListener(this);
                }
            }

            if (flag == 1 || flag ==2){
                mAdapter.setData(mList);
            }
        }


    }

    @Override
    public void onItemClick(View view, int postion) {
        Toast.makeText(getActivity(), "点击了", Toast.LENGTH_SHORT).show();
    }
}
