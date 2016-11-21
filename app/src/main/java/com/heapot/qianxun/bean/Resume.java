package com.heapot.qianxun.bean;

import java.util.List;

/**
 * Created by Karl on 2016/11/21.
 */

public class Resume {

    /**
     * status : success
     * message :
     * content : {"total":1,"hasNext":false,"rows":[{"userId":"f535188fed1f42cbba12e39960bb62ed","resume":" My first Resume","updateTime":null,"id":"141302fecde64280a18372cdaa8c0d48","createTime":1479695550698,"status":{"index":1,"name":"正常"}}],"hasPre":false}
     */

    private String status;
    private String message;
    /**
     * total : 1
     * hasNext : false
     * rows : [{"userId":"f535188fed1f42cbba12e39960bb62ed","resume":" My first Resume","updateTime":null,"id":"141302fecde64280a18372cdaa8c0d48","createTime":1479695550698,"status":{"index":1,"name":"正常"}}]
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

    public static class ContentBean {
        private int total;
        private boolean hasNext;
        private boolean hasPre;
        /**
         * userId : f535188fed1f42cbba12e39960bb62ed
         * resume :  My first Resume
         * updateTime : null
         * id : 141302fecde64280a18372cdaa8c0d48
         * createTime : 1479695550698
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

        public static class RowsBean {
            private String userId;
            private String resume;
            private Object updateTime;
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

            public String getResume() {
                return resume;
            }

            public void setResume(String resume) {
                this.resume = resume;
            }

            public Object getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(Object updateTime) {
                this.updateTime = updateTime;
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
