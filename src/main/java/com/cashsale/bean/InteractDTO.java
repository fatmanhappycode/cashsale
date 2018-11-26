package com.cashsale.bean;

/**
 * @Description:商品互动内容
 * @Author: 8-0416
 * @Date: 2018/11/26
 */
public class InteractDTO {
    /** 商品id */
    private int productId;
    /** 执行者 */
    private String userName;
    /** 评论数 */
    private int commentsNumber;
    /** 评论时间 */
    private String comments_time;
    /** 评论内容 */
    private String comments;
    /** 点赞数 */
    private int like;
    /** 点赞时间 */
    private String like_time;
    /** 分享数 */
    private int share;
    /** 分享时间 */
    private String share_time;

    public int getCommentsNumber() {
        return commentsNumber;
    }

    public void setCommentsNumber(int commentsNumber) {
        this.commentsNumber = commentsNumber;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getShare() {
        return share;
    }

    public void setShare(int share) {
        this.share = share;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getComments_time() {
        return comments_time;
    }

    public void setComments_time(String comments_time) {
        this.comments_time = comments_time;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getLike_time() {
        return like_time;
    }

    public void setLike_time(String like_time) {
        this.like_time = like_time;
    }

    public String getShare_time() {
        return share_time;
    }

    public void setShare_time(String share_time) {
        this.share_time = share_time;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
