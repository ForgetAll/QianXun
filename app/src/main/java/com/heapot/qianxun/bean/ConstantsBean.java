package com.heapot.qianxun.bean;

import android.os.Environment;

import com.heapot.qianxun.application.CustomApplication;

/**
 * Created by 15859 on 2016/9/3.
 *
 *
 */
public class ConstantsBean {

    //是否进入引导界面
    public static final String KEY_SPLASH = "isEnter";

    //基本路径
    public static final String BASE_PATH = "http://qianxun.heapot.com/";
    //    public static final String BASE_PATH = "https://qinxi1992.xicp.net/";
    public static final String BASE_PHP_PATH = "http://114.215.252.158/";


    public static final String CONFIG_NAME = "config";

    //相机拍照请求码
    public static final int CARMA_RESULT_CODE = 101;
    public static final String HEAD_IMAGE_PATH = Environment.getExternalStorageDirectory() + "/headImage.jpg";

    //下载地址常量
    public static final String APP_UEL = "appUrl";
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
    //查看已订阅
    public static final String GET_SUBSCRIBED = "user/subscribe/catalog";
    //提交订阅
    public static final String POST_SUBSCRIPTION = "user/subscribe/catalog?catalogId=";
    //根据分类取消订阅
    public static final String CANCEL_SUBSCRIPTION = "user/subscribe/catalog?catalogId=";
    //根据文章ID获取文章详情GET: /articles/{articlesId}
    public static final String ARTICLE_DETAIL = "/articles/";
    //获取用户个人信息
    public static final String PERSONAL_INFO = "user/i";
    //个人发表的内容
    public static final String PERSONAL_ARTICLE = "user/detail";
    //全文检索 GET: /articles/search?searchStr={searchStr}
    public static final String ARTICLES_SEARCH = "/articles/search?searchStr=";
    //修改个人信息put
    public static final String PERSONAL_FIX = "user/i";
    //验证原密码
    public static final String CHECK_PWD = "/user/checkPwd?oldPassword=";
    //修改密码
    public static final String UPDATE_PWD = "/user/updatePwd?oldPassword=";
    //文件上传接口
    public static final String UPLOAD = "http://114.215.252.158/qiniu/upload.php";
    //版本更新
    public static final String UPDATE_VERSION = "http://114.215.252.158/qianxun_article/index/apk_version/get_version.api.php";
    //获取指定标签下的列表
//    public static final String GET_LIST_WITH_TAG = "http://114.215.252.158/qianxun_article/index/article/view_tag_article.api.php?";
    //测试接口
    public static final String GET_LIST_WITH_TAG = BASE_PHP_PATH+"/qianxun_article/index/article/view_tag_article.api.php?";

    public static final String SET_BANNER_MAIN = BASE_PHP_PATH+"/qianxun_article/index/view_tag_super_article.api.php?";
    //添加评论
    public static final String ADD_COMMENT = "comments";
    //创建文章
    public static final String CREATE_ARTICLES = "articles";
    //创建工作
    public static final String CREATE_JOB = "jobs";
    //查询员工所属公司
    public static final String QUERY_UER_ORG = "user/org";
    //工作类型的接口
    public static final String CREATE_JOB_TYPE = "https://qinxi1992.xicp.net/catalogs/jobs";
    //用户详情，查询get，修改post
    public static final String USER_DETAIL =  "/user/detail";
    //教育信息
    public static final String USER_EDUCATION =  "/user/education";
    //文章分类
    public static final String CREATE_ARTICLE_TYPE = "/catalogs/articles";


    //创建文章WEbView的URL
    public static final String WEB_CREATE_ARTICLE_EDIT = "http://sijiache.heapot.com/Tabs/editer/artical/?f?device=android&author=";
    //创建招聘URL
    public static final String WEB_CREATE_JOB_EDIT = "http://sijiache.heapot.com/Tabs/editer/artical/?f?device=android&type=job";

    //以下聊天相关
    public static final String IM_TOKEN = "user/rongCloudToken";
    public static final String IM_CHECK_FRIEND = "friends/check?friendId";//={friendId}//验证是否是好友
    public static final String IM_GET_FRIEND_LIST = "friends";//获取好友列表
    public static final String IM_GET_FRIENDS_REQUEST_LIST = "friends/req";//获取好友请求列表
    public static final String IM_POST_ADD_FRIENDS_REQUEST = "friends/req";//发送好友请求
    public static final String IM_POST_ADMIT_ADD_FRIEND = "friends/req/";//{userId}同意好友请求
    //根据id去查用户信息
    public static final String IM_USER_INFO = "user/id/";

    //简历,获取简历列表GET，创建简历POST，修改个人简历PUT
    public static final String RESUME_INFO = "user/resume";

    //查看个人简历内容详情
    public static final String RESUME_UPDATE = "user/resume/";









    /*
     * 常量类key用于跳转传值
     */
    public static final String LOGINNAME = "loginName";
    public static final String USER_PHONE = "phone";
    public static final String USER_PASS = "password";
    public static final String USER_ID = "id";
    public static final String salt = "salt";
    public static final String email = "email";
    public static final String loginTime = "createTime";
    public static final String userImage = "icon";
    public static final String name = "name";
    public static final String nickName = "nickname";
    public static final String showname = "showname";
    public static String INFO = "info";
    public static final int EDUCATION_NUMBER = 0;
    //签名目前未定
    public static final String userAutograph = "description";


    //本地存储文件名
    public static final String TAG_FILE_NAME = "TAG_FILE_NAME";//存储所有标签的文件名
    public static final String MY_USER_INFO = "userInfo";//本地个人信息文件名
    public static final String USER_ORG_LIST = "UserOrg";//用户所属公司列表
    public static final String USER_ORG_INFO = "OrgInfo";//用户所属公司详情

    //本地存储好友列表
    public static final String IM_FRIEND = "IM_FRIEND";



}
