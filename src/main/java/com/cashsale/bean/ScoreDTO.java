package com.cashsale.bean;

public class ScoreDTO {

    /** 用户名 */
    private String username;

    /** 商品id */
    private int productId;

    /** 操作代码 */
    private String scoreCode;

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
}
