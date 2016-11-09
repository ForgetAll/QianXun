package com.heapot.qianxun.activity.create;

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
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.heapot.qianxun.R;
import com.heapot.qianxun.activity.BaseActivity;
import com.heapot.qianxun.activity.JobActivity;
import com.heapot.qianxun.application.CustomApplication;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.bean.CreateJobBean;
import com.heapot.qianxun.bean.CreateJobResultBean;
import com.heapot.qianxun.bean.UserOrgBean;
import com.heapot.qianxun.util.CommonUtil;
import com.heapot.qianxun.util.FileUploadTask;
import com.heapot.qianxun.util.JsonUtil;
import com.heapot.qianxun.util.PreferenceUtil;
import com.heapot.qianxun.util.ToastUtil;
import com.heapot.qianxun.widget.PhotoCarmaWindow;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 15859 on 2016/10/5.
 * 创建工作发布
 */
public class CreateJobActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_back, tv_complete, tv_company1Title, tv_company1Content, tv_companyChoose;
    private ImageView iv_image, iv_company, iv_detail, iv_describe, iv_describeChoose, iv_sum;
    private EditText et_title, et_sumTitle;
    private TextView tv_detailTitle, tv_detailContent, tv_detailChoose;
    private TextView tv_describeTitle, tv_describeContent;
    private RelativeLayout rl_company, rl_detail, rl_describe;
    private int requestCode;

    Context mContext;
    private String path;
    private List<UserOrgBean.ContentBean> orgList = new ArrayList<>();
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
                        path = content.getString("url");
                        Log.e("这是上传头像返回的路径", path);
                        CommonUtil.loadImage(iv_image, path + "", R.mipmap.imagetest);
                        //Glide.with(CustomApplication.getContext()).load(path.toString()).into(iv_image);
                    } catch (JSONException e) {
                    }
                }
            }
            return false;
        }
    });
    private ListView lv_jobList;
    private LinearLayout ll_list;
    private EditText et_min, et_max;
    private ImageView iv_max, iv_min;
    private String catalogId;
    private String jobName;
    private String describe;
    private String title;
    private TextView tv_companyTitle;
    private int length;
    private String min;
    private String max;
    private String describeTitle;
    private String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_job);
        testData();
        findView();

    }


    private void findView() {
        ll_list = (LinearLayout) findViewById(R.id.ll_list);
        mContext = getApplicationContext();
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_complete = (TextView) findViewById(R.id.tv_complete);
        tv_back.setOnClickListener(this);
        tv_complete.setOnClickListener(this);


        iv_image = (ImageView) findViewById(R.id.iv_image);
        et_title = (EditText) findViewById(R.id.et_title);
        iv_image.setOnClickListener(this);

        rl_company = (RelativeLayout) findViewById(R.id.rl_company);
        iv_company = (ImageView) findViewById(R.id.iv_company);
        Glide.with(this).load(PreferenceUtil.getString(ConstantsBean.userImage)).error(R.mipmap.imagetest).into(iv_company);
        tv_companyTitle = (TextView) findViewById(R.id.tv_companyTitle);
        String orgName = PreferenceUtil.getString("orgName");
        tv_companyTitle.setText(orgName);
        tv_companyChoose = (TextView) findViewById(R.id.tv_companyChoose);
        rl_company.setOnClickListener(this);

        et_min = (EditText) findViewById(R.id.tv_minTitle);
        iv_max = (ImageView) findViewById(R.id.iv_max);
        Glide.with(this).load(PreferenceUtil.getString(ConstantsBean.userImage)).error(R.mipmap.imagetest).into(iv_max);
        iv_min = (ImageView) findViewById(R.id.iv_min);
        et_max = (EditText) findViewById(R.id.tv_maxTitle);
        Glide.with(this).load(PreferenceUtil.getString(ConstantsBean.userImage)).error(R.mipmap.imagetest).into(iv_min);

        rl_detail = (RelativeLayout) findViewById(R.id.rl_detail);
        iv_detail = (ImageView) findViewById(R.id.iv_detail);
        tv_detailTitle = (TextView) findViewById(R.id.tv_detailTitle);
        rl_detail.setOnClickListener(this);
        Glide.with(this).load(PreferenceUtil.getString(ConstantsBean.userImage)).error(R.mipmap.imagetest).into(iv_detail);

        iv_sum = (ImageView) findViewById(R.id.iv_sum);
        et_sumTitle = (EditText) findViewById(R.id.tv_sumTitle);
        Glide.with(this).load(PreferenceUtil.getString(ConstantsBean.userImage)).error(R.mipmap.imagetest).into(iv_sum);


        rl_describe = (RelativeLayout) findViewById(R.id.rl_describe);
        iv_describe = (ImageView) findViewById(R.id.iv_describe);
        tv_describeTitle = (TextView) findViewById(R.id.tv_describeTitle);
        tv_describeContent = (TextView) findViewById(R.id.tv_describeContent);
        iv_describeChoose = (ImageView) findViewById(R.id.iv_describeChoose);
        Glide.with(this).load(PreferenceUtil.getString(ConstantsBean.userImage)).error(R.mipmap.imagetest).into(iv_describe);
        rl_describe.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_complete:
                if (catalogId != null) {
                    sendData();
                } else {
                    ToastUtil.show("请检查输入的内容");
                }


                break;
            case R.id.iv_image:
                PhotoCarmaWindow bottomPopup = new PhotoCarmaWindow(CreateJobActivity.this);
                bottomPopup.showPopupWindow();
                break;

            case R.id.rl_company:
                break;
            case R.id.rl_detail:
                requestCode = 106;
                Intent type = new Intent(CreateJobActivity.this, CreateJobTypeActivity.class);
                startActivityForResult(type, requestCode);
                break;
            case R.id.rl_describe:
                requestCode = 105;
                describeTitle = tv_describeTitle.getText().toString().trim();
                Intent intent = new Intent(CreateJobActivity.this, CreateJobDescribe.class);
                intent.putExtra("describeTitle", describeTitle);
                startActivityForResult(intent, requestCode);
                break;
        }

    }

    private void sendData() {
        CreateJobBean createJobBean = new CreateJobBean();
        min = et_min.getText().toString().trim();
        Log.e("最低工资", min);
        max = et_max.getText().toString().trim();
        createJobBean.setCatalogId(catalogId);
        String orgCode = PreferenceUtil.getString("orgCode");
        createJobBean.setCode(orgCode);
        createJobBean.setDescription(describe);
        createJobBean.setEmail("");
        title = et_title.getText().toString().trim();
        length = title.length();
        createJobBean.setName(title);
        createJobBean.setPhone(PreferenceUtil.getString("orgPhone"));
        number = et_sumTitle.getText().toString().trim();
        createJobBean.setImg(path);
        if (!TextUtils.isEmpty(number) && !TextUtils.isEmpty(min) && !TextUtils.isEmpty(max)&&catalogId != null && title != null && length >= 2 && describe != null) {
            createJobBean.setMinSalary(Integer.parseInt(min));
            createJobBean.setMaxSalary(Integer.parseInt(max));
            createJobBean.setNum(Integer.parseInt(number));
            String data = JsonUtil.toJson(createJobBean);
            Log.e("发送的数据：", data);
            ToastUtil.show("请稍等");
            upData(data);
        } else {
            Toast.makeText(CreateJobActivity.this, "请检查输入的内容", Toast.LENGTH_SHORT).show();
        }

    }

    private void upData(String data) {
        JSONObject json = null;
        try {
            json = new JSONObject(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
            String url = ConstantsBean.BASE_PATH + ConstantsBean.CREATE_JOB;
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST, url, json,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Logger.d(response);
                            try {
                                String status = response.getString("status");
                                if (status.equals("success")) {

                                    CreateJobResultBean createJobResultBean = (CreateJobResultBean) JsonUtil.fromJson(String.valueOf(response), CreateJobResultBean.class);
                                    String newJobId = createJobResultBean.getContent().getId();
                                    Toast.makeText(CreateJobActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
                                    Intent job = new Intent(CreateJobActivity.this, JobActivity.class);
                                    job.putExtra("id",createJobResultBean.getContent().getId());
                                    startActivity(job);
                                    finish();
                                } else {
                                    Toast.makeText(CreateJobActivity.this, "发布失败", Toast.LENGTH_SHORT).show();
                                    Logger.d(response.getString("message"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }
            ) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put(ConstantsBean.KEY_TOKEN, getAppToken());
                    return headers;
                }
            };
            CustomApplication.getRequestQueue().add(jsonObjectRequest);


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
                case 104:
                    break;
                case 105:
                    describe = intent.getStringExtra("describe");
                    Log.e("describe", describe);
                    tv_describeTitle.setText(describe);
                    break;
                case 106:
                    catalogId = intent.getStringExtra("catalogId");
                    jobName = intent.getStringExtra("jobName");
                    tv_detailTitle.setText(jobName);
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
        intent.putExtra("aspectX", 4);
        intent.putExtra("aspectY", 3);
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
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

    //获取数据

    private void testData() {
        String url = ConstantsBean.BASE_PATH + ConstantsBean.QUERY_UER_ORG;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Logger.json(String.valueOf(response));
                        Log.e("所有的Json数据：", response.toString());
                        try {
                            String status = response.getString("status");
                            if (status.equals("success")) {
                                UserOrgBean userOrgBean = (UserOrgBean) JsonUtil.fromJson(String.valueOf(response), UserOrgBean.class);
                                //SerializableUtils.setSerializable(CreateActivity.this,ConstantsBean.USER_ORG_LIST,userOrgBean);
                                orgList.addAll(userOrgBean.getContent());
                                //根据ID查询公司名字
                                orgList.get(0).getOrgId();
                                Logger.d("orgst------->" + orgList.size());
                            } else {
                                Toast.makeText(CreateJobActivity.this, "" + response.getString("message"), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put(ConstantsBean.KEY_TOKEN, getAppToken());
                return headers;
            }
        };
        CustomApplication.getRequestQueue().add(jsonObjectRequest);
    }
}
