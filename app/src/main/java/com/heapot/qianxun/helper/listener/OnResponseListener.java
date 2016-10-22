package com.heapot.qianxun.helper.listener;

import com.heapot.qianxun.bean.Friend;

/**
 * Created by Karl on 2016/10/21.
 * desc: 网络请求回调
 *
 */

public interface OnResponseListener {

    void responseSuccess(Friend friend);
    void responseError();

}
