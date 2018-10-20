package com.cashsale.util;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtil {
	
	public static void sendMail(String to, String code, String nickname, String password) throws Exception
	{
		//创建连接对象，连接到邮箱服务器
		Properties props = new Properties();
		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("Cashsale0416@cashsale.com","cashsale0416");
			}
		});
		
		//创建邮件对象
		Message message = new MimeMessage(session);
		//设置发件人
		message.setFrom(new InternetAddress("Cashsale0416@cashsale.com"));
		//设置收件人
		message.setRecipient(RecipientType.TO, new InternetAddress(to));
		//设置邮件的主题
		message.setSubject("来自“现卖”的激活邮件");
		//设置邮件的正文
		message.setContent("<h1>亲爱的"+nickname+"：</h1>"+"<br><h3>欢迎加入现卖！<br>" + 
				"请点击下面的连接完成注册：<br>" + "<a href='http://localhost:8080/thecashsale/active?code="+code
				+"&password="+password+"'>"+"http://localhost:8080/thecashsale/active?code="+code+"</a><br>"+"<h4>"
				+ "如以上连接无法点击，请将其复制到浏览器中打开（请于5分钟内完成验证，逾期需重新注册）</h4>",
				"text/html;charset=UTF-8");
		System.out.println(message.getContent());
		//发送一封激活邮件
		Transport.send(message);
	}
	
}