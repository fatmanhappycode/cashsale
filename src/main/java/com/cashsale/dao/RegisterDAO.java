package com.cashsale.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.cashsale.util.MailUtil;
import com.cashsale.util.TimeUtil;

/**
 * 注册
 * @author Sylvia
 * 2018年11月3日
 */
public class RegisterDAO {

	/** 提示进行邮箱激活的code */
	private static final int ACTIVATION_TIP = 109;
	/** 注册失败的code */
	private static final int REGISTER_FAILED = 102;
	/** 手机号已被注册 */
	private static final int NUMBER_IS_REGISTER = 103;
	/** 邮箱已被注册 */
	private static final int EMAIL_IS_REGISTER = 110;

	private Connection conn = new com.cashsale.conn.Conn().getCon();
	private PreparedStatement pstmt = null;
	private PreparedStatement pstmt2 = null;
	private PreparedStatement pstmt3 = null;
	private ResultSet rs = null;
	private ResultSet rs2 = null;
	private ResultSet rs3 = null;

	public int register(String username, String email, String encodedPass, String encodedCode, String nickname,
						String code, String password){

		try {
			//判断该用户名和邮箱是否已被注册
			pstmt = conn.prepareStatement("SELECT * FROM user_data WHERE user_name=?");
			pstmt.setString(1,username);
			rs = pstmt.executeQuery();
			pstmt2 = conn.prepareStatement("SELECT * FROM all_user WHERE user_name=?");
			pstmt2.setString(1, username);
			rs2 = pstmt2.executeQuery();
			pstmt3 = conn.prepareStatement("SELECT * FROM user_data WHERE email = ?");
			pstmt3.setString(1, email);
			rs3 = pstmt3.executeQuery();

			//System.out.println("rs.next()="+rs.next()+"rs2.next()="+rs2.next()+"rs3.next()="+rs3.next());
			if (rs.next() || rs2.next())  {
				closeConn();
				return NUMBER_IS_REGISTER;
			}
			else if(rs3.next()){
				closeConn();
				return EMAIL_IS_REGISTER;
			}
			else{
				//将用户信息存进数据库中
				pstmt2 = conn.prepareStatement("INSERT INTO register_user(user_name,pass_word,nick_name,email,code) VALUES(?,?,?,?,?)");
				pstmt2.setString(1, username);
				pstmt2.setString(2, password);
				pstmt2.setString(3, nickname);
				pstmt2.setString(4, email);
				pstmt2.setString(5, code);

				//获取当前时间
				String currentTime = TimeUtil.getTime();

				//发送邮件
				MailUtil.sendMail(email, encodedCode, username, encodedPass, nickname, currentTime);

				pstmt.execute();
				pstmt2.execute();

				closeConn();
				return ACTIVATION_TIP;
			}
		}
		catch (Exception e) {
			closeConn();
			e.printStackTrace();
			return REGISTER_FAILED;
		}
	}

	/** 关闭所有链接  */
	public void closeConn() {
		new com.cashsale.conn.Conn().closeConn(rs, pstmt, conn);
		new com.cashsale.conn.Conn().closeConn(rs2, pstmt2, conn);
		new com.cashsale.conn.Conn().closeConn(rs3, pstmt3, conn);
	}
}
