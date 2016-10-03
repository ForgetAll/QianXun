package com.heapot.qianxun.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.heapot.qianxun.R;
import com.heapot.qianxun.util.PreferenceUtil;

/**
 * Created by 15859 on 2016/8/31.
 */
public class PersonalFirstFragment extends Fragment {
    public static final String PAGE = "PAGE";
    private TextView mNumber;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.personal_first_center,container,false);
      mNumber=(TextView) mView.findViewById(R.id.tv_phoneNumber);
        loadData();

        return mView;
    }
    private void loadData(){
       mNumber.setText(PreferenceUtil.getString("phone"));
    }


}
