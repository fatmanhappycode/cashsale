package com.cashsale.controler;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 肥宅快乐码
 * @date 2018/10/27 - 13:47
 */
public class SortServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置响应编码
        resp.setContentType("application/json;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        // 获取参数
        String sort = req.getParameter("sort");
        // 构建jsonObject对象后解析参数
        JsonObject jsonObject = (JsonObject) new JsonParser().parse(sort);
        String sortElement = jsonObject.get("sortElement").getAsString();
    }
}
