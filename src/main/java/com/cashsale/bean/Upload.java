package com.cashsale.bean;

import java.util.ArrayList;

/**
 * 上传
 * @author Sylvia
 * 2018年10月14日
 */
public class Upload {
	
	/** 图片地址 */
	private ArrayList<String> imgUri;
	
	/** 商品id */
	private String productId;

	public Upload(ArrayList<String> imgUri, String productId) {
		super();
		this.imgUri = imgUri;
		this.productId = productId;
	}

	public ArrayList<String> getImgUri() {
		return imgUri;
	}

	public void setImgUri(ArrayList<String> imgUri) {
		this.imgUri = imgUri;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
}