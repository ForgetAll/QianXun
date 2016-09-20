package com.heapot.qianxun.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Karl on 2016/9/19.
 * 标签订阅列表
 */
public class SubscriptionBean implements Serializable {

    /**
     * status : success
     * message :
     * content : [{"id":"704d54f44d7845f8a30322c9923a0d19","pid":null,"name":"新闻","code":"news","ascription":"专属分类","description":null},{"id":"e504595ef4be4d4c821649978172b647","pid":"704d54f44d7845f8a30322c9923a0d19","name":"游戏","code":"games","ascription":"专属分类","description":null}]
     */

    private String status;
    private String message;
    /**
     * id : 704d54f44d7845f8a30322c9923a0d19
     * pid : null
     * name : 新闻
     * code : news
     * ascription : 专属分类
     * description : null
     */

    private List<ContentBean> content;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean implements Serializable {
        private String id;
        private Object pid;
        private String name;
        private String code;
        private String ascription;
        private Object description;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Object getPid() {
            return pid;
        }

        public void setPid(Object pid) {
            this.pid = pid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getAscription() {
            return ascription;
        }

        public void setAscription(String ascription) {
            this.ascription = ascription;
        }

        public Object getDescription() {
            return description;
        }

        public void setDescription(Object description) {
            this.description = description;
        }
    }
}
