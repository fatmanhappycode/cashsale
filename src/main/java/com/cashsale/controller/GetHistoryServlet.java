package com.cashsale.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.cashsale.bean.PagerDTO;
import com.cashsale.bean.ResultDTO;
import com.cashsale.service.GetHistoryService;
import com.cashsale.util.CommonUtils;
import io.jsonwebtoken.Claims;

/**
 * 获取聊天记录
 * @author Sylvia
 * 2018年11月3日
 */
@WebServlet("/getHistory")
public class GetHistoryServlet extends HttpServlet{

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 设置响应编码
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        //设置请求编码
        request.setCharacterEncoding("utf-8");

        String strPage = request.getParameter("page");
        //String sender = request.getParameter("sender");
        String receiver = request.getParameter("receiver");
        PrintWriter write = response.getWriter();

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

        //获取历史记录
        ResultDTO<PagerDTO> result = new GetHistoryService().getHistory(username,receiver, strPage);
        write.println(JSONObject.toJSON(result));
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }
}
