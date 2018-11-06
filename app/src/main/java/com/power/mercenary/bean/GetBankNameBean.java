package com.power.mercenary.bean;

import java.util.List;

public class GetBankNameBean {

    /**
     * code : 0
     * msg : success
     * data : [{"bankname":"阿拉善左旗方大村镇银行","bankcode":"ALSFDYH"},{"bankname":"安徽肥西石银村镇银行","bankcode":"SHIYINBANK"},{"bankname":"安阳银行","bankcode":"AYYH"},{"bankname":"鞍山银行","bankcode":"ASYH"},{"bankname":"奥地利奥合国际银行股份有限公司","bankcode":"ADLAH"},{"bankname":"澳大利亚和新西兰银行（中国）有限公司","bankcode":"ADLYHXXL"},{"bankname":"澳洲联邦银行","bankcode":"COMMBANK"},{"bankname":"巴彦融兴村镇银行","bankcode":"BYRX"},{"bankname":"包商银行","bankcode":"BTCB"},{"bankname":"北部湾银行","bankcode":"BBWYH"},{"bankname":"北京昌平兆丰村镇银行","bankcode":"BJCPZF"},{"bankname":"北京大兴九银村镇银行","bankcode":"DXJY"},{"bankname":"北京怀柔融兴村镇银行","bankcode":"BJHR"},{"bankname":"北京顺义银座村镇银行","bankcode":"BJSYYZCZYH"},{"bankname":"北京银行","bankcode":"BCCB"},{"bankname":"本溪市商业银行","bankcode":"BXSSYYH"},{"bankname":"比利时联合银行","bankcode":"KBC"},{"bankname":"渤海银行","bankcode":"CBHB"},{"bankname":"沧州融信农村商业银行","bankcode":"CZRCB"},{"bankname":"沧州银行","bankcode":"CZCCB"},{"bankname":"长安银行","bankcode":"CAYH"},{"bankname":"长沙银行","bankcode":"CSCB"},{"bankname":"长治市商业银行","bankcode":"CZHCCB"},{"bankname":"朝阳银行","bankcode":"CYYH"},{"bankname":"成都农村商业银行","bankcode":"CDNCSY"},{"bankname":"成都银行","bankcode":"CDYH"},{"bankname":"成泰农村合作银行","bankcode":"CTNCHZYH"},{"bankname":"承德银行","bankcode":"CDBANK"},{"bankname":"城市合作银行或上海银行","bankcode":"CSHZYH"},{"bankname":"城市信用合作社","bankcode":"CSXYHZS"},{"bankname":"城市信用社","bankcode":"CHSXYS"},{"bankname":"稠州商业银行","bankcode":"888888"},{"bankname":"楚雄兴彝村镇银行","bankcode":"CXXD"},{"bankname":"创兴银行有限公司","bankcode":"CXYHYXGS"},{"bankname":"村镇银行","bankcode":"CUNZB"},{"bankname":"达州市商业银行","bankcode":"DZCB"},{"bankname":"大华银行","bankcode":"UOBCHINA"},{"bankname":"大连农村商业银行","bankcode":"DLNCSYYH"},{"bankname":"大连银行","bankcode":"DLSCB"},{"bankname":"大同市商业银行","bankcode":"DTSYH"},{"bankname":"大新银行","bankcode":"DAHSING"},{"bankname":"大冶农村商业银行","bankcode":"DYRCB"},{"bankname":"大众银行","bankcode":"TCBANK"},{"bankname":"代收付清算组织","bankcode":"DSFQSZZ"},{"bankname":"丹东银行","bankcode":"DDYH"},{"bankname":"德国北德意志州银行","bankcode":"NORDLB"},{"bankname":"德国商业银行","bankcode":"COMMERZBANK"},{"bankname":"德国西德银行股份有限公司","bankcode":"DGXDYH"},{"bankname":"德阳银行","bankcode":"DYYH"},{"bankname":"德意志银行","bankcode":"DB"},{"bankname":"德州银行","bankcode":"DZYH"},{"bankname":"电子联行转换中心","bankcode":"DZLHZHZX"},{"bankname":"东方汇理银行","bankcode":"BI"},{"bankname":"东莞农村商业银行","bankcode":"DRCB"},{"bankname":"东莞银行","bankcode":"DONGGUANBC"},{"bankname":"东亚银行","bankcode":"HKBEA"},{"bankname":"东营银行","bankcode":"DYCCB"},{"bankname":"法国巴黎银行","bankcode":"BNPPARIBAS"},{"bankname":"法国外贸银行","bankcode":"NBPBK"},{"bankname":"丰业银行","bankcode":"SCTABK"},{"bankname":"福建海峡银行","bankcode":"FJHXYH"},{"bankname":"福建省农村信用社联合社","bankcode":"FJNX"},{"bankname":"抚顺银行","bankcode":"FSYH"},{"bankname":"阜新银行","bankcode":"FXYH"},{"bankname":"富滇银行","bankcode":"FDYH"},{"bankname":"甘肃银行","bankcode":"GSYH"},{"bankname":"赣州银行","bankcode":"GZBC"},{"bankname":"工商银行","bankcode":"ICBC"},{"bankname":"光大银行","bankcode":"CEB"},{"bankname":"广东华兴银行","bankcode":"GDHXYH"},{"bankname":"广东南粤银行","bankcode":"GDNYYJ"},{"bankname":"广东省农村信用社","bankcode":"GDNC"},{"bankname":"广发银行","bankcode":"CGB"},{"bankname":"广州农村商业银行","bankcode":"GZNCSY"},{"bankname":"广州银行","bankcode":"GZYH"},{"bankname":"贵阳银行","bankcode":"GYYH"},{"bankname":"桂林市商业银行","bankcode":"GLSY"},{"bankname":"桂林银行","bankcode":"GLYH"},{"bankname":"国家开发银行","bankcode":"CDBB"},{"bankname":"国民银行（中国）有限公司","bankcode":"GMYH"},{"bankname":"哈尔滨银行","bankcode":"HEBYH"},{"bankname":"哈密市商业银行","bankcode":"HMSSYYH"},{"bankname":"邯郸银行","bankcode":"HDCB"},{"bankname":"韩国产业银行","bankcode":"KDBK"},{"bankname":"韩国外换银行","bankcode":"KEB"},{"bankname":"韩国中小企业银行","bankcode":"IBKK"},{"bankname":"韩亚银行","bankcode":"HYYH"},{"bankname":"汉口银行","bankcode":"HKYH"},{"bankname":"杭州联合农村商业银行","bankcode":"HZLHNCSSYH"},{"bankname":"杭州银行","bankcode":"HZBANK"},{"bankname":"河北省农村信用社","bankcode":"HBNC"},{"bankname":"河北银行","bankcode":"HBYH"},{"bankname":"荷兰安智银行股份有限公司","bankcode":"HLAZYH"},{"bankname":"荷兰合作银行有限公司","bankcode":"HLHZYH"},{"bankname":"恒丰银行","bankcode":"EGB"},{"bankname":"恒生银行","bankcode":"HSB"},{"bankname":"湖北松滋农村商业银行","bankcode":"SZRCB"},{"bankname":"湖北银行","bankcode":"HBC"},{"bankname":"湖州银行","bankcode":"HZYH"},{"bankname":"葫芦岛银行","bankcode":"HLDYH"},{"bankname":"花旗银行","bankcode":"HQYH"},{"bankname":"华美银行(中国)有限公司","bankcode":"HMYH"},{"bankname":"华侨银行","bankcode":"OCBC"},{"bankname":"华融湘江银行","bankcode":"HRXJBK"},{"bankname":"华夏银行","bankcode":"HXB"},{"bankname":"华一银行","bankcode":"COB"},{"bankname":"黄河农村商业银行","bankcode":"HHNCSYYH"},{"bankname":"徽商银行","bankcode":"AHCB"},{"bankname":"汇丰银行","bankcode":"HFB"},{"bankname":"吉林银行","bankcode":"JLSB"},{"bankname":"集友银行","bankcode":"CHIYUBANK"},{"bankname":"济宁银行","bankcode":"JNYH"},{"bankname":"嘉兴银行","bankcode":"JXCCB"},{"bankname":"建东银行","bankcode":"ORIBANK"},{"bankname":"建设银行","bankcode":"CCB"},{"bankname":"江南农村商业银行","bankcode":"JNNCSYYH"},{"bankname":"江苏长江商业银行","bankcode":"JSCJSY"},{"bankname":"江苏银行","bankcode":"JSBCHINA"},{"bankname":"交通银行","bankcode":"BOCO"},{"bankname":"焦作市商业银行","bankcode":"JZSSY"},{"bankname":"金华银行","bankcode":"JHCCB"},{"bankname":"锦州银行","bankcode":"JZYN"},{"bankname":"晋城银行","bankcode":"JCYH"},{"bankname":"晋商银行","bankcode":"JSYH"},{"bankname":"晋中银行","bankcode":"JZYH"},{"bankname":"景德镇市商业银行","bankcode":"JDZCB"},{"bankname":"九江银行","bankcode":"JJBC"},{"bankname":"开封市商业银行","bankcode":"KFSSY"},{"bankname":"昆仑银行","bankcode":"KLB"},{"bankname":"昆山农村商业银行","bankcode":"KSRCB"},{"bankname":"莱商银行","bankcode":"ISBC"},{"bankname":"兰州银行","bankcode":"LZBK"},{"bankname":"廊坊银行","bankcode":"LFYH"},{"bankname":"乐山市商业银行","bankcode":"ISCCB"},{"bankname":"凉山州商业银行","bankcode":"ISZSH"},{"bankname":"辽阳银行","bankcode":"LYCCB"},{"bankname":"临商银行","bankcode":"LSYH"},{"bankname":"柳州银行","bankcode":"LZYH"},{"bankname":"龙江银行","bankcode":"LJYH"},{"bankname":"泸州市商业银行","bankcode":"LZCCB"},{"bankname":"洛阳银行","bankcode":"LYBC"},{"bankname":"马来亚银行","bankcode":"MAYBK"},{"bankname":"美国银行","bankcode":"BAC"},{"bankname":"蒙特利尔银行","bankcode":"BMOBK"},{"bankname":"绵阳市商业银行","bankcode":"MYCCB"},{"bankname":"民生银行","bankcode":"CMBC"},{"bankname":"民泰商业银行","bankcode":"mtsyyh"},{"bankname":"摩根大通银行","bankcode":"JPMC"},{"bankname":"摩根士丹利国际银行（中国）有限公司","bankcode":"MGSDL"},{"bankname":"内蒙古银行","bankcode":"NMGYH"},{"bankname":"南昌银行","bankcode":"NCYH"},{"bankname":"南充市商业银行","bankcode":"NCCB"},{"bankname":"南京银行","bankcode":"NJCB"},{"bankname":"南阳市商业银行","bankcode":"NYSCCB"},{"bankname":"南阳银行","bankcode":"NYBANK"},{"bankname":"南洋商业银行","bankcode":"NOBC"},{"bankname":"宁波东海银行","bankcode":"NBDHYH"},{"bankname":"宁波银行","bankcode":"NBB"},{"bankname":"宁夏银行","bankcode":"NXYH"},{"bankname":"农村合作银行","bankcode":"ZJRCU"},{"bankname":"农村商业银行","bankcode":"NCSY"},{"bankname":"农村信用合作联社","bankcode":"NCXYLS"},{"bankname":"农村信用合作社","bankcode":"NXS"},{"bankname":"农村信用联社","bankcode":"NXLS"},{"bankname":"农村信用社","bankcode":"NCXYS"},{"bankname":"农商行","bankcode":"NSH"},{"bankname":"农商银行","bankcode":"NSYH"},{"bankname":"农业银行","bankcode":"ABC"},{"bankname":"攀枝花市商业银行","bankcode":"PZHCCB"},{"bankname":"盘谷银行(中国)有限公司","bankcode":"PGYH"},{"bankname":"盘锦市商业银行","bankcode":"PJCCB"},{"bankname":"平安银行","bankcode":"SZCB"},{"bankname":"平顶山银行","bankcode":"PDSYH"},{"bankname":"濮阳银行","bankcode":"PYYH"},{"bankname":"齐鲁银行","bankcode":"QLYH"},{"bankname":"齐商银行","bankcode":"QSYH"},{"bankname":"青岛银行","bankcode":"QDTH"},{"bankname":"青海银行","bankcode":"QHBANK"},{"bankname":"曲靖市商业银行","bankcode":"QJCCB"},{"bankname":"泉州银行","bankcode":"QZBC"},{"bankname":"日联银行","bankcode":"BTMU"},{"bankname":"日照银行","bankcode":"RZYH"},{"bankname":"瑞典北欧斯安银行有限公司","bankcode":"RDBOSA"},{"bankname":"瑞典银行有限公司","bankcode":"RDYH"},{"bankname":"瑞士信贷第一波士顿银行","bankcode":"RSXDDYB"},{"bankname":"瑞士银行","bankcode":"UBS"},{"bankname":"瑞穗实业银行","bankcode":"RSSY"},{"bankname":"三井住友银行","bankcode":"SMBC"},{"bankname":"三门峡银行","bankcode":"SMBANK"},{"bankname":"山口银行","bankcode":"YMGCBK"},{"bankname":"商丘银行","bankcode":"SQYH"},{"bankname":"商业银行","bankcode":"CSSYBANK"},{"bankname":"上海浦东发展银行","bankcode":"SPDB"},{"bankname":"上海银行","bankcode":"SHB"},{"bankname":"上饶银行","bankcode":"SRB"},{"bankname":"绍兴银行","bankcode":"SXYH"},{"bankname":"深圳发展银行","bankcode":"SDB"},{"bankname":"盛京银行","bankcode":"SJYH"},{"bankname":"石嘴山市城市信用社","bankcode":"SZSSCS"},{"bankname":"石嘴山银行","bankcode":"SZSYH"},{"bankname":"首都银行（中国）有限公司","bankcode":"SDYHYX"},{"bankname":"苏格兰皇家银行（中国）有限公司","bankcode":"SGLHJYH"},{"bankname":"苏州银行","bankcode":"SZYH"},{"bankname":"台州银行","bankcode":"TZYH"},{"bankname":"泰国开泰银行(大众)有限公司","bankcode":"TGKTYH"},{"bankname":"天津银行","bankcode":"TJYH"},{"bankname":"铁岭银行股份有限公司","bankcode":"TLYH"},{"bankname":"潍坊银行","bankcode":"WFYH"},{"bankname":"温州银行","bankcode":"WZYH"},{"bankname":"西安银行","bankcode":"XAYH"},{"bankname":"厦门国际银行","bankcode":"XIB"},{"bankname":"厦门银行","bankcode":"XMYH"},{"bankname":"新韩银行","bankcode":"XHYH"},{"bankname":"信阳银行","bankcode":"XYBANK"},{"bankname":"兴业银行","bankcode":"CIB"},{"bankname":"星展银行","bankcode":"DBS"},{"bankname":"邢台银行","bankcode":"XTBANK"},{"bankname":"许昌银行","bankcode":"XCBC"},{"bankname":"烟台银行","bankcode":"YTYH"},{"bankname":"意大利联合圣保罗银行股份有限公司","bankcode":"YDLLHSBL"},{"bankname":"意大利裕信银行股份有限公司","bankcode":"YDLYX"},{"bankname":"银行间市场清算所","bankcode":"YHJSC"},{"bankname":"英国巴克莱银行有限公司","bankcode":"YGBLKYH"},{"bankname":"营口沿海银行股份有限公司","bankcode":"YKYAN"},{"bankname":"营口银行","bankcode":"YKYH"},{"bankname":"永亨银行","bankcode":"WHBCN"},{"bankname":"永隆银行","bankcode":"WINGLBANK"},{"bankname":"友利银行","bankcode":"YLYH"},{"bankname":"渣打银行","bankcode":"ZDYH"},{"bankname":"招商银行","bankcode":"CMBCHINA"},{"bankname":"浙商银行","bankcode":"CZ"},{"bankname":"郑州银行","bankcode":"ZZYH"},{"bankname":"支付业务收费专户","bankcode":"ZFYWSF"},{"bankname":"中德住房储蓄银行","bankcode":"SGB"},{"bankname":"中国进出口银行","bankcode":"EXIMBANK"},{"bankname":"中国农业发展银行","bankcode":"NYFZYH"},{"bankname":"中国人民银行","bankcode":"PBC"},{"bankname":"中国外汇交易中心","bankcode":"ZGWHJYZX"},{"bankname":"中国银行","bankcode":"BOC"},{"bankname":"中国邮政储蓄","bankcode":"POST"},{"bankname":"中华人民共和国国家金库","bankcode":"ZHRMGHG"},{"bankname":"中信银行","bankcode":"ECITIC"},{"bankname":"中央国债登记结算有限责任公司","bankcode":"zygzdjjs"},{"bankname":"重庆三峡银行","bankcode":"CQSXYH"},{"bankname":"重庆银行","bankcode":"CQCB"},{"bankname":"珠海华润银行","bankcode":"ZHHR"}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * bankname : 阿拉善左旗方大村镇银行
         * bankcode : ALSFDYH
         */

        private String bankname;
        private String bankcode;

        public String getBankname() {
            return bankname;
        }

        public void setBankname(String bankname) {
            this.bankname = bankname;
        }

        public String getBankcode() {
            return bankcode;
        }

        public void setBankcode(String bankcode) {
            this.bankcode = bankcode;
        }
    }
}
