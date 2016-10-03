package com.heapot.qianxun.bean;

import java.io.Serializable;

/**
 * Created by 15859 on 2016/10/2.
 */
public class UploadBean implements Serializable{

    /**
     * code : yes
     * tip : 上传成功
     * url : http://odxpoei6h.bkt.clouddn.com/qianxun57ee46f0873f2.jpg
     */

    private ContentBean content;
    /**
     * content : {"code":"yes","tip":"上传成功","url":"http://odxpoei6h.bkt.clouddn.com/qianxun57ee46f0873f2.jpg"}
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

    public static class ContentBean {
        private String code;
        private String tip;
        private String url;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getTip() {
            return tip;
        }

        public void setTip(String tip) {
            this.tip = tip;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
