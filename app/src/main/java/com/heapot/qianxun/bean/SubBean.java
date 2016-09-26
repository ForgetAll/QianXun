package com.heapot.qianxun.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Karl on 2016/9/8.
 * 已订阅列表,由于后台数据坑太多，所以我另外创建一个类，用于存干净数据
 * 存储后台已订阅数据，已经这些数据在所有数据中对应的位置
 *
 */
public class SubBean implements Serializable{
    private String name;

    private String id;

    private int subscribeStatus;

    private int position;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSubscribeStatus() {
        return subscribeStatus;
    }

    public void setSubscribeStatus(int subscribeStatus) {
        this.subscribeStatus = subscribeStatus;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
