package com.heapot.qianxun.bean;

import java.io.Serializable;

/**
 * Created by 15859 on 2016/9/23.
 * 版本更新bean类
 */
public class UpdateResultBean implements Serializable{
    /**
     * stateCode : 200
     * data : {"appIcon":"http://img.r1.market.hiapk.com/data/upload/2016/01_25/11/72_72_20160125114915_1302.png","appName":"掌上链家","appDescribe":"掌上链家是集二手房、租房、新房功能于一体的手机找房软件，我们打破信息壁垒，为您提供全行业独家市场行情分析、房源成交历史、带看历史、变价历史信息，并第一时间告知您关注房源、小区的动态信息，让您绝不错过好房源！\r\n【专业的数据分析】独家市场数据，帮您认识市场行情。掌上链家给您提供专业的城市及小区市场数据分析，独家第一手数据。您来，就有惊喜！","appUrl":"http://apk.hiapk.com/appdown/com.homelink.android?planid=2064969&seid=c6f57ef9-fe60-0001-a27f-3bb64b60c460","appStar":"4.5","appType":"生活","appClassify":"购物","appCovers":"http://img.r1.market.hiapk.com/data/upload/2016/01_25/11/20160125114921_5214.png|http://img.r1.market.hiapk.com/data/upload/2016/01_25/11/20160125114923_1386.png|http://img.r1.market.hiapk.com/data/upload/2016/01_25/11/20160125114925_6473.png","id":1,"appDownCount":2560,"appSize":12.64,"tags":"1"}
     */

    private int stateCode;
    /**
     * appIcon : http://img.r1.market.hiapk.com/data/upload/2016/01_25/11/72_72_20160125114915_1302.png
     * appName : 掌上链家
     * appDescribe : 掌上链家是集二手房、租房、新房功能于一体的手机找房软件，我们打破信息壁垒，为您提供全行业独家市场行情分析、房源成交历史、带看历史、变价历史信息，并第一时间告知您关注房源、小区的动态信息，让您绝不错过好房源！
     * 【专业的数据分析】独家市场数据，帮您认识市场行情。掌上链家给您提供专业的城市及小区市场数据分析，独家第一手数据。您来，就有惊喜！
     * appUrl : http://apk.hiapk.com/appdown/com.homelink.android?planid=2064969&seid=c6f57ef9-fe60-0001-a27f-3bb64b60c460
     * appStar : 4.5
     * appType : 生活
     * appClassify : 购物
     * appCovers : http://img.r1.market.hiapk.com/data/upload/2016/01_25/11/20160125114921_5214.png|http://img.r1.market.hiapk.com/data/upload/2016/01_25/11/20160125114923_1386.png|http://img.r1.market.hiapk.com/data/upload/2016/01_25/11/20160125114925_6473.png
     * id : 1
     * appDownCount : 2560
     * appSize : 12.64
     * tags : 1
     */

    private AppBean data;

    public AppBean getData() {
        return data;
    }

    public void setData(AppBean data) {
        this.data = data;
    }

    public int getStateCode() {
        return stateCode;
    }

    public void setStateCode(int stateCode) {
        this.stateCode = stateCode;
    }
}
