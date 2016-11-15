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
import com.heapot.qianxun.activity.detail.CourseActivity;
import com.heapot.qianxun.adapter.TrainListAdapter;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.bean.MainListBean;
import com.heapot.qianxun.util.network.LoadTagsList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karl on 2016/11/14.
 * desc:
 */

public class TrainListFragment extends Fragment implements LoadTagsList.onLoadTagsListListener, TrainListAdapter.OnTrainListItemClickListener {

    private static final String CURRENT_PAGE_NUM = "CURRENT_PAGE_NUM";

    private static final String CURRENT_PAGE_ID = "CURRENT_PAGE_ID";

    private final int PAGE_SIZE = 8;

    private String mPageId;

    private int mPage;

    private View mView;

    private int mMaxIndex = 0;

    private int mCurrentIndex = 1;

    private LoadTagsList loadTagsList;

    private RecyclerView mRecyclerView;

    private LinearLayoutManager mLayoutManager;

    private TrainListAdapter mAdapter;

    private SwipeRefreshLayout mRefresh;

    private List<MainListBean.ContentBean> mList = new ArrayList<>();

    private boolean isRefresh;

    private boolean isLoadMore;

    private Integer[] refreshColor = {
            android.R.color.holo_green_light,
            android.R.color.holo_blue_light,
            android.R.color.holo_purple,
            android.R.color.holo_orange_light};

    private boolean isPre = false;


    public static TrainListFragment getInstance(int page,String id){
        Bundle bundle = new Bundle();
        bundle.putString(CURRENT_PAGE_ID,id);
        bundle.putInt(CURRENT_PAGE_NUM,page);
        TrainListFragment trainListFragment = new TrainListFragment();
        trainListFragment.setArguments(bundle);

        return trainListFragment;
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
        if (flag == 1){
            mCurrentIndex ++;

        } else{

            mCurrentIndex = 1;
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

        if (mAdapter == null) {
            mAdapter = new TrainListAdapter(getActivity(), mList);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.setOnTrainListItemClickListener(this);
        }

        if (flag == 0){
            mList.clear();
        }

        if (flag ==2){
            mList.clear();
            mRefresh.setRefreshing(false);
            isRefresh = false;
        }

        if (mCurrentIndex > mMaxIndex){

            if (flag == 0 ){
                if (mAdapter == null) {
                    mAdapter = new TrainListAdapter(getActivity(), mList);
                    mRecyclerView.setAdapter(mAdapter);
                }
            }

            if (flag == 1){
                isLoadMore = true;
                Toast.makeText(getActivity(), "没有更多数据了", Toast.LENGTH_SHORT).show();
            }

            if (flag ==2){
                //加载完成，不提示消息
            }

        }else if (mCurrentIndex == mMaxIndex){

            mList.addAll(list);

            if (flag == 0){

            }

            if (flag == 1){
                isLoadMore = true;
                mAdapter.setData(mList);
            }

            if (flag == 2){
                mAdapter.setData(mList);
            }

        }else {
            mList.addAll(list);

            if (flag == 0){

            }

            if (flag == 1){
                isLoadMore = false;
            }


            if (flag == 1 || flag == 2){
                mAdapter.setData(mList);
            }
        }

    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), CourseActivity.class);
        intent.putExtra("id",mList.get(position).getId());
        startActivity(intent);
    }

    /**
     * 懒加载
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
