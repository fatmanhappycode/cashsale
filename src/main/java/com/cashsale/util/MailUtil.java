package com.cashsale.util;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 发送激活邮件
 * @author Sylvia
 * 2018年10月20日
 */
public class MailUtil {

	private static final String sendEmailAccount = "916061509@qq.com";
	private static final String sendEmailPassword = "dkmteeoetvlgbaij";

	/**
	 * 发送激活邮件
	 * @param to
	 * 			接收者
	 * @param code
	 * 			验证码
	 * @param username
	 * 			用户名
	 * @param nickname
	 * 			昵称
	 * @param currentTime
	 * 			发送邮件的时间
	 * @throws Exception
	 */
	public static void sendMail(String to, String code, String username/*, String password*/, String nickname, String currentTime) throws Exception
	{
		//创建连接对象，连接到邮箱服务器
		Properties props = new Properties();

		// 参数配置
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", "smtp.qq.com");
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");

        Session session = Session.getInstance(props, new Authenticator() {
			@Override
			//设置发送方的邮箱地址和密码   dkmteeoetvlgbaij
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(sendEmailAccount,sendEmailPassword);
			}
		});

        // 设置为debug模式, 可以查看详细的发送 log
        //session.setDebug(true);

		//System.out.println("MailUtil下的密码："+password);
		//System.out.println("MailUtil下的验证码："+code);

		//创建邮件对象
		Message message = new MimeMessage(session);
		//设置发件人
		String nick = javax.mail.internet.MimeUtility.encodeText("“现卖”邮箱管理员");
		message.setFrom(new InternetAddress(nick + " <"+sendEmailAccount+">"));
		//设置收件人
		message.setRecipient(RecipientType.TO, new InternetAddress(to));
		//设置邮件的主题
		message.setSubject("来自“现卖”的激活邮件");
		//设置邮件的正文
		message.setContent("<h1>亲爱的"+nickname+"：</h1>"+"<br><h3>欢迎加入现卖！<br>" + 
				"请点击下面的连接完成注册：<br></h3>" + "<a href='http://localhost:8080/cashsale/active?code="+code
				+"&username="+username+"&currentTime="+currentTime+"'>"+"点击验证"+"<h5>如以上连接无法点击，请将其复制到浏览器中打开（请于5"
				+ "分钟内完成验证，逾期需重新注册）</h5>",
				"text/html;charset=UTF-8");
		// 根据 Session 获取邮件传输对象
        Transport transport = session.getTransport();
        // 使用 邮箱账号 和 密码 连接邮件服务器, 这里认证的邮箱必须与 message 中的发件人邮箱一致, 否则会报错
        transport.connect(sendEmailAccount, sendEmailPassword);
		//发送一封激活邮件
		Transport.send(message);
	}

	/**
	 * 发送重置密码邮件
	 * @param to
	 * 			接收者
	 * @param code
	 * 			验证码
	 * @param username
	 * 			用户名
	 * @param currentTime
	 * 			发送时间
	 * @throws Exception
	 */
	public static void sendMail(String to, String code, String username, String currentTime) throws Exception {

		//创建连接对象，连接到邮箱服务器
		Properties props = new Properties();

		// 参数配置
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.smtp.host", "smtp.qq.com");
		props.setProperty("mail.smtp.auth", "true");
		props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.setProperty("mail.smtp.port", "465");
		props.setProperty("mail.smtp.socketFactory.port", "465");

		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			//设置发送方的邮箱地址和密码   dkmteeoetvlgbaij
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(sendEmailAccount,sendEmailPassword);
			}
		});

		//创建邮件对象
		Message message = new MimeMessage(session);
		//设置发件人
		String nick = javax.mail.internet.MimeUtility.encodeText("“现卖”邮箱管理员");
		message.setFrom(new InternetAddress(nick + " <"+sendEmailAccount+">"));
		//设置收件人
		message.setRecipient(RecipientType.TO, new InternetAddress(to));
		//设置邮件的主题
		message.setSubject("来自“现卖”的重置密码邮件");
		//设置邮件的正文
		message.setContent("<h1>您好：</h1>"+
						"您在现卖申请找回密码：<br>" + "<a href='http://localhost:8080/comfirmSet?code="+code
						+"&username="+username+"&currentTime="+currentTime+"'>"+"点击重置密码</a>"+"<h5>如以上连接无法点击，请将其复制到浏览器中打开（请于3"
						+ "分钟内完成验证，逾期需重新申请。如不是本人操作，请忽略本邮件，并及时修改密码！）</h5>",
				"text/html;charset=UTF-8");
		// 根据 Session 获取邮件传输对象
		Transport transport = session.getTransport();
		// 使用 邮箱账号 和 密码 连接邮件服务器, 这里认证的邮箱必须与 message 中的发件人邮箱一致, 否则会报错
		transport.connect(sendEmailAccount, sendEmailPassword);
		//发送一封激活邮件
		Transport.send(message);
	}
}