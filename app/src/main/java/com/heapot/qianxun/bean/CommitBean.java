package com.heapot.qianxun.bean;

/**
 * Created by Karl on 2016/11/21.
 *
 */

public class CommitBean {

    /**
     * status : success
     * message :
     * content : {"userId":"f535188fed1f42cbba12e39960bb62ed","refId":null,"articleId":"9516d2fb8fec48c7a8121ed78fa5d18d","content":"呵呵哒","id":"87194348b598453589c97354a469c53a","createTime":1479719114432,"status":{"index":1,"name":"正常"}}
     */

    private String status;
    private String message;
    /**
     * userId : f535188fed1f42cbba12e39960bb62ed
     * refId : null
     * articleId : 9516d2fb8fec48c7a8121ed78fa5d18d
     * content : 呵呵哒
     * id : 87194348b598453589c97354a469c53a
     * createTime : 1479719114432
     * status : {"index":1,"name":"正常"}
     */

    private ContentBean content;

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

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
        private String userId;
        private Object refId;
        private String articleId;
        private String content;
        private String id;
        private long createTime;
        /**
         * index : 1
         * name : 正常
         */

        private StatusBean status;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public Object getRefId() {
            return refId;
        }

        public void setRefId(Object refId) {
            this.refId = refId;
        }

        public String getArticleId() {
            return articleId;
        }

        public void setArticleId(String articleId) {
            this.articleId = articleId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public StatusBean getStatus() {
            return status;
        }

        public void setStatus(StatusBean status) {
            this.status = status;
        }

        public static class StatusBean {
            private int index;
            private String name;

            public int getIndex() {
                return index;
            }

            public void setIndex(int index) {
                this.index = index;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
