package com.heapot.qianxun.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.heapot.qianxun.R;
import com.heapot.qianxun.application.CustomApplication;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.bean.DetailPostBean;
import com.heapot.qianxun.bean.MyDetailBean;
import com.heapot.qianxun.bean.MyUserBean;
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
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 15859 on 2016/8/29.
 * 用户个人信息界面
 */
public class PersonalInforActivity extends BaseActivity implements View.OnClickListener {
    public String userId;
    private TextView mBack;
    private ImageView mHeadUrl;
    private TextView mName;
    private TextView mSign;
    private int requestCode;
    private String nick;
    private String autograph;
    private int personalStatus = 0;
    private MyUserBean.ContentBean userBean;
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
                        // Glide.with(activity).load(path).error(R.mipmap.imagetest).into(mHead);
                        CommonUtil.loadImage(mHeadUrl, path, R.mipmap.imagetest);
                        userBean.setIcon(path);
                        personalStatus = 1;
                        PreferenceUtil.putString(ConstantsBean.userImage, path);
                        String body3 = "{\"icon\":\"" + path + "\"}";
                        updateUserInfo(body3);
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
    private TextView tv_birth, tv_sex, tv_man, tv_woman, tv_extra;
    private AlertDialog alertDialogSex;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_main_information);
        initView();
        initEvent();
        getDetail();
    }

    //查找控件
    private void initView() {
        mBack = (TextView) findViewById(R.id.tv_back);
        mHeadUrl = (ImageView) findViewById(R.id.iv_head);
        mName = (TextView) findViewById(R.id.tv_nick);
        mSign = (TextView) findViewById(R.id.tv_autograph);
        findViewById(R.id.rl_autograph).setOnClickListener(this);
        findViewById(R.id.rl_nick).setOnClickListener(this);
        findViewById(R.id.rl_birth).setOnClickListener(this);
        findViewById(R.id.rl_sex).setOnClickListener(this);
        tv_birth = (TextView) findViewById(R.id.tv_birth);
        tv_sex = (TextView) findViewById(R.id.tv_sex);
        mHeadUrl.setOnClickListener(this);
        mBack.setOnClickListener(this);

    }

    private void getDetail() {
        String url = ConstantsBean.BASE_PATH + ConstantsBean.USER_DETAIL;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Logger.json(String.valueOf(response));
                        Log.e("所有的Json数据：", String.valueOf(response));
                        try {
                            String status = response.getString("status");
                            Log.e("content：", String.valueOf(response.getJSONObject("content")));
                            if (status.equals("success")&&response.getJSONObject("content") != null) {
                                MyDetailBean myDetailBean = (MyDetailBean) JsonUtil.fromJson(String.valueOf(response), MyDetailBean.class);
                                PreferenceUtil.putString("sex", String.valueOf(myDetailBean.getContent().getSex()));
                                PreferenceUtil.putString("detaildescribe", myDetailBean.getContent().getDescription());
                                PreferenceUtil.putString("birthyear", String.valueOf(myDetailBean.getContent().getBirthYear()));
                                int birthYear = myDetailBean.getContent().getBirthYear();
                                if (birthYear == 1) {
                                    tv_birth.setText("");
                                } else {
                                    tv_birth.setText(String.valueOf(birthYear));
                                }

                                int sexNumber = myDetailBean.getContent().getSex();
                                if (sexNumber == 0) {
                                    tv_sex.setText("女");
                                } else if (sexNumber == 1) {
                                    tv_sex.setText("男");
                                } else {
                                    tv_sex.setText("其他");
                                }

                            }else {

                            }




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ToastUtil.show("失败了。。。。。。。。。。");
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


    private void initEvent() {
        String nickName= PreferenceUtil.getString(ConstantsBean.nickName);
        String autoGraph=PreferenceUtil.getString(ConstantsBean.userAutograph);
        String headUrl=PreferenceUtil.getString(ConstantsBean.userImage);
        if (nickName != null) {
            mName.setText(nickName);
        } else {
            mName.setText("请设置昵称");
        }
        if (autoGraph != null) {
            mSign.setText(autoGraph);
        }  else {
            mSign.setText("请设置签名");
        }
        if (headUrl != null) {
            CommonUtil.loadImage(mHeadUrl,headUrl, R.drawable.imagetest);
        } else {
            mHeadUrl.setImageResource(R.drawable.imagetest);
        }

    }



    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回
            case R.id.tv_back:
                finish();
                break;
            //头像
            case R.id.iv_head:
                setHeadImage();
                break;
            //名字
            case R.id.rl_nick:
                requestCode = 201;
                jumpAlterActivity(mName.getText().toString().trim());
                break;
            //签名
            case R.id.rl_autograph:
                requestCode = 202;
                jumpAlterActivity(mSign.getText().toString().trim());
                break;
            //生日
            case R.id.rl_birth:
                requestCode = 203;
                Intent birth = new Intent(PersonalInforActivity.this, BirthActivity.class);
                startActivityForResult(birth, requestCode);
                break;
            //性别
            case R.id.rl_sex:
                personalSexDialog();
                break;
            //男
            case R.id.tv_man:
                tv_sex.setText("男");
                DetailPostBean detailMan = new DetailPostBean();
                String birthy = PreferenceUtil.getString("birthyear");
                if (birthy==null){
                    birthy="1";
                }
                detailMan.setBirthYear(Integer.parseInt(birthy));
                PreferenceUtil.putString("sex", String.valueOf(1));
                detailMan.setSex(1);
                detailMan.setDescription("仟询");
                String body4 = JsonUtil.toJson(detailMan);
                alertDialogSex.dismiss();
                upDataInfo(body4);
                Log.e("上传的数据", body4);
                break;
            //女
            case R.id.tv_woman:
                tv_sex.setText("女");
                DetailPostBean detailWoman = new DetailPostBean();
                String birthye = PreferenceUtil.getString("birthyear");
                if (birthye==null){
                    birthye="1";
                }
                detailWoman.setBirthYear(Integer.parseInt(birthye));
                PreferenceUtil.putString("sex", String.valueOf(0));
                detailWoman.setSex(0);
                detailWoman.setDescription("仟询");
                String body5 = JsonUtil.toJson(detailWoman);
                alertDialogSex.dismiss();
                upDataInfo(body5);
                Log.e("上传的数据", body5);
                break;
            //其他
            case R.id.tv_extra:
                tv_sex.setText("其他");
                DetailPostBean detailExtra = new DetailPostBean();
                String birthyea = PreferenceUtil.getString("birthyear");
                if (birthyea==null){
                    birthyea="1";
                }
                detailExtra.setBirthYear(Integer.parseInt(birthyea));
                PreferenceUtil.putString("sex", String.valueOf(2));
                detailExtra.setSex(2);
                detailExtra.setDescription("仟询");
                String body6 = JsonUtil.toJson(detailExtra);
                alertDialogSex.dismiss();
                upDataInfo(body6);
                Log.e("上传的数据", body6);
                break;

        }
    }

    //增加性别栏目的对话框
    private void personalSexDialog() {
        AlertDialog.Builder builderSex = new AlertDialog.Builder(PersonalInforActivity.this);
        View viewAdd = LayoutInflater.from(PersonalInforActivity.this).inflate(R.layout.personal_sex_dialog, null);
        tv_man = (TextView) viewAdd.findViewById(R.id.tv_man);
        tv_woman = (TextView) viewAdd.findViewById(R.id.tv_woman);
        tv_extra = (TextView) viewAdd.findViewById(R.id.tv_extra);
        tv_man.setOnClickListener(this);
        tv_woman.setOnClickListener(this);
        tv_extra.setOnClickListener(this);
        builderSex.setView(viewAdd);
        alertDialogSex = builderSex.show();
    }

    private void sendBroadcast() {
        //发送广播
        Intent intent = new Intent("com.personal.change");
        intent.putExtra("personalStatus", personalStatus);
        switch (personalStatus) {
            case 0://没有更新不需要处理
                break;
            case 1:
                personalStatus = 0;
                break;
        }
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.sendBroadcast(intent);//发送本地广播
    }

    //修改头像
    private void setHeadImage() {
        PhotoCarmaWindow bottomPopup = new PhotoCarmaWindow(PersonalInforActivity.this);
        bottomPopup.showPopupWindow();
    }

    /**
     * 更新用户信息
     */
    private void updateUserInfo(String body) {
        Log.e("新建的内容", String.valueOf(userBean));
        // String body = "{\"name\":\"" + userBean.getName() + "\",\"nickname\":\"" + userBean.getNickname() + "\",\"icon\":\"" +userBean.getIcon() + "\",\"description\":\"" + userBean.getDescription() + "\"}";
        //发送数据
        JSONObject json = null;
        try {
            json = new JSONObject(body);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("上传的个人信息数据", body);
        String url = ConstantsBean.BASE_PATH + ConstantsBean.PERSONAL_FIX;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.PUT, url, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("上传后返回的数据：response", response.toString());
                        Logger.json(String.valueOf(response));
                        try {
                            String status = response.getString("status");
                            if (status.equals("success")) {

                                sendBroadcast();
                                //发送成功

                            } else {
                                Toast.makeText(PersonalInforActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
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
                Map<String, String> map = new HashMap<>();
                map.put(ConstantsBean.KEY_TOKEN, getAppToken());
                return map;
            }
        };
        CustomApplication.getRequestQueue().add(jsonObjectRequest);
    }

    //上传头像
    private void uploadImageFile(File file) {
        FileUploadTask task = new FileUploadTask(this, handler, file);
        task.execute(ConstantsBean.UPLOAD);
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


    private void jumpAlterActivity(String info) {
        Intent intent = new Intent(PersonalInforActivity.this, PersonInfoAlterActivity.class);
        intent.putExtra(ConstantsBean.INFO, info);
        startActivityForResult(intent, requestCode);
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
                    // yasuo(uri1);
                    break;
                //修改昵称
                case 201:
                    nick = intent.getStringExtra(ConstantsBean.INFO);
                    mName.setText(nick);
                    userBean.setNickname(nick);
                    PreferenceUtil.putString(ConstantsBean.nickName, nick);
                    personalStatus = 1;
                    String body4 = "{\"nickname\":\"" + nick + "\"}";
                    Log.e("修改后的名字", body4);
                    updateUserInfo(body4);
                    break;
                //修改签名
                case 202:
                    autograph = intent.getStringExtra(ConstantsBean.INFO);
                    mSign.setText(autograph);
                    userBean.setDescription(autograph);
                    PreferenceUtil.putString(ConstantsBean.userAutograph, autograph);
                    personalStatus = 1;
                    String body2 = "{\"description\":\"" + autograph + "\"}";
                    updateUserInfo(body2);
                    break;
                //修改出生年
                case 203:
                    int birth = intent.getIntExtra(ConstantsBean.INFO, 1992);
                    PreferenceUtil.putString("birthyear", String.valueOf(birth));
                    Log.e("birthyear第一", String.valueOf(birth));
                    tv_birth.setText(String.valueOf(birth));
                  /*  DetailPostBean detailPostBean = new DetailPostBean();
                    detailPostBean.setBirthYear(birth);
                    Log.e("birth", String.valueOf(birth));
                    String sexNum=PreferenceUtil.getString("sex");
                    detailPostBean.setSex(Integer.parseInt(sexNum));
                    Log.e("sex",PreferenceUtil.getString("sex"));
                    detailPostBean.setDescription("仟询");
                    String body3 = JsonUtil.toJson(detailPostBean);*/
                    String sexNumb = PreferenceUtil.getString("sex");
                    if (sexNumb==null){
                        sexNumb="2";
                    }
                    Log.e("sexNumb", sexNumb);
                    String descri = "仟询";
                    String body3 = "{\"sex\":\"" + sexNumb + "\",\"birthYear\":\"" + birth + "\",\"description\":\"" + descri + "\"}";
                    Log.e("上传的数据", body3);
                    upDataInfo(body3);
                    break;
            }
        }
    }

    private void upDataInfo(String data) {
        JSONObject json = null;
        try {
            json = new JSONObject(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = ConstantsBean.BASE_PATH + ConstantsBean.USER_DETAIL;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, url, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Logger.d(response);
                        try {
                            String status = response.getString("status");
                            if (status.equals("success")) {
                                Toast.makeText(PersonalInforActivity.this, "更新成功", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(PersonalInforActivity.this, "更新失败", Toast.LENGTH_SHORT).show();
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

}

