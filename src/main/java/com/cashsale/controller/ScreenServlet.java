package com.cashsale.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.cashsale.bean.PagerDTO;
import com.cashsale.bean.ResultDTO;
import com.cashsale.service.ScreenService;

/**
 * 筛选
 * @author Sylvia
 * 2018年10月23日
 */
@WebServlet("/screen")
public class ScreenServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 设置响应编码
		response.setContentType("application/json;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		PrintWriter writer = response.getWriter();
		String label = request.getParameter("label");
		String price = request.getParameter("price");
		String tradeMethod = request.getParameter("tradMethod");
		String isBargain = request.getParameter("isBargain");
		String page = request.getParameter("currentPage");
		ArrayList<String> tapGroup = new ArrayList<String>();
		ArrayList<String> priceGroup = new ArrayList<String>();
		ArrayList<String> queryList = new ArrayList<String>();
		String query = "";

		StringTokenizer tapToken = new StringTokenizer(label, ";");
		while ( tapToken.hasMoreTokens() )
		{
			tapGroup.add(tapToken.nextToken());
		}
		//数据库中保存的label要用分号作为分割符，且字符串末尾也需要分号
		if(!tapGroup.isEmpty())
		{
			String temp = "label LIKE '%";
			for( int i = 0; i < tapGroup.size(); i++ )
			{
				temp += tapGroup.get(i)+";%";
			}
			temp += "'";
			queryList.add(temp);
		}
		if( tradeMethod != null && !tradeMethod.equals("") )
		{
			queryList.add("trade_method = " + tradeMethod);
		}
		if( isBargain != null && !isBargain.equals("") )
		{
			queryList.add("is_bargain = " + isBargain );
		}

		StringTokenizer priceToken = new StringTokenizer(price, ";");
		while ( priceToken.hasMoreTokens() )
		{
			priceGroup.add(priceToken.nextToken());
		}

		int i = 0;
		for( ; i < queryList.size(); i ++ )
		{
			if ( i == queryList.size() - 1 )
			{
				query += queryList.get(i);
			}
			else
			{
				query += queryList.get(i) + " AND ";
			}
		}
		if ( !priceGroup.isEmpty() )
		{
			if ( !queryList.isEmpty() )
			{
				query += " AND ("+"price>="+priceGroup.get(0)+" AND "+"price<="+priceGroup.get(1)+")";
			}
			else {
				query += "price>"+priceGroup.get(0)+" AND "+"price<"+priceGroup.get(1);
			}
		}

		ResultDTO<PagerDTO> result = new ScreenService().screen(query, page);
		writer.println(JSONObject.toJSON(result));
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
