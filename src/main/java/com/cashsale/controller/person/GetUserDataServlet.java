package com.cashsale.controller.person;

import com.alibaba.fastjson.JSONObject;
import com.cashsale.bean.CustomerDO;
import com.cashsale.bean.ResultDTO;
import com.cashsale.service.UserService;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author 肥宅快乐码
 * @date 2018/11/13 - 22:12
 */
@WebServlet("/GetUserData")
public class GetUserDataServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置响应编码
        resp.setContentType("application/json;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String username = req.getParameter("username");

        PrintWriter writer = resp.getWriter();

        ResultDTO<String> result = new UserService().isConfirm(username);
        writer.print(JSONObject.toJSON(result));
    }
}
