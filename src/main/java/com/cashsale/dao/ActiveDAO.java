package com.cashsale.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.cashsale.util.TimeUtil;

public class ActiveDAO {

	/** 验证码过期的code */
	private static final int CODE_IS_EXPIRE = 113;
	/** 激活成功 */
	private static final int ACTIVE_SUCCESSED = 101;
	/** 验证码有误 */
	private static final int CODE_IS_WRONG = 111;
	/** 验证失败 */
	private static final int VERTIFY_FAILED = 112;
	
	public int active(String code, String currentTime, String username, String password) {
		
		try {
			Connection conn = new com.cashsale.conn.Conn().getCon();
			//System.out.println(code);
			//根据激活码查询用户
			PreparedStatement pstmt = conn.prepareStatement("SELECT user_name FROM user_data WHERE code = ?");
			pstmt.setString(1, code);
			ResultSet result = pstmt.executeQuery();
			//根据用户名查询密码
			PreparedStatement pstmt2 = conn.prepareStatement("SELECT pass_word FROM register_user WHERE user_name = ? ");
			//result.next();
			ResultSet result2 = null;
		
			if( result.next() )
			{
				pstmt2.setString(1, result.getString(1));
				result2 = pstmt2.executeQuery();
			}
			
			//result2.next();
			//若根据验证码找得到该用户，且密码正确，时间未超过五分钟，则验证通过
			if( result2.next() || result2.getString(1).equals(password) )
			{
				pstmt = conn.prepareStatement("UPDATE user_data SET code = ?,state = ? 	WHERE user_name=?");
				pstmt.setString(1, null);
				pstmt.setBoolean(2, true);
				pstmt.setString(3, username);
				pstmt.execute();
				
				//判断验证时间是否超过五分钟，若超过则删除该用户的注册信息
				if( !TimeUtil.emailTime(currentTime) )
				{
					pstmt = conn.prepareStatement("DELETE FROM register_user WHERE user_name = ?");
					pstmt.setString(1, username);
					pstmt.execute();
					pstmt2 = conn.prepareStatement("DELETE FROM user_data WHERE user_name = ?");
					pstmt2.setString(1, username);
					pstmt2.execute();
					//writer.println(JSONObject.toJSON(new Result<String>(113, null, "验证码已过期，请重新注册！")));
					return CODE_IS_EXPIRE;
				}
				else
				{
					// 删除注册表的信息
					pstmt = conn.prepareStatement("DELETE FROM register_user WHERE user_name = ?");
					pstmt.setString(1, username);
					pstmt.execute();
					pstmt2 = conn.prepareStatement("INSERT INTO all_user(user_name,pass_word) VALUES (?,?)");
					pstmt2.setString(1, username);
					pstmt2.setString(2,password);
					pstmt2.execute();
					//writer.println(JSONObject.toJSON(new Result<String>(101, null, "激活成功！")));
					return ACTIVE_SUCCESSED;
				}
			}
			else
			{
				//writer.println(JSONObject.toJSON(new Result<String>(111, null, "验证码有误，请重新激活！")));
				return CODE_IS_WRONG;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			//writer.println(JSONObject.toJSON(new Result<String>(112, null, "验证失败！")));
			return VERTIFY_FAILED;
		}
	}
}
