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
		
		//接收激活码
		String code = request.getParameter("code");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String currentTime = request.getParameter("currentTime");
		
		// 设置响应编码
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
		
		PrintWriter writer = response.getWriter();
        
        //获取密钥
        String privateKey = (String) this.getServletContext().getAttribute(username);
        try
        {
        	//解密
        	code = RSAUtil.privateDecrypt(code, RSAUtil.getPrivateKey(privateKey));
        	password = RSAUtil.privateDecrypt(password, RSAUtil.getPrivateKey(privateKey));
        }
        catch(Exception e )
        {
        	System.err.println("解密失败！");
        	e.printStackTrace();
        }
        
        ResultDTO<String> result = new ActiveService().UserActive(code, currentTime, username, password);
        writer.print(JSONObject.toJSON(result));
	}
}