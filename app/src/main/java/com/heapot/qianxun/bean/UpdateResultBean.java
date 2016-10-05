package com.heapot.qianxun.bean;

import java.io.Serializable;

/**
 * Created by 15859 on 2016/9/23.
 * 版本更新bean类
 */
public class UpdateResultBean implements Serializable{
    /**
     * appIcon : khykhk
     * appName : kyku
     * appDescribe : gkgkj
     * appUrl : hjghj
     * id : e702317d4be0dd897a3e16091a16df90
     * appDownCount : 1
     * version : 2
     * versioncode : 2.2
     */

    private ContentBean content;
    /**
     * content : {"appIcon":"khykhk","appName":"kyku","appDescribe":"gkgkj","appUrl":"hjghj","id":"e702317d4be0dd897a3e16091a16df90","appDownCount":"1","version":"2","versioncode":"2.2"}
     * return_code : success
     */

    private String return_code;

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public static class ContentBean implements Serializable{
        private String appIcon;
        private String appName;
        private String appDescribe;
        private String appUrl;
        private String id;
        private int appDownCount;
        private Integer version;
        private String versioncode;

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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getAppDownCount() {
            return appDownCount;
        }

        public void setAppDownCount(int appDownCount) {
            this.appDownCount = appDownCount;
        }

        public Integer getVersion() {
            return version;
        }

        public void setVersion(Integer version) {
            this.version = version;
        }

        public String getVersioncode() {
            return versioncode;
        }

        public void setVersioncode(String versioncode) {
            this.versioncode = versioncode;
        }
    }

   /* {
        "content": {
        "appIcon": "khykhk",
                "appName": "kyku",
                "appDescribe": "gkgkj",
                "appUrl": "hjghj",
                "id": "e702317d4be0dd897a3e16091a16df90",
                "appDownCount": "1",
                "version": "2",
                "versioncode": "2.2"
    },
        "return_code": "success"
    }*/

}
