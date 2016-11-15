package com.heapot.qianxun.view;

/**
 * Created by Karl on 2016/11/9.
 * desc: 统一管理主页的方法
 * 1、 获取app登陆的token
 * 2、 获取聊天的token
 */

public interface BaseView {

    String getAppToken();

    String getChatToken();
}
