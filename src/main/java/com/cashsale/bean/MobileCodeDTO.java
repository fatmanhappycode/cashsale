package com.cashsale.bean;

/**
 * @Description:发送验证码的返回信息
 * @Author: 8-0416
 * @Date: 2018/12/2
 */
public class MobileCodeDTO {
    /** 状态码  */
    private String respCode;
    /** 状态码的描述 */
    private String respDesc;
    /** 验证码通知短信发送失败的条数 */
    private String failCount;
    /** 失败列表 */
    private String[] failList;
    /** 短信标识符 */
    private String smsId;

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespDesc() {
        return respDesc;
    }

    public void setRespDesc(String respDesc) {
        this.respDesc = respDesc;
    }

    public String getFailCount() {
        return failCount;
    }

    public void setFailCount(String failCount) {
        this.failCount = failCount;
    }

    public String[] getFailList() {
        return failList;
    }

    public void setFailList(String[] failList) {
        this.failList = failList;
    }

    public String getSmsId() {
        return smsId;
    }

    public void setSmsId(String smsId) {
        this.smsId = smsId;
    }
}
