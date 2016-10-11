package com.heapot.qianxun.bean;

import java.util.List;

/**
 * Created by 15859 on 2016/10/11.
 */
public class MyPersonalArticle {

    /**
     * status : success
     * message :
     * content : {"total":4,"hasNext":false,"rows":[{"id":"57ee3d6d11058a3b20ee968a","title":"MVP开发之路","subtitle":null,"images":null,"summary":null,"hits":5,"catalog":{"pid":"f3b8d91b8f9c4a03a4a06a5678e79872","name":"新闻","code":"news","left":2,"right":5,"creatorId":"6e07bc2797fb44e488e9b88fec42199e","catalog":{"name":"文章","code":"articles","left":1,"right":6,"creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"f3b8d91b8f9c4a03a4a06a5678e79872","createTime":1475220231708,"status":{"index":1,"name":"正常"}},"id":"704d54f44d7845f8a30322c9923a0d19","createTime":1473675919136,"status":{"index":1,"name":"正常"}}},{"id":"57f7739c11058a3b967e6555","title":" 我的第一篇","subtitle":null,"images":null,"summary":null,"hits":12,"catalog":{"pid":"f3b8d91b8f9c4a03a4a06a5678e79872","name":"金融","code":"tech","left":26,"right":27,"description":"资本论","creatorId":"3312cfb5794742dc8b9f98544fdb6854","catalog":{"name":"文章","code":"articles","left":1,"right":94,"creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"f3b8d91b8f9c4a03a4a06a5678e79872","createTime":1475220231708,"status":{"index":1,"name":"正常"}},"id":"85e9c09ec6c740c9852f441e3fbbd272","createTime":1475656563140,"status":{"index":1,"name":"正常"}}},{"id":"57f9e94211058a4c1f819332","title":"咯兔子","subtitle":null,"images":null,"summary":null,"hits":1,"catalog":{"pid":"f3b8d91b8f9c4a03a4a06a5678e79872","name":"贸易","code":"tech","left":32,"right":33,"description":"没有我卖不出去的","creatorId":"3312cfb5794742dc8b9f98544fdb6854","catalog":{"name":"文章","code":"articles","left":1,"right":94,"creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"f3b8d91b8f9c4a03a4a06a5678e79872","createTime":1475220231708,"status":{"index":1,"name":"正常"}},"id":"fcb62ebe26a74fa4b5f028ad4924d04d","createTime":1475656747994,"status":{"index":1,"name":"正常"}}},{"id":"57f9e94311058a4c1f819333","title":"咯兔子","subtitle":null,"images":null,"summary":null,"hits":1,"catalog":{"pid":"f3b8d91b8f9c4a03a4a06a5678e79872","name":"贸易","code":"tech","left":32,"right":33,"description":"没有我卖不出去的","creatorId":"3312cfb5794742dc8b9f98544fdb6854","catalog":{"name":"文章","code":"articles","left":1,"right":94,"creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"f3b8d91b8f9c4a03a4a06a5678e79872","createTime":1475220231708,"status":{"index":1,"name":"正常"}},"id":"fcb62ebe26a74fa4b5f028ad4924d04d","createTime":1475656747994,"status":{"index":1,"name":"正常"}}}],"hasPre":false}
     */

    private String status;
    private String message;
    /**
     * total : 4
     * hasNext : false
     * rows : [{"id":"57ee3d6d11058a3b20ee968a","title":"MVP开发之路","subtitle":null,"images":null,"summary":null,"hits":5,"catalog":{"pid":"f3b8d91b8f9c4a03a4a06a5678e79872","name":"新闻","code":"news","left":2,"right":5,"creatorId":"6e07bc2797fb44e488e9b88fec42199e","catalog":{"name":"文章","code":"articles","left":1,"right":6,"creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"f3b8d91b8f9c4a03a4a06a5678e79872","createTime":1475220231708,"status":{"index":1,"name":"正常"}},"id":"704d54f44d7845f8a30322c9923a0d19","createTime":1473675919136,"status":{"index":1,"name":"正常"}}},{"id":"57f7739c11058a3b967e6555","title":" 我的第一篇","subtitle":null,"images":null,"summary":null,"hits":12,"catalog":{"pid":"f3b8d91b8f9c4a03a4a06a5678e79872","name":"金融","code":"tech","left":26,"right":27,"description":"资本论","creatorId":"3312cfb5794742dc8b9f98544fdb6854","catalog":{"name":"文章","code":"articles","left":1,"right":94,"creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"f3b8d91b8f9c4a03a4a06a5678e79872","createTime":1475220231708,"status":{"index":1,"name":"正常"}},"id":"85e9c09ec6c740c9852f441e3fbbd272","createTime":1475656563140,"status":{"index":1,"name":"正常"}}},{"id":"57f9e94211058a4c1f819332","title":"咯兔子","subtitle":null,"images":null,"summary":null,"hits":1,"catalog":{"pid":"f3b8d91b8f9c4a03a4a06a5678e79872","name":"贸易","code":"tech","left":32,"right":33,"description":"没有我卖不出去的","creatorId":"3312cfb5794742dc8b9f98544fdb6854","catalog":{"name":"文章","code":"articles","left":1,"right":94,"creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"f3b8d91b8f9c4a03a4a06a5678e79872","createTime":1475220231708,"status":{"index":1,"name":"正常"}},"id":"fcb62ebe26a74fa4b5f028ad4924d04d","createTime":1475656747994,"status":{"index":1,"name":"正常"}}},{"id":"57f9e94311058a4c1f819333","title":"咯兔子","subtitle":null,"images":null,"summary":null,"hits":1,"catalog":{"pid":"f3b8d91b8f9c4a03a4a06a5678e79872","name":"贸易","code":"tech","left":32,"right":33,"description":"没有我卖不出去的","creatorId":"3312cfb5794742dc8b9f98544fdb6854","catalog":{"name":"文章","code":"articles","left":1,"right":94,"creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"f3b8d91b8f9c4a03a4a06a5678e79872","createTime":1475220231708,"status":{"index":1,"name":"正常"}},"id":"fcb62ebe26a74fa4b5f028ad4924d04d","createTime":1475656747994,"status":{"index":1,"name":"正常"}}}]
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
         * id : 57ee3d6d11058a3b20ee968a
         * title : MVP开发之路
         * subtitle : null
         * images : null
         * summary : null
         * hits : 5
         * catalog : {"pid":"f3b8d91b8f9c4a03a4a06a5678e79872","name":"新闻","code":"news","left":2,"right":5,"creatorId":"6e07bc2797fb44e488e9b88fec42199e","catalog":{"name":"文章","code":"articles","left":1,"right":6,"creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"f3b8d91b8f9c4a03a4a06a5678e79872","createTime":1475220231708,"status":{"index":1,"name":"正常"}},"id":"704d54f44d7845f8a30322c9923a0d19","createTime":1473675919136,"status":{"index":1,"name":"正常"}}
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
             * name : 新闻
             * code : news
             * left : 2
             * right : 5
             * creatorId : 6e07bc2797fb44e488e9b88fec42199e
             * catalog : {"name":"文章","code":"articles","left":1,"right":6,"creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"f3b8d91b8f9c4a03a4a06a5678e79872","createTime":1475220231708,"status":{"index":1,"name":"正常"}}
             * id : 704d54f44d7845f8a30322c9923a0d19
             * createTime : 1473675919136
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
                private String creatorId;
                /**
                 * name : 文章
                 * code : articles
                 * left : 1
                 * right : 6
                 * creatorId : 3312cfb5794742dc8b9f98544fdb6854
                 * id : f3b8d91b8f9c4a03a4a06a5678e79872
                 * createTime : 1475220231708
                 * status : {"index":1,"name":"正常"}
                 */

                private CatalogBean catalog;
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

                public String getCreatorId() {
                    return creatorId;
                }

                public void setCreatorId(String creatorId) {
                    this.creatorId = creatorId;
                }

                public CatalogBean getCatalog() {
                    return catalog;
                }

                public void setCatalog(CatalogBean catalog) {
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
