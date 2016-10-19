package com.heapot.qianxun.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 15859 on 2016/10/11.
 */
public class MyPersonalArticle implements Serializable{
    /**
     * status : success
     * message :
     * content : {"total":3,"hasNext":false,"rows":[{"id":"57fc9a4a11058a799eb30233","title":"嗨","subtitle":null,"images":null,"summary":null,"hits":1,"catalog":{"pid":"f3b8d91b8f9c4a03a4a06a5678e79872","name":"法律","code":"tech","left":38,"right":39,"description":"我就是红线","creatorId":"3312cfb5794742dc8b9f98544fdb6854","catalog":{"name":"文章","code":"articles","left":1,"right":94,"creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"f3b8d91b8f9c4a03a4a06a5678e79872","createTime":1475220231708,"status":{"index":1,"name":"正常"}},"id":"c8bbd11d9ccf46a787930ea12c3843e0","createTime":1475656967680,"status":{"index":1,"name":"正常"}}},{"id":"57fc9a4b11058a799eb30234","title":"嗨","subtitle":null,"images":null,"summary":null,"hits":1,"catalog":{"pid":"f3b8d91b8f9c4a03a4a06a5678e79872","name":"法律","code":"tech","left":38,"right":39,"description":"我就是红线","creatorId":"3312cfb5794742dc8b9f98544fdb6854","catalog":{"name":"文章","code":"articles","left":1,"right":94,"creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"f3b8d91b8f9c4a03a4a06a5678e79872","createTime":1475220231708,"status":{"index":1,"name":"正常"}},"id":"c8bbd11d9ccf46a787930ea12c3843e0","createTime":1475656967680,"status":{"index":1,"name":"正常"}}},{"id":"57fcadf911058a799eb30235","title":"嗨","subtitle":null,"images":"http://odxpoei6h.bkt.clouddn.com/qianxun57fcadee2865f.jpg","summary":null,"hits":1,"catalog":{"pid":"f3b8d91b8f9c4a03a4a06a5678e79872","name":"体育","code":"tech","left":36,"right":37,"description":"其实我头脑也很发达","creatorId":"3312cfb5794742dc8b9f98544fdb6854","catalog":{"name":"文章","code":"articles","left":1,"right":94,"creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"f3b8d91b8f9c4a03a4a06a5678e79872","createTime":1475220231708,"status":{"index":1,"name":"正常"}},"id":"c5295a8998884d9f89035d28652d9444","createTime":1475656851190,"status":{"index":1,"name":"正常"}}}],"hasPre":false}
     */

    private String status;
    private String message;
    /**
     * total : 3
     * hasNext : false
     * rows : [{"id":"57fc9a4a11058a799eb30233","title":"嗨","subtitle":null,"images":null,"summary":null,"hits":1,"catalog":{"pid":"f3b8d91b8f9c4a03a4a06a5678e79872","name":"法律","code":"tech","left":38,"right":39,"description":"我就是红线","creatorId":"3312cfb5794742dc8b9f98544fdb6854","catalog":{"name":"文章","code":"articles","left":1,"right":94,"creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"f3b8d91b8f9c4a03a4a06a5678e79872","createTime":1475220231708,"status":{"index":1,"name":"正常"}},"id":"c8bbd11d9ccf46a787930ea12c3843e0","createTime":1475656967680,"status":{"index":1,"name":"正常"}}},{"id":"57fc9a4b11058a799eb30234","title":"嗨","subtitle":null,"images":null,"summary":null,"hits":1,"catalog":{"pid":"f3b8d91b8f9c4a03a4a06a5678e79872","name":"法律","code":"tech","left":38,"right":39,"description":"我就是红线","creatorId":"3312cfb5794742dc8b9f98544fdb6854","catalog":{"name":"文章","code":"articles","left":1,"right":94,"creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"f3b8d91b8f9c4a03a4a06a5678e79872","createTime":1475220231708,"status":{"index":1,"name":"正常"}},"id":"c8bbd11d9ccf46a787930ea12c3843e0","createTime":1475656967680,"status":{"index":1,"name":"正常"}}},{"id":"57fcadf911058a799eb30235","title":"嗨","subtitle":null,"images":"http://odxpoei6h.bkt.clouddn.com/qianxun57fcadee2865f.jpg","summary":null,"hits":1,"catalog":{"pid":"f3b8d91b8f9c4a03a4a06a5678e79872","name":"体育","code":"tech","left":36,"right":37,"description":"其实我头脑也很发达","creatorId":"3312cfb5794742dc8b9f98544fdb6854","catalog":{"name":"文章","code":"articles","left":1,"right":94,"creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"f3b8d91b8f9c4a03a4a06a5678e79872","createTime":1475220231708,"status":{"index":1,"name":"正常"}},"id":"c5295a8998884d9f89035d28652d9444","createTime":1475656851190,"status":{"index":1,"name":"正常"}}}]
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

    public static class ContentBean implements Serializable{
        private int total;
        private boolean hasNext;
        private boolean hasPre;
        /**
         * id : 57fc9a4a11058a799eb30233
         * title : 嗨
         * subtitle : null
         * images : null
         * summary : null
         * hits : 1
         * catalog : {"pid":"f3b8d91b8f9c4a03a4a06a5678e79872","name":"法律","code":"tech","left":38,"right":39,"description":"我就是红线","creatorId":"3312cfb5794742dc8b9f98544fdb6854","catalog":{"name":"文章","code":"articles","left":1,"right":94,"creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"f3b8d91b8f9c4a03a4a06a5678e79872","createTime":1475220231708,"status":{"index":1,"name":"正常"}},"id":"c8bbd11d9ccf46a787930ea12c3843e0","createTime":1475656967680,"status":{"index":1,"name":"正常"}}
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
            private String id;
            private String title;
            private Object subtitle;
            private String images;
            private Object summary;
            private int hits;
            /**
             * pid : f3b8d91b8f9c4a03a4a06a5678e79872
             * name : 法律
             * code : tech
             * left : 38
             * right : 39
             * description : 我就是红线
             * creatorId : 3312cfb5794742dc8b9f98544fdb6854
             * catalog : {"name":"文章","code":"articles","left":1,"right":94,"creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"f3b8d91b8f9c4a03a4a06a5678e79872","createTime":1475220231708,"status":{"index":1,"name":"正常"}}
             * id : c8bbd11d9ccf46a787930ea12c3843e0
             * createTime : 1475656967680
             * status : {"index":1,"name":"正常"}
             */

            private CatalogBean catalog;

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

            public Object getSubtitle() {
                return subtitle;
            }

            public void setSubtitle(Object subtitle) {
                this.subtitle = subtitle;
            }

            public String getImages() {
                return images;
            }

            public void setImages(String images) {
                this.images = images;
            }

            public Object getSummary() {
                return summary;
            }

            public void setSummary(Object summary) {
                this.summary = summary;
            }

            public int getHits() {
                return hits;
            }

            public void setHits(int hits) {
                this.hits = hits;
            }

            public CatalogBean getCatalog() {
                return catalog;
            }

            public void setCatalog(CatalogBean catalog) {
                this.catalog = catalog;
            }

            public static class CatalogBean {
                private String pid;
                private String name;
                private String code;
                private int left;
                private int right;
                private String description;
                private String creatorId;
                /**
                 * name : 文章
                 * code : articles
                 * left : 1
                 * right : 94
                 * creatorId : 3312cfb5794742dc8b9f98544fdb6854
                 * id : f3b8d91b8f9c4a03a4a06a5678e79872
                 * createTime : 1475220231708
                 * status : {"index":1,"name":"正常"}
                 */

                private com.heapot.qianxun.bean.CatalogBean catalog;
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

                public com.heapot.qianxun.bean.CatalogBean getCatalog() {
                    return catalog;
                }

                public void setCatalog(com.heapot.qianxun.bean.CatalogBean catalog) {
                    this.catalog = catalog;
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
}
