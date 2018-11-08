package com.cashsale.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.cashsale.bean.ResultDTO;
import com.cashsale.service.UpdateScoreService;

/**
 * 商品评分  and 用户信用评分
 * @author Sylvia
 * 2018年11月3日
 */
public class ScoreServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		doPost(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//设置响应编码
		response.setContentType("application/json;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
        //设置请求编码
        request.setCharacterEncoding("utf-8");
        
        //对商品进行的操作编码
        String strCode = request.getParameter("scoreCode");
        //浏览者
        String username = request.getParameter("username");
        //被浏览的商品
        String strProductId = request.getParameter("productId");
        
        ResultDTO<String> result = new UpdateScoreService().updateScore(username, strProductId, strCode);
        PrintWriter writer = response.getWriter();
        writer.println(JSONObject.toJSONString(result));
	}
}
