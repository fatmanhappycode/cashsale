package com.cashsale.bean;

/**
 * @Description:商品互动的具体内容
 * @Author: 8-0416
 * @Date: 2018/11/27
 */
public class InteractInfoDTO {
    /** 执行者 */
    private String userName;
    /** 评论时间 */
    private String commentsTime;
    /** 评论内容 */
    private String comments;
    /** 分享时间 */
    private String shareTime;
    /** 点赞时间 */
    private String likeTime;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCommentsTime() {
        return commentsTime;
    }

    public void setCommentsTime(String commentsTime) {
        this.commentsTime = commentsTime;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getShareTime() {
        return shareTime;
    }

    public void setShareTime(String shareTime) {
        this.shareTime = shareTime;
    }

    public String getLikeTime() {
        return likeTime;
    }

    public void setLikeTime(String likeTime) {
        this.likeTime = likeTime;
    }
}
