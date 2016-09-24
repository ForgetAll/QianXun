package com.heapot.qianxun.bean;

import java.io.Serializable;

/**
 * Created by 15859 on 2016/9/23.
 * 软件信息的实体类
 */
public class AppBean implements Serializable{
    private String appIcon;

    private String appName;

    private String appDescribe;

    private String appUrl;

    private String appStar;

    private String appType;

    private String appClassify;

    private String appCovers;

    private String userId;
    private Integer id;

    private Integer appDownCount;

    private Double appSize;

    private String tags;

    private Integer version;

    private String code;

    private String time;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAppDownCount(Integer appDownCount) {
        this.appDownCount = appDownCount;
    }

    public void setAppSize(Double appSize) {
        this.appSize = appSize;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(String appIcon) {
        this.appIcon = appIcon;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppDescribe() {
        return appDescribe;
    }

    public void setAppDescribe(String appDescribe) {
        this.appDescribe = appDescribe;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public String getAppStar() {
        return appStar;
    }

    public void setAppStar(String appStar) {
        this.appStar = appStar;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getAppClassify() {
        return appClassify;
    }

    public void setAppClassify(String appClassify) {
        this.appClassify = appClassify;
    }

    public String getAppCovers() {
        return appCovers;
    }

    public void setAppCovers(String appCovers) {
        this.appCovers = appCovers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAppDownCount() {
        return appDownCount;
    }

    public void setAppDownCount(int appDownCount) {
        this.appDownCount = appDownCount;
    }

    public double getAppSize() {
        return appSize;
    }

    public void setAppSize(double appSize) {
        this.appSize = appSize;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
