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
     * id : 93e7aea33364828872efc04bec759149
     * appDownCount : 1
     * version : ykuyk
     */

    private ContentBean content;
    /**
     * content : {"appIcon":"khykhk","appName":"kyku","appDescribe":"gkgkj","appUrl":"hjghj","id":"93e7aea33364828872efc04bec759149","appDownCount":"1","version":"ykuyk"}
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

    public static class ContentBean implements Serializable {
        private String appIcon;
        private String appName;
        private String appDescribe;
        private String appUrl;
        private String id;
        private Integer appDownCount;
        private Integer version;

        public String getVersioncode() {
            return versioncode;
        }

        public void setVersioncode(String versioncode) {
            this.versioncode = versioncode;
        }

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

        public Integer getAppDownCount() {
            return appDownCount;
        }

        public void setAppDownCount(Integer appDownCount) {
            this.appDownCount = appDownCount;
        }

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }
    }
    /*{
        "content": {
        "appIcon": "khykhk",
                "appName": "kyku",
                "appDescribe": "gkgkj",
                "appUrl": "hjghj",
                "id": "93e7aea33364828872efc04bec759149",
                "appDownCount": "1",
                "version": "ykuyk"
    },
        "return_code": "success"
    }*/

}
