package com.cashsale.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.cashsale.bean.Result;
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
		//接收激活码
		String code = request.getParameter("code");
		//String username = request.getParameter("username");
		String password = request.getParameter("password");
		//System.out.println(code);
		
		// 设置响应编码
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
		
		//根据激活码查询用户
		PrintWriter writer = response.getWriter();
        Connection conn = new com.cashsale.conn.Conn().getCon();
        
		try {
			System.out.println(code);
			PreparedStatement pstmt = conn.prepareStatement("SELECT user_name FROM user_data WHERE code = ?");
			pstmt.setString(1, code);
			ResultSet result = pstmt.executeQuery();
			
			PreparedStatement pstmt2 = conn.prepareStatement("SELECT pass_word FROM all_user WHERE user_name = ? ");
			result.next();
			pstmt2.setString(1, result.getString(1));
			ResultSet result2 = pstmt2.executeQuery();
			result2.next();
			if( result2.getString(1).equals(password) )
			{
				pstmt = conn.prepareStatement("UPDATE user_data SET code = ?,state = ? 	WHERE user_name=?");
				pstmt.setString(1, null);
				pstmt.setBoolean(2, true);
				pstmt.setString(3, result.getString(1));
				pstmt.execute();
				writer.println(JSONObject.toJSON(new Result<String>(101, null, "激活成功！")));
			}
			else
			{
				writer.println(JSONObject.toJSON(new Result<String>(111, null, "验证码有误，请重新激活！")));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			writer.println(JSONObject.toJSON(new Result<String>(112, null, "验证失败！")));
		}
	}
}