package com.cashsale.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.cashsale.enums.ResultEnum;
import com.cashsale.util.TimeUtil;

/**
 * @author Sylvia
 * 2018年11月3日
 */
public class ActiveDAO {

	private Connection conn = new com.cashsale.conn.Conn().getCon();
	private PreparedStatement pstmt = null;
	private PreparedStatement pstmt2 = null;
	private PreparedStatement pstmt3 = null;
	private ResultSet result = null;
	private ResultSet result2 = null;

	public int active(String code, String currentTime, String username/*, String password*/) {
		try {
			//根据激活码查询用户
			pstmt = conn.prepareStatement("SELECT user_name FROM register_user WHERE code = ?");
			pstmt.setString(1, code);
			result = pstmt.executeQuery();
			//根据用户名查询密码
			/*pstmt2 = conn.prepareStatement("SELECT pass_word FROM register_user WHERE user_name = ? ");
			result2 = null;

			if( result.next() )
			{
				pstmt2.setString(1, result.getString(1));
				result2 = pstmt2.executeQuery();
			}*/

			//若根据验证码找得到该用户，且用户名正确，时间未超过五分钟，则验证通过
			if( result.next() && result.getString("user_name").equals(username) )
			{
				System.out.println(1);
				//判断验证时间是否超过五分钟，若超过则删除该用户的注册信息
				if( TimeUtil.emailTime(currentTime) )
				{
					System.out.println(2);
					pstmt = conn.prepareStatement("SELECT * FROM register_user WHERE code = ?");
					pstmt.setString(1, code);
					result = pstmt.executeQuery();

					pstmt2 = conn.prepareStatement("INSERT INTO user_data(user_name, credit, nick_name, email) VALUES (?,600,?,?)");
					pstmt3 = conn.prepareStatement("INSERT INTO all_user(user_name,pass_word) VALUES (?,?)");
					if (result.next()) {
						pstmt2.setString(1,result.getString("user_name"));
						pstmt2.setString(2,result.getString("nick_name"));
						pstmt2.setString(3,result.getString("email"));
						pstmt3.setString(1,result.getString("user_name"));
						pstmt3.setString(2,result.getString("pass_word"));
						pstmt2.executeUpdate();
						pstmt3.executeUpdate();
					}

					pstmt2 = conn.prepareStatement("DELETE FROM register_user WHERE user_name = ?");
					pstmt2.setString(1, username);
					pstmt2.execute();
					pstmt2.execute();

					closeConn();
					return ResultEnum.ACTIVE_SUCCESS.getCode();
				}
				else
				{
					closeConn();
					return ResultEnum.ACTIVE_TIME_ERROR.getCode();
				}
			}
			else
			{
				closeConn();
				return ResultEnum.ACTIVE_ERROR.getCode();
			}
		} catch(Exception e) {
			e.printStackTrace();
			closeConn();
			return ResultEnum.ERROR.getCode();
		}
	}

	/** 关闭所有链接 */
	public void closeConn() {
		new com.cashsale.conn.Conn().closeConn(result, pstmt, conn);
		new com.cashsale.conn.Conn().closeConn(result2, pstmt2, conn);
	}
}
