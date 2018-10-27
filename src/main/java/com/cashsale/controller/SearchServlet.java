package com.cashsale.controler;

import com.alibaba.fastjson.JSONObject;
import com.cashsale.bean.Pager;
import com.cashsale.bean.Product;
import com.cashsale.bean.Result;
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
        // 构建jsonObject对象后解析参数
        JsonObject jsonObject = (JsonObject) new JsonParser().parse(searchByTitle);
        String title = jsonObject.get("title").getAsString();
        int currentPage = jsonObject.get("currentPage").getAsInt();
        // 添加模糊查询需要的%
        title = "%" + title + "%";
        // 根据页数计算出从第几条开始查询的offset
        int offset = (currentPage - 1) * 8;

        PrintWriter writer = resp.getWriter();
        Connection conn = new com.cashsale.conn.Conn().getCon();

        try{
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM product_info WHERE title LIKE ? LIMIT ?,8");
            pstmt.setString(1,title);
            pstmt.setInt(2,offset);
            ResultSet rs = pstmt.executeQuery();

            // 构造获取元数据，用以获取列数，列名，列对应的值等相关信息
            ResultSetMetaData metaData = rs.getMetaData();
            int cols_len = metaData.getColumnCount();

            // 用于存放每一件商品的信息
            List<Map<String, Object>> list = new ArrayList<>();
            List<Product> result = new ArrayList<>();

            while (rs.next()) {
                // 存放列名和对应值
                Map<String, Object> map = new HashMap<String, Object>();
                for (int i = 0; i < cols_len; i++) {
                    String cols_name = metaData.getColumnName(i + 1);
                    Object cols_value = rs.getObject(cols_name);
                    if (cols_value == null) {
                        cols_value = "";
                    }
                    map.put(cols_name, cols_value);
                }
                list.add(map);
            }
            if (list != null) {
                for (Map<String, Object> map : list) {
                    Product p = new Product(map);
                    result.add(p);
                }
            }
            Pager<Product> product = new Pager<>(currentPage+1,result);
            writer.print(JSONObject.toJSON(new Result<>(107, product,"查询成功")));
        } catch (Exception e) {
            writer.print(JSONObject.toJSON(new Result<String>(108,null,"查询失败")));
            e.printStackTrace();
        }
    }
}
