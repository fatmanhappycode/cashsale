package com.cashsale.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.cashsale.bean.CustomerDO;
import com.cashsale.bean.ResultDTO;
import com.cashsale.bean.ScoreDTO;
import com.cashsale.service.UpdateScoreService;
import com.cashsale.util.CommonUtils;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;

/**
 * 商品评分  and 用户信用评分
 * @author Sylvia
 * 2018年11月3日
 */
@WebServlet("/score")
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


		BufferedReader br = request.getReader();
		String str,score = "";
		while((str = br.readLine()) != null){
			score += str;
		}
		ScoreDTO s = new Gson().fromJson(score, ScoreDTO.class);
		//对商品进行的操作编码
		String strCode = s.getScoreCode();
		//被浏览的商品
		int productId = s.getProductId();
		//评论内容
		String comments = s.getComments();

		//浏览者
		//获取请求头token
		Cookie[] cookies = request.getCookies();
		String token = "";
		for (Cookie cookie : cookies) {
			switch(cookie.getName()){
				case "token":
					token = cookie.getValue();
					break;
				default:
					break;
			}
		}
		Claims claims = null;
		try {
			claims = CommonUtils.parseJWT(token);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String username = claims.getSubject();

		ResultDTO<String> result = new UpdateScoreService().updateScore(username, productId, strCode, comments);
		PrintWriter writer = response.getWriter();
		writer.println(JSONObject.toJSONString(result));
	}
}
