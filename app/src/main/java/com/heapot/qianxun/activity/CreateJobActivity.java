package com.heapot.qianxun.activity;

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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.heapot.qianxun.R;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.util.CommonUtil;
import com.heapot.qianxun.util.FileUploadTask;
import com.heapot.qianxun.widget.PhotoCarmaWindow;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by 15859 on 2016/10/5.
 * 创建工作发布
 */
public class CreateJobActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_back,tv_complete,tv_company1Title,tv_company1Content,tv_companyChoose;
    private ImageView iv_image,iv_company,iv_job,iv_detail,iv_describe,iv_describeChoose;
    private EditText et_title;
    private TextView tv_jobTitle,tv_jobContent,tv_jobChoose;
    private TextView tv_detailTitle,tv_detailContent,tv_detailChoose;
    private TextView tv_describeTitle,tv_describeContent;
    private RelativeLayout rl_company,rl_job,rl_detail,rl_describe;
    private int requestCode;
private int size;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            String resutlt = (String) msg.obj;
            Log.d("上传头像返回数据", resutlt);
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
                        Log.e("上传头像返回的数据", path);
                        CommonUtil.loadImage(iv_image, path + "", R.mipmap.imagetest);
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_job);
        findView();
    }

    private void findView() {
        tv_back=(TextView)findViewById(R.id.tv_back);
        tv_complete=(TextView)  findViewById(R.id.tv_complete);
        tv_back.setOnClickListener(this);
        tv_complete.setOnClickListener(this);

        iv_image=(ImageView)  findViewById(R.id.iv_image);
        et_title = (EditText) findViewById(R.id.et_title);
        iv_image.setOnClickListener(this);

        rl_company=(RelativeLayout)  findViewById(R.id.rl_company);
        iv_company=(ImageView) findViewById(R.id.iv_company);
        tv_company1Title=(TextView)  findViewById(R.id.tv_company1Title);
        tv_company1Content=(TextView)  findViewById(R.id.tv_company1Content);
        tv_companyChoose=(TextView)  findViewById(R.id.tv_companyChoose);
        rl_company.setOnClickListener(this);

        rl_job=(RelativeLayout)  findViewById(R.id.rl_job);
        iv_job=(ImageView) findViewById(R.id.iv_job);
        tv_jobTitle=(TextView)  findViewById(R.id.tv_jobTitle);
        tv_jobContent=(TextView)  findViewById(R.id.tv_jobContent);
        tv_jobChoose=(TextView)  findViewById(R.id.tv_jobChoose);
        rl_job.setOnClickListener(this);

        rl_detail=(RelativeLayout)  findViewById(R.id.rl_detail);
        iv_detail=(ImageView) findViewById(R.id.iv_detail);
        tv_detailTitle=(TextView)  findViewById(R.id.tv_detailTitle);
        tv_detailContent=(TextView)  findViewById(R.id.tv_detailContent);
        tv_detailChoose=(TextView)  findViewById(R.id.tv_detailChoose);
        rl_detail.setOnClickListener(this);

        rl_describe=(RelativeLayout)  findViewById(R.id.rl_describe);
        iv_describe = (ImageView) findViewById(R.id.iv_describe);
        tv_describeTitle = (TextView) findViewById(R.id.tv_describeTitle);
        tv_describeContent = (TextView) findViewById(R.id.tv_describeContent);
        iv_describeChoose = (ImageView) findViewById(R.id.iv_describeChoose);
        rl_describe.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
       switch (v.getId()){
         case   R.id.tv_back:
             finish();
           break;
           case   R.id.tv_complete:

               break;
           case   R.id.iv_image:
               PhotoCarmaWindow bottomPopup = new PhotoCarmaWindow(CreateJobActivity.this);
               bottomPopup.showPopupWindow();
               break;

           case   R.id.rl_company:
               if (size>=2){

               }else {

               }
               break;
           case   R.id.rl_job:
               break;
           case   R.id.rl_detail:

               break;
           case   R.id.rl_describe:
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
}
