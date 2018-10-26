package com.cashsale.controler;

import com.alibaba.fastjson.JSONObject;
import com.cashsale.bean.Customer;
import com.cashsale.bean.Result;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author 肥宅快乐码
 * @date 2018/10/23 - 21:47
 */
public class SearchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置响应编码
        resp.setContentType("application/json;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String searchByTitle = req.getParameter("searchByTitle");
        JsonObject jsonObject = (JsonObject) new JsonParser().parse(searchByTitle);
        String title = "";

        PrintWriter writer = resp.getWriter();
        Connection conn = new com.cashsale.conn.Conn().getCon();

        try{
            PreparedStatement pstmt = conn.prepareStatement("SELECT INTO product_info WHERE title LIKE %?%");
            pstmt.setString(1,title);
            ResultSet rs = pstmt.executeQuery();

            writer.print(JSONObject.toJSON(new Result<String>(107,null,"查询成功")));
        } catch (Exception e) {
            writer.print(JSONObject.toJSON(new Result<String>(108,null,"发布失败")));
            e.printStackTrace();
        }
    }
}
