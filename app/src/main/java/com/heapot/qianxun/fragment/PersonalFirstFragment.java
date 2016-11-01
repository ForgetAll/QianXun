package com.heapot.qianxun.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.heapot.qianxun.R;
import com.heapot.qianxun.activity.SchoolListActivity;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.util.PreferenceUtil;

/**
 * Created by 15859 on 2016/8/31.
 */
public class PersonalFirstFragment extends Fragment implements View.OnClickListener {
    public static final String PAGE = "PAGE";
    private TextView mNumber;
    private TextView tv_school;
    private int requestCode;
    private String schoolName;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.personal_first_center,container,false);
      mNumber=(TextView) mView.findViewById(R.id.tv_phoneNumber);
       mView.findViewById(R.id.rl_school).setOnClickListener(this);
        tv_school=(TextView)mView.findViewById(R.id.tv_school);
        loadData();

        return mView;
    }
    private void loadData(){
       mNumber.setText(PreferenceUtil.getString("phone"));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //学校
            case  R.id.rl_school:
                requestCode=111;
                Intent school=new Intent(getContext(),SchoolListActivity.class);
                startActivityForResult(school,requestCode);
                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==110){
            switch (requestCode){
                case 111:
                    schoolName=data.getStringExtra(ConstantsBean.INFO);
                    tv_school.setText(schoolName);
                    break;
            }
        }
    }
}
