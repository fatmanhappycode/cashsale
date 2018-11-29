package com.cashsale.controller.product;

import com.alibaba.fastjson.JSONObject;
import com.cashsale.bean.ResultDTO;
import com.cashsale.dao.ListProductDAO;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author 肥宅快乐码
 * @date 2018/11/17 - 2:57
 */
@WebServlet("/getShoppingTrolley")
public class GetShoppingTrolleyServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置响应编码
        resp.setContentType("application/json;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        BufferedReader br = req.getReader();
        String str,user = "";
        while((str = br.readLine()) != null){
            user += str;
        }

        // 解析json串
        JsonObject jsonObject = (JsonObject) new JsonParser().parse(user);
        String username = jsonObject.get("username").getAsString();

        ResultDTO<List<String>> result = new ListProductDAO().listProductByTrolley(username);

        PrintWriter writer = resp.getWriter();

        writer.print(JSONObject.toJSON(result));
    }
}
