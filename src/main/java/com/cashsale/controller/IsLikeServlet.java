package com.cashsale.controller;

import com.alibaba.fastjson.JSONObject;
import com.cashsale.bean.ResultDTO;
import com.cashsale.dao.GetInteractDAO;
import com.cashsale.service.IsLikeService;
import com.cashsale.service.UpdateScoreService;
import com.cashsale.util.CommonUtils;
import io.jsonwebtoken.Claims;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Description:判断用户是否已点赞该商品
 * @Author: 8-0416
 * @Date: 2018/11/28
 */
@WebServlet("/isLike")
public class IsLikeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置响应编码
        resp.setContentType("application/json;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        // 获取参数
        int productId = 0;
        String strProductId = req.getParameter("productId");
        if(strProductId != null && !strProductId.equals("")){
            productId = Integer.parseInt(strProductId);
        }
        //获取用户名
        String username = req.getParameter("username");
        //获取请求头token
        /*Cookie[] cookies = req.getCookies();
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
        String username = claims.getSubject();*/

        PrintWriter writer = resp.getWriter();

        ResultDTO<String> result = new IsLikeService().isLike(username,productId);
        writer.print(JSONObject.toJSON(result));

    }
}
