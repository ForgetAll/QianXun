package com.heapot.qianxun.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Karl on 2016/10/5.
 *
 *
 */
public class MainListBean implements Serializable{

    /**
     * total_page : 1
     * content : [{"id":"57ee358211058a3b20ee9687","title":"小鸟","subtitle":"","summary":"","images":"","create_time":"1475384731"},{"id":"57ee367711058a3b20ee9689","title":"天空之城","subtitle":"","summary":"","images":"","create_time":"1475384731"},{"id":"57ee3d6d11058a3b20ee968a","title":"MVP开发之路","subtitle":"","summary":"","images":"","create_time":"1475384731"},{"id":"57f0705511058a07d158f839","title":"青石板桥的回忆","subtitle":"","summary":"","images":"","create_time":"1475384731"}]
     * return_code : success
     */

    private int total_page;
    private String return_code;
    /**
     * id : 57ee358211058a3b20ee9687
     * title : 小鸟
     * subtitle :
     * summary :
     * images :
     * create_time : 1475384731
     */

    private List<ContentBean> content;

    public int getTotal_page() {
        return total_page;
    }

    public void setTotal_page(int total_page) {
        this.total_page = total_page;
    }

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean implements Serializable{
        private String id;
        private String title;
        private String subtitle;
        private String summary;
        private String images;
        private String create_time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }
}
