package com.cashsale.controller.search;

import com.alibaba.fastjson.JSONObject;
import com.cashsale.bean.PagerDTO;
import com.cashsale.bean.ProductDO;
import com.cashsale.bean.ResultDTO;
import com.cashsale.dao.GetInteractDAO;
import com.cashsale.enums.ResultEnum;
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
 * @date 2018/10/27 - 13:47
 */
@WebServlet("/sort")
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
        // String sortElement = jsonObject.get("sortElement").getAsString();
        boolean sortMethod = jsonObject.get("sortMethod").getAsBoolean();
        int currentPage = jsonObject.get("currentPage").getAsInt();
        // 根据页数计算出从第几条开始查询的offset
        int offset = (currentPage - 1) * 8;

        PrintWriter writer = resp.getWriter();
        Connection conn = new com.cashsale.conn.Conn().getCon();

        try{
            ResultSet rs;
            if (sortMethod) {
                PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM product_info ORDER BY price DESC LIMIT ?,8");
                pstmt.setInt(1,offset);
                rs = pstmt.executeQuery();
            } else {
                PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM product_info ORDER BY price LIMIT ?,8");
                pstmt.setInt(1,offset);
                rs = pstmt.executeQuery();
            }

            // 构造获取元数据，用以获取列数，列名，列对应的值等相关信息
            ResultSetMetaData metaData = rs.getMetaData();
            int cols_len = metaData.getColumnCount();

            // 用于存放每一件商品的信息
            List<Map<String, Object>> list = new ArrayList<>();
            List<ProductDO> result = new ArrayList<>();

            while (rs.next()) {
                int commentsNumber = new GetInteractDAO().getCommentNumber(rs.getInt("product_id"));
                int likeNumber = new GetInteractDAO().getLikeNumber(rs.getInt("product_id"));
                int shareNumber = new GetInteractDAO().getShareNumber(rs.getInt("product_id"));
                // 存放列名和对应值
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("commentsNumber",commentsNumber);
                map.put("likeNumber",likeNumber);
                map.put("shareNumber",shareNumber);
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
                    ProductDO p = new ProductDO(map);
                    result.add(p);
                }
            }
            PagerDTO<ProductDO> product = new PagerDTO<>(currentPage+1,result);
            writer.print(JSONObject.toJSON(new ResultDTO<>(ResultEnum.SEARCH_SUCCESS.getCode(), product,ResultEnum.SEARCH_SUCCESS.getMsg())));
        } catch (Exception e) {
            writer.print(JSONObject.toJSON(new ResultDTO<String>(ResultEnum.ERROR.getCode(),null,ResultEnum.ERROR.getMsg())));
            e.printStackTrace();
        }
    }
}
