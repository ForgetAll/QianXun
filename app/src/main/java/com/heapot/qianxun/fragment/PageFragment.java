package com.heapot.qianxun.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.heapot.qianxun.R;

/**
 * Created by Karl on 2016/8/25.
 */
public class PageFragment extends Fragment {
    public static final String PAGE = "PAGE";
    private int mPage;

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
        View pageView = inflater.inflate(R.layout.fragment_layout,container,false);
        TextView textView = (TextView) pageView.findViewById(R.id.txt_fragment);
        Log.d("TAG","打印了！");
        textView.setText("Fragment # "+mPage);
        textView.setTextSize(mPage+5);

        return pageView;
    }
}
