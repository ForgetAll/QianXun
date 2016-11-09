package com.heapot.qianxun.bean;

import java.util.List;

/**
 * Created by Karl on 2016/11/9.
 * desc: 已订阅标签
 */

public class MyTagBean {

    /**
     * status : success
     * message :
     * content : {"total":4,"hasNext":false,"rows":[{"pid":"f3b8d91b8f9c4a03a4a06a5678e79872","name":"美术","code":"fine arts","left":44,"right":45,"description":"梵高爱上毕加索","creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"846617ca70f246cda326dc94be5fe50a","createTime":1476325521855,"status":{"index":1,"name":"正常"}},{"pid":"f3b8d91b8f9c4a03a4a06a5678e79872","name":"交通","code":" traffic","left":16,"right":17,"description":"真·老司机","creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"9c443bbd3087433386051d8e2e16844d","createTime":1476324458261,"status":{"index":1,"name":"正常"}},{"pid":"f3b8d91b8f9c4a03a4a06a5678e79872","name":"制造业","code":" manufacturing","left":14,"right":15,"description":"Made in China","creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"9be628c1949f424b83d9563aaf076abe","createTime":1476324391731,"status":{"index":1,"name":"正常"}},{"pid":"f3b8d91b8f9c4a03a4a06a5678e79872","name":"传媒","code":"media","left":8,"right":9,"description":"镜头中最美的你","creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"19e353c0a783403bbb60f706fa3e2c7b","createTime":1476323916817,"status":{"index":1,"name":"正常"}}],"hasPre":false}
     */

    private String status;
    private String message;
    /**
     * total : 4
     * hasNext : false
     * rows : [{"pid":"f3b8d91b8f9c4a03a4a06a5678e79872","name":"美术","code":"fine arts","left":44,"right":45,"description":"梵高爱上毕加索","creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"846617ca70f246cda326dc94be5fe50a","createTime":1476325521855,"status":{"index":1,"name":"正常"}},{"pid":"f3b8d91b8f9c4a03a4a06a5678e79872","name":"交通","code":" traffic","left":16,"right":17,"description":"真·老司机","creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"9c443bbd3087433386051d8e2e16844d","createTime":1476324458261,"status":{"index":1,"name":"正常"}},{"pid":"f3b8d91b8f9c4a03a4a06a5678e79872","name":"制造业","code":" manufacturing","left":14,"right":15,"description":"Made in China","creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"9be628c1949f424b83d9563aaf076abe","createTime":1476324391731,"status":{"index":1,"name":"正常"}},{"pid":"f3b8d91b8f9c4a03a4a06a5678e79872","name":"传媒","code":"media","left":8,"right":9,"description":"镜头中最美的你","creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"19e353c0a783403bbb60f706fa3e2c7b","createTime":1476323916817,"status":{"index":1,"name":"正常"}}]
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
         * pid : f3b8d91b8f9c4a03a4a06a5678e79872
         * name : 美术
         * code : fine arts
         * left : 44
         * right : 45
         * description : 梵高爱上毕加索
         * creatorId : 3312cfb5794742dc8b9f98544fdb6854
         * id : 846617ca70f246cda326dc94be5fe50a
         * createTime : 1476325521855
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
            private String pid;
            private String name;
            private String code;
            private int left;
            private int right;
            private String description;
            private String creatorId;
            private String id;
            private long createTime;
            /**
             * index : 1
             * name : 正常
             */

            private StatusBean status;

            public String getPid() {
                return pid;
            }

            public void setPid(String pid) {
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

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
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
