package com.heapot.qianxun.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.heapot.qianxun.R;

/**
 * Created by Karl on 2016/8/20.
 */
public class ScienceFragment extends Fragment {
    private TextView textView;
    private View mScienceView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mScienceView = inflater.inflate(R.layout.fragment_layout,container,false);
        textView = (TextView) mScienceView.findViewById(R.id.fragment);
        textView.setText("ScienceFragment");

        return mScienceView;

    }
}
