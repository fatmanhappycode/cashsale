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
	
	private Connection conn = new com.cashsale.conn.Conn().getCon();
	private PreparedStatement pstmt = null;
	private PreparedStatement pstmt2 = null;
	private ResultSet result = null;
	private ResultSet result2 = null;
	
	public int active(String code, String currentTime, String username, String password) {
		
		try {
			//System.out.println(code);
			//根据激活码查询用户
			pstmt = conn.prepareStatement("SELECT user_name FROM user_data WHERE code = ?");
			pstmt.setString(1, code);
			result = pstmt.executeQuery();
			//根据用户名查询密码
			pstmt2 = conn.prepareStatement("SELECT pass_word FROM all_user WHERE user_name = ? ");
			//result.next();
			result2 = null;
		
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
					pstmt = conn.prepareStatement("DELETE FROM all_user WHERE user_name = ?");
					pstmt.setString(1, username);
					pstmt.execute();
					pstmt2 = conn.prepareStatement("DELETE FROM user_data WHERE user_name = ?");
					pstmt2.setString(1, username);
					pstmt2.execute();
					//writer.println(JSONObject.toJSON(new Result<String>(113, null, "验证码已过期，请重新注册！")));
					closeConn();
					return CODE_IS_EXPIRE;
				}
				else
				{
					//writer.println(JSONObject.toJSON(new Result<String>(101, null, "激活成功！")));
					closeConn();
					return ACTIVE_SUCCESSED;
				}
			}
			else
			{
				//writer.println(JSONObject.toJSON(new Result<String>(111, null, "验证码有误，请重新激活！")));
				closeConn();
				return CODE_IS_WRONG;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			//writer.println(JSONObject.toJSON(new Result<String>(112, null, "验证失败！")));
			closeConn();
			return VERTIFY_FAILED;
		}
	}
	
	/** 关闭所有链接 */
	public void closeConn() {
		new com.cashsale.conn.Conn().closeConn(result, pstmt, conn);
    	new com.cashsale.conn.Conn().closeConn(result2, pstmt2, conn);
	}
}
