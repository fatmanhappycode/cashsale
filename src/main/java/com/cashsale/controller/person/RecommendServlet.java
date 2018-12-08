/**
 *
 */
package com.cashsale.controller.person;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.cashsale.bean.PagerDTO;
import com.cashsale.bean.ProductDO;
import com.cashsale.bean.ResultDTO;
import com.cashsale.service.RecommendService;

/**
 * 基于用户的协同过滤算法推荐商品
 * @author Sylvia
 * 2018年11月3日
 */
@WebServlet("/recommend")
public class RecommendServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 设置响应编码
		response.setContentType("application/json;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		PrintWriter writer = response.getWriter();

		String username = request.getParameter("username");
		String page = request.getParameter("currentPage");
		if(username != null && !username.equals("")) {
			ResultDTO<PagerDTO> result = new RecommendService().getList(username, page);
			writer.print(JSONObject.toJSON(result));
		}else{
			writer.print(JSONObject.toJSON(null));
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		doGet(request,response);
	}
}
