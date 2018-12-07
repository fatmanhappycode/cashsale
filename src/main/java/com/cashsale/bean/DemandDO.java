package com.cashsale.bean;

/**
 * @Description: 需求
 * @Author: 8-0416
 * @Date: 2018/12/4
 */
public class DemandDO {
    /** 需求id */
    private int demandId;
    /** 发布者 */
    private String username;
    /** 标题 */
    private String title;
    /** 内容 */
    private String content;
    /** 图片 */
    private String imgUrl;
    /** 发布者所属组织 */
    private String organization;
    /** 发布时间 */
    private String time;

    public int getDemandId() {
        return demandId;
    }

    public void setDemandId(int demandId) {
        this.demandId = demandId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
