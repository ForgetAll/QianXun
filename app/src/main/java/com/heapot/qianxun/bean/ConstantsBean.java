package com.heapot.qianxun.bean;

import android.os.Environment;

/**
 * Created by 15859 on 2016/9/3.
 */
public class ConstantsBean {
    //是否进入引导界面
    public static final String KEY_SPLASH = "isEnter";
    //基本路径-已改
    public static final String BASE_PATH = "https://qinxi1992.xicp.net/";
    //退出登录接口-仟询
    public  static  final  String  LOGOUT="/admin/logout";
    //个人信息接口-仟徇
    public  static  final  String  PERSONAL_INFO="/admin/i";
    //获取当前用户权限接口-仟询
     public  static  final  String  ADMIN_PERMISSSIONS="/admin/permissions";
    //检测旧密码接口-仟询
     public  static  final  String  ADMIN_CHECKPWD="/admin/checkPwd";
    //修改密码接口-仟询
      static  final  String  ADMIN_UPDATEPWD="/admin/updatePwdpublic";
    //更新
    public static final String UPDATE = "/user/update";
    //文件上传
    public static final String UPLOAD = "/file/upload";
    //查询软件(版本更新)
    public static final String APP_SELECT = "/app/select";
    //图片基本路径
    public static final String BASE_IMAGE_PATH = "http://122.114.85.104:8080/images/classes/res/images/";

    public static final String CONFIG_NAME = "config";
    public static String INFO="info";
    //相机拍照请求码
    public static final int CARMA_RESULT_CODE = 101;
    public static final String HEAD_IMAGE_PATH = Environment.getExternalStorageDirectory() + "/headImage.jpg";

    //关于我们
    public static final String ABOUT_US = "http://122.114.85.104:8080/images/classes/res/html/about.html";
    public static final String APP_UEL = "appUrl";
    public static final String TITLE = "title";
    public static final String POSITION = "position";
    /**
     * url地址
     */
    //验证Token的请求头的key
    public static final String KEY_TOKEN = "x-auth-token";
    //验证管理员权限的
    public static final String KEY_REQUEST = "X-Requested-With";
    public static final String VALUE_REQUEST = "XMLHttpRequest";
    //OrgCode
    public static final String ORG_CODE = "qianxun/";
    //登陆
    public static final String LOGIN = "login";
    //注册
    public static final String REGISTER = "register";
    //获取分类
    public static final String CATALOGS = "catalogs";
    //验证账户名的唯一性
    public static final String CHECK_LOGIN_NAME = "checkLoginName?loginName=";
    //发送验证码
    public static final String SEND_MESSAGE = "registerSMS?phone=";
    //重置密码
    public static final String RESET_PASSWORD = "resetPassword";
    //找回密码手机号
    public static final String SEND_RESET_MESSAGE = "findPasswordByPhone?phone=";
    //订阅分类
    public static final String SUBSCRIBE_CATALOGS = "user/subscribe/catalog";





    /*
     * 常量类key用于跳转传值
     */
    public static final String LOGINNAME = "loginName";
    public static final String USER_PHONE = "phone";
    public static final String USER_PASS = "password";
    public static final String USER_ID = "id";
    public static final String salt="salt";
    public static final String email="email";
    public static final String loginTime="createTime";
    public static final String userImage = "icon";
    public static final String name = "name";
    public static final String nickName = "nickname";
    //签名目前未定
    public static final String userAutograph = "userAutograph";

    public static final String PAGE_SCIENCE = "PAGE_SCIENCE";
    public static final String PAGE_RECRUIT = "PAGE_RECRUIT";
    public static final String PAGE_TRAIN = "PAGE_TRAIN";

    //标签订阅本地文件名
    public static final String TAG_FILE_NAME = "Subscription";//存储所有标签的文件名


}
