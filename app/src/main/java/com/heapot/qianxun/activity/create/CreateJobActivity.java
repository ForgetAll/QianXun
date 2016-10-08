package com.heapot.qianxun.activity.create;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.heapot.qianxun.R;
import com.heapot.qianxun.activity.BaseActivity;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.bean.UserOrgBean;
import com.heapot.qianxun.helper.SerializableUtils;
import com.heapot.qianxun.util.CommonUtil;
import com.heapot.qianxun.util.FileUploadTask;
import com.heapot.qianxun.widget.PhotoCarmaWindow;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 15859 on 2016/10/5.
 * 创建工作发布
 */
public class CreateJobActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_back, tv_complete, tv_company1Title, tv_company1Content, tv_companyChoose;
    private ImageView iv_image, iv_company, iv_job, iv_detail, iv_describe, iv_describeChoose;
    private EditText et_title;
    private TextView tv_jobTitle, tv_jobContent, tv_jobChoose;
    private TextView tv_detailTitle, tv_detailContent, tv_detailChoose;
    private TextView tv_describeTitle, tv_describeContent;
    private RelativeLayout rl_company, rl_job, rl_detail, rl_describe;
    private int requestCode;
    private int number;
    Button show;
    ListView lv;
    List<UserOrgBean.ContentBean> persons = new ArrayList<UserOrgBean.ContentBean>();
    Context mContext;
    MyListAdapter adapter;
    List<Integer> listItemID = new ArrayList<Integer>();
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            String resutlt = (String) msg.obj;
            if (!TextUtils.isEmpty(resutlt)) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(resutlt);
                    Log.e("jsonObject", String.valueOf(jsonObject));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (jsonObject.optString("return_code").equals("success")) {
                    try {
                        JSONObject content = jsonObject.getJSONObject("content");
                        String path = content.getString("url");
                        Log.e("这是上传头像返回的路径", path);
                        CommonUtil.loadImage(iv_image, path + "", R.mipmap.imagetest);
                        //Glide.with(CustomApplication.getContext()).load(path.toString()).into(iv_image);
                    } catch (JSONException e) {
                    }
                }
                /*{
                "content": {
                    "code": "yes",
                            "tip": "上传成功",
                            "url": "http://odxpoei6h.bkt.clouddn.com/qianxun57ee46f0873f2.jpg"
                },
                "return_code": "success"
            }*/
            }
            return false;
        }
    });
    private ListView lv_jobList;
    private LinearLayout ll_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_job);
        findView();
        getData();
        initPersonData();
    }

    private void getData() {
        Object company = SerializableUtils.getSerializable(activity, ConstantsBean.USER_ORG_LIST);
        persons.addAll((Collection<? extends UserOrgBean.ContentBean>) company);
    }

    private void findView() {
        ll_list = (LinearLayout) findViewById(R.id.ll_list);
        mContext = getApplicationContext();
        show = (Button) findViewById(R.id.show);
        lv = (ListView) findViewById(R.id.lvperson);
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_complete = (TextView) findViewById(R.id.tv_complete);
        tv_back.setOnClickListener(this);
        tv_complete.setOnClickListener(this);

        iv_image = (ImageView) findViewById(R.id.iv_image);
        et_title = (EditText) findViewById(R.id.et_title);
        iv_image.setOnClickListener(this);

        rl_company = (RelativeLayout) findViewById(R.id.rl_company);
        iv_company = (ImageView) findViewById(R.id.iv_company);

        tv_companyChoose = (TextView) findViewById(R.id.tv_companyChoose);
        rl_company.setOnClickListener(this);


        rl_describe = (RelativeLayout) findViewById(R.id.rl_describe);
        iv_describe = (ImageView) findViewById(R.id.iv_describe);
        tv_describeTitle = (TextView) findViewById(R.id.tv_describeTitle);
        tv_describeContent = (TextView) findViewById(R.id.tv_describeContent);
        iv_describeChoose = (ImageView) findViewById(R.id.iv_describeChoose);
        rl_describe.setOnClickListener(this);



        adapter = new MyListAdapter(persons);
        lv.setAdapter(adapter);
        show.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                listItemID.clear();
                for (int i = 0; i < adapter.mChecked.size(); i++) {
                    if (adapter.mChecked.get(i)) {
                        listItemID.add(i);
                    }
                }
                number = listItemID.size();

                if (listItemID.size() == 0) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(CreateJobActivity.this);
                    builder1.setMessage("没有选中任何记录");
                    builder1.show();
                } else {
                    StringBuilder sb = new StringBuilder();

                    for (int i = 0; i < listItemID.size(); i++) {
                        sb.append("ItemID=" + listItemID.get(i) + " . ");
                    }
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(CreateJobActivity.this);
                    builder2.setMessage(sb.toString());
                    builder2.show();
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_complete:

                break;
            case R.id.iv_image:
                PhotoCarmaWindow bottomPopup = new PhotoCarmaWindow(CreateJobActivity.this);
                bottomPopup.showPopupWindow();
                break;

            case R.id.rl_company:
                if (persons.size() > 2) {
                    Intent more = new Intent(activity, CreateJobMoreList.class);
                    startActivity(more);
                } else {
                    ll_list.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.rl_detail:

                break;
            case R.id.rl_describe:
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        //先判断返回结果码
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                //相机请求码
                case ConstantsBean.CARMA_RESULT_CODE:
                    Uri uri = Uri.fromFile(new File(ConstantsBean.HEAD_IMAGE_PATH));
                    cropImage(uri);
                    //yasuo(uri);
                    break;
                //裁剪完成
                case 102:
                    //上传图片
                    File file = new File(ConstantsBean.HEAD_IMAGE_PATH);
                    if (file.exists()) {
                        Log.e("file", file.getAbsolutePath());
                    }
                    uploadImageFile(file);
                    break;
                //相册返回
                case 103:
                    Uri uri1 = intent.getData();
                    cropImage(uri1);
                    break;

            }
        }
    }

    //裁剪图片
    private void cropImage(Uri uri) {
        Uri outuri = Uri.fromFile(new File(ConstantsBean.HEAD_IMAGE_PATH));
        if (uri == null) {
            Log.e("uri:", uri + "");
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 90);
        intent.putExtra("outputY", 90);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outuri);
        intent.putExtra("scale", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(intent, 102);
    }

    //上传头像
    private void uploadImageFile(File file) {
        FileUploadTask task = new FileUploadTask(this, handler, file);
        task.execute(ConstantsBean.UPLOAD);
    }


    /**
     * 模拟数据
     */
    private void initPersonData() {
        UserOrgBean.ContentBean mPerson;
        for (int i = 1; i <= 12; i++) {
            mPerson = new UserOrgBean.ContentBean();
            mPerson.setOrgId("Andy" + i);
            mPerson.setUserId("GuangZhou" + i);
            persons.add(mPerson);
        }
    }

    //自定义ListView适配器
    class MyListAdapter extends BaseAdapter {
        List<Boolean> mChecked;
        List<UserOrgBean.ContentBean> listPerson;
        HashMap<Integer, View> map = new HashMap<Integer, View>();

        public MyListAdapter(List<UserOrgBean.ContentBean> list) {
            listPerson = new ArrayList<UserOrgBean.ContentBean>();
            listPerson = list;

            mChecked = new ArrayList<Boolean>();
            for (int i = 0; i < list.size(); i++) {
                mChecked.add(false);
            }
        }

        @Override
        public int getCount() {
            return listPerson.size();
        }

        @Override
        public Object getItem(int position) {
            return listPerson.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            ViewHolder holder = null;

            if (map.get(position) == null) {
                Log.e("MainActivity", "position1 = " + position);

                LayoutInflater mInflater = (LayoutInflater) mContext
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = mInflater.inflate(R.layout.create_job_list_item, null);
                holder = new ViewHolder();
                holder.selected = (CheckBox) view.findViewById(R.id.list_select);
                holder.name = (TextView) view.findViewById(R.id.list_name);
                holder.address = (TextView) view.findViewById(R.id.list_address);
                final int p = position;
                map.put(position, view);
                holder.selected.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;
                        mChecked.set(p, cb.isChecked());
                    }
                });
                view.setTag(holder);
            } else {
                Log.e("MainActivity", "position2 = " + position);
                view = map.get(position);
                holder = (ViewHolder) view.getTag();
            }

            holder.selected.setChecked(mChecked.get(position));
            holder.name.setText(listPerson.get(position).getOrgId());
            holder.address.setText(listPerson.get(position).getUserId());

            return view;
        }

    }

    static class ViewHolder {
        CheckBox selected;
        TextView name;
        TextView address;
    }


}
