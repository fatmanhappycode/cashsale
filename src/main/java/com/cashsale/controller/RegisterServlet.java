package com.cashsale.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.cashsale.bean.CustomerData;
import com.cashsale.bean.Result;
import com.cashsale.util.MailUtil;
import com.cashsale.util.UUIDUtil;
import com.google.gson.Gson;

/**
 * 用户登录方法
 * @author Sylvia
 * 2018年10月14日
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    	// 设置响应编码
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        BufferedReader br = request.getReader();
        String str,user = "";
        while((str = br.readLine()) != null){
            user += str;
        }
        
        CustomerData c = new Gson().fromJson(user,CustomerData.class);
        String username = c.getUsername();
        String password = c.getPassword();
        String nickname = c.getNickname();
        String email = c.getEmail();
        
        //生成一个64位的随机验证码
        String code = UUIDUtil.getUUID() + UUIDUtil.getUUID();

        PrintWriter writer = response.getWriter();
        Connection conn = new com.cashsale.conn.Conn().getCon();
        
        try {
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
                writer.print(JSONObject.toJSON(new Result<String>(103, null, "该手机号已被注册！")));
            } 
            else if(rs3.next()){
            	writer.print(JSONObject.toJSON(new Result<String>(110, null, "该邮箱已被注册！")));
            }
            else{
            	pstmt = conn.prepareStatement("INSERT INTO user_data(user_name, real_name,email,code) VALUES(?,?,?,?)");
            	pstmt.setString(1, username);
            	pstmt.setString(2, nickname);
            	pstmt.setString(3, email);
            	pstmt.setString(4, code);
            	
            	pstmt2 = conn.prepareStatement("INSERT INTO all_user VALUES(?,?)");
            	pstmt2.setString(1, username);
            	pstmt2.setString(2, password);
            	
            	//发送邮件
            	MailUtil.sendMail(email, code, password, nickname);
            	
            	pstmt.execute();
            	pstmt2.execute();
            	writer.print(JSONObject.toJSON(new Result<String>(109, null, "请到邮箱进行账号激活！")));
            }
        } 
        catch (Exception e) {
            writer.print(JSONObject.toJSON(new Result<String>(102,null,"注册失败！")));
        }
    }
}