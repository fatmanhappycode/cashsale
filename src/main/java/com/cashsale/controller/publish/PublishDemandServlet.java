package com.cashsale.controller.publish;

import com.alibaba.fastjson.JSONObject;
import com.cashsale.bean.DemandDO;
import com.cashsale.bean.ResultDTO;
import com.cashsale.dao.PublishDemandDAO;
import com.cashsale.enums.ResultEnum;
import com.cashsale.util.CommonUtils;
import com.cashsale.util.SensitivewordFilterUtil;
import com.google.gson.Gson;
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
import java.util.Set;

/**
 * @Description: 发布需求
 * @Author: 8-0416
 * @Date: 2018/12/3
 */
@WebServlet("/publishDemand")
public class PublishDemandServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置响应编码
        resp.setContentType("application/json;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

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

        BufferedReader br = req.getReader();
        String str,de = "";
        while ((str = br.readLine()) != null) {
            de += str;
        }
        System.out.println(de);
        DemandDO demand = new Gson().fromJson(de, DemandDO.class);

        PrintWriter writer = resp.getWriter();
        String title = demand.getTitle();
        Set<String> filterTitle = new SensitivewordFilterUtil().getSensitiveWord(title,1);
        if (!filterTitle.isEmpty()) {
            writer.print(JSONObject.toJSON(new ResultDTO<String>(ResultEnum.PUBLISH_CONTENT_ERROR.getCode(),null,ResultEnum.PUBLISH_CONTENT_ERROR.getMsg())));
        }
        if (!filterTitle.isEmpty()) {
            return;
        }

        ResultDTO<String> result = new PublishDemandDAO().publishDemand(demand, username);
        writer.print(JSONObject.toJSON(result));
    }
}
