package com.cashsale.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.cashsale.bean.Result;
import com.cashsale.util.SearchUtil;

/**
 * 按标签分类
 * @author Sylvia
 * 2018年10月26日
 */
@WebServlet("/screenByLabel")
public class ScreenByLabelServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 设置响应编码
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        PrintWriter writer = response.getWriter();
        String label = request.getParameter("label");
        String page = request.getParameter("page");
        ArrayList<String> tapGroup = new ArrayList<String>();
        String query = "";
        
	    StringTokenizer tapToken = new StringTokenizer(label, ";");
	    while ( tapToken.hasMoreTokens() )
	    {
	        tapGroup.add(tapToken.nextToken());
	    }
	    //数据库中保存的label要用分号作为分割符，且字符串末尾也需要分号
	    if(!tapGroup.isEmpty())
	    {
	    	query = "label LIKE '%";
	    	for( int i = 0; i < tapGroup.size(); i++ )
	    	{
	    		query += tapGroup.get(i)+";%";
	    	}
	    	query += "'";
	    }
	   
        //System.out.println(query);
        
        Map<String, Object> map = new HashMap<String, Object>();
        map = SearchUtil.search(query, page);
        int code = (int) map.get("code");
        String queryResult = (String) map.get("queryResult");
        //System.out.println(queryResult);
        
        if( code == 115 )
        {
        	writer.println(JSONObject.toJSON(new Result<Object>(115,null,"查询失败！")));
        }
        else if(queryResult == null || queryResult.equals("") || queryResult.equals("[]"))
        {
        	writer.println(JSONObject.toJSON(new Result<Object>(116,null,"没有更多数据了……")));
        }
        else
        {
        	writer.println(JSONObject.toJSON(queryResult));
        }
	}
	
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doGet(request, response);
    }
}