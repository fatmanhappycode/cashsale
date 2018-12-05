package com.cashsale.controller.interact;

import com.alibaba.fastjson.JSONObject;
import com.cashsale.bean.ResultDTO;
import com.cashsale.bean.UserInteractDTO;
import com.cashsale.dao.UserInteractDAO;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Description: 用户之间的关注
 * @Author: 8-0416
 * @Date: 2018/12/4
 */
@WebServlet("/concern")
public class UserInteractServlet extends HttpServlet{

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // 设置响应编码
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        // 读取前端传过来的json串
        BufferedReader br = request.getReader();
        String str,user = "";
        while((str = br.readLine()) != null){
            user += str;
        }
        UserInteractDTO c = new Gson().fromJson(user, UserInteractDTO.class);
        //获取被关注者
        String concern = c.getConcern();
        //获取用户名
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
        ResultDTO result = new UserInteractDAO().concern(username, concern);
        writer.println(JSONObject.toJSON(result));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }
}
