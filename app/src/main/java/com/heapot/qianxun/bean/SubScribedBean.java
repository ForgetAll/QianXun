package com.heapot.qianxun.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Karl on 2016/9/28.
 */
public class SubScribedBean implements Serializable{

    /**
     * status : success
     * message :
     * content : {"total":1,"hasNext":false,"rows":[{"name":"新闻","code":"news","left":1,"right":4,"creatorId":"6e07bc2797fb44e488e9b88fec42199e","id":"704d54f44d7845f8a30322c9923a0d19","createTime":1473675919136,"status":{"index":1,"name":"正常"}}],"hasPre":false}
     */

    private String status;
    private String message;
    /**
     * total : 1
     * hasNext : false
     * rows : [{"name":"新闻","code":"news","left":1,"right":4,"creatorId":"6e07bc2797fb44e488e9b88fec42199e","id":"704d54f44d7845f8a30322c9923a0d19","createTime":1473675919136,"status":{"index":1,"name":"正常"}}]
     * hasPre : false
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

    public static class ContentBean implements Serializable {
        private int total;
        private boolean hasNext;
        private boolean hasPre;
        /**
         * name : 新闻
         * code : news
         * left : 1
         * right : 4
         * creatorId : 6e07bc2797fb44e488e9b88fec42199e
         * id : 704d54f44d7845f8a30322c9923a0d19
         * createTime : 1473675919136
         * status : {"index":1,"name":"正常"}
         */

        private List<RowsBean> rows;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public boolean isHasNext() {
            return hasNext;
        }

        public void setHasNext(boolean hasNext) {
            this.hasNext = hasNext;
        }

        public boolean isHasPre() {
            return hasPre;
        }

        public void setHasPre(boolean hasPre) {
            this.hasPre = hasPre;
        }

        public List<RowsBean> getRows() {
            return rows;
        }

        public void setRows(List<RowsBean> rows) {
            this.rows = rows;
        }

        public static class RowsBean implements Serializable{
            private String name;
            private String code;
            private int left;
            private int right;
            private String creatorId;
            private String id;
            private long createTime;
            /**
             * index : 1
             * name : 正常
             */

            private StatusBean status;

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

            public int getLeft() {
                return left;
            }

            public void setLeft(int left) {
                this.left = left;
            }

            public int getRight() {
                return right;
            }

            public void setRight(int right) {
                this.right = right;
            }

            public String getCreatorId() {
                return creatorId;
            }

            public void setCreatorId(String creatorId) {
                this.creatorId = creatorId;
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
}
