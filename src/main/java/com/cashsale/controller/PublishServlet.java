package com.cashsale.controller;

import com.alibaba.fastjson.JSONObject;
import com.cashsale.bean.ProductDO;
import com.cashsale.bean.ResultDTO;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
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
            writer.print(JSONObject.toJSON(new ResultDTO<String>(ResultEnum.PUBLISH_CONTENT_ERROR.getCode(),null,ResultEnum.PUBLISH_CONTENT_ERROR.getMsg())));
        }
        if (!filterTitle.isEmpty()) {
            return;
        }
        String label = p.getLabel();
        double price = p.getPrice();
        int tradeMethod = p.getTradeMethod();
        int isBargain = p.getIsBargain();
        String pdDescription = p.getPdDescription();
        String imageUrl = p.getImageUrl();

        Connection conn = new com.cashsale.conn.Conn().getCon();

        try{
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO product_info(title, label, price, trade_method, is_bargain, product_description, image_url,user_name) VALUES (?,?,?,?,?,?,?,?)");
            pstmt.setString(1,title);
            pstmt.setString(2,label);
            pstmt.setDouble(3,price);
            pstmt.setInt(4,tradeMethod);
            pstmt.setInt(5,isBargain);
            pstmt.setString(6,pdDescription);
            pstmt.setString(7,imageUrl);
            pstmt.setString(8,username);
            pstmt.executeUpdate();
            writer.print(JSONObject.toJSON(new ResultDTO<String>(ResultEnum.PUBLISH_SUCCESS.getCode(),null,ResultEnum.PUBLISH_SUCCESS.getMsg())));
        } catch (Exception e) {
            writer.print(JSONObject.toJSON(new ResultDTO<String>(ResultEnum.ERROR.getCode(),null,ResultEnum.ERROR.getMsg())));
            e.printStackTrace();
        }
    }
}
