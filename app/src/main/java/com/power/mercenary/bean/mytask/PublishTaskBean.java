package com.power.mercenary.bean.mytask;

import java.util.List;

/**
 * admin  2018/7/18 wan
 */
public class PublishTaskBean {


    /**
     * code : 0
     * msg : success
     * data : [{"id":"337","task_name":"测试","task_type":"1","pay_amount":"0.10","task_description":"","task_tag":"wwwwwww","task_status":"3","refuse_cause":"","view_num":"0","share_num":"0","is_yanqi":"0","yanqi_days":"0","yanqi_start":"0","yanqi_end":"0","yanqi_reason":"","ticheng":"0","zfpt_ticheng":"0","fafang_money":"0","fabu_money":"10","settle_status":"2"},{"id":"334","task_name":"ddd","task_type":"1","pay_amount":"0.10","task_description":"","task_tag":"fffffff","task_status":"3","refuse_cause":"","view_num":"0","share_num":"0","is_yanqi":"1","yanqi_days":"2","yanqi_start":"1542265968","yanqi_end":"1542438768","yanqi_reason":"fffff","ticheng":"0","zfpt_ticheng":"0","fafang_money":"0","fabu_money":"10","settle_status":"2"}]
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
         * id : 337
         * task_name : 测试
         * task_type : 1
         * pay_amount : 0.10
         * task_description :
         * task_tag : wwwwwww
         * task_status : 3
         * refuse_cause :
         * view_num : 0
         * share_num : 0
         * is_yanqi : 0
         * yanqi_days : 0
         * yanqi_start : 0
         * yanqi_end : 0
         * yanqi_reason :
         * ticheng : 0
         * zfpt_ticheng : 0
         * fafang_money : 0
         * fabu_money : 10
         * settle_status : 2
         */

        private String id;
        private String task_name;
        private String task_type;
        private String pay_amount;
        private String task_description;
        private String task_tag;
        private String task_status;
        private String refuse_cause;
        private String view_num;
        private String share_num;
        private String is_yanqi;
        private String yanqi_days;
        private String yanqi_start;
        private String yanqi_end;
        private String yanqi_reason;
        private String ticheng;
        private String zfpt_ticheng;
        private String fafang_money;
        private String fabu_money;
        private String settle_status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTask_name() {
            return task_name;
        }

        public void setTask_name(String task_name) {
            this.task_name = task_name;
        }

        public String getTask_type() {
            return task_type;
        }

        public void setTask_type(String task_type) {
            this.task_type = task_type;
        }

        public String getPay_amount() {
            return pay_amount;
        }

        public void setPay_amount(String pay_amount) {
            this.pay_amount = pay_amount;
        }

        public String getTask_description() {
            return task_description;
        }

        public void setTask_description(String task_description) {
            this.task_description = task_description;
        }

        public String getTask_tag() {
            return task_tag;
        }

        public void setTask_tag(String task_tag) {
            this.task_tag = task_tag;
        }

        public String getTask_status() {
            return task_status;
        }

        public void setTask_status(String task_status) {
            this.task_status = task_status;
        }

        public String getRefuse_cause() {
            return refuse_cause;
        }

        public void setRefuse_cause(String refuse_cause) {
            this.refuse_cause = refuse_cause;
        }

        public String getView_num() {
            return view_num;
        }

        public void setView_num(String view_num) {
            this.view_num = view_num;
        }

        public String getShare_num() {
            return share_num;
        }

        public void setShare_num(String share_num) {
            this.share_num = share_num;
        }

        public String getIs_yanqi() {
            return is_yanqi;
        }

        public void setIs_yanqi(String is_yanqi) {
            this.is_yanqi = is_yanqi;
        }

        public String getYanqi_days() {
            return yanqi_days;
        }

        public void setYanqi_days(String yanqi_days) {
            this.yanqi_days = yanqi_days;
        }

        public String getYanqi_start() {
            return yanqi_start;
        }

        public void setYanqi_start(String yanqi_start) {
            this.yanqi_start = yanqi_start;
        }

        public String getYanqi_end() {
            return yanqi_end;
        }

        public void setYanqi_end(String yanqi_end) {
            this.yanqi_end = yanqi_end;
        }

        public String getYanqi_reason() {
            return yanqi_reason;
        }

        public void setYanqi_reason(String yanqi_reason) {
            this.yanqi_reason = yanqi_reason;
        }

        public String getTicheng() {
            return ticheng;
        }

        public void setTicheng(String ticheng) {
            this.ticheng = ticheng;
        }

        public String getZfpt_ticheng() {
            return zfpt_ticheng;
        }

        public void setZfpt_ticheng(String zfpt_ticheng) {
            this.zfpt_ticheng = zfpt_ticheng;
        }

        public String getFafang_money() {
            return fafang_money;
        }

        public void setFafang_money(String fafang_money) {
            this.fafang_money = fafang_money;
        }

        public String getFabu_money() {
            return fabu_money;
        }

        public void setFabu_money(String fabu_money) {
            this.fabu_money = fabu_money;
        }

        public String getSettle_status() {
            return settle_status;
        }

        public void setSettle_status(String settle_status) {
            this.settle_status = settle_status;
        }
    }
}
