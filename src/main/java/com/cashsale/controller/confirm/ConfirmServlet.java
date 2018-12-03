package com.cashsale.controller.confirm;

import com.alibaba.fastjson.JSONObject;
import com.cashsale.bean.ResultDTO;
import com.cashsale.dao.ConfirmDAO;
import com.cashsale.service.UserService;
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

/**
 * @author 肥宅快乐码
 * @date 2018/11/1 - 13:08
 */
@WebServlet("/confirm")
public class ConfirmServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 设置响应编码
        resp.setContentType("application/json;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        // 获取响应体的json串
        BufferedReader br = req.getReader();
        String str,user = "";
        while((str = br.readLine()) != null){
            user += str;
        }

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

        // 解析json串
        JsonObject jsonObject = (JsonObject) new JsonParser().parse(user);
        String encoded = jsonObject.get("encode").getAsString();
        String sno = jsonObject.get("sno").getAsString();

        UserService comfirmUser = new UserService();
        ResultDTO result = comfirmUser.userComfirm(encoded);

        if (result.getMsg().equals("认证成功")) {
            new ConfirmDAO().Confirm(username,sno);
        }

        PrintWriter writer = resp.getWriter();

        writer.print(JSONObject.toJSON(result));

    }
}
