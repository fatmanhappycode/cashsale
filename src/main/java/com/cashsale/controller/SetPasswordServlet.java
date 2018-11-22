package com.cashsale.controller;

import com.alibaba.fastjson.JSONObject;
import com.cashsale.bean.CustomerDO;
import com.cashsale.bean.CustomerInfoDO;
import com.cashsale.bean.ResultDTO;
import com.cashsale.service.SetPasswordService;
import com.cashsale.util.CommonUtils;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/setPassword")
public class SetPasswordServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 设置响应编码
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        //设置请求编码
        request.setCharacterEncoding("utf-8");

        // 读取前端传过来的json串
        BufferedReader br = request.getReader();
        String str,user = "";
        while((str = br.readLine()) != null){
            user += str;
        }
        CustomerDO c = new Gson().fromJson(user, CustomerDO.class);
        String password = c.getPassword();
        String username = c.getUsername();

        //获取请求头token
        /*Cookie[] cookies = request.getCookies();
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

        PrintWriter writer = response.getWriter();
        ResultDTO result = new SetPasswordService().setPassword(username, password);
        writer.print(JSONObject.toJSON(result));
    }
}
