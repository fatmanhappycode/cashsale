package com.cashsale.controller;

import com.alibaba.fastjson.JSONObject;
import com.cashsale.bean.ProductDO;
import com.cashsale.bean.ResultDTO;
import com.cashsale.service.TransactService;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 商品交易
 * @author Sylvia
 * 2018年11月24日
 */
@WebServlet("/transact")
public class TransactServlet extends HttpServlet{

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        // 设置响应编码
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        //设置请求编码
        request.setCharacterEncoding("utf-8");

        // 读取前端传过来的json串
        BufferedReader br = request.getReader();
        String str,product = "";
        while((str = br.readLine()) != null){
            product += str;
        }
        ProductDO productDO = new Gson().fromJson(product, ProductDO.class);
        int productId = productDO.getProductId();

        ResultDTO result = new TransactService().transact(productId);
        PrintWriter write = response.getWriter();
        write.print(JSONObject.toJSON(result));
    }
}
