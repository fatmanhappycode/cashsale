package com.cashsale.bean;

import java.util.Map;

/**
 * @Description: 用户互动具体内容（关注)
 * @Author: 8-0416
 * @Date: 2018/12/4
 */
public class UserInteractDTO {
    /** 用户名 */
    private String username;
    /** 被关注者 */
    private String concern;
    /** 关注数 */
    private int concernNumber;
    /** 粉丝数 */
    private int fanNumber;
    /** 具体粉丝 */
    private String[] fans;
    /** 具体关注 */
    private Map<String, Integer> concernsProductNumber;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getConcern() {
        return concern;
    }

    public void setConcern(String concern) {
        this.concern = concern;
    }

    public int getConcernNumber() {
        return concernNumber;
    }

    public void setConcernNumber(int concernNumber) {
        this.concernNumber = concernNumber;
    }

    public int getFanNumber() {
        return fanNumber;
    }

    public void setFanNumber(int fanNumber) {
        this.fanNumber = fanNumber;
    }

    public String[] getFans() {
        return fans;
    }

    public void setFans(String[] fans) {
        this.fans = fans;
    }

    public Map<String, Integer> getConcernsProductNumber() {
        return concernsProductNumber;
    }

    public void setConcernsProductNumber(Map<String, Integer> concernsProductNumber) {
        this.concernsProductNumber = concernsProductNumber;
    }
}
