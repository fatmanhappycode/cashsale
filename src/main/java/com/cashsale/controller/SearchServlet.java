package com.cashsale.controler;

import com.alibaba.fastjson.JSONObject;
import com.cashsale.bean.Pager;
import com.cashsale.bean.Product;
import com.cashsale.bean.Result;
import com.cashsale.service.SearchService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 肥宅快乐码
 * @date 2018/10/23 - 21:47
 */
@WebServlet("/search")
public class SearchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置响应编码
        resp.setContentType("application/json;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        // 获取参数
        String searchByTitle = req.getParameter("searchByTitle");
        Result<Pager> result = new SearchService().searchByTitle(searchByTitle);

        PrintWriter writer = resp.getWriter();
        writer.print(JSONObject.toJSON(result));
    }
}
