package com.cashsale.controller;

import com.alibaba.fastjson.JSONObject;
import com.cashsale.bean.ProductDO;
import com.cashsale.bean.ResultDTO;
import com.cashsale.util.SensitiveWordInitUtil;
import com.cashsale.util.SensitivewordFilterUtil;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Map;
import java.util.Set;

/**
 * @author 肥宅快乐码
 * @date 2018/10/17 - 22:30
 */
@WebServlet(urlPatterns = {"/publish"})
public class PublishServlet extends HttpServlet {
   
	private static final long serialVersionUID = 1L;

	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置响应编码
        resp.setContentType("application/json;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        BufferedReader br = req.getReader();
        String str,product = "";
        while ((str = br.readLine()) != null) {
            product += str;
        }
        System.out.println(product);
        ProductDO p = new Gson().fromJson(product, ProductDO.class);

        PrintWriter writer = resp.getWriter();

        String title = p.getTitle();
        Set<String> filterTitle = new SensitivewordFilterUtil().getSensitiveWord(title,1);
        if (!filterTitle.isEmpty()) {
            writer.print(JSONObject.toJSON(new ResultDTO<String>(109,null,"发布失败，含有非法字符")));
        }
        String label = p.getLabel();
        int price = p.getPrice();
        int tradeMethod = p.getTradeMethod();
        int isBargain = p.getIsBargain();
        String pdDescription = p.getPdDescription();
        String imageUrl = p.getImageUrl();

        Connection conn = new com.cashsale.conn.Conn().getCon();

        try{
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO product_info(title, label, price, trade_method, is_bargain, product_description, image_url) VALUES (?,?,?,?,?,?,?)");
            pstmt.setString(1,title);
            pstmt.setString(2,label);
            pstmt.setInt(3,price);
            pstmt.setInt(4,tradeMethod);
            pstmt.setInt(5,isBargain);
            pstmt.setString(6,pdDescription);
            pstmt.setString(7,imageUrl);
            pstmt.executeUpdate();
            writer.print(JSONObject.toJSON(new ResultDTO<String>(107,null,"发布成功")));
        } catch (Exception e) {
            writer.print(JSONObject.toJSON(new ResultDTO<String>(108,null,"发布失败")));
            e.printStackTrace();
        }
    }
}
