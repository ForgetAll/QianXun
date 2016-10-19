package com.heapot.qianxun.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 15859 on 2016/10/19.
 */
public class JobTypeBean implements Serializable{
    /**
     * status : success
     * message :
     * content : [{"pid":"af3a09e8a4414c97a038a2d735064ebc","name":"体育","code":"physical","left":148,"right":149,"description":"其实我头脑也很发达","creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"488908e6e1e84e59b85bf19e05b24ae9","createTime":1476196684382,"status":{"index":1,"name":"正常"}},{"pid":"af3a09e8a4414c97a038a2d735064ebc","name":"科技","code":"science","left":150,"right":151,"description":"硅谷论坛","creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"4a910d542cf548b88bd0b66336d27680","createTime":1476241213292,"status":{"index":1,"name":"正常"}},{"pid":"af3a09e8a4414c97a038a2d735064ebc","name":"金融","code":"jobs","left":152,"right":153,"description":"资本论","creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"1a0b018d84ba4f4e967f936650d3972d","createTime":1476323140667,"status":{"index":1,"name":"正常"}},{"pid":"af3a09e8a4414c97a038a2d735064ebc","name":"建筑","code":"building","left":154,"right":155,"description":"搬砖者们","creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"5069e71ee44b4498ba58e00f82c83bdb","createTime":1476323561219,"status":{"index":1,"name":"正常"}},{"pid":"af3a09e8a4414c97a038a2d735064ebc","name":"服务业","code":"services","left":156,"right":157,"description":"除了我，全都是上帝","creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"be4b81ca38e04cb4ace5b12089f729d1","createTime":1476323621972,"status":{"index":1,"name":"正常"}},{"pid":"af3a09e8a4414c97a038a2d735064ebc","name":"贸易","code":"trade","left":158,"right":159,"description":"没有我卖不出去的","creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"20e8343b06fa482a99952321c96853ea","createTime":1476323681212,"status":{"index":1,"name":"正常"}},{"pid":"af3a09e8a4414c97a038a2d735064ebc","name":"传媒","code":"media","left":160,"right":161,"description":"镜头中最美的你","creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"e56292f438f440e69e38af01e6a84e28","createTime":1476323958638,"status":{"index":1,"name":"正常"}},{"pid":"af3a09e8a4414c97a038a2d735064ebc","name":"法律","code":" law","left":162,"right":163,"description":"我就是红线","creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"929ad1a38b064262984857ae3e8aa2dc","createTime":1476324289717,"status":{"index":1,"name":"正常"}},{"pid":"af3a09e8a4414c97a038a2d735064ebc","name":"制造业","code":" manufacturing","left":164,"right":165,"description":"Made in China","creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"871dd62511ac4f149d07ceb6bbd7499c","createTime":1476324371713,"status":{"index":1,"name":"正常"}},{"pid":"af3a09e8a4414c97a038a2d735064ebc","name":"交通","code":" traffic","left":166,"right":167,"description":"真·老司机","creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"8334bd2bb6f1462d891cc700fd11ddef","createTime":1476324469131,"status":{"index":1,"name":"正常"}},{"pid":"af3a09e8a4414c97a038a2d735064ebc","name":"人力资源","code":" resources","left":168,"right":169,"description":"千里马快到碗里来","creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"5c73324fcdf04c148be73c97f16558b6","createTime":1476324558194,"status":{"index":1,"name":"正常"}},{"pid":"af3a09e8a4414c97a038a2d735064ebc","name":"公职","code":" Public office","left":170,"right":171,"description":"为人民服务！","creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"116a545df84a4df2a48620405a45bc89","createTime":1476324634824,"status":{"index":1,"name":"正常"}},{"pid":"af3a09e8a4414c97a038a2d735064ebc","name":"种植","code":" planting","left":172,"right":173,"description":"俺是种地嘞","creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"7c1f0d09cdfc4a32a61c5c39942b1048","createTime":1476324731259,"status":{"index":1,"name":"正常"}},{"pid":"af3a09e8a4414c97a038a2d735064ebc","name":"畜牧","code":" animal husbandry","left":174,"right":175,"description":"不想被包养就去养猪吧","creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"df20059d0f584dbe88c89ef113383140","createTime":1476324801236,"status":{"index":1,"name":"正常"}},{"pid":"af3a09e8a4414c97a038a2d735064ebc","name":"能源","code":"energy","left":176,"right":177,"description":"美国打伊拉克图个啥","creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"2edddb7fb69f4987ab03a77aeaecb1b3","createTime":1476324882628,"status":{"index":1,"name":"正常"}},{"pid":"af3a09e8a4414c97a038a2d735064ebc","name":"环保","code":"Environmental protection","left":178,"right":179,"description":"我们是地球的杀毒软件","creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"8583518e350b49f385b05ffd74913a10","createTime":1476324960685,"status":{"index":1,"name":"正常"}},{"pid":"af3a09e8a4414c97a038a2d735064ebc ","name":"军事","code":"military","left":180,"right":181,"description":"精忠报国","creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"c41ae4bd7bab498099bbec0bb7d481f1","createTime":1476325041679,"status":{"index":1,"name":"正常"}},{"pid":"af3a09e8a4414c97a038a2d735064ebc ","name":"外语","code":"foreign language","left":182,"right":183,"description":"鸟语爱好者","creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"8615aa93da634e5fb37a86a157dc1b37","createTime":1476325103979,"status":{"index":1,"name":"正常"}},{"pid":"af3a09e8a4414c97a038a2d735064ebc","name":"管理","code":"management","left":184,"right":185,"description":"我就静静地看着你们干活","creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"a60c17c1ae5d4be986ea4fe093b65fde","createTime":1476325183357,"status":{"index":1,"name":"正常"}},{"pid":"af3a09e8a4414c97a038a2d735064ebc","name":"心理","code":"psychological","left":186,"right":187,"description":"我看得透人心却看不透你","creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"31054f2dd6df4e94b473db9199dcca1f","createTime":1476325258643,"status":{"index":1,"name":"正常"}},{"pid":"af3a09e8a4414c97a038a2d735064ebc","name":"教育","code":"education","left":188,"right":189,"description":"传道授业解惑也","creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"8f83821e2f4e44679f0f2e9b825d9b5a","createTime":1476325334212,"status":{"index":1,"name":"正常"}},{"pid":"af3a09e8a4414c97a038a2d735064ebc","name":"策划","code":"planning","left":190,"right":191,"description":"不谋全局者，不足谋一域","creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"e7aaae5456d244508f1cdaa115a40cd5","createTime":1476325393174,"status":{"index":1,"name":"正常"}},{"pid":"af3a09e8a4414c97a038a2d735064ebc","name":"烹饪","code":"cooking","left":192,"right":193,"description":"辞职去炒菜","creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"0598cb8f586a4031867e1195bdd754db","createTime":1476325460163,"status":{"index":1,"name":"正常"}},{"pid":"af3a09e8a4414c97a038a2d735064ebc","name":"美术","code":"fine arts","left":194,"right":195,"description":"梵高爱上毕加索","creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"adab13275129447abaf4915965d95590","createTime":1476325532671,"status":{"index":1,"name":"正常"}},{"pid":"af3a09e8a4414c97a038a2d735064ebc","name":"音乐","code":"music","left":196,"right":197,"description":"缪Z克","creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"3c268c1c971e485a867804bb999971ed","createTime":1476325597678,"status":{"index":1,"name":"正常"}},{"pid":"af3a09e8a4414c97a038a2d735064ebc","name":"历史","code":"history","left":198,"right":199,"description":"祖宗那点事儿","creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"e06f0998af944746a696729460d68cf2","createTime":1476325684945,"status":{"index":1,"name":"正常"}},{"pid":"af3a09e8a4414c97a038a2d735064ebc","name":"文学","code":"literature","left":200,"right":201,"description":"喝墨水长大的人","creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"554e061a72e5411c8e00485e6b6f88f1","createTime":1476325749182,"status":{"index":1,"name":"正常"}},{"pid":"af3a09e8a4414c97a038a2d735064ebc","name":"政治","code":"political","left":202,"right":203,"description":"台湾是中国的一个省","creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"3c4338ea5e414afdb42001272f124226","createTime":1476325809558,"status":{"index":1,"name":"正常"}},{"pid":"af3a09e8a4414c97a038a2d735064ebc ","name":"哲学","code":"philosophy","left":204,"right":205,"description":"老子请苏格拉底吃培根","creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"46b2148d3ed84890a25bd31f9383b344","createTime":1476325871545,"status":{"index":1,"name":"正常"}},{"pid":"af3a09e8a4414c97a038a2d735064ebc ","name":"情感","code":"emotional","left":206,"right":207,"description":"喷在屏幕上的荷尔蒙","creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"10e140d6b9ab4d29bb94730031626350","createTime":1476325931160,"status":{"index":1,"name":"正常"}},{"pid":"af3a09e8a4414c97a038a2d735064ebc","name":" 游戏","code":"game","left":208,"right":209,"description":"我知道一直这样会瞎 可我还是忍不住","creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"c760698164fc4257a305e04487ac975a","createTime":1476325998331,"status":{"index":1,"name":"正常"}},{"pid":"af3a09e8a4414c97a038a2d735064ebc","name":" 娱乐","code":"entertainment","left":210,"right":211,"description":"贵圈有点乱","creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"8d7bc4e5b10f45ac8bf229013a93e770","createTime":1476326075951,"status":{"index":1,"name":"正常"}},{"pid":"af3a09e8a4414c97a038a2d735064ebc","name":" 动漫","code":"anime","left":212,"right":213,"description":"国漫当自强","creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"27c38a14c1a540bc902b62bf19e62697","createTime":1476326133826,"status":{"index":1,"name":"正常"}},{"pid":"af3a09e8a4414c97a038a2d735064ebc","name":" 汽车","code":"car","left":214,"right":215,"description":"想当老司机 得先有它","creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"556e5f7c0910493e89213b014eb18c60","createTime":1476326186544,"status":{"index":1,"name":"正常"}},{"pid":"af3a09e8a4414c97a038a2d735064ebc","name":" 撕逼区","code":"Quarrel area","left":216,"right":217,"description":"撕逼请来此处","creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"a047bdc9de394aaba9cbb742cc438e21","createTime":1476326259939,"status":{"index":1,"name":"正常"}},{"pid":"af3a09e8a4414c97a038a2d735064ebc","name":" 科技","code":"technology","left":218,"right":219,"description":"硅谷论坛","creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"12da4848c1264b5f82e2cd1378627979","createTime":1476326450135,"status":{"index":1,"name":"正常"}},{"pid":"af3a09e8a4414c97a038a2d735064ebc","name":" 金融","code":"financial","left":220,"right":221,"description":"资本论","creatorId":"3312cfb5794742dc8b9f98544fdb6854","id":"802e0733f2b64781af4d95cd7d8be28d","createTime":1476326487044,"status":{"index":1,"name":"正常"}}]
     */

    private String status;
    private String message;
    /**
     * pid : af3a09e8a4414c97a038a2d735064ebc
     * name : 体育
     * code : physical
     * left : 148
     * right : 149
     * description : 其实我头脑也很发达
     * creatorId : 3312cfb5794742dc8b9f98544fdb6854
     * id : 488908e6e1e84e59b85bf19e05b24ae9
     * createTime : 1476196684382
     * status : {"index":1,"name":"正常"}
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

    public static class ContentBean implements Serializable{
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

        public static class StatusBean implements Serializable{
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
