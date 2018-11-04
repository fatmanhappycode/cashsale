package com.cashsale.bean;

/**
 * @author 肥宅快乐码
 * @date 2018/10/17 - 22:52
 */
public class ProductDO {
    private String title;
    private String label;
    private int price;
    private int tradeMethod;
    private int isBargain;
    private String pdDescription;
    private String imageUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getTradeMethod() {
        return tradeMethod;
    }

    public void setTradeMethod(int tradeMethod) {
        this.tradeMethod = tradeMethod;
    }

    public int getIsBargain() {
        return isBargain;
    }

    public void setIsBargain(int isBargain) {
        this.isBargain = isBargain;
    }

    public String getPdDescription() {
        return pdDescription;
    }

    public void setPdDescription(String pdDescription) {
        this.pdDescription = pdDescription;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
