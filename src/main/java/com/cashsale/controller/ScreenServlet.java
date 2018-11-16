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
		String tradePlace = request.getParameter("trandPlace");
		ArrayList<String> tapGroup = new ArrayList<String>();
		//ArrayList<String> priceGroup = new ArrayList<String>();
		ArrayList<String> queryList = new ArrayList<String>();
		String query = "";

		StringTokenizer tapToken = new StringTokenizer(label, ";");
		while ( tapToken.hasMoreTokens() )
		{
			tapGroup.add(tapToken.nextToken());
		}
		//数据库中保存的label要用分号作为分割符
		if(!tapGroup.isEmpty())
		{
			String temp = "label LIKE '%";
			for( int i = 0; i < tapGroup.size(); i++ )
			{
				if(i == tapGroup.size() - 1){
					temp += tapGroup.get(i)+"%";
				}else {
					temp += tapGroup.get(i) + ";%";
				}
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
		if( tradePlace != null && !tradePlace.equals("")){
			queryList.add("trade_place = " + tradePlace);
		}

		String[] priceGroup = price.split(";");
		/*System.out.println("priceGroup[0]="+priceGroup[0]);
		System.out.println("priceGroup[1]="+priceGroup[1]);*/

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
		if ( priceGroup.length > 0 )
		{
			if ( !queryList.isEmpty() )
			{
				if(priceGroup[0] == null || priceGroup[0].equals("")){
					query += " AND "+"price<="+priceGroup[1];
				}else if(priceGroup.length == 2){
					if(priceGroup[1] == null || priceGroup[1].equals("")) {
						query += " AND " + "price>=" + priceGroup[0];
					}else{
						query += " AND (" + "price>=" + priceGroup[0] + " AND " + "price<=" + priceGroup[1] + ")";
					}
				}
			}
			else {
				if(priceGroup[0] == null || priceGroup[0].equals("")){
					query += "price<="+priceGroup[1]+")";
				}else if(priceGroup.length == 2){
					if(priceGroup[1] == null || priceGroup[1].equals("")){
						query += "price>="+priceGroup[0];
					}else{
						query += "price>=" + priceGroup[0] + " AND " + "price<=" + priceGroup[1];
					}
				}
			}
		}

		//System.out.println(query);

		ResultDTO<PagerDTO> result = new ScreenService().screen(query, page);
		writer.println(JSONObject.toJSON(result));
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
