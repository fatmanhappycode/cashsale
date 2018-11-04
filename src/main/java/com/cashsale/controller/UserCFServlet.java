/**
 * 
 */
package com.cashsale.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.cashsale.service.ListUserService;

/**
 * 基于用户的协同过滤算法推荐商品
 * @author Sylvia
 * 2018年11月3日
 */
public class UserCFServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws   ServletException, IOException {
		// 设置响应编码
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        PrintWriter writer = response.getWriter();
        
        String username = request.getParameter("username");
        String result = new ListUserService().getList(username);
        writer.print(JSONObject.toJSON(result));
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws   ServletException, IOException {
		doGet(request,response);
	}
}
