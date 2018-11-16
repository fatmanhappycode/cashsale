package com.cashsale.bean;

public class MessageDTO {
	/** 消息发送者 */
	private String sender;
	
	/** 消息接收者 */
	private String receiver;
	
	/** 消息内容 */
	private String content;
	
	/** 发送时间 */
	private String date;
	
	/** 发送的图片 */
	private String imgUrl;

	public MessageDTO(){ }
	public MessageDTO(String sender, String receiver, String content, String date, String imgUrl) {
		super();
		this.sender = sender;
		this.receiver = receiver;
		this.content = content;
		this.date = date;
		this.imgUrl = imgUrl;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
}