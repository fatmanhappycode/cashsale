package com.cashsale.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.cashsale.util.MailUtil;
import com.cashsale.util.TimeUtil;

public class RegisterDAO {
	
	/** 提示进行邮箱激活的code */
	private static final int ACTIVATION_TIP = 109;
	/** 注册失败的code */
	private static final int REGISTER_FAILED = 102;
	/** 手机号已被注册 */
	private static final int NUMBER_IS_REGISTER = 103;
	/** 邮箱已被注册 */
	private static final int EMAIL_IS_REGISTER = 110;
	
	
	public int register(String username, String email, String encodedPass, String encodedCode, String nickname,
			String code, String password){
		
		Connection conn = new com.cashsale.conn.Conn().getCon();
        
        try {
        	//判断该用户名和邮箱是否已被注册
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM user_data WHERE user_name=?");
            pstmt.setString(1,username);
            ResultSet rs = pstmt.executeQuery();
            PreparedStatement pstmt2 = conn.prepareStatement("SELECT * FROM all_user WHERE user_name=?");
            pstmt2.setString(1, username);
            ResultSet rs2 = pstmt2.executeQuery();
            PreparedStatement pstmt3 = conn.prepareStatement("SELECT * FROM user_data WHERE email = ?");
            pstmt3.setString(1, email);
            ResultSet rs3 = pstmt3.executeQuery();
            
            if (rs.next() || rs2.next())  {
                return NUMBER_IS_REGISTER;
            } 
            else if(rs3.next()){
            	return EMAIL_IS_REGISTER;
            }
            else{
            	//将用户信息存进数据库中
            	pstmt = conn.prepareStatement("INSERT INTO user_data(user_name, nick_name,email,code) VALUES(?,?,?,?)");
            	pstmt.setString(1, username);
            	pstmt.setString(2, nickname);
            	pstmt.setString(3, email);
            	pstmt.setString(4, code);
            	
            	pstmt2 = conn.prepareStatement("INSERT INTO register_user VALUES(?,?)");
            	pstmt2.setString(1, username);
            	pstmt2.setString(2, password);
            	
            	//获取当前时间
            	String currentTime = TimeUtil.getTime();
                
            	//发送邮件
            	MailUtil.sendMail(email, encodedCode, username, encodedPass, nickname, currentTime);
            	
            	pstmt.execute();
            	pstmt2.execute();
            	return ACTIVATION_TIP;
            	//writer.print(JSONObject.toJSON(new ResultDTO<String>(109, null, "请到邮箱进行账号激活！")));
            }
        } 
        catch (Exception e) {
        	e.printStackTrace();
            return REGISTER_FAILED;
        }
	}
}
