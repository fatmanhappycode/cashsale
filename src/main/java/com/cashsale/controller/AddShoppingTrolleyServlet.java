/*
package com.cashsale.controller;

import com.alibaba.fastjson.JSONObject;
import com.cashsale.bean.ResultDTO;
import com.cashsale.service.ShoppingTrolleyService;
import com.cashsale.util.CommonUtils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.jsonwebtoken.Claims;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

*/
/**
 * @author 肥宅快乐码
 * @date 2018/11/15 - 15:38
 *//*

@WebServlet("/AddShoppingTrolley")
public class AddShoppingTrolleyServlet extends HttpServlet {
    @Override
    protected void doPost (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置响应编码
        resp.setContentType("application/json;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        //获取请求头token
        Cookie[] cookies = req.getCookies();
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

        BufferedReader br = req.getReader();
        String str,product = "";
        while((str = br.readLine()) != null){
            product += str;
        }

        // 解析json串
        JsonObject jsonObject = (JsonObject) new JsonParser().parse(product);
        String productId = jsonObject.get("productId").getAsString();

        PrintWriter writer = resp.getWriter();

        ResultDTO<String> result = new ShoppingTrolleyService().addShoppingTrolley(username,productId);
        writer.print(JSONObject.toJSON(result));
    }
}
*/
