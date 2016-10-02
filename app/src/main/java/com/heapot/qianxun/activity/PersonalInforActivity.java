package com.heapot.qianxun.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.heapot.qianxun.R;
import com.heapot.qianxun.application.CustomApplication;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.bean.MyUserBean;
import com.heapot.qianxun.bean.UploadBean;
import com.heapot.qianxun.util.CommonUtil;
import com.heapot.qianxun.util.FileUploadTask;
import com.heapot.qianxun.util.JsonUtil;
import com.heapot.qianxun.util.PreferenceUtil;
import com.heapot.qianxun.util.ToastUtil;
import com.heapot.qianxun.widget.PhotoCarmaWindow;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.File;

/**
 * Created by 15859 on 2016/8/29.
 * 用户个人信息界面
 */
public class PersonalInforActivity extends BaseActivity implements View.OnClickListener {
    public String userId;
    private TextView mBack;
    private ImageView mHead;
    private TextView mNick;
    private TextView mAutograph;
    private RelativeLayout rl_sex;
    private int requestCode;
    private String nick;
    private String autograph;
    private RelativeLayout mBottomSet;
    private TextView mCarmaView,mPhotoView;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            String resutlt = (String) msg.obj;
            if (!TextUtils.isEmpty(resutlt)) {
                /*{
                "content": {
                    "code": "yes",
                            "tip": "上传成功",
                            "url": "http://odxpoei6h.bkt.clouddn.com/qianxun57ee46f0873f2.jpg"
                },
                "return_code": "success"
            }*/
                UploadBean uploadBean = (UploadBean) JsonUtil.fromJson(String.valueOf(resutlt),UploadBean.class);
           String return_code= uploadBean.getReturn_code();
                if (return_code.equals("success")) {
                    String path = uploadBean.getContent().getUrl();
                    CommonUtil.loadImage(mHead, path, R.mipmap.imagetest);
                    //修改用户头像路径
                    updateUserInfo(ConstantsBean.userImage, path);
                }
            }
            return false;
        }
    });

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_main_information);
        setTransparentBar();
        initView();
        initEvent();


    }
//查找控件
    private void initView() {
       mBack=(TextView) findViewById(R.id.tv_back);
        mHead = (ImageView) findViewById(R.id.iv_head);
        mNick = (TextView) findViewById(R.id.tv_nick);
        mAutograph = (TextView) findViewById(R.id.tv_autograph);
        findViewById(R.id.rl_autograph).setOnClickListener(this);
        findViewById(R.id.rl_nick).setOnClickListener(this);
        mPhotoView = (TextView) findViewById(R.id.tv_girl);
        mPhotoView.setOnClickListener(this);
        mCarmaView = (TextView)findViewById(R.id.tv_boy);
        mCarmaView.setOnClickListener(this);
       mBottomSet=(RelativeLayout) findViewById(R.id.rl_bottomSet);
        mBottomSet.setOnClickListener(this);
        mHead.setOnClickListener(this);
        mBack.setOnClickListener(this);
    }

    private void initEvent() {
        nick = PreferenceUtil.getString(ConstantsBean.nickName);
        autograph = PreferenceUtil.getString(ConstantsBean.userAutograph);
        mNick.setText(nick);
        mAutograph.setText(autograph);
        String imagePath = PreferenceUtil.getString(ConstantsBean.userImage);
        CommonUtil.loadImage(mHead, imagePath, R.mipmap.imagetest);
    }
//toolbar
    private void setTransparentBar() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
    }
//点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()){
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
                jumpAlterActivity(nick);
                break;
            //签名
            case R.id.rl_autograph:
                requestCode = 202;
                jumpAlterActivity(autograph);
                break;

        }
    }
   //修改头像
    private void setHeadImage() {
        PhotoCarmaWindow bottomPopup = new PhotoCarmaWindow(PersonalInforActivity.this);
        bottomPopup.showPopupWindow();
    }
    /**
     * 更新用户信息
     *
     * @param key  更新的字段
     * @param info 具体信息
     */
    private void updateUserInfo(final String key, final String info) {

        MyUserBean.ContentBean userBean=new MyUserBean.ContentBean();
        userBean.setId(userId);
        switch (requestCode) {
            //昵称
            case 201:
                userBean.setNickname(info);
                break;
            //签名
            case 202:
                userBean.setDescription(info);
                break;
            //头像
            case 203:
                userBean.setIcon(info);
                break;

        }
        String data = JsonUtil.toJson(userBean);
        //发送数据
        Ion.with(this).load(ConstantsBean.UPLOAD).setHeader(ConstantsBean.KEY_TOKEN, CustomApplication.TOKEN).setStringBody(data).asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {
                if (!TextUtils.isEmpty(result)) {
                    if (result.contains("success")) {
                        switch (requestCode) {
                            //昵称
                            case 201:
                                PreferenceUtil.putString(key, info);
                                mNick.setText(info);
                                break;
                            //签名
                            case 202:
                                PreferenceUtil.putString(key, info);
                                mAutograph.setText(info);
                            //头像
                            case 203:
                                PreferenceUtil.putString(key, info);
                                break;

                        }
                        ToastUtil.show("信息修改成功");
                    } else {
                        ToastUtil.show("信息修改失败");
                    }
                } else {
                    ToastUtil.show("信息修改失败");
                }
            }
        });
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
                    updateUserInfo(ConstantsBean.nickName, nick);
                    break;
                //修改签名
                case 202:
                    autograph = intent.getStringExtra(ConstantsBean.INFO);
                    updateUserInfo(ConstantsBean.userAutograph, autograph);
                    break;
            }
        }
    }

}

