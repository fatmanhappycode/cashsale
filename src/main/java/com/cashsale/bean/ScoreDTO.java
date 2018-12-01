package com.cashsale.bean;

/**
 * @author Sylvia
 * 2018年11月3日
 */
public class ScoreDTO {

    /** 用户名 */
    private String username;

    /** 商品id */
    private int productId;

    /** 操作代码 */
    private String scoreCode;

    /** 评论内容 */
    private String comments;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getScoreCode() {
        return scoreCode;
    }

    public void setScoreCode(String scoreCode) {
        this.scoreCode = scoreCode;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
