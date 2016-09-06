package com.heapot.qianxun.bean;

import android.os.Environment;

/**
 * Created by 15859 on 2016/9/3.
 */
public class ConstantsBean {
    //基本路径
    public static final String BASE_PATH = "http://122.114.85.104:8080/classes";
    //文件上传
    public static final String UPLOAD = "/file/upload";
    //图片基本路径
    public static final String BASE_IMAGE_PATH = "http://122.114.85.104:8080/images/classes/res/images/";

    public static final String CONFIG_NAME = "config";
    public static String INFO="info";
    //相机拍照请求码
    public static final int CARMA_RESULT_CODE = 101;
    public static final String HEAD_IMAGE_PATH = Environment.getExternalStorageDirectory() + "/headImage.jpg";

    /*
       常量类key用于跳转传值
        */
    public static final String KEY_LOOK_BEAN = "lookBean";
    public static final String USER_ID = "userId";
    public static final String USER_PHONE = "userPhone";
    public static final String USER_PASS = "userPassword";
    public static final String userImage = "userImage";
    public static final String userName = "userName";
    public static final String userNick = "userNick";
    public static final String userSex = "userSex";
    public static final String userBirth = "userBirth";
    public static final String userAutograph = "userAutograph";
    public static final String userProvince = "userProvince";
    public static final String userCity = "userCity";
    public static final String userArea = "userArea";
    public static final String userAddress = "userAddress";
    public static final String userLove = "userLove";
    public static final String userIntegral = "userIntegral";
    public static final String userLevel = "userLevel";
    public static final String userQq = "userQq";
    public static final String userWx = "userWx";
    public static final String userSina = "userSina";
    public static final String userSchool = "userSchool";
    public static final String pushId = "pushId";
    public static final String token = "token";
    public static final String userLatitude = "userLatitude";
    public static final String userLongitude = "userLongitude";

}
