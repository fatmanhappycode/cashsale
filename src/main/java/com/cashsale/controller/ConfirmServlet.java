package com.cashsale.controller;

import com.alibaba.fastjson.JSONObject;
import com.cashsale.bean.Result;
import com.cashsale.service.UserService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 肥宅快乐码
 * @date 2018/11/1 - 13:08
 */
@WebServlet("/confirm")
public class ConfirmServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 设置响应编码
        resp.setContentType("application/json;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        // 获取响应体的json串
        BufferedReader br = req.getReader();
        String str,user = "";
        while((str = br.readLine()) != null){
            user += str;
        }


        // 解析json串
        JsonObject jsonObject = (JsonObject) new JsonParser().parse(user);
        String encoded = jsonObject.get("encode").getAsString();

        UserService comfirmUser = new UserService();
        Result result = comfirmUser.userComfirm(encoded);
        PrintWriter writer = resp.getWriter();

        writer.print(JSONObject.toJSON(result));

    }
}
