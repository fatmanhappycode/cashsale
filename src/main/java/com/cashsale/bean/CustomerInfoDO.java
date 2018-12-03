package com.cashsale.bean;

/**
 * @author Sylvia
 * 2018年10月16日
 * 用户的个人信息
 */
public class CustomerInfoDO {
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 信用值
	 */
	private int credit;
	/**
	 * 学号
	 */
	private String sno;
	/**
	 * 真实姓名
	 */
	private String realname;
	/**
	 * 昵称
	 */
	private String nickname;
	/**
	 * 是否验证学号
	 */
	private boolean isCertificate;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 密码
	 */
	private String password;

	/**
	 * 是否验证手机号
	 */
	private boolean mobile_certificate;
	
	public CustomerInfoDO(String username) {
		super();
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public String getSno() {
		return sno;
	}

	public void setSno(String sno) {
		this.sno = sno;
	}
	
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public boolean isCertificate() {
		return isCertificate;
	}

	public void setCertificate(boolean isCertificate) {
		this.isCertificate = isCertificate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public boolean isMobile_certificate() {
		return mobile_certificate;
	}

	public void setMobile_certificate(boolean mobile_certificate) {
		this.mobile_certificate = mobile_certificate;
	}
}