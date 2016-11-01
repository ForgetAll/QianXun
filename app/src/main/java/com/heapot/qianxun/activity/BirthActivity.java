package com.heapot.qianxun.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.heapot.qianxun.R;
import com.heapot.qianxun.adapter.BirthAdapter;
import com.heapot.qianxun.bean.ConstantsBean;

/**
 * Created by 15859 on 2016/10/31.
 */
public class BirthActivity extends BaseActivity implements View.OnClickListener {
    private TextView txt_title;
    private ListView lv_typeList;
    private ImageView iv_btn_back;
    private int birthYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_job_tybe_list);
        intView();
        setData();
    }


    private void intView() {
        txt_title=(TextView)    findViewById(R.id.txt_title);
        txt_title.setText("请选择出生年");
        lv_typeList=(ListView)    findViewById(R.id.lv_typeList);
        iv_btn_back=(ImageView)    findViewById(R.id.iv_btn_back);
        iv_btn_back.setOnClickListener(this);
    }

    private void setData() {
        final int [] birth={2010,2009,2008,2007,2006,2005,2004,2003,2002,2001,2000,1999,1998,1997,1996,1995,1994,1993,1992,1991,1990,1989,1988,1987,1986,1985,1984,1983,1982,1981,1980,1979,1978,1977,1976};
        BirthAdapter birthAdapter=new BirthAdapter(this,birth);
        lv_typeList.setAdapter(birthAdapter);
        lv_typeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               birthYear=   birth[position];
                Intent birthIntent=new Intent();
                birthIntent.putExtra(ConstantsBean.INFO,birthYear);
                setResult(RESULT_OK, birthIntent);
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_btn_back:
                finish();
                break;
        }
    }
}
