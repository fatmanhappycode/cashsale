package com.cashsale.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.cashsale.bean.ResultDTO;
import com.cashsale.service.ActiveService;
import com.cashsale.util.RSAUtil;

/**
 * 邮箱激活
 * @author Sylvia
 * 2018年10月17日
 */
@WebServlet("/active")
public class ActiveServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
		doPost(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		request.setCharacterEncoding("utf-8");
		
		// 接收激活码
		String code = request.getParameter("code");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String currentTime = request.getParameter("currentTime");
		System.out.println(username);
		
		// 设置响应编码
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
		
		PrintWriter writer = response.getWriter();
        //Connection conn = new com.cashsale.conn.Conn().getCon();
        
        //获取密钥
        String privateKey = (String) this.getServletContext().getAttribute(username);
        System.out.println("密钥:"+privateKey);
        try
        {
        	//解密
        	code = RSAUtil.privateDecrypt(code, RSAUtil.getPrivateKey(privateKey));
        	password = RSAUtil.privateDecrypt(password, RSAUtil.getPrivateKey(privateKey));
        	//System.out.println("解密后验证码:\r\n"+code);
        	//System.out.println("解密后密码:\r\n"+password);
        }
        catch(Exception e )
        {
        	System.err.println("解密失败！");
        	//writer.println(JSONObject.toJSON(new ResultDTO<String>(114, null, "解密失败！")));
        	e.printStackTrace();
        }
        
        ResultDTO<String> result = new ActiveService().UserActive(code, currentTime, username, password);
        writer.print(JSONObject.toJSON(result));
		
        /*try {
			System.out.println(code);
			//根据激活码查询用户
			PreparedStatement pstmt = conn.prepareStatement("SELECT user_name FROM user_data WHERE code = ?");
			pstmt.setString(1, code);
			ResultSet result = pstmt.executeQuery();
			//根据用户名查询密码
			PreparedStatement pstmt2 = conn.prepareStatement("SELECT pass_word FROM all_user WHERE user_name = ? ");
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
					pstmt = conn.prepareStatement("DELETE FROM all_user WHERE user_name = ?");
					pstmt.setString(1, username);
					pstmt.execute();
					pstmt2 = conn.prepareStatement("DELETE FROM user_data WHERE user_name = ?");
					pstmt2.setString(1, username);
					pstmt2.execute();
					writer.println(JSONObject.toJSON(new ResultDTO<String>(113, null, "验证码已过期，请重新注册！")));
				}
				else
				{
					writer.println(JSONObject.toJSON(new ResultDTO<String>(101, null, "激活成功！")));
				}
			}
			else
			{
				writer.println(JSONObject.toJSON(new ResultDTO<String>(111, null, "验证码有误，请重新激活！")));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			writer.println(JSONObject.toJSON(new ResultDTO<String>(112, null, "验证失败！")));
		}*/
	}
}