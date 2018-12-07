package com.cashsale.controller.publish;

import com.alibaba.fastjson.JSONObject;
import com.cashsale.bean.DemandDO;
import com.cashsale.bean.ProductDO;
import com.cashsale.bean.ResultDTO;
import com.cashsale.dao.PublishDemandDAO;
import com.cashsale.util.CommonUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
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

/**
 * @Description: 发布需求（号召捐赠等）
 * @Author: 8-0416
 * @Date: 2018/12/3
 */
@WebServlet("/publishDemand")
public class PublishDemandServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        // 设置响应编码
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        BufferedReader br = request.getReader();
        String str,dem = "";
        while ((str = br.readLine()) != null) {
            dem += str;
        }
        System.out.println(dem);
        DemandDO demand = new Gson().fromJson(dem, DemandDO.class);
        PrintWriter writer = response.getWriter();
        
        //获取用户名
        //String username = demand.getUsername();

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

        ResultDTO result = new PublishDemandDAO().publishDemand(demand, username);
        writer.print(JSONObject.toJSON(result));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }
}
