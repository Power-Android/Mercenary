package com.power.mercenary.bean.mytask;

/**
 * admin  2018/7/18 wan
 */
public class PublishTaskBean {

    /**
     * id : 1
     * task_name : 任务名称
     * pay_amount : 2.00
     * task_description : 任务详情
     * task_tag : 简单,便宜
     * task_status : 1
     * view_num : 0
     * share_num : 0
     */

    private String id;
    private String task_name;
    private String pay_amount;
    private String task_description;
    private String task_tag;
    private String task_status;
    private String task_type;
    private String view_num;
    private String ticheng;
    private String zfpt_ticheng ;
    private String fafang_money;
    private String fabu_money;
    private String is_yanqi;
    private String yanqi_days;
    private String yanqi_start;
    private String yanqi_reason;
    private String refuse_cause;

    public String getRefuse_cause() {
        return refuse_cause;
    }

    public void setRefuse_cause(String refuse_cause) {
        this.refuse_cause = refuse_cause;
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

    public String getYanqi_reason() {
        return yanqi_reason;
    }

    public void setYanqi_reason(String yanqi_reason) {
        this.yanqi_reason = yanqi_reason;
    }

    public String getIs_yanqi() {
        return is_yanqi;
    }

    public void setIs_yanqi(String is_yanqi) {
        this.is_yanqi = is_yanqi;
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

    public String getTask_type() {
        return task_type;
    }

    public void setTask_type(String task_type) {
        this.task_type = task_type;
    }

    private String share_num;

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
}
