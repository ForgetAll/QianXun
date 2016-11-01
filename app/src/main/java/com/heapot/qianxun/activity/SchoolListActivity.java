package com.heapot.qianxun.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.heapot.qianxun.R;
import com.heapot.qianxun.adapter.SchoolListAdapter;
import com.heapot.qianxun.bean.ConstantsBean;

/**
 * Created by 15859 on 2016/10/28.
 */
public class SchoolListActivity extends BaseActivity implements View.OnClickListener {
    private TextView txt_title;
    private ListView lv_typeList;
    private ImageView iv_btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_job_tybe_list);
        intView();
        setData();
    }


    private void intView() {
        txt_title=(TextView)    findViewById(R.id.txt_title);
        txt_title.setText("请选择学校");
        lv_typeList=(ListView)    findViewById(R.id.lv_typeList);
        iv_btn_back=(ImageView)    findViewById(R.id.iv_btn_back);
        iv_btn_back.setOnClickListener(this);
    }

    private void setData() {
final String [] school={"郑州大学","河南工业","河南农大","华北水利水电大学","郑州轻工","郑州航空工业","黄河科技","中原工学院","河南中医学院","河南财经政法","郑州城市职业学院","新乡学院","开封文化艺术职业学院","长垣博大烹饪职业技术学院","河南理工大学","高等职业学院","郑州成功财经学院","安阳职业技术学院","安阳职业技术学院医药卫生学院","安阳护理职业学院","安阳幼儿师范高等专科学校","长城铝业公司职工工学院","长垣烹饪职业技术学院","河南护理职业学院","河南化工职业学院","河南机电职业学院","河南推拿职业学院","河南艺术职业学院","鹤壁汽车工程职业学院","焦作工贸职业学院","焦作职工医学院","开封空分设备厂职工大学","洛阳有色金属职工大学","洛阳职业技术学院","洛阳轴承职工大学","漯河食品职业学院","磨料磨具工业职工大学","南阳职业学院","商丘工学院","新乡职业技术学院","信阳涉外职业技术学院","许昌电气职业学院","许昌陶瓷职业学院","郑州黄河护理职业学院","郑州理工职业学院","郑州商贸旅游职业学院","郑州市职工大学","郑州信息工程职业学院","郑州幼儿师范高等专科学校","驻马店教育学院","驻马店职业技术学院","河南大学","河南科技大学林业职业学院","郑州财经学院","郑州职业技术学院","河南科技大学","洛阳师院","安阳工学院","安阳师范学院","南阳理工","南阳师院","河南城建学院","平顶山学院","新乡医学院","河南科技学院","河南师大","信阳师院","商丘师院","河南工商学院","黄淮学院","许昌学院","河南理工","河南财政税务高等专科学校","河南工程学院","河南工业贸易职业学院","河南工业职业技术学院","河南警察学院","河南广播影视学院","河南机电高等专科学校","河南检察职业学院","河南交通职业技术学院","河南教育学院","河南经贸职业学院","河南农业职业学院","河南省工商行政管理广播电视大学","河南司法警官职业学院","河南新华电脑学院","河南职业技术学院","河南质量工程职业学院","鹤壁职业技术学院","黄河水利职业技术学院","济源职业技术学院","焦作大学","焦作师范高等专科学校","开封大学","开封市电子科技专修学校","洛阳大学","洛阳理工学院","漯河医学高等专科学校","漯河职业技术学院","南阳医学高等专科学校","平顶山教育学院","平顶山工业职业技术学院","濮阳职业技术学院","三门峡职业技术学院","商丘科技职业学院","商丘医学高等专科学校","商丘职业技术学院","嵩山少林武术职业学院","铁道警察学院","信阳农林学院","信阳职业技术学院","许昌职业技术学院","永城职业学院","郑州大学西亚斯国际学院","郑州电力高等专科学校","郑州电子信息职业技术学院","郑州工业安全职业学院","郑州华信学院","郑州交通学院","郑州科技职业学院","郑州旅游职业学院","河南牧业经济学院","郑州师范学院","郑州澍青医学高等专科学校","郑州铁路职业技术学院","郑州信息科技职业学院","郑州科技学院","中州大学","周口职业技术学院","郑州升达经贸管理学院","洛阳工业高等专科学校","河南职工医学院","河南科技学院新科学院","河南理工大学万方科技学院","中原工学院信息商务学院","安阳师范学院","人文管理学院","商丘学院","开封教育学院","河南卫生职工学院","河南建筑职业技术学院","河南大学民生学院","河南师范大学新联学院","新乡医学院三全学院","信阳师范学院华锐学院","郑州电力职业技术学院","周口科技职业学院","河南省广播电视大学","中国人民解放军外国语学院","清华IT河南校区第一拖拉机制造厂拖拉机学院","郑州煤炭管理干部学院","河南工业大学化学工业职业学院","河南省轻工业职工大学","郑州交通职业学院","郑州牧业高等专科学校","郑州布瑞达理工职业学院","河南医科大学教育中心","郑州轻工业轻工职业学院"};
   SchoolListAdapter schoolListAdapter=new SchoolListAdapter(this,school);
        lv_typeList.setAdapter(schoolListAdapter);
        lv_typeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             String schoolName=   school[position];
                Intent schoolIntent=new Intent();
                schoolIntent.putExtra(ConstantsBean.INFO,schoolName);
                setResult(110, schoolIntent);
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
